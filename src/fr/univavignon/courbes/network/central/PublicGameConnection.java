package fr.univavignon.courbes.network.central;

import java.sql.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import fr.univavignon.courbes.network.central.PlayerConnection;
import fr.univavignon.courbes.inter.simpleimpl.SettingsManager;

public class PublicGameConnection {
	
	public static InetAddress Ip=null;
	
	public static void createPublicGame()
    {
        String query="Insert into partie(IP, nb_joueurs, nb_joueurs_max) values(?, 1, 6)";
        PreparedStatement prepare;
        Connection connection = PlayerConnection.connect();
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
                prepare.executeUpdate();
                prepare.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
            }
        }
    }
	
	public static boolean joinPublicGame()
	{
		Connection connection = PlayerConnection.connect();
		String query="Select ip from partie where nb_players<nb_max_players";
		if(connection != null) {
			while(true)
			{
	            try {
	                PreparedStatement prepare = connection.prepareStatement(query);
	                ResultSet result = prepare.executeQuery();
	                if(result.next())
	        		{
	        			SettingsManager.setLastServerIp(Ip.getHostAddress());
	        		}
	            } catch (SQLException e) {
	                // TODO Auto-generated catch block
	            }
	            if(SettingsManager.getLastServerIp()!="localhost")
	            {
	            	return true;
	            }
			}
        }
		else
		{
			return false;
		}
	}
}
