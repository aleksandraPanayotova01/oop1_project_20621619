import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class OpenCloseXmlFile {

    private File inputFile;

    public void readXml(String fileName) {
        try {
            inputFile = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println( doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("room");
            System.out.println("---------------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("Current " + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("Room number :" + eElement.getAttribute("number"));
                    System.out.println("Available:" + eElement.getElementsByTagName("free").item(0).getTextContent());
                    System.out.println("Check in date :" + eElement.getElementsByTagName("checkInDate").item(0).getTextContent());
                    System.out.println("Check out date :" + eElement.getElementsByTagName("checkOutDate").item(0).getTextContent());
                    System.out.println("Number of beds:" + eElement.getElementsByTagName("beds").item(0).getTextContent());
                    System.out.println("Number of guests:" + eElement.getElementsByTagName("guests").item(0).getTextContent());
                    System.out.println("Note:" + eElement.getElementsByTagName("note").item(0).getTextContent());
                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                }
            }
            CopyContent copy=new CopyContent();
            copy.copyContent("hotel.xml","temp.xml");
            System.out.println("---------------------------------");

        }  catch (Exception e) {
            System.out.println("Please check parameters of command!");
        }
    }

    public void close() {
        if(inputFile != null) {
            inputFile = null;

            try {
                Files.deleteIfExists(Paths.get("temp.xml"));
            } catch (Exception e) {
                System.out.println("Please check parameters of command!");
            }
        } else {
            System.out.println("There is no open file!");
        }
    }
    public void exit(){
        close();
        System.out.println("Exiting the program...");
    }
    public File getInputFile() {
        return inputFile;
    }
}