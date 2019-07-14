package Dialogs;

import Vehicles.Vehicle;
import graphics.CityPanel;

import javax.swing.*;
import ColorDecorator.ChangeColorVehicle;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Decorator dialog Class.
 */
public class DecoratorColorDialog extends JDialog implements ItemListener, ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel p1,VehiclePanel,ColorPanel,OkPanel;
    private JButton ok;
    private String[] colors = {"RED","GREEN","SILVER", "WHITE"};
	private JRadioButton[] rb;
	private ButtonGroup rbg;
	private JComboBox<String> vehicleList;
	private String c = null;
    private CityPanel ap;
    private ArrayList<Vehicle> vehicles;

    public DecoratorColorDialog(CityPanel pan, String title)
    {
    	super(new JFrame(),title,true);
        ap = pan;
        vehicles = ap.getVehicles();
    	setSize(600,250);
		setBackground(new Color(100,230,255));
		
		p1 = new JPanel();
		p1.setLayout(new GridLayout(1,2,0,0));
		
		VehiclePanel = new JPanel();
		VehiclePanel.setBorder(BorderFactory.createTitledBorder("Select vehicle to decorate"));
		VehiclePanel.setLayout(new BorderLayout());
		
		ColorPanel = new JPanel();
		ColorPanel.setBorder(BorderFactory.createTitledBorder("Choose decoration color"));
		ColorPanel.setLayout(new GridLayout(4,1,0,0));
		
		OkPanel = new JPanel();
		OkPanel.setLayout(new GridLayout(1,1,0,0));
		ok = new JButton("OK");
		ok.addActionListener(this);
		OkPanel.add(ok);
        ArrayList<String> vehicleLst = new ArrayList<>();
        vehicleLst.add("No vehicle");
		for (Vehicle vehicle: vehicles)
                vehicleLst.add(vehicles.indexOf(vehicle) + ". " + vehicle);
        vehicleList = new JComboBox<String>();
		for (String str: vehicleLst)
		    vehicleList.addItem(str);
		VehiclePanel.add("North", vehicleList);
		VehiclePanel.add("South", OkPanel);
		
		p1.add(VehiclePanel);
		p1.add(ColorPanel);
		
		rbg = new ButtonGroup();
		rb = new JRadioButton[colors.length];
		for(int i=0;i<colors.length;i++)
		{
			rb[i] = new JRadioButton(colors[i],false);
			rb[i].addItemListener(this);
			rbg.add(rb[i]);
			ColorPanel.add(rb[i]);
		}
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
            if (!selectedVehicle.equals("No animal") && c != null) {
            	ChangeColorVehicle cad = new ChangeColorVehicle(vehicles.get(Integer.parseInt("" + selectedVehicle.charAt(0))));
                cad.PaintVehicle(c);
                ap.repaint();
                setVisible(false);
            }
            else
                JOptionPane.showMessageDialog(this, "You must choose an animal and a color!");
		}

    }

    /**
     * Check for items changes and set the values.
     * @param e
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        for(int i=0;i<rb.length;i++)
            if(rb[i].isSelected())
            {
                c = colors[i];
                break;
            }
    }
}
