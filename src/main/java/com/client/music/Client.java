package com.client.music;

import org.apache.log4j.Logger;

public class Client {

	private static Logger logger = Logger.getLogger(Client.class);

	// Client main class
	public static void main(String[] args) {

		try {

			// Création de l'objet ReadMusicHub en fournissant localhost comme ip et
			// 6666 comme port
			ReadMusicHub rmh = new ReadMusicHub("localhost", 6666);

			// Appel de la méthode readOptions() de la classe ReadMusicHUb
			rmh.readOptions();
		} catch (Exception e) {
			logger.error("Error -{}", e);
		}
	}
}