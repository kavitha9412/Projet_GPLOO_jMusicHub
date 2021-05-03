package musichub.main;

import musichub.business.MusicHub;

// Une interface ayant une méthode de connexion
public abstract class AbstractServer
{
	public abstract void connect(String ip,MusicHub musicHub);
} 