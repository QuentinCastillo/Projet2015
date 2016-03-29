package fr.univavignon.courbes.stats;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import fr.univavignon.courbes.inter.simpleimpl.MainWindow;

/**
 * @author gael
 *
 */
public class GraphPanel extends JPanel {

	/** Fenêtre contenant ce panel */
	private MainWindow mainWindow;
	/** Table affichée par ce panel */
	private JTable statTable;
	/** Scrollpane contenu dans ce panel pour afficher la table */
	private JScrollPane scrollPane;
	/** Bouton pour revenir au menu principal */
	private JButton backButton;
	
	/**
	 * @param mainWindow
	 */
	public GraphPanel(MainWindow mainWindow) {
		super();
		this.mainWindow = mainWindow;

		init();
	}

	private void init() {
		
		StatGraph myCHart = new StatGraph();
	}

}
