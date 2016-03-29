<?php

class StatsHandler
{
	$elo;
	$gameCount;
	$roundCount;
	$gameWon;
	$pointByGame;
	$pointByRound;

	function downloadStats($id)
	{

		$sql='Select value from elo where idplayer='.$id.';';
		$result= mysql_query('$sql');
		$elo=$result;

		$sql='Select value from gameCount where idplayer='.$id.';';
		$result= mysql_query('$sql');
		$gameCount=$result;

		$sql='Select value from roundCount where idplayer='.$id.';';
		$result= mysql_query('$sql');
		$roundCount=$result;

		$sql='Select value from gameWon where idplayer='.$id.';';
		$result= mysql_query('$sql');
		$gameWon=$result;

		$sql='Select value from pointByGame where idplayer='.$id.';';
		$result= mysql_query('$sql');
		$pointByGame=$result;

		$sql='Select value from pointByRound where idplayer='.$id.';';
		$result= mysql_query('$sql');
		$pointByRound=$result;
	}

	function uploadStats($id, $elo, $gameCount, $ roundCount, $gameWon, $pointByGame, $pointByRound)
	{
		$sql='Update elo set value='.$elo.' where idplayer='.$id.';';

		$sql='Update gameCount set value='.$gameCount.' where idplayer='.$id.';';

		$sql='Update roundCount set value='.$roundCount.' where idplayer='.$id.';';

		$sql='Update gameWon set value='.$gameWon.' where idplayer='.$id.';';

		$sql='Update pointByGame set value='.$pointByGame.' where idplayer='.$id.';';

		$sql='Update pointByRound set value='.$pointByRound.' where idplayer='.$id.';';
	}
}
?>