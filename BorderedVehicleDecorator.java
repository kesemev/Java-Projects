package BorderDecorator;

import java.awt.Graphics;

import Vehicles.Vehicle;


public abstract class BorderedVehicleDecorator implements BorderedVehicle{
	
	protected Vehicle vehicle;

    /**
     *  Set the vehicle to decorate.
     * @param vehicle
     */
    public BorderedVehicleDecorator(Vehicle vehicle){
        this.vehicle = vehicle;
    }

	public void drawObject(Graphics g) {
		vehicle.drawObject(g);
	}
}
