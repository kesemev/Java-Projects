package Vehicles;
import java.lang.Math;

/**
 * A class that representing a point.
 * @version 1.00 26 Mar 2019
 * @author Kesem Even-Hen, 208055483
 * @see 
 *
 */
public class Point implements Cloneable{
	private int x,y;
	
	/**
	 * constructor
	 * @param x
	 * @param y
	 */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Set a x value into the point,
     * @param x the Integer x to set into the point.
     * @return boolean par of the success.
     */
    public boolean setX(int x){
        this.x = x;
        return true;
    }

    /**
     * Set a y value into the point,
     * @param y the Integer y to set into the point.
     * @return boolean par of the success.
     */
    public boolean setY(int y){
        this.y = y;
        return true;
    }
    
    /**
     * @return Integer x of the point.
     */
    public int getX(){
        return x;
    }

    /**
     * @return Integer y of the point.
     */
    public int getY(){
        return y;
    }

    /**
     * Override toString of Object class.
     * @return String to display in print.
     */
    @Override
    public String toString() {
        return "("+ x + ", " + y +")";
    }
    
    /**
     * @param other is other Point to Check equals
     * @return True if they equals, else False
     */
    public boolean equals(Point other) {
    	return (x == other.x) && ( y== other.y);
    }
    
    /**
     * 
     * @param other
     * @return distance between this point and other point
     */
    public int getDistance(Point other) {
    	return Math.abs(x-other.x)+Math.abs(y-other.y);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }



    
} //class Point
