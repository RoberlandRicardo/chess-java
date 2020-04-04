package board;

import chess.Color;

public class Piece {
	protected Position position;
	private Board board;
	private Color color;
	
	public Piece(Board board, Color color) {
		this.board = board;
		this.color = color;
		position = null;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Color getColor() {
		return color;
	}
}
