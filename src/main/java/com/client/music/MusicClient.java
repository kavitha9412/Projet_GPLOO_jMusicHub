package com.client.music;

import java.io.*;
import java.net.*;

import org.apache.log4j.Logger;

public class MusicClient {
	
	private static Logger logger = Logger.getLogger(AudioPlayer.class);
	
	private static MusicClient musicClient;
	
	private MusicClient() {
		
	}

	public static synchronized MusicClient getMusicClient() {
		return musicClient;
	}
	// Faire de la classe objectFactory un singleton afin de ne créer qu'un seul objet
	// créé
	public static MusicClient getInstance() {
		if(musicClient!=null) {
			return musicClient;
		}else {
			musicClient = new MusicClient();
			return musicClient;
		}
		
		
	}

	// Création de variables
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket socket;

	// Méthode ConnectSocket avec IP et PORT
	public void connectSocket(String ip, int port) {
		try {
			// Création du socket pour l'ip et le port
			socket = new Socket(ip, port);
		} catch (IOException e) {
			logger.error("Error - {}",e);
		}
	}

	// writeObjectOutput pour écrire des objets
	public void writeObjectOutput(String text) {
		try {
			// Création du flux et écriture des objets
			output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject(text);
		} catch (IOException e) {

			logger.error("Error - {}",e);
		}

	}

	// readObjectInput pour lire les objets
	public ObjectInputStream readObjectInput() {
		try {
			// Récupérer le flux d'entrée

			input = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {

			logger.error("Error - {}",e);
		}
		return input;
	}

	// la méthode relaseConnection pour fermer les flux et le socket
	public void releaseConnection() {
		try {
			input.close();
			output.close();
			socket.close();
		} catch (IOException e) {
			logger.error("Error - {}",e);
		}

	}

}