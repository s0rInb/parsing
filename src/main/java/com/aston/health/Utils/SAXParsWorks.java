package com.aston.health.Utils;

import com.aston.health.Configuration.HibernateSessionFactory;
import com.aston.health.entity.Address;
import com.aston.health.entity.EntityRosMinZdravNadzor;
import com.aston.health.entity.Works;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class SAXParsWorks extends DefaultHandler {

    private Set<Works> worksSet = new HashSet<>();
    private String thisElement = "";
    private String thisWork = "";
    private Set<String> stringSet = new HashSet<>();

    public Set<Works> getResult() {
        return worksSet;
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start parse Works");
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        thisElement = qName;
        if(thisElement.equals("work")){
            thisWork= "";
        }
    }


    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        if(thisElement.equals("work")){
            stringSet.add(thisWork.trim());
        }
        thisElement = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (thisElement.equals("work")) {
//            works = new Works();
//            works.setWork(new String(ch, start, length).trim());
//            worksSet.add(works);
            thisWork=thisWork+new String(ch, start, length);
        }
    }

    @Override
    public void endDocument() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        stringSet.stream().forEach(s -> {
            Works works = new Works();
            works.setWork(s);
            worksSet.add(works);
        });
        Transaction tx =session.beginTransaction();
        worksSet.stream().forEach(session::save);
        tx.commit();
        worksSet= session.createQuery("select w from Works w",Works.class).stream().collect(Collectors.toSet());
        session.close();
        System.out.println("End parse Works");
    }
}