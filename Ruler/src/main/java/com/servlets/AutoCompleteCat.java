package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.filter.model.Category;
import com.utils.GlobalData;

/**
 * Servlet implementation class AutoCompleteCat
 */
@WebServlet("/AutoCompleteCat")
public class AutoCompleteCat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AutoCompleteCat() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("AutoComplete doGet() is called");
		PrintWriter out = response.getWriter();
		
		JSONArray jsonarr = new JSONArray();
		
		//jsonobj.put("a", "a");
		jsonarr.put("a");
		jsonarr.put("aa");
		jsonarr.put("aas");
		out.println(jsonarr);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		System.out.println("AutoComplete doPost() is called");
		PrintWriter out = response.getWriter();
		
		JSONArray jsonarr = new JSONArray();
		//jsonobj.put("a", "a");
//		jsonarr.put("a");
//		jsonarr.put("aa");
//		jsonarr.put("aas");
//		jsonarr.put("aaa");
//		jsonarr.put("aaaas");
//		JSONObject res = new JSONObject();
//		res.put("query", jsonarr);
//		out.println(jsonarr);
		
		for(Category ithCategory:GlobalData.filters.getCategory()) {
			jsonarr.put(ithCategory.getName());
		}
		out.println(jsonarr);
		out.close();
	}

}
