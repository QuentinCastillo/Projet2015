package fr.univavignon.courbes.network.central;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.univavignon.courbes.common.Profile;
import fr.univavignon.courbes.inter.simpleimpl.profiles.ProfileManager;

/**
 * @author gael
 * Cette classe gère la communication avec la base de données située sur
 * l'espace personnel uapv1402562
 */
public class DatabaseCommunication {
	/**
	 * The connection object that bonds the program to the database
	 */
	static Connection conn;
  /**
 * Cette fonction connecte le programme à la base de données
 */
public static void connect_db()
  {
    try {
     Class.forName("org.postgresql.Driver");

     String url = "pedago02a.univ-avignon.fr";
     String user = "uapv1402562";
     String passwd = "M6V4rS";

     Connection connect = DriverManager.getConnection(url, user, passwd);
     System.out.println("Connexion effective !");
     conn = connect;
    }

    catch(Exception e)
    {
    	e.printStackTrace();
    }

  }

  /**
 * @param name player's name
 * @param pwd player's password
 * @param country player's country
 * @param email player's email
 * @throws SQLException //TODO: handle this 
 */
public static void insert_new_player(String name, String pwd, String country, String email) throws SQLException
  {
	String query = "INSERT INTO ?(name, pwd, email, country) values(?,?,?,?) RETURNING playerid;";
	  PreparedStatement state;


	  state = conn.prepareStatement(query);
	  state.setString(1, "player");
	  state.setString(2, name);
	  state.setString(3, pwd);
	  state.setString(4, email);
	  state.setString(5, country);

	  ResultSet result = state.executeQuery();

	  int playerId = result.getInt("playerid");

		Profile new_profile = new Profile();

		query = "INSERT INTO ?(id, _date, value) values(?,?,?);";
		state = conn.prepareStatement(query);

		state.setString(1, "elo");
		state.setInt(2, playerId);
		state.setTimestamp(3, getCurrentTimeStamp());
		state.setInt(4, new_profile.eloRank);
		state.executeQuery();

		String[] tables = {"gamecount", "gamewon", "pointbygame", "pointbyround", "roundcount"};
	  for(String s : tables)
	  {
		  state = conn.prepareStatement(query);

		  state.setString(1, s);
		  state.setInt(2, playerId);
		  state.setTimestamp(3, getCurrentTimeStamp());
		  state.setInt(4, 0);
		  state.executeQuery();
	  }

		new_profile.profileId = playerId;
		new_profile.password = pwd;
		new_profile.gameCount = 0;
		new_profile.gameWon = 0;
		new_profile.pointByGame = 0;
		new_profile.pointByRound = 0;
		new_profile.roundCount = 0;
		
		ProfileManager.addProfile(new_profile);
		

  }

/**
 * @return The current Time
 */
private static java.sql.Timestamp getCurrentTimeStamp() {

	java.util.Date today = new java.util.Date();
	return new java.sql.Timestamp(today.getTime());

}

/**
 * Removes a profile from the database;
 * @param profile The profile to remove
 * @throws SQLException 
 */
public static void removeProfile(Profile profile) throws SQLException {
	String query = "DELETE FROM player WHERE id = " + profile.profileId;
	PreparedStatement state = conn.prepareStatement(query);
	state.executeQuery();
	
}

}
