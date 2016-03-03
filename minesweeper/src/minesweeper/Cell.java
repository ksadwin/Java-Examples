package minesweeper;
/**
 * Cell contains the properties of a cell in the game Minesweeper. To be used with Minefield class.
 * @author Kelly
 *
 */
public class Cell {
	
	private int bombs;
	//number from 0 to 9 indicating number of bombs touching Cell. 9 indicates Cell itself has bomb.
	
	private boolean isClicked;
	private boolean isFlagged;
	
	/**
	 * Default initialization as empty, unclicked, unflagged Cell.
	 */
	public Cell() {
		this.bombs = 0;
		this.isClicked = false;
		this.isFlagged = false;
	}
	/**
	 * Changes isClicked value from false to true.
	 * @return
	 */
	public void click() {
		if (!this.isClicked) {
			this.isClicked = true;
		}
	}
	/**
	 * Returns whether or not the Cell has been clicked.
	 * @return
	 */
	public boolean getClick() {
		return this.isClicked;
	}
	/**
	 * Flags or unflags a Cell, pending on its prior status.
	 */
	public void flag() {
		if (!this.isClicked && !this.isFlagged) {
			this.isFlagged = true;
		} else if (!this.isClicked && this.isFlagged) {
			this.isFlagged = false;
		}
	}
	/**
	 * Returns whether or not the Cell is flagged.
	 * @return
	 */
	public boolean getFlag() {
		return this.isFlagged;
	}
	/**
	 * Sets the number of bombs touching the Cell to the parameter integer.
	 * @param num
	 */
	public void setBomb(int num) {
		this.bombs = num;
	}
	/**
	 * Returns number of bombs touching Cell (or 9 if Cell contains bomb).
	 * @return
	 */
	public int getBomb() {
		return this.bombs;
	}

}
