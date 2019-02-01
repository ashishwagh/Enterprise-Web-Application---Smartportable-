import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import com.mongodb.AggregationOutput;


@WebServlet("/DataVisualization")
public class DataVisualization extends HttpServlet {

    static DBCollection myReviews;

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        displayPage(request, response, pw);
    }

    protected void displayPage(HttpServletRequest request, HttpServletResponse response, PrintWriter pw)
            throws ServletException, IOException {

        Utilities utility = new Utilities(request, pw);
        if (utility.isLoggedin())
			utility.printHtml("HeaderLogout.html");
		else
			utility.printHtml("Header.html");
		pw.println("<section id=\"content\">");
	
		pw.print("<div class='box'>");
		pw.print("<div class='box-header'>");
		pw.print("<h3 class='box-title' >Data Visualization</h3>");
		pw.print("</div>");

        
            
        pw.print("<h3><button id='btnGetChartData' class='lbutton'>View Chart</h3>");
        pw.println("<div id='chart_div'></div>");
        pw.print("</div>");
		pw.println("</section>");
		utility.printHtml("LeftNav.html");
		utility.printHtml("Footer.html");

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ArrayList<Review> reviews = MongoDBDataStoreUtilities.selectReviewForChart();
            ArrayList<Review> topReviewsPerCity = getTop3InEveryCity(reviews);
            
            String reviewJson = new Gson().toJson(topReviewsPerCity);

            response.setContentType("application/JSON");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(reviewJson);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    private static ArrayList<Review> getTop3InEveryCity(ArrayList<Review> reviewList){

        Collections.sort(reviewList, new Comparator<Review>(){
            public int compare(Review r1, Review r2){
                return Integer.parseInt(r2.getReviewRating()) - Integer.parseInt(r1.getReviewRating());
            }
        });


       ArrayList<String> zipCodeList = new ArrayList<>();
       for(Review review:reviewList){
            String zipCode = review.getRetailerPin();
            if(!(zipCodeList.contains(zipCode))){
                zipCodeList.add(zipCode);
            }
       } 

       ArrayList<Review> top3Reviews = new ArrayList<>();
       for(String zipCode:zipCodeList){
            ArrayList<Review> top3ReviewsCity = new ArrayList<>();
            for(Review review:reviewList){
                if(review.getRetailerPin().equals(zipCode) && top3ReviewsCity.size()<3){
                    top3ReviewsCity.add(review);
                }
            }
            top3Reviews.addAll(top3ReviewsCity); 
       }

        return top3Reviews;
    }

}
