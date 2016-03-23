package fr.univavignon.courbes.network.central;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * @author gael
 * Cette classe gère la communication avec la base de données située sur
 * l'espace personnel uapv1402562
 */
class DatabaseCommunication {
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

  public static void insert_player(String name, String pwd)
  {
	  String query = "INSERT INTO ?(name, pwd) values(?,?) RETURNING playerid";
	  PreparedStatement state; 
	  
	  state = conn.prepareStatement(query);
	  state.setString(1, "player");
	  state.setString(2, name);
	  state.setString(3, pwd);
	  
	  ResultSet result = state.executeQuery();
	  
	  int playerId = result.getInt("playerid");
	  
	  String[] tables = {"elo", "gamecount", "gamewon", "pointbygame", "pointbyround", "roundcount"};
	  
	  query = "INSERT INTO ?(id, _date, value) values(?,?,?) ";
	  for(String s : tables)
	  {
		  state = conn.prepareStatement(query);
		  
		  state.setString(1, s);
		  state.setInt(2, playerId);
		  state.setTimestamp(3, x);
		  state.setInt(4, 0);
	  }
	  
  }
}
