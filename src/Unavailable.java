import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class Unavailable {
    private Transform transform=new Transform();
    public void unavailableRoom(int room, LocalDate from, LocalDate to, List<String> note,File openedFile) {
        if(openedFile!=null){
            File xmlFile = new File("temp.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(xmlFile);
                Element root = doc.getDocumentElement();
                NodeList childNodes = root.getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node node = childNodes.item(i);

                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) node;
                        if (Integer.parseInt(eElement.getAttribute("number")) == room) {
                            eElement.getElementsByTagName("free").item(0).setTextContent("no");
                            eElement.getElementsByTagName("checkInDate").item(0).setTextContent(from.toString());
                            eElement.getElementsByTagName("checkOutDate").item(0).setTextContent(to.toString());
                            eElement.getElementsByTagName("guests").item(0).setTextContent("0");
                            eElement.getElementsByTagName("note").item(0).setTextContent(note.toString());
                        }
                    }
                }
                transform.transformOriginalFile(doc,"temp.xml");
            }  catch (Exception e) {
                System.out.println("Please check parameters of command!");
            }
        }else {
            System.out.println("Please open file!");
        }
    }
}

