import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddCart extends HttpServlet {

	PrintWriter out;
	List<Cart> arraylist;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		out = response.getWriter();

		String username = request.getParameter("username");
		Utilities utility = new Utilities(request,out);
		if(!utility.isLoggedin()) {
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}else {

		String makers = request.getParameter("id");
		String name = request.getParameter("name");
		String price = request.getParameter("price");
		String image = request.getParameter("image");
		
		arraylist = new ArrayList<Cart>();
		addToCart(name, price, image, request);
		request.getRequestDispatcher("/ViewCart").forward(request, response);
		}
	}

	public void addToCart(String name, String price, String image, HttpServletRequest request) {
		int id = 0;
		HttpSession session = request.getSession();
		out.println("addcart method");
		List<Cart> list = (ArrayList) session.getAttribute("cartitem");
		if (list == null) {
			id = id + 1;
			Cart cart = new Cart(name, price, image, id);
			arraylist.add(cart);
			session.setAttribute("cartitem", arraylist);
			out.println("hi-if");

		} else {
			id = list.size() + 1;
			Cart cart = new Cart(name, price, image, id);
			arraylist = list;
			arraylist.add(cart);
			session.setAttribute("cartitem", arraylist);
			out.println("hi-else");
		}

	}
}