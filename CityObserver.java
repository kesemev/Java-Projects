package Observer;

import java.util.Observable;
import java.util.Observer;

import graphics.CityPanel;

/**
 * Created by Kesem Even Hen on 25/05/2019.
 * Observer class.
 */
public class CityObserver extends Thread implements Observer {
	    private CityPanel pan;

	    public CityObserver(CityPanel pan){
	        this.pan = pan;
	    }

	    /**
	     * Update function that calls when the Observables notify.
	     * @param o
	     * @param arg
	     */
	    @Override
	    public void update(Observable o, Object arg) {
	        synchronized (this) {notify();}
	    }

	    @Override
	    public void run(){
	        while(true) {
	            try {synchronized (this) {wait();}}
	            catch (InterruptedException e){
	                System.out.println("CityObserver stopped!");
	                return;
	            }
	            pan.repaint();
	        }
	    }


}
