import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class XMLParser {

  private static final String userFilePath = "Resources/users.xml";

  private final Map<String, String> userPasswords;
  private final Map<String, Double> userBalances;

  public XMLParser() {
    userPasswords = new HashMap<>();
    userBalances = new HashMap<>();
    parseUsers();
  }

  private void parseUsers() {
    try {
      File xmlFile = new File(userFilePath);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(xmlFile);

      doc.getDocumentElement().normalize();
      NodeList nList = doc.getElementsByTagName("user");

      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node nNode = nList.item(temp);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Element eElement = (Element) nNode;
          String username = eElement.getElementsByTagName("username").item(0).getTextContent();
          String password = eElement.getElementsByTagName("password").item(0).getTextContent();
          double walletBalance = Double.parseDouble(eElement.getElementsByTagName("walletBalance").item(0).getTextContent());

          userPasswords.put(username, password);
          userBalances.put(username, walletBalance);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

    public void newUser(String username, String password, double walletBalance) {
      try {
        File xmlFile = new File(userFilePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        doc.getDocumentElement().normalize();

        Node users = doc.getFirstChild();

        Element newUser = doc.createElement("user");

        Element newUsername = doc.createElement("username");
        newUsername.appendChild(doc.createTextNode(username));
        newUser.appendChild(newUsername);

        Element newPassword = doc.createElement("password");
        newPassword.appendChild(doc.createTextNode(password));
        newUser.appendChild(newPassword);

        Element newWalletBalance = doc.createElement("walletBalance");
        newWalletBalance.appendChild(doc.createTextNode(String.valueOf(walletBalance)));
        newUser.appendChild(newWalletBalance);

        users.appendChild(newUser);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(source, result);

        userPasswords.put(username, password);
        userBalances.put(username, walletBalance);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    public boolean validPassword(String username, String password) {
      return userPasswords.containsKey(username) && userPasswords.get(username).equals(password);
    }

    public boolean existingUsername(String username) {
      return userPasswords.containsKey(username);
    }
    public double balanceGivenValidPassword(String username, String password) {
      if (validPassword(username, password)) {
          return userBalances.get(username);
      }
      else {
        return 0.0;
      }
    }
  }
