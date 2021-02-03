package com.musicwiz.test;

import com.musicwiz.dao.MusicWizDAO;

public class MusicWizServiceTest {
	public static void main(String[] args) {
		MusicWizDAO dao = new MusicWizDAO();
		//dao.getTopLabels();
		//dao.getSearchResults("Coldplay");
		dao.getTextSearchResults("Coldplay release in Germany");
		//dao.getCount();
	}
}
