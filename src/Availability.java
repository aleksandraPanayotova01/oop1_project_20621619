import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.time.LocalDate;

public class Availability {
    public void checkAvailability(LocalDate dateOfAvailability,File openedFile) {
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
                        String cElement = eElement.getElementsByTagName("free").item(0).getTextContent();
                        if (cElement.equals("no")){
                            String date= eElement.getElementsByTagName("checkOutDate").item(0).getTextContent();
                            LocalDate checkOutDate = LocalDate.parse(date);
                            if( checkOutDate.isBefore(dateOfAvailability)){
                                System.out.println(eElement.getAttribute("number"));
                            }
                        }
                        else{
                            System.out.println(eElement.getAttribute("number"));
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println("Please open file!");
        }
    }
}
