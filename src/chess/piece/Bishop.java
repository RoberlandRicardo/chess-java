package chess.piece;

import board.Board;
import board.Piece;
import board.Position;
import chess.Color;

public class Bishop extends Piece{

	public Bishop(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] aux = new boolean[super.getBoard().getRows()][super.getBoard().getCols()];
		
		Position p = new Position(0,0);
		
		//Noroeste
		p.setPositions(position.getRow() - 1, position.getColumn() - 1);;
		while (getBoard().positionExists(p)) {
			if (getBoard().haveAPiece(p)) {
				if (getBoard().getPiece(p).getColor() == super.getColor()) {
					break;
				} else {
					aux[p.getRow()][p.getColumn()] = true;
					break;
				}
			} else {
				aux[p.getRow()][p.getColumn()] = true;
				p.setPositions(p.getRow()-1, p.getColumn()-1);
			}
		}
		
		//Sudoeste
		p.setPositions(position.getRow() + 1, position.getColumn() - 1);;
		while (getBoard().positionExists(p)) {
			if (getBoard().haveAPiece(p)) {
				if (getBoard().getPiece(p).getColor() == super.getColor()) {
					break;
				} else {
					aux[p.getRow()][p.getColumn()] = true;
					break;
				}
			} else {
				aux[p.getRow()][p.getColumn()] = true;
				p.setPositions(p.getRow()+1, p.getColumn()-1);
			}
		}
		
		//Sudeste
		p.setPositions(position.getRow() + 1, position.getColumn() + 1);;
		while (getBoard().positionExists(p)) {
			if (getBoard().haveAPiece(p)) {
				if (getBoard().getPiece(p).getColor() == super.getColor()) {
					break;
				} else {
					aux[p.getRow()][p.getColumn()] = true;
					break;
				}
			} else {
				aux[p.getRow()][p.getColumn()] = true;
				p.setPositions(p.getRow()+1, p.getColumn()+1);
			}
		}
		
		//Nordeste
		p.setPositions(position.getRow() - 1, position.getColumn() + 1);;
		while (getBoard().positionExists(p)) {
			if (getBoard().haveAPiece(p)) {
				if (getBoard().getPiece(p).getColor() == super.getColor()) {
					break;
				} else {
					aux[p.getRow()][p.getColumn()] = true;
					break;
				}
			} else {
				aux[p.getRow()][p.getColumn()] = true;
				p.setPositions(p.getRow()-1, p.getColumn()+1);
			}
		}
		
		return aux;
	}
 
}
