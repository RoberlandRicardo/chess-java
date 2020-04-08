package chess;


import board.Position;
import board.Board;
import board.Piece;
import chess.piece.King;
import chess.piece.Rook;

public class ChessMatch {

	private Board board;
	
	public ChessMatch() {
		board = new Board(8,8);
		initialSetup();
	}
	
	public Piece[][] getPieces(){
		return board.getPieces();
	}
	
	public Piece chessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		if (!board.positionExists(source)) {
			throw new ChessException("Error in the source position: This position are invalid");
		}
		if (!board.haveAPiece(source)) {
			throw new ChessException("Error in the source position: There is no piece in the original position");
		}
		if (!board.getPiece(source).havePossibleMove()) {
			throw new ChessException("Error in the source position: There is no possible moves for the piece");
		}
		if (!board.getPiece(source).possibleMove(target)) {
			throw new ChessException("Error in the source position: This move is invalid");
		}
		
		return makeMove(source, target);
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		return capturedPiece;
	}
	
	private void placeNewPiece(char col, int row, Piece piece) {
		board.placePiece(piece, new ChessPosition(row, col).toPosition());
	}
	
	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
