package fr.univavignon.courbes.network.central;

import java.sql.*;

import fr.univavignon.courbes.common.Profile;

/** Connection d'un joueur */
public class PlayerConnection {
	/** 
	 * Connection à la base de donnée.
	 * @return connection*/
	public static Connection connect()
    {
      try {
    	  String url = "jdbc:postgresql://pedago02a.univ-avignon.fr/etd";
    	     String user = "uapv1402562";
    	     String passwd = "M6V4rS";

    	     Connection connect = DriverManager.getConnection(url, user, passwd);
       return connect;
      }
      
      catch(Exception e)
      {
          e.printStackTrace();
      }
      return null;
    }
    
    public static boolean playerConnect(String user, String pswd)
    {
          String query="Select * from player where name=? and pwd=? ";
          Connection connection=connect();
          if(connection != null) {
              try {
                  PreparedStatement prepare = connection.prepareStatement(query);
                  prepare.setString(1,user);
                  prepare.setString(2,pswd);
                  ResultSet result = prepare.executeQuery();
                  if(result.next())
                  {
                      return true;
                  }
              } catch (SQLException e) {
                  // TODO Auto-generated catch block
              }
          }
          return false;
    }
    
    public static boolean addPlayer(Profile profil)
    {
          String query="Insert into player_waiting(id,name,country,pswd)";
          query+=" values(default,?,?,?) RETURNING id";
          PreparedStatement prepare;
          Connection connection = connect();
          if(connection != null)
          try {
              prepare = connection.prepareStatement(query);
              prepare.setString(1,profil.userName);
              prepare.setString(2,profil.country);
              prepare.setString(3,profil.password);
              ResultSet result = prepare.executeQuery();
              if(result.next())
              {
                  profil.profileId = result.getInt(1);    
                  return true;
              }
              prepare.close();
              
          } catch (SQLException e) {
              // TODO Auto-generated catch block
          }
          return false;
      }
}
