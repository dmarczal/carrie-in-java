/* Copyright (c) 2008 Centro de Computacao Cientifica e Software Livre
 * Departamento de Informatica - Universidade Federal do Parana - C3SL/UFPR
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.
 */

package org.c3sl.ufpr.br.fractal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

/* Fractal Class.
 * Define properties and operations intrinsec to a IFS Fractal.
 * IFS (Iterated Function system) Fractals are defined by a L-system,
 * an increment angle and a iteration number. The L-system is a kind of
 * formal grammar, which includes an Axiom (a string of symbols -
 * constants and variables - which is used to initiate the production
 * process) and a set of production rules, which are successively
 * applied to each of the axiom variables at every step of the production
 * process.
 */
@SuppressWarnings("unchecked")
public class Fractal implements Serializable{
    private static final long serialVersionUID = -3281917256291850681L;
	/* The L-system's axiom, the initial set of symbols */
    private String axiom;
    /* The L-system's set of production rules */
	private HashMap<String, String> grammar;
    /* Increment Angle, used at the fractal construction */
    private double incrementAngle;
    /* The current step of the L-system production process */
    private int iteration;
    /* The smallest rectangle that contains all Fractal points */
    private BoundingRectangle bounds;
    /* A turtle state used to interpret the L-system's commands
     * during the Fractal construction. Contains (x,y) coordinates
     * and the angle to which the "turtle" is currently heading. */
    private Turtle turtle;
    /* The current state of the L-system's production process */
    private String description;
    /* A list of line segments that compose the fractal itself */
    private Vector<double[]> segmentList;
    /* A flag that signalizes if the segmentList is consistent */
    private boolean valid;

    /* Fractal default Constructor.
     * Input: n/a.
     * Output: n/a. */
    public Fractal() {
        axiom = null;
        grammar = new HashMap<String, String>();
        incrementAngle = 0;
        iteration = 0;
        bounds = new BoundingRectangle();
        turtle = new Turtle();
        description = null;
        segmentList = null;
        valid = false;
    }

    /* Parametric Fractal Constructor.
     * Input: an Axiom, a set of production rules, an increment angle
     * and an iteration number.
     * Output: n/a. */
    public Fractal(String axiom, String rules, double incrementAngle, int iteration) {
        setAxiom(axiom);
        grammar = new HashMap<String, String>();
        setRules(rules);
        setIncrementAngle(incrementAngle);
        setIteration(iteration);
        bounds = new BoundingRectangle();
        turtle = new Turtle();
        description = null;
        segmentList = null;
        valid = false;
    }

    /* Sets up the value of the Axiom string.
     * Input: An Axiom String, possibly containing constants and
     * variables of the L-system.
     * Output: True if it the value is successfully setup. */
    public boolean setAxiom(String axiom) {
        axiom = axiom.replaceAll("[ \t\n\f\r]", "");
        if(isAxiom(axiom)) {
            this.axiom = axiom;
            this.valid = false;
        } else {
            return false;
        }
        return true;
    }

    /* Returns the current Axiom string.
     * Input: n/a.
     * Output: A String containing the Fractal's Axiom. */
    public String getAxiom() {
        return this.axiom;
    }

    /* Sets up all the L-system's production rules at once.
     * Input: A string containing all the rules, separated by
     * newlines. Each rule is in the format:
     *    <predecessor>=<successor>
     * Where <predecessor> is a single alphabetic character,
     * and <successor> is a (possibly empty) string containing
     * only alphabetic characters, or characters like '+', '-',
     * '[' and ']'.
     * Output: True if it all the rules are successfully setup.
     * Side effect(s): In case of succesful output, any previously
     * assigned rule is lost. Otherwise, the L-system remains
     * unchanged. */
    public boolean setRules(String rules) {
        HashMap<String, String> saved = (HashMap<String, String>) this.grammar.clone();
        String rule[], pred_succ[];
        rule = rules.split("\n");
        grammar.clear();
        for(int i = 0; i < rule.length; i++) {
            rule[i] = rule[i].replaceAll("[ \t\n\f\r]", "");
            pred_succ = rule[i].split("=");
            if(pred_succ.length != 2) {
                grammar = saved;
                return false;
            }
            if(isPredecessor(pred_succ[0]) && isSuccessor(pred_succ[1])) {
                grammar.put(pred_succ[0], pred_succ[1]);
                valid = false;
            } else {
                grammar = saved;
                return false;
            }
        }
        return true;
    }

