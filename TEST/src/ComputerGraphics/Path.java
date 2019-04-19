package ComputerGraphics;

public class Path {
	int []points;
	
	public Path(int x0, int y0, int x1, int y1) {
		points = new int[4];
		points[0] = x0;
		points[1] = y0;
		points[2] = x1;
		points[3] = y1;
	}
}
