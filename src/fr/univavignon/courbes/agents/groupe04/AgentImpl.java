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
		
	
	public void checkObjectEffect()
	{ 
		checkInterruption();	// on doit tester l'interruption au début de chaque méthode
		
		ItemType item = null ;			// item tmp "affecté"  au snake
		
		if(agentSnake.currentItems != null) /*s'il a pris un (ou plusieurs?) item*/
		{
			if(item == ItemType.USER_FAST) /*si c'est USER_FAST*/
			{
				// alors on modif l'angle pour tourner	
				currentAngle = agentSnake.currentAngle  * ANGLE_WIDTH;
				// augmente la vigilance
				// on change CORNER_THRESHOLD?
			}
			else if(item == ItemType.USER_SLOW) /*si c'est USER_SLOW*/
			{
				// alors on modif l'angle pour tourner	
				currentAngle = agentSnake.currentAngle / ANGLE_WIDTH;
				
				// augmente la vigilance
				// on change CORNER_THRESHOLD?
			}
			else if (item == ItemType.COLLECTIVE_TRAVERSE)/*si y a plus de bordure COLLECTIVE_TRAVERSE*/
			{
				// pas de modif sur les angles, mais les conditions sur les bordures disparaissent
				// utilisation de la var bool hasBorder;
			}
		}
		else if (agentSnake.fly)
		{
			// calcule l'endroit le plus safe et on y vaaaaaaa
				
		}
		else if(agentSnake.inversion)
		{
				/*Direction result = Direction.NONE;
				if(result == Direction.LEFT) result = Direction.RIGHT;
				else result = Direction.LEFT;*/
				
				if(direction_agent == Direction.LEFT) direction_agent = Direction.RIGHT;
				else direction_agent = Direction.LEFT;
		}
					
	}


	
}