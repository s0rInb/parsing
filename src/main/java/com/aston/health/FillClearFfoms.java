package com.aston.health;

import com.aston.health.Configuration.HibernateSessionFactory;
import com.aston.health.entity.ClearFfoms;
import com.aston.health.entity.EntityFfoms;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FillClearFfoms {
    private static int batch = 0;

    public static void main(String[] args) throws IOException, ParseException {
        clearTable();
        List<EntityFfoms> unclearEntityFfomss  = getUnclearRosminzdravList();
        List<ClearFfoms> clearFfomsList = new ArrayList<>();
        unclearEntityFfomss.forEach(entityFfoms -> clearFfomsList.add(new ClearFfoms(entityFfoms)));
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        long start = System.nanoTime();
        clearFfomsList.forEach(entityFfoms -> {
            session.save(entityFfoms);
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
        tx.commit();
        session.close();
        System.out.println();
    }

    private static void clearTable() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.createNativeQuery("TRUNCATE TABLE clear_ffoms RESTART IDENTITY CASCADE").executeUpdate();
        tx.commit();
        session.close();
    }

    private static List<EntityFfoms> getUnclearRosminzdravList() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<EntityFfoms> collect = session.createNativeQuery("SELECT * FROM (SELECT r1.*, RANK() OVER (PARTITION BY r1.full_name ORDER BY to_date(r1.license_start_date, 'dd.mm.yyyy') DESC ) dest_rank FROM ffoms r1) AS r1dr WHERE dest_rank = 1", EntityFfoms.class).stream().collect(Collectors.toList());
        session.close();
        return collect;
    }
}
