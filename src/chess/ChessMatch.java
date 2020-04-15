package chess;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import board.Board;
import board.Piece;
import board.Position;
import chess.piece.King;
import chess.piece.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public int getTurn() {
		return turn;
	}
	
	private void setTurn(int newTurn) {
		this.turn = newTurn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	private void setCurrentPlayer(Color currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public Piece[][] getPieces(){
		return board.getPieces();
	}
	
	public ChessMatch() {
		board = new Board(8,8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position source = sourcePosition.toPosition();
		if (!board.positionExists(source)) {
			throw new ChessException("Error in the source position: This position are invalid");
		}
		if (!board.haveAPiece(source)) {
			throw new ChessException("Error in the source position: There is no piece in the original position");
		}
		if (currentPlayer != board.getPiece(source).getColor()) {
			throw new ChessException("Error in the source position: The chosen piece is not yours");
		}
		if (!board.getPiece(source).havePossibleMove()) {
			throw new ChessException("Error in the source position: There is no possible moves for the piece");
		}
		return board.getPiece(source).possibleMoves();
	}
	
	public Piece chessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();

		if (!board.getPiece(source).possibleMove(target)) {
			throw new ChessException("Error in the target position: This move is invalid");
		}
		
		return makeMove(source, target);
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		
		board.placePiece(p, target);
		
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You cant put yourself in check");
		}
		
		check = (testCheck(getOpponent(currentPlayer))) ? true : false;
		nextTurn();
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		Piece p = board.removePiece(target);
		board.placePiece(p, source);
		
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
	}
	
	private boolean testCheck(Color color) {
		Position kingPosition = searchKing(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> x.getColor() == getOpponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] pos = p.possibleMoves();
			if (pos[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private Color getOpponent(Color color) {
		return (color == color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private Piece searchKing(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> x.getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return p;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board");
	}
	
	private void placeNewPiece(char col, int row, Piece piece) {
		board.placePiece(piece, new ChessPosition(row, col).toPosition());
		piecesOnTheBoard.add(piece);
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
