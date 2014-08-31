package com.testjdbc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jdbc.SessionJDBCTemplate;
import com.model.Session;

public class TestGetSession {

	public static void main(String[] args) {
				
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc-config.xml");
		SessionJDBCTemplate sjt = (SessionJDBCTemplate) context.getBean("sessionJDBCTemplate");
		
		List<Session> sessions = sjt.getStackTrace();
		
		String pathName = "E:\\zhou\\";
		
		for(Session ithSession : sessions) {
			//System.out.println(ithSession.getId());
			File questionName = new File(pathName + "questionRepos\\question"+ithSession.getId() + ".txt");
			File solutionName = new File(pathName + "solutionRepos\\solution"+ithSession.getId() + ".txt");
			
			System.out.println(ithSession.getFullStackTrace());
			System.out.println("-----------------------------------------------");
			System.out.println("-----------------------------------------------");
			BufferedWriter out1 = null;
			BufferedWriter out2 = null;
			try {
				out1 = new BufferedWriter(new FileWriter(questionName));
				out2 = new BufferedWriter(new FileWriter(solutionName));
				out1.write(ithSession.getFullStackTrace());
				out2.write(ithSession.getFullStackTrace());
				
				if(out1 != null) {
					out1.flush();
					out1.close();
				}
				
				if(out2 != null) {
					out2.flush();
					out2.close();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		
	}

}
