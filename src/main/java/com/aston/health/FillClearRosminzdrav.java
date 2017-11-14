package com.aston.health;

import com.aston.health.Configuration.HibernateSessionFactory;
import com.aston.health.entity.ClearRosZdravNadzor;
import com.aston.health.entity.ClearRosminzdrav;
import com.aston.health.entity.EntityRosMinZdravNadzor;
import com.aston.health.entity.EntityRosminzdrav;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FillClearRosminzdrav {
    private static int batch = 0;

    public static void main(String[] args) throws IOException, ParseException {
        clearTable();
        List<EntityRosminzdrav> unclearEntityRosminzdravs  = getUnclearRosminzdravList();
        List<ClearRosminzdrav> clearRosminzdravsList = new ArrayList<>();
        unclearEntityRosminzdravs.forEach(entityRosminzdrav -> clearRosminzdravsList.add(new ClearRosminzdrav(entityRosminzdrav)));
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        long start = System.nanoTime();
        clearRosminzdravsList.forEach(entityRosminzdrav -> {
            session.save(entityRosminzdrav);
            batch++;
            if (batch % 50 == 0) {
                session.flush();
                session.clear();
                if (batch % 1000 == 0) {
                    long end = System.nanoTime();
                    System.out.println(batch + " : " + ((double) TimeUnit.NANOSECONDS.toMillis(end - start) / 1000));
                }
            }
        });
        session.createNativeQuery("UPDATE clear_rosminzdrav cr set region_name=(SELECT sd.name FROM subject_dictionary sd WHERE sd.rosminzradv_name=cr.region_name) WHERE cr.region_name not in (SELECT distinct(sd2.name) from subject_dictionary sd2)");
        tx.commit();
        session.close();
        System.out.println();
    }

    private static void clearTable() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.createNativeQuery("TRUNCATE TABLE clear_rosminzdrav RESTART IDENTITY CASCADE").executeUpdate();
        tx.commit();
        session.close();
    }

    private static List<EntityRosminzdrav> getUnclearRosminzdravList() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<EntityRosminzdrav> collect = session.createNativeQuery("SELECT * FROM (SELECT r1.*, RANK() OVER (PARTITION BY r1.name_full|| r1.inn ORDER BY to_date(r1.create_date, 'dd.mm.yyyy') DESC ) dest_rank FROM rosminzdrav r1) AS r1dr WHERE dest_rank = 1", EntityRosminzdrav.class).stream().collect(Collectors.toList());
        session.close();
        return collect;
    }
}
