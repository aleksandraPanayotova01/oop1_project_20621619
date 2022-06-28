import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

public class Report {
    private final Transform transform = new Transform();

    public void addReport(LocalDate checkInDate, LocalDate checkOutDate, int roomNumber) {
        try {
            File xmlFile = new File("report.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            Element documentElement = document.getDocumentElement();
            Element textNode = document.createElement("checkInDate");
            textNode.setTextContent(checkInDate.toString());
            Element textNode1 = document.createElement("checkOutDate");
            textNode1.setTextContent(checkOutDate.toString());
            Element nodeElement = document.createElement("room");
            Attr attr = document.createAttribute("number");
            attr.setValue(String.valueOf(roomNumber));
            nodeElement.setAttributeNode(attr);
            nodeElement.appendChild(textNode);
            nodeElement.appendChild(textNode1);
            documentElement.appendChild(nodeElement);
            document.replaceChild(documentElement, documentElement);
            transform.transformOriginalFile(document, "report.xml");
        }  catch (Exception e) {
            System.out.println("Please check parameters of command!");
        }
    }

    public void showReport(LocalDate checkInDate, LocalDate checkOutDate) {
        try {
            File inputFile = new File("report.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println(doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("room");
            System.out.println("---------------------------------");
            Map<Integer,Integer> roomDaysTaken=new HashMap<>();
            int totalDaysTaken;
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    LocalDate reportCheckIn = LocalDate.parse(eElement.getElementsByTagName("checkInDate").item(0).getTextContent());
                    LocalDate reportCheckOut = LocalDate.parse(eElement.getElementsByTagName("checkOutDate").item(0).getTextContent());
                    if ((checkInDate.isBefore(reportCheckIn) || checkInDate.isEqual(reportCheckIn))
                            && ((checkOutDate.isAfter(reportCheckOut) || checkOutDate.isEqual(reportCheckOut)))) {
                        if(roomDaysTaken.get(Integer.parseInt(eElement.getAttribute("number")))!=null
                                && roomDaysTaken.get(Integer.parseInt(eElement.getAttribute("number")))!=0) {
                            totalDaysTaken = roomDaysTaken.get(Integer.parseInt(eElement.getAttribute("number")))
                                    + Integer.parseInt(String.valueOf(DAYS.between(reportCheckIn, reportCheckOut)+1));
                        }
                        else {
                            totalDaysTaken = Integer.parseInt(String.valueOf(DAYS.between(reportCheckIn, reportCheckOut)+1));
                        }
                        roomDaysTaken.put(Integer.parseInt(eElement.getAttribute("number")), totalDaysTaken);




                    }
                }
            }
            System.out.println("```````````````````````````````````````````````````````````````");
            System.out.println("For the period between "+ checkInDate+ ": "+checkOutDate);
            for (Map.Entry<Integer, Integer> entry : roomDaysTaken.entrySet()) {
                System.out.println("Room number "+entry.getKey() + " has been taken for :" + entry.getValue() + " days");
                System.out.println("---------------------------------");
            }
        }  catch (Exception e) {
            System.out.println("Please check parameters of command!");
        }
    }
}


