package Vehicles;
import graphics.CityPanel;

/**
 * 208055483
 * @author χρν
 *
 */
public class Bike extends Vehicle {
	private int transmission;
	//private final static int passengers=1;
	public Bike(Color col, int transmission, CityPanel pan) {
		super(col, 2 , pan);
		this.transmission=transmission;
		speed=8;
		durability=3;
	}

	@Override
    /**
     * Override toString of Object class.
     * @return String to display in print.
     */
	public String toString() {
		return super.toString() + "transmission=" + transmission;
	}

	@Override
	public String getVehicleName() {
		return "Bike";
	}

	@Override
	public int getSpeed() {
		return speed;
	}

	@Override
	public boolean move(Point p) {
		this.drive(p);
		pan.repaint();
		return true;
	}
	

	@Override
	public int getDurability() {
		return durability;
	}


}
