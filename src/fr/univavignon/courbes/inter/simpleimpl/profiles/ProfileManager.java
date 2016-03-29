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
	/** Nombre de champs à lire par profil */
	private static final int PROFILE_FIELD_NBR = 5;

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
	{	Profile mx = Collections.max(PROFILES);
		profile.profileId = mx.profileId + 1;
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
		DatabaseCommunication.removeProfile(profile);
		recordProfiles();
	}

	/**
	 * Enregistre la liste des profils dans la base de données
	 * Grâce à la fonction xx de la classe DatabaseCommunication
	 */
	private static void recordProfiles()
	{
		// //TODO: Figure out how to do this with mary
		// for(Profile profile: PROFILES)
		// 		(	profile.userName + SEPARATOR +
		// 			profile.country + SEPARATOR +
		// 			profile.eloRank + SEPARATOR +
		// 			profile.email + SEPARATOR +
		// 			profile.partyNumber + SEPARATOR +
		// 			profile.partyWon + SEPARATOR +
		// 			profile.password + "\n"
		// 		);
	}

	/**
	 * Charge la liste des profils à partir d'un fichier
	 * texte structuré comme expliqué pour {@link #recordProfiles()}.
	 */
	private static void loadProfiles()
	{	try
		{
			int profile_number = DatabaseCommunication.getProfileNumber();

			for (int profileId = 0; profileId < profile_number; profileId++) {
				Profile profile = DatabaseCommunication.getProfile(profileId);
				profile.profileId = profileId;
				profile.userName = elem[0].trim();
				profile.country = elem[1].trim();
				profile.eloRank = Integer.parseInt(elem[2].trim());
				profile.email = elem[3].trim();
				profile.password = elem[4].trim();
				PROFILES.add(profile);
			}

		}
		
		catch (IOException e)
		{	e.printStackTrace();
		}
	}

	/**
	 * Cherche le noms spécifié parmi les utilisateurs de la liste
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
		return false;
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
