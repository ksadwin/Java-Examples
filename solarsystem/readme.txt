This program generates models of celestial bodies, given their data in a text file written in a specific order. First, the number of celestial bodies to be generated, then the radius of the generated universe, then the data of the planets in this order: position on x axis, position on y axis, velocity in x direction, velocity in y direction, mass, image filename.
The provided file "planets.txt" serves as a template and models the behavior of the inner solar system.

Some code is commented out which tests the program's operation. There is a loop at line 47 of the program which will print out the contents of the two data arrays - one for doubles and one for the names of the images - and there is a loop at line 111 that will print out the changes in position and velocity of the celestial bodies for at every timestep. To prevent that loop from writing massive amounts of data, the infinite while-loop that spans most of the code should be modified to a finite for-loop, as explained in the code.







NOTE: I included the code StdAudio.play("2001.mid"); in my program, but it does not run on my computer due to a Windows error.
"WARNING: Could not open/create prefs root node Software\JavaSoft\Prefs at root 0x80000002. Windows RegCreateKeyEx(...) returned error code 5."
Googled the error, found a solution that proposed allowing all permissions to the folder referenced in the error, still didn't work. I'm assuming it will work when run on another computer.