    /* Sets up a L-system's production rule.
     * Input: Two strings, pred and succ, that combined form a
     * production rule, where pred contains a single alphabetic
     * character, and succ is a (possibly empty) string containing
     * only alphabetic characters, or characters like '+', '-',
     * '[' and ']'.
     * Output: True if it the rule is successfully setup.
     * Side effect(s): If a rule with the same predecessor was
     * already set, it's successor is overriden by the new one. */
    public boolean setRule(String pred, String succ) {
        pred = pred.replaceAll("[ \t\n\f\r]", "");
        succ = succ.replaceAll("[ \t\n\f\r]", "");
        if(isPredecessor(pred) && isSuccessor(succ)) {
            grammar.put(pred, succ);
            valid = false;
        } else {
            return false;
        }
        return true;
    }

    /* Returns a production rule.
     * Input: A string containing the Predecessor of a rule
     * (a single alphabetic character).
     * Output: A String containing the Successor of the
     * production rule corresponding to the input Predecessor.
     * If the corresponding rule does not have a Successor,
     * or if the input Predecessor is not valid, a null
     * value is returned. */
    public String getRule(String pred) {
        pred = pred.replaceAll("[ \t\n\f\r]", "");
        if(isPredecessor(pred)) {
            grammar.remove(pred);
            pred = pred.substring(0, 1);
            return grammar.get(pred);
        } else {
            return null;
        }
    }

    /* Removes a previously assigned rule from the L-system.
     * Input: A string containing the predecessor of a rule
     * (a single alphabetic character).
     * Output: True if the rule is successfully removed.
     * Side effect(s): No rule is removed if there is no rule
     * having the input predecessor as it's predecessor. */
    public boolean removeRule(String pred) {
        pred = pred.replaceAll("[ \t\n\f\r]", "");
        if(isPredecessor(pred)) {
            grammar.remove(pred);
            valid = false;
        } else {
            return false;
        }
        return true;
    }

    /* Sets up a new incrementAngle.
     * Input: An angle in degrees , between 0 and 360 (inclusive).
     * Output: True if the value is successfully setup */
    public boolean setIncrementAngle(double incrementAngle) {
        if(incrementAngle < 0 || incrementAngle > 360)
            return false;
        this.incrementAngle = incrementAngle;
        valid = false;
        return true;
    }

    /* Returns the current incrementAngle.
     * Input: n/a.
     * Output: The value of the current incrementAngle. */
    public double getIncrementAngle() {
        return incrementAngle;
    }

    /* Sets up a new iteration number.
     * Input: A iteration number, greater or equal to zero.
     * Output: True if the value is successfully setup. */
    public boolean setIteration(int iteration) {
        if(iteration < 0) {
            return false;
        }
        this.iteration = iteration;
        valid = false;
        return true;
    }


    /* Returns the current iteration.
     * Input: n/a.
     * Output: The current iteration number. */
    public int getIteration() {
        return this.iteration;
    }

    /* Checks if a string is a valid Axiom.
     * Input: A string containing an Axiom candidate.
     * Output: True if the input was a valid Axiom. */
    private boolean isAxiom(String s) {
        return (isSuccessor(s) && s.length() > 0);
    }

    /* Checks if a string is a valid Predecessor of a production rule.
     * Input: A string containing a Predecessor candidate.
     * Output: True if the input is a valid Predecessor. */
    private boolean isPredecessor(String s) {
        if(s != null && s.length() == 1 && Character.isLetter(s.charAt(0)))
            return true;
        else
            return false;
    }

