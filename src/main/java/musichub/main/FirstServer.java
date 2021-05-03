package musichub.main;

import java.io.*;
import java.net.*;

import org.apache.log4j.Logger;

import musichub.business.MusicHub;
import musichub.business.PlayList;

public class FirstServer extends AbstractServer {
	
	private static Logger logger = Logger.getLogger(PlayList.class);
	private String ip = "localhost";
	private ServerSocket ss;

	@Override
	public void connect(String ip, MusicHub musicHub) {
		try {
			// socket du serveur défini uniquement par un port (son IP est localhost)
			ss = new ServerSocket(6666);

			// Cette boucle sera exécutée jusqu'à ce que le programme ne soit pas fermé.
			while (true) {

				// Établissement d'une connexion socket pour accepter les demandes
				Socket socket = ss.accept();
				// créer un nouveau thread pour gérer le socket client
				new ServerThread(socket, musicHub).start();
			}
		} catch (IOException ioe) {
			logger.error("Error- {}",ioe);
			// si IOException, fermer le socket du serveur
			if (ss != null && !ss.isClosed()) {
				try {
					ss.close();
				} catch (IOException e) {
					logger.error("Error- {}",e);
				}
			}
		}
	}

}