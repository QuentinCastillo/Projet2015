package fr.univavignon.courbes.inter.central;

	import javax.swing.JOptionPane;
	import javax.swing.SwingUtilities;

	import fr.univavignon.courbes.inter.ClientConnectionHandler;
	import fr.univavignon.courbes.inter.simpleimpl.AbstractConfigurationPanel;
	import fr.univavignon.courbes.inter.simpleimpl.MainWindow;
	import fr.univavignon.courbes.inter.simpleimpl.SettingsManager;
	import fr.univavignon.courbes.inter.simpleimpl.MainWindow.PanelName;
	import fr.univavignon.courbes.network.ClientCommunication;
	import fr.univavignon.courbes.network.central.NetworkToCentral;
	import fr.univavignon.courbes.network.simpleimpl.client.ClientCommunicationImpl;
	
public class ClientCentralWaitPanel {
	

	public class ClientCentralWaitPanel extends AbstractConfigurationPanel implements ClientServeurSelection , ClientConnectionHandler 
	{
		private static final  String TITLE = "Recherche d'un Serveur" ;
		private int idGame;
		private String ipServer;
		private String portServer;
		private Thread whatserver;
		public boolean continuer =true;
		
		public ClientServerSelectionPanel(MainWindow mainWindow) 
		{
			super(mainWindow, TITLE);
			nextButton.setEnabled(false);
			whatserver= new Thread(new WhatServerConnect(this));
			whatserver.start();
		}
		private boolean connect()
		{	// on initialise le Moteur Réseau
			ClientCommunication clientCom = new ClientCommunicationImpl();
			mainWindow.clientCom = clientCom;
			clientCom.setErrorHandler(mainWindow);
			clientCom.setConnectionHandler((ClientConnectionHandler) this);
			
			String ipStr = getIpServer();
			clientCom.setIp(ipStr);
			SettingsManager.setLastServerIp(ipStr);
			
			String portStr = getPortServer();
			int port = Integer.parseInt(portStr);
			clientCom.setPort(port);
			SettingsManager.setLastServerPort(port);
			
			// puis on se connecte
			boolean result = clientCom.launchClient();
			return result;
		}
		@Override
		protected void initContent() {
			// TODO Auto-generated method stub
		}

		@Override
		public synchronized void nextStep() {
	boolean connected = connect();
			
			if(connected)
			{	// on désactive les boutons le temps de l'attente
				backButton.setEnabled(false);
				nextButton.setEnabled(false);
			
				// puis on se contente d'attendre la réponse : acceptation ou rejet
				// la méthode correspondante du handler sera automatiquement invoquée
			}
			
			else
			{	
			NetworkToCentral.DeletePartie(getIdPartie());
			}
			// TODO Auto-generated method stub
			
		}

		@Override
		protected void previousStep() {
			// TODO Auto-generated method stub
			continuer = false;
			mainWindow.clientCom = null;
			mainWindow.displayPanel(PanelName.CLIENT_GAME_PLAYER_SELECTION);
		}
		@Override
		public String getIpServer() {
			// TODO Auto-generated method stub
			return ipServer;
		}
		@Override
		public String getPortServer() {
			// TODO Auto-generated method stub
			return portServer;
		}
		@Override
		public void setPortServer(String portServer) {
			// TODO Auto-generated method stub
			this.portServer = portServer;
		}
		@Override
		public void setIpServer(String ipServer) {
			// TODO Auto-generated method stub
			this.ipServer = ipServer;
		}
		@Override
		public void gotRefused()
		{	SwingUtilities.invokeLater(new Runnable()
			{	@Override
				public void run()
				{	JOptionPane.showMessageDialog(mainWindow, 
						"<html>Le serveur a rejeté votre candidature, car il ne reste "
						+ "<br/>pas de place dans la partie en cours de configuration.</html>");
				//Suppresion d'un serveur qui n'est plus en ligne
				}
		    });
		}
		
		@Override
		public void gotAccepted()
		{	SwingUtilities.invokeLater(new Runnable()
			{	@Override
				public void run()
				{	continuer = false;
					mainWindow.clientCom.setConnectionHandler(null);
					mainWindow.displayPanel(PanelName.CLIENT_GAME_WAIT);
				}
		    });
		}
		@Override
		public int getIdPartie() {
			// TODO Auto-generated method stub
			return idPartie;
		}
		@Override
		public void setIdPartie(int Idpartie) {
			// TODO Auto-generated method stub
			this.idPartie=Idpartie;
		}

		
	}
}
