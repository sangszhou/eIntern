package com.entry.model;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EntryMapper implements RowMapper<Entry>{

	public Entry mapRow(ResultSet rs, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		Entry entry = new Entry();
		entry.setAccessTime(rs.getDate("ACCESSTIME"));
		entry.setBundleID(rs.getString("BUNDLEID"));
		entry.setBundleVersion(rs.getString("BUNDLEVERSION"));
		entry.setCategory(rs.getString("CATEGORY"));
		//entry.setDescription(rs.getString("DESCRIPTION"));
		entry.setDuration(rs.getInt("DURATION"));
		entry.setErrorCode(rs.getString("ERRORCODE"));
		//entry.setException(rs.getString("EXCEPTION"));
		entry.setHost(rs.getString("HOST"));
		entry.setId(rs.getInt("ID"));
		entry.setIDEType(rs.getString("IDETYPE"));
		entry.setIDEVersion(rs.getString("IDEVERSION"));
		entry.setKind(rs.getString("KIND"));
		//entry.setProperties(rs.getString("PROPERTIES"));
		entry.setQuantity(rs.getInt("QUANTITY"));
		entry.setSessionID(rs.getString("SESSIONID"));
		entry.setSize(rs.getInt("SIZE_"));
		entry.setUsername(rs.getString("USERNAME"));
		entry.setWhat(rs.getString("WHAT"));
		
		
		Clob description = rs.getClob("DESCRIPTION");
		Clob exception   = rs.getClob("EXCEPTION");
		Clob properties  = rs.getClob("PROPERTIES");
		
		if(description != null)
			entry.setDescription(description.getSubString(1, (int)description.length()));
		if(exception != null)
			entry.setException(exception.getSubString(1, (int)exception.length()));
		if(properties != null)
			entry.setProperties(properties.getSubString(1, (int)properties.length()));
		
		return entry;
	}
	
}
