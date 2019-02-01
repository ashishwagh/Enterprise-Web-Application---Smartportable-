import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;


public class SalesDailyReport extends HttpServlet {
	 MySQLDataStoreUtilities ms = new MySQLDataStoreUtilities();
  /*public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
PrintWriter out= response.getWriter();

String username = request.getParameter("username");
// Product product = new Product();
 HttpSession session=request.getSession();  
ArrayList<Order> Orders;

Users user = (Users)session.getAttribute("user");
//String usert = user.usertype;

Utilities utility = new Utilities(request,out);

if(session.getAttribute("user")!= null )  
{
	//
//String n=(String)session.getAttribute("uname");  
	
	//users= (Users)session.getAttribute("user");
	
Orders=ms.checkSalesdate();
utility.printHtml("HeaderAdd.html");

  out.println("<section id=\"content\">");
 out.println("<h2 align=\"center\" margin-top=\"60px\">Products Sales Report</h2>");
		
	  out.println("<table cellspacing=\"0\" class=\"shopping-cart\"  style=\"Height:50px\"> ");
                out.println("<thead>");
                out.println("<tr class=\"headings\">");
                
				
				out.println("<th class=\"\">No.</td>");
                out.println("<th class=\"\">Date</td>");
               out.println("<th class=\"\">Total Sale in USD:$</td>");
              
			   
               
               
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody>");
			
			   int i= 1;
			    for(Order order : Orders)
			 {
				//Product product=(Product)entry.getValue();
				
				
						out.println("<tr>"); 
						out.println("<td class=\"productName\">");
						out.println(i++);
						out.println("</td>");
						out.println("<td class=\"productName\">");
						out.println(order.getDate());
						out.println("</td>");
						
						 out.println("<td class=\"productName\">");
						out.println("$ " +order.getprice());
						out.println("</td>");
						
						
						out.println("</tr>");
				
			 }
				 out.println("</tbody>");
                out.println("</table>");
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
	 
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
		        Utilities utility= new Utilities(request, pw);
		        showDailySale(request, response);
		}
		
		protected void showDailySale(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		        try
	                {         
	                response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
	                Utilities utility = new Utilities(request,pw);
			if(!utility.isLoggedin()){
				HttpSession session = request.getSession(true);				
				session.setAttribute("login_msg", "Please Login to view Daily Sales Report.");
				response.sendRedirect("Login");
				return;
			}
			ArrayList<Order> Orders;
			Orders=ms.checkSalesdate();
				
				if (utility.isLoggedin())
					utility.printHtml("HeaderLogout.html");
				else
					utility.printHtml("Header.html");
				pw.println("<section id=\"content\">");
			
				pw.print("<div class='box'>");
				pw.print("<div class='box-header'>");
				pw.print("<h3 class='box-title' style='font-weight: bold;text-transform: uppercase'>Total Daily Sale</h3>");
				pw.print("</div>");
				pw.print("<table  class='table' style='border:double'>");
				pw.print("<th><label><b>Number</b></label></th>");
				pw.print("<th><label><b>Date</b></label></th>");
				pw.print("<th><label><b>Total Sale</b></label></th>");
				Iterator itr2 = Orders.iterator();
				int count =1;
		        while(itr2.hasNext()) {
		         Order order = (Order)itr2.next();
		 		pw.print("<tr style='border:inset'>");
				pw.print("<td><label><b>"+count+"</b></label></td>");
				pw.print("</td>");
				pw.print("<td>");
				pw.print("<label><b>"+order.getDate()+"</b></label>");
				pw.print("</td>");
				pw.print("<td>");
				pw.print("<label><b>$"+order.getprice()+"</b></label>");
				pw.print("</td>");
				pw.print("</tr>");
				count++;
		        }
				pw.print("</table></div>");	
				
				pw.println("</section>");
				utility.printHtml("LeftNav.html");
				utility.printHtml("Footer.html");
		                     	
	        			
			}catch(Exception e){
	                System.out.println(e.getMessage());
			}
		}
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
		        Utilities utility= new Utilities(request, pw);
		        
		        showDailySale(request, response);
			
	            }
}