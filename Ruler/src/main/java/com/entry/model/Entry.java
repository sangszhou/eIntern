/**
 * 
 */
package com.entry.model;

import java.io.Serializable;
import java.util.Date;

public class Entry implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIDEType() {
		return IDEType;
	}

	public void setIDEType(String iDEType) {
		IDEType = iDEType;
	}

	public String getIDEVersion() {
		return IDEVersion;
	}

	public void setIDEVersion(String iDEVersion) {
		IDEVersion = iDEVersion;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getWhat() {
		return what;
	}

	public void setWhat(String what) {
		this.what = what;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBundleID() {
		return bundleID;
	}

	public void setBundleID(String bundleID) {
		this.bundleID = bundleID;
	}

	public String getBundleVersion() {
		return bundleVersion;
	}

	public void setBundleVersion(String bundleVersion) {
		this.bundleVersion = bundleVersion;
	}

	public Date getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	private String IDEType;
	private String IDEVersion;
	private String sessionID;
	private String host;
	private String username;
	private String kind;
	private String what;
	private String description;
	private String bundleID;
	private String bundleVersion;
	private Date accessTime;
	private int duration;
	private int size;
	private int quantity;
	private String exception;
	private String properties;
	private String category;
	private String errorCode;
	
	public Entry() {
		// TODO Auto-generated constructor stub
	}

}
