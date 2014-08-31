package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class VerifyUser
 */
@WebServlet("/VerifyUser")
public class VerifyUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String REALUSERNAME = "ride";
    private static final String REALPASSWORD = "ride";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Verify User doGet() called");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Verify User doPost() called");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if(username.equals(REALUSERNAME) && password.equals(REALPASSWORD)) {
			request.getSession().setAttribute("user", "ride");
			request.getRequestDispatcher("/WEB-INF/secret.jsp").forward(request, response);
			//response.sendRedirect("/Ruler/WEB-INF/secret.jsp");
			return;
		}
		
		PrintWriter out = response.getWriter();
		out.write("Invalid username or password");
		out.close();
	}
	

}
