import java.io.*;
import java.text.SimpleDateFormat;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;


public class AddProduct extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		AddProduct(request, response);
	}
	protected void AddProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        try
                {
                response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
                Utilities utility = new Utilities(request,pw);
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}
                String productname=request.getParameter("productname");		
                String producttype=request.getParameter("producttype");
                String productcondition=request.getParameter("productcondition");
				String productprice=request.getParameter("productprice");
				String productquantity=request.getParameter("productquantity");
                String productmaker=request.getParameter("productmanufacturer");
                String productimage=request.getParameter("productimage");
                String productonsale=request.getParameter("productonsale");
                String productrebate=request.getParameter("productrebate");
		
                utility.addProduct(productname,producttype,productcondition,productmaker,
                		productimage,productquantity,productprice,productonsale,productrebate);				     
       		
                utility.printHtml("HeaderLogout.html");
                pw.println("<section id=\"content\">");
		
	      		
	      		pw.print("<h2 style=\"color:green\">Product Added Successfully.</h2>");
	           
	      		pw.println("</section>");
	      		utility.printHtml("LeftNav.html");
	    		utility.printHtml("Footer.html");
	                     	
                    }
              	catch(Exception e)
		{
                 System.out.println(e.getMessage());
		}  			
       
	 	
		}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
            }
}