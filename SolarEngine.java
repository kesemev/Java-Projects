package Vehicles;

import javax.swing.JFrame;

/**
 * SolarEngine Class.
 * @version 1.10 23 April 2019
 * @author  Kesem Even-Hen, 208055483
 * @see JFrame
 */
public class SolarEngine extends Engine{
	/**
	 * constructor to SolarEngine
	 * @param fuelKm
	 * @param capacity
	 */
	public SolarEngine() {
		super(1, 40);
	}

	@Override
    /**
     * Override toString of Object class.
     * @return String to display in print.
     */
	public String toString() {
		return super.toString() + "engine=Solar ,"  ;
	}
	/**
	 * get Engine Name
	 */
	@Override
	public String getEngineName() {
		return "Solar";
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}


}
