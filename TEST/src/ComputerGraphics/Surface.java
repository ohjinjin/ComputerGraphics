package ComputerGraphics;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;

// ĵ������ ��ӹ޾�  Surface Ŭ���� ����
public class Surface extends JPanel
{
	public ArrayList <Path> pathList= new ArrayList<>();	// �ٰ����� �׸� �� �ֵ��� ���е��� ��� ������ ����Ʈ
	public ArrayList <Circle> circleList = new ArrayList<>();	// �� ������ ����Ʈ
	public double[] transList= new double[7];	// ��ȯ ��Ʈ������ ���� �Ķ���͸� ������ �迭
	public String algorithm = "DDA";	// ����ڰ� ������ �˰��� ���� �뵵, default�� DDA
	public boolean isDraw = false;	// �׸����� ���� �뵵

	/* ȭ�� ���� �Լ� JVM�� �ð��� ���� paintComponent�� ȣ���ϵ��� �ϱ� ���� */
	public void refreshSurface(String algorithm) {
		if (algorithm.equals("")) {
			isDraw = false;
		}
		else {
			isDraw = true;
			this.algorithm = algorithm;         
		}

		repaint();
	}

	/* ĵ������ �׷��ֱ� */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		/* ����� �׷��ֱ� */
		for(int i = 0; i < test.b_height; i += test.SCALE) {
			if (i == test.b_height/2) {
				Graphics2D g2d = (Graphics2D)g;
				g2d.setStroke(new BasicStroke(2));   // ����� �׷��� �� x��, y�� (2���� ��������) �׷��ֱ� ��Ŭ���� ��ǥ ���� ǥ�����ַ���
				g.drawLine(0, i, (int) test.b_width, i);
				g2d.setStroke(new BasicStroke(1));
			}
			else {
				g.drawLine(0, i, (int) test.b_width, i);

			}
		}

		for(int i = 0; i < test.b_width; i += test.SCALE) {
			if (i == test.b_width/2) {
				Graphics2D g2d = (Graphics2D)g;
				g2d.setStroke(new BasicStroke(2));
				g.drawLine(i, 0, i, (int) test.b_height);
				g2d.setStroke(new BasicStroke(1));
			}
			else {
				g.drawLine(i, 0, i, (int) test.b_height);            
			}
		}

