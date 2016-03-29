package fr.univavignon.courbes.stats;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.swing.JCheckBox;
import javax.swing.table.AbstractTableModel;

import fr.univavignon.courbes.common.Profile;
import fr.univavignon.courbes.inter.simpleimpl.profiles.ProfileManager;
import fr.univavignon.courbes.network.central.DatabaseCommunication;

/**
 * Modèle associé à la table affichant la liste des stats
 * (i.e. objet chargé de gérer le contenu la table concernée).
 *
 * @author	Mary L3 Info UAPV 2015-16
 */
public class Stats extends AbstractTableModel
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Données */
	private List<List<Object>> rowdata;

	/** En-têtes */
	private String[] columnNames;

	/**
	 * Constructeur :Initialise le modèle de table.
	 */
	public Stats()
	{
		rowdata = new ArrayList<List<Object>>();

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
		int profileCount = DatabaseCommunication.getProfileNumber();
		JCheckBox[] checkBoxList = new JCheckBox[profileCount];
		int i = 0;
		for (Profile profile : profiles)
		{	List<Object> row = new ArrayList<Object>();
			row.add(Integer.toString(profile.eloRank));
			row.add(profile.userName);
			row.add(Integer.toString(profile.gameCount));
			row.add(Integer.toString(profile.gameWon));
			row.add(Integer.toString(profile.pointByGame));
			checkBoxList[i] = new JCheckBox("player" + profile.profileId);
			row.add(checkBoxList[i]);
			rowdata.add(row);
			i++;
		}
	}

	/**
	 * Rajoute les stats d'un joueur dans la table.
	 *
	 * @param profile
	 * 		Le profil à rajouter.
	 */
	public void addStat(Profile profile)
	{	ArrayList<Object> newRow = new ArrayList<Object>();
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

	@Override
	public String getColumnName(int c)
	{	return columnNames[c];
	}

}
