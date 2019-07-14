package ColorDecorator;

import Vehicles.Vehicle;

public class ChangeColorVehicle extends ColoredVehicleDecorator{

	public ChangeColorVehicle(Vehicle vehicle) {
		super(vehicle);

	}
	
	/**
     * Change Vehicle color.
     * @param color
     */
	@Override
	public void PaintVehicle(String color) {
		vehicle.setColor(vehicle.pan.stringColorToEnum.get(color));
		vehicle.loadImages();
	    }

}