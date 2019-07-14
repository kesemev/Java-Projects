package Dialogs;
import LightDecorator.ChangeLightVehicle;
import Vehicles.Vehicle;
import graphics.CityPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Decorator dialog Class.
 */
public class DecoratorLightDialog extends JDialog implements ActionListener {
		private static final long serialVersionUID = 1L;
		private JPanel p1,p2,p4;
	    private JButton ok;
		private JComboBox<String> vehicleList;
	    private CityPanel ap;
	    private ArrayList<Vehicle> vehicles;

	    public DecoratorLightDialog(CityPanel pan, String title)
	    {	
	    	super(new JFrame(),title,true);
	        ap = pan;
	        vehicles = ap.getVehicles();
	    	setSize(600,250);
			setBackground(new Color(100,230,255));
			p1 = new JPanel();
			p1.setLayout(new GridLayout(1,2,0,0));
			p2 = new JPanel();
			p2.setBorder(BorderFactory.createTitledBorder("Select vehicle to decorate"));
			p2.setLayout(new BorderLayout());

			p4 = new JPanel();
			p4.setLayout(new GridLayout(1,1,0,0));
			ok = new JButton("OK");
			ok.addActionListener(this);
			p4.add(ok);
	        ArrayList<String> vehicleLst = new ArrayList<>();
	        vehicleLst.add("No vehicle");
			for (Vehicle vehicle: vehicles)
	                vehicleLst.add(vehicles.indexOf(vehicle) + ". " + vehicle);
	        vehicleList = new JComboBox<String>();
			for (String str: vehicleLst)
			    vehicleList.addItem(str);
			p2.add("North", vehicleList);
			p2.add("South", p4);
			p1.add(p2);

			
			setLayout(new BorderLayout());
			add("Center" ,p1);
			
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setResizable(false);
	        setLocationRelativeTo(null);
	    }

	    /**
	     * Check for selected vehicle and decorate it or print message.
	     * @param e
	     */
	    public void actionPerformed(ActionEvent e)
	    {
	 		if(e.getSource() == ok)
			{
	            String selectedVehicle = vehicleList.getSelectedItem().toString();
	            if (!selectedVehicle.equals("No vehicle") ) {
	            	ChangeLightVehicle cad = new ChangeLightVehicle(vehicles.get(Integer.parseInt("" + selectedVehicle.charAt(0))));
	                cad.changeLights();
	                setVisible(false);
	            }
	            else
	                JOptionPane.showMessageDialog(this, "You must choose an vehicle!");
			}

	    }



}
