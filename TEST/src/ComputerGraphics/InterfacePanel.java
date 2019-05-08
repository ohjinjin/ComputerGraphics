package ComputerGraphics;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

<<<<<<< HEAD
/* 인터페이스를 위한 프레임 */
=======
>>>>>>> refs/remotes/origin/master
public class InterfacePanel extends JFrame{
	//ArrayList<Object> objList = new ArrayList<>();
	Surface surface = new Surface();
	JPanel panel = new JPanel();

	JLabel x1 = new JLabel("xa"); 
	JLabel y1 = new JLabel("ya");
	JLabel x2 = new JLabel("xb"); 
	JLabel y2 = new JLabel("yb"); 
	JLabel radius = new JLabel("radius");
	JLabel Xf = new JLabel("fixed point_x");
	JLabel Yf = new JLabel("fixed point_y");
	JLabel M_x = new JLabel("move_x");
	JLabel M_y = new JLabel("move_y");
	JLabel S_x = new JLabel("scale_x");
	JLabel S_y = new JLabel("scale_y");
	JLabel angle_L = new JLabel("Angle");

	JTextField xa = new JTextField(3);
	JTextField ya = new JTextField(3);
	JTextField xb = new JTextField(3);
	JTextField yb = new JTextField(3);
	JTextField r = new JTextField(3);
	JTextField x_f = new JTextField(3);
	JTextField y_f = new JTextField(3);
	JTextField m_x = new JTextField(3);
	JTextField m_y = new JTextField(3);
	JTextField s_x = new JTextField(3);
	JTextField s_y = new JTextField(3);
	JTextField Angle = new JTextField(3);

	JButton Draw = new JButton("Draw");
	JButton Add = new JButton("Add");
	JButton Clear = new JButton("Clear");
	
	JRadioButton Line = new JRadioButton("Line");
	JRadioButton Circle = new JRadioButton("Circle");
	JRadioButton DDA = new JRadioButton("DDA");
	JRadioButton Bresenham = new JRadioButton("Bresenham");

	ButtonGroup groupOfAlgorithm = new ButtonGroup();
	ButtonGroup groupOfShape = new ButtonGroup();

	int coordX1 = 0, coordY1 = 0, coordX2 = 0, coordY2 = 0;

	int xf = 0, yf = 0; //고정점
	int mx = 0, my = 0;   // 얼마나 이동할 것인지
	double sx = 0, sy = 0; //몇 배 확대할 것인지  
	double angle = 0;

	//생성자
	public InterfacePanel() {
		setSize(test.b_width+100, test.b_height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		groupOfAlgorithm.add(DDA);
		groupOfAlgorithm.add(Bresenham);
		DDA.setSelected(true);   // default
		groupOfShape.add(Line);
		groupOfShape.add(Circle);
		Line.setSelected(true);   // default  
		r.setEnabled(false);	// default

		Circle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				xb.setEnabled(false);
				yb.setEnabled(false);
				r.setEnabled(true);	// 중심점 x,y와 반지름 필드만 활성화
				DDA.setEnabled(false);
				Bresenham.setEnabled(false);
			}
		});
		
		Line.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				xb.setEnabled(true);
				yb.setEnabled(true);
				r.setEnabled(false);	// 반지름 필드를 비활성화시키고, 두 좌표점에 대한 필드를 활성화
				DDA.setEnabled(true);
				Bresenham.setEnabled(true);
			}
		});

		//Add 버튼을 누르면 좌표 값을 배열에 저장
		Add.addActionListener(new ActionListener() {
			@Override 

			public void actionPerformed(ActionEvent e) {
				if(e.getSource().equals(Add)) {
					//사용자가 텍스트 필드에 입력한 값을 정수로 바꾼 후 저장
					coordX1 = Integer.parseInt(xa.getText());
					coordY1 = Integer.parseInt(ya.getText());
					if (Line.isSelected()) {
						coordX2 = Integer.parseInt(xb.getText());
						coordY2 = Integer.parseInt(yb.getText());
						surface.pathList.add(new Path(coordX1,coordY1,coordX2,coordY2));   // path를 추가만 해준다
						drawSetToDefault();
					}
					//원의 경우
					else{
						surface.circleList.add(new Circle(coordX1,coordY1,Integer.parseInt(r.getText())));	// Circle을 추가만 해준다.
						drawSetToDefault();
					}

					// 인터페이스 폼 초기화
					xa.setText("");
					ya.setText("");
					xb.setText("");
					yb.setText("");
					r.setText("");
				}
			}
		});

		//그리기 버튼 이벤트 처리
		Draw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource().equals(Draw)) {//사용자가 Draw 버튼을 누르면 이벤트 실행
					if (Line.isSelected()) {
						if (DDA.isSelected()) {
							surface.refreshSurface("DDA");
						}
						else {// if (Bresenham.isSelected())
							surface.refreshSurface("Bresenham");
						}
					}
					else {	// Circle의 경우
						surface.refreshSurface("Circle");
					}
					//사용자가 텍스트 필드에 입력한 고정점을 정수로 바꾼 후 저장
					surface.transList[0] = Integer.parseInt(x_f.getText());
					surface.transList[1] = Integer.parseInt(y_f.getText());
					//어느방향으로 얼만큼 이동할 것인지
					surface.transList[2] = Double.parseDouble(m_x.getText());
					surface.transList[3] = Double.parseDouble(m_y.getText());
					//몇배 확장, 축소 할 것인지
					surface.transList[4] = Double.parseDouble(s_x.getText());
					surface.transList[5] = Double.parseDouble(s_y.getText());
					//각도
					surface.transList[6] = Double.parseDouble(Angle.getText());

					// 인터페이스 폼 초기화
					xa.setText("");
					ya.setText("");
					xb.setText("");
					yb.setText("");
					x_f.setText("");
					y_f.setText("");
					m_x.setText("");
					m_y.setText("");
					s_x.setText("");
					s_y.setText("");
					Angle.setText("");

				}
			}
		});

		Clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource().equals(Clear)) {
					surface.refreshSurface("");
					surface.pathList.clear();
					surface.circleList.clear();
				}
			}
		});

		panel.add(Line);
		panel.add(Circle);
		panel.add(x1);
		panel.add(xa);      
		panel.add(y1);
		panel.add(ya);
		panel.add(x2);
		panel.add(xb);
		panel.add(y2);
		panel.add(yb);
		panel.add(radius);
		panel.add(r);
		panel.add(Add);
		panel.add(Xf);
		panel.add(x_f);
		panel.add(Yf);
		panel.add(y_f);
		panel.add(M_x);
		panel.add(m_x);
		panel.add(M_y);
		panel.add(m_y);
		panel.add(S_x);
		panel.add(s_x);
		panel.add(S_y);
		panel.add(s_y);
		panel.add(angle_L);
		panel.add(Angle);
		panel.add(Draw);
		panel.add(Clear);
		panel.add(DDA);
		panel.add(Bresenham);

		add(surface,BorderLayout.CENTER);
		add(panel,BorderLayout.PAGE_END);
		setVisible(true);   
	}
	
	public void drawSetToDefault() {
		x_f.setText("0");
		y_f.setText("0");
		m_x.setText("0");
		m_y.setText("0");
		s_x.setText("1");
		s_y.setText("1");
		Angle.setText("0");
	}
}