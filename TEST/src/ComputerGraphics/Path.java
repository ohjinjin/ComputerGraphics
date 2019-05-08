package ComputerGraphics;

<<<<<<< HEAD
/*다각형 및 선분의 성분을 저장하기 위한 클래스*/
public class Path {
	int []points;
	double []Uset;
	
	public Path(int x0, int y0, int x1, int y1) {
		points = new int[6];
		Uset = new double[2];
		points[0] = x0;
		points[1] = y0;
		points[2] = x1;
		points[3] = y1;
		Uset[0] = 0.0;	// u1
		Uset[1] = 1.0;	// u2
=======
public class Path {
	int []points;
	
	public Path(int x0, int y0, int x1, int y1) {
		points = new int[4];
		points[0] = x0;
		points[1] = y0;
		points[2] = x1;
		points[3] = y1;
>>>>>>> refs/remotes/origin/master
	}
}
