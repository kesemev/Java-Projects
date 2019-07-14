package graphics;
import Vehicles.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import BorderDecorator.RecBorderVehicle;
import Dialogs.DecorateBorderDialog;
import Dialogs.DecoratorColorDialog;
import Dialogs.DecoratorLightDialog;
import FactoryMethod.CityFactoryMethod;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import Observer.CityObserver;
import Memento.Caretaker;
import Memento.Originator;
import Memento.CityMemento;

/**
 * CityPanel Class.
 * @version 1.10 23 April 2019
 * @author  Kesem Even-Hen, 208055483
 * @see JFrame
 */
public class CityPanel extends JPanel implements ActionListener, Observer
{
	private static final long serialVersionUID = 1L;
	private static final int MAX_VEHICLE_NUMBER  = 5; // Maximum number of threads in thread pool 
	private BufferedImage img=null;
	private String BACKGROUND_PATH = IDrawable.PICTURE_PATH + "cityBackground.png";
	private JPanel p1;
    private JPanel p2;
    private JPanel p;
	private JButton[] b_num;
    private JButton[] b_num1;
	private String[] names = {"Add Vehicle","Clear","Fuel/Food","Decorate Lights","Info","Decorate Colors","Decorate Border", "Save state", "Restore State", "Exit"};
	public HashMap<String, Vehicle.Color> stringColorToEnum = new HashMap<String, Vehicle.Color>();
	public ArrayList<Vehicle> vehicles;
	public Queue<Vehicle> waitingQueue;
	public ArrayList<Vehicle> vehiclesInfo;
    private static CityPanel instance = null;
    private Info info=null;
    public boolean isTableVisible = false;
    private CityObserver controller;
    private ExecutorService threadPool;
    private Originator originator;
    private CityMemento CityMemento;
    private Caretaker caretaker;
    public int NumberOfNotMoving;
    
	/**
	 * Constructor for CityPanel
	 */
	private CityPanel() 
	{
	    // creates a thread pool with MAX_T no. of  
	    // threads as the fixed pool size 5
		threadPool = Executors.newFixedThreadPool(MAX_VEHICLE_NUMBER);
		
		stringColorToEnum.put("RED", Vehicle.Color.RED);
		stringColorToEnum.put("GREEN", Vehicle.Color.GREEN);
		stringColorToEnum.put("SILVER", Vehicle.Color.SILVER);
		stringColorToEnum.put("WHITE", Vehicle.Color.WHITE);
		
		vehicles=new ArrayList<Vehicle>();
		waitingQueue = new LinkedList<>();
		vehiclesInfo=new ArrayList<Vehicle>();	//for Info()
		
		info = Info.getInstance(vehiclesInfo);
		add(info, BorderLayout.CENTER);
        originator = new Originator();
        caretaker = new Caretaker();
        controller = new CityObserver(this);
        controller.start();

		
		try { img = ImageIO.read(new File(BACKGROUND_PATH)); } 
		  catch (IOException e) { System.out.println("Cannot load image"); }
		
	    p1=new JPanel();
		p1.setLayout(new GridLayout(1,5,0,0));
		p1.setBackground(new Color(0,150,255));

		b_num=new JButton[5];
		for(int i=0;i<5;i++)
		{
		    b_num[i]=new JButton(names[i]);
		    b_num[i].addActionListener(this);
		    b_num[i].setBackground(Color.lightGray);
		    p1.add(b_num[i]);		
		}
		setLayout(new BorderLayout());
		add("South", p1);
		
        p2=new JPanel();
        p2.setLayout(new GridLayout(1,5,0,0));
        p2.setBackground(new Color(0,150,255));
        
		b_num1=new JButton[5];
        for(int i=0;i<5;i++)
        {
            b_num1[i]=new JButton(names[i+5]);
            b_num1[i].addActionListener(this);
            b_num1[i].setBackground(Color.lightGray);
            p2.add(b_num1[i]);
        }

        setLayout(new BorderLayout());
        p = new JPanel();
        p.setLayout(new GridLayout(2,6,0,0));
        p.setBackground(new Color(0,150,255));
        p.add(p1);
        p.add(p2);
        add("South", p);

	}
	
	/**
	 * Singleton (CityPanel)
	 * @param f
	 * @return
	 */
    public static CityPanel getInstance() {
        if(CityPanel.instance == null) {
        	CityPanel.instance = new CityPanel();
        }
        return CityPanel.instance;
    }
    
