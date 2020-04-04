package chess;

import board.Board;
import board.Piece;

public class ChessMatch {

	private Board board;
	
	public ChessMatch() {
		board = new Board(8,8);
	}
	
	public Piece[][] getPieces(){
		return board.getPieces();
	}
}
