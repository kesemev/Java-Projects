package graphics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * AddVehicleDialog Class.
 *
 * @version 1.10 23 April 2019
 * @author  Kesem Even-Hen, 208055483
 * @see JDialog
 *
 */

public class AddVehicleDialog extends JDialog implements ItemListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private CityPanel ap;
    private String[] colors = {"RED","GREEN","WHITE","SILVER"};
    private String[] vehicles = {"Bike","Carriage","Solar Car","Benzine Car"};
	private ButtonGroup rbg, rbg1;
	private JRadioButton[] rb;
	private JRadioButton[] rb1;
	private JPanel VehiclePanel;
	private JPanel ColorPanel,Choose, p1;
	private JPanel ButtonsPanel;
	private JButton okBtn;
	private JButton cancelBtn;
	private String chosenVehicle;
	private String c;
	private JSlider tran;
	/**
	 * itemEvent for colorsItemListener
	 */
	private ItemListener colorsItemListener = new ItemListener() {
		public void itemStateChanged(ItemEvent itemEvent) {
			for(int i=0;i<rb.length;i++) {
				if(rb[i].isSelected())
			    {
			    	c = colors[i];
			    	break;
		        }
			}
		}
	};
	
	/**
	 * itemEvent for vehicleItemListener
	 */
	private ItemListener vehicleItemListener = new ItemListener() {
		public void itemStateChanged(ItemEvent itemEvent) 
		{
			for(int i=0;i<rb1.length;i++) 
			{
			if(rb1[i].isSelected())
				{
					chosenVehicle = vehicles[i];
					break;
				}
			}
			p1.setVisible(false);
			if (chosenVehicle.equals("Bike"))
			{
				p1.setVisible(true);
			}
		}
	};
	
	/**
	 * Add Vehicle Dialog for CityPanel pan
	 * @param pan
	 * @param title
	 */
	public AddVehicleDialog(CityPanel pan, String title)
    {
    	super(new JFrame(),title,true);
    	ap = pan;
   
    	setSize(250, 250);
		//setBackground(new Color(100,230,255));
		
		ColorPanel = new JPanel();
		
		ColorPanel.setBorder(BorderFactory.createTitledBorder("Choose a color"));
		ColorPanel.setLayout(new GridLayout(4,1,10,10));
		
		rbg = new ButtonGroup();
		rb= new JRadioButton[colors.length];
		for(int i=0;i<colors.length;i++)
		{
		    rb[i] = new JRadioButton(colors[i],false);
		    rb[i].addItemListener(colorsItemListener);
		    rbg.add(rb[i]);
		    ColorPanel.add(rb[i]);
		}
		rb[0].setSelected(true);
		
		VehiclePanel = new JPanel();
		VehiclePanel.setBorder(BorderFactory.createTitledBorder("Choose a vehicle"));
		VehiclePanel.setLayout(new GridLayout(4,1,5,5));
		
		rbg1 = new ButtonGroup();
		rb1=new JRadioButton[vehicles.length];
		for(int i=0;i<vehicles.length;i++)
		{
		    rb1[i] = new JRadioButton(vehicles[i], false);
		    rb1[i].addItemListener(vehicleItemListener);
		    rbg1.add(rb1[i]);
		    VehiclePanel.add(rb1[i]);
		}
		
		Choose = new JPanel();
		Choose.add(ColorPanel);
		Choose.add(VehiclePanel);
		
		p1 = new JPanel();
		p1.setVisible(false);
		tran = new JSlider(0,10);
		tran.setMajorTickSpacing(2);
		tran.setMinorTickSpacing(1);
		tran.setPaintTicks(true);
		tran.setPaintLabels(true);
		p1.add(tran);
		
		ButtonsPanel = new JPanel();
		ButtonsPanel.setLayout(new GridLayout(1,2,5,5));
		okBtn = new JButton("Ok");
		okBtn.addActionListener(this);
		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(this);
		ButtonsPanel.add(okBtn);
		ButtonsPanel.add(cancelBtn);
		
        setLayout(new BorderLayout());
		add("North", Choose);
		add("Center", p1);
		add("South", ButtonsPanel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }
	
	@Override
	public void actionPerformed(ActionEvent e)
    {
 		if(e.getSource() == okBtn)
		{
		    ap.addVehicle1(chosenVehicle,c, tran.getValue());
		    setVisible(false);
		}
		else 
		    setVisible(false);
    }

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
	}

	    
}
