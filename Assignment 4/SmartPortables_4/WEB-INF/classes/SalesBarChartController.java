import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

import org.json.JSONObject;
import com.google.gson.Gson;


public class SalesBarChartController extends HttpServlet {
	 MySQLDataStoreUtilities ms = new MySQLDataStoreUtilities();
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
PrintWriter out= response.getWriter();

String username = request.getParameter("username");
// Product product = new Product();
 HttpSession session=request.getSession();  
ArrayList<Product> products;

Users user = (Users)session.getAttribute("user");
//String usert = user.usertype;

Utilities utility = new Utilities(request,out);

if(session.getAttribute("user")!= null )  
{
	//
//String n=(String)session.getAttribute("uname");  
	
	//users= (Users)session.getAttribute("user");
	
products=ms.checkSales();

 Gson gson=new Gson();
 String jsonString=gson.toJson(products);
out.println(jsonString);



}

}
}
