import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;


public class SalesBarChart extends HttpServlet {
	 /*MySqlDataStoreUtilities ms = new MySqlDataStoreUtilities();
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
PrintWriter out= response.getWriter();

String username = request.getParameter("username");
// Product product = new Product();
 HttpSession session=request.getSession();  
//ArrayList<Product> products;

Users user = (Users)session.getAttribute("user");
//String usert = user.usertype;

Utilities utility = new Utilities(request,out);

if(session.getAttribute("user")!= null )  
{
	//
//String n=(String)session.getAttribute("uname");  
	
	//users= (Users)session.getAttribute("user");
	
//products=ms.checkInventory();
utility.printHtml("HeaderAdd.html");
 out.println("<section id=\"content\">");
 out.println("<h2 align=\"center\" margin-top=\"60px\">Sales BarChart</h2>");
		
	  out.println("<table cellspacing=\"0\" class=\"shopping-cart\"  style=\"Height:50px\"> ");
                out.println("<thead>");
                out.println("<tr class=\"headings\">");
                
				out.println("<th class=\"\">No.</td>");
                out.println("<th class=\"\">Product Name</td>");
               out.println("<th class=\"\">Price</td>");
               out.println("<th class=\"\">Quantity</td>");
               
               
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody>");
			
			   int i= 1;
			    for(Product product : products)
			 {
				//Product product=(Product)entry.getValue();
				
				
						out.println("<tr>"); 
						
						out.println("<td class=\"productName\">");
						out.println(product.getName());
						out.println("</td>");
						 out.println("<td class=\"productName\">");
						out.println(product.getQuantity());
						out.println("</td>");
						out.println("</tr>");
				
			 }
				 out.println("</tbody>");
                out.println("</table>");
				
				out.println("<div id=\"piechart_sales\" ></div>" );
                out.println("</section>");


utility.printHtml("side_sales.html");

utility.printHtml("Footer.html");
}
 

else
{
	pw.println("Hello else");
	
utility.printHtml("Header.html");
   
utility.printHtml("product.html");
utility.printHtml("Sidebar.html");

utility.printHtml("Footer.html");
}

	
	  }*/
	
	MySQLDataStoreUtilities ms = new MySQLDataStoreUtilities();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String username = request.getParameter("username");
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user");
		Utilities utility = new Utilities(request, out);

		if (session.getAttribute("user") != null) {
			utility.printHtml("HeaderLogout.html");
			out.println("<section id=\"content\">");
			out.println("<h2 align=\"center\" margin-top=\"60px\">Sales BarChart</h2>");
			out.println("<div id=\"piechart_sales\" ></div>");
			out.println("</section>");
			utility.printHtml("LeftNav.html");
			utility.printHtml("Footer.html");
		} else {
			utility.printHtml("Header.html");
			utility.printHtml("Product.html");
			utility.printHtml("LeftNav.html");
			utility.printHtml("Footer.html");
		}

	}
}