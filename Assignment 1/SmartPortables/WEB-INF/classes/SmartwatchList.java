import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
public class SmartwatchList extends HttpServlet {


	public PrintWriter out;
	Utilities utility;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		Saxpaser handler = new Saxpaser("C:\\apache-tomcat-7.0.34\\webapps\\SmartPortables\\WEB-INF\\classes\\ProductCatalog.xml");
		HashMap<String, Product> hm2 = handler.getProducts();

		/*
		 * for (Map.Entry<String,Product> entry : hm2.entrySet()) {
		 * 
		 * Product product=(Product)entry.getValue();
		 * if(product.getCategory.equals("SmartWatches")){
		 * System.out.println(product.getName());
		 * System.out.println(product.getCondition());
		 * System.out.println(product.getImage());
		 * System.out.println(accessory.getAName());
		 * System.out.println(product.getName()); }
		 * System.out.println(entry.getKey()+" : "+entry.getValue()); }
		 */

		String makers = request.getParameter("category");
		// out.println(makers);
		String manufacturer = request.getParameter("maker");
		utility = new Utilities(request, out);

		utility.printHtml("HeaderLogout.html");

		out.println("<div id=\"body\">");
		out.println("<section id=\"content\">");
		out.println("<article>");
		out.println("<table cellspacing=\"0\" class=\"shopping-cart\">");
		out.println("<thead>");
		out.println("<tr class=\"headings\">");
		out.println("<th class=\"link\">&nbsp;</th>");
		out.println("<th class=\"link\">&nbsp;</th>   ");
		out.println("</tr>");
		out.println("</thead>");
		out.println("<tbody>");
		out.println("<tr>");
		for (Map.Entry<String, Product> entry : hm2.entrySet()) {
			Product product = (Product) entry.getValue();
			// out.println("name " + product.getName());

			if (product.getCategory().equals(makers) && product.getManufacturer().equals(manufacturer)) {

				// out.println("<div id=\"body\">");

				out.println("<a  href=\"#\">");

				out.println("<img src=\"Html\\images\\" + product.getImage()
						+ "\" alt=\"Trolltunga Norway\" width=\"300\" height=\"200\">");
				out.println("</a>");

				out.println("<ul>");
				out.println("<li class=\"desc\">Name : " + product.getName() + "</li>");
				out.println("<li>Price: $" + product.getPrice() + "</li>");
				// out.println("<li><a href=""BuyItem?name=" + item.getName() + "&&price=" +
				// item.getPrice()" class = \"btnreview\">Buy Now</a></li>");

				out.println("<li><a href=\"ViewItem?id=" + product.getId() + "&&category=" + product.getCategory()
						+ "\" class=\"btnreview\">View Item</a></li>");
				// out.println("<li><a href=\"AddCart\" class=\"btnreview\">Add to
				// Cart</a></li>");
				out.println("</ul>");

			}
		}

		out.println("</tr>");
		out.println("</tbody>");
		out.println("</table>");

		out.println("</article>");
		// out.println("</div>");
		out.println("</section>");
		// out.println("<div class=\"clear\"></div>");

		utility.printHtml("Sidebar.html");
		utility.printHtml("Footer.html");

	}


}
