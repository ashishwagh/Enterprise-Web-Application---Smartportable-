import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.*;
import javax.servlet.http.HttpSession;
@WebServlet("/DataAnalytics")

public class DataAnalytics extends HttpServlet {
	static DBCollection myReviews;

	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		
		Saxpaser handler = new Saxpaser("C:\\apache-tomcat-7.0.34\\webapps\\SmartPortables\\WEB-INF\\classes\\ProductCatalog.xml");
		HashMap<String, Product> hm2 = handler.getProducts();
		
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to View Reviews");
			response.sendRedirect("Login");
			return;
		}
		
						
		if (utility.isLoggedin())
			utility.printHtml("HeaderLogout.html");
		else
			utility.printHtml("Header.html");
		
		pw.println("<section id=\"content\">");
		pw.print("<form name ='FindReviews' action='FindReviews' method='post'>");
		pw.print("<div class='box'>");
		pw.print("<div class='box-header'>");
		pw.print("<h3 class='box-title'>Data Analytics on Review</h3>");
		pw.print("</div>");
		pw.print("<table  class='table'>");
			pw.print("<tr>");
			pw.print("<td> <input type='checkbox' name='queryCheckBox' value='productName'> Select </td>");
                                pw.print("<td><label><b> Product Name: </label></b></td>");
                                pw.print("<td>");
                                       pw.print("<select name='productName' class='uname' style='width: 250px'>");
				       pw.print("<option value='ALL_PRODUCTS'>All Products</option>");
				       for (Map.Entry<String, Product> entry : hm2.entrySet()) {
							Product product = (Product) entry.getValue();
                            pw.print("<option value='"+product.getName()+"'>"+product.getName()+"</option>");
				       }             

                 pw.print("</td>");
                 pw.print("</tr>");
                 pw.print("<tr>");
			     pw.print("<td> <input type='checkbox' name='queryCheckBox' value='productPrice'> Select </td>");
                                pw.print("<td><label><b>  Product Price: </label></b> </td>");
                              pw.print(" <td>");
                                  pw.print("  <input type='number' name='productPrice' value = '0' size=10  class='uname'/> </td>");
						pw.print("<td>");
					pw.print("<input type='radio' name='comparePrice' value='EQUALS_TO' checked> Equals <br>");
					pw.print("<input type='radio' name='comparePrice' value='GREATER_THAN'> Greater Than <br>");
					pw.print("<input type='radio' name='comparePrice' value='LESS_THAN'> Less Than");
					pw.print("</td></tr>");				
                            
  			
			pw.print("<tr><td> <input type='checkbox' name='queryCheckBox' value='reviewRating'> Select </td>");
                               pw.print(" <td><label><b>  Review Rating: </label></b></td>");
                               pw.print(" <td>");
                                   pw.print(" <select name='reviewRating' class='uname'>");
                                       pw.print(" <option value='1' selected>1</option>");
                                       pw.print(" <option value='2'>2</option>");
                                       pw.print(" <option value='3'>3</option>");
                                     pw.print("   <option value='4'>4</option>");
                                      pw.print("  <option value='5'>5</option>");
                                pw.print("</td>");
				pw.print("<td>");
				pw.print("<input type='radio' name='compareRating' value='EQUALS_TO' checked> Equals <br>");
				pw.print("<input type='radio' name='compareRating' value='GREATER_THAN'> Greater Than"); 
			pw.print("</td></tr>");
			
			pw.print("<tr>");
								pw.print("<td> <input type='checkbox' name='queryCheckBox' value='retailerCity'> Select </td>");
                                pw.print("<td><label><b> Retailer City: </label></b></td>");
                                pw.print("<td>");
                                pw.print("<input type='text' name='retailerCity' class='uname'/> </td>");
								
                            pw.print("</tr>");
							
							 pw.print("<tr>");
								pw.print("<td> <input type='checkbox' name='queryCheckBox' value='retailerZipcode'> Select </td>");
                               pw.print(" <td><label><b> Retailer Zip code: </label></b></td>");
                               pw.print(" <td>");
                                    pw.print("<input type='text' name='retailerZipcode' class='uname'/> </td>");
					        pw.print("</tr>");
				pw.print("<tr><td>");
					pw.print("<input type='checkbox' name='extraSettings' value='GROUP_BY'> Group By");
								pw.print("</td>");
								pw.print("<td>");
								pw.print("<select name='groupByDropdown' class='uname'>");
                                pw.print("<option value='GROUP_BY_CITY' selected>City</option>");
                                pw.print("<option value='GROUP_BY_PRODUCT'>Product Name</option>");                                        
                                pw.print("</td><td>");
								pw.print("<input type='radio' name='dataGroupBy' value='Count' checked> Count <br>");
								pw.print("<input type='radio' name='dataGroupBy' value='Detail'> Detail <br>");
								pw.print("</td></tr>");
								
									
			
                                pw.print("<td colspan = '4'> <input type='submit' value='Find Data' class='lbutton' /> </td>");
							
							
		pw.print("</table>");	
		pw.print("</form>");
		pw.print("</table>");
		pw.println("</section>");
		utility.printHtml("LeftNav.html");
		utility.printHtml("Footer.html");
	
	
	
			
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
