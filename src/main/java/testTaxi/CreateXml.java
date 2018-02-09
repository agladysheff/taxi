package testTaxi;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicInteger;

public class CreateXml {


    public static void updateElementValue(Document doc) {
        NodeList nidelist = doc.getElementsByTagName("dispatched");
        Element lang = null;
        for (int i = 0; i < nidelist.getLength(); i++) {
            lang = (Element) nidelist.item(i);
            System.out.println(lang.getAttribute("id"));
            lang.setAttribute("id", "4");
        }
    }


    public static void updateMessage(String filePath, AtomicInteger num) {

        File xmlFile = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            updateElementValue(doc);
            Node message = doc.getFirstChild();
            Element language = doc.createElement("dispatched");


            language.setAttribute("id", String.valueOf(num));
            message.appendChild(language);
            doc.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);


        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public static void createMessage(String filePath, int num) {


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element message = doc.createElement("message");
            doc.appendChild(message);
            Element target = doc.createElement("target");
            target.setAttribute("id", String.valueOf(num));
            message.appendChild(target);
            Element sometags = doc.createElement("sometags");

            message.appendChild(sometags);


            for (int i = 0; i < 3; i++) {
                Element data = doc.createElement("data");
                data.setTextContent(" ");
                sometags.appendChild(data);
            }


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);


        } catch (Exception exc) {
            exc.printStackTrace();
        }


    }

    public static String getTarget(String filePath, String attribute) {
        String idTarget = null;
        File xmlFile = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nidelist = doc.getElementsByTagName(attribute);
            Element lang = (Element) nidelist.item(0);
            idTarget = lang.getAttribute("id");
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return idTarget;
    }


    public static String createXmlString(int idTarget) {


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element message = doc.createElement("message");
            doc.appendChild(message);
            Element target = doc.createElement("target");
            target.setAttribute("id", String.valueOf(idTarget));
            message.appendChild(target);
            Element sometags = doc.createElement("sometags");

            message.appendChild(sometags);


            for (int i = 0; i < 3; i++) {
                Element data = doc.createElement("data");
                data.setTextContent(" ");
                sometags.appendChild(data);
            }


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.getBuffer().toString();


        } catch (Exception exc) {
            exc.printStackTrace();
        }

        return null;

    }

    public static String updateXmlString(String xmlStr, int idDispatched) {


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));

            Node message = doc.getFirstChild();
            Element language = doc.createElement("dispatched");


            language.setAttribute("id", String.valueOf(idDispatched));
            message.appendChild(language);


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.getBuffer().toString();


        } catch (Exception exc) {
            exc.printStackTrace();
        }

        return null;

    }

    public static String getTargetString(String xmlStr, String element) {
        String idTarget = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
            doc.getDocumentElement().normalize();
            NodeList nidelist = doc.getElementsByTagName(element);
            Element lang = (Element) nidelist.item(0);
            idTarget = lang.getAttribute("id");
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return idTarget;
    }

    public static void createFile(String filePath, String xmlStr) {


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));


            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);


        } catch (Exception exc) {
            exc.printStackTrace();
        }


    }


}

