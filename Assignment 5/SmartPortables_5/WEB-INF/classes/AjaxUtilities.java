import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;


public class AjaxUtilities extends HttpServlet {
   String searchKeyword;
   String action;
  HashMap<String, Product> products ;
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
StringBuffer sb = new StringBuffer();

		boolean prod  = false;
		PrintWriter out= response.getWriter();
		Utilities utility = new Utilities(request,out);
		products = MySQLDataStoreUtilities.getProducts();
		action = request.getParameter("action");
		searchKeyword = request.getParameter("id");	  
	  
	  
	  if(searchKeyword!= "" || !searchKeyword.equals(""))
	  {
		
	  
		  if(action.equals("complete"))
		  {
			  searchKeyword = searchKeyword.trim().toLowerCase();  
				 for(Map.Entry<String,Product> entry : products.entrySet())
					{
						Product product=(Product)entry.getValue();
						
						if(product.getName().toLowerCase().startsWith(searchKeyword))
						{
						sb.append("<product>");
						sb.append("<id>" + product.getId() + "</id>");
						sb.append("<name>" + product.getName() + "</name>");
						sb.append("</product>");
						prod = true;
						}
					}	
					if(prod)
					{
						response.setContentType("text/xml");
						response.setHeader("Cache-Control","no-cache");
						out.write("<products>" + sb.toString() +"</products>");
					}
					else
					{
						response.setStatus(HttpServletResponse.SC_NO_CONTENT);
					}
		  }
		if(action.equals("lookup"))
			{
				request.setAttribute("productSearched",products.get(searchKeyword));
				request.getRequestDispatcher("/ViewSearchProduct").forward(request, response);
			}
	  }
	  }
}