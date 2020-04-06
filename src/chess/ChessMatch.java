package chess;

import board.*;
import chess.piece.Rook;
import chess.piece.King;

public class ChessMatch {

	private Board board;
	
	public ChessMatch() {
		board = new Board(8,8);
		initialSetup();
	}
	
	public Piece[][] getPieces(){
		return board.getPieces();
	}
	
	private void initialSetup() {
		board.placePiece(new Rook(board, Color.WHITE), new Position(3,1));
		board.placePiece(new King(board, Color.WHITE), new Position(3,1));
	}
}
