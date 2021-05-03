package com.client.music;

import musichub.business.MusicHub;

public class ObjectFactory {

	// class ObjectFactory pour fournir des objets
	private static ObjectFactory objectFactory;

	private static MusicHub musicHub;

	private ObjectFactory() {

	}

	// Faire de la class objectFactory un singleton afin qu'un seul objet puisse être
	// créé
	public static synchronized ObjectFactory getInstance() {

		// Vérifier s'il n'est pas nul et retourner le même objet
		if (objectFactory != null)
			return objectFactory;

		// Sinon, créer un nouvel objet
		else {
			objectFactory = new ObjectFactory();
			return objectFactory;
		}
	}

	// Méthode pour fournir l'objet DataProcessingThread
	public static MusicHub getMusicHUb() {

		return new MusicHub();

	}

	// methode pour clear les objets
	public void clear() {
		objectFactory = null;
	}
}
