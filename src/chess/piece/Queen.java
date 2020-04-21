package chess.piece;

import board.Board;
import board.Piece;
import board.Position;
import chess.Color;

public class Queen extends Piece{
	
	public Queen(Board board, Color color) {
		super(board, color);
		
	}
	
	@Override
	public String toString() {
		return "Q";
	}
	
	@Override
	public boolean[][] possibleMoves(){
		boolean[][] aux = new boolean[super.getBoard().getRows()][super.getBoard().getCols()];
		
		Position p = new Position(0,0);
		
		// Norte
		p.setColumn(position.getColumn());
		for (int i = position.getRow() - 1; i >= 0; i--) {
			p.setRow(i);
			if (getBoard().haveAPiece(p)) {
				if (isOpponentPiece(p)) {
					aux[p.getRow()][p.getColumn()] = true;
				}
				break;
			} else {
				aux[p.getRow()][p.getColumn()] = true;
			}
		}
		
		// Leste
		p.setRow(position.getRow());
		for (int i = position.getColumn() - 1; i >= 0; i--) {
			p.setColumn(i);
			if (getBoard().haveAPiece(p)) {
				if (isOpponentPiece(p)) {
					aux[p.getRow()][p.getColumn()] = true;
				}
				break;
			} else {
				aux[p.getRow()][p.getColumn()] = true;
			}
		}
		
		// Sul
		p.setColumn(position.getColumn());
		for (int i = position.getRow() + 1; i < getBoard().getRows(); i++) {
			p.setRow(i);
			if (getBoard().haveAPiece(p)) {
				if (isOpponentPiece(p)) {
					aux[p.getRow()][p.getColumn()] = true;
				}
				break;
			} else {
				aux[p.getRow()][p.getColumn()] = true;
			}
		}
		
		// Oeste
		p.setRow(position.getRow());
		for (int i = position.getColumn() + 1; i < getBoard().getCols(); i++) {
			p.setColumn(i);
			if (getBoard().haveAPiece(p)) {
				if (isOpponentPiece(p)) {
					aux[p.getRow()][p.getColumn()] = true;
				}
				break;
			} else {
				aux[p.getRow()][p.getColumn()] = true;
			}
		}
		
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
