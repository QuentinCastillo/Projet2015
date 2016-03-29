package fr.univavignon.courbes.stats;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.swing.table.AbstractTableModel;

import fr.univavignon.courbes.common.Profile;
import fr.univavignon.courbes.inter.simpleimpl.profiles.ProfileManager;

/**
 * Modèle associé à la table affichant la liste des stats
 * (i.e. objet chargé de gérer le contenu la table concernée).
 * 
 * @author	Mary L3 Info UAPV 2015-16
 */
public class Stats extends AbstractTableModel
{
	/** Données */
	private List<List<String>> rowdata;
	
	/** En-têtes */
	private String[] columnNames;

	/**
	 * Constructeur :Initialise le modèle de table.
	 */
	public Stats()
	{
		rowdata = new ArrayList<List<String>>();

		// on définit les titres des colonnes
		columnNames = new String[6];
		columnNames[0] = "Rang ELO";
		columnNames[1] = "Nom";
		columnNames[2] = "Nombre de parties jouées";
		columnNames[3] = "Nombre de parties gagnées";
		columnNames[4] = "Nombre de points dans une partie";
		columnNames[5] = "Choix";
		
		// on définit le contenu de la table
		TreeSet<Profile> profiles = ProfileManager.getProfiles();
		for (Profile profile : profiles)
		{	List<String> row = new ArrayList<String>();
			row.add(Integer.toString(profile.eloRank));
			row.add(profile.userName);
			row.add(Integer.toString(profile.gameCount));
			row.add(Integer.toString(profile.gameWon));
			row.add(Integer.toString(profile.pointByGame));
			rowdata.add(row);
		}
	}
	
	/**
	 * Rajoute les stats d'un joueur dans la table.
	 * 
	 * @param profile
	 * 		Le profil à rajouter.
	 */
	public void addStat(Profile profile)
	{	ArrayList<String> newRow = new ArrayList<String>();
		newRow.add(Integer.toString(profile.eloRank));
		newRow.add(profile.userName);
		newRow.add(Integer.toString(profile.gameCount));
		newRow.add(Integer.toString(profile.gameWon));
		rowdata.add(newRow);
		fireTableRowsInserted(rowdata.size()-1, rowdata.size()-1);
	}
	
	@Override
	public int getColumnCount() 
	{
		if (rowdata != null && rowdata.size() > 0)
			return rowdata.get(0).size();
		else
			return 0;
	}

	@Override
	public int getRowCount() {
		return rowdata.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) 
	{
		return rowdata.get(rowIndex).get(columnIndex);
	}
	
	public String getColumnName(int c)
	{	return columnNames[c];
	}
	
}