	/**
	* Graphics settings for the exercise
	*/
    @Override
	public void paintComponent(Graphics g)   
	{
	     super.paintComponent(g); 
	     if (img!=null)
	    	 g.drawImage(img,0,0,getWidth(),getHeight(), this);
     
	   	synchronized(this) {
		   	for(Vehicle ve : vehicles) {
                if (ve.isRunning()) {
                	if(ve.isBorder()) {
                		RecBorderVehicle recBorderVehicle = new RecBorderVehicle(ve);
                		recBorderVehicle.drawObject(g);
                		continue;
                	}
                    ve.drawObject(g);
                }
                
		 	}
	   	}
	}
	   
	  	   
    /**
     * Run the Add Vehicle Dialog, Only if vehicle is null
     */
    private void AddVehicleDialog() 
    {

	   if(vehicles.size()==MAX_VEHICLE_NUMBER) {
		   JOptionPane.showMessageDialog(this, "You can not add more than " + MAX_VEHICLE_NUMBER + " vehicles, So any request for additional vehicles will be queued");
	   }
	   AddVehicleDialog dialog = new AddVehicleDialog(this,"Add an vehicle to the city");
	   dialog.setVisible(true);
		   
    }
	   
   /**
    * Opens a window of AddVehicleDialog, which allows to define new vehicles
    * @param name
    * @param c
    * @param tran
    */
    public void addVehicle1(String name, String c, int tran) {
	   CityFactoryMethod VehiclesFactory = new CityFactoryMethod(); //factory method- creating the factory 
	   Vehicle ve = VehiclesFactory.getVehicle(name, c, tran, this); //factory instantiates an object 
       ve.setTask((threadPool).submit(ve)); //The Java ExecutorService submit(Runnable) method also takes a Runnable implementation, but returns a Future object. This Future object can be used to check if the Runnable has finished executing.
       ve.addObserver(info); 
	   ve.addObserver(this); 
	   vehicles.add(ve);
	   vehiclesInfo.add(ve);
	   ve.loadImages();
	   repaint();
   }
	   
    /**
     * Check if Vehicle is Running and resume it.
     */
    public void start() {
        for(Vehicle ve : vehicles) {
            if (ve.isRunning()) {
                ve.setResume();
            }
        }
    }

    /**
     * Check if Vehicle is Running and stop it.
     */
    public void stop() {
        for(Vehicle ve : vehicles) {
            if (ve.isRunning())
                ve.setSuspend();
        }
    }

    /**
     * Clear running Vehicles.
     */
    synchronized public void clear()
    {
        ArrayList<Vehicle> tmp = new ArrayList<>();
        for(Vehicle ve : vehicles) {
            if (ve.isRunning())
                ve.interrupt();
            else
                tmp.add(ve);
        }
        vehicles = tmp;
        b_num[2].setBackground(Color.lightGray);
        repaint();
    }
	    
	    	        
    /**
    * Fuel / food distribution. Add an option to choose a type of feed: 
    * "Benzine" or Solar (suitable for motorized vehicles) or "Food" (suitable for carriage)
    */
    public void addFood() 
    {
	   for(Vehicle ve : vehicles)
	   {	
			if(ve.getVehicleName().equals("Car")) {
				HasEngine j = (HasEngine)ve;
				j.refuel();
			}
			if(ve.getVehicleName().equals("Carriage")) {
				Carriage j = (Carriage)ve;
				j.feed();
			}	
	        synchronized (ve) {
	        	ve.notify();
	        }
	   }
	   b_num[2].setBackground(Color.lightGray);
	   repaint();
	   JOptionPane.showMessageDialog(this, "All the vehicle are fueled/ate");
   }

	   
   /**
    * Exit the program
    */
   public void destroy()
   { 
      controller.interrupt();
	  for(Vehicle ve : vehicles)
		  ve.closeFromCity();
	  System.exit(0);
   }
   
	   
    /**
     * Open decorate color dialog.
     */
    private void decorateColor()
    {
        DecoratorColorDialog dec = new DecoratorColorDialog(this, "Decorate an vehicle");
        stop();
        dec.setVisible(true);
        start();
    }
	    
    /**
     * Open decorate light dialog.
     * Changes the light variable to True if it was, another False
     */
    private void decorateLight()
    {
        DecoratorLightDialog dec = new DecoratorLightDialog(this, "Decorate an vehicle");
        stop();
        dec.setVisible(true);
        start();
    }
    
    
    /**
     * Open decorate light dialog.
     * Changes the light variable to True if it was, another False
     */
    private void decorateBorder()
    {
        DecorateBorderDialog dec = new DecorateBorderDialog(this, "Decorate an vehicle");
        stop();
        dec.setVisible(true);
        start();
    }
    
    
    private void showInfo() {
    	if(!info.isVisible()) {
    		info.updateData();
    		info.setVisible(true);
    	}
    	else {
    		info.setVisible(false);
    	}
    	repaint();
    }
    
