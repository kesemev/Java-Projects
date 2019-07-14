package Vehicles;

import javax.swing.JFrame;

/**
 * 
 * Engine Class.
 * @version 1.10 23 April 2019
 * @author  Kesem Even-Hen, 208055483
 * @see JFrame
 */
public abstract class Engine implements Cloneable{
	private int fuelKm;
	private int capacity;
	
	/**
	 * constructor
	 * @param fuelKm
	 * @param capacity
	 */
	public Engine(int fuelKm, int capacity) {
		this.fuelKm = fuelKm;
		this.capacity = capacity;
	}

	/**
	 * @return Integer fuelKm
	 */
	public int getFuelKm() {
		return fuelKm;
	}
	
	/**
	 * @param fuelKm
	 * @return
	 */
	public boolean setFuelKm(int fuelKm)
	{
		this.fuelKm=fuelKm;
		return true;
	}
	
	/**
	 * @return Integer capacity
	 */
	public int getCapacity() {
		return capacity;
	}
	/**
	 * Set capacity
	 * @param capacity
	 * @return
	 */
	public boolean setCapacity(int capacity) {
		this.capacity=capacity;
		return true;
	}
	
	@Override
    /**
     * Override toString of Object class.
     * @return String to display in print.
     */
	public String toString() {
		return "fuelKm=" + fuelKm + ", capacity=" + capacity + ", ";
	}
	/**
	 * Get engine name
	 * @return
	 */
	public String getEngineName() {
		if (this instanceof BenzineEngine) 
			return "Benzine ";
		else
			return "Solar ";
	}
    
	@Override
	protected Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}
	
}
