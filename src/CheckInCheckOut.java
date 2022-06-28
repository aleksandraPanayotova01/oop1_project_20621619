import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
public class CheckInCheckOut {
    private final Transform transform=new Transform();
    private final Report report=new Report();
    public void checkIn(int roomNumber, LocalDate checkInDate, LocalDate checkOutDate, List<String> note, int numberOfGuests, File openedFile) {
        if(openedFile != null){
            File xmlFile = new File("temp.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(xmlFile);
                doc.getDocumentElement().normalize();
                Element root = doc.getDocumentElement();
                NodeList childNodes = root.getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node node = childNodes.item(i);

                    if (node.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) node;
                        if (Integer.parseInt(eElement.getAttribute("number")) == roomNumber &&
                                (Integer.parseInt(eElement.getElementsByTagName("beds").item(0).getTextContent())
                                        >= numberOfGuests)) {
                            String cElement = eElement.getElementsByTagName("free").item(0).getTextContent();
                            if (cElement.equals("yes")) {
                                eElement.getElementsByTagName("free").item(0).setTextContent("no");
                                eElement.getElementsByTagName("checkInDate").item(0).setTextContent(checkInDate.toString());
                                eElement.getElementsByTagName("checkOutDate").item(0).setTextContent(checkOutDate.toString());
                                eElement.getElementsByTagName("note").item(0).setTextContent(note.toString());
                                if (numberOfGuests == 0) {
                                    eElement.getElementsByTagName("guests").item(0)
                                            .setTextContent(eElement.getElementsByTagName("beds").item(0).getTextContent());
                                } else if (numberOfGuests <= Integer.parseInt(eElement.getElementsByTagName("beds").item(0).getTextContent())) {
                                    eElement.getElementsByTagName("guests").item(0).setTextContent(String.valueOf(numberOfGuests));//casting int to string
                                } else {
                                    throw new Exception("Not enough beds in the room!");
                                }
                            }
                        }
                    }
                }
                report.addReport(checkInDate,checkOutDate,roomNumber);
                transform.transformOriginalFile(doc,"temp.xml");
            } catch (Exception e) {
                System.out.println("Please check parameters of command!");
            }
        }else{
            System.out.println("Please open file!");
        }
    }
    public void checkOut(int roomNumber,File openedFile) {
        if(openedFile != null){
            File xmlFile = new File("temp.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(xmlFile);
                doc.getDocumentElement().normalize();
                Element root = doc.getDocumentElement();
                NodeList childNodes = root.getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node node = childNodes.item(i);

                    if (node.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) node;
                        if(Integer.parseInt(eElement.getAttribute("number")) == roomNumber) {
                            if(eElement.getElementsByTagName("free").item(0).getTextContent().equals("yes")){
                                System.out.println("Room number " + roomNumber + " is already available!");
                            }
                            else{
                                eElement.getElementsByTagName("free").item(0).setTextContent("yes");
                                eElement.getElementsByTagName("checkOutDate").item(0).setTextContent("NULL");
                                eElement.getElementsByTagName("checkInDate").item(0).setTextContent("NULL");
                                eElement.getElementsByTagName("guests").item(0).setTextContent("0");
                                eElement.getElementsByTagName("note").item(0).setTextContent("NULL");

                            }
                        }
                    }
                }
                transform.transformOriginalFile(doc,"temp.xml");
            } catch (Exception e) {
                System.out.println("Please check parameters of command!");
            }
        }else {
            System.out.println("Please open file!");
        }
    }



}
