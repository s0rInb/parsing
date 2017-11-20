package com.aston.health;

import com.aston.health.Configuration.HibernateSessionFactory;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, JAXBException, SAXException, ParseException {
        FillFfoms.main(args);
        FillRosMinZdrav.main(args);
        FillRosMinZdravNadzor.main(args);
        FillClearFfoms.main(args);
        FillClearRosminzdrav.main(args);
        FillClearRosZdravNadzor.main(args);
        MergeAll.main(args);
        HibernateSessionFactory.shutdown();
    }
}
