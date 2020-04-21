package program;

import java.security.InvalidParameterException;
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
		boolean flag01 = false;
		
		while (!match.getCheckMate()) {
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
				if (match.getPromoted() != null) {
					while (flag01 == false) {
						try{
							Interface.clearScreen();
							Interface.printBoard(match.getPieces(), possibleMoves);
							System.out.println("Enter the piece for promotion (B/N/Q/R)");
							String type = sc.nextLine().toUpperCase();
							match.replacePromotedPiece(type);
							flag01 =true;
						} catch (InvalidParameterException e) {
							System.out.println(e.getMessage());
							sc.nextLine();
						}
					}
				}
			} catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		Interface.clearScreen();
		Interface.printMatch(match, captured);
	}

}
