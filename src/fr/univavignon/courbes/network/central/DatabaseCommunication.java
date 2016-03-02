package fr.univavignon.courbes.network.central;

import java.sql.Connection;
import java.sql.DriverManager;

class DatabaseCommunication {
  public static void connect()
  {
    try {
     Class.forName("org.postgresql.Driver");

     String url = "pedago02a.univ-avignon.fr";
     String user = "uapv1402562";
     String passwd = "M6V4rS";

     Connection conn = DriverManager.getConnection(url, user, passwd);
     System.out.println("Connexion effective !");
    }
    
    catch(Exception e)
    {
    	e.printStackTrace();
    }
  }
}
