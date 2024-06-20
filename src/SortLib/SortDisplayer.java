package SortLib;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class SortDisplayer {

	SortTracker st;
	int index = 0;
	MyPanel panel = null;
	int maxLen = 0;

	public SortDisplayer(SortTracker st) {
		this.st = st;

		for (SortSolution s : st.solutions) {
			if (s.getActionCount() > maxLen) {
				maxLen = s.getActionCount();
			}
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private void createAndShowGUI() {
		JFrame f = new JFrame("Swing Paint Demo");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new MyPanel(st);
		f.add(panel);
		f.pack();
		f.setVisible(true);

		registerKeystrokes(f);
	}
	
	private void registerKeystrokes(JFrame f) {
		registerRightArrow(f);
		registerLeftArrow(f);
		registerUpArrow(f);
		registerDownArrow(f);
		registerEscape(f);
	}

	private void registerRightArrow(JFrame f) {
		KeyStroke rightKeyStroke = KeyStroke.getKeyStroke("RIGHT");
		Action rightAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
//				System.out.println("Right");
				adjustIndex(1);
			}
		};

	    f.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(rightKeyStroke, "RIGHT");
	    f.getRootPane().getActionMap().put("RIGHT", rightAction);
	}

	private void registerLeftArrow(JFrame f) {
		KeyStroke leftKeyStroke = KeyStroke.getKeyStroke("LEFT");
		Action leftAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
//				System.out.println("Left");
				adjustIndex(-1);
			}
		};

	    f.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(leftKeyStroke, "LEFT");
	    f.getRootPane().getActionMap().put("LEFT", leftAction);
	}

	private void registerUpArrow(JFrame f) {
		KeyStroke upKeyStroke = KeyStroke.getKeyStroke("UP");
		Action upAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
//				System.out.println("Up");
				adjustIndex(10);
			}
		};

	    f.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(upKeyStroke, "UP");
	    f.getRootPane().getActionMap().put("UP", upAction);
	}

	private void registerDownArrow(JFrame f) {
		KeyStroke downKeyStroke = KeyStroke.getKeyStroke("DOWN");
		Action downAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
//				System.out.println("Down");
				adjustIndex(-10);
			}
		};

	    f.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(downKeyStroke, "DOWN");
	    f.getRootPane().getActionMap().put("DOWN", downAction);
	}

	private void registerEscape(JFrame f) {
		KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke("ESCAPE");
		Action escapeAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("ESCAPE");
			}
		};

	    f.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
	    f.getRootPane().getActionMap().put("ESCAPE", escapeAction);
	}
	
	private void adjustIndex(int adjustment) {
		int tmp = index;
		
		switch (adjustment) {
		case 1:
		case -1:
			tmp += adjustment;
			break;
		default:
			tmp += maxLen/adjustment;
			break;
		}
		
		if (tmp < 0) {
			tmp = 0;
		}
		else if (tmp > maxLen) {
			tmp = maxLen;
		}
		
		if (tmp != index) {
			index = tmp;
			panel.update(index);
		}
	}
}

class MyPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	SortTracker st;
	int index = 0;
	Rectangle rect = new Rectangle();

	public MyPanel(SortTracker st) {
		this.st = st;

		int maxLen = 0;
		for (SortSolution s : st.solutions) {
			if (s.getActionCount() > maxLen) {
				maxLen = s.getActionCount();
			}
		}
		setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	public void update(int index) {
		this.index = index;
		repaint();
	}

	public Dimension getPreferredSize() {
		return new Dimension(250, 200);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw Text
		g.getClipBounds(rect);
		g.setFont(new Font("TimesRoman", Font.BOLD, 18));
//		System.out.println("rect: "+rect.height+","+rect.width);
		int h = rect.height;
		int graphWidth = rect.width/st.solutions.size();
		int wOffset = 0;
		
		for (SortSolution s : st.solutions) {
			
			EState es = s.getPlayback(index);
			int w = graphWidth/es.e.length;
			int gap = 1;
			if (w < 5) {
				gap = 0;
			}
			int vScale = (h-120)/es.e.length;
				
//			System.out.println("graphWidth: "+graphWidth+","+w);
			g.setColor(Color.BLACK);
			g.drawString(s.getTitle(), wOffset+10, 20);
			g.drawString(s.getDescription(), wOffset+10, 40);

			for (int i = 0; i < es.e.length; i++) {
				if ((st.style < 3) && (es.e[i] == i+1)) {
					g.setColor(Color.GRAY);
				}
				else {
					g.setColor(Color.LIGHT_GRAY);
				}
				if (i==es.action.a || i==es.action.b) {
					g.setColor(Color.RED);
				}
	
				int height = es.e[i]*vScale;
				int floor = h-60;
				g.fillRect(wOffset+i * w, floor-height, w-gap, height);
			}
			String actionDesc = "";
			switch (es.action.type) {
			case 0:
				actionDesc = "Initial State "+es.action.note;
				break;
			case 1:
				actionDesc = "Compare("+es.action.a+","+es.action.b+") "+es.action.note;
				break;
			case 2:
				actionDesc = "Swap("+es.action.a+","+es.action.b+") "+es.action.note;
				break;
			case 3:
				actionDesc = "Completed Successfully "+es.action.note;
				break;
			case 4:
				actionDesc = "Completed Incorrectly "+es.action.note;
				break;
			}
			
			int tmp = Math.min(index, s.getActionCount());
			g.setColor(Color.BLACK);
			g.drawString(tmp+": "+actionDesc, wOffset+10, h-30);
			
			if (wOffset > 0) {
				g.drawLine(wOffset, 0, wOffset, h);
			}
			
			wOffset += graphWidth;
		}
	}
}
