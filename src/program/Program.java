package program;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import board.Piece;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		ChessMatch match = new ChessMatch();
		List<Piece> captured = new ArrayList<>();
		
		while (true) {
			try {
				Interface.clearScreen();
				Interface.printMatch(match, captured);
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = Interface.readChessPosition(sc);
				
				boolean[][] possibleMoves = match.possibleMoves(source);
				Interface.clearScreen();
				Interface.printBoard(match.getPieces(), possibleMoves);
				
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = Interface.readChessPosition(sc);
				
				Piece capturedPiece = match.chessMove(source, target);
				if (capturedPiece != null) {
					captured.add(capturedPiece);
				}
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
	}

}
