
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Saxpaser extends DefaultHandler {
	Product product;
	List<Accesory> accessories;
	Accesory accessory;
	String consoleXmlFileName;
	String elementValueRead;
	int a;
	static HashMap<String, Product> hm;

	public Saxpaser() {
	}

	public Saxpaser(String consoleXmlFileName) {
		a = 1;
		hm = new HashMap<String, Product>();
		this.consoleXmlFileName = consoleXmlFileName;
		parseDocument();
	}

	private void parseDocument() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(consoleXmlFileName, this);
		} catch (ParserConfigurationException e) {
			System.out.println("ParserConfig error");
		} catch (SAXException e) {
			System.out.println("SAXException : xml not well formed");
		} catch (IOException e) {
			System.out.println("IO error");
		}
	}

	public HashMap getProducts() {
		return hm;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if (qName.equalsIgnoreCase("Product")) {
			product = new Product();
			product.setId(Integer.parseInt(attributes.getValue("id")));
			product.setCategory(attributes.getValue("category"));
			
		}
		if (qName.equalsIgnoreCase("Accesories")) {
			accessories = new ArrayList<Accesory>();
		}
		if (qName.equalsIgnoreCase("Accesory")) {
			accessory = new Accesory();
		}

	}

	public void endElement(String uri, String localName, String element) throws SAXException {

		if (element.equals("Product")) {
			hm.put(product.getId()+"", product);
			a++;
			return;
		}

		if (element.equalsIgnoreCase("name")) {
			product.setName(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("manufacturer")) {
			product.setManufacturer(elementValueRead);
			return;
		}
		if (element.equalsIgnoreCase("condition")) {
			product.setCondition(elementValueRead);
			return;
		}
		if (element.equalsIgnoreCase("onsale")) {
            product.setOnsale(elementValueRead);
	    return;
        }
		 if (element.equalsIgnoreCase("rebate")) {
            product.setRebate(elementValueRead);
	    return;
        }
		if (element.equalsIgnoreCase("image")) {
			product.setImage(elementValueRead);
			return;
        }if(element.equalsIgnoreCase("quantity")){
           product.setQuantity(Integer.parseInt(elementValueRead));
	    return;
        }
		if (element.equals("Accesories")) {
			product.setAccesory(accessories);

			return;
		}
		if (element.equals("Accesory")) {
			accessories.add(accessory);
			return;
		}
		if (element.equalsIgnoreCase("Aname")) {
			accessory.setAName(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("Aimage")) {
			accessory.setAImage(elementValueRead);
			return;
		}
		if (element.equalsIgnoreCase("price")) {
			product.setPrice(Integer.parseInt(elementValueRead));
			return;
		}

	}

	public void characters(char content[], int begin, int end) throws SAXException {
		elementValueRead = new String(content, begin, end);

	}

	public static void addHashmap() {
		new Saxpaser("C:\\apache-tomcat-7.0.34\\webapps\\SmartPortables_4\\WEB-INF\\classes\\ProductCatalog.xml");
		System.out.println("inside addHashmap"+hm.size());
    } 
}
