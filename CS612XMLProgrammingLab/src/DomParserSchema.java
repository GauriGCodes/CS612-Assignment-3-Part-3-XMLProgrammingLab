import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;


public class DomParserSchema {
        private final static String xmlFileName = "resources/bookInfo.xml";
        public static void main(String [] args){
            Schema schema = null;
            String schemaFileName = "resources/bookInfoSchema.xsd";

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new File(xmlFileName));

                // Validating the XML Schema
                String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
                SchemaFactory factory = SchemaFactory.newInstance(language);
                schema = factory.newSchema(new File(schemaFileName));

                Validator validator = schema.newValidator();
                validator.validate(new DOMSource(doc));

                // Ensures that document hierarchy is not affected by any whitespaces
                // https://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
                doc.getDocumentElement().normalize();

                // Parsing the XML Document
                System.out.println("ROOT ELEMENT");
                // Retrieving the root element of XML File
                System.out.println(doc.getDocumentElement().getNodeName());
                System.out.println("---------");
                NodeList list = doc.getElementsByTagName("book");

                for(int i=0;i<list.getLength();i++){
                    Node node = list.item(i);
                    Element element = (Element) node;
                    // Get the Book ID
                    String id = element.getAttribute("id");

                    // Get all other text data
                    String title = element.getElementsByTagName("title").item(0).getTextContent();
                    String author = element.getElementsByTagName("author").item(0).getTextContent();
                    String genre = element.getElementsByTagName("genre").item(0).getTextContent();

                    // Print the content
                    System.out.println("Current Element :" + node.getNodeName());
                    System.out.println("Book Id : " + id);
                    System.out.println("Title : " + title);
                    System.out.println("Author : " + author);
                    System.out.println("Genre : " + genre);
                    System.out.println("---------");
                }
            }catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }
        }
}
