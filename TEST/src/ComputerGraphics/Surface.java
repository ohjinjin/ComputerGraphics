package ComputerGraphics;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.JPanel;

// ĵ������ ��ӹ޾�  Surface Ŭ���� ����
public class Surface extends JPanel
{
	public ArrayList <Path> pathList= new ArrayList<>();	//circle�� path�Ѵ� �����Ҽ� �ֵ��� ����Ŭ������ Object Ŭ���� ��ü ���������͸� Ȱ���ϴ� arraylist�� ����//////////////////////////////
	public ArrayList <Circle> circleList = new ArrayList<>();
	public double[] transList= new double[7];
	public String algorithm = "DDA";
	public boolean isDraw = false;

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

		/* ����� �׷��ֱ� *///
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

		if (isDraw) {
			if (algorithm.equals("DDA")) {	// Line via DDA�� ���
				for (Path eachPath : pathList) {
					g.setColor(Color.BLACK);
					DDA(g,eachPath.points[0],eachPath.points[1],eachPath.points[2],eachPath.points[3]);
					g.setColor(Color.RED);
					scale(g,eachPath.points[0],eachPath.points[1],eachPath.points[2],eachPath.points[3],(int)transList[0],(int)transList[1],transList[4],transList[5]);
					g.setColor(Color.GREEN);
					rotate(g,eachPath.points[0],eachPath.points[1],eachPath.points[2],eachPath.points[3],(int)transList[0],(int)transList[1],transList[4],transList[5],(int)transList[6]);
					g.setColor(Color.BLUE);
					move(g,eachPath.points[0],eachPath.points[1],eachPath.points[2],eachPath.points[3],(int)transList[0],(int)transList[1],transList[2],transList[3]);
				}
			}
			
			else if (algorithm.equals("Bresenham")) {	// Line via Bresenham�� ���
				for (Path eachPath : pathList) {
					g.setColor(Color.BLACK);
					Bresenham(g,eachPath.points[0],eachPath.points[1],eachPath.points[2],eachPath.points[3]);
					g.setColor(Color.RED);
					scale(g,eachPath.points[0],eachPath.points[1],eachPath.points[2],eachPath.points[3],(int)transList[0],(int)transList[1],(int)transList[4],(int)transList[5]);
					g.setColor(Color.GREEN);
					rotate(g,eachPath.points[0],eachPath.points[1],eachPath.points[2],eachPath.points[3],(int)transList[0],(int)transList[1],(int)transList[4],(int)transList[5],(int)transList[6]);
					g.setColor(Color.BLUE);
					move(g,eachPath.points[0],eachPath.points[1],eachPath.points[2],eachPath.points[3],(int)transList[0],(int)transList[1],transList[2],transList[3]);
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

	}

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
	}


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
				{(1-sx)*xf, (1-sy)*yf, 1}///////////Ȯ�� �ʿ�
		};

		double[][] array_a = {
				{xa, ya, 1}
		};

		double[][] array_b = {
				{xb, yb, 1}
		};

		double[][] result_a = new double[1][3];
		double[][] result_b = new double[1][3];
		System.out.println("��xa : "+ xa + " ya : " + ya);
		System.out.println("��xb : "+ xb + " yb : " + yb);
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
	}

	//���� Rotation �޼ҵ� : ������ ������ŭ ȸ��
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
		System.out.println("����xa : "+ xa + " ya : " + ya);
		System.out.println("����xb : "+ xb + " yb : " + yb);
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
	}
	
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
	
	public void move(Graphics g,int x_c,int y_c,int radius,int m_x, int m_y) { // �̵� �޼ҵ� �ߺ� ����_��
		drawCircle(g,x_c, y_c, radius, m_x, m_y, 1);
	}

	public void scale(Graphics g,int x_c,int y_c,int radius, double s) { // Ȯ�� �޼ҵ� �ߺ� ����_��
		drawCircle(g,x_c, y_c, radius, 0, 0, s);
	}
}