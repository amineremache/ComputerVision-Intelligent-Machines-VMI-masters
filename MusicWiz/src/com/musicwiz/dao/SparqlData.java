package com.musicwiz.dao;

import java.util.List;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

public class SparqlData {
	
	public static ResultSet executeQuery(String query) {
		String connection = "http://localhost:3030/Music/query";
		QueryExecution qe = QueryExecutionFactory.sparqlService(connection, query);
		ResultSet results = qe.execSelect();
		List<String> vars = results.getResultVars();
		while (results.hasNext()) {
			QuerySolution binding = results.nextSolution();
			binding.get("");
		}
		qe.close();
		return results;
	}
}
