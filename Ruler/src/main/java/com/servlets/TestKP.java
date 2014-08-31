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
 * Servlet implementation class TestKP
 */
@WebServlet("/TestKP")
public class TestKP extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private AssembleDS assembler;
	private FilteLog  filteLog;
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    public TestKP() {
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
		System.out.println("Test KP doGet() is called");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Test KP doPost() is called");
		
		String strID = request.getParameter("logID");
		System.out.println(strID);
		String strKeywords = request.getParameter("keywords");
		String strPatterns = request.getParameter("patterns");
		
		Filter newFilter = assembler.assembleFilter("", "", "", strKeywords, strPatterns);
		
		List<Entry> matchedEntry = new ArrayList<Entry>();
		
		for(Entry ithEntry : GlobalData.noCategories) {
			if(filteLog.isMatch(ithEntry.getException(), newFilter)) {
				matchedEntry.add(ithEntry);
			}
		}
		
		boolean matchCurrentLog = false;
		for(Entry ithEntry : matchedEntry) {
			if(ithEntry.getId() == Integer.parseInt(strID)) {
				matchCurrentLog = true;
				break;
			}
		}
		
		// assemble json object
		JSONObject res = new JSONObject();
		res.put("success", true);
		res.put("matchCurrentLog", matchCurrentLog);
		res.put("matchTotal", matchedEntry.size());
		
		PrintWriter out = response.getWriter();
		out.println(res);
		out.close();
	}

}
