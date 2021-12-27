package fr.dauphine.JavaAvance.Solve;


import java.util.ArrayList;
import java.util.Random;

import fr.dauphine.JavaAvance.Components.Orientation;
import fr.dauphine.JavaAvance.Components.Pair;
import fr.dauphine.JavaAvance.Components.Piece;
import fr.dauphine.JavaAvance.Components.PieceType;
import fr.dauphine.JavaAvance.GUI.Grid;

import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedWriter;
import java.io.IOException;
/**
 * Generate a solution, number of connexe composant is not finished
 *
 */

public class Generator {

	private static Grid filledGrid;

	/**
	 * @param output
	 *            file name
	 * @throws IOException
	 *             - if an I/O error occurs.
	 * @return a File that contains a grid filled with pieces (a level)
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	/*public static void generateLevel(String fileName, Grid inputGrid) {
		Random randType = new Random();
		ArrayList<Integer> lType = new ArrayList<Integer>();
		lType.add(0); lType.add(1); lType.add(2); lType.add(3); lType.add(4); lType.add(5);
		int type = 0;
		for (int i = 0; i < inputGrid.getHeight(); i++) {
			for (int j = 0; j < inputGrid.getWidth(); j++) {
				type = lType.get(randType.nextInt(lType.size()));
				inputGrid.setPiece(i, j, new Piece(i,j,type, randType.nextInt(maxOri(type))));
			}
		}
		System.out.println(inputGrid + "\n\n\n");
		ArrayList<Integer> lType2 = new ArrayList<Integer>(lType);
		for (int i = 0; i < inputGrid.getHeight(); i++) {
			for (int j = 0; j < inputGrid.getWidth(); j++) {
				while (!inputGrid.goodPiece(inputGrid.getPiece(i,j))) {
					lType2.remove(Integer.valueOf(type));
					type = lType.get(randType.nextInt(lType.size()));
					inputGrid.setPiece(i, j, new Piece(i,j,type,randType.nextInt(maxOri(type))));
					i = 0; j = 0;
				}
				lType2 = new ArrayList<Integer>(lType);
			}
		}
		Charset charset = Charset.forName("US-ASCII");
		Path p = FileSystems.getDefault().getPath(fileName);
		try (BufferedWriter bw = Files.newBufferedWriter(p, charset)){
			bw.write(inputGrid.getWidth() + "\n" + inputGrid.getHeight() + "\n", 0, (inputGrid.getWidth() + "\n" + inputGrid.getHeight() + "\n").length());
			for (int i = 0; i < inputGrid.getHeight(); i++) {
				for (int j = 0; j < inputGrid.getWidth(); j++) {
					bw.write(stringPiece(inputGrid,i,j), 0, stringPiece(inputGrid,i,j).length());
					System.out.println(stringPiece(inputGrid,i,j));
				}
			}
		}
		catch (IOException e) {
			System.out.println("Erreur fichier");
		}
		System.out.println(inputGrid);
	}*/
	
	
	public static void generateLevel(String fileName, Grid inputGrid) {
		inputGrid.initializeGrid(); // Initialisation du Grid avec des pièces VOID
		Random rand = new Random();
		for (int i = 0; i < inputGrid.getHeight(); i++) { // Generation d'un solved Grid
			for (int j = 0; j < inputGrid.getWidth(); j++) {
				ArrayList<Pair<Integer,Integer>> filter = inputGrid.interFilter(inputGrid.getPiece(i, j));
				Pair<Integer, Integer> type = filter.get(rand.nextInt(filter.size()));
				inputGrid.setPiece(i, j, new Piece(i,j,type.getKey(),type.getValue()));
			}
		}
		Charset charset = Charset.forName("US-ASCII");
		Path p = FileSystems.getDefault().getPath(fileName);
		try (BufferedWriter output = Files.newBufferedWriter(p, charset)){
			output.write(inputGrid.getWidth() + "\n" + inputGrid.getHeight() + "\n", 0, (inputGrid.getWidth() + "\n" + inputGrid.getHeight() + "\n").length());

			for (int i = 0; i < inputGrid.getHeight(); i++) { // Generation d'un solved Grid
				for (int j = 0; j < inputGrid.getWidth(); j++) {
					ArrayList<Orientation> possibOri = inputGrid.getPiece(i, j).getPossibleOrientations();
					int ori = rand.nextInt(possibOri.size());
					inputGrid.getPiece(i, j).setOrientation(possibOri.get(ori).getValuefromOri());
					output.write(stringPiece(inputGrid,i,j), 0, stringPiece(inputGrid,i,j).length());
					
				}
			}
		}
		catch (IOException e) {
			System.out.println("Erreur fichier");
		}
		System.out.println(inputGrid);
	}
	
	
	public static String stringPiece(Grid inputGrid, int i, int j) {
		return inputGrid.getPiece(i,j).getType().getValuefromType() + " " + inputGrid.getPiece(i,j).getOrientation().getValuefromOri() + "\n";
	}
		
	public static int maxOri(int type) {
		if (type == 0 || type == 4)
			return 1;
		else if (type == 2)
			return 2;
		else
			return 4;
	}
		
		// To be implemented
	public static int[] copyGrid(Grid filledGrid, Grid inputGrid, int i, int j) {
		Piece p;
		int hmax = inputGrid.getHeight();
		int wmax = inputGrid.getWidth();

		if (inputGrid.getHeight() != filledGrid.getHeight())
			hmax = filledGrid.getHeight() + i; // we must adjust hmax to have the height of the original grid
		if (inputGrid.getWidth() != filledGrid.getWidth())
			wmax = filledGrid.getWidth() + j;

		int tmpi = 0;// temporary variable to stock the last index
		int tmpj = 0;

		// DEBUG System.out.println("copyGrid : i =" + i + " & j = " + j);
		// DEBUG System.out.println("hmax = " + hmax + " - wmax = " + wmax);
		for (int x = i; x < hmax; x++) {
			for (int y = j; y < wmax; y++) {
				// DEBUG System.out.println("x = " + x + " - y = " + y);
				p = filledGrid.getPiece(x - i, y - j);
				// DEBUG System.out.println("x = " + x + " - y = " +
				// y);System.out.println(p);
				inputGrid.setPiece(x, y, new Piece(x, y, p.getType(), p.getOrientation()));
				// DEBUG System.out.println("x = " + x + " - y = " +
				// y);System.out.println(inputGrid.getPiece(x, y));
				tmpj = y;
			}
			tmpi = x;
		}
		//DEBUGSystem.out.println("tmpi =" + tmpi + " & tmpj = " + tmpj);
		return new int[] { tmpi, tmpj };
	}

}