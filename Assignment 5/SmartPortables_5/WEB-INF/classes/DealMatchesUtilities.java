import java.io.IOException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DealMatchesUtilities")

public class DealMatchesUtilities extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

			HashMap<String,Product> selectedproducts=new HashMap<String,Product>();
		try
			{
				
		pw.print("<section id=\"content\">");
		pw.print("<h3>We beat our competitors in all aspects. Price-Match Guaranteed</h3>");
		
			String line=null;
			String TOMCAT_HOME = System.getProperty("catalina.home");

			HashMap<String,Product> productmap=MySQLDataStoreUtilities.getProducts();
			
			for(Map.Entry<String, Product> entry : productmap.entrySet())
			{
				
			if(selectedproducts.size()<2 && !selectedproducts.containsKey(entry.getKey()))
			{		
				
				
			BufferedReader reader = new BufferedReader(new FileReader (new File("C:\\apache-tomcat-7.0.34\\webapps\\SmartPortables_5\\DealMatches.txt")));
			line=reader.readLine();

			if(line==null)
			{
				pw.print("<h4 align='center'>No Offers Found</h4>");
				break;
			}	
			else
			{	
			do {	
				Product product = (Product) entry.getValue();
				  if(line.contains(product.getName()))
				  {
				 
					pw.print("<h4>"+line+"</h4>");
					pw.print("<br>");
					selectedproducts.put(entry.getKey(),entry.getValue());
					break;
				  }
				  
			    }while((line = reader.readLine()) != null);
			
			 }
			 }
			}
			}
			catch(Exception e)
			{
			pw.print("<h4 align='center'>No Offers Found</h4>");
			}
		pw.print("<div><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Deal Matches</a>");
		pw.print("</h2><div class='entry'>");
		if(selectedproducts.size()==0)
		{
		pw.print("<h4 align='center'>No Deals Found</h4>");	
		}
		else
		{
		pw.print("<article>");
		pw.print("<table cellspacing=\"0\" class=\"shopping-cart\">");
		pw.print("<thead>");
		pw.print("<tr class=\"headings\">");
		pw.print("<th class=\"link\">&nbsp;</th>");
		pw.print("<th class=\"link\">&nbsp;</th>   ");
		pw.print("</tr>");
		pw.print("</thead>");
		pw.print("<tbody>");
		pw.print("<tr>");
		for(Map.Entry<String, Product> entry : selectedproducts.entrySet()){
			Product product = (Product) entry.getValue();	
			pw.print("<a  href=\"#\">");
			pw.print("<img src=\"Html\\images\\" + product.getImage()
					+ "\" alt=\"Trolltunga Norway\" width=\"300\" height=\"200\">");
			pw.print("</a>");
			pw.print("<ul>");
			pw.print("<li class=\"desc\">Name : " + product.getName() + "</li>");
			pw.print("<li>Price: $" + product.getPrice() + "</li>");
			pw.print("<button class=\"lbutton\"><a href=\"AddCart?id=" + product.getId() + "&&name="
					+ product.getName() + "&&price=" + product.getPrice() + "&&image=" + product.getImage()
					+ "\" class=\"btnreview\" style=\"color:white\">Add To Cart</a></button>");
			
			pw.print("<button class=\"lbutton\"><a href=\"WriteReview?id=" + product.getId() + "&&name="
					+ product.getName() + "&&price=" + product.getPrice() + "&&image=" + product.getImage()+ "&&catogory=" + product.getCategory()+
					"&&manufacturer=" + product.getManufacturer()
					+ "\" class=\"btnreview\" style=\"color:white\">Write Review</a></button>");
			
			pw.print("<button class=\"lbutton\"><a href=\"ViewReview?id=" + product.getId() + "&&name="
					+ product.getName() + "&&price=" + product.getPrice() + "&&image=" + product.getImage()+ "&&catogory=" + product.getCategory()+
					"&&manufacturer=" + product.getManufacturer()
					+ "\" class=\"btnreview\" style=\"color:white\">Read Review</a></button>");
			pw.print("</ul>");
		}
		pw.print("</tr>");
		pw.print("</tbody>");
		pw.print("</table>");
		pw.print("</article>");
		}
		pw.print("</div></div></div>");
		pw.print("</section>");
		
	}
}
