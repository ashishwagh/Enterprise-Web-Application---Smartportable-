import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class ViewItem extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String username = request.getParameter("username");

		Saxpaser handler = new Saxpaser("C:\\apache-tomcat-7.0.34\\webapps\\SmartPortables_5\\WEB-INF\\classes\\ProductCatalog.xml");
		HashMap<String, Product> hm3 = handler.getProducts();

		int id = Integer.parseInt(request.getParameter("id"));
		String category = request.getParameter("category");

		Utilities utility = new Utilities(request, out);
		if (utility.isLoggedin())
			utility.printHtml("HeaderLogout.html");
		else
			utility.printHtml("Header.html");
		out.println("<section id=\"content\">");
		for (Map.Entry<String, Product> entry : hm3.entrySet()) {
			Product product = (Product) entry.getValue();
			List<Accesory> accessories = product.getAccesory();

			if (product.getId()==id && product.getCategory().equals(category)) {
				out.println("<article>");
				out.println("<a target=\"_blank\" href=\"#\">");
				out.println("<img src=\"Html\\images\\" + product.getImage()
						+ "\" alt=\"Trolltunga Norway\" width=\"300\" height=\"200\">");
				out.println("</a>");

				out.println("<ul>");
				out.println("<li>Price: $" + product.getPrice() + "</li>");
				out.println("<li>Condition:" + product.getCondition() + "</li>");

				out.println("<button class=\"lbutton\"><a href=\"AddCart?id=" + product.getId() + "&&name="
						+ product.getName() + "&&price=" + product.getPrice() + "&&image=" + product.getImage()
						+ "\" class=\"btnreview\" style=\"color:white\">Add To Cart</a></button>");
				
				out.println("<button class=\"lbutton\"><a href=\"WriteReview?id=" + product.getId() + "&&name="
						+ product.getName() + "&&price=" + product.getPrice() + "&&image=" + product.getImage()+ "&&catogory=" + product.getCategory()+
						"&&manufacturer=" + product.getManufacturer()
						+ "\" class=\"btnreview\" style=\"color:white\">Write Review</a></button>");
				
				out.println("<button class=\"lbutton\"><a href=\"ViewReview?id=" + product.getId() + "&&name="
						+ product.getName() + "&&price=" + product.getPrice() + "&&image=" + product.getImage()+ "&&catogory=" + product.getCategory()+
						"&&manufacturer=" + product.getManufacturer()
						+ "\" class=\"btnreview\" style=\"color:white\">Read Review</a></button>");
				
				out.println("</ul>");

				out.println("</article>");

				out.println("<div id=\"myCarousel\" class=\"carousel slide\" data-ride=\"carousel\">");
				out.println("<div id=\"myCarousel\" class=\"carousel slide\" data-ride=\"carousel\">");

				out.println("<ol class=\"carousel-indicators\"> ");
				out.println("<li data-target=\"#myCarousel\" data-slide-to=\"0\" class=\"active\"></li>  ");
				out.println("<li data-target=\"#myCarousel\" data-slide-to=\"1\"></li>  ");
				out.println("<li data-target=\"#myCarousel\" data-slide-to=\"2\"></li>  ");
				out.println("</ol>  ");
				out.println("<div class=\"carousel-inner\" role=\"listbox\">  ");

				out.println("<div class=\"item active\">  ");
				out.println("<img src=\"Html\\images\\accessories\\s9a1.jpg\" alt=\"jokes 2\" >  ");
				out.println("<h3> You can also buy this accessory: Smart Power Switch</h3>");
				out.println("</div> ");
				for (Accesory accesory : accessories) {
					out.println("<div class=\"item\">  ");
					out.println("<img src=\"Html\\images\\accessories\\" + accesory.getAImage() + "\" alt=\"jokes 2\" >  ");
					out.println("<h3> You can also buy this accessory: " + accesory.getAName() + "</h3>");
					out.println("</div> ");

				}
				out.println(
						"<a class=\"left carousel-control\" href=\"#myCarousel\" role=\"button\" data-slide=\"prev\">  ");
				out.println("<span class=\"glyphicon glyphicon-chevron-left\" aria-hidden=\"true\"></span>  ");
				out.println("<span class=\"sr-only\">Previous</span>  ");
				out.println("</a>  ");
				out.println(
						"<a class=\"right carousel-control\" href=\"#myCarousel\" role=\"button\" data-slide=\"next\">  ");
				out.println("<span class=\"glyphicon glyphicon-chevron-right\" aria-hidden=\"true\"></span>  ");
				out.println("<span class=\"sr-only\">Next</span>  ");
				out.println("</a> ");
				out.println("</div>");
				out.println("</div>");
				out.println("</div>");
			}

		}
		out.println("</section>");
		utility.printHtml("LeftNav.html");
		utility.printHtml("Footer.html");
	}

}