package com.aston.health;

import com.aston.health.Configuration.HibernateSessionFactory;
import com.aston.health.Utils.StringReplace;
import com.aston.health.entity.EntityFfoms;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FillFfoms {
    public static void main(String[] args) throws IOException {
        System.out.println("start fill ffoms");
        downloadExcel();
        System.out.println("end fill ffoms");
        FileInputStream file = new FileInputStream(new File("ffoms.xls"));

        //Create Workbook instance holding reference to .xlsx file
        Workbook workbook = new HSSFWorkbook(file);

        //Get first/desired sheet from the workbook
        Sheet sheet = workbook.getSheetAt(0);

        List<EntityFfoms> ffomsList = new ArrayList<>();
        System.out.println("start parse ffoms");
        for (int i=sheet.getFirstRowNum()+1; i<=sheet.getLastRowNum(); i++){
            ffomsList.add(new EntityFfoms(sheet.getRow(i)));
        }
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx =session.beginTransaction();
        session.createNativeQuery("TRUNCATE TABLE public.ffoms RESTART IDENTITY CASCADE").executeUpdate();
        ffomsList.stream().forEach(ffoms -> {
            ffoms.setShortName(StringReplace.clearString(ffoms.getShortName()));
            ffoms.setFullName(StringReplace.clearString(ffoms.getFullName()));
            session.save(ffoms);
        });
        tx.commit();
        session.close();
        System.out.println("end parse ffoms");
    }



    private static void downloadExcel() throws IOException {
        System.out.println("start download ffoms from http://www.ffoms.ru/upload/register_documents_tmp/МО-Портал.xls");
        URL url = new URL("http://www.ffoms.ru/upload/register_documents_tmp/МО-Портал.xls");
        File file = new File("ffoms.xls");
        FileUtils.copyURLToFile(url, file);
        System.out.println("end download ffoms");
    }

}
