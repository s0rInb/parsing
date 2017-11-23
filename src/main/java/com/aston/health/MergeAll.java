package com.aston.health;

import com.aston.health.Configuration.HibernateSessionFactory;
import com.aston.health.entity.ClearFfoms;
import com.aston.health.entity.ClearRosZdravNadzor;
import com.aston.health.entity.ClearRosminzdrav;
import com.aston.health.entity.Result;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MergeAll {
    private static int batch = 0;

    public static void main(String[] args) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<ClearRosminzdrav> rosminzdravList = (List<ClearRosminzdrav>) session.createQuery("from ClearRosminzdrav").list();
        List<ClearFfoms> ffomsList = (List<ClearFfoms>) session.createQuery("from ClearFfoms").list();
        List<Result> resultList = new ArrayList<>();
        rosminzdravList.forEach(clearRosminzdrav -> {
            Result result = new Result();
            result.setClearRosminzdrav(clearRosminzdrav);
            result.setClearRosminzdravSerialNumber(clearRosminzdrav.getSerialCode());
            result.setCountTe(result.getCountTe() + 1);
            resultList.add(result);
        });
        ffomsList.stream().forEach(clearFfoms -> {
            final boolean[] isAdd = {false};
            resultList.stream().filter(result ->
                    result.getClearFfoms() == null && result.getClearRosminzdrav() != null &&
                            result.getClearRosminzdrav().getRegionName().equalsIgnoreCase(clearFfoms.getSubject()) &&
                            (result.getClearRosminzdrav().getNameShort().equalsIgnoreCase(clearFfoms.getShortName()) ||
                                    result.getClearRosminzdrav().getNameFull().equalsIgnoreCase(clearFfoms.getFullName())))
                    .findFirst().ifPresent(result -> {
                result.setClearFfoms(clearFfoms);
                result.setFfomsSerialNumber(clearFfoms.getSerialCode());
                result.setCountTe(result.getCountTe() + 1);
                isAdd[0] = true;
            });
            if (!isAdd[0]) {
                Result result = new Result();
                result.setClearFfoms(clearFfoms);
                result.setFfomsSerialNumber(clearFfoms.getSerialCode());
                result.setCountTe(result.getCountTe() + 1);
                resultList.add(result);
            }
        });
        System.out.println("s");
        List<ClearRosZdravNadzor> rosZdravNadzorList = (List<ClearRosZdravNadzor>) session.createQuery("from ClearRosZdravNadzor ").list();
        rosZdravNadzorList.stream().forEach(clearRosZdravNadzor -> {
            final boolean[] isAdd = {false};
            resultList.stream().filter(result ->
                    result.getClearRosZdravNadzor() == null && (
                            (result.getClearRosminzdrav() != null && (
//                                    result.getClearRosminzdrav().getRegionName().equalsIgnoreCase(clearRosZdravNadzor.getRegionName()) &&
                                    (result.getClearRosminzdrav().getNameShort().equalsIgnoreCase(clearRosZdravNadzor.getAbbreviatedNameLicensee())
                                            || result.getClearRosminzdrav().getNameFull().equalsIgnoreCase(clearRosZdravNadzor.getFullNameLicensee())) || result.getClearRosminzdrav().getInn().equalsIgnoreCase(clearRosZdravNadzor.getInn())))
                                    ||
                                    (result.getClearFfoms() != null) && (
//                                            result.getClearFfoms().getSubject().equalsIgnoreCase(clearRosZdravNadzor.getRegionName()) &&
                                            (result.getClearFfoms().getShortName().equalsIgnoreCase(clearRosZdravNadzor.getAbbreviatedNameLicensee())
                                                    || result.getClearFfoms().getFullName().equalsIgnoreCase(clearRosZdravNadzor.getFullNameLicensee())
                                            )))).findFirst().ifPresent(result -> {
                result.setClearRosZdravNadzor(clearRosZdravNadzor);
                result.setClearRosZdravNadzorSerialNumber(clearRosZdravNadzor.getSerialCode());
                result.setCountTe(result.getCountTe() + 1);
                isAdd[0] = true;
            });
        });
        resultList.removeIf(result -> result.getCountTe() == 1);
        long start = System.nanoTime();
        Transaction tx = session.beginTransaction();
        session.createNativeQuery("TRUNCATE TABLE result RESTART IDENTITY").executeUpdate();
        resultList.stream().forEach(result -> {
            session.save(result);
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
        session.createNativeQuery("UPDATE clear_ffoms SET clearrosminzdrav_serial_code=result.clearrosminzdrav_serial_code,clearroszdravnadzor_serial_code=result.clearroszdravnadzor_serial_code from result WHERE clear_ffoms.guid=result.clearffoms_guid;").executeUpdate();
        session.createNativeQuery("UPDATE clear_rosminzdrav SET clearroszdravnadzor_serial_code=result.clearroszdravnadzor_serial_code,ffoms_serial_code=result.ffoms_serial_code FROM result WHERE clear_rosminzdrav.guid=result.clearrosminzdrav_guid;").executeUpdate();
        session.createNativeQuery("UPDATE clear_ros_zdrav_nadzor SET clearrosminzdrav_serial_code=result.clearrosminzdrav_serial_code,ffoms_serial_code=result.ffoms_serial_code FROM result WHERE clear_ros_zdrav_nadzor.guid=result.clearroszdravnadzor_guid;").executeUpdate();
        tx.commit();
        session.close();
        System.out.println("s");
    }
}
