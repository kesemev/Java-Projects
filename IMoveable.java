package graphics;

import Vehicles.Point;

public interface IMoveable {	
     public String getVehicleName();
	 public int getSpeed();
	 public int getFuelConsumption();
	 public boolean move(Point p);
	 public int getDurability();
}

