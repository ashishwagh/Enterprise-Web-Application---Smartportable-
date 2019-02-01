

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SubmitReview")

public class SubmitReview extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	        Utilities utility= new Utilities(request, pw);
		storeReview(request, response);
	}
	protected void storeReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				String productprice=request.getParameter("productprice");
                String productmaker=request.getParameter("productmaker");
                String reviewrating=request.getParameter("reviewrating");
                String reviewdate=request.getParameter("reviewdate");
                String reviewtext=request.getParameter("reviewtext");
				String retailerpin=request.getParameter("zipcode");
				String retailercity = request.getParameter("retailercity");
				if(reviewdate==null) {
					Date date = new Date();
					Calendar c = Calendar.getInstance(); 
					c.setTime(date); 
					date = c.getTime();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
					reviewdate = sdf.format(date);
				}
		String message=utility.storeReview(productname,producttype,productmaker,reviewrating,reviewdate,reviewtext,retailerpin,productprice,retailercity);				     
       		
		utility.printHtml("HeaderLogout.html");
		pw.println("<section id=\"content\">");
		
      		if(message.equals("Successfull"))
      		pw.print("<h2 style=\"color:green\">Review for "+productname+" Stored </h2>");
                else
		pw.print("<h2 style=\"color:green\">Mongo Db is not up and running </h2>");
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
