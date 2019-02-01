import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateItem extends HttpServlet {
	public PrintWriter out;
	Utilities utility;
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	out = response.getWriter();
	Saxpaser handler = new Saxpaser("C:\\apache-tomcat-7.0.34\\webapps\\SmartPortables_3\\WEB-INF\\classes\\ProductCatalog.xml");
	HashMap<String, Product> hm2 = handler.getProducts();

	String id = request.getParameter("id");
	String category = request.getParameter("category");
	utility = new Utilities(request, out);
	if(utility.isLoggedin())
		utility.printHtml("HeaderLogout.html");
	else
		utility.printHtml("Header.html");
	
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
			if (product.getId().equals(id) && product.getCategory().equals(category)) {
				out.println("<a  href=\"#\">");
				out.println("<img src=\"Html\\images\\" + product.getImage()
						+ "\" alt=\"Trolltunga Norway\" width=\"300\" height=\"200\">");
				out.println("</a>");
				out.println("<ul>");
				
				out.println("<li class=\"desc\"> Product Name: "
						+ "<input type=\"text\" class=\"uname\" name=\"prodName\" required=\"true\" style=\"margin-left:48px !important\" value=\""+product.getName()+"\">" +"</input></li>");
				out.println("<li>Price: $" +"<input type=\"text\" class=\"uname\" name=\"prodPrice\" required=\"true\" style=\"margin-left:48px !important\" value=\""+ product.getPrice()+"\">" +"</input></li>");
				out.println("<button class=\"lbutton\"><a href=\"SaveItem?id=" + product.getId() + "&&category=" + product.getCategory()
						+ "\" class=\"btnreview\" style=\"color:white\">Update Item</a></button>");
				out.println("</ul>");
			}
	}
	
	out.println("</tr>");
	out.println("</tbody>");
	out.println("</table>");
	out.println("</article>");
	out.println("</section>");

	utility.printHtml("LeftNav.html");
	utility.printHtml("Footer.html");
	}
}
