package Vehicles;
import graphics.CityPanel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.Future;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

import BorderDecorator.BorderedVehicle;

import LightDecorator.LightedVehicle;
import Vehicles.Location.Direction;
import graphics.*;

/**
 * Vehicle Class.
 * @version 1.10 23 April 2019
 * @author  Kesem Even-Hen, 208055483
 * @see JFrame
 */
public abstract class Vehicle extends Observable implements IMoveable, IDrawable, Cloneable, Runnable, ColorDecorator.ColoredVehicle, LightedVehicle, BorderedVehicle, Observer{

	protected double mileage;
	protected int speed;
	public static int size=65;
	protected int id;
	protected boolean lights;
	protected Color col;
	protected boolean isBorder;
	protected int wheels;
	protected Location loc;
	protected int fuelConsumption;
	public CityPanel pan;
	protected BufferedImage img1, img2, img3, img4;
	protected int durability;
	static int ID=1000;
	public String collidedWith;
    private Future<?> task;
    private boolean isRunning;
    protected boolean threadSuspended;
    
    
    public boolean isRunning() {
        return isRunning;
    }


    public void setTask(Future<?> task) {
        this.task = task;
    }

    public Future<?> getTask() {
        return task;
    }
		
	/**
	 * Constructor to Vehicle
	 * @param col
	 * @param wheels
	 * @param pan
	 */
	public Vehicle(Color col, int wheels,CityPanel pan) {
		id=ID++;
		this.col=col;
		this.wheels=wheels;
		mileage=0;
		loc= new Location(new Point(0,0),Direction.EAST);
		lights=false;
		this.pan = pan;
		fuelConsumption=0;
		isBorder = false;
	}
	


	public static enum Color { RED, GREEN, WHITE, SILVER;}
	public int getId() {return id;}
	public String getEngineName() {return "";}
	public Location getLocation() {return this.loc;}
	public double getMileage() {return this.mileage;}
	public double getWheels() {return wheels;}
	public String getLight() { if (lights==true) return "true"; else return "false";}
	public boolean setLocation(Location location) { this.loc=location; return true;}
    synchronized public void setSuspend() { threadSuspended = true; }
    synchronized public void setResume() { threadSuspended = false; notify(); }

	/**
	 * Receives a Point object and updates the position of the vehicle and the total mileage accordingly 
	 * (the addition per mileage will be the distance from Manhattan to the current location to the point).
	 * @param other (Point object)
	 * @return The method will return false if the transport tool does not move
	 */
	public boolean drive(Point other) {
		if (loc.getPoint().equals(other)==true) {
			return false; 
		}
		mileage+= loc.getPoint().getDistance(other);
		loc.setPoint(other);
		return true; 
	}
	

    /**
     * Override toString of Object class.
     * @return String to display in print.
     */
	public String toString() {
		return "Id=" + id + ", color=" + col + ", numberWheels=" + wheels + ", location="
				+ loc + ", mileage=" + mileage + ", lights=" + lights + ", ";
	}
	
	/**
	 * Function to Upload image
	 */
	public void loadImages() {

		try {
			img1=ImageIO.read(new File(IDrawable.PICTURE_PATH + this.getColor() + this.getVehicleName() + "North.png"));
			img2=ImageIO.read(new File(IDrawable.PICTURE_PATH + this.getColor() + this.getVehicleName() + "South.png"));
			img3=ImageIO.read(new File(IDrawable.PICTURE_PATH + this.getColor() + this.getVehicleName() + "East.png"));
			img4=ImageIO.read(new File(IDrawable.PICTURE_PATH + this.getColor() + this.getVehicleName() + "West.png"));

		} catch (IOException e) { 
			System.out.println("Cannot load picture"); 
		}
	}
	