    /**
     * Open choose state to restore dialog.
     */
    private void restore(){
        if (caretaker.notEmpty()) {
            Object[] options = {"State 1", "State 2", "State 3", "Cancel"};
            int n = JOptionPane.showOptionDialog(this, "Please choose state for restore", "Saved states",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[3]);
            if (n != 3 && n != -1)
                setCityMemento(originator, n);
        }
        else{
            JOptionPane.showMessageDialog(this, "You have not saved states");
        }
    }

    /**
     * Restore choosen state.
     * @param originator
     * @param i
     */
    private void setCityMemento(Originator originator, int i){
        CityMemento memento = caretaker.getMemento(i);
        if (memento != null) {
            originator.setMemento(memento);
            for(Vehicle an : vehicles)
                an.interrupt();
            vehicles = originator.getVehicles();
            for (Vehicle vehicle : vehicles)
                vehicle.setTask(((ExecutorService) threadPool).submit(vehicle));
            caretaker.removeMemento(i);
            repaint();
        }
        else{
            JOptionPane.showMessageDialog(this, "There is no such state " + String.valueOf(i+1));
        }
    }

    /**
     * Saving state.
     */
    private void save() {
        if(caretaker.notFull()) {
            Object[] options = {"State 1", "State 2", "State 3", "Cancel"};
            int n = JOptionPane.showOptionDialog(this, "Please choose state for save", "Save state",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[3]);
            if (n != 3 && n != -1 ) {
                if (caretaker.getMemento(n) == null) {
                    originator.setState(vehicles);
                    CityMemento = originator.createMemento();
                    caretaker.addMemento(CityMemento, n);
                }
                else
                    JOptionPane.showMessageDialog(this, "This cell is full");
            }
        }
        else
            JOptionPane.showMessageDialog(this, "You have reached the maximum states limit");

    }

	   
   /**
    * Action Event to the panel
    */
   public void actionPerformed(ActionEvent e)
   {
	if(e.getSource() == b_num[0]) // "Add Vehicle"
		AddVehicleDialog();
	else if(e.getSource() == b_num[1]) // "Clear"
		clear();
	else if(e.getSource() == b_num[2]) // "Fuel/Food"
		addFood();
	else if(e.getSource() == b_num[3]) // "Decorate Lights"
		decorateLight();
	else if(e.getSource() == b_num[4]) // "Info"
		showInfo();
    else if(e.getSource() == b_num1[0]) // "Decorate Color"
        decorateColor();
    else if(e.getSource() == b_num1[1]) // "Decorate Border"
        decorateBorder();
    else if(e.getSource() == b_num1[2]) // "Save state"
        save();
    else if(e.getSource() == b_num1[3]) // "Restore state"
        restore();
	else if(e.getSource() == b_num1[4]) // "Exit"
		destroy();
   }
   
   /**
    * @return ArrayList<Vehicle> of all the running vehicle
    */
   public ArrayList<Vehicle> getVehicles(){
	   return vehicles;
   }
   
   /**
    * @return ArrayList<Vehicle> of all the vehicle that created
    */
   public ArrayList<Vehicle> getInfoVehicles(){
	   return vehiclesInfo;
   }
   
   /**
    * Check if there is at list one vehicle that not fuel
    * if false, the red button not change for gray
    * else change the button to gray by the observer
    * @return
    */
   public boolean CheckFuelVehicle() {
//	   synchronized(this){
		   for(Vehicle ve : vehicles) {
			   if (ve.isRunning()) {
				   if(ve.getVehicleName().equals("Car")) {
						Car j = (Car)ve;
						if(!j.canMove())
							return false;
						}
				   if(ve.getVehicleName().equals("Carriage")) { 
						Carriage j = (Carriage)ve;
						if(!j.canMove())
							return false;
				   }
			   }
		   }
//	   }
	   return true;
   }
   


/**
    * update function for observer City Panel
    */
	@Override
	public void update(Observable ob, Object arg1) {
		if(arg1 == "paint red") 
			b_num[2].setBackground(Color.red);
		else if(arg1 == "paint gray") 
			b_num[2].setBackground(Color.lightGray);
		else if(arg1 == "")
			System.out.println("Goodbye...");
	}
}