
  

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;	

			
			
public class Carousel{
			
	public String  carouselfeature(Utilities utility){
				
		ProductRecommenderUtility prodRecUtility = new ProductRecommenderUtility();
		
		HashMap<String, Product> hm = new HashMap<String, Product>();
		StringBuilder sb = new StringBuilder();
		String myCarousel = null;
			
		String name = null;
		String CategoryName = null;
		if(CategoryName==null){
			try{
			hm=MySQLDataStoreUtilities.getProducts();
			name = "";
			}catch(Exception e)
			{
				
			}
			
		}
		HashMap<String,String> prodRecmMap = new HashMap<String,String>();
		prodRecmMap = prodRecUtility.readOutputFile();
		
		
		
		int l =0;
		for(String user: prodRecmMap.keySet())
		{
			if(user.equals(utility.username()))
			{
				String products = prodRecmMap.get(user);
				products=products.replace("[","");
				products=products.replace("]","");
				products=products.replace("\"", " ");
				ArrayList<String> productsList = new ArrayList<String>(Arrays.asList(products.split(",")));

		        myCarousel = "myCarousel"+l;
					
				sb.append("<div><div class='post'><h2 class='title meta'>");
				sb.append("<a style='font-size: 24px;'>"+""+" Recommended Products</a>");
				
				sb.append("</h2>");

				sb.append("<div>");
				sb.append("<div id=\""+myCarousel+"\" class=\"carousel slide\" data-ride=\"carousel\">");

				sb.append("<ol class=\"carousel-indicators\"> ");
				sb.append("<li data-target=\"#myCarousel\" data-slide-to=\"0\" class=\"active\"></li>  ");
				sb.append("<li data-target=\"#myCarousel\" data-slide-to=\"1\"></li>  ");
				sb.append("<li data-target=\"#myCarousel\" data-slide-to=\"2\"></li>  ");
				sb.append("</ol>  ");
				sb.append("<div class=\"carousel-inner\" role=\"listbox\">  ");

						
				int k = 0;
				for(String prod : productsList){
					prod= prod.replace("'", "");
					Product prodObj = new Product();
					prodObj = ProductRecommenderUtility.getProduct(prod.trim());
					if (k==0 )
					{
						
						sb.append("<div class='item active' align=\"center\">");
					}
					else
					{
						sb.append("<div class='item' align=\"center\">");
					}
					
					sb.append("<div class=\"item\" align=\"center\">  ");
					
					sb.append("<h3>"+prodObj.getName()+"</h3>");
					
					sb.append("<img src='Html\\images\\" + prodObj.getImage()+"' alt='' style='height:200px' align=\"center\" />");
					sb.append("<li id='item' align=\"center\"><strong>Price:"+prodObj.getPrice()+"$</strong></li><ul>");
					sb.append("<button class=\"lbutton\"><a href=\"AddCart?id=" + prodObj.getId() + "&&name="
							+ prodObj.getName() + "&&price=" + prodObj.getPrice() + "&&image=" + prodObj.getImage()
							+ "\" class=\"btnreview\" style=\"color:white\">Add To Cart</a></button>");
					
					sb.append("<button class=\"lbutton\"><a href=\"WriteReview?id=" + prodObj.getId() + "&&name="
							+ prodObj.getName() + "&&price=" + prodObj.getPrice() + "&&image=" + prodObj.getImage()+ "&&catogory=" + prodObj.getCategory()+
							"&&manufacturer=" + prodObj.getManufacturer()
							+ "\" class=\"btnreview\" style=\"color:white\">Write Review</a></button>");
					
					sb.append("<button class=\"lbutton\"><a href=\"ViewReview?id=" + prodObj.getId() + "&&name="
							+ prodObj.getName() + "&&price=" + prodObj.getPrice() + "&&image=" + prodObj.getImage()+ "&&catogory=" + prodObj.getCategory()+
							"&&manufacturer=" + prodObj.getManufacturer()
							+ "\" class=\"btnreview\" style=\"color:white\">Read Review</a></button>");

					sb.append("</ul></div>");
					sb.append("</div>");
				
					k++;
					
				}
				
			
				sb.append(
						"<a class=\"left carousel-control\" href=\"#"+myCarousel+"\" role=\"button\" data-slide=\"prev\">  ");
				sb.append("<span class=\"glyphicon glyphicon-chevron-left\" aria-hidden=\"true\"></span>  ");
				sb.append("<span class=\"sr-only\">Previous</span>  ");
				sb.append("</a>  ");
				sb.append(
						"<a class=\"right carousel-control\" href=\"#"+myCarousel+"\" role=\"button\" data-slide=\"next\">  ");
				sb.append("<span class=\"glyphicon glyphicon-chevron-right\" aria-hidden=\"true\"></span>  ");
				sb.append("<span class=\"sr-only\">Next</span>  ");
				sb.append("</a> ");
				sb.append("</div></div>");
				sb.append("</div></div>");
				sb.append("</div>");
				l++;
			
				}
			}
			return sb.toString();
		}
	}
	