    /* Checks if a string is a valid Successor of a Production Rule.
     * Input: A string containing a Successor candidate.
     * Output: True if the input is a valid Successor. */
    private boolean isSuccessor(String s) {
        if(s == null)
            return false;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(!Character.isLetter(c) && c != '+' && c != '-' && c != '[' && c != ']')
                return false;
        }
        return true;
    }

    /* Returns the left-most coordinate of the Bounding Rectangle.
     * Input: n/a.
     * Output: The left-most coordinate of the Bounding Rectangle
     * that contains all the Fractal points. */
    public double getX() {
        return bounds.x;
    }

    /* Returns the top-most coordinate of the Bounding Rectangle.
     * Input: n/a.
     * Output: The top-most coordinate of the Bounding Rectangle
     * that contains all the Fractal points. */
    public double getY() {
        return bounds.y;
    }

    /* Returns the width of the Bounding Rectangle.
     * Input: n/a.
     * Output: The width of the Bounding Rectangle that contains
     * all the Fractal points. */
    public double getWidth() {
        return bounds.width;
    }

    /* Returns the height of the Bounding Rectangle.
     * Input: n/a.
     * Output: The height of the Bounding Rectangle that contains
     * all the Fractal points. */
    public double getHeight() {
        return bounds.height;
    }

    /* Returns a list of line segments corresponding to the Fractal.
     * Input: n/a.
     * Output: The list of line segments that describe the Fractal.
     * Each line segment is a double array  of length 4. From 0
     * to 3, each position of the vectors correspond to x1, y1, x2
     * and y2 respectively, where (x1,y1) are the coordinates of
     * one of the segment ends and (x2,y2) are the coordinates of
     * the other segment end. In case of error, the output is null. */
    public Vector<double[]> getSegmentList() {
        /* If the segmentList does not correspond to the current
         * fractal properties, update it. */
        if(!valid)
            update();
        return segmentList;
    }

    /* Returns the current IFS String representing the Fractal.
     * Input: n/a.
     * Output: A string of symbols representing the current state
     * of the Fractal. */
    public String getDescription() {
        /* If the description does not correspond to the current
         * fractal properties, update it. */
        if(!valid)
            update();
        return description;
    }

    /* Update the internal segmentList of the Fractal's line segments
     * to match the current Fractal's properties.
     * Input: n/a.
     * Output: n/a. */
    private void update() {
        /* If the fractal's segmentList is up-to-date, return now */
        if(valid)
            return;
        /* Used to stack up turtle states */
        Stack<Turtle> st = new Stack<Turtle>();
        /* Updates the Fractal's description */
        productionProcess();
        /* Initialize the turtle at (0,0) and 0 degree */
        turtle.setPosition(0, 0, 180);
        /* This vector will hold the list of line segments */
        segmentList = new Vector<double[]>();
        /* Reset the attributes of the BoundingRectangle */
        bounds.setBounds(0, 0, 0, 0);
        /* For each command in the Fractal's description */
        for(int i = 0; i < description.length(); i++) {
            char c = description.charAt(i);
            /* If it is a "draw line segment" instruction */
            if(Character.isLetter(c) && Character.isLowerCase(c)) {
                /* Moves the turtle forward */
                double[] line = turtle.moveForward();
                if(line == null) {
                    bounds.setBounds(0, 0, 0, 0);
                    segmentList = null;
                    valid = true;
                    return;
                }
                /* Includes the last line segment "drawn" by the turtle */
                segmentList.add(line);
                /* Stretch the bounds to fit in the Fractal's dimensions */
                bounds.add(line[2], line[3]);
            /* If it is a "turn right" instruction */
            } else if(c == '+') {
                turtle.turn(-incrementAngle);
            /* If it is a "turn left" instruction */
            } else if(c == '-') {
                turtle.turn(incrementAngle);
            /* If it is a "save state" instruction */
            } else if(c == '[') {
                /* Save the current Turtle in the Stack */
                st.push(turtle);
                turtle = new Turtle(turtle);
            /* If it is a "load last saved state" instruction */
            } else if(c == ']') {
                /* Restores the last saved state to the current turtle */
                turtle = st.pop();
            }
        }
        valid = true;
    }

    /* Expands the Fractal Axiom, based on the L-system's production rules.
     * Input: n/a.
     * Output: n/a. */
    private void productionProcess() {
        String gen, next_gen, pred, succ;
        gen = axiom;
        for(int i = 0; i < iteration; i++) {
            next_gen = new String("");
            for(int j = 0; j < gen.length(); j++) {
                pred = gen.substring(j, j+1);
                succ = grammar.get(pred);
                if(succ != null) {
                    next_gen = next_gen.concat(succ);
                } else {
                    next_gen = next_gen.concat(pred);
                }
            }
            gen = next_gen;
        }
        description = gen;
    }
}

