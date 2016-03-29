Drop table partie;

Create table partie
(
	ip varchar(20);
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


