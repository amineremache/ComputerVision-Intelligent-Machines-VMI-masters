package com.musicwiz.bt;

import java.io.Serializable;

/***
 * Class to represent search results triples
 * @author Abhishek
 *
 */
public class SearchResult implements Serializable{
	private static final long serialVersionUID = 1L;
	private String subject;
	private String label;
	
	public SearchResult (){
		
	}
	
	public SearchResult (String subject, String label) {
		this.subject = subject;
		this.label = label;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

}
