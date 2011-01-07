package org.c3sl.ufpr.br.fractal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class Drawing extends JPanel {

	private static final long serialVersionUID = -8684100207687105447L;
	/* Reference to a fractal */
    Fractal f, thumb_f;
    Point cell = new Point(-1, -1);;
    double[] bPoint, fPoint, tPoint;
    double scale;
    String tip;
    double[] bg_first_seg = new double[4];
    boolean shadowed, paintThumb = false, paintExercise = true;
    
    public Drawing (boolean shadowed) {
    	this.shadowed = shadowed;
    	setBorder(BorderFactory.createLineBorder(Color.red));
    }
 
    /* Set the Fractal reference */
    public void setFractal(Fractal f) { 
        this.f = f;
    }
    
    /* Set the boolean value in variable */
    public void setPaintWithColor(boolean paintExercise){
    	this.paintExercise = paintExercise;
    }
    
    public double getScale () {
    	return scale;
    }
    
    
    public Dimension drawFractal(Fractal f, Graphics g, Color c) {
        return drawFractal(f, g, c, new Dimension(0, 0));
    }
   
    public Dimension drawFractal(Fractal f, Graphics g, Color c, Dimension bg_d) {
        double delta_x = 0, delta_y = 0;
       
        /* Retrieve the line segments list of the Fractal */
        Vector<?> list = f.getSegmentList();

        /* Check for errors in the Fractal construction */
        if(list == null) {
            g.drawString("Erro na construcao do fractal!", 20, 20);
            return null;
        }

        /* Retrieve the bounds of the fractal */
        double x = f.getX();
        double y = f.getY();
        double width = f.getWidth();
        double height = f.getHeight();

        /* If there is no line segment in the list, doesn't need to draw */
        if(height == 0 && width == 0)
            return null;
        
        
        String axiom = f.getAxiom();
        /* If we don't have a previous fractal's dimensions, use
         * the window's dimensions to do the scaling */
        Dimension d=new Dimension();
        if(bg_d.width == 0 && bg_d.height == 0) {
            if ( axiom.equals("f--f--f") && x == -1.0) {
            	Dimension p = getSize();
            	if ( p.width <= 114){
            		d.height = getSize().height-14;
            		d.width = getSize().width-14;
            }
            	else
            		d.setSize(100,100);
            }
            else
            	//d = getSize();
            	d = new Dimension ((int) (getSize().width*0.9),getSize().height-10);
        }
        else {
            d = bg_d;
        }
       
        
        
        /* Define the scale to transform the fractal points */
        double scale;
        if(height == 0) {
            scale = d.width/width;
        }
        else if(width == 0) {
            scale = d.height/height;
        }
        else {
            if(d.width/width < d.height/height)
                scale = d.width/width;
            else
               scale = d.height/height;
        }
       
       
        
        /* Test the axiom of possibles fractals */
        if ( axiom.equals("fX-fX-fX-fX-fX-fX") ) {
            // Set the scale for the Hexagon Fractal's points
            double[] first_seg;
            double x1, x2, y1, y2, size, bg_size;
            // If we are drawing the BackGround (bg) fractal, just save the coords
            // from the first segment
            if(bg_d.width == 0 && bg_d.height == 0) {
                double[] temp = (double[]) list.get(0);
                bg_first_seg[0] = temp[0] * scale;
                bg_first_seg[1] = temp[1] * scale;
                bg_first_seg[2] = temp[2] * scale;
                bg_first_seg[3] = temp[3] * scale;
            }
            // Else, set the ForeGround fractal to the scale of the BackGround fractal
            else {
                first_seg = (double[]) list.get(0);
                x1 = bg_first_seg[0];
                y1 = bg_first_seg[1];
                x2 = bg_first_seg[2];
                y2 = bg_first_seg[3];
                bg_size = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
                x1 = first_seg[0] * scale;
                y1 = first_seg[1] * scale;
                x2 = first_seg[2] * scale;
                y2 = first_seg[3] * scale;
                size = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
                scale = scale * (bg_size/size) * 3;
            }
            
            // Set the offsets for the Hexagon Fractal's points
            d = getSize();
            delta_x = (d.width - (width * scale))/2;
            delta_y = (d.height - (height * scale))/2;
        }
       
        /* Graphical primitives */
        Polygon2D p = new Polygon2D();
        Graphics2D g2d = (Graphics2D) g;
        int marginx = 10;
        int marginy = 5;

        /* Construction of a Polygon with the fractal points */
        for(int i = 0; i < list.size(); i++) {
            double[] coord = (double[]) list.get(i);           
            /* Add first line point for create a Polygon */
            if ( getSize().width < 114) {
            	marginx = 5;
            	if (getSize().width < 70) {
            		marginx=0;
            	}
            }
            
            p.addPoint(((coord[0] - x) * scale) + delta_x+marginx, ((coord[1] - y) * scale) + delta_y+marginy);
            //p.addPoint(((coord[0] - x) * scale) + delta_x, ((coord[1] - y) * scale) + delta_y);
            marginx=10;
        }
       
        /* Show Fractal in screen */
        g2d.setColor(c);
        g2d.fill(p);
        g2d.setColor(new Color(0, 0, 0));
        g2d.draw(p);
        p.reset();
        /* Return the fractal's scaled dimensions */ 
        Dimension scaled_d = new Dimension();
        scaled_d.setSize(width * scale, height * scale);
        return scaled_d;
    }
   
    /* Painting method of the Drawing space */
    public void paint(Graphics g) {
        Dimension bg_d;
        
        bg_d = drawFractal(f, g, new Color(100, 250, 100));
        
        String axiom = f.getAxiom();
        /* Test the axiom fractal's */
        if ( axiom.equals ("fXf--ff--ff")) {
        	if ( !paintExercise )
        		drawFractal(f, g, new Color(222,222,222));
        	else
        		return;
        }
        else {
            if ( f.getIteration() != 0) {
                f.setIteration(f.getIteration() - 1);
                drawFractal(f, g, new Color(222, 222, 222), bg_d);
                f.setIteration(f.getIteration() +1);
            }
            if ( ! paintExercise ){
            	drawFractal(f, g, new Color(222,222,222));
            }
        }
        
    }
}