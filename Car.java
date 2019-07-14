package Vehicles;
import javax.swing.JFrame;
import graphics.CityPanel;

/**
 * Car Class.
 * @version 1.10 23 April 2019
 * @author  Kesem Even-Hen, 208055483
 * @see JFrame
 *
 */
public class Car extends HasEngine{
	private final static int passengers=5;
	
	public Car(Color col ,CityPanel pan, Engine engine) {
		super(col, 4, pan, engine);
		super.refuel();
		speed=4;
		durability=5;
	}
	
	@Override
    /**
     * Override toString of Object class.
     * @return String to display in print.
     */
	public String toString() {
		return super.toString() + "passengers=" + passengers;
	}

	public String getVehicleName() {	
		return "Car";
	}

	public boolean setSpeed(int speed) {
		this.speed=speed;
		return true;
	}

	/**
	 * 
	 * @return if this car can move
	 */
	public boolean canMove() {
		//if (getLocation().getPoint().getDistance(P) <= amount / engine.getFuelKm()) 
		if(amount > getEngine().getFuelKm())
		{
			return true;
		}
		setChanged();
		notifyObservers("paint red");
		return false;
	}
	/**
	 * Get amount of Fuel
	 */
	public int getAmount(){ 
		return amount;
	}
	/**
	 * move the car
	 */
	@Override
	public boolean move(Point p) {
		// TODO Auto-generated method stub
		if (canMove())
		{
			drive(p);
			pan.repaint();
			return true;
		}
		else 
		 	return false;
	}

	@Override
	public int getDurability() {
		return durability;
	}
	

}