	/**
	 * The run() method is executed.
	 */
    public void run() 
    {
    	isRunning = true;
        while (isRunning)
        {
    	   try 
           {
               Thread.sleep(1000/getSpeed());
               synchronized(this) {
                   while (threadSuspended)
                       wait();
               }
           } 
           catch (InterruptedException e) 
           {
           	System.out.println(getVehicleName() + " dead...");
           	return;
           }
           if (!checkCrash())
           {
        	   move(nextLocation());
               setChanged(); //observer
               notifyObservers();
           }
        	   
      }
   }
    
	
    /**
     * Check if there is a crashbetween two vehicles,
     * Crash defined as the cutting of the frame of both
     */
    private boolean checkCrash() {
		synchronized(this) {
			for(Vehicle other : pan.getVehicles()) 
			{
				if(this != other && this.isRunning() && other.isRunning() &&
				   (Math.abs(this.getLocation().getPoint().getX() - other.getLocation().getPoint().getX()) < 100 &&
				   (Math.abs(this.getLocation().getPoint().getY() - other.getLocation().getPoint().getY()) < 100))) 
				{
					//If this vehicle have a higher durability from the other vehicle
					if (this.getDurability()>other.getDurability() )
						oneDead(this,other);
					//If both vehicles have the same durability value - both are off the road (ie, disappear from the panel).
					else if (this.getDurability()==other.getDurability() )		
						twoDead(this,other);
					else
						oneDead(other,this);
					//Check if after the crash, the FUEL\EAT button need to paint in gray
					if(pan.CheckFuelVehicle())
					{
		               setChanged();
		               notifyObservers("paint gray");
					}
					return true;
				}
			}
		}
    	return false;
    }
    
    /**
     * Crash treatment with different type of vehicle
     * @param v1
     * @param v2
     */
    private void oneDead(Vehicle v1, Vehicle v2)
    {
		System.out.print("The "+v1.getVehicleName()+" killed the "+v2.getVehicleName()+" ==> ");
		v2.collidedWith=v1.getVehicleName()+ " " + this.getId();	
		v2.interrupt();
		pan.getVehicles().remove(v2);
 	   	pan.repaint();
    }
    
    /**
     * Crash treatment with same type of vehicle
     * @param v1
     * @param v2
     */
    private void twoDead(Vehicle v1, Vehicle v2)
    {
    	System.out.print("The "+v1.getVehicleName()+" and "+ v2.getVehicleName()+" killed each other==> ");
		v1.collidedWith=v2.getVehicleName()+ " " + v2.getId();
		v2.collidedWith=v1.getVehicleName()+ " " + v1.getId();
		v1.interrupt();
		v2.interrupt();
		pan.getVehicles().remove(v1);
		pan.getVehicles().remove(v2);
 	   	pan.repaint();
    }
		
	public java.awt.Color getColorG() {
		switch (col) {
		case RED:
			return java.awt.Color.red;
		case WHITE:
			return java.awt.Color.white;
		case GREEN:
			return java.awt.Color.green;
		case SILVER:
			return java.awt.Color.lightGray;
		default:
			return java.awt.Color.red;
		}
	}
    
	/**
	 * All vehicles must use the drawObject()
	 * (declared in the IDrawable interface) and call it in CityPanel.paintComponent()
	 */
	public void drawObject(Graphics g)
	{
		g.setColor(getColorG());
		
		Direction orientation= getLocation().getDirection();
		int x= loc.getPoint().getX();
		int y= loc.getPoint().getY();
		if(orientation==Direction.NORTH) // car drives to the north side
			g.drawImage(img1, x-size/2, y-size/10, size, size*2, pan);
		else if(orientation==Direction.SOUTH) // car drives to the south side
			g.drawImage(img2, x, y-size/10, size, size, pan);
		else if(orientation==Direction.EAST) // car drives to the east side
			g.drawImage(img3, x, y-size/10, size*2, size, pan);
		else if(orientation==Direction.WEST) // car drives to the west side
			g.drawImage(img4, x, y-size/10, size*2, size, pan);
	}
	
