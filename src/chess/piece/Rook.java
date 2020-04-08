package chess.piece;

import board.Board;
import board.Piece;
import board.Position;
import chess.Color;

public class Rook extends Piece {

	public Rook(Board board, Color color) {
		super(board, color);
		
	}
	
	@Override
	public String toString() {
		return "R";
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
		
		/*for (int i = 0; i < aux.length; i++) {
			for (int j = 0; j < aux.length; j++) {
				if (aux[i][j]) {
					System.out.print("* ");
				} else {
					System.out.print("- ");
				}
			}
			System.out.println();
		}*/
		
		return aux;
	}
}
