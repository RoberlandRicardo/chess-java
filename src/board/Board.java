package board;

public class Board {

	private int rows;
	private int cols;
	private Piece[][] pieces;
	
	public Board (int rows, int cols) {
		if (rows < 1 || cols < 1) {
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
		}
		this.rows = rows;
		this.cols = cols;
		pieces = new Piece[rows][cols];
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}
	
	public Piece[][] getPieces(){
		return pieces;
	}
	
	public Piece getPiece(int row, int col) {
		if (!positionExists(new Position(row, col))) {
			throw new BoardException("Erro to get the piece: The position don't exists");
		}
		return pieces[row][col];
	}
	
	public Piece getPiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Erro to get the piece: The position: "+ position + " don't exists");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Erro to place the piece: The position: "+ position + " don't exists");
		} else if (haveAPiece(position)) {
			throw new BoardException("Erro to place the piece: The position: "+ position + " already have a piece");
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
	}
	
	public Piece removePiece(Position position) {
		if (!positionExists(position)) {
			throw new BoardException("Erro to place the piece: The position: "+ position + " don't exists");
		}
		if (!haveAPiece(position)) {
			return null;
		}
		Piece aux = getPiece(position);
		aux.position = null;
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}
	
	public boolean positionExists(Position position) {
		return position.getRow() >= 0 && position.getRow() < rows 
				&& position.getColumn() >= 0 && position.getColumn() < cols;
	}
	
	public boolean haveAPiece(Position position) {
		return getPiece(position) != null;
	}
}
