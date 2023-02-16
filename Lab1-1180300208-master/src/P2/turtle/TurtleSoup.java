/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {

        for (int i = 0; i < 4; i++) {
        	turtle.forward(sideLength);
            turtle.turn(90.0);
        }
        
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        return 180.0 * (sides - 2) / sides;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        return (int) Math.round((360.0/(180.0-angle)));
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        
    	for (int i = 0; i < sides; i++) {
        	turtle.forward(sideLength);
        	turtle.turn(180.0 - calculateRegularPolygonAngle(sides));
        }
    	
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
    	
    	double ret = Math.toDegrees(Math.PI/2 - Math.atan((double)(targetY - currentY) / (targetX - currentX))) - currentBearing + 360.0;
    	while(ret >= 360.0) {
    		ret -= 360.0;
    	}
        return ret;
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        
    	List<Double> angle = new ArrayList<Double>();
    	
    	double currentAngle = 0.0;
    	int currentX = 0, currentY = 0, targetX = 0, targetY = 0;
    	
    	for(int i = 0; i < xCoords.size() - 1; i++) {
    		currentX = xCoords.get(i);
    		currentY = yCoords.get(i);
    		targetX = xCoords.get(i+1);
    		targetY = yCoords.get(i+1);
    		currentAngle = calculateBearingToPoint(currentAngle, currentX, currentY, targetX, targetY);
    		angle.add(currentAngle);
    	}
    	
    	return angle;
    	
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
    	
    	Point currentPoint;
    	Point lastPoint;
    	Point initPoint = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Set<Point> point = new HashSet<Point>();
        double x1, y1, x2, y2;
        double cross;

        
        if (points.isEmpty()) ;
        else if(points.size() == 1 || points.size() == 2) point.addAll(points);
        else {
        	
        	for (Point p : points) {
        		if(p.x() < initPoint.x()) initPoint = p;
        		else if(p.x() == initPoint.x()) {
        			if(p.y() < initPoint.y()) initPoint = p;
        		}
        	}
        	
//        	System.out.println(initPoint.x() + "," + initPoint.y());
        	
        	currentPoint = initPoint;
        	lastPoint = initPoint;
        	x1 = 0.0; y1 = -1.0;
        	
        	do {
        		point.add(currentPoint);
        		x1 = -x1; y1 = -y1;
        		for(Point p : points) {
        			
        			if(point.contains(p) && !p.equals(initPoint)) {
        				continue;
        			}
        			
        			x2 = p.x() - currentPoint.x();
        			y2 = p.y() - currentPoint.y();
        			
        			cross = x1 * y2 - x2 * y1;
        			
        			if(cross < 0) {
        				x1 = x2; y1 = y2;
        				lastPoint = p;
        			}
        			
        			else if(cross == 0 && (Math.abs(x2) > Math.abs(x1) || Math.abs(y2) > Math.abs(y1))) {
        				x1 = x2; y1 = y2;
        				lastPoint = p;
        			}
        			
        		}
        		currentPoint = lastPoint;
        	} while (!initPoint.equals(lastPoint));
        	
        	        	
        }
        
        return point;
    }
    
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
    	for(int j = 0; j < 10; j++) {
	    	for (int i = 30; i < 100; i += 3) {
		    	turtle.forward(100 - i);
		        turtle.turn(i);
		        turtle.color(PenColor.BLUE);
		        turtle.forward(100 - i);
		        turtle.turn(i);
		        turtle.color(PenColor.GREEN);
		        turtle.forward(100 - i);
		        turtle.turn(i);
		        turtle.color(PenColor.RED);
		        turtle.forward(100 - i);
		        turtle.turn(i);
		        turtle.color(PenColor.YELLOW);
		        turtle.forward(100 - i);
		        turtle.turn(i);
		        turtle.color(PenColor.MAGENTA);
	        }
	    	for (int i = 100; i > 30; i -= 2) {
		    	turtle.forward(100 - i);
		        turtle.turn(i);
		        turtle.color(PenColor.BLUE);
		        turtle.forward(100 - i);
		        turtle.turn(i);
		        turtle.color(PenColor.GREEN);
		        turtle.forward(100 - i);
		        turtle.turn(i);
		        turtle.color(PenColor.RED);
		        turtle.forward(100 - i);
		        turtle.turn(i);
		        turtle.color(PenColor.YELLOW);
		        turtle.forward(100 - i);
		        turtle.turn(i);
		        turtle.color(PenColor.MAGENTA);
	        }
    	}
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

//        drawSquare(turtle, 40);
//        drawRegularPolygon(turtle, 7, 100);
        drawPersonalArt(turtle);
        
//         draw the window
        turtle.draw();
    	
//    	Set<Point> points = new HashSet<Point>();
//		Set<Point> convexHull = new HashSet<Point>();
//		
//		Point p11 = new Point(1, 1);
//		Point p1010 = new Point(10, 10);
//		Point p110 = new Point(1, 10);
//		Point p12 = new Point(1, 2);
//		Point p23 = new Point(2, 3);
//		Point p32 = new Point(3, 2);
//
//		points.add(p11);
//		points.add(p1010);
//		points.add(p110);
//		points.add(p12);
//		points.add(p23);
//		points.add(p32);
//		
//		convexHull = TurtleSoup.convexHull(points);
//		
//		for(Point i : convexHull) {
//			System.out.println("(" + i.x() + "," + i.y() + ")");
//		}
        
    }

}
