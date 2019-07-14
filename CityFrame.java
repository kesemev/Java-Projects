package graphics;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CityFrame Class.
 *
 * @version 1.10 23 April 2019
 * @author  Kesem Even-Hen, 208055483
 * @see JFrame
 *
 */
public class CityFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private CityPanel panel;
    private JMenu m1, m2;
    private JMenuItem m3, m4;
    private JMenuBar mb;

	   /**
	    * main function
	    * @param args
	    */
	   public static void main(String[]args)
	   {
		   CityFrame city = new CityFrame();
		   city.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   city.setSize(800,600);
		   city.setVisible(true);
	   }

	   /**
	    * constructor for CityFrame
	    */
	   public CityFrame()
	   {
		    super("City");
		    //Singleton (CityPanel)
			panel = CityPanel.getInstance();

		    add(panel);
		    panel.setVisible(true);
		    
		    
			mb = new JMenuBar();
			
			m1 = new JMenu("File");
			m2 = new JMenu("Help");
		
			m3 = new JMenuItem("Exit");
			m4=  new JMenuItem("Help");

			m1.add(m3);
			m2.add(m4);

			mb.add(m1);
			mb.add(m2);
			
			m3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					System.exit(0);
				}
			});
			
			m4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					printHelp();
				}
			});
			setJMenuBar(mb);

	        
	   }
	   
		/**
		 * Function to exit
		 */
		public void destroy() {
			panel.destroy();
		}
		
		/**
		 * showMessageDialog for Help button
		 */
		public void printHelp() {
			JOptionPane.showMessageDialog(this, "Home Work 2\nGUI");
		}
	   
}


