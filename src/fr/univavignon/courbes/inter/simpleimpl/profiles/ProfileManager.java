package fr.univavignon.courbes.inter.simpleimpl.profiles;

/*
 * Courbes
 * Copyright 2015-16 L3 Info UAPV 2015-16
 *
 * This file is part of Courbes.
 *
 * Courbes is free software: you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation,
 * either version 2 of the License, or (at your option) any later version.
 *
 * Courbes is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Courbes. If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

import fr.univavignon.courbes.common.Profile;
import fr.univavignon.courbes.network.central.DatabaseCommunication;

/**
 * Classe chargée de charger, gérer et enregistrer les profils.
 * On utilise pour cela une base de données.
 *
 * @author	L3 Info UAPV 2015-16
 */
public class ProfileManager
{
	/** Liste des profils */
	private static final TreeSet<Profile> PROFILES = new TreeSet<Profile>();
<<<<<<< HEAD

=======
	/** Nombre de champs à lire par profil */
	private static final int PROFILE_FIELD_NBR = 6;
	
>>>>>>> refs/remotes/origin/master
	/**
	 * Renvoie la liste de tous profils disponibles. La méthode charge
	 * cette liste si ce n'est pas déjà fait.
	 *
	 * @return
	 * 		Liste des profils disponibles.
	 */
	public static TreeSet<Profile> getProfiles()
	{	if(PROFILES.isEmpty())
			loadProfiles();
		return PROFILES;
	}

	/**
	 * Ajoute un profil à la liste. Aucune vérification n'est effectuée
	 * sur les noms ou emails des utilisateurs. La liste modifiée est
	 * enregistrée.
	 *
	 * @param profile
	 * 		Utilisateur à rajouter.
	 */
	public static void addProfile(Profile profile)
<<<<<<< HEAD
	{	Profile mx = Collections.max(PROFILES);
		profile.profileId = mx.profileId + 1;
		profile.changed = true;
=======
	{	if(PROFILES.isEmpty())
			profile.profileId = 0;
		else
		{	Profile mx = Collections.max(PROFILES);
			profile.profileId = mx.profileId + 1;
		}
		
>>>>>>> refs/remotes/origin/master
		PROFILES.add(profile);
		recordProfiles();
	}

	/**
	 * Supprime un profil de la liste, et enregistre.
	 *
	 * @param profile
	 * 		Le profil à supprimer.
	 */
	public static void removeProfile(Profile profile)
	{
		PROFILES.remove(profile);
		try {
			DatabaseCommunication.removeProfile(profile);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Enregistre la liste des profils dans la base de données
	 * Grâce à la fonction setProfile(Profile) de la classe DatabaseCommunication
	 */
	private static void recordProfiles()
<<<<<<< HEAD
	{
		for(Profile profile: PROFILES)
		{
			if(profile.changed)
			{
				try {
					DatabaseCommunication.setProfile(profile);
					profile.changed = false;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
=======
	{	try
		{	FileOutputStream fos = new FileOutputStream(PROFILE_FILE);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			PrintWriter writer = new PrintWriter(osw);
			
			for(Profile profile: PROFILES)
			{	writer.write
				(	profile.userName + SEPARATOR + 
					profile.country + SEPARATOR +
					profile.eloRank + SEPARATOR +
					profile.email + SEPARATOR +
					profile.password + SEPARATOR +
					profile.agent + "\n"
				);
>>>>>>> refs/remotes/origin/master
			}
		}

	}

	/**
	 * Charge la liste des profils à partir d'un fichier
	 * texte structuré comme expliqué pour {@link #recordProfiles()}.
	 */
	private static void loadProfiles()
<<<<<<< HEAD
	{
		int profile_number = 0;
		try {
			profile_number = DatabaseCommunication.getProfileNumber();
		} catch (SQLException e1) {
			e1.printStackTrace();
=======
	{	try
		{	// on ouvre le fichier en lecture
			FileInputStream fis = new FileInputStream(PROFILE_FILE);
			InputStreamReader isr = new InputStreamReader(fis);
			Scanner scanner = new Scanner(isr);
			
			int profileId = 0;
			// on en lit chaque ligne
			while(scanner.hasNext())
			{	String line = scanner.nextLine();
				String elem[] = line.split(SEPARATOR);
				if(elem.length == PROFILE_FIELD_NBR)
				{	// on crée le profil et on l'initialise
					Profile profile = new Profile();
					profile.profileId = profileId;
					profile.userName = elem[0].trim();
					profile.country = elem[1].trim();
					profile.eloRank = Integer.parseInt(elem[2].trim());
					profile.email = elem[3].trim();
					profile.password = elem[4].trim();
					profile.agent = elem[5].trim();
					if(profile.agent.equals("null"))
						profile.agent = null;
					PROFILES.add(profile);
				}
				else
					System.err.println("Erreur à la ligne "+(profileId+1)+" : elle contient " + elem.length + " éléments au lieu des "+PROFILE_FIELD_NBR+" attendus");
				
				profileId++;
			}
			
			// on ferme le fichier
			scanner.close();  
>>>>>>> refs/remotes/origin/master
		}

	for (int profileId = 0; profileId < profile_number; profileId++) {
		try {
			Profile profile;
			profile = DatabaseCommunication.getProfile(profileId);
			profile.changed = false;
			PROFILES.add(profile);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	}

	/**
	 * Cherche le nom spécifié parmi les utilisateurs de la liste
	 * et renvoie {@code true} s'il est trouvé.
	 *
	 * @param userName
	 * 		Nom recherché.
	 * @return
	 * 		{@code true} ssi le nom est déjà utilisé.
	 */
	public static boolean containsUserName(String userName)
	{	boolean found = false;
		Iterator<Profile> it = PROFILES.iterator();
		while(!found && it.hasNext())
		{	Profile profile = it.next();
			found = userName.equalsIgnoreCase(profile.userName);
		}
		return found;
	}

	/**
	 * Renvoie le profil associé au numéro de profil spécifié,
	 * ou {@code null} si aucun profil ne correspond.
	 *
	 * @param profileId
	 * 		Numéro du profil désiré.
	 * @return
	 * 		Le profil associé à ce numéro.
	 */
	public static Profile getProfile(int profileId)
	{	Profile result = null;
		Iterator<Profile> it = PROFILES.iterator();
		while(result==null && it.hasNext())
		{	Profile profile = it.next();
			if(profile.profileId==profileId)
				result = profile;
		}
		return result;
	}
}
