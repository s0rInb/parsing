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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SAXPars extends DefaultHandler {

    private List<EntityRosMinZdravNadzor> nadzorList = new ArrayList<>();
    private EntityRosMinZdravNadzor nadzor = new EntityRosMinZdravNadzor();
    private List<Address> addressList = new ArrayList<>();
    private Address address = new Address();
    private List<Works> worksList = new ArrayList<>();
    private Map<String, Works> worksHashMap= new HashMap<>();
    private String thisElement = "";
    private int count = 1;
    private Long batch = 0L;
    private String thisWork = "";
    private long startGlobal = System.nanoTime();

    public List<EntityRosMinZdravNadzor> getResult() {
        return nadzorList;
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start parse XML");
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx =session.beginTransaction();
        session.createNativeQuery("TRUNCATE TABLE public.ros_min_zdrav_nadzor RESTART IDENTITY CASCADE").executeUpdate();
        tx.commit();
        session.close();
//        Session session = HibernateSessionFactory.getSessionFactory().openSession();
//        worksHashMap = session.createQuery("select w from Works w)",Works.class).stream().collect(Collectors.toMap(Works::getWork, Function.identity()));
//        session.close();
        System.out.println();
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        thisElement = qName;
        switch (thisElement) {
            case "licenses":
                if(nadzorList.size()==1000){
                    long start = System.nanoTime();
                    Session session = HibernateSessionFactory.getSessionFactory().openSession();
                    Transaction tx =session.beginTransaction();
                    nadzorList.stream().forEach(entityRosMinZdravNadzor -> {
                        entityRosMinZdravNadzor.setAbbreviatedNameLicensee(StringReplace.clearString(entityRosMinZdravNadzor.getAbbreviatedNameLicensee()));
                        entityRosMinZdravNadzor.setFullNameLicensee(StringReplace.clearString(entityRosMinZdravNadzor.getFullNameLicensee()));
                        session.save(entityRosMinZdravNadzor);
                        batch++;
                        if(batch%50==0){
                            session.flush();
                            session.clear();
                        }
                    });
                    tx.commit();
                    session.close();
                    nadzorList=new ArrayList<>();
                    long end = System.nanoTime();
                    System.out.println(batch+" : "+ ((double)TimeUnit.NANOSECONDS.toMillis(end-start)/1000) + " Total time: " +((double)TimeUnit.NANOSECONDS.toMillis(end-startGlobal)/1000));
                    count++;
                }
                nadzor = new EntityRosMinZdravNadzor();
                nadzorList.add(nadzor);
                break;
            case "work_address_list":
                addressList = new ArrayList<>();
                nadzor.setAddressPlace(addressList);
                break;
            case "address_place":
                address = new Address();
                addressList.add(address);
                break;
//            case "works":
//                worksList = new ArrayList<>();
//                address.setWorks(worksList);
//                break;
//            case "work": {
//                thisWork= "";
////                works = new Works();
////                worksList.add(works);
////                break;
//            }
        }

    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
//        if (thisElement.equals("work")){
//            worksList.add(worksHashMap.get(thisWork.trim()));
//        }
        thisElement = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        switch (thisElement) {
            case "name": {
                nadzor.setName(nadzor.getName() + new String(ch, start, length));
                break;
            }
            case "activity_type": {
                nadzor.setActivityType(nadzor.getActivityType() + new String(ch, start, length));
                break;
            }
            case "full_name_licensee": {
                nadzor.setFullNameLicensee(nadzor.getFullNameLicensee() + new String(ch, start, length));
                break;
            }
            case "abbreviated_name_licensee": {
                nadzor.setAbbreviatedNameLicensee(nadzor.getAbbreviatedNameLicensee() + new String(ch, start, length));
                break;
            }
            case "brand_name_licensee": {
                nadzor.setBrandNameLicensee(nadzor.getBrandNameLicensee() + new String(ch, start, length));
                break;
            }
            case "form": {
                nadzor.setForm(nadzor.getForm() + new String(ch, start, length));
                break;
            }
            case "address": {
                if (nadzor.getAddressPlace().size() == 0) {
                    nadzor.setAddress(new String(ch, start, length));
                } else {
                    address.setAddress(address.getAddress() + new String(ch, start, length));
                }
                break;
            }
            case "ogrn": {
                nadzor.setOgrn(nadzor.getOgrn() + new String(ch, start, length));
                break;
            }
            case "inn": {
                nadzor.setInn(nadzor.getInn() + new String(ch, start, length));
                break;
            }
            case "index": {
                address.setIndex(address.getIndex() + new String(ch, start, length));
                break;
            }
            case "region": {
                address.setRegion(address.getRegion() + new String(ch, start, length));
                break;
            }
            case "city": {
                address.setCity(address.getCity() + new String(ch, start, length));
                break;
            }
            case "street": {
                address.setStreet(address.getStreet() + new String(ch, start, length));
                break;
            }
//            case "work": {
//                thisWork=thisWork+new String(ch, start, length);
//                break;
//            }
            case "number": {
                nadzor.setNumber(nadzor.getNumber() + new String(ch, start, length));
                break;
            }
            case "date": {
                nadzor.setDate(nadzor.getDate() + new String(ch, start, length));
                break;
            }
            case "number_orders": {
                nadzor.setNumberOrders(nadzor.getNumberOrders() + new String(ch, start, length));
                break;
            }
            case "date_order": {
                nadzor.setDateOrder(nadzor.getDateOrder() + new String(ch, start, length));
                break;
            }
            case "date_register": {
                nadzor.setDateRegister(nadzor.getDateRegister() + new String(ch, start, length));
                break;
            }
            case "number_duplicate": {
                nadzor.setNumberDuplicate(nadzor.getNumberDuplicate() + new String(ch, start, length));
                break;
            }
            case "date_duplicate": {
                nadzor.setDateDuplicate(nadzor.getDateDuplicate() + new String(ch, start, length));
                break;
            }
            case "termination": {
                nadzor.setTermination(nadzor.getTermination() + new String(ch, start, length));
                break;
            }
            case "date_termination": {
                nadzor.setDateTermination(nadzor.getDateTermination() + new String(ch, start, length));
                break;
            }
            case "information": {
                nadzor.setInformation(nadzor.getInformation() + new String(ch, start, length));
                break;
            }
            case "information_regulations": {
                nadzor.setInformationRegulations(nadzor.getInformationRegulations() + new String(ch, start, length));
                break;
            }
            case "information_suspension_resumption": {
                nadzor.setInformationSuspensionResumption(nadzor.getInformationSuspensionResumption() + new String(ch, start, length));
                break;
            }
            case "information_cancellation": {
                nadzor.setInformationCancellation(nadzor.getInformationCancellation() + new String(ch, start, length));
                break;
            }
            case "information_reissuing": {
                nadzor.setInformationReissuing(nadzor.getInformationReissuing() + new String(ch, start, length));
                break;
            }
        }
    }

    @Override
    public void endDocument() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx =session.beginTransaction();
        nadzorList.stream().forEach(entityRosMinZdravNadzor -> {
            entityRosMinZdravNadzor.setAbbreviatedNameLicensee(StringReplace.clearString(entityRosMinZdravNadzor.getAbbreviatedNameLicensee()));
            entityRosMinZdravNadzor.setFullNameLicensee(StringReplace.clearString(entityRosMinZdravNadzor.getFullNameLicensee()));
            session.save(entityRosMinZdravNadzor);
        });
        tx.commit();
        session.close();
        nadzorList=new ArrayList<>();
        long end = System.nanoTime();
        System.out.println(batch+" : " +" Total time: " +((double)TimeUnit.NANOSECONDS.toMillis(end-startGlobal)/1000));
        System.out.println("End parse XML...");
    }
}
