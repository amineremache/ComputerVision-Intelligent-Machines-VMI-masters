package com.musicwiz.service;

import java.util.List;
import java.util.Map;

import com.musicwiz.bt.SearchResult;

/***
 * Interface for the REST webservices.
 * @author Abhishek
 *
 */
public interface IMusicWizService {
	public int getCount();
	public List<String> getGraphList();
	public Map<String, Integer> getTopArtists();
	public Map<String, Integer> getLocations();
	public Map<String, Integer> getTopLabels();
	public Map<String, Integer> getTopReleases();
	public List<SearchResult> getSearchResults(String searchparam);
	public List<SearchResult> getTextSearchResults(String searchparam);
}