/* Turtle Class.
 * Define a LOGO-like turtle cursor.
 * Its attributes are: (x,y) coordinates and an angle.
 * The latter represents the direction where the turtle
 * is heading, while the pair (x,y) are the cartesian
 * coordinates of the turtle.
 * This simples class is used only by the Fractal class,
 * in order to track down multiple cursor (turtle) states
 * while building the Fractal line segments. */
class Turtle implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5406692716427019724L;
	private double x;
    private double y;
    private double angle;

    /* Turtle default Constructor.
     * Input: n/a.
     * Output: n/a. */
    public Turtle() {
        x = y = angle = 0;
    }

    /* Turtle parametric Constructor.
     * Input: A x coordinate, a y coordinate and a heading angle.
     * Output: n/a. */
    public Turtle(double x, double y, double angle) {
        this.setPosition(x, y, angle);
    }

    /* Constructor that copies a Turtle to a new one.
     * Input: An existing Turtle Object.
     * Output: n/a. */
    public Turtle(Turtle t) {
        this.setPosition(t.x, t.y, t.angle);
    }

    /* Sets up the Turtle position (state).
     * Input: A x coordinate, a y coordinate and a heading angle.
     * Output: n/a. */
    public void setPosition(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    /* Moves the Turtle towards the heading angle, by a unit of length.
     * Input: n/a.
     * Output: A line segment which connects the last and current
     * Turtle's states. Returns null in case of error. */
    public double[] moveForward() {
        double deltax, deltay, cos, sin;
        double line[];
        cos = Math.cos(Math.toRadians(angle));
        if(Double.isNaN(cos) || Double.isInfinite(cos))
            return null;
        deltax = x + cos;
        if(Double.isNaN(deltax) || Double.isInfinite(deltax))
            return null;
        sin = Math.sin(Math.toRadians(angle));
        if(Double.isNaN(sin) || Double.isInfinite(sin))
            return null;
        deltay = y + sin;
        if(Double.isNaN(deltay) || Double.isInfinite(deltay))
            return null;
        line = new double[4];
        line[0] = x;
        line[1] = y;
        line[2] = deltax;
        line[3] = deltay;
        x = deltax;
        y = deltay;
        return line;
    }

    /* Turns the Turtle's heading Angle
     * Input: The angle to be added to the current Turtle's angle.
     * Output: n/a. */
    public void turn(double angle) {
        this.angle += angle;
    }
}

/* BoundingRectangle Class.
 * Define the dimensions of the smallest Rectangle that can contain
 * all the points of a set.
*/
class BoundingRectangle implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7379298422629492148L;
	public double x;
    public double y;
    public double width;
    public double height;

    /* Default Constructor.
     * Input: n/a.
     * Output: n/a. */
    public BoundingRectangle() {
        x = y = width = height = 0;
    }

    /* Parametric BoundingRectangle Constructor.
     * Input: The (x,y) coordinates of the top-left corner, width
     * and height of the rectangle.
     * Output: n/a. */
    public BoundingRectangle(double x, double y, double w, double h) {
        setBounds(x, y, w, h);
    }

    /* Sets up the dimensions of the BoundingRectangle
     * Input: The (x,y) coordinates of the top-left corner, width
     * and height of the rectangle.
     * Output: n/a. */
    public void setBounds(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    /* Stretch the Rectangle to include the input point.
     * Input: The (x,y) coordinates of the to-be included point.
     * Output: n/a.*/
    public void add(double x, double y) {
        if(in(x,y)) {
            return;
        }
        if(x < this.x) {
            this.width += this.x - x;
            this.x = x;
        } else if(x > this.x + this.width) {
            this.width = x - this.x;
        }
        if(y < this.y) {
            this.height += this.y - y;
            this.y = y;
        } else if(y > this.y + this.height) {
            this.height = y - this.y;
        }
    }

    /* Checks if the input point is inside the Rectangle.
     * Input: The (x,y) coordinates of a point.
     * Output: True if the input point is inside the Rectangle. */
    public boolean in(double x, double y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
    }
}
