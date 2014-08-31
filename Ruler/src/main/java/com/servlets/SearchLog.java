package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.entry.model.Entry;
import com.filter.model.Filter;
import com.filter.operator.FilteLog;
import com.utils.AssembleDS;
import com.utils.GlobalData;

/**
 * Servlet implementation class SearchLog
 */
@WebServlet("/SearchLog")
public class SearchLog extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private AssembleDS assembler;
	private FilteLog  filteLog;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchLog() {
        super();
        // TODO Auto-generated constructor stub
        assembler = new AssembleDS();
        filteLog  = new FilteLog();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Search Log doGet() called");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Search Log doPost() called");
		
		String restoreDT = request.getParameter("restoreDT");
		
		String strKeywords = request.getParameter("keywords");
		String strPatterns = request.getParameter("patterns");
		
		Filter newFilter = assembler.assembleFilter("", "", "", strKeywords, strPatterns);
		
		List<Entry> matchedEntry;
		if(restoreDT.equals("true")) {
			matchedEntry = GlobalData.noCategories;
		} else {
			matchedEntry = new ArrayList<Entry>();
			for(Entry ithEntry : GlobalData.noCategories) {
				if(filteLog.isMatch(ithEntry.getException(), newFilter)) {
					matchedEntry.add(ithEntry);
				}
			}
		}
		
		JSONObject res = assembler.assembleJson(matchedEntry); 
		//JSONObject res = new JSONObject();
		res.put("matchedLog", matchedEntry.size());
		res.put("success", true);
		//res.put("data", data);
		PrintWriter out = response.getWriter();
		out.println(res);
		out.close();
	}

}