	/**
	 * A function that defines what is the next point to which the vehicle should arrive and returns this point 
	 * (note - this function does not change anything in the object but only returns the next point it reaches)
	 * @return point for the next location
	 */
	
	public Point nextLocation()
	{
		Direction orientation= getLocation().getDirection();
		int x= loc.getPoint().getX();
		int y= loc.getPoint().getY();
		int center = (pan.getSize().height-size) /2;
		if(center % this.getSpeed() != 0)
			center -= (center % this.getSpeed());		
		//top right corner
		if(x >= pan.getSize().width - size*1.5 && y <= 0) 
			topRightCorner(orientation);
		// top left corner
		else if(x <=size && y <= 0)
			topLeftCorner(orientation);
		// down right corner
		else if(x >= pan.getSize().width - size*1.5 && y>=pan.getSize().height - (size*1.5))
			downRightCorner(orientation);
		// down left corner
		else if(x <=size && y >= pan.getSize().height - size*1.5)	
			downLeftCorner(orientation);
		// right center
		else if(x >= pan.getSize().width - size && y == center)
			rightCenter(orientation, center);
		// left center
		else if(x <= size && y == center)
			leftCenter(orientation, center);
		//move the vehicle
		return Going();
	}
	
	
	/**
	 * top right corner
	 * @param orientation
	 */
	private void topRightCorner(Direction orientation){
		if(orientation == Direction.EAST)
		{
			loc.setDirection(Direction.SOUTH);
			loc.setPoint(new Point(pan.getSize().width - size,0));
		}
		else if(orientation == Direction.NORTH)
		{
			loc.setDirection(Direction.WEST);
			loc.setPoint(new Point(pan.getSize().width-size*2,0));
		}
	}
	
	/**
	 * top left corner
	 * @param orientation
	 */
	private void topLeftCorner(Direction orientation){
		if(orientation == Direction.NORTH)
		{
			loc.setDirection(Direction.EAST);
			loc.setPoint(new Point(0,0));
		}
		else if(orientation == Direction.WEST)
		{
			loc.setDirection(Direction.SOUTH);
			loc.setPoint(new Point(0,0));
		}
	}
	

	/**
	 * down right corner
	 * @param orientation
	 */
	private void downRightCorner(Direction orientation) {
		if(orientation == Direction.SOUTH)
		{
			loc.setDirection(Direction.WEST);
			loc.setPoint(new Point(pan.getSize().width - size*2,
					pan.getSize().height - (int)(size*1.5)));
		}
		else if(orientation == Direction.EAST)
		{
			loc.setDirection(Direction.NORTH);
			loc.setPoint(new Point(pan.getSize().width-(int)(size*0.5)+1 ,
					pan.getSize().height - (int)(size*2.5)+1));
		}
	}

	// down left corner
	private void downLeftCorner(Direction orientation) {
		if(orientation == Direction.WEST)
		{
			loc.setDirection(Direction.NORTH);	
			loc.setPoint(new Point(size/2,pan.getSize().height - (int)(size*2.5)+1));
			
		}
		else if(orientation == Direction.SOUTH)
		{
			loc.setDirection(Direction.EAST);
			loc.setPoint(new Point(0,pan.getSize().height - (int)(size*1.5)));
			
		}
	}
	
	/**
	 * right center
	 * @param orientation
	 * @param center
	 */
	private void rightCenter(Direction orientation, int center) {
		Random rand = new Random();
		if(orientation ==Direction.NORTH ||loc.getDirection() == Direction.SOUTH)
		{
			if(rand.nextBoolean())
			{
				loc.setDirection(Direction.WEST);
				loc.setPoint(new Point(pan.getSize().width - size*2,center));
			}
		}	
		else if(orientation == Direction.EAST)
		{
			if(rand.nextBoolean())
			{
				loc.setDirection(Direction.SOUTH);
				loc.setPoint(new Point(pan.getSize().width -size,center+size));
			}
			else
			{
				loc.setDirection(Direction.NORTH);
				loc.setPoint(new Point(pan.getSize().width-size,center-size));
				
			}
		}
	}		
	
