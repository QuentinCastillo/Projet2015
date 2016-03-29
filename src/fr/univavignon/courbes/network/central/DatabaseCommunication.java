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
 * @author gael et mary
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
    //DriverManager.registerDriver(new org.postgresql.Driver());
     //jdbc:postgresql:ProductDB&user=root&password=secret
     String url = "jdbc:postgresql://pedago02a.univ-avignon.fr/etd";
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
 * @return
 * @throws SQLException //TODO: handle this
 */
public static Profile insert_new_player(String name, String pwd, String country, String email) throws SQLException
  {
	String query = "INSERT INTO player(name, pwd, email, country) values(cast(? as VARCHAR(50)),cast(? as VARCHAR(50)),cast(? as VARCHAR(30)),cast(? as VARCHAR(20))) RETURNING id";
	  PreparedStatement state;


	  state = conn.prepareStatement(query);
	  state.setString(1, name);
	  state.setString(2, pwd);
	  state.setString(3, email);
	  state.setString(4, country);

	  ResultSet result = state.executeQuery();
	  result.next();
	  int playerId = result.getInt("id");

	  result.close();
	  state.close();

		Profile new_profile = new Profile();

		query = "INSERT INTO elo(id, _date, value) values(cast(? as integer),cast(? as Timestamp),cast(? as integer))";
		state = conn.prepareStatement(query);

		state.setInt(1, playerId);
		state.setTimestamp(2, getCurrentTimeStamp());
		state.setInt(3, new_profile.eloRank);
		state.executeUpdate();
		state.close();

		String[] tables = {"gamecount", "gamewon", "pointbygame", "pointbyround", "roundcount"};
	  for(String s : tables)
	  {
		  query = "INSERT INTO " + s + "(id, _date, value) values(cast(? as integer),cast(? as Timestamp),cast(? as integer))";
		  state = conn.prepareStatement(query);

		  state.setInt(1, playerId);
		  state.setTimestamp(2, getCurrentTimeStamp());
		  state.setInt(3, 0);
		  state.executeUpdate();
		  state.close();
	  }

		new_profile.profileId = playerId;
		new_profile.password = pwd;
		new_profile.gameCount = 0;
		new_profile.gameWon = 0;
		new_profile.pointByGame = 0;
		new_profile.pointByRound = 0;
		new_profile.roundCount = 0;

		ProfileManager.addProfile(new_profile);

		return new_profile;

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
	// 1 : d'abord on recupere les info du profile
	String query = "SELECT * FROM player  WHERE id = cast(? as integer)";
	  PreparedStatement state;

	  state = conn.prepareStatement(query);

	  state.setInt(1, playerid);

	  ResultSet result = state.executeQuery();

	  Profile profile = new Profile();
	  profile.profileId = playerid;
	  
	  if(result.next())
	  {
	  profile.userName = result.getString("name");
	  profile.password = result.getString("pwd");
	  profile.email = result.getString("email");
	  profile.country = result.getString("country");
	  }
	  
	  else
		  return null;
	  result.close();
	  
	  //2) Ensuite on recupere le rang ELO
	 /* String query2 = "SELECT TOP 1 ? FROM ? WHERE id = playerid ORDER BY date DESC";
	  PreparedStatement state2;
	  state2 = conn.prepareStatement(query2);
	  state2.setString(1,"value");
	  state2.setString(2,"elo");
	  ResultSet result2 = state2.executeQuery();

	  profile.eloRank = result2.getInt("value");

	  //3) Le nombre de partie jouées
	  String query3 = "SELECT TOP 1 ? FROM ? WHERE id = playerid ORDER BY date DESC";
	  PreparedStatement state3;
	  state3 = conn.prepareStatement(query2);
	  state3.setString(1,"value");
	  state3.setString(2,"gameCount");
	  ResultSet result3 = state3.executeQuery();

	  profile.gameCount = result3.getInt("value");

	  // Nombre de parties gagnées
	  String query4 = "SELECT TOP 1 ? FROM ? WHERE id = playerid ORDER BY date DESC";
	  PreparedStatement state4;
	  state4 = conn.prepareStatement(query2);
	  state4.setString(1,"value");
	  state4.setString(2,"gameWon");
	  ResultSet result4 = state4.executeQuery();

	  profile.gameWon = result2.getInt("value");

	  //roundCount
	  String query5 = "SELECT TOP 1 ? FROM ? WHERE id = playerid ORDER BY date DESC";
	  PreparedStatement state5;
	  state5 = conn.prepareStatement(query2);
	  state5.setString(1,"value");
	  state5.setString(2,"roundCount");
	  ResultSet result5 = state5.executeQuery();

	  profile.roundCount = result5.getInt("value");

	  //pointByRound
	  String query6 = "SELECT TOP 1 ? FROM ? WHERE id = playerid ORDER BY date DESC";
	  PreparedStatement state6;
	  state6 = conn.prepareStatement(query2);
	  state6.setString(1,"value");
	  state6.setString(2,"pointByRound");
	  ResultSet result6 = state6.executeQuery();

	  profile.pointByRound = result6.getInt("value");

	  //pointByGame
	  String query7 = "SELECT TOP 1 ? FROM ? WHERE id = playerid ORDER BY date DESC";
	  PreparedStatement state7;
	  state7 = conn.prepareStatement(query2);
	  state7.setString(1,"value");
	  state7.setString(2,"pointByGame");
	  ResultSet result7 = state7.executeQuery();

	  profile.pointByGame = result7.getInt("value");
	  */
	return profile;
}

/**
 *
 * @return The number of profile
 * @throws SQLException
 */
public static int getProfileNumber() throws SQLException
{
	PreparedStatement state = conn.prepareStatement("SELECT count(id) AS count FROM player;");
	ResultSet result = state.executeQuery();
	result.next();
    int count = result.getInt("count");


    return count;
}

}
