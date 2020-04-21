package chess.piece;

import board.Board;
import board.Piece;
import board.Position;
import chess.Color;

public class Knight extends Piece {

	public Knight(Board board, Color color) {
		super(board, color);
		
	}
	
	@Override
	public String toString() {
		return "N";
	}
	
	private boolean canMove(Position position) {
		Piece p = getBoard().getPiece(position);
		return p == null || p.getColor() != super.getColor();
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] aux = new boolean[super.getBoard().getRows()][super.getBoard().getCols()];
		
		Position p = new Position(0,0);
		
		p.setPositions(position.getRow() - 1, position.getColumn() - 2);
		if (getBoard().positionExists(p) && canMove(p)) {
			aux[p.getRow()][p.getColumn()] = true;
		}
		
		p.setPositions(position.getRow() - 2, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			aux[p.getRow()][p.getColumn()] = true;
		}
		
		p.setPositions(position.getRow() + 1, position.getColumn() + 2);
		if (getBoard().positionExists(p) && canMove(p)) {
			aux[p.getRow()][p.getColumn()] = true;
		}
		
		p.setPositions(position.getRow() + 2, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			aux[p.getRow()][p.getColumn()] = true;
		}
		
		p.setPositions(position.getRow() - 2, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			aux[p.getRow()][p.getColumn()] = true;
		}
		
		p.setPositions(position.getRow() - 1, position.getColumn() + 2);
		if (getBoard().positionExists(p) && canMove(p)) {
			aux[p.getRow()][p.getColumn()] = true;
		}
		
		p.setPositions(position.getRow() + 1, position.getColumn() - 2);
		if (getBoard().positionExists(p) && canMove(p)) {
			aux[p.getRow()][p.getColumn()] = true;
		}
		
		p.setPositions(position.getRow() + 2, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			aux[p.getRow()][p.getColumn()] = true;
		}
		
		return aux;
	}

}
