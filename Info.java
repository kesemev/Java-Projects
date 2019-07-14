package graphics;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import Vehicles.Vehicle;

public class Info extends JScrollPane implements Observer{
	private static final long serialVersionUID = 1L;
	private static Info instance = null;
	private JTable table;
	private ArrayList<Vehicle> infoVehicles;
   /**
    * All information about vehicles in the JTable format appears
    */
   private Info(ArrayList<Vehicle> infoVehicles)
   {  
	   this.infoVehicles = infoVehicles;
	   table = new JTable();
	   setVisible(false);
   }
   
   public static Info getInstance(ArrayList<Vehicle> infoVehicles) {
       if(Info.instance == null) {
    	   Info.instance = new Info(infoVehicles);
       }
       return Info.instance;
   }
   
   public void updateData()
   {
	   	  int i=0;
		  int sz =infoVehicles.size();
		  
		  String[] columnNames = {"Vehicle","ID","Color","Wheels","Speed","FuelAmount","Distance","Fuel consumption","Lights","collided with"};
	      String [][] data = new String[sz+1][columnNames.length];  
	      for(Vehicle ve : infoVehicles)
			  {
				  data[i][0] = ve.getEngineName() + ve.getVehicleName();
				  data[i][1] = new Integer(ve.getId()).toString();
				  data[i][2] = ve.getColor();
				  data[i][3] = new Double(ve.getWheels()).toString();
				  data[i][4] = new Integer(ve.getSpeed()).toString();
				  data[i][5] = new Integer(ve.getAmount()).toString();
				  data[i][6] = new Double(ve.getMileage()).toString();
				  data[i][7] = new Integer(ve.getFuelConsumption()).toString();
				  data[i][8] = ve.getLight();
				  data[i][9] = ve.collidedWith;
				  i++;
	      	  }
	      
	      table = new JTable(data, columnNames);
	      getViewport().setView(table);
	      setSize(600,table.getRowHeight()*(sz+1)+30);
   }

@Override
public void update(Observable o, Object arg) {
	// TODO Auto-generated method stub
	updateData();
}

}
