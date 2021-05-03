package musichub.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javazoom.jl.player.advanced.PlaybackListener;
import musichub.business.Album;
import musichub.business.PlayList;
import musichub.business.Song;

public class SendDataToClient implements Serializable {

	// Créer des variables pour contenir différentes données
	private String albumsTitlesSortedByDate;
	private String audiobooksTitlesSortedByAuthor;
	private AlbumSongs albumSongs;
	List<AlbumSongs> songs = new ArrayList<AlbumSongs>();
	List<AlbumSongs> listOfSongs = new ArrayList<>();
	List<PlayList> listOfPlayLists = new ArrayList<>();

	

	
	
	public List<AlbumSongs> getSongs() {
		return songs;
	}

	public List<PlayList> getListOfPlayLists() {
		return listOfPlayLists;
	}

	public void setListOfPlayLists(List<PlayList> listOfPlayLists) {
		this.listOfPlayLists.addAll(listOfPlayLists);
	}

	public List<AlbumSongs> getListOfSongs() {
		return listOfSongs;
	}

	public void setListOfSongs(List<AlbumSongs> listOfSongs) {
		this.listOfSongs.addAll(listOfSongs);
	}

	public void setSongs(List<AlbumSongs> songs) {
		this.songs.addAll(songs);
	}

	public AlbumSongs getAlbumSongs() {
		return albumSongs;
	}

	public void setAlbumSongs(AlbumSongs albumSongs) {
		this.albumSongs = albumSongs;
	}

	public String getAlbumsTitlesSortedByDate() {
		return albumsTitlesSortedByDate;
	}

	public void setAlbumsTitlesSortedByDate(String albumsTitlesSortedByDate) {
		this.albumsTitlesSortedByDate = albumsTitlesSortedByDate;
	}

	public String getAudiobooksTitlesSortedByAuthor() {
		return audiobooksTitlesSortedByAuthor;
	}

	public void setAudiobooksTitlesSortedByAuthor(String audiobooksTitlesSortedByAuthor) {
		this.audiobooksTitlesSortedByAuthor = audiobooksTitlesSortedByAuthor;
	}

}
