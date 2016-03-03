
public class FireSimulator2014 {
	
	public Terrain terrain;
	
	public FireSimulator2014(int w, int h) {
		this.terrain = new Terrain(w, h);
	}
	
	public void update() {
		for (int i=0; i<this.terrain.getWidth(); i++) {
			for (int j=0; j<this.terrain.getHeight(); j++) {
				if (this.terrain.getState(i,j) == 1) {
					checkSurroundings(i,j);
					this.terrain.setEmpty(i, j);
				}
			}
		}
		this.terrain.update();
	}
	
	public void checkSurroundings(int i, int j){
		spreadFire(i-1, j);
		spreadFire(i, j-1);
		spreadFire(i+1, j);
		spreadFire(i, j+1);
	}

	public void spreadFire(int i, int j){
		try {
			if (this.terrain.getState(i,j) == 2) {
				if (Math.random() < .5) {
					this.terrain.setFire(i, j);
				}	
			} else if (this.terrain.getState(i,j) == 3) {
				if (Math.random() < .3) {
					this.terrain.setFire(i, j);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {}
		
		
	}
	
	public void timeStep() {
		this.update();
		//try {Thread.sleep(100);} catch (InterruptedException ie) {}
	}
	
	public static void main(String[] args) {
		//build forested terrain
		int W = 10;
		int H = 10;
		FireSimulator2014 fs = new FireSimulator2014(W, H);
		
		
		//start trials
		int numTrials = 20;
		int timeSteps = 20;
		int count = 0;
		for (int n=0; n<numTrials; n++) {
			//build house in terrain
			int houseW = 3;
			int houseH = 4;
			fs.terrain.buildHouse(houseW, houseH);
			//start a fire
			int randW = (int) (Math.random()*W);
			int randH = (int) (Math.random()*H);
			fs.terrain.setFire(randW, randH);
			for (int t=0; t<timeSteps; t++) {
				fs.timeStep();
				boolean onFire = false;
				for (int i = 0; i<W; i++){
			    	for (int j=0; j <H; j++){
			    		if (fs.terrain.getState(i, j) == 1){
			    			onFire = true;
			    			break;
			    		}
			    	}
				}
				if (!onFire) {break;}
			}
			if (fs.terrain.getState(houseW,houseH) == 0) {
				count++;
			}
			fs.terrain.reset();
		}
		System.out.println("Out of "+numTrials+" trials, house was burned "+count+" times.");
		

	}

}
