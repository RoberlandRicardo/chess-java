package program;

import chess.ChessMatch;

public class Program {

	public static void main(String[] args) {
		
		ChessMatch match = new ChessMatch();
		Interface.printBoard(match.getPieces());
	}

}
