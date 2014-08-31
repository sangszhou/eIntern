package com.servlets;

import java.io.File;
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
import com.filter.model.Category;
import com.filter.model.Filter;
import com.filter.operator.FilteLog;
import com.utils.AssembleDS;
import com.utils.GlobalData;

/**
 * Servlet implementation class AddRule
 */
@WebServlet("/AddRule")
public class AddRule extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AssembleDS assembler;
	private FilteLog  filteLog;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddRule() {
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
		System.out.println("Add Rule doGet() called");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Add Rule doPost() called");
//		System.out.println("No categories entrie size is " + GlobalData.noCategories.size());
//		System.out.println("Category details is \n" + GlobalData.filters.getCategory().toString());
//		System.out.println("List<Entry> details is \n" + GlobalData.noCategories.toString());
		
		
		String categoryName = request.getParameter("categoryName");
		String filterName   = request.getParameter("filterName");
		String filterDescription = request.getParameter("filterDescription");
		String keywords     = request.getParameter("keywords");
		String patterns     = request.getParameter("patterns");
		
		Filter newFilter = assembler.assembleFilter(categoryName, filterName, filterDescription, keywords, patterns);
		
		boolean duplicatedFilter = false;
		boolean newCategory = true;
		for(Category ithCategory:GlobalData.filters.getCategory()) {
			if(ithCategory.getName().equals(categoryName)) {
				newCategory = false;
				for(Filter ithFilter : ithCategory.getFilter()) {
					if(ithFilter.getName().equals(newFilter.getName())) {
						duplicatedFilter = true;
						break;
					}
				}
				
				if(!duplicatedFilter) {
					// to format xml
					newFilter.setCategory(null); 
					ithCategory.getFilter().add(newFilter);
				}
				break;
			}
		}
		
		PrintWriter out = response.getWriter();
		if(duplicatedFilter) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("duplicatedFilter", true);
			jsonObj.put("success", true);
			out.println(jsonObj);
			out.close();
			return;
		}
		
		if(newCategory) {
			System.out.println("New Category");
			Category category = new Category();
			category.setName(categoryName);
			category.getFilter().add(newFilter);
			GlobalData.filters.getCategory().add(category);
		}		
		
		// write filter to local XML
		GlobalData.mbff.marshal(new File(GlobalData.FILTERSPATH), GlobalData.filters);
		
		// write those log qualify the new filter to DB 
		// and delete them from memory
		
		System.out.println("Before, the log size is " + GlobalData.noCategories.size());
		List<Entry> toDB = new ArrayList<Entry>();
		for(Entry ithEntry : GlobalData.noCategories) {
			if(filteLog.isMatch(ithEntry.getException(), newFilter)) {
				ithEntry.setCategory(categoryName);
				toDB.add(ithEntry);
			}
		}
		
		System.out.println("logs size that should be updated to DB: " + toDB.size());
		
		// toDB size cannot be zero since batchUpdateCategory
		// may throw exception
		if(toDB.size() == 0) {
			System.out.println("toDB size is 0");
			JSONObject res = assembler.assembleJson(GlobalData.noCategories);
			res.put("duplicatedFilter", false);
			res.put("success", true);
			res.put("size", toDB.size());
			out.println(res);
			out.close();
			return;
		}
		
		if(toDB.size() > 0)
			GlobalData.entryJDBCTemplate.batchUpdateCategory(toDB);
		
		for(Entry ithEntry : toDB) {
			GlobalData.noCategories.remove(ithEntry);
		}
		System.out.println("After, the log size shrink to " + GlobalData.noCategories.size());
		
		// send response json to client
		///JSONObject res = new JSONObject();
		JSONObject res = assembler.assembleJson(GlobalData.noCategories);
		res.put("duplicatedFilter", false);
		res.put("success", true);
		res.put("size", toDB.size());
		out.println(res);
		out.close();
		
	}

}
