package fr.univavignon.courbes.network.central;

import java.sql.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import fr.univavignon.courbes.network.central.PlayerConnection;

public class PublicGameConnection {
	public static void createPublicGame()
    {
        String query="Insert into publicGame(IP, nb_joueurs, nb_joueurs_max) values(?, 1, 6)";
        PreparedStatement prepare;
        Connection connection = PlayerConnection.connect();
        InetAddress Ip=null;
        try
        {
        	Ip = InetAddress.getLocalHost();
        }
        catch(UnknownHostException e)
        {
        	e.printStackTrace();
        }
        if(connection != null)
        {
            try {
                prepare = connection.prepareStatement(query);
                prepare.setString(1, Ip.getHostAddress());
                prepare.executeQuery();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
            }
        }
    }
	
	public static void joinPublicGame()
	{
		Connection connection = PlayerConnection.connect();
		String query="Select * from partie where nb_players<nb_max_players";
		if(connection != null) {
            try {
                PreparedStatement prepare = connection.prepareStatement(query);
                ResultSet result = prepare.executeQuery();
                while(result.next())
        		{
        			
        		}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
            }
        }
	}
}
