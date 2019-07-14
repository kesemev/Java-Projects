package Vehicles;
import javax.swing.JFrame;

import graphics.CityPanel;

/**
 * HasEngine Class.
 * @version 1.10 23 April 2019
 * @author  Kesem Even-Hen, 208055483
 * @see JFrame
 */
public abstract class HasEngine extends Vehicle {
	protected Engine engine;
	protected int amount;
	private final static int minAge=18;
	/**
	 * Constructor to HasEngine
	 * @param col
	 * @param wheels
	 * @param pan
	 * @param engine
	 */
	public HasEngine( Color col, int wheels,CityPanel pan, Engine engine) {
		super( col, 4, pan);
		try {
		this.engine = (Engine)engine.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.amount = engine.getCapacity();
	}
	
	/** If these vehicles are engine, the amount of fuel will be updated as well.
	 * The method will return false if the vehicle does not move.
	/**
	 * @param P
	 * calculates the driving with the fuel
	 */
	@Override
	public boolean drive(Point P) {
		if (getLocation().getPoint().getDistance(P) <= amount / engine.getFuelKm()) {
			amount -= getLocation().getPoint().getDistance(P) * engine.getFuelKm();
			return super.drive(P);
		} else
		{			
			return false;
		}
	}
	
	/**
	 * The method updates the amount of fuel to the maximum amount of the vehicle (depending on the engine) 
	 * @return false if the tank is already full)
	 */
	public boolean refuel()
	{
		if(amount == engine.getCapacity())
			return false;
		amount = engine.getCapacity();
		fuelConsumption += engine.getCapacity();
		return true;
	}
	
	/**
	 * @return Engine engine
	 */
	public Engine getEngine() {
		return this.engine;
	}
	/**
	 * Get engine name
	 */
	public String getEngineName() {
		return engine.getEngineName();
	}
	
	@Override
	public int getAmount(){ 
		return this.amount;
	}

	@Override
    /**
     * Override toString of Object class.
     * @return String to display in print.
     */
	public String toString() {
		return super.toString() + "amount=" + amount + ", minAge=" + minAge + ", " + engine;
	}
	/**
	 * @return amount
	 */
	public int getFuelAmount() {
		return amount;
	}
	

	
	


	
}