		// �׸��� ����� ��� �˰����� �����Ͽ� �׸�����
		if (isDraw) {
			if (algorithm.equals("DDA")) {	// Line via DDA�� ���
				for (Path eachPath : pathList) {
					g.setColor(Color.BLACK);
					DDA(g,eachPath.points[0],eachPath.points[1],eachPath.points[2],eachPath.points[3]);
					g.setColor(Color.RED);	// ���������� ���� Ŭ���� ������ ���̱� ���Ͽ� �� ����
					lineClipLiangBarsk(g,10,50,10,30,"DDA",eachPath);
					/*g.setColor(Color.RED);
					scale(g,eachPath.points[0],eachPath.points[1],eachPath.points[2],eachPath.points[3],(int)transList[0],(int)transList[1],transList[4],transList[5]);
					g.setColor(Color.GREEN);
					rotate(g,eachPath.points[0],eachPath.points[1],eachPath.points[2],eachPath.points[3],(int)transList[0],(int)transList[1],transList[4],transList[5],(int)transList[6]);
					g.setColor(Color.BLUE);
					move(g,eachPath.points[0],eachPath.points[1],eachPath.points[2],eachPath.points[3],(int)transList[0],(int)transList[1],transList[2],transList[3]);*/
				}
			}
			
			else if (algorithm.equals("Bresenham")) {	// Line via Bresenham�� ���
				for (Path eachPath : pathList) {
					g.setColor(Color.BLACK);
					Bresenham(g,eachPath.points[0],eachPath.points[1],eachPath.points[2],eachPath.points[3]);
					g.setColor(Color.RED);
					lineClipLiangBarsk(g,10,50,10,30,"Bresenham",eachPath);
					/*g.setColor(Color.RED);
					scale(g,eachPath.points[0],eachPath.points[1],eachPath.points[2],eachPath.points[3],(int)transList[0],(int)transList[1],(int)transList[4],(int)transList[5]);
					g.setColor(Color.GREEN);
					rotate(g,eachPath.points[0],eachPath.points[1],eachPath.points[2],eachPath.points[3],(int)transList[0],(int)transList[1],(int)transList[4],(int)transList[5],(int)transList[6]);
					g.setColor(Color.BLUE);
					move(g,eachPath.points[0],eachPath.points[1],eachPath.points[2],eachPath.points[3],(int)transList[0],(int)transList[1],transList[2],transList[3]);*/
				}
			}
			
			else {	// Circle�� ���
				for (Circle eachCircle : circleList) {
					g.setColor(Color.BLACK);
					drawCircle(g,eachCircle.centerX,eachCircle.centerY,eachCircle.radius,0,0,1);
					g.setColor(Color.RED);
					scale(g,eachCircle.centerX,eachCircle.centerY,eachCircle.radius,transList[4]);
					g.setColor(Color.BLUE);
					move(g,eachCircle.centerX,eachCircle.centerY,eachCircle.radius,(int)transList[2],(int)transList[3]); 
				}
			}
		}
	}

	/* DDA algorithm */
	public void DDA(Graphics g,int x0, int y0, int x1, int y1) {
		x0 = x0*test.SCALE + test.b_width/2;
		x1 = x1*test.SCALE + test.b_width/2;
		y0 = -1*y0*test.SCALE + test.b_height/2;
		y1 = -1*y1*test.SCALE + test.b_height/2;

		/* DDA �˰��� */
		int dx=x1-x0,dy=y1-y0;   // deltaX, deltaY
		float xIncrement, yIncrement, steps, x=x0,   y=y0;

		if (dx>dy) {   // 0<= m <=1
			steps=Math.abs(dx)*test.SCALE;
		}
		else{   // 1< m
			steps=Math.abs(dy)*test.SCALE;
		}
		xIncrement=dx/steps;
		yIncrement=dy/steps;

		g.fillRect(x0,y0,test.SCALE,test.SCALE);   // ������ setPixel()
		while(steps!=0)   {
			steps--;
			x += xIncrement;
			y += yIncrement;
			g.fillRect(Math.round(x/test.SCALE)*test.SCALE,Math.round(y/test.SCALE)*test.SCALE,test.SCALE,test.SCALE);   // setPixel()
		}

	}

	/* Bresenham Algorithm */
	public void Bresenham(Graphics g,int x0, int y0, int x1, int y1) {
		/* Bresenham Algorithm */
		x0 = x0*test.SCALE + test.b_width/2;
		x1 = x1*test.SCALE + test.b_width/2;
		y0 = -1*y0*test.SCALE + test.b_height/2;
		y1 = -1*y1*test.SCALE + test.b_height/2;

		// dx > dy�� ���(m < 1)
		if (Math.abs(y1-y0) < Math.abs(x1-x0)) {
			int m_new = 2 * (y1 - y0);
			int slope_error_new = m_new + (x1 - x0); //������p0
			double ratio = 0.0;
			
			g.fillRect(Math.round(x0/test.SCALE)*test.SCALE,Math.round(y0/test.SCALE)*test.SCALE,test.SCALE,test.SCALE);   // setPixel()
			
			for (int x = x0, y = y0; x <= x1; x++) 
			{ 
				g.fillRect(Math.round(x/test.SCALE)*test.SCALE,Math.round(y/test.SCALE)*test.SCALE,test.SCALE,test.SCALE);   // setPixel()

				// Add slope to increment angle formed 
				slope_error_new += m_new;

				// Slope error reached limit, time to 
				// increment y and update slope error. 
				if (slope_error_new <= 0) 
				{ 
					y--; // ������ ��ǥ�� y���� �����Ǿ������Ƿ�
					slope_error_new += 2 * (x1 - x0); 
				} 
				/*
				//g.fillRect(Math.round(x/test.SCALE)*test.SCALE,Math.round(y/test.SCALE)*test.SCALE,test.SCALE,test.SCALE);   // setPixel()

				// Add slope to increment angle formed 
				slope_error_new += m_new;
				
				// ������ ������ �������� ������
				//if (x0!=x1) {
				int tmpy = (y1-y0)/(x1-x0)*(x*test.SCALE-x0*test.SCALE)+y0;
				//}
				ratio = tmpy-(Math.round(tmpy/test.SCALE)*test.SCALE);
				System.out.println("ratio = " + ratio);

				// Slope error reached limit, time to 
				// increment y and update slope error. 
				if (slope_error_new <= 0) 
				{ 
					y--; // ������ ��ǥ�� y���� �����Ǿ������Ƿ�
					g.setColor(new Color((int)(0+255*ratio)));
					g.fillRect(Math.round(x/test.SCALE)*test.SCALE,Math.round(y-1/test.SCALE)*test.SCALE,test.SCALE,test.SCALE);   // setPixel()
					g.setColor(new Color((int)(0+255*(1-ratio))));
					g.fillRect(Math.round(x/test.SCALE)*test.SCALE,Math.round(y/test.SCALE)*test.SCALE,test.SCALE,test.SCALE);   // setPixel()
					slope_error_new += 2 * (x1 - x0); 
				} 
				else {
					g.setColor(new Color((int)(0+255*ratio)));
					g.fillRect(Math.round(x/test.SCALE)*test.SCALE,Math.round(y/test.SCALE)*test.SCALE,test.SCALE,test.SCALE);   // setPixel()
					g.setColor(new Color((int)(0+255*(1-ratio))));
					g.fillRect(Math.round(x/test.SCALE)*test.SCALE,Math.round(y-1/test.SCALE)*test.SCALE,test.SCALE,test.SCALE);   // setPixel()
				}*/
			}
		}
		// dx <= dy�� ���(m > 1)
		else {
			int m_new = 2 * (x1 - x0); 
			int slope_error_new = m_new + (y1 - y0); //������p0

			for (int x = x0, y = y0; y >= y1; y--) 
			{ 
				g.fillRect(Math.round(x/test.SCALE)*test.SCALE,Math.round(y/test.SCALE)*test.SCALE,test.SCALE,test.SCALE);   // setPixel()

				// Add slope to increment angle formed 
				slope_error_new += m_new;

				// Slope error reached limit, time to 
				// increment y and update slope error. 
				if (slope_error_new >= 0) 
				{ 
					x++; 
					slope_error_new += 2 * (y1 - y0); 
				} 
			}
		}

		/* anti aliasing ����
		int dx = Math.abs(x1-x0), sx = x0<x1?1:-1;
		int dy = Math.abs(y1-y0), sy = y0<y1?1:-1;
		int err = dx - dy, e2, x2;
		int ed = (int) ((dx + dy ==0)? 1: (Math.sqrt((double)dx*dx + (double)dy*dy)));
		
		for (;;) {
			g.setColor(new Color(255*Math.abs(err-dx + dy)/ed));
			g.fillRect(Math.round(x0/test.SCALE)*test.SCALE,Math.round(y0/test.SCALE)*test.SCALE,test.SCALE,test.SCALE);
			e2 = err; x2 = x0;
			if (2*e2>=-dx) {
				break;
			}
			if (e2+dy<ed) {
				g.setColor(new Color(255*Math.abs(e2+dy)/ed));
				g.fillRect(Math.round(x0/test.SCALE)*test.SCALE,Math.round(y0+sy/test.SCALE)*test.SCALE,test.SCALE,test.SCALE);
				err -= dy; x0 += sx;
			}
			if (2*e2<=dy) {
				if (y0==y1) {
					break;
				}
				if (dx-e2<ed) {
					g.setColor(new Color(255*Math.abs(dx-e2)/ed));
					g.fillRect(Math.round(x2+sx/test.SCALE)*test.SCALE,Math.round(y0/test.SCALE)*test.SCALE,test.SCALE,test.SCALE);
					err += dx; y0 +=sy;
				}
			}
		}*/
		
	}

	/* �̵� ��ȯ �Լ� */
	public void move(Graphics g,int x0,int y0,int xEnd,int yEnd,int fix_x,int fix_y,double m_x, double m_y) { //�̵� �޼ҵ�
		double xf = fix_x;
		double yf = fix_y;
		double mx = m_x;
		double my = m_y;
		double xa = x0;
		double xb = xEnd;
		double ya = y0;
		double yb = yEnd;

		double[][] arrayOfMove = {
				{1, 0, 0},
				{0, 1, 0},
				{mx, my, 1}
		};

		double[][] array_a = {
				{xa, ya, 1}
		};

		double[][] array_b = {
				{xb, yb, 1}
		};       

		double[][] result_a = new double[1][3];
		double[][] result_b = new double[1][3];

		for(int i = 0; i < 1; i++) {
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 3; k++) {
					result_a[i][j] += (array_a[i][k] * arrayOfMove[k][j]);   
					result_b[i][j] += (array_b[i][k] * arrayOfMove[k][j]);
				}
			}
		}

		xa = result_a[0][0];
		ya = result_a[0][1];
		xb = result_b[0][0];
		yb = result_b[0][1];

		DDA(g,(int)xa, (int)ya, (int)xb, (int)yb);
		//Bresenham(g,(int)xa, (int)ya, (int)xb, (int)yb);
	}

	/* �����ϸ� ��ȯ �Լ� */
	public void scale(Graphics g,int x0,int y0,int xEnd,int yEnd,int fix_x,int fix_y,double s_x, double s_y) { //���� Scaling �޼ҵ�
		double xf = fix_x;
		double yf = fix_y;
		double sx = s_x;
		double sy = s_y;
		double xa = x0;
		double xb = xEnd;
		double ya = y0;
		double yb = yEnd;
		double[][] arrayOfScaling = {
				{sx, 0, 0},
				{0, sy, 0},
				{(1-sx)*xf, (1-sy)*yf, 1}
		};

		double[][] array_a = {
				{xa, ya, 1}
		};

		double[][] array_b = {
				{xb, yb, 1}
		};

		double[][] result_a = new double[1][3];
		double[][] result_b = new double[1][3];

		for(int i = 0; i < 1; i++) {
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 3; k++) {
					result_a[i][j] += (array_a[i][k] * arrayOfScaling[k][j]);   
					result_b[i][j] += (array_b[i][k] * arrayOfScaling[k][j]);
				}
			}
		}

		xa = result_a[0][0];
		ya = result_a[0][1];
		xb = result_b[0][0];
		yb = result_b[0][1];

		DDA(g,(int)xa, (int)ya, (int)xb, (int)yb);
		//Bresenham(g,(int)xa, (int)ya, (int)xb, (int)yb);
	}

	/* ������ ���� Rotation �Լ� : �Ǻ�����Ʈ�� �������� ������ ������ŭ ȸ�� */
	public void rotate(Graphics g,int x0,int y0,int xEnd,int yEnd,int fix_x,int fix_y,double s_x, double s_y, int degree) {
		double xf = fix_x;
		double yf = fix_y;
		double sx = s_x;
		double sy = s_y;
		double angle = degree;
		double xa = x0;
		double xb = xEnd;
		double ya = y0;
		double yb = yEnd;

		double cosX = Math.cos(Math.toRadians(angle));
		double sinX = Math.sin(Math.toRadians(angle));

		double[][] arrayOfRotation = {
				{cosX, sinX, 0},
				{-(sinX), cosX, 0},
				{0, 0, 1}
		};

		double[][]p_arrayOfRatation = {
				{cosX, sinX, 0},
				{-(sinX), cosX,0},
				{(-xf * cosX + (yf * sinX) + xf),
					(-xf * sinX - (yf * cosX) + yf),1}
		};       

		double[][] array_a = {
				{xa, ya, 1}
		};

		double[][] array_b = {
				{xb, yb, 1}
		};       

		double[][] result_a = new double[1][3];
		double[][] result_b = new double[1][3];

		for(int i = 0; i < 1; i++) {
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 3; k++) {
					result_a[i][j] += (array_a[i][k] * arrayOfRotation[k][j]);   
					result_b[i][j] += (array_b[i][k] * arrayOfRotation[k][j]);
				}
			}
		}

		xa = result_a[0][0];
		ya = result_a[0][1];
		xb = result_b[0][0];
		yb = result_b[0][1];

		DDA(g,(int)xa,(int)ya, (int)xb,(int)yb);
		//Bresenham(g,(int)xa, (int)ya, (int)xb, (int)yb);
	}
	
	/* �� �׸��� �޼ҵ� */
	public void drawCircle(Graphics g,int x_c,int y_c,int radius, int moveX, int moveY, double scale) {
		int x_center = x_c+moveX;
		int y_center = -1*(y_c+moveY);
		int r = (int)(radius*scale);

		int x = 0;
		int y = r;
		int p = 1 - r;

		halfAQuarterOfCircle(g,x_center,y_center,x,-1*y);
		
		while (x < y) {
			x++;
			if(p < 0) {
				p += 2 * x + 1;
			}
			else {
				y--;
				p += 2 * (x - y) + 1;
			}
			halfAQuarterOfCircle(g,x_center,y_center,x,-1*y);
		}
	}
	
	/* ���� 1/8�� �׷��ִ� �޼ҵ�-���⼭ Ȯ���ϰ� ����������� ���� ���ڰ� ���� */
	public void halfAQuarterOfCircle(Graphics g, int x_center, int y_center, int x, int y) {
		g.fillRect(Math.round(x_center + x)*test.SCALE+test.b_width/2,Math.round(y_center + y)*test.SCALE+test.b_height/2,test.SCALE,test.SCALE);   // setPixel(x_center + x, y_center + y)
		g.fillRect(Math.round(x_center - x)*test.SCALE+test.b_width/2,Math.round(y_center + y)*test.SCALE+test.b_height/2,test.SCALE,test.SCALE);   // setPixel(x_center - x, y_center + y)
		g.fillRect(Math.round(x_center + x)*test.SCALE+test.b_width/2,Math.round(y_center - y)*test.SCALE+test.b_height/2,test.SCALE,test.SCALE);   // setPixel(x_center + x, y_center - y)
		g.fillRect(Math.round(x_center - x)*test.SCALE+test.b_width/2,Math.round(y_center - y)*test.SCALE+test.b_height/2,test.SCALE,test.SCALE);   // setPixel(x_center - x, y_center - y)
		g.fillRect(Math.round(x_center + y)*test.SCALE+test.b_width/2,Math.round(y_center + x)*test.SCALE+test.b_height/2,test.SCALE,test.SCALE);   // setPixel(x_center + y, y_center + x)
		g.fillRect(Math.round(x_center - y)*test.SCALE+test.b_width/2,Math.round(y_center + x)*test.SCALE+test.b_height/2,test.SCALE,test.SCALE);   // setPixel(x_center - y, y_center + x)
		g.fillRect(Math.round(x_center + y)*test.SCALE+test.b_width/2,Math.round(y_center - x)*test.SCALE+test.b_height/2,test.SCALE,test.SCALE);   // setPixel(x_center + y, y_center - x)
		g.fillRect(Math.round(x_center - y)*test.SCALE+test.b_width/2,Math.round(y_center - x)*test.SCALE+test.b_height/2,test.SCALE,test.SCALE);   // setPixel(x_center - y, y_center - x)
	}
	
	/* �޼ҵ� �������̵�, ���� ���� �̵� ��ȯ �Լ� */
	public void move(Graphics g,int x_c,int y_c,int radius,int m_x, int m_y) { // �̵� �޼ҵ� �ߺ� ����_��
		drawCircle(g,x_c, y_c, radius, m_x, m_y, 1);
	}

	/* �޼ҵ� �������̵�, ���� ���� �����ϸ� ��ȯ �Լ� */
	public void scale(Graphics g,int x_c,int y_c,int radius, double s) { // Ȯ�� �޼ҵ� �ߺ� ����_��
		drawCircle(g,x_c, y_c, radius, 0, 0, s);
	}
	
	/* Ŭ���� ����Ȯ�� �޼ҵ� */
	public boolean clipTest(double p, double q, Path path) {
		double r = 0.0;
		boolean returnValue = true;
		
		if (p < 0.0) {
			r = q / p;
			if (r > path.Uset[1]) {
				returnValue = false;
			}
			else {
				if (r > path.Uset[0]) {
					path.Uset[0] = r;
				}
			}
		}
		else {
			if (p > 0.0) {
				r = q / p;
				if (r < path.Uset[0]) {
					returnValue = false;
				}
				else if (r < path.Uset[1]) {
					path.Uset[1] = r;
				}
			}
			else {
				/* p = 0�� ��� ������ Ŭ���� ������� �����ϴ� */
				if (q < 0.0) {
					/* ������ Ŭ���� ��� �ۿ� ��ġ�Ѵ� */
					returnValue = false;
				}
			}
		}
		
		return returnValue;
	}
	
	/* Ŭ�����Ͽ� �׸����� �ϴ� �Լ� */
	public void lineClipLiangBarsk(Graphics g, int winMinX, int winMaxX, int winMinY, int winMaxY, String algorithm, Path path) {
		int dx = path.points[2]-path.points[0];	// x1 - x0
		int dy = 0;
		double xStart = path.points[0], yStart = path.points[1], xEnd = path.points[2], yEnd = path.points[3];
		
		if (clipTest(-dx, path.points[0]-winMinX,path)) {
			if (clipTest(dx, winMaxX-path.points[0],path)) {
				dy = path.points[3]-path.points[1];	// y1 - y0
				if (clipTest(-dy,path.points[1]-winMinY,path)) {
					if (clipTest(dy,winMaxY-path.points[1],path)) {
						if (path.Uset[1] < 1.0) {
							xEnd = path.points[0] + path.Uset[1]*dx;
							yEnd = path.points[1] + path.Uset[1]*dy;
						}
						if (path.Uset[0] > 0.0) {
							xStart = path.points[0] + path.Uset[0]*dx;
							yStart = path.points[1] + path.Uset[0]*dy;
						}

						//�극���� or DDA
						if (algorithm.equals("DDA")) {
							DDA(g,(int)xStart,(int)yStart,(int)xEnd,(int)yEnd);
						}
						else {
							Bresenham(g,(int)xStart,(int)yStart,(int)xEnd,(int)yEnd);
						}
					}
				}
			}
		}
	}
}