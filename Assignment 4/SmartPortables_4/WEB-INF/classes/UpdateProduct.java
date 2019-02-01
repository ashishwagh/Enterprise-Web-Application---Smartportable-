import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateProduct extends HttpServlet {
	public PrintWriter out;
	Utilities utility;
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	out = response.getWriter();
	/*Saxpaser handler = new Saxpaser("C:\\apache-tomcat-7.0.34\\webapps\\SmartPortables_4\\WEB-INF\\classes\\ProductCatalog.xml");
	HashMap<String, Product> hm2 = handler.getProducts();*/
	HashMap<String, Product> hm2 = MySQLDataStoreUtilities.getProducts();

	String id = request.getParameter("id");
	String name = request.getParameter("name");
	String category = request.getParameter("category");
	Boolean prodFound=false;
	utility = new Utilities(request, out);
	if(utility.isLoggedin())
		utility.printHtml("HeaderLogout.html");
	else
		utility.printHtml("Header.html");
	
	out.println("<div id=\"body\">");
	out.println("<section id=\"content\">");
	
	for (Map.Entry<String, Product> entry : hm2.entrySet()) {
		Product product = (Product) entry.getValue();
			if (product.getId()==Integer.parseInt(id)) {
				prodFound=true;
				out.print("<form name ='SaveUpdatedProduct' action='SaveUpdatedProduct' method='get'>");
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
				out.println("<ul>");
				
				out.println("<li class=\"desc\"> Product Name: "
						+ "<input type=\"text\" class=\"uname\" name=\"prodName\" required=\"true\" style=\"margin-left:48px !important\" value=\""+product.getName()+"\">" +"</input></li>");
				out.print("<input type='hidden' name='id' value='" + id + "'>");
				out.print("<input type='hidden' name='category' value='" + category + "'>");
				out.println("<li class=\"desc\"> Product Condition: "
						+ "<input type=\"text\" class=\"uname\" name=\"prodCondition\" required=\"true\" style=\"margin-left:48px !important\" value=\""+product.getCondition()+"\">" +"</input></li>");
				
				out.println("<li class=\"desc\"> Product Image: "
						+ "<input type=\"text\" class=\"uname\" name=\"prodImage\" required=\"true\" style=\"margin-left:48px !important\" value=\""+product.getImage()+"\">" +"</input></li>");
				
				
				out.println("<li>Price: $" +"<input type=\"text\" class=\"uname\" name=\"prodPrice\" required=\"true\" style=\"margin-left:48px !important\" value=\""+ product.getPrice()+"\">" +"</input></li>");
				
				out.println("<li class=\"desc\"> Product Manufacturer: "
						+ "<input type=\"text\" class=\"uname\" name=\"prodmanufacturer\" required=\"true\" style=\"margin-left:48px !important\" value=\""+product.getManufacturer()+"\">" +"</input></li>");
				
				out.println("<li class=\"desc\"> Product Quantity: "
						+ "<input type=\"text\" class=\"uname\" name=\"prodQuantity\" required=\"true\" style=\"margin-left:48px !important\" value=\""+product.getQuantity()+"\">" +"</input></li>");
				
				out.println("<li class=\"desc\"> Product Onsale: "
						+ "<input type=\"text\" class=\"uname\" name=\"prodOnsale\" required=\"true\" style=\"margin-left:48px !important\" value=\""+product.getOnsale()+"\">" +"</input></li>");
				
				out.println("<li class=\"desc\"> Product Rebate: "
						+ "<input type=\"text\" class=\"uname\" name=\"prodRebate\" required=\"true\" style=\"margin-left:48px !important\" value=\""+product.getRebate()+"\">" +"</input></li>");
				
				out.println("<input type='submit' class='lbutton' name='SaveUpdatedProduct' value='UpdateProduct'>");
				out.println("</ul>");
				out.println("</tr>");
				out.println("</tbody>");
				out.println("</table>");
				out.println("</article>");
				out.print("</form>");
			}
	}
	
	if(prodFound==false)
		out.print("<h2 style='color:red'>No product found with the id "+id+" .</h2>");
	out.println("</section>");

	utility.printHtml("LeftNav.html");
	utility.printHtml("Footer.html");
	}
}
