import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class InventoryOnSale extends HttpServlet {
	MySQLDataStoreUtilities ms = new MySQLDataStoreUtilities();

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	        Utilities utility= new Utilities(request, pw);
	        
	        showInventoryListOnSale(request, response);
		
            }
	protected void showInventoryListOnSale(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try
            {         
            response.setContentType("text/html");
	PrintWriter pw = response.getWriter();
            Utilities utility = new Utilities(request,pw);
	if(!utility.isLoggedin()){
		HttpSession session = request.getSession(true);				
		session.setAttribute("login_msg", "Please Login to view Inventory List On Sale.");
		response.sendRedirect("Login");
		return;
	}
	ArrayList<Product> products;
	products = ms.checkInventoryonsale();
		
		if (utility.isLoggedin())
			utility.printHtml("HeaderLogout.html");
		else
			utility.printHtml("Header.html");
		pw.println("<section id=\"content\">");
	
		pw.print("<div class='box'>");
		pw.print("<div class='box-header'>");
		pw.print("<h3 class='box-title' style='font-weight: bold;text-transform: uppercase'>Product on Sale</h3>");
		pw.print("</div>");
		pw.print("<table  class='table' style='border:double'>");
		pw.print("<th><label><b>Number</b></label></th>");
		pw.print("<th><label><b>Product Name</b></label></th>");
		pw.print("<th><label><b>Price</b></label></th>");
		pw.print("<th><label><b>Quantity</b></label></th>");
		pw.print("<th><label><b>Catogery</b></label></th>");
		pw.print("<th><label><b>Onsale</b></label></th>");
		Iterator itr2 = products.iterator();
		int count =1;
        while(itr2.hasNext()) {
         Product prod = (Product)itr2.next();
 		pw.print("<tr style='border:inset'>");
		pw.print("<td><label><b>"+count+"</b></label></td>");
		pw.print("</td>");
		pw.print("<td>");
		pw.print("<label><b>"+prod.getName()+"</b></label>");
		pw.print("</td>");
		pw.print("<td>");
		pw.print("<label><b>"+prod.getPrice()+"</b></label>");
		pw.print("</td>");
		pw.print("<td>");
		pw.print("<label><b>"+prod.getQuantity()+"</b></label>");
		pw.print("</td>");
		pw.print("<td>");
		pw.print("<label><b>"+prod.getCategory()+"</b></label>");
		pw.print("</td>");
		pw.print("<td>");
		pw.print("<label><b>"+prod.getOnsale()+"</b></label>");
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
}