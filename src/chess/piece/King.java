package chess.piece;

import board.Board;
import board.Piece;
import chess.Color;

public class King extends Piece{

	public King(Board board, Color color) {
		super(board, color);
		
	}
	
	@Override
	public String toString() {
		return "K";
	}
	
	@Override
	public boolean[][] possibleMoves(){
		boolean[][] aux = new boolean[super.getBoard().getRows()][super.getBoard().getCols()];
		return aux;
	}
}
