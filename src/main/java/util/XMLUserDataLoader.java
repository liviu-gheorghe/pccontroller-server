package util;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pccontroller.App;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;

public class XMLUserDataLoader {
    private static HashMap<String,String> commands = new HashMap<>();

    private static Document loadXMLCommandsDocument() {
        Document commandsDocument = null;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            commandsDocument = documentBuilder.parse(XMLUserDataLoader.class.getResource("/data/commands.xml").toExternalForm());
            commandsDocument.getDocumentElement().normalize();
        }
        catch(ParserConfigurationException | SAXException | IOException exception) {
            exception.printStackTrace();
        }
        return commandsDocument;
    }

    public static void loadDefinedCommands() {
        Document commandsDocument = loadXMLCommandsDocument();
        NodeList elements = commandsDocument.getChildNodes().item(0).getChildNodes();
        for(int i=0;i<elements.getLength();i++) {
            Node element = elements.item(i);
            if(element.getNodeType() != Node.TEXT_NODE) {
                short c = 0;
                NodeList elementChildren = element.getChildNodes();
                for(int j=0;j<elementChildren.getLength();j++) {
                    if(elementChildren.item(j).getNodeName().equals("action")) {
                        c++;
                        if(c-1 == App.OS_ID)
                            commands.put(element.getNodeName(),elementChildren.item(j).getTextContent());
                    }
                }
            }
        }
    }
    public static String getAction(String action) {
        return commands.get(action);
    }
}
