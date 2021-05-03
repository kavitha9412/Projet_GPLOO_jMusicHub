package com.client.music;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.log4j.Logger;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class AudioPlayer {
	
	private static Logger logger = Logger.getLogger(AudioPlayer.class);

	// Taille pour lire/écrire le flux audio.
	private static final int BUFFER_SIZE = 4096;
	
	
	

	/**
	 * Joue un fichier audio 
	 * 
	 * @param audioFilePath Chemin du fichier audio
	 */
	public void play(String audioFilePath) throws JavaLayerException {

		FileInputStream fileInputStream;
		try {
			// Création du flux d'entrée pour contenir le fichier audio
			fileInputStream = new FileInputStream(audioFilePath);

			// Création de l'objet Player en utilisant le flux d'entrée créé
			Player player = new Player((fileInputStream));

			// Play
			player.play();
			
			System.out.println("Please enter v to stop playing the song");

			while (true) {
				
				System.out.println(player.getPosition());
			}
		} catch (FileNotFoundException e) {
			logger.error("Error -{}",e);
		}

	}
}
