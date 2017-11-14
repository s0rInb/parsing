package com.aston.health;

import com.aston.health.Utils.SAXPars;
import com.aston.health.Utils.SAXParsWorks;
import com.aston.health.Utils.UnZip;
import org.apache.commons.io.FileUtils;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FillRosMinZdravNadzor {
    public static void main(String args[]) throws IOException, JAXBException, SAXException, ParserConfigurationException {
        System.out.println("start fill ros zdrav nadzor");
        downloadArchive();
        unzip();

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        SAXParsWorks saxParsWorks = new SAXParsWorks();

        parser.parse(new File("unzip/data-20171029-structure-20170926.xml"),saxParsWorks);
        SAXPars saxp = new SAXPars();
        parser.parse(new File("unzip/data-20171029-structure-20170926.xml"),saxp);

        System.out.println("end fill ros zdrav nadzor");
    }
    private static void downloadArchive() throws IOException {
        System.out.println("Start download from http://www.roszdravnadzor.ru/opendata/7710537160-licenses/data-20171029-structure-20170926.zip");
        URL url = new URL("http://www.roszdravnadzor.ru/opendata/7710537160-licenses/data-20171029-structure-20170926.zip");
        File file = new File("roszdravnadzor.zip");
        FileUtils.copyURLToFile(url, file);
        System.out.println("End download");
    }
    private static void unzip() throws IOException{
        System.out.println("Start unzip");
        UnZip unZip = new UnZip();
        unZip.unZipIt("roszdravnadzor.zip","unzip");
        System.out.println("End unzip");
    }
}
