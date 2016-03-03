/*******************************
 * NBody Simulation - visually represent solar system using Newtonian Physics
 * @author Kelly Sadwin
 * 2014.3.3
 */


import java.io.FileInputStream;
public class NBody {

	public static void main(String[] args) throws Exception{
		// read in planet information
		System.setIn(new FileInputStream("planets.txt"));
		
		// read in number of planets
		int N = StdIn.readInt();
		
		// radius of universe
		double R = StdIn.readDouble();
		//set universe scale
		StdDraw.setXscale(-R, R);
		StdDraw.setYscale(-R, R);
		
		// Gravitational Constant
		double G = 6.67e-11;
		
		// time step
		double dt = 25000; //time delta
		
		//number of data stored in text file
		//ORDER: xCoord, yCoord, xVel, yVel, mass
		int T = 5;
		
		double[][] startData = new double[N][T];
		
		String[] images = new String[N];
		
		for (int n=0; n<N; n++) {
			for (int t=0; t<T; t++) {
				startData[n][t] = StdIn.readDouble();
			}
			images[n] = StdIn.readString();
		}
		
		//This code tests that the program has correctly read the text file
		//by printing out the data in the arrays.
		/*System.out.println("Initial Data");
		for (int n=0; n<N; n++) {
			for (int t=0; t<T; t++) {
				System.out.print(startData[n][t]+"    ");
			}
			System.out.print(images[n]+"\n");
		}*/
		
		//play music
		StdAudio.play("2001.mid");
		
		//TO TEST TIMESTEPS, turn this infinite while-loop into a finite for-loop
		//and remove the comments on the for-loop below that prints out the data.
		while(true){
			//getting Ax, Ay
			for (int i=0; i<N; i++){
				double totalFx = 0;
				double totalFy = 0;
				for (int j=0; j<N; j++){
					//to prevent a divide-by-zero error
					if (j == i) {
						continue;
					}
					//physics: d = sqrt(deltaX^2+ deltaY^2)
					//determine which way the force is going based on which planet is larger
					double deltaX, deltaY;
					if (startData[i][4] > startData[j][4]) {
						deltaX = startData[i][0]-startData[j][0];
						deltaY = startData[i][1]-startData[j][1];
					} else {
						deltaX = startData[j][0]-startData[i][0];
						deltaY = startData[j][1]-startData[i][1];
					}
					//d^2
					double sqradius = Math.pow(deltaX, 2)+Math.pow(deltaY, 2);
					
					//physics: nondirectional force = G*m1*m2/(d^2)
					double force = G*startData[i][4]*startData[j][4]/sqradius;
					
					//physics: F(x,y) = F*delta(X,Y)/d 
					//sign of delta(X,Y) assigned earlier determines direction of force
					double Fx = force*deltaX/Math.sqrt(sqradius);
					double Fy = force*deltaY/Math.sqrt(sqradius);
					
					//add forces to running sums of x and y forces
					totalFx += Fx;
					totalFy += Fy;
					
				}
				//physics: a = f/m
				double totalAx = totalFx / startData[i][4];
				double totalAy = totalFy / startData[i][4];
				
				//physics: d = 1/2*a*t^2 + Vi*t
				startData[i][0] += totalAx*dt*dt/2 + startData[i][2]*dt;
				startData[i][1] += totalAy*dt*dt/2 + startData[i][3]*dt;
				
				//physics: Vf = a*t + Vi
				startData[i][2] += totalAx*dt;
				startData[i][3] += totalAy*dt;
				
			}
			//TIMESTEPPING: prints xCoord, yCoord, vx, vy, mass, planet for each timestep
			//edit while-loop as indicated above, then remove comments for below code.
			
			/*System.out.println("Timestep "+);
			for (int n=0; n<N; n++) {
				for (int t=0; t<T; t++) {
					System.out.print(startData[n][t]+"    ");
				}
				System.out.print(images[n]+"\n");
			}*/
			
			//drawing the planets
			StdDraw.picture(0, 0, "starfield.jpg");
			for (int n=0; n<N; n++) {
				StdDraw.picture(startData[n][0], startData[n][1], images[n]);
			}
			StdDraw.show(10);
			
		}
		
		
	}
}
