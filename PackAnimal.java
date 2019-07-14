package Vehicles;
import javax.swing.JFrame;

import graphics.IAnimal;
import graphics.IClonable;

/**
 * PackAnimal Class.
 * @version 1.10 23 April 2019
 * @author  Kesem Even-Hen, 208055483
 * @see JFrame
 */
public class PackAnimal implements IAnimal, IClonable{
	private static final int maxEnergy=1000;
	private static final int energyPerKm=20;
	private String animalName;
	private int energy;
	private Carriage carriage;
	/**
	 * Constructor for FackAnimal
	 * @param animalName
	 * @param energy
	 */
	public PackAnimal(String animalName, int energy) {
		this.animalName = animalName;
		this.energy=maxEnergy;
	}
	/**
	 * 
	 * @return pack animal's carriage
	 */
	public Carriage getCarriage() {
		return carriage;
	}
	/**
	 *  set carriage to the pack animal
	 * @param carriage
	 */
	public void setCarriage(Carriage carriage) {
		this.carriage = carriage;
	}

	/**
	 * move the carriage by the animal
	 */
	@Override
	public boolean move(Point p) {
		energy -= energyPerKm;
		return true;
	}
	/**
	 * get the animal name
	 */
	@Override
	public String getAnimalName() {
		return animalName;
	}
	/**
	 * animal eat by initialize the energy to be max Energy
	 */
	@Override
	public boolean eat() {
		this.energy=maxEnergy;
		return true;
	}


	@Override
	public int getFuelConsumption() {
		return energyPerKm;
	}
	/**
	 * there is no speed to the animal yet only to the carriage
	 */
	@Override
	public int getSpeed() {
		return 0;
	}
	/**
	 * return string animal Name
	 */
	@Override
	public String getVehicleName() {
		return animalName;
	}

	/**
	 * @return energy of the animal
	 */
	public int getEnergy() {
		return energy;
	}
	
	@Override
	public Object Clone() {
		return new PackAnimal( animalName, energy);
	}
	
	@Override
	public int getDurability() {
		return 0;
	}

	
}
