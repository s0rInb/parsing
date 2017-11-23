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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FillClearRosZdravNadzor {
    private static int batch=0;
    private static int serialCode=0;
    public static void main(String[] args) throws IOException, ParseException {
        clearTable();
        List<EntityRosMinZdravNadzor> unclearRosZdravNadzorList = getUnclearRosZdravNadzorList();
        List<ClearRosZdravNadzor> clearRosZdravNadzorsList = new ArrayList<>();
        unclearRosZdravNadzorList.forEach(entityRosMinZdravNadzor -> clearRosZdravNadzorsList.add(new ClearRosZdravNadzor(entityRosMinZdravNadzor)));
        Map<String, List<ClearRosZdravNadzor>> collect = clearRosZdravNadzorsList.stream().collect(Collectors.groupingBy(ClearRosZdravNadzor::getInn));

        collect.forEach((s, clearRosZdravNadz) -> {
            try {
                clearRosZdravNadz.sort((o1, o2) -> LocalDate.parse(o2.getDate(), DateTimeFormatter.ofPattern("dd.LL.yyyy")).compareTo(LocalDate.parse(o1.getDate(),DateTimeFormatter.ofPattern("dd.LL.yyyy"))));
            }
            catch (DateTimeParseException ignored){

            }
            for (int i=1; i<clearRosZdravNadz.size()+1; i++){
                clearRosZdravNadz.get(i-1).setIndexOfDuplicate(i);
                clearRosZdravNadz.get(i-1).setSerialCode(serialCode);
            }
            serialCode++;
        });
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
        List<EntityRosMinZdravNadzor> collect = session.createQuery("from EntityRosMinZdravNadzor ", EntityRosMinZdravNadzor.class).list();
        session.close();
        return collect;
    }
}
