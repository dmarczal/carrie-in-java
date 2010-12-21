package br.ufpr.c3sl.view.util;

import java.awt.AWTEventMulticaster;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import java.net.URL;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImageButton extends JPanel {
	protected ActionListener actionListener = null;
	int w, h;
	boolean clicked;
	boolean down;
	boolean enabled;
	Image UPimage;
	Image DOWNimage;
	Image disabledimage;

	public ImageButton(URL up_b, URL down_b) {
		clicked = false;
		down = false;
		enabled = true;
		initImage(up_b, down_b);
		setSize(w, h);
		addMouseListener(new ImageButtonMouseListener());
		addMouseMotionListener(new ImageButtonMouseMotionListener());
	}
	
	public ImageButton(Image up, Image down) {
		this.clicked = false;
		this.down = false;
		this.enabled = true;
		initImage(up, down);
		setSize(w, h);
		addMouseListener(new ImageButtonMouseListener());
		addMouseMotionListener(new ImageButtonMouseMotionListener());
	}

	public void initImage(URL up, URL down) {
		UPimage = getToolkit().getImage(up);
		DOWNimage = getToolkit().getImage(down);
		initImage(UPimage, DOWNimage);
	}
	
	public void initImage(Image up, Image down) {
		MediaTracker tracker;
		try {
			UPimage = up;
			DOWNimage = down;
			tracker = new MediaTracker(this);
			tracker.addImage(UPimage, 0);
			tracker.addImage(DOWNimage, 1);
			tracker.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		disabledimage = createImage(new FilteredImageSource(
				UPimage.getSource(), new ImageButtonDisableFilter()));
		w = UPimage.getWidth(this);
		h = UPimage.getHeight(this);
	}
	
	public void paint(Graphics g) {
		g.clearRect(0, 0, getSize().width, getSize().height);
		g.setColor(getBackground());
		g.fillRect(0, 0, getSize().width, getSize().height);
		if (down) {
			g.drawImage(DOWNimage, 0, 0, this);
		} else {
			if (enabled) {
				g.drawImage(UPimage, 0, 0, this);
			} else {
				g.drawImage(disabledimage, 0, 0, this);
			}
		}
	}

	public void setEnabled(boolean b) {
		enabled = b;
		repaint();
	}

	public boolean isEnabled() {
		return (enabled);
	}

	public void addActionListener(ActionListener l) {
		actionListener = AWTEventMulticaster.add(actionListener, l);
	}

	public void removeActionListener(ActionListener l) {
		actionListener = AWTEventMulticaster.remove(actionListener, l);
	}

	public class ImageButtonMouseListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			Point p = e.getPoint();
			if ((p.x < w) && (p.y < h) && (p.x > 0) && (p.y > 0)
					&& (enabled == true)) {
				clicked = true;
				down = true;
				repaint();
			}
		}

		public void mouseReleased(MouseEvent e) {
			Point p = e.getPoint();
			if (down) {
				down = false;
				repaint();
			}
			if ((p.x < w) && (p.y < h) && (p.x > 0) && (p.y > 0)
					&& (clicked == true)) {
				ActionEvent ae = new ActionEvent(e.getComponent(), 0, "click");
				if (actionListener != null) {
					actionListener.actionPerformed(ae);
				}
			}
			clicked = false;
		}
	}

	public class ImageButtonMouseMotionListener extends MouseMotionAdapter {
		public void mouseDragged(MouseEvent e) {
			Point p = e.getPoint();
			if ((p.x < w) && (p.y < h) && (p.x > 0) && (p.y > 0)
					&& (clicked == true)) {
				if (down == false) {
					down = true;
					repaint();
				}
			} else {
				if (down == true) {
					down = false;
					repaint();
				}
			}
		}
	}

	public Dimension getPreferredSize() {
		return (new Dimension(UPimage.getWidth(this), UPimage.getHeight(this)));
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	class ImageButtonDisableFilter extends RGBImageFilter {
		public ImageButtonDisableFilter() {
			canFilterIndexColorModel = true;
		}

		public int filterRGB(int x, int y, int rgb) {
			return (rgb & ~0xff000000) | 0x80000000;
		}
	}
}
