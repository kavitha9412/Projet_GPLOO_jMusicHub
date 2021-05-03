package musichub.main;

import java.io.*;
import java.net.*;

import musichub.business.MusicHub;

public class ServerConnection implements Runnable {

	// Création d'un porte-objet pour la classe MusicHub
	private MusicHub musicHub = null;

	// Création du constructeur
	public ServerConnection(MusicHub musicHub) {
		// Initialisation de l'objet MusicHub
		this.musicHub = musicHub;
	}

	// La méthode run du thread. Elle est appelée lorsque la méthode start() est lancéé.
	@Override
	public void run() {

		// Création d'un objet pour la classe FirstServer
		AbstractServer as = new FirstServer();

		// Configurer l'ip du socket comme localhost
		String ip = "localhost";

		// Appel de la méthode de connexion
		as.connect(ip, musicHub);
	}
}