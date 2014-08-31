package com.utils;

import java.util.Set;


public class ErrorLog {
	private Set<String> errorCode = null;
	private Set<String> causedBy = null;
	private Set<String> failedTo = null;
	private String errorBody = null;
	
	private String info = "";
	
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info += info;
	}

	public Set<String> getErrorCode() {
		return errorCode;
	}
	

	public void setErrorCode(Set<String> errorCode) {
		if(this.errorCode == null) 
			this.errorCode = errorCode;
		else
			this.errorCode.addAll(errorCode);
	}

	public Set<String> getCausedBy() {
		return causedBy;
	}

	public void setCausedBy(Set<String> causedBy) {
		if(this.causedBy == null)
			this.causedBy = causedBy;
		else
			this.causedBy.addAll(causedBy);
	}

	public Set<String> getFailedTo() {
		return failedTo;
	}

	public void setFailedTo(Set<String> failedTo) {
		if(this.failedTo == null)
			this.failedTo = failedTo;
		else
			this.failedTo.addAll(failedTo);
	}

	public String getErrorBody() {
		
		return errorBody;
	}

	public void setErrorBody(String errorBody) {
		if(this.errorBody == null)
			this.errorBody = errorBody;
		else
			this.errorBody += errorBody + "\n";
	}
	
	public String toString() {
		String res = "";
		
		if(info != null) 
			res += info + "\n";
		
		if(failedTo != null) 
			res += failedTo.toString() + "\n";
		
		if(causedBy != null) 
			res += causedBy.toString() + "\n";
		
		if(errorCode != null)
			res += errorCode.toString() + "\n";
		
		if(errorBody != null) 
			res += errorBody + "\n";
	
		return res;
	}
	
	
	
//	public void processSelf() {
//		MySimpleAnalyzer simAnalyzer = new MySimpleAnalyzer();
//		// query should be 
//	}
}
