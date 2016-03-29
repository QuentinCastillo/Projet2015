package fr.univavignon.courbes.stats;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import fr.univavignon.courbes.common.Profile;
import fr.univavignon.courbes.inter.simpleimpl.MainWindow;
import fr.univavignon.courbes.inter.simpleimpl.MainWindow.PanelName;
import fr.univavignon.courbes.inter.simpleimpl.profiles.ProfileManager;
import fr.univavignon.courbes.inter.simpleimpl.profiles.ProfileTableModel;
import fr.univavignon.courbes.inter.simpleimpl.SettingsManager;


/*
* Panel destiné à afficher la liste des profils existants.
* 
* @author	Mary L3 Info UAPV 2015-16
*/
public class StatListPanel extends JPanel implements ActionListener
{	/** Numéro de série de la classe */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Crée un nouveau panel destiné à afficher la liste des profils.
	 * 
	 * @param mainWindow
	 * 		Fenêtre principale contenant ce panel.
	 */
	public StatListPanel(MainWindow mainWindow)
	{	super();
		this.mainWindow = mainWindow;
		
		init();
	}
	
	/** Fenêtre contenant ce panel */
	private MainWindow mainWindow;
	/** Table affichée par ce panel */
	private JTable statTable;
	/** Scrollpane contenu dans ce panel pour afficher la table */
	private JScrollPane scrollPane; 
	/** Bouton pour revenir au menu principal */
	private JButton backButton;

	
	/**
	 * Méthode principale d'initialisation du panel.
	 */
	private void init()
	{	BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);
	
		initTablePanel();
		
		add(Box.createVerticalGlue());

		initButtonsPanel();
	}
	
	/**
	 * Initialisation de la table affichée par ce panel.
	 */
	private void initTablePanel()
	{	statTable = new JTable();
		statTable.setAutoCreateRowSorter(true);
		
		statTable.setModel(new Stats());
		
		scrollPane = new JScrollPane
		(	statTable,
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
		);
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		Dimension frameDim = mainWindow.getPreferredSize();
		int boardHeight = SettingsManager.getBoardHeight();
		Dimension dim = new Dimension(frameDim.width,(int)(boardHeight*0.8));
		scrollPane.setPreferredSize(dim);
		scrollPane.setMaximumSize(dim);
		scrollPane.setMinimumSize(dim);
		add(scrollPane);
	}

	
	/**
	 * Initialisation des boutons contenus dans ce panel.
	 */
	private void initButtonsPanel()
	{	JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.LINE_AXIS);
		panel.setLayout(layout);
		
		backButton = new JButton("Retour");
		backButton.addActionListener(this);
		panel.add(backButton);
		
		panel.add(Box.createHorizontalGlue());
		
		add(panel);
	}
	
	public void actionPerformed(ActionEvent e)
	{	if(e.getSource()==backButton)
		mainWindow.displayPanel(PanelName.MAIN_MENU);
	}
}