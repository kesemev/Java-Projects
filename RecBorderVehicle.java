package BorderDecorator;

import java.awt.Graphics;

import Vehicles.Vehicle;
import Vehicles.Location;
import Vehicles.Location.Direction;

public class RecBorderVehicle extends BorderedVehicleDecorator {

	public RecBorderVehicle(Vehicle vehicle) {
		super(vehicle);
	}

	@Override
	public void drawObject(Graphics g) {
		super.drawObject(g);
		Location loc = vehicle.getLocation();
		int size = Vehicle.size;
		if (loc.getDirection() == Direction.NORTH) // car drives to the north side
			g.drawRect( loc.getPoint().getX()-size/2, loc.getPoint().getY(), size, size*2);
		else if (loc.getDirection() == Direction.SOUTH) // car drives to the south side
			g.drawRect( loc.getPoint().getX(), loc.getPoint().getY(), size, size);
		else if (loc.getDirection() == Direction.EAST) // car drives to the east side
			g.drawRect( loc.getPoint().getX(), loc.getPoint().getY(), size * 2, size);
		else if (loc.getDirection() == Direction.WEST) // car drives to the west side
			g.drawRect( loc.getPoint().getX(), loc.getPoint().getY(), size * 2, size);
	}



}
