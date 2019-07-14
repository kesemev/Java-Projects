package FactoryMethod;
import Vehicles.*;
import graphics.*;
////Factory method pattern implementation that 
//instantiates objects based on logic 
public class CityFactoryMethod {

		public Vehicle getVehicle(String name, String c, int tran, CityPanel pan) {
			   Vehicle ve = null;
			   // based on logic factory instantiates an object 
			   if(name.equals("Bike"))
				   ve = new Bike(pan.stringColorToEnum.get(c) , tran ,pan);
			   else if (name.equals("Carriage"))
				   ve = new Carriage(pan.stringColorToEnum.get(c),new PackAnimal("horse",1000),pan);
			   else if (name.equals("Solar Car")) 
			       ve = new Car(pan.stringColorToEnum.get(c),pan,new SolarEngine());
			   else 
				   ve = new Car(pan.stringColorToEnum.get(c),pan, new BenzineEngine());
			   return ve;
		}
}