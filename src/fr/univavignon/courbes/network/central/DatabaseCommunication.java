package fr.univavignon.courbes.network.central;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

/**
 * Udpates in the database the given Profile
 * @param profile the profile to update
 * @throws SQLException
 */
public static void setProfile(Profile profile) throws SQLException {
	//On modifie la table player
	String query = "UPDATE player SET (name, pwd, email, country) = (?,?,?,?)";
	PreparedStatement state = conn.prepareStatement(query);
	state.setString(1, profile.userName);
	state.setString(2, profile.password);
	state.setString(3, profile.email);
	state.setString(4, profile.country);
	state.executeQuery();

	String[] tables = {"elo", "gamecount", "gamewon", "pointbygame", "pointbyround", "roundcount"};
	int[] values = {profile.eloRank, profile.gameCount, profile.gameWon, profile.pointByGame, profile.pointByRound, profile.roundCount};

	//On insère de nouvelles valeurs dans les autres tables
	for(int i = 0; i < tables.length; i++)
	  {
		  query = "INSERT INTO ?(playerid, date, value) VALUES(?,?,?)";

		  state = conn.prepareStatement(query);

		  state.setString(1, tables[i]);
		  state.setInt(2, profile.profileId);
		  state.setTimestamp(3, getCurrentTimeStamp());
		  state.setInt(4, values[i]);
		  state.executeQuery();
	  }

}

/**
 * @param playerid
 * @return
 * @throws SQLException
 */
public static Profile getProfile(int playerid) throws SQLException
{
	String query = "SELECT ?,?,?,? FROM player  WHERE id = playerid";
	  PreparedStatement state;

	  state = conn.prepareStatement(query);

	  state.setString(1,"name");
	  state.setString(2,"pwd");
	  state.setString(3,"email");
	  state.setString(4,"country");

	  ResultSet result = state.executeQuery();

	  Profile profile = new Profile();

	  profile.userName = result.getString("name");
	  profile.password = result.getString("pwd");
	  profile.email = result.getString("email");
	  profile.country = result.getString("country");

	return profile;
}

/**
 *
 * @return The number of profile
 * @throws SQLException
 */
public static int getProfileNumber() throws SQLException
{
	PreparedStatement state = conn.prepareStatement("SELECT count(id) FROM player;");
	ResultSet result = state.executeQuery();

    int count = result.getInt("count(id)");


    return count;
}

}
