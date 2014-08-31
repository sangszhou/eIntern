/*
Copyright [2013-2014] eBay Software Foundation

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

*/

package com.jdbc;



import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.model.Session;


public class SessionJDBCTemplate {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public List<Session> getSessionWithoutDod(String claus) {
		return jdbcTemplateObject.query("select * from RBT_SESSION " + " where " + claus, 
				new SessionMapper());
	}
	

	
	public List<Session> getStackTrace() {
		String query = "select * from RBT_SESSION where status = 1 and FULL_STACKTRACE is not null and rownum < 500";
		return jdbcTemplateObject.query(query, new SessionMapper());
	}
	
}
