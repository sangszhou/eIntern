package com.entry.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

public class EntryJDBCTemplate {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	public Entry getEntry(Integer id) {
		String SQL = "select * from USAGEDATA4P_TEST " +
				"where ID =?";
		Entry entry = jdbcTemplateObject.queryForObject(SQL, 
				new Object[] {id}, new EntryMapper());
		return entry;
	}
	
	// maybe useless
	public List<Entry> getEntriesAfter(Date date) {
		String SQL = "select * from USAGEDATA4P_TEST " +
				"where DATE >= ?";
		List<Entry> entries = jdbcTemplateObject.query(SQL,
				new EntryMapper());
		return entries;
	}
	
	// update category
	public int[] batchUpdateCategory(final List<Entry> entries) {
		System.out.println("entries size is " + entries.size());
		System.out.println("entries[0].id = " + entries.get(0).getId());
		System.out.println("entries[0].category = " + entries.get(0).getCategory());
		int[] updateCounts = jdbcTemplateObject.batchUpdate(
				"update USAGEDATA4P_TEST set category = ? where id = ?",
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, entries.get(i).getCategory());
						ps.setInt(2, entries.get(i).getId());
					}
					
					public int getBatchSize() {
						return entries.size();
					}
				});
		
		return updateCounts;
	}
	
	// get null category entries
	// This SQL need to be replaced after testing
	public List<Entry> getExpEntriesWithoutCategory() {
		String SQL = "select * from USAGEDATA_TEST_BAK where rownum < 20 "+		
				" and CATEGORY is null " +
				" and EXCEPTION is not null ";
		return jdbcTemplateObject.query(SQL, new EntryMapper());
	}
}
