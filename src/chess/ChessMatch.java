package chess;

import board.*;
import chess.piece.Rook;
import chess.piece.King;
import chess.*;

public class ChessMatch {

	private Board board;
	
	public ChessMatch() {
		board = new Board(8,8);
		initialSetup();
	}
	
	public Piece[][] getPieces(){
		return board.getPieces();
	}
	
	private void placeNewPiece(char col, int row, Piece piece) {
		board.placePiece(piece, new ChessPosition(row, col).toPosition());
	}
	
	private void initialSetup() {
		placeNewPiece('b', 6, new Rook(board, Color.WHITE));
		placeNewPiece('e', 8, new King(board, Color.BLACK));
		placeNewPiece('e', 1, new King(board, Color.WHITE));
	}
}
