import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveUpdatedProduct extends HttpServlet {
	public PrintWriter out;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		out = response.getWriter();
		Utilities utility= new Utilities(request,out);
		/*Saxpaser handler = new Saxpaser("C:\\apache-tomcat-7.0.34\\webapps\\SmartPortables_4\\WEB-INF\\classes\\ProductCatalog.xml");
		HashMap<String, Product> hm2 = handler.getProducts();*/
		HashMap<String, Product> hm2 = MySQLDataStoreUtilities.getProducts();
		
		String productid=request.getParameter("id");
		String productname=request.getParameter("prodName");		
        String producttype=request.getParameter("category");
        String productcondition=request.getParameter("prodCondition");
		String productprice=request.getParameter("prodPrice");
		String productquantity=request.getParameter("prodQuantity");
        String productmaker=request.getParameter("prodmanufacturer");
        String productimage=request.getParameter("prodImage");
        String productonsale=request.getParameter("prodOnsale");
        String productrebate=request.getParameter("prodRebate");
		
        utility.updateProduct(productname,producttype,productcondition,productmaker,
        		productimage,productquantity,productprice,productonsale,productrebate,productid);	
        
		utility = new Utilities(request, out);
		if (utility.isLoggedin())
			utility.printHtml("HeaderLogout.html");
		else
			utility.printHtml("Header.html");

		out.print("<div id='content'><div class='post'><h2 class='title meta'>");
		out.print("</h2><div class='entry'>");
		out.print("<h4 style='color:green'>Your prodct is updated successfully.");
		out.print("</h4></div></div></div>");
		utility.printHtml("LeftNav.html");
		utility.printHtml("Footer.html");
	}

}
