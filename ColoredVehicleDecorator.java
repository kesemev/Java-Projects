package ColorDecorator;

import ColorDecorator.ColoredVehicle;
import Vehicles.Vehicle;

/**
 * Created by Kesem Even Hen on 24/05/2019.
 * Decorate DP Class.
 */

public abstract class ColoredVehicleDecorator implements ColoredVehicle {

	protected Vehicle vehicle;

    /**
     *  Set the vehicle to decorate.
     * @param vehicle
     */
    public ColoredVehicleDecorator(Vehicle vehicle){
        this.vehicle = vehicle;
    }

    /**
     * Change Vehicle color.
     * @param color
     */
	@Override
	public void PaintVehicle(String color) {
		vehicle.PaintVehicle(color);
		
	}

}
