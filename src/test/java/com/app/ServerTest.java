package com.app;

import java.net.ServerSocket;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import musichub.business.Album;
import musichub.business.AudioElement;
import musichub.business.MusicHub;
import musichub.business.NoElementFoundException;
import musichub.business.NoPlayListFoundException;
import musichub.business.PlayList;
import musichub.business.Song;


public class ServerTest {

	public MusicHub hub;
	
	

	@Before
	public void initializeResource() {
		hub = new MusicHub();
		
	}
	

	@Test
	public void createSongTest() {

		String title = "Test Song";

		String genre = "hiphop";

		String artist = "Eminem";

		boolean flag = false;

		int length = 500;

		String content = "E:\\Ashish\\Songs\\Pachtaoge.mp3";

		Song s = new Song(title, artist, length, content, genre);

		hub.addElement(s);

		Iterator<AudioElement> it = hub.elements();
		while (it.hasNext()) {
			if (it.next().getTitle().equals(title)) {
				flag = true;
			}
		}
		Assert.assertTrue(flag);
	}

	@Test
	public void createAlbumTest() {

		String aTitle = "Album Title Test";

		String aArtist = "Album Artist";

		int aLength = 500;

		boolean flag = false;
		String aDate = "2020-12-12";
		Album a = new Album(aTitle, aArtist, aLength, aDate);

		hub.addAlbum(a);

		Iterator<Album> it = hub.albums();

		while (it.hasNext()) {
			if (it.next().getTitle().equals(aTitle)) {
				flag = true;
			}
		}
		Assert.assertTrue(flag);

	}

	@Test
	public void createPlayListTest() {

		String playListTitle = "PlayList 1";
		boolean flag = true;

		PlayList pl = new PlayList(playListTitle);

		hub.addPlaylist(pl);

		
		String title = "Test Song";

		String genre = "hiphop";

		String artist = "Eminem";

		

		int length = 500;

		String content = "E:\\Ashish\\Songs\\Pachtaoge.mp3";

		Song s = new Song(title, artist, length, content, genre);

		hub.addElement(s);
		
		try {
			hub.addElementToPlayList(title, playListTitle);
		} catch (NoPlayListFoundException ex) {
			flag = false;
		} catch (NoElementFoundException ex) {
			flag = false;
		}

		Assert.assertTrue(flag);

	}
	
	

}
