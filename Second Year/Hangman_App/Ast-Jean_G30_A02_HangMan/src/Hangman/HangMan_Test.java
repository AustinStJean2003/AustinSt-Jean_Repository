package Hangman;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HangMan_Test {

	@Test
	void test_Score_Board() {
		SB_Frame sb = new SB_Frame();
		Login_Frame player = new Login_Frame();
		assertTrue(sb.totalPlayers(sb.getNumPlayers()));
		assertFalse(sb.listPlayers(0));
		assertFalse(sb.getNextPlayer(0));
		assertFalse(sb.getNextPlayer(1));
		assertFalse(sb.getNextPlayer(3));
		assertTrue(sb.gamePlayed(0));
		assertTrue(sb.gamePlayed(1));
		assertFalse(sb.gamePlayed(-1));
		assertFalse(player.nameTooLong("NAMEWAYYYYTOOOOOOLONGGGG"));
		assertTrue(player.nameUnique("Name taken"));
	}// test

}
