package com.client.music;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import javazoom.jl.decoder.JavaLayerException;
import musichub.business.Album;
import musichub.business.MusicHub;
import musichub.business.PlayList;
import musichub.main.AlbumSongs;
import musichub.main.SendDataToClient;

public class ReadMusicHub {

	private static Logger logger = Logger.getLogger(ReadMusicHub.class);
	// Variables pour l'IP et le port
	String ip;
	int port;

	// Variable pour la liste des chansons
	List<AlbumSongs> songs;

	// Constructeur avec ip et port comme paramètres
	public ReadMusicHub(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	// méthode readOptions pour lire l'entrée de l'utilisateur
	public void readOptions() {

		System.out.println("Type h for available commands");

		// Objet scanné pour obtenir l'entrée de l'utilisateur

		Scanner scan = new Scanner(System.in);

		// Obtenir les entrées
		String choice = scan.nextLine();

		// Vérifier si l'entrée de l'utilisateur est nulle
		if (choice.length() == 0)
			System.exit(0);

		// Condition pour vérifier la commande d'entrée de l'utilisateur
		// Boucle à exécuter jusqu'à ce que l'utilisateur entre la touche q
		while (choice.charAt(0) != 'q') {
			switch (choice.charAt(0)) {

			// Quand la commande de l'utilisateur est h
			case 'h':
				printAvailableCommands();
				choice = scan.nextLine();
				break;

			// Quand la commande de l'utilisateur est t
			case 't':
				// titres des albums, classés par date
				System.out.println(getAlbumsTitlesSortedByDate());
				printAvailableCommands();
				choice = scan.nextLine();
				break;

			// Quand la commande de l'utilisateur est d
			case 'd':
				System.out.println(getAlbumsTitlesSortedByDate());

				String albumTitle = scan.nextLine();
				System.out.println(getSongs(albumTitle));

				printAvailableCommands();
				choice = scan.nextLine();
				break;

			// Lorsque la commande de l'utilisateur est x
			case 'x':

				songs = getListOfSongs();

				for (AlbumSongs song : songs) {
					System.out.println(song.getTitle());
				}

				System.out.println("Put Title To Play Song !");
				String title = scan.nextLine();

				playSong("", title);
				printAvailableCommands();
				choice = scan.nextLine();
				break;

			// Quand la commande de l'utilisateur est o
			case 'o':

				songs = getListOfSongs();

				for (AlbumSongs song : songs) {
					System.out.println(song.getTitle());
				}

				printAvailableCommands();
				choice = scan.nextLine();
				break;

			// Lorsque la commande de l'utilisateur est i
			case 'i':

				List<PlayList> listOfPlayList = getPlayLists();

				for (PlayList playlist : listOfPlayList) {
					System.out.println(playlist.getTitle());
				}

				printAvailableCommands();
				choice = scan.nextLine();
				break;

			default:

				break;

			}

		}

	}

	private List<PlayList> getPlayLists() {
		// Création d'une instance de MusicClient
		MusicClient mc = MusicClient.getInstance();
		mc.connectSocket(ip, port);

		//titre
		mc.writeObjectOutput("PLAYLIST~");

		List<PlayList> list = new ArrayList<>();

		try {

			// Lecture de l'objet
			SendDataToClient sendDataToClient = (SendDataToClient) mc.readObjectInput().readObject();

			// récupérer les chansons reçues du serveur
			list = sendDataToClient.getListOfPlayLists();
			return list;
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Error - {}", e);
		}
		return list;

	}

	private List<AlbumSongs> getListOfSongs() {
		
		MusicClient mc = MusicClient.getInstance();
		mc.connectSocket(ip, port);

		
		mc.writeObjectOutput("ALBUMLIST~");

		List<AlbumSongs> listOfSongs = new ArrayList<>();

		try {

			
			SendDataToClient sendDataToClient = (SendDataToClient) mc.readObjectInput().readObject();

			
			listOfSongs = sendDataToClient.getListOfSongs();
			return listOfSongs;
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Error - {}", e);
		}
		return listOfSongs;

	}

	// La méthode pour jouer la chanson
	private void playSong(String albumTitle, String songTitle) {

		
		for (AlbumSongs song : songs) {

			
			if (song.getTitle().equals(songTitle)) {

				// Création de l'objet AudioPlayer
				AudioPlayer ap = new AudioPlayer();
				try {
					
					ap.play(song.getContent());
				} catch (JavaLayerException e) {
					logger.error("Error - {}", e);
				}

			} else {
			}
		}
	}

	// La méthode pour obtenir les chansons de l'album (meme commentaires que pour les 2 getList precédentes)
	private String getSongs(String albumTitle) {

		
		MusicClient mc = MusicClient.getInstance();
		mc.connectSocket(ip, port);

		
		mc.writeObjectOutput("SONGS~" + albumTitle);

		try {

			
			SendDataToClient sendDataToClient = (SendDataToClient) mc.readObjectInput().readObject();

			
			songs = sendDataToClient.getSongs();
			return songs.toString();
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Error - {}", e);
		}

		return "";
	}

	// Get les titres des albums
	public String getAlbumsTitlesSortedByDate() {

		// Instance de MusicClient
		MusicClient mc = MusicClient.getInstance();
		mc.connectSocket(ip, port);
		mc.writeObjectOutput("ALBUM");

		try {

			// Récupérer la liste des albums depuis le serveur
			SendDataToClient sendDataToClient = (SendDataToClient) mc.readObjectInput().readObject();
			return sendDataToClient.getAlbumsTitlesSortedByDate();
		} catch (ClassNotFoundException | IOException e) {
			logger.error("Error - {}", e);
		}

		return "";
	}

	private static void printAvailableCommands() {

		System.out.println();
		System.out.println("t: Display the albums");
		System.out.println("i: Display playlists");
		System.out.println("o: Display the list of songs");
		System.out.println("d: Display songs of an album");
		System.out.println("x: Play a song");

		System.out.println("q: quit program");
	}

}
