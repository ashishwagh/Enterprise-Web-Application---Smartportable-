import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;


public class SalesDailyReport extends HttpServlet {
	 MySQLDataStoreUtilities ms = new MySQLDataStoreUtilities();
  
	 
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