	/**
	 * left center
	 * @param orientation
	 * @param center
	 */
	private void leftCenter(Direction orientation, int center) {
		Random rand = new Random();
		if(orientation == Direction.NORTH || loc.getDirection() == Direction.SOUTH) {
			if(rand.nextBoolean())
			{
				loc.setDirection(Direction.EAST);
				loc.setPoint(new Point(size,center));	
			}
		}
		else if(orientation == Direction.WEST)
		{
			if(rand.nextBoolean())
			{
				loc.setDirection(Direction.SOUTH);
				loc.setPoint(new Point(0,center + size));
			}
			else
			{
				loc.setDirection(Direction.NORTH);
				loc.setPoint(new Point(size/2,center-size));
			}
		}
	}
	
	/**
	 * if the point is not in the critical area
	 * @return point for the next location
	 */

	public Point Going() {
				if(loc.getDirection() == Direction.NORTH)
					return new Point (loc.getPoint().getX(),loc.getPoint().getY()-this.getSpeed());
				else if(loc.getDirection() == Direction.SOUTH)
					return new Point (loc.getPoint().getX(),loc.getPoint().getY()+this.getSpeed());
				else if(loc.getDirection() == Direction.EAST)
					return new Point (loc.getPoint().getX()+this.getSpeed(),loc.getPoint().getY());
				else if(loc.getDirection() == Direction.WEST)
					return new Point (loc.getPoint().getX()-this.getSpeed(),loc.getPoint().getY());
			return loc.getPoint();
	}

	/**
	 * 
	 */
	@Override
	public int getSpeed() {
		return speed;
	}
	
    /**
     * Override getColor of IDrawable interface.
     * @return Color col of the vehicle in String.
     */
	public String getColor() {
		return this.col.name();
	}

    /**
     *  Set new color to the vehicle.
     * @param color
     */
    @Override
    public void PaintVehicle(String color) {
    	col=pan.stringColorToEnum.get(color);
    	loadImages();
    }
    
	/**
	 *  Change the lights of the vehicle
	 */
	public void changeLights() { if (lights==true) lights=false; else lights=true;}
	
	/**
	 * Get fuelConsumption
	 */
	@Override 
	public int getFuelConsumption() {
		return fuelConsumption;
	}
	
	/**
	 * Get amount
	 * @return amount
	 */
	public int getAmount(){ 
		return 0;
	}
	
	/**
	 * interrupt function
	 */
    public void interrupt() {
        task.cancel(true);		
    }

    /**
     * set state
     * @param state
     */
    public void setRunning(boolean state) {
        isRunning = state;
    }

    /**
     * @return state of the vehicle
     */
    public String state(){
        if (isRunning())
            return "running";
		return "blocked";
    }
	
    /**
    * Copy instance of Animal.
    * @return Animal
    */
   @Override
   public Vehicle clone() {
       Object clone = null;
       try {
           clone = super.clone();
       } catch (CloneNotSupportedException e) {
           e.printStackTrace();
       }
       ((Vehicle)clone).setLocation(loc);
       return (Vehicle)clone;
   }
   
   /**
    * True or False if Vehicle has a border
    * @return
    */
	public boolean isBorder() {
		return isBorder;
	}

	/**
	 * change if vehicle has Border
	 */
	public void changeBorder() {
		isBorder = !isBorder;
	}
	
	/**
	 * update function for the observer Vehicle
	 */
	@Override
	public void update(Observable o, Object arg) {
		interrupt();
	}

	/**
	 * @param color
	 */
	public void setColor(Color color) {
		col=color;
	}

	/**
	 * Close the vehicle thread
	 */
    public void closeFromCity()
    {
    	isRunning = false;
    	interrupt();
    	setChanged();
    	notifyObservers("exitFromTheCity");
    }

}
