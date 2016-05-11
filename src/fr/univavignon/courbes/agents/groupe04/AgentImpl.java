package fr.univavignon.courbes.agents.groupe04;

import java.util.Set;
import java.util.TreeSet;

import fr.univavignon.courbes.agents.Agent;
import fr.univavignon.courbes.common.Board;
import fr.univavignon.courbes.common.Direction;
import fr.univavignon.courbes.common.ItemType;
import fr.univavignon.courbes.common.Position;
import fr.univavignon.courbes.common.Snake;

public class AgentImpl extends Agent
{
	
	/** Moitié de l'angle de vision de l'agent, i.e. délimitant la zone traitée devant lui pour détecter des obstacles. Contrainte : doit être inférieure à PI */
	private static double ANGLE_WIDTH = Math.PI/2;
	/** Distance en pixels à partir de laquelle on considère qu'on est dans un coin */
	private static int CORNER_THRESHOLD = 100;
	
	/** Direction de l'agent*/
	private static Direction direction_agent = Direction.NONE;
	
	/** Direction précédemment choisie par cet agent */
	private Direction previousDirection = Direction.NONE;
	
	/** Serpent contrôlé par l'agent */
	private Snake agentSnake;


	/** Direction courante du serpent de l'agent */
	private double currentAngle;
	/** Borne inférieure de l'angle de vision de l'agent */
	private double lowerBound;
	/** Borne supérieure de l'angle de vision de l'agent */
	private double upperBound;
	
	public AgentImpl(Integer playerId) 
	{
		super(playerId);
	}
		
	@Override
	public Direction processDirection() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Direction aggression()
	{
		Direction toTake = Direction.NONE;
		//checker les scores, recup le serpent qui a le plus haut score
		//recup la position de ce serpent + prevision
		//on fonce dessus : return RIGHT ou LEFT
		return toTake;
	}
	
}