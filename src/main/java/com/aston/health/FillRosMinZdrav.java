package com.aston.health;

import com.aston.health.Configuration.HibernateSessionFactory;
import com.aston.health.Utils.StringReplace;
import com.aston.health.entity.EntityRosminzdrav;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.hibernate.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class FillRosMinZdrav {
    public static void main(String args[]) throws IOException {
        System.out.println("start fill ros min zdrav");
        int count = getRosminzdravCount();
        int pageSize = 500;
        int pageCount = (int) Math.ceil((double) count / pageSize);
        Set<EntityRosminzdrav> entityRosminzdravs = new HashSet<EntityRosminzdrav>() {
        };
        for (int i = 1; i <= pageCount; i++) {
            System.out.println("start page №" + i + " of " + pageCount);
            URL url = new URL("http://nsi.rosminzdrav.ru/data?pageId=refbookProvider$MS4yLjY0My41LjEuMTMuMi4xLjEuMTc4ITQyNTghMTUwODg4OTY5ODIyMw&containerId=refbookSimple0&size=" + pageSize + "&page=" + i + "&filter.version.id=4258&filter.dictionaryId=3560");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String json = in.readLine();
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            JsonFactory factory = mapper.getFactory();
            JsonParser jsonParser = factory.createParser(json);
            JsonNode node = mapper.readTree(jsonParser);
            ArrayNode list = (ArrayNode) node.get("list");
            list.forEach(jsonNode -> {
                try {
                    EntityRosminzdrav entityRosminzdrav = mapper.readerFor(EntityRosminzdrav.class).readValue(jsonNode);
                    entityRosminzdrav.setNameFull(StringReplace.clearString(entityRosminzdrav.getNameFull()));
                    entityRosminzdrav.setNameShort(StringReplace.clearString(entityRosminzdrav.getNameShort()));
                    entityRosminzdravs.add(entityRosminzdrav);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("End page №" + i + " of " + pageCount);
        }
        System.out.println("start save to database");
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        session.createNativeQuery("TRUNCATE TABLE rosminzdrav RESTART IDENTITY CASCADE").executeUpdate();
        entityRosminzdravs.stream().forEach(session::save);
        session.getTransaction().commit();
        session.close();
        System.out.println("save to database finish");
        System.out.println("end fill ros min zdrav");
    }

    private static int getRosminzdravCount() throws IOException {
        System.out.println("start getRosminzdravCount");
        URL url = new URL("http://nsi.rosminzdrav.ru/data?pageId=refbookProvider$MS4yLjY0My41LjEuMTMuMi4xLjEuMTc4ITQyNTghMTUwODg4OTY5ODIyMw&containerId=refbookSimple0&size=15&page=1&filter.version.id=4258&filter.dictionaryId=3560");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String json = in.readLine();
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser jsonParser = factory.createParser(json);
        JsonNode node = mapper.readTree(jsonParser);
        System.out.println("end getRosminzdravCount");
        return node.get("count").asInt();
    }
}