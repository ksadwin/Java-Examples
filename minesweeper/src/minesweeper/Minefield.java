
package minesweeper;

import java.awt.Color;
import java.awt.event.KeyEvent;
/**
 * Incorporates the Cell class to construct a game of Minesweeper of customizable size.
 * @author Kelly
 *
 */
public class Minefield {
	
	private int W;
	private int H;
	private Cell[][] cells;
	private int numBombs; //later used to count flags remaining
	private int cellsRemaining;
	
	/**
	 * Generates a field of empty Cells to prepare for a game of Minesweeper.
	 * @param w
	 * @param h
	 * @param numBombs
	 */
	public Minefield(int w, int h, int numBombs) {
		this.W = w;
		this.H = h;
		this.cells = new Cell[W][H];
		this.numBombs = numBombs;
		this.cellsRemaining = this.W*this.H - this.numBombs;
		
		//intialize Cells
		for (int x=0; x<W; x++) {
			for (int y=0; y<H; y++) {
				this.cells[x][y] = new Cell();
			}
		}
				
		//draw field
		StdDraw.setCanvasSize(30*W,30*H+60);
        StdDraw.setXscale(0, W);
        StdDraw.setYscale(0, H+2);
        
        for (int i = 0; i<W; i++){
	    	for (int j=0; j <H; j++){
	    		Color c = new Color(100,150,255); //light blue
	    		StdDraw.setPenColor(c);
	    		StdDraw.filledSquare(i+.45, j+.45, 0.45);
	    	}
        }
	}
	/**
	 * Takes the Cell at the given coordinates and turns it into a bomb,
	 * then checks its surroundings to add to the bomb count of the Cell.
	 * @param x
	 * @param y
	 */
	public void setBomb(int x, int y) {
		this.cells[x][y].setBomb(9);
		
		for (int i=x-1; i<=x+1; i++) {
			for (int j=y-1; j<=y+1; j++) {
				addBomb(i, j);
			}
		}
	}
	/**
	 * After checking to see if the indices exist in the array,
	 * adds to the bomb count of the Cell IF it is not already a bomb Cell.
	 * @param x
	 * @param y
	 */
	public void addBomb(int x, int y) {
		try {
			if (this.cells[x][y].getBomb() != 9) {
				int bombs = this.cells[x][y].getBomb();
				this.cells[x][y].setBomb(bombs+1);
			}
		} catch (ArrayIndexOutOfBoundsException e) {}
		
	}
	/**
	 * Initializes gameplay. Event listeners for clicks and f-clicks (flags) that then call the
	 * appropriate functions. Includes functionality for the first click, which places the bombs
	 * via the placeBombs() function.
	 */
	public void play() {
		boolean isFirstClick = true;
		updateFlagsRemaining();
		while (this.cellsRemaining>0) {
			if (StdDraw.mousePressed() && !StdDraw.isKeyPressed(KeyEvent.VK_F)) {
				int x = (int) StdDraw.mouseX();
				int y = (int) StdDraw.mouseY();
				if (isFirstClick) {
					placeBombs(x, y);
					isFirstClick = false;
				}
				click(x, y);
			}
			if (StdDraw.mousePressed() && StdDraw.isKeyPressed(KeyEvent.VK_F)) {
				int x = (int) StdDraw.mouseX();
				int y = (int) StdDraw.mouseY();
				flag(x, y);
				delay();
			}
		}
		win();
	}
	/**
	 * Reveals the contents of the Cell, then takes the appropriate action pending on its contents,
	 * including recursively revealing surrounding Cells if there are zero surrounding bombs.
	 * @param x
	 * @param y
	 */
	public void click(int x, int y) {
		if (!this.cells[x][y].getFlag() && !this.cells[x][y].getClick()) {
			this.cells[x][y].click();
			this.cellsRemaining--;
			Color c = new Color(200,200,200); //grey
			StdDraw.setPenColor(c);
    		StdDraw.filledSquare(x+.45, y+.45, 0.45);
    		
    		if (this.cells[x][y].getBomb() == 9) {
    			StdDraw.setPenColor(StdDraw.RED);
	    		StdDraw.filledSquare(x+.45, y+.45, 0.45);
	    		lose();
	    		return;
			} else if (this.cells[x][y].getBomb() == 0) {
				for (int i=x-1; i<=x+1; i++) {
					for (int j=y-1; j<=y+1; j++) {
						try {
							if (!this.cells[i][j].getClick()) {
								click(i,j);
							}
						} catch (ArrayIndexOutOfBoundsException e) {}
					}
				}
			} else {
				String temp = Integer.toString(this.cells[x][y].getBomb());
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.text(x+.45, y+.45, temp);
				return;
			}
		}
		
		
	}
	/**
	 * Places or removes a flag from the Cell.
	 * @param x
	 * @param y
	 */
	public void flag(int x, int y) {
		this.cells[x][y].flag();
		if (!this.cells[x][y].getClick()) {
			if (this.cells[x][y].getFlag()) {
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.text(x+.45, y+.45, "F");
				this.numBombs--;
			} else {
				Color c = new Color(100,150,255);
	    		StdDraw.setPenColor(c);
	    		StdDraw.filledSquare(x+.45, y+.45, 0.45);
	    		this.numBombs++;
			}
			updateFlagsRemaining();
		}
		
	}
	/**
	 * Changes number of flags remaining as displayed at the top of the game screen.
	 */
	public void updateFlagsRemaining(){
		String temp = Integer.toString(this.numBombs);
		StdDraw.setPenColor(new Color(200, 200, 200));
		StdDraw.filledSquare((this.W)/2.0, this.H+1, .8);
		StdDraw.setPenColor();
		StdDraw.text((this.W)/2.0, this.H+1, temp);
	}
	/**
	 * Given the coordinates of the first click, bombs are placed accordingly.
	 * No bombs are placed on the clicked Cell, nor any of its surrounding Cells.
	 * This reveals more Cells in the beginning, making for better gameplay.
	 * @param i
	 * @param j
	 */
	public void placeBombs(int i, int j) {
		for (int n=0; n<this.numBombs; n++) {
			int x = (int) (Math.random()*this.W);
			int y = (int) (Math.random()*this.H);
			//if the random Cell is not within 1-Cell radius of first click, AND is not already bombed
			if (!((x >=i-1 && x <=i+1) && (y >= j-1 && y <= j+1)) && this.cells[x][y].getBomb() != 9) {
				setBomb(x, y);
			} else {
				n--;
			}
		}
	}
	/**
	 * Called when cellsRemaining (referring to non-bomb, clicked Cells) is 0.
	 * Flashes all flagged Cells with pretty colors and prompts user to play again or exit.
	 */
	public void win(){
		for (int i=0; i<5; i++){
			int r = (int) (Math.random()*256);
			int g = (int) (Math.random()*256);
			int b = (int) (Math.random()*256);
			Color c = new Color(r, g, b);
			StdDraw.setPenColor(c);
			for (int x=0; x<this.W; x++) {
				for (int y=0; y<this.H; y++) {
					if (this.cells[x][y].getBomb() == 9) {
						StdDraw.filledSquare(x+.45, y+.45, 0.45);
						
					}
				}
			}
			delay();
		}
		System.out.print("You win! Play again?\n1) Yes\n2) No\nSelect one: ");
		int choice = getMenuOption(1,2);
		if (choice == 1) {
			startGame();
		} else {
			System.out.println("Thanks for playing Minesweeper.");
		}
	}
	/**
	 * Called when a bombed Cell is clicked. Reveals all bombs, marks all incorrect flags,
	 * and prompts the user to play again or exit.
	 */
	public void lose() {
		StdDraw.setPenColor(StdDraw.RED);
		for (int x=0; x<this.W; x++) {
			for (int y=0; y<this.H; y++) {
				if (this.cells[x][y].getBomb() == 9) {
					StdDraw.filledSquare(x+.45, y+.45, 0.45);
				} else if (this.cells[x][y].getFlag()) {
					StdDraw.text(x+.45, y+.45, "X");
				}
			}
		}
		System.out.print("You lose. Play again?\n1) Yes\n2) No\nSelect one: ");
		int choice = getMenuOption(1,2);
		if (choice == 1) {
			startGame();
		} else {
			System.out.println("Thanks for playing Minesweeper.");
		}
	}
	/**
	 * Used to receive integers of menu options from user.
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getMenuOption(int min, int max) {
		
		int option = min;
		while (true){
			try{
				String input = "";
				while (input.equals("")) {
					input = StdIn.readLine();
				}
				option = Integer.parseInt(input);
			} catch (java.lang.NumberFormatException e){
				System.out.print("Error: entry not an integer. try again: ");
				continue;
			}
			
			if ( (option >= min) && (option <= max)){ 
				 return option;
			}
			System.out.print("Error: integer out of bounds. try again: ");
			
		}
	}
	/**
	 * Pauses program. Used in mouse listener because Cells were repeatedly flagged and unflagged
	 * depending on the duration of the mouse click.
	 */
	public static void delay() {
		try {Thread.sleep(300);} catch (InterruptedException ie) {}
	}
	/**
	 * Receives width, height, and number of bombs from user in predetermined or custom builds.
	 */
	public static void startGame() {
		int w, h, numBombs;
		System.out.println("1) Beginner\n2) Intermediate\n3) Expert\n4) Custom\n");
		System.out.print("Select difficulty: ");
		int choice = getMenuOption(1,4);
		if (choice==1) {
			//Beginner
			w = 9;
			h = 9;
			numBombs = 10;
		} else if (choice ==2) {
			//Intermediate
			w = 16;
			h = 16;
			numBombs = 40;
		} else if (choice==3) {
			//Expert
			w = 30;
			h = 16;
			numBombs = 99;
		} else {
			//Custom
			System.out.print("Enter width (9-30): ");
			w = getMenuOption(9,30);
			System.out.print("Enter height (9-20): ");
			h = getMenuOption(9,20);
			System.out.print("Number of bombs (10-"+(w*h-10)+"): ");
			numBombs = getMenuOption(10,(w*h-10));
		}
		Minefield minesweeper = new Minefield(w, h, numBombs);
		minesweeper.play();
	}
	/**
	 * Prints welcome screen controls, calls game constructor
	 * @param args
	 */
	public static void main(String args[]) {
		System.out.println("Welcome to Minesweeper\n\nControls:\nClick - Select Cell\nClick + F key - Flag Cell");
		startGame();
	}

}
