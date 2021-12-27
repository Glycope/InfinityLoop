package fr.dauphine.JavaAvance.Solve;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import fr.dauphine.JavaAvance.Components.Orientation;
import fr.dauphine.JavaAvance.Components.Piece;
import fr.dauphine.JavaAvance.Components.PieceType;
import fr.dauphine.JavaAvance.GUI.Grid;


public class Checker {

	public static void isSolved(String fileName, Grid inputGrid) {
		boolean solved = true;
		for (Piece[] lp : inputGrid.getAllPieces()) {
			for (Piece p : lp) {
				if (inputGrid.isTotallyConnected(p))
					solved = false;
			}
		}
		Charset charset = Charset.forName("US-ASCII");
		Path p = FileSystems.getDefault().getPath(fileName);
		try (BufferedWriter output = Files.newBufferedWriter(p, charset)){
			output.write("SOLVED: " + solved , 0, ("SOLVED: " + solved).length());
		}
		catch (IOException e) {
			System.out.println("Erreur fichier");
		}
	}
	
	public static Grid readFile(String fileName) {
		Grid outputGrid = null;
		Charset charset = Charset.forName("US-ASCII");
		Path p = FileSystems.getDefault().getPath(fileName);
		try (BufferedReader readBuff = Files.newBufferedReader(p, charset)){
			String ligne = "";
			int width = 0, height = 0;
			if ((ligne = readBuff.readLine()) != null)
				width = Integer.parseInt(ligne);
			
			if ((ligne = readBuff.readLine()) != null)
				height = Integer.parseInt(ligne);
			
			outputGrid = new Grid(width, height);
			int i = 0, j = 0;
			while ((ligne = readBuff.readLine()) != null) {
				String[] pieceformat = ligne.split(" ");
				outputGrid.setPiece(i, j , new Piece(i, j, Integer.parseInt(pieceformat[0]), Integer.parseInt(pieceformat[1])));
				j++;
				if (j == width)
					j = 0; i++;
			}
			if (i != height)
				throw new IOException();
		} catch (NumberFormatException | IOException | ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		
		
		return outputGrid;
	}
	
	
}
