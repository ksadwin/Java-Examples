import java.awt.Color;


public class Terrain {

	
	private int W;  // Width of Grid
	private int H;  // Height of Grid
	private int[][] state; //0 - empty, 1 - burning, 2- forest, 3- house
	private int[][] nextState; //0 - empty, 1 - burning, 2- forest, 3- house
	
	/**
	 * constructor for Terrain Class
	 * @param width
	 * @param height
	 */
	public Terrain (int width, int height){
  	    W = width;
		H = height;

		state = new int[W][H];
		nextState = new int[W][H];

	    reset();

	    StdDraw.setCanvasSize(500,500);
        StdDraw.setXscale(0, W);
        StdDraw.setYscale(0, H);
	    this.update();        
	}	

	/**
	 * updates terrain by swapping next state as current state and redrawing
	 */
	
	public void update(){
        
        for (int i = 0; i<W; i++){
	    	for (int j=0; j <H; j++){
	    		state[i][j] = nextState[i][j];

	    		Color c = new Color(30,30,30);
	    		if (state[i][j] == 1){
	    			c = new Color(255,30,30);
	    		} else if (state[i][j] == 2){
	    			c = new Color(30,200,30);
	    		} else if (state[i][j] == 3){
	    			c = new Color(150,150,150);
	    		}
	    		StdDraw.setPenColor(c);
	    		StdDraw.filledSquare(i+.45, j+.45, 0.45);
	    	}
	    }
		
		
	}
	/**
	 * change nextState to either fire or empty in a certain block
	 * @param i
	 * @param j
	 */
	public void setFire(int i, int j){
		nextState[i][j] = 1;
	}
	
	public void setEmpty(int i, int j){
		nextState[i][j] = 0;
	}
	
	public void buildHouse(int i, int j) {
		state[i][j] = 3;
		nextState[i][j] = 3;
	}
	public void reset(){
		for (int i = 0; i<W; i++){
	    	for (int j=0; j <H; j++){
	    		state[i][j] = 2;
	    		nextState[i][j] = 2;
	    	}
	    }
	}
	
	public int getWidth() {
		return this.W;
	}
	public int getHeight() {
		return this.H;
	}
	public int getState(int i, int j) {
		return this.state[i][j];
	}
	

	
}