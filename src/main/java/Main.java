import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        String fileName = "data.xml";
        List<Employee> list = parseXML(fileName);
        String json = listToJson(list);
        writeString(json);
    }

    public static List<Employee> parseXML(String fileName) {
        List<Employee> staff = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(fileName));
            Node root = doc.getDocumentElement();
            NodeList nodeList = root.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (Node.ELEMENT_NODE == node.getNodeType()) {
                    Element element = (Element) node;
                    // получение id
                    NodeList node1 = element.getElementsByTagName("id");
                    String id = node1.item(0).getTextContent();
                    // получение firstName
                    NodeList node2 = element.getElementsByTagName("firstName");
                    String firstName = node2.item(0).getTextContent();
                    //получение lastName
                    NodeList node3 = element.getElementsByTagName("lastName");
                    String lastName = node3.item(0).getTextContent();
                    // получение country
                    NodeList node4 = element.getElementsByTagName("country");
                    String country = node4.item(0).getTextContent();
                    //получение age
                    NodeList node5 = element.getElementsByTagName("age");
                    String age = node5.item(0).getTextContent();
                    //Добавление сотрудника в список
                    staff.add(new Employee(Long.parseLong(id), firstName, lastName, country, Integer.parseInt(age)));
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return staff;
    }

    public static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        String json = gson.toJson(list, listType);
        return json;
    }

    public static void writeString(String string) {
        try (FileWriter file = new FileWriter("data.json")) {
            file.write(string);
            file.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}