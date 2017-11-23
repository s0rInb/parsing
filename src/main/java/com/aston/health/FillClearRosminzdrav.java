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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FillClearRosminzdrav {
    private static int batch = 0;
    private static int serialCode = 0;

    public static void main(String[] args) throws IOException, ParseException {
        clearTable();
        List<EntityRosminzdrav> unclearEntityRosminzdravs = getUnclearRosminzdravList();
        List<ClearRosminzdrav> clearRosminzdravsList = new ArrayList<>();
        unclearEntityRosminzdravs.forEach(entityRosminzdrav -> clearRosminzdravsList.add(new ClearRosminzdrav(entityRosminzdrav)));
        Map<String, List<ClearRosminzdrav>> collect = clearRosminzdravsList.stream().collect(Collectors.groupingBy(ClearRosminzdrav::getInn));

        collect.forEach((s, clearRosminzdravs) -> {
            clearRosminzdravs.sort((o1, o2) -> LocalDate.parse(o2.getCreateDate(), DateTimeFormatter.ofPattern("dd.LL.yyyy")).compareTo(LocalDate.parse(o1.getCreateDate(),DateTimeFormatter.ofPattern("dd.LL.yyyy"))));
            for (int i = 1; i < clearRosminzdravs.size() + 1; i++) {
                clearRosminzdravs.get(i - 1).setIndexOfDuplicate(i);
                clearRosminzdravs.get(i - 1).setSerialCode(serialCode);
            }
            serialCode++;
        });
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        long start = System.nanoTime();
        clearRosminzdravsList.forEach(entityRosminzdrav -> {
            batch++;
            session.save(entityRosminzdrav);
            if (batch % 50 == 0) {
                session.flush();
                if (batch % 1000 == 0) {
                    long end = System.nanoTime();
                    System.out.println(batch + " : " + ((double) TimeUnit.NANOSECONDS.toMillis(end - start) / 1000));
                }
                session.clear();
            }
        });
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
        List<EntityRosminzdrav> collect = session.createQuery("from EntityRosminzdrav", EntityRosminzdrav.class).list();
        session.close();
        return collect;
    }
}
