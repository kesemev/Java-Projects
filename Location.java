
package Vehicles;
import javax.swing.JFrame;

/**
 * Location Class.
 * @version 1.10 23 April 2019
 * @author  Kesem Even-Hen, 208055483
 * @see JFrame
 */
public class Location {
	private Point point;
	private Direction dir;	
	public enum Direction 
	{ 
	    SOUTH, NORTH, EAST, WEST; 
	}
	/**
	 * @param point
	 * @param dir
	 */
	public Location(Point point,Direction dir) {
		try {
			this.point=(Point)point.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dir=dir;
	}
	/**
	 * @param point
	 * @return yes if the set is success
	 */
	public boolean setPoint(Point point) {
		this.point=point;
		return true;
	}

	/**
	 * @return the point value
	 */
    public Point getPoint(){
        return point;
    }
    /**
     * @return the direction of the Location
     */
    public Direction getDirection(){
        return dir;
    }
    /**
     * Set Direction
     * @param dir
     * @return true
     */
    public boolean setDirection(Direction dir)
    {
    	this.dir=dir;
    	return true;
    }

	@Override
    /**
     * Override toString of Object class.
     * @return String to display in print.
     */
	public String toString() {
		return "point=" + point + ", dir=" + dir;
	}
    
}
