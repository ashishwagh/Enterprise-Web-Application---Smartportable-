
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ViewSearchProduct extends HttpServlet {
	HttpSession session;
	public PrintWriter out;
	Utilities utility;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		Saxpaser handler = new Saxpaser("C:\\apache-tomcat-7.0.34\\webapps\\SmartPortables_4\\WEB-INF\\classes\\ProductCatalog.xml");
		HashMap<String, Product> hm3 = handler.getProducts();
		String usertype =(String) session.getAttribute("usertype");
		Product searchProduct = (Product) request.getAttribute("productSearched");
		List<Accesory> accessories = new ArrayList<Accesory>();
		for (Map.Entry<String, Product> entry : hm3.entrySet()) {
				Product product = (Product) entry.getValue();
				if(product.getName().equals(searchProduct.getName())) {
					accessories = product.getAccesory();
				}
				break;
			}
		
		Utilities utility = new Utilities(request, out);
		if (utility.isLoggedin())
			utility.printHtml("HeaderLogout.html");
		else
			utility.printHtml("Header.html");
		out.println("<section id=\"content\">");
		
		out.println("<article>");
		out.println("<a target=\"_blank\" href=\"#\">");
		out.println("<img src=\"Html\\images\\" + searchProduct.getImage()
				+ "\" alt=\"Trolltunga Norway\" width=\"300\" height=\"200\">");
		out.println("</a>");

		out.println("<ul>");
		out.println("<li>Name:" + searchProduct.getName() + "</li>");
		out.println("<li>Price: $" + searchProduct.getPrice() + "</li>");
		out.println("<li>Condition:" + searchProduct.getCondition() + "</li>");
		out.println("<li>Manufacturer:" + searchProduct.getManufacturer() + "</li>");

		if(usertype==null || usertype.equals("Customer")) {
			out.println("<button class=\"lbutton\"><a href=\"AddCart?id=" + searchProduct.getId() + "&&name="
					+ searchProduct.getName() + "&&price=" + searchProduct.getPrice() + "&&image=" + searchProduct.getImage()
					+ "\" class=\"btnreview\" style=\"color:white\">Add To Cart</a></button>");
			
			out.println("<button class=\"lbutton\"><a href=\"WriteReview?id=" + searchProduct.getId() + "&&name="
					+ searchProduct.getName() + "&&price=" + searchProduct.getPrice() + "&&image=" + searchProduct.getImage()+ "&&catogory=" + searchProduct.getCategory()+
					"&&manufacturer=" + searchProduct.getManufacturer()
					+ "\" class=\"btnreview\" style=\"color:white\">Write Review</a></button>");
			
			out.println("<button class=\"lbutton\"><a href=\"ViewReview?id=" + searchProduct.getId() + "&&name="
					+ searchProduct.getName() + "&&price=" + searchProduct.getPrice() + "&&image=" + searchProduct.getImage()+ "&&catogory=" + searchProduct.getCategory()+
					"&&manufacturer=" + searchProduct.getManufacturer()
					+ "\" class=\"btnreview\" style=\"color:white\">Read Review</a></button>");
		}else if(usertype.equals("Manager")) {
			out.println("<button class=\"lbutton\"><a href=\"UpdateProduct?id=" + searchProduct.getId() +  "&&name="
					+ searchProduct.getName() + "&&category=" + searchProduct.getCategory()
			+ "\" class=\"btnreview\" style=\"color:white\">Update Product</a></button>");
			out.println("<button class=\"lbutton\"><a href=\"DeleteProduct?id=" + searchProduct.getId() + "&&name="
					+ searchProduct.getName() +"&&category=" + searchProduct.getCategory()
			+ "\" class=\"btnreview\" style=\"color:white\">Delete Product</a></button>");
		}
		
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
		out.println("</section>");
		utility.printHtml("LeftNav.html");
		utility.printHtml("Footer.html");

	}
}
