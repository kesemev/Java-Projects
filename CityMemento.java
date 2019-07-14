package Memento;

import Vehicles.Vehicle;
import java.util.ArrayList;

/**
 * Created by Kesem Even Hen on 26/05/2019.
 * CityMemento class of Memento DP.
 */
public class CityMemento {
    private ArrayList<Vehicle> vehicles;
    
    /**
     * – the object that is going to maintain the state of originator.
     * @param vehicles
     */
    CityMemento(ArrayList<Vehicle> vehicles){
        this.vehicles = new ArrayList<>(vehicles.size());
        for (Vehicle vehicle :vehicles)
            this.vehicles.add(vehicle.clone());
    }

    ArrayList<Vehicle> getVehicles(){
        return vehicles;
    }

}
