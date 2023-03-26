import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DomParserDTD {
    private final static String xmlFileName = "resources/bookInfo.xml";
    static Boolean errorFlag = false;
    public static void main(String[] args) {
        try {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        // Validating the XML file against DTD

        dbf.setValidating(true);
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        // Custom Error handler to display error messages

        ErrorHandler errorHandler = new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {
                System.out.println("Warning: ");
                printInfo(exception);
            }

            @Override
            public void error(SAXParseException exception) throws SAXException {
                System.out.println("Error: ");
                printInfo(exception);
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                System.out.println("Fatal Error: ");
                printInfo(exception);
            }
            private void printInfo(SAXParseException e) {
                errorFlag = true;
                System.out.println("   Public ID: "+e.getPublicId());
                System.out.println("   System ID: "+e.getSystemId());
                System.out.println("   Line number: "+e.getLineNumber());
                System.out.println("   Column number: "+e.getColumnNumber());
                System.out.println("   Message: "+e.getMessage());
            }

        };

        db.setErrorHandler(errorHandler);
        // Parsing the XML

        Document doc = db.parse(new File(xmlFileName));

        // Retrieve the content only if no error is there

            doc.getDocumentElement().normalize();

            // Parsing the XML Document
            System.out.println("ROOT ELEMENT");
            // Retrieving the root element of XML File
            System.out.println(doc.getDocumentElement().getNodeName());
            System.out.println("---------");
            NodeList list = doc.getElementsByTagName("book");
            int i = 0;

            // Error flag will be set in case of errors while validating through DTD
            while(!errorFlag && i < list.getLength()) {
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
                i+=1;
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}




