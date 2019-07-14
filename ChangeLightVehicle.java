package LightDecorator;

import Vehicles.Vehicle;

public class ChangeLightVehicle extends LightedVehicleDecorator {

	public ChangeLightVehicle(Vehicle vehicle) {
		super(vehicle);

	}

    /**
     * Change Vehicle light.
     */
	@Override
	public void changeLights() {
		vehicle.changeLights();
		
	}
}
