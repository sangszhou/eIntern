package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.utils.AssembleDS;
import com.utils.GlobalData;

/**
 * Servlet implementation class initTable
 */
@WebServlet("/InitTable")
public class InitTable extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
	private AssembleDS assembler;
    public InitTable() {
        // TODO Auto-generated constructor stub
    	assembler = new AssembleDS();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Init Table doGet() called!");
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONObject res = assembler.assembleJson(GlobalData.noCategories);
		
		System.out.println("Init Table doPost() called!");
		System.out.println(res);
		
		PrintWriter out = response.getWriter();
		out.println(res);
		out.close();
	}

}
