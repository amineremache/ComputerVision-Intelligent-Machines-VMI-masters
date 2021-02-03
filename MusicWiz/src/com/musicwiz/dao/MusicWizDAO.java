package com.musicwiz.dao;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

import com.musicwiz.bt.SearchResult;

/***
 * Data access class which interacts with Apache Jena DB
 * @author Abhishek
 *
 */
public class MusicWizDAO {
	
	public static final String connection = "http://localhost:3030/Music/query";
	
	/***
	 * Return the number of records in the database
	 * @return int number of records
	 */
	public int getCount() {
		int count = 0;
		String query = "SELECT (COUNT (?subject) as ?count) WHERE { "
				+ "?subject ?predicate ?object }";
		
		QueryExecution qe = QueryExecutionFactory.sparqlService(connection, query);
		ResultSet results = qe.execSelect();
		while (results.hasNext()) {
			QuerySolution binding = results.nextSolution();
			count = Integer.parseInt(binding.getLiteral("count").getLexicalForm());
		}
		qe.close();
		return count;
	}
	
	/***
	 * Get top artists based on the number of releases of each artist
	 * @return map containing artist and number of his releases
	 */
	public Map<String, Integer> getTopArtists() {
		Map<String, Integer> topArtists = new LinkedHashMap<String, Integer>();
		String query = "SELECT distinct ?band (count(distinct ?o) as ?Number_of_releases) "
				+ "WHERE "
				+ "{  "
				+ "?s <http://xmlns.com/foaf/0.1/made> ?ob. "
				+ "?ob <http://purl.org/ontology/mo/release_event> ?o. "
				+ "?s <http://xmlns.com/foaf/0.1/name> ?band. "
				+ "FILTER(!(?band = 'Various Artists')) "
				+ "} "
				+ "group by ?band "
				+ "order by desc(?Number_of_releases) "
				+ "LIMIT 25";
		
		QueryExecution qe = QueryExecutionFactory.sparqlService(connection, query);
		ResultSet results = qe.execSelect();
		while (results.hasNext()) {
			QuerySolution binding = results.nextSolution();
			String band = binding.getLiteral("band").getLexicalForm();
			int numberOfReleases = Integer.parseInt(binding.getLiteral("Number_of_releases").getLexicalForm());
			topArtists.put(band, numberOfReleases);
		}
		qe.close();
		return topArtists;
	}
	
	/***
	 * Get top locations based on the number of releases in each location
	 * @return map containing countries as key and number of releases as value
	 */
	public Map<String, Integer> getLocations() {
		Map<String, Integer> locations = new LinkedHashMap<String, Integer>();
		String query = "SELECT distinct ?place (count(?place) as ?number_of_releases) "
				+ "WHERE "
				+ "{ "
				+ "?s <http://xmlns.com/foaf/0.1/made> ?ob. "
				+ "?ob <http://purl.org/ontology/mo/publishing_location> ?o. "
				+ "?o <http://www.w3.org/2003/01/geo/wgs84_pos#name> ?place "
				+ "} "
				+ "group by ?place "
				+ "order by desc(?number_of_releases) "
				+ "LIMIT 25";

		QueryExecution qe = QueryExecutionFactory.sparqlService(connection, query);
		ResultSet results = qe.execSelect();
		while (results.hasNext()) {
			QuerySolution binding = results.nextSolution();
			String place = binding.getLiteral("place").getLexicalForm();
			int numberOfReleases = Integer.parseInt(binding.getLiteral("number_of_releases").getLexicalForm());
			locations.put(place, numberOfReleases);
		}
		qe.close();
		return locations;
	}
	
	/***
	 * Get top labels based on number of releases
	 * @return map containing Labels and number of releases
	 */
	public Map<String, Integer> getTopLabels() {
		Map<String, Integer> labels = new LinkedHashMap<String, Integer>();
		String query = "SELECT distinct ?label (count(distinct ?s) as ?num_of_releases) "
				+ "WHERE "
				+ "{  "
				+ "?s <http://purl.org/ontology/mo/label> ?o. "
				+ "?o <http://xmlns.com/foaf/0.1/name> ?label. "
				+ "FILTER(!(?label = '[no label]')) "
				+ "} "
				+ "group by ?label "
				+ "Order by desc(?num_of_releases) "
				+ "limit 25";
		
		QueryExecution qe = QueryExecutionFactory.sparqlService(connection, query);
		ResultSet results = qe.execSelect();
		while (results.hasNext()) {
			QuerySolution binding = results.nextSolution();
			String Label = binding.getLiteral("label").getLexicalForm();
			int numberOfReleases = Integer.parseInt(binding.getLiteral("num_of_releases").getLexicalForm());
			labels.put(Label, numberOfReleases);
		}
		qe.close();
		return labels;
	}
	
