package chess.piece;

import board.Board;
import board.Piece;
import chess.Color;

public class Rook extends Piece {

	public Rook(Board board, Color color) {
		super(board, color);
		
	}
	
	@Override
	public String toString() {
		return "R";
	}
}
