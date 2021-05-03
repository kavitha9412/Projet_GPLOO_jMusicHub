hpackage musichub.main;

import musichub.business.*;
import java.util.*;

import org.apache.log4j.Logger;

import com.client.music.AudioPlayer;
import com.client.music.ObjectFactory;

import javazoom.jl.decoder.JavaLayerException;

public class Server {

	private static Logger logger = Logger.getLogger(Server.class);

	public static void main(String[] args) {

		// Création de l'objet de la classe MusicHub
		MusicHub theHub = ObjectFactory.getMusicHUb();

		// Création d'un nouveau thread pour la classe ServerConnection
		// Et ensuite on commence l'exécution du thread
		Thread socketThread = new Thread(new ServerConnection(theHub));
		socketThread.start();

		System.out.println("Type h for available commands");

		// Scanner pour recevoir les données de l'utilisateur.
		Scanner scan = new Scanner(System.in);
		String choice = scan.nextLine();

		String albumTitle = null;

		// Si l'utilisateur n'a rien saisi et a appuyé sur la touche Entrée
		if (choice.length() == 0)
			System.exit(0);

		// Boucle infini jusqu'à ce que l'utilisateur entre la touche 'q'
		while (choice.charAt(0) != 'q') {

			// Vérification de la toiche d'entrée de l'utilisateur
			switch (choice.charAt(0)) {

			// si l'utilisateur a entré la touche h
			case 'h':
				// Liste des commandes
				printAvailableCommands();

				// Obtenir la commande suivante de l'utilisateur
				choice = scan.nextLine();
				break;
			case 't':
				// titres des albums, classés par date
				System.out.println(theHub.getAlbumsTitlesSortedByDate());
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			/*
			 * case 'g': // chansons d'un album, triées par genre System.out.println(
			 * "Les chansons d'un album triées par genre seront affichées ; entrez le nom de l'album, les albums disponibles sont :"
			 * ); System.out.println(theHub.getAlbumsTitlesSortedByDate());
			 * 
			 * albumTitle = scan.nextLine(); try {
			 * System.out.println(theHub.getAlbumSongsSortedByGenre(albumTitle)); } catch
			 * (NoAlbumFoundException ex) {
			 * System.out.println("Aucun album trouvé avec le titre demandé " +
			 * ex.getMessage()); } printAvailableCommands(); choice = scan.nextLine();
			 * break;
			 */
			case 'd':
				// songs of an album
				System.out.println("The songs of an album will be displayed, enter the name of the album, the available albums are :");
				System.out.println(theHub.getAlbumsTitlesSortedByDate());

				albumTitle = scan.nextLine();
				try {
					System.out.println(theHub.getAlbumSongs(albumTitle));
				} catch (NoAlbumFoundException ex) {
					System.out.println("No albums found with the requested title " + ex.getMessage());
				}
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			/*
			 * case 'u': // livres audio classés par auteur
			 * System.out.println(theHub.getAudiobooksTitlesSortedByAuthor());
			 * printAvailableCommands(); choice = scan.nextLine(); break;
			 */
			case 'c':
				// add a new song
				System.out.println("Enter a new song: ");
				System.out.println("Song title: ");
				String title = scan.nextLine();
				System.out.println("Song genre (jazz, classic, hiphop, rock, pop, rap):");
				String genre = scan.nextLine();
				System.out.println("Song artist: ");
				String artist = scan.nextLine();
				System.out.println("Song length in seconds: ");
				int length = Integer.parseInt(scan.nextLine());
				System.out.println("Song content: ");
				String content = scan.nextLine();
				Song s = new Song(title, artist, length, content, genre);
				theHub.addElement(s);
				System.out.println("New element list: ");
				Iterator<AudioElement> it = theHub.elements();
				while (it.hasNext())
					System.out.println(it.next().getTitle());
				System.out.println("Song created!");
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case 'a':
				// add a new album
				System.out.println("Enter a new album: ");
				System.out.println("Album title: ");
				String aTitle = scan.nextLine();
				System.out.println("Album artist: ");
				String aArtist = scan.nextLine();
				System.out.println("Album length in seconds: ");
				int aLength = Integer.parseInt(scan.nextLine());
				System.out.println("Album date as YYYY-DD-MM: ");
				String aDate = scan.nextLine();
				Album a = new Album(aTitle, aArtist, aLength, aDate);
				theHub.addAlbum(a);
				System.out.println("New list of albums: ");
				Iterator<Album> ita = theHub.albums();
				while (ita.hasNext())
					System.out.println(ita.next().getTitle());
				System.out.println("Album created!");
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			case '+':
				// add a song to an album:
				System.out.println("Add an existing song to an existing album");
				System.out.println("Type the name of the song you wish to add. Available songs: ");
				Iterator<AudioElement> itae = theHub.elements();
				while (itae.hasNext()) {
					AudioElement ae = itae.next();
					if (ae instanceof Song)
						System.out.println(ae.getTitle());
				}
				String songTitle = scan.nextLine();

				System.out.println("Type the name of the album you wish to enrich. Available albums: ");
				Iterator<Album> ait = theHub.albums();
				while (ait.hasNext()) {
					Album al = ait.next();
					System.out.println(al.getTitle());
				}
				String titleAlbum = scan.nextLine();
				try {
					theHub.addElementToAlbum(songTitle, titleAlbum);
				} catch (NoAlbumFoundException ex) {
					System.out.println(ex.getMessage());
				} catch (NoElementFoundException ex) {
					System.out.println(ex.getMessage());
				}
				System.out.println("Song added to the album!");
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			/*
			 * case 'l': // add a new audiobook
			 * System.out.println("Enter a new audiobook: ");
			 * System.out.println("AudioBook title: "); String bTitle = scan.nextLine();
			 * System.out.
			 * println("AudioBook category (youth, novel, theater, documentary, speech)");
			 * String bCategory = scan.nextLine(); System.out.println("AudioBook artist: ");
			 * String bArtist = scan.nextLine();
			 * System.out.println("AudioBook length in seconds: "); int bLength =
			 * Integer.parseInt(scan.nextLine()); System.out.println("AudioBook content: ");
			 * String bContent = scan.nextLine(); System.out.
			 * println("AudioBook language (french, english, italian, spanish, german)");
			 * String bLanguage = scan.nextLine(); AudioBook b = new AudioBook(bTitle,
			 * bArtist, bLength, bContent, bLanguage, bCategory); theHub.addElement(b);
			 * System.out.println("Audiobook created! New element list: ");
			 * Iterator<AudioElement> itl = theHub.elements(); while (itl.hasNext())
			 * System.out.println(itl.next().getTitle()); printAvailableCommands(); choice =
			 * scan.nextLine(); break;
			 */
			case 'p':
				// create a new playlist from existing elements
				System.out.println("Add an existing song to a new playlist");
				System.out.println("Existing playlists:");
				Iterator<PlayList> itpl = theHub.playlists();
				while (itpl.hasNext()) {
					PlayList pl = itpl.next();
					System.out.println(pl.getTitle());
				}
				System.out.println("Type the name of the playlist you wish to create:");
				String playListTitle = scan.nextLine();
				PlayList pl = new PlayList(playListTitle);
				theHub.addPlaylist(pl);
				System.out.println("Available elements: ");

				Iterator<AudioElement> itael = theHub.elements();
				while (itael.hasNext()) {
					AudioElement ae = itael.next();
					System.out.println(ae.getTitle());
				}
				while (choice.charAt(0) != 'n') {
					System.out.println("Type the name of the audio element you wish to add:");
					String elementTitle = scan.nextLine();
					try {
						theHub.addElementToPlayList(elementTitle, playListTitle);
					} catch (NoPlayListFoundException ex) {
						System.out.println(ex.getMessage());
					} catch (NoElementFoundException ex) {
						System.out.println(ex.getMessage());
					}

					System.out.println("Type y to add a new one, n to end");
					choice = scan.nextLine();
				}
				System.out.println("Playlist created!");
				printAvailableCommands();
				choice = scan.nextLine();
				break;
			/*
			 * case '-': // delete a playlist
			 * System.out.println("Delete an existing playlist. Available playlists:");
			 * Iterator<PlayList> itp = theHub.playlists(); while (itp.hasNext()) { PlayList
			 * p = itp.next(); System.out.println(p.getTitle()); } String plTitle =
			 * scan.nextLine(); try { theHub.deletePlayList(plTitle); } catch
			 * (NoPlayListFoundException ex) { logger.error("Error - {}",ex); }
			 * System.out.println("Playlist deleted!"); printAvailableCommands(); choice =
			 * scan.nextLine(); break; case 's': // save elements, albums, playlists
			 * theHub.saveElements(); theHub.saveAlbums(); theHub.savePlayLists();
			 * System.out.println("Elements, albums and playlists saved!");
			 * printAvailableCommands(); choice = scan.nextLine(); break;
			 */
			case 'i':
				System.out.println("Available Playlists:");
				Iterator<PlayList> itp = theHub.playlists();

				while (itp.hasNext()) {
					PlayList p = itp.next();

					System.out.println(p.getTitle());

				}
				printAvailableCommands();
				choice = scan.nextLine();
				break;

			case 'o':
				System.out.println("Available Songs:");

				Iterator<AudioElement> itae1 = theHub.elements();
				while (itae1.hasNext()) {
					AudioElement ae = itae1.next();
					if (ae instanceof Song)
						System.out.println(ae.getTitle());
				}
				printAvailableCommands();
				choice = scan.nextLine();
				break;

			case 'x':
				try {
					System.out.println("Available Songs:");

					AudioPlayer ap = new AudioPlayer();

					List<AlbumSongs> albumSongs = new ArrayList<>();

					Iterator<AudioElement> itae11 = theHub.elements();
					while (itae11.hasNext()) {
						AudioElement ae = itae11.next();
						if (ae instanceof Song)
							System.out.println(ae.getTitle());

						AlbumSongs songs = new AlbumSongs();
						songs.setArtist(ae.getArtist());
						songs.setContent(ae.getContent());
						songs.setTitle(ae.getTitle());

						albumSongs.add(songs);
					}

					System.out.println();
					System.out.println("Enter song title to play it");
					choice = scan.nextLine();

					for (AlbumSongs song : albumSongs) {
						ap.play(song.getContent());
					}

					printAvailableCommands();
					choice = scan.nextLine();
				} catch (JavaLayerException e) {
					e.printStackTrace();
				}
				break;

			default:
				System.out.println("Command not valid");

				printAvailableCommands();
				choice = scan.nextLine();
				break;
			}
		}
		scan.close();
	}

	
	private static void printAvailableCommands() {
		System.out.println();
		System.out.println("t: Display the albums");
		System.out.println("i: Display playlists");
		System.out.println("o: Display the list of songs");
		System.out.println("d: Display songs of an album");
		System.out.println("x: Play a song");

		System.out.println("c: add a new song");
		System.out.println("a: add a new album");
		System.out.println("p: create a new playlist from existing songs");
		System.out.println("+: add a song to an album");

		System.out.println("q: quit program");
	}
}