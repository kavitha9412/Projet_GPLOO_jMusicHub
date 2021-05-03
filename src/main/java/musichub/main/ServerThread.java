package musichub.main;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import musichub.business.AudioElement;
import musichub.business.MusicHub;
import musichub.business.NoAlbumFoundException;

/**
 * Ce thread est responsable de la connexion du client.
 */
public class ServerThread extends Thread {
	
	private static Logger logger = Logger.getLogger(ServerThread.class);

	// Déclarer des variables
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private MusicHub musicHub;

	// Création du constructeur
	public ServerThread(Socket socket, MusicHub musicHub) {
		this.socket = socket;
		this.musicHub = musicHub;
	}

	// run() method
	public void run() {
		try {
			// créer les flux qui traiteront les objets arrivant par les sockets
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());

			// lire l'objet reçu par le flux 
			String text = (String) input.readObject();
			
			// Création de l'objet de SendDataToClient
			SendDataToClient sendDataToClient = new SendDataToClient();

			// Tableau
			String[] texts = text.split("~");

			
			switch (texts[0]) {
			case "ALBUM":
				// Si l'entrée du client est Album
				sendDataToClient.setAlbumsTitlesSortedByDate(musicHub.getAlbumsTitlesSortedByDate());
				break;
			case "AUDIO":
				// Si l'entrée du client est Audio
				sendDataToClient.setAudiobooksTitlesSortedByAuthor(musicHub.getAudiobooksTitlesSortedByAuthor());
				break;
			
			case "ALBUMLIST":
				sendDataToClient.setListOfSongs(musicHub.getListOfSongs());
				break;
			case "PLAYLIST":
				sendDataToClient.setListOfPlayLists(musicHub.getPlayListAsList());
				break;
			case "SONGS":
				//  Si l'entrée du client est Songs
				String albumTitle = texts[1];
				List<AlbumSongs> songs = new ArrayList<AlbumSongs>();
				List<AudioElement> audioElements;
				try {

					// Obtenir les chansons associées à l'album
					audioElements = musicHub.getAlbumSongs(albumTitle);

					// boucle de chaque élément audio
					for (AudioElement audioElement : audioElements) {

						// Création d'un objet de AlbumSongs
						AlbumSongs song = new AlbumSongs();

						// Configurer les propriétés de la chanson
						song.setArtist(audioElement.getArtist());
						song.setContent(audioElement.getContent());
						song.setTitle(audioElement.getTitle());

						// Ajout d'une chanson à la liste des chansons
						songs.add(song);
					}

					// Configuration des chansons dans l'objet SendDataToClient
					sendDataToClient.setSongs(songs);
				} catch (NoAlbumFoundException e) {
					logger.error("Error - {}",e);
				}

				break;
			}

			output.writeObject(sendDataToClient);

			// Traitement des exceptions
		} catch (IOException ex) {
			
			logger.error("Error - {}",ex);

		} catch (ClassNotFoundException ex) {
			
			logger.error("Error - {}",ex);
		} finally {
			// Fermeture des cours d'eau
			try {
				output.close();
				input.close();
			} catch (IOException ioe) {
				logger.error("Error - {}",ioe);
			}
		}
	}
}