	/***
	 * Get top releases based on number of tracks in each release
	 * @return map containing release name and number of tracks
	 */
	public Map<String, Integer> getTopReleases() {
		Map<String, Integer> releases = new LinkedHashMap<String, Integer>();
		String query = "SELECT distinct ?record ?trackcount "
				+ "WHERE "
				+ "{  "
				+ "?s <http://purl.org/ontology/mo/record> ?rec.  "
				+ "?rec <http://purl.org/ontology/mo/track_count> ?trackcount.  "
				+ "?s <http://purl.org/dc/terms/title> ?record. "
				+ "} "
				+ "order by desc(?trackcount) "
				+ "limit 25";
		
		QueryExecution qe = QueryExecutionFactory.sparqlService(connection, query);
		ResultSet results = qe.execSelect();
		while (results.hasNext()) {
			QuerySolution binding = results.nextSolution();
			String Label = binding.getLiteral("record").getLexicalForm();
			int numberOfReleases = Integer.parseInt(binding.getLiteral("trackcount").getLexicalForm());
			releases.put(Label, numberOfReleases);
		}
		qe.close();
		return releases;
	}
	
	/***
	 * Get search results for simple search strings
	 * Simple search strings are those which don't have many predicates
	 * @param searchparam search string
	 * @return triples containing the search results
	 */
	public List<SearchResult> getSearchResults(String searchparam) {
		List<SearchResult> searchResults = new LinkedList<SearchResult>();
		String query = "PREFIX text: <http://jena.apache.org/text#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
				+ "PREFIX date:    <http://purl.org/dc/terms/> "
				+ "PREFIX geo:     <http://www.w3.org/2003/01/geo/wgs84_pos#> "
				+ "SELECT distinct ?s ?label "
				+ "WHERE "
				+ "{ {?s text:query ('" + searchparam + "') } "
				+ "{ ?s rdfs:label ?label } "
				+ "UNION "
				+ "{ ?s foaf:name ?label } "
				+ "UNION "
				+ "{ ?s date:date ?label } "
				+ "UNION "
				+ "{ ?s geo:name ?label} }";
		
		QueryExecution qe = QueryExecutionFactory.sparqlService(connection, query);
		ResultSet results = qe.execSelect();
		while (results.hasNext()) {
			QuerySolution binding = results.nextSolution();
			String subject = binding.getResource("s").getURI();
			String label = binding.getLiteral("label").getLexicalForm();
			SearchResult sr = new SearchResult(subject, label);
			searchResults.add(sr);
		}
		qe.close();
		return searchResults;
	}
	
	/***
	 * Get the search results for a complex search query
	 * Complex search query involves multiple subjects and predicates
	 * in different triples
	 * @param searchparam search string 
	 * @return triples containing the search string
	 */
	public List<SearchResult> getTextSearchResults(String searchparam) {
		List<SearchResult> searchResults = new LinkedList<SearchResult>();
		String query = "PREFIX text: <http://jena.apache.org/text#> "
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "prefix foaf: <http://xmlns.com/foaf/0.1/> "
				+ "prefix purl: <http://purl.org/ontology/mo/> "
				+ "prefix date: <http://purl.org/dc/terms/> "
				+ "prefix geo:  <http://www.w3.org/2003/01/geo/wgs84_pos#> "
				+ "select ?release ?label "
				+ "{ "
				+ "{ select distinct ?release ?label "
				+ "where { "
				+ "{ "
				+ "SELECT distinct ?release "
				+ "WHERE { "
				+ "{ ?s text:query ('" + searchparam + "'); "
				+ "foaf:name ?label; "
				+ "foaf:made ?release } } } "
				+ "{ "
				+ "?year text:query ('" + searchparam + "'); "
				+ "date:date ?label "
				+ "}   "
				+ "?release purl:release_event ?year "
				+ "} } "
				+ "union { "
				+ "select distinct ?release ?label "
				+ "where { "
				+ "{ "
				+ "SELECT distinct ?release "
				+ "WHERE { "
				+ "{ "
				+ "?s text:query ('" + searchparam + "'); "
				+ "foaf:name ?label; "
				+ "foaf:made ?release "
				+ "} } } { "
				+ "?loc text:query ('" + searchparam + "'); "
				+ "geo:name ?label "
				+ "}  "
				+ "?release purl:publishing_location ?loc "
				+ "}"
				+ "	} }";
		
		QueryExecution qe = QueryExecutionFactory.sparqlService(connection, query);
		ResultSet results = qe.execSelect();
		while (results.hasNext()) {
			QuerySolution binding = results.nextSolution();
			String subject = binding.getResource("release").getURI();
			String label = binding.getLiteral("label").getLexicalForm();
			SearchResult sr = new SearchResult(subject, label);
			searchResults.add(sr);
		}
		qe.close();
		return searchResults;
	}
}