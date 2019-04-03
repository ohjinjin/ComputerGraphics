import java.awt.*;
import javax.swing.JFrame;
import java.util.Scanner;
import java.lang.*;

// 캔버스를 상속받아 DDA 클래스 정의..
public class DDA extends Canvas
{
	public static final int SCALE = 10;	// 축척을 위한 용도 상수 선언
	public static final int b_width =150*SCALE;	// 윈도우 가로 크기 상수 선언
	public static final int b_height =100*SCALE;	// 윈도우 세로 크기 상수 선언
	static int  x0, y0,x1,y1;
	
	/* DDA 객체 생성자 */
	public DDA(int x0,int y0,int x1,int y1){
		this.x0=x0;
		this.y0=y0;
		this.x1=x1;
		this.y1=y1;
	}

	/* 캔버스에 그려주기 */
	public void paint(Graphics g)
	{
		/* 방안지 그려주기 */
		for(int i = 0; i < b_height; i += SCALE) {
			if (i == b_height/2) {
				Graphics2D g2d = (Graphics2D)g;
				g2d.setStroke(new BasicStroke(2));	// 방안지 그려줄 때 x축, y축 (2차원 기저벡터) 그려주기 유클리드 좌표 원점 표시해주려고
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
		
		/* DDA 알고리즘 */
		int dx=x1-x0,dy=y1-y0;	// deltaX, deltaY
		float xIncrement, yIncrement, steps, x=x0,	y=y0;

		if (dx>dy) {	// 0<= m <=1
			steps=Math.abs(dx)*SCALE;
		}
		else{	// 1< m
			steps=Math.abs(dy)*SCALE;
		}
		xIncrement=dx/steps;
		yIncrement=dy/steps;

		g.fillRect(x0,y0,SCALE,SCALE);	// 시작점 setPixel()
		while(steps!=0)	{
			steps--;
			x += xIncrement;
			y += yIncrement;
			g.fillRect(Math.round(x/SCALE)*SCALE,Math.round(y/SCALE)*SCALE,SCALE,SCALE);	// setPixel()
		}
	}
	
	/* 메인 메소드 */
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter first end x0 and y0");
		int x0=sc.nextInt()*SCALE + b_width/2;
		int y0=-1*sc.nextInt()*SCALE + b_height/2;
		System.out.println("Enter last end x1 and y1");
		int x1=sc.nextInt()*SCALE + b_width/2;
		int y1=-1*sc.nextInt()*SCALE + b_height/2;
		DDA d=new DDA(x0,y0,x1,y1);
		JFrame f=new JFrame();
		f.add(d);
		f.setSize(b_width,b_height);
		f.setVisible(true);
	}
}