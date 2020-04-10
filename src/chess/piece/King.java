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
		
		for (int i = position.getRow() - 1; i <= position.getRow() + 1; i++) {
			
			for (int j = position.getColumn() - 1; j <= position.getColumn() + 1; j++) {
				if (!(i < 0 || i >= getBoard().getRows() || j < 0 || j >= getBoard().getCols())) {
					if (getBoard().getPiece(i,j) == null){
						aux[i][j] = true;
					} else {
						if (getBoard().getPiece(i,j).getColor() != getColor()) {
							aux[i][j] = true;
						}
					}
				}
			}
		}
		return aux;
	}
}
