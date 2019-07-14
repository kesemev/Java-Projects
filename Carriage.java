package Vehicles;
import javax.swing.JFrame;
import graphics.CityPanel;

/**
 * Carriage Class.
 * @version 1.10 23 April 2019
 * @author  Kesem Even-Hen, 208055483
 * @see JFrame
 *
 */
public class Carriage extends Vehicle {
	private PackAnimal animal;
	//private final static int passengers=2;
	
	/**
	 * Constructor for Carriage
	 * @param col
	 * @param animal
	 * @param pan
	 */
	public Carriage( Color col, PackAnimal animal,CityPanel pan) {
		super(col, 4,pan);
		this.animal = animal;
		speed=8;
		durability=4;
	}
	
	public boolean setAnimalBreed(PackAnimal animalBreed) {
		animal = animalBreed;
		return true;
	}
	
	@Override
    /**
     * Override toString of Object class.
     * @return String to display in print.
     */
	public String toString() {
		return super.toString()+ ", animal=" + animal;
	}

	@Override
	public String getVehicleName() {
		return "Carriage";
	}
	
	@Override
	public int getFuelConsumption() {
		return fuelConsumption;
	}
	
	/**
	 * get energy of the pack animal of the carriage
	 */
	public int getAmount() {
		return animal.getEnergy();
	}
	/**
	 * To feed the pack animal
	 * @return
	 */
	public boolean feed()
	{
		animal.eat();
		fuelConsumption += animal.getFuelConsumption();
		return true;
	}

	/**
	 * 
	 * @return if this vehicle can move 
	 */
	public boolean canMove()
	{
		if(animal.getEnergy()>= animal.getFuelConsumption())
		{
			return true;
		}
		setChanged();
		notifyObservers("paint red");
		return false;
	}
	

	@Override
	public boolean move(Point p) 
	{
		if(canMove())	
		{
			animal.move(p);
			this.drive(p);
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
