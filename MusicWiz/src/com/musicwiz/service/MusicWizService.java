package com.musicwiz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.musicwiz.bt.SearchResult;
import com.musicwiz.dao.MusicWizDAO;

/***
 * The REST web services implementing different data requirements
 * @author Abhishek
 *
 */
@Path("/Music")
public class MusicWizService implements IMusicWizService {
	MusicWizDAO dao = new MusicWizDAO();
	
	@GET
	@Path("/count")
	@Produces(MediaType.APPLICATION_JSON)
	public int getCount() {
		return dao.getCount();
	}
	
	@GET
	@Path("/graphList")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getGraphList() {
		List<String> graphList = new ArrayList<String>();
		graphList.add("Top Artists");
		graphList.add("Top Countries");
		graphList.add("Top Labels");
		graphList.add("Top Releases");
		return graphList;
	}
	
	@GET
	@Path("/artists")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Integer> getTopArtists() {
		return dao.getTopArtists();
	}
	
	@GET
	@Path("/locations")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Integer> getLocations() {
		return dao.getLocations();
	}
	
	@GET
	@Path("/labels")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Integer> getTopLabels() {
		return dao.getTopLabels();
	}
	
	@GET
	@Path("/releases")
	@Produces("application/json")
	public Map<String, Integer> getTopReleases() {
		return dao.getTopReleases();
	}
	
	@GET
	@Path("/search")
	@Produces("application/json")
	public List<SearchResult> getSearchResults(
			@QueryParam("searchInput") String searchparam) {
		return dao.getSearchResults(searchparam);
	}
	
	@GET
	@Path("/textSearch")
	@Produces("application/json")
	public List<SearchResult> getTextSearchResults(
			@QueryParam("searchInput") String searchparam) {
		return dao.getTextSearchResults(searchparam);
	}
}
