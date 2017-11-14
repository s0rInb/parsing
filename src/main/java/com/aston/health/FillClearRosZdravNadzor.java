package com.aston.health;

import com.aston.health.Configuration.HibernateSessionFactory;
import com.aston.health.entity.ClearRosZdravNadzor;
import com.aston.health.entity.EntityRosMinZdravNadzor;
import com.aston.health.entity.Works;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FillClearRosZdravNadzor {
    private static int batch=0;
    public static void main(String[] args) throws IOException, ParseException {
        clearTable();
        List<EntityRosMinZdravNadzor> unclearRosZdravNadzorList = getUnclearRosZdravNadzorList();
        List<ClearRosZdravNadzor> clearRosZdravNadzorsList = new ArrayList<>();
        unclearRosZdravNadzorList.forEach(entityRosMinZdravNadzor -> clearRosZdravNadzorsList.add(new ClearRosZdravNadzor(entityRosMinZdravNadzor)));
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        long start = System.nanoTime();
        clearRosZdravNadzorsList.forEach(clearRosZdravNadzor -> {
            session.save(clearRosZdravNadzor);
            batch++;
            if (batch % 50 == 0) {
                session.flush();
                session.clear();
                if (batch % 1000 ==0){
                    long end = System.nanoTime();
                    System.out.println(batch+" : "+ ((double) TimeUnit.NANOSECONDS.toMillis(end-start)/1000));
                }
            }
        });
        session.createNativeQuery("UPDATE clear_ros_zdrav_nadzor crzn SET region_name=(SELECT sd.name from subject_dictionary sd WHERE sd.inn =substring(crzn.inn, 1,2))");
        tx.commit();
        session.close();
        System.out.println();
    }

    private static void clearTable() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.createNativeQuery("TRUNCATE TABLE clear_ros_zdrav_nadzor RESTART IDENTITY CASCADE").executeUpdate();
        tx.commit();
        session.close();
    }

    private static List<EntityRosMinZdravNadzor> getUnclearRosZdravNadzorList() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<EntityRosMinZdravNadzor> collect = session.createNativeQuery("SELECT * FROM (SELECT r1.*,RANK() OVER (PARTITION BY r1.abbreviated_name_licensee||r1.inn ORDER BY to_date(r1.date,'dd.mm.yyyy') DESC) dest_rank FROM ros_min_zdrav_nadzor r1) AS r1dr WHERE dest_rank = 1", EntityRosMinZdravNadzor.class).stream().collect(Collectors.toList());
        session.close();
        return collect;
    }
}
