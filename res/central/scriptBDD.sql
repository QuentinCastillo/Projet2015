Drop table partie;

Create table partie
(
<<<<<<< HEAD
	ip VARCHAR(20);
=======
	ip varchar(20);
>>>>>>> branch 'server' of ssh://git@github.com/QuentinCastillo/Projet2015.git
	nb_players INT;
	nb_max_players INT;
)

Drop table player;

Create table player
(
	id Serial primary key;
	pseudo varchar(50);
	country varchar(20);
	pswd varchar(20);
)


