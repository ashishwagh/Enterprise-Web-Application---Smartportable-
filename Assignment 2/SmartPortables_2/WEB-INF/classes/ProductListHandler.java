import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProductListHandler extends HttpServlet {

	public PrintWriter out;
	Utilities utility;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		Saxpaser handler = new Saxpaser("C:\\apache-tomcat-7.0.34\\webapps\\SmartPortables\\WEB-INF\\classes\\ProductCatalog.xml");
		HashMap<String, Product> hm2 = handler.getProducts();

		String makers = request.getParameter("category");
		String manufacturer = request.getParameter("maker");
		HttpSession session = request.getSession();
		String usertype = (String) session.getAttribute("usertype");
		utility = new Utilities(request, out);

		if (utility.isLoggedin())
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
		System.out.println("ProductListHandler usertype::::"+usertype);
		for (Map.Entry<String, Product> entry : hm2.entrySet()) {
			Product product = (Product) entry.getValue();
			if (product.getCategory().equals(makers) && product.getManufacturer().equals(manufacturer)) {
				out.println("<a  href=\"#\">");
				out.println("<img src=\"Html\\images\\" + product.getImage()
						+ "\" alt=\"Trolltunga Norway\" width=\"300\" height=\"200\">");
				out.println("</a>");
				out.println("<ul>");
				out.println("<li class=\"desc\">Name : " + product.getName() + "</li>");
				out.println("<li>Price: $" + product.getPrice() + "</li>");
				if(usertype !=null && usertype.equals("Manager")) {
					out.println("<button class=\"lbutton\"><a href=\"UpdateItem?id=" + product.getId() + "&&category=" + product.getCategory()
					+ "\" class=\"btnreview\" style=\"color:white\">Update Item</a></button>");
				}else {
				
				out.println("<button class=\"lbutton\"><a href=\"ViewItem?id=" + product.getId() + "&&category=" + product.getCategory()
						+ "\" class=\"btnreview\" style=\"color:white\">View Item</a></button>");
				}
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
