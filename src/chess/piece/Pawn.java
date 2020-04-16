package chess.piece;

import board.Board;
import board.Piece;
import board.Position;
import chess.Color;

public class Pawn extends Piece{

	public Pawn(Board board, Color color) {
		super(board, color);
		
	}

	@Override
	public String toString() {
		return "P";
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] aux = new boolean[super.getBoard().getRows()][super.getBoard().getCols()];
		
		Position p = new Position(0,0);
		
		if (getColor() == Color.WHITE) {
			p.setPositions(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().haveAPiece(p)) {
				aux[p.getRow()][p.getColumn()] = true;
			}
			Position p2 = p;
			p.setPositions(position.getRow() - 2, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().haveAPiece(p) && getMoveCount() == 0 && !getBoard().haveAPiece(p2)) {
				aux[p.getRow()][p.getColumn()] = true;
			}
			p.setPositions(position.getRow() - 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && isOpponentPiece(p)) {
				aux[p.getRow()][p.getColumn()] = true;
			}
			p.setPositions(position.getRow() - 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isOpponentPiece(p)) {
				aux[p.getRow()][p.getColumn()] = true;
			}
		} else {
			p.setPositions(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().haveAPiece(p)) {
				aux[p.getRow()][p.getColumn()] = true;
			}
			Position p2 = p;
			p.setPositions(position.getRow() + 2, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().haveAPiece(p) && getMoveCount() == 0 && !getBoard().haveAPiece(p2)) {
				aux[p.getRow()][p.getColumn()] = true;
			}
			p.setPositions(position.getRow() + 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && isOpponentPiece(p)) {
				aux[p.getRow()][p.getColumn()] = true;
			}
			p.setPositions(position.getRow() + 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isOpponentPiece(p)) {
				aux[p.getRow()][p.getColumn()] = true;
			}
		}
		return aux;
	}

}
