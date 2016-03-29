package fr.univavignon.courbes.network.central;

import java.sql.*;
import fr.univavignon.courbes.network.central.PlayerConnection;
import fr.univavignon.courbes.network.ServerCommunication;

public class PublicGameConnection {
	
	public static void createPublicGame()
	{
		String query="Insert into publicGame(IP, nb_joueurs, nb_joueurs_max) values(?, 1, 6)";
		PreparedStatement prepare;
	    Connection connection = connect();
	    if(connection != null)
		    try {
				prepare = connection.prepareStatement(query);
				prepare.setString(1,getIp());
				prepare.executeQuery();
		    } catch (SQLException e) {
				// TODO Auto-generated catch block
			}
	    	
	}
}
