package com.app;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.server.ServerCloneException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.client.music.AudioPlayer;
import com.client.music.MusicClient;
import com.client.music.ReadMusicHub;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import junit.framework.Assert;
import musichub.business.AudioElement;
import musichub.business.MusicHub;
import musichub.business.NoAlbumFoundException;
import musichub.main.AlbumSongs;
import musichub.main.SendDataToClient;

public class ClientTest {

	private MusicHub hub;

	@Before
	public void initializeResources() {

		ServerTest server = new ServerTest();
		server.initializeResource();
		server.createSongTest();
		server.createAlbumTest();
		server.createPlayListTest();

		hub = server.hub;

	}

	@Test
	public void displaySongsTest() {

		boolean flag = true;

		String albumTitle = "Album Title Test";
		try {
			List<AudioElement> result = hub.getAlbumSongs(albumTitle);

		} catch (NoAlbumFoundException e) {
			flag = false;
		}

		Assert.assertTrue(flag);

	}

	@Test
	public void displayAlbumsTest() {

		boolean flag = true;

		String result = hub.getAlbumsTitlesSortedByDate();

		if (result.length() == 0) {
			flag = false;
		}

		Assert.assertTrue(flag);

	}

	@Test
	public void displayPlaylistsTest() {
		boolean flag = true;

		String result = hub.getPlayLists();

		if (result.length() == 0) {
			flag = false;
		}

		Assert.assertTrue(flag);
	}

	@Test
	public void playSongTest() {

		boolean flag = true;

		try {
			for (AudioElement song : hub.getAlbumSongs("Album Title Test")) {

				if (song.getTitle().equals("Test Song")) {

					try {
						FileInputStream fileInputStream;

						fileInputStream = new FileInputStream(song.getContent());

						Player player = new Player((fileInputStream));

						player.play();
					} catch (JavaLayerException e1) {
						flag = false;

					} catch (FileNotFoundException e2) {
						flag = false;
					}

				}
			}
		} catch (NoAlbumFoundException e) {
			flag = false;
		}
		Assert.assertTrue(flag);
	}

}
