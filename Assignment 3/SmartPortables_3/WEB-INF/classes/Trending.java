import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@WebServlet("/Trending")

public class Trending extends HttpServlet {

	ArrayList <Mostsold> mostsold = new ArrayList <Mostsold> ();
    ArrayList <Mostsoldzip> mostsoldzip = new ArrayList <Mostsoldzip> ();
	ArrayList <Bestrating> bestrated = new ArrayList <Bestrating> ();

	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		mostsold = MySQLDataStoreUtilities.mostsoldProducts();
		mostsoldzip = MySQLDataStoreUtilities.mostsoldZip();
		bestrated      = MongoDBDataStoreUtilities.topProducts();
		
		Utilities utility = new Utilities(request, pw);
		if (utility.isLoggedin())
			utility.printHtml("HeaderLogout.html");
		else
			utility.printHtml("Header.html");
		pw.println("<section id=\"content\">");
	
		pw.print("<div class='box'>");
		pw.print("<div class='box-header'>");
		pw.print("<h3 class='box-title' style='font-weight: bold;text-transform: uppercase'>Top 5 Most Liked Products based on Review Ratings</h3>");
		pw.print("</div>");
		pw.print("<table  class='table' style='border:double'>");
		pw.print("<th><label><b>Number</b></label></th>");
		pw.print("<th><label><b>Product Name</b></label></th>");
		pw.print("<th><label><b>Ratings</b></label></th>");
		Iterator itr2 = bestrated.iterator();
		int count =1;
        while(itr2.hasNext()) {
         Bestrating best = (Bestrating)itr2.next();
 		pw.print("<tr style='border:inset'>");
		pw.print("<td><label><b>"+count+"</b></label></td>");
		pw.print("</td>");
		pw.print("<td>");
		pw.print("<label><b>"+best.getProductname()+"</b></label>");
		pw.print("</td>");
		pw.print("<td>");
		pw.print("<label><b>"+best.getRating()+"</b></label>");
		pw.print("</td>");
		pw.print("</tr>");
		count++;
        }
		pw.print("</table></div>");	
		
		
		pw.print("<div class='box'>");
		pw.print("<div class='box-header'>");
		pw.print("<h3 class='box-title' style='font-weight: bold;text-transform: uppercase'>Top 5 Zipcode with Max number of product sold</h3>");
		pw.print("</div>");
		pw.print("<table  class='table' style='border:double'>");
		pw.print("<th><label><b>Zip Code</b></label></th>");
		pw.print("<th><label><b>Number of Products Sold</b></label></th>");
		Iterator itr1 = mostsoldzip.iterator();
         while(itr1.hasNext()) {
         Mostsoldzip mostzip = (Mostsoldzip)itr1.next();
 		pw.print("<tr style='border:inset'>");
 		pw.print("<td>");
		pw.print("<label><b>"+mostzip.getZipcode()+"</b></label>");
		pw.print("</td>");
		pw.print("<td>");
		pw.print("<label><b>"+mostzip.getCount()+"</b></label>");
		pw.print("</td>");
		pw.println("</tr>");
        }
         pw.print("</table></div>");
         
        pw.print("<div class='box'>");
 		pw.print("<div class='box-header'>");
 		pw.print("<h3 class='box-title' style='font-weight: bold;text-transform: uppercase'>Top 5 Most Sold Products</h3>");
 		pw.print("</div>");
 		pw.print("<table  class='table' style='border:double'>");
		pw.print("<th><label><b>Product Name</b></label></th>");
		pw.print("<th><label><b>Number of units sold</b></label></th>");
		
         Iterator itr = mostsold.iterator();
        while(itr.hasNext()) {
         Mostsold most = (Mostsold)itr.next();
 		pw.println("<tr style='border:inset'>");
 		pw.print("<td>");
		pw.print("<label><b>"+most.getProductname()+"</b></label>");
		pw.print("</td>");
		pw.print("<td>");
		pw.print("<label><b>"+most.getCount()+"</b></label>");
		pw.print("</td>");
		pw.println("</tr>");
        }
		pw.print("</table></div>");
		
		pw.println("</section>");
		utility.printHtml("LeftNav.html");
		utility.printHtml("Footer.html");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
