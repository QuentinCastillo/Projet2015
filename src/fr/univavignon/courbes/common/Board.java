package fr.univavignon.courbes.common;

/*
 * Courbes
 * Copyright 2015-16 L3 Info UAPV 2015-16
 * 
 * This file is part of Courbes.
 * 
 * Courbes is free software: you can redistribute it and/or modify it under the terms 
 * of the GNU General Public License as published by the Free Software Foundation, 
 * either version 2 of the License, or (at your option) any later version.
 * 
 * Courbes is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
 * PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Courbes. If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe correspond à l'ensemble des informations propres à 
 * l'aire de jeu utilisée pendant une manche.
 * <br/>
 * Il faut bien distinguer la notion de partie et de manche. Les joueurs
 * sont confrontés lors d'une parties se déroulant sur plusieurs manches
 * distinctes. À chaque, chaque joueur marque un certain nombre de points.
 * Un joueur gagne la partie quand son score dépasse une certaine valeur
 * limite. 
 * 
 * @author	L3 Info UAPV 2015-16
 */
public class Board implements Serializable
{	/** Numéro de série (pour {@code Serializable}) */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Initialise une aire de jeu vide.
	 */
	public Board()
	{	// rien à faire
	}
	
	/**
	 * Initialise une nouvelle aire de jeu en recopiant
	 * celle passée en paramètre. On en a besoin pour des
	 * raisons de synchronisation.
	 * 
	 * @param board
	 * 		L'aire de jeu à recopier.
	 */
	public Board(Board board)
	{	this.state = board.state;
		this.hasBorder = board.hasBorder;
		
		this.snakes = new Snake[board.snakes.length];
		for(int i=0;i<snakes.length;i++)
		{	Snake snake = board.snakes[i];
			Snake copy = new Snake(snake);
			this.snakes[i] = copy;
		}
		
		this.items = new ArrayList<ItemInstance>();
		for(ItemInstance item: board.items)
		{	ItemInstance copy = new ItemInstance(item);
			this.items.add(copy);
		}
	}
	
	/** Indique la phase du jeu : présentation, entrée ou normal */
	public State state;
	
	/**
	 * Représente l'état de la partie : présentation des joueurs,
	 * entrée des joueurs, ou jeu normal.
	 * 
	 * @author	L3 Info UAPV 2015-16
	 */
	public enum State
	{	/** Les serpents ne bougent pas, des flèches indiquent leur direction */
		PRESENTATION,
		/** Les serpents bougent, mais ne laissent pas de trainée */
		ENTRANCE,
		/** Le jeu se déroule normalement */
		REGULAR;
	}
	
	/** Indique si l'aire de jeu contient actuellement une bordure ou si celle-ci est absente */
	public boolean hasBorder;
	
	/** Tableau contenant tous les serpents de la manche, placés dans l'ordre des ID des joueurs correspondants */
	public Snake snakes[];
	
	/** Position des items sur l'aire de jeu: associe la position du <i>centre</i> d'un item à la valeur de cet item */
	public List<ItemInstance> items;
}
