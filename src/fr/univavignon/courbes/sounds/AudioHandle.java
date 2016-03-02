package fr.univavignon.courbes.sounds;

/**
 * @author Quentin Castillo
 */
public interface AudioHandle {
	
	/** Son au lancement du programme **/
	public void IntroSong();
	// TODO : "CURVE FEVER" voix grave
	/** Son lors du ramassage d'Item **/
	public void ItemCollision();
	/** Son lors de l'apparition d'Item **/
	public void ItemCreated();
	/** Music d'ambiance **/
	public void MusicInGame();
	/** Son de collision **/
	public void CollisionSound();
	/** Son lorsqu'on appuie sur un bouton **/
	public void Button();
}
