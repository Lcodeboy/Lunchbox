package com.server.config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

public class Config {
    public static void main(String[] args) {
        try {
            File f = new File("./Logger/XML/com.server.config.xml");
            SAXReader reader = new SAXReader();
            Document doc = reader.read(f);
            Element root = doc.getRootElement();
            Element foo;
            for (Iterator i=root.elementIterator("LOG");i.hasNext();){
                foo = (Element) i.next();
                ConfigInit.logFile = foo.elementText("FILE");
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
