package Memento;

import java.util.ArrayList;
import Vehicles.Vehicle;

/**
 * Created by Kesem Even Hen on 26\05\19.
 * Originator class of Memento DP.
 * the object for which the state is to be saved. It creates the memento and uses it in future to undo.
 */
public class Originator {
    private ArrayList<Vehicle> vehicles;


    public void setState(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;

    }
    
    public CityMemento createMemento() {
        return new CityMemento(vehicles);
    }

    public void setMemento(CityMemento memento) {
        vehicles = memento.getVehicles();

    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

}

