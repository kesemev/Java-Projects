package Vehicles;
import javax.swing.JFrame;

/**
 * BenzineEngine Class.
 *
 * @version 1.10 23 April 2019
 * @author  Kesem Even-Hen, 208055483
 * @see JFrame
 *
 */
public class BenzineEngine extends Engine {

	/**
	 * constructor
	 * @param fuelKm
	 * @param capacity
	 */
	public BenzineEngine() {
		super(2, 2000);
	}

	@Override
    /**
     * Override toString of Object class.
     * @return String to display in print.
     */
	public String toString() {
		return super.toString() + "engine=Solar ," ;
	}
    /**
     * get Engine Name
     */
	public String getEngineName() {
		return "Benzine";
	}
	

	@Override
	protected Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}


}
