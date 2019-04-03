import java.awt.*;
import javax.swing.JFrame;
import java.util.Scanner;
import java.lang.*;

// ĵ������ ��ӹ޾� DDA Ŭ���� ����
public class Bresenham extends Canvas
{
	public static final int SCALE = 10;	// ��ô�� ���� �뵵 ��� ����
	public static final int b_width =150*SCALE;	// ������ ���� ũ�� ��� ����
	public static final int b_height =100*SCALE;	// ������ ���� ũ�� ��� ����
	static int  x0, y0,x1,y1;

	/* DDA ��ü ������ */
	public Bresenham(int x0,int y0,int x1,int y1){
		this.x0=x0;
		this.y0=y0;
		this.x1=x1;
		this.y1=y1;
	}

	/* ĵ������ �׷��ֱ� */
	public void paint(Graphics g)
	{
		/* ����� �׷��ֱ� *///
		for(int i = 0; i < b_height; i += SCALE) {
			if (i == b_height/2) {
				Graphics2D g2d = (Graphics2D)g;
				g2d.setStroke(new BasicStroke(2));	// ����� �׷��� �� x��, y�� (2���� ��������) �׷��ֱ� ��Ŭ���� ��ǥ ���� ǥ�����ַ���
				g.drawLine(0, i, (int) b_width, i);
				g2d.setStroke(new BasicStroke(1));
			}
			else {
				g.drawLine(0, i, (int) b_width, i);

			}
		}

		for(int i = 0; i < b_width; i += SCALE) {
			if (i == b_width/2) {
				Graphics2D g2d = (Graphics2D)g;
				g2d.setStroke(new BasicStroke(2));
				g.drawLine(i, 0, i, (int) b_height);
				g2d.setStroke(new BasicStroke(1));
			}
			else {
				g.drawLine(i, 0, i, (int) b_height);				
			}
		}

		/* Bresenham Algorithm */
		// dx > dy�� ���(m < 1)
		if (Math.abs(y1-y0) < Math.abs(x1-x0)) {
			int m_new = 2 * (y1 - y0);
			int slope_error_new = m_new + (x1 - x0); //������p0

			for (int x = x0, y = y0; x <= x1; x++) 
			{ 
				g.fillRect(Math.round(x/SCALE)*SCALE,Math.round(y/SCALE)*SCALE,SCALE,SCALE);	// setPixel()

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
				g.fillRect(Math.round(x/SCALE)*SCALE,Math.round(y/SCALE)*SCALE,SCALE,SCALE);	// setPixel()

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

	/* ���� �޼ҵ� */
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter first end x0 and y0");
		int x0=sc.nextInt()*SCALE + b_width/2;
		int y0=-1*sc.nextInt()*SCALE + b_height/2;
		System.out.println("Enter last end x1 and y1");
		int x1=sc.nextInt()*SCALE + b_width/2;
		int y1=-1*sc.nextInt()*SCALE + b_height/2;
		Bresenham d=new Bresenham(x0,y0,x1,y1);
		JFrame f=new JFrame();
		f.add(d);
		f.setSize(b_width,b_height);
		f.setVisible(true);
	}
}