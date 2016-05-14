package fr.univavignon.courbes.agents.groupe04;

import java.util.Set;
import java.util.TreeSet;

import fr.univavignon.courbes.agents.Agent;
import fr.univavignon.courbes.common.Board;
import fr.univavignon.courbes.common.Direction;
import fr.univavignon.courbes.common.ItemType;
import fr.univavignon.courbes.common.Player;
import fr.univavignon.courbes.common.Position;
import fr.univavignon.courbes.common.Round;
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
	
	/**
	 * Teste si un angle donné est compris dans l'angle de vision d'un agent.
	 * 
	 * @param angle 
	 * 		L'angle testé (entre 0 et 2PI).
	 * @return 
	 * 		{@code true} ssi l'angle est visible par le serpent.
	 * 		 fait par le prof
	 */
	private boolean isInSight(double angle)
	{	checkInterruption();	// on doit tester l'interruption au début de chaque méthode
		boolean result = false;
		
		if(angle>=lowerBound && angle<=upperBound)
			result = true;

		// premier cas limite : si la borne supérieure dépasse 2PI
		// on teste si l'angle est inférieur à upperBound - 2pi.
		else if(upperBound>2*Math.PI && angle<=upperBound-2*Math.PI)
			result = true;
			
		// second cas limite : si la borne inférieure est négative 
		// on teste si l'angle est supérieur à lowerBound + 2PI
		else if(lowerBound<0 && angle>=lowerBound+2*Math.PI)
			result = true;
			
		return result;
	}
	
	/**
	 * Check le serpent le proche et fonce dessus
	 * 
	 * @return Direction toTake : direction que va prendre l'agent
	 */
	public Direction aggression()
	{
		checkInterruption();
		
		Direction toTake = Direction.NONE;
		
		//recup la position de ce serpent + prevision
		double result = closestSnake();
		
		// si le serpent se trouve entre lowerBound et currentAngle
		// alors on va à gauche
		if(result>=lowerBound && result<=currentAngle)
			toTake = Direction.LEFT;
		
		// si le serpent se trouve entre currentAngle et upperAngle
		// alors on va à droite
		else if(result >=currentAngle && result<=upperBound)
			toTake = Direction.RIGHT;
		
		// premier cas limite : si la borne supérieure dépasse 2PI
		// on teste si l'angle est inférieur à upperBound - 2pi
		else if(upperBound>2*Math.PI && result<=upperBound-2*Math.PI)
			toTake = Direction.RIGHT;
				
		// second cas limite : si la borne inférieure est négative
		// on teste si l'angle est supérieur à lowerBound + 2PI
		else if(lowerBound<0 && result>=lowerBound+2*Math.PI)
			toTake = Direction.LEFT;

		//on fonce dessus : return RIGHT ou LEFT
		return toTake;
	}
	
	
	//int maxIdx = 0;
	
			//checker les scores, recup le serpent qui a le plus haut score
			// Je n'arrive pas a recup le score des snakes , 
			//il faut relier le playerId du snake dans un tableau de player ?
					
					/*players.playerId = snake.playerId;				
					Player[] players.getPlayerId() = i
					if(players.totalScore > maxIdx)
						maxIdx = i;*/
							
			//deuxieme solution : trouver le serpent le plus proche: 
					
				
	
	/**
	 * Retourne la distance entre la position du serpent le plus proche et l'agent
	 * 
	 * @return double dist 
	 */
	public double closestSnake()
	{
		double dist = 0;
		Board board = getBoard();
		
		for(int i=0; i<board.snakes.length ;i++)
		{	checkInterruption();
				
			Snake snake = board.snakes[i];	
			
			// on traite seulement les serpents des autres joueurs
			if(i != getPlayerId())
			{
				//On recup la trainée du serpent i
				Set<Position> trail = new TreeSet<Position>(snake.oldTrail);
				trail.addAll(snake.newTrail);
				
				for(Position position: trail)
				{	checkInterruption();	// une boucle, donc un autre test d'interruption
					
					// on récupère l'angle entre la tête du serpent de l'agent 
					// et la position traitée (donc une valeur entre 0 et 2*PI)
					double angle = Math.atan2(position.y-agentSnake.currentY, position.x-agentSnake.currentX);
					if(angle<0)
						angle = angle + 2*Math.PI;
						
					// si la position est visible par le serpent de l'agent
					if(isInSight(angle))
					{	
						// on calcule la distance entre cette position et la tête du serpent de l'agent
						dist = Math.sqrt(
								Math.pow(agentSnake.currentX-position.x, 2) 
								+ Math.pow(agentSnake.currentY-position.y,2));
					}
				}
			}
		}
		return dist;	
	}
}