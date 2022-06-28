import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class FindRooms {
    void findBeds(int beds, String availableFrom, String availableUntil,File openedFile) {
        if (openedFile != null) {

            File xmlFile = new File("hotel.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Map<Integer, Integer> availableRooms = new HashMap<>();
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
                        int roomNumber = Integer.parseInt(eElement.getAttribute("number"));
                        int numBeds = Integer.parseInt(eElement.getElementsByTagName("beds").item(0).getTextContent());

                        if (eElement.getElementsByTagName("checkInDate").item(0).getTextContent().equals("NULL")) {//check if the room is currently free
                            if (numBeds >= beds) {//check if the beds in the current room are enough
                                availableRooms.put(roomNumber, numBeds);
                            }
                        } else {
//                        LocalDate checkIn = LocalDate.parse(eElement.getElementsByTagName("checkInDate").item(0).getTextContent());
                            LocalDate checkOut = LocalDate.parse(eElement.getElementsByTagName("checkOutDate").item(0).getTextContent());
                            LocalDate today = LocalDate.now();
                            if ((LocalDate.parse(availableFrom).isAfter(checkOut)) &&
                                    (LocalDate.parse(availableFrom).isEqual(today) || LocalDate.parse(availableFrom).isAfter(today))) //check if the given dates are both after today and check out
                            {

                                if ((LocalDate.parse(availableFrom).isAfter(checkOut)) && numBeds >= beds) {
                                    availableRooms.put(roomNumber, numBeds);
                                }
                            }
                        }
                    }

                }
                if (!availableRooms.isEmpty()) {
                    System.out.println("From top to bottom the most preferable available options for rooms at the moment are:");
                    Map<Integer, Integer> result = availableRooms.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                    result.forEach((k, v) -> System.out.println(k + "=" + v));
                } else {
                    System.out.println("Sorry! There are no available rooms with enough beds at the moment!");
                }
            }  catch (Exception e) {
                System.out.println("Please check parameters of command!");
            }
        }else {
            System.out.println("Please open file!");
        }
    }
}
