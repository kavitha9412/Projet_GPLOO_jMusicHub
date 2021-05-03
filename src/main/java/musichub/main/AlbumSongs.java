package musichub.main;

import java.io.Serializable;

public class AlbumSongs implements Serializable {

	// Création de variables pour contenir les chansons de l'album

	private String title;
	private String content;
	private String artist;



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	@Override
	public String toString() {
		return "AlbumSongs [title=" + title + ", content=" + content + ", artist=" + artist + "]";
	}

}
