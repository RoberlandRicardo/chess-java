package chess;


import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import board.Board;
import board.Piece;
import board.Position;
import chess.piece.*;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private boolean checkMate;
	private Piece promoted;
	private Piece enPassantVunerable;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public Piece getPromoted() {
		return promoted;
	}
	
	public Piece getEnPassantVunerable() {
		return enPassantVunerable;
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
		Piece capturedPiece = makeMove(source,target);
		
		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You cant put yourself in check");
		}
		
		Piece movedPiece = board.getPiece(target);
		
		
		// Promoted move
		promoted = null;
		if (movedPiece instanceof Pawn) {
			if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) ||(movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
				promoted = board.getPiece(target);
				promoted = replacePromotedPiece("Q");
			}
		}
		
		check = (testCheck(getOpponent(currentPlayer))) ? true : false;
		if (testCheckMate(getOpponent(currentPlayer))) {
			checkMate = true;
		} else {
			nextTurn();
		}
		
		// En passant move
		if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2 )) {
			enPassantVunerable = movedPiece;
		} else {
			enPassantVunerable = null;
		}
		
		return capturedPiece;
	}
	
	public Piece replacePromotedPiece(String type) {
		if (promoted == null) {
			throw new IllegalStateException("No have to be promoted");
		}
		if (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
			throw new InvalidParameterException("Invalid type for promotion");
		}
		
		Position pos = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);
		
		Piece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);
		
		return newPiece;
	}
	
	private Piece newPiece(String type, Color color) {
		if (type.equals("B")) return new Bishop(board, color);  
		if (type.equals("N")) return new Knight(board, color);  
		if (type.equals("Q")) return new Queen(board, color);  
		return new Rook(board, color);  
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		
		board.placePiece(p, target);
		
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		// Enpassant move
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == null) {
				Position pawnPosition;
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position(target.getRow() + 1, target.getColumn());
				} else {
					pawnPosition = new Position(target.getRow() - 1, target.getColumn());
				}
				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}
		
		// Roque move
		// King' sides
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn()+3);
			Position targetT = new Position(source.getRow(), source.getColumn()+1);
			Piece rook = board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		// Queen' sides
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn()-4);
			Position targetT = new Position(source.getRow(), source.getColumn()-1);
			Piece rook = board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		Piece p = board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);
		
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		
		// Roque move
		// King' sides
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn()+3);
			Position targetT = new Position(source.getRow(), source.getColumn()+1);
			Piece rook = board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
		// Queen' sides
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn()-4);
			Position targetT = new Position(source.getRow(), source.getColumn()-1);
			Piece rook = board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
		
		// Enpassant move
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVunerable) {
				Piece pawn = board.removePiece(target);
				Position pawnPosition;
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position(3, target.getColumn());
				} else {
					pawnPosition = new Position(4, target.getColumn());
				}
				board.placePiece(pawn, pawnPosition);
				
			}
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
	
	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> x.getColor() == color).collect(Collectors.toList());
		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getCols(); j++) {
					if (mat[i][j]) {
						Position source = p.getChessPosition().toPosition();
						Position target = new Position(i,j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
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
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
	}
}
