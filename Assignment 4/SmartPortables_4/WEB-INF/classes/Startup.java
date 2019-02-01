import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/Startup")

/*  
startup servlet Intializes HashMap in SaxParserDataStore
*/

public class Startup extends HttpServlet
{

	public void init() throws ServletException
    {
		Saxpaser.addHashmap();
		MySQLDataStoreUtilities.insertProduct();
		System.out.println("inside Startup-init "+Saxpaser.hm.size());

    }
}
