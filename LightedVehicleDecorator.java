package LightDecorator;

import LightDecorator.LightedVehicle;
import Vehicles.Vehicle;

/**
 * Created by Kesem Even Hen on 24/05/2019.
 * Decorate DP Class.
 */

public abstract class LightedVehicleDecorator implements LightedVehicle {
	
	protected Vehicle vehicle;

    /**
     *  Set the vehicle to decorate.
     * @param vehicle
     */
    public LightedVehicleDecorator(Vehicle vehicle){
        this.vehicle = vehicle;
    }

    /**
     * Change Vehicle light.
     */
	@Override
	public void changeLights() {
		vehicle.changeLights();
		
	}
}