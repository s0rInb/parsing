package com.aston.health;

import com.aston.health.Configuration.HibernateSessionFactory;
import com.aston.health.entity.ClearFfoms;
import com.aston.health.entity.ClearRosZdravNadzor;
import com.aston.health.entity.EntityFfoms;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FillClearFfoms {
    private static int batch = 0;
    private static int serialCode = 0;
    public static void main(String[] args) throws IOException, ParseException {
        clearTable();
        List<EntityFfoms> unclearEntityFfomss  = getUnclearRosminzdravList();
        List<ClearFfoms> clearFfomsList = new ArrayList<>();
        unclearEntityFfomss.forEach(entityFfoms -> clearFfomsList.add(new ClearFfoms(entityFfoms)));
        Map<String, List<ClearFfoms>> collect = clearFfomsList.stream().collect(Collectors.groupingBy(o -> o.getDirectorName()+o.getDirectorPatronymic()+o.getDirectorSurname()));
        collect.forEach((s, clearFfoms) -> {
            clearFfoms.sort((o1, o2) -> LocalDate.parse(o2.getLicenseStartDate(), DateTimeFormatter.ofPattern("dd.LL.yyyy")).compareTo(LocalDate.parse(o1.getLicenseStartDate(),DateTimeFormatter.ofPattern("dd.LL.yyyy"))));
            for (int i=1; i<clearFfoms.size()+1; i++){
                clearFfoms.get(i-1).setIndexOfDuplicate(i);
                clearFfoms.get(i-1).setSerialCode(serialCode);
            }
            serialCode++;
        });
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
        List<EntityFfoms> collect = session.createQuery("from EntityFfoms ", EntityFfoms.class).stream().collect(Collectors.toList());
        session.close();
        return collect;
    }
}
