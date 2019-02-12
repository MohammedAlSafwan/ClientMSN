package gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import logic.MsnLogic;

/**
 * @author Mohammed Al-Safwan
 * @date Jan 30, 2019
 */
public class Main extends JFrame {

	private JPanel mCards; 									//A JPanel to show one card at a time
	private MsnGUI mMainFrame; 						//JFrame to hold the Main screen

	final String MSN_FRAME = "MSNFrame"; 		//Name tag for Game screen

	final String START_FRAME = MSN_FRAME; 	//Name tag to hold the first screen to show

	final int HEIGHT = 800; 									//Holds the Main Height for all screens
	final int WIDTH = 1500; 									//Holds the Main Width for all screens

	private Main() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	private Main()
		//
		// Method parameters	:	
		//
		// Method return		:	
		//
		// Synopsis				:   Constructor method for the Main class. It creates the necessary frames.
		//							
		//
		// Modifications		:
		//								   Date						  Developer								Notes
		//									----			      			---------			 	     			 -----
		//							Mar 1, 2018		  Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		setSystemLookAndFeel(); 									//Set the look and feel to the look and feel of the system
		createCards(); 														//Create the necessary frames
		setWindowProps(); 												//Sets the Main frame properties 
	}

	private void createCards() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	private void createCards()
		//
		// Method parameters	:	
		//
		// Method return		:	void
		//
		// Synopsis				:   Create the Card holder and all the frames needed and add them to individual cards, then add them to the card holder.
		//							
		//
		// Modifications		:
		//								   Date						  Developer								Notes
		//									----			      			---------			 	     			 -----
		//							Mar 1, 2018		  Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		mCards = new JPanel(new CardLayout()); 													//Initialize main card holder

		mMainFrame = new MsnGUI(); 																	//Initialize Main frame

		mCards.add(mMainFrame.setFrameSize(HEIGHT, WIDTH), MSN_FRAME); 	//Add Main frame to the card holder and set the Width and Height of the frame

		add(mCards); 																								//Add Cards as the main layout controller
		((CardLayout) (mCards.getLayout())).show(mCards, START_FRAME); 			//Show the main screen

		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {

			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}

			@Override
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to close this window?", "Close Window?",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					MsnLogic.getInstance().sendClose();
					System.exit(0);
				}

			}

			@Override
			public void windowClosed(WindowEvent e) {

			}

			@Override
			public void windowActivated(WindowEvent e) {

			}
		});

	}

	private void setWindowProps() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	private void setWindowProps()
		//
		// Method parameters	:	
		//
		// Method return		:	void
		//
		// Synopsis				:   Set up the main window properties 
		//							
		//
		// Modifications		:
		//								   Date						  Developer								Notes
		//									----			      			---------			 	     			 -----
		//							Mar 1, 2018		  Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 	//Get system screen dimensions
		setSize(WIDTH, HEIGHT); 																			//Set the Width and Height of the main screen
		setLocation((int) ((screenSize.getWidth() / 2) - (WIDTH / 2)),
				(int) ((screenSize.getHeight() / 2) - (HEIGHT / 2))); 							//Set the location of the screen to be in the center
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 								//Set the closing operation to terminate the application on close
		setAlwaysOnTop(false); 																				//Will not set it to always be on top so error screen could show on top
		setVisible(true); 																							//Set the visibility to true, so it the main frame can be drawn
	}

	private void setSystemLookAndFeel() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void setSystemLookAndFeel()
		//
		// Method parameters	:	
		//
		// Method return		:	void
		//
		// Synopsis				:   Get the look and feel of the system
		//							
		//
		// Modifications		:
		//							Date			    Developer				Notes
		//							  ----			      ---------			 	     -----
		//							Nov 27, 2018		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 							//Get the look and feel of the system
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException ex) {
		}
	}

	public static void main(String[] args) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void main()
		//
		// Method parameters	:	args - the method permits String command line parameters to be entered
		//
		// Method return		:	void
		//
		// Synopsis				:   
		//							
		//
		// Modifications		:
		//							Date			    Developer				Notes
		//							  ----			      ---------			 	     -----
		//							Jan 30, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		EventQueue.invokeLater(() -> { 												//Call invokeLater() to insure that the application is being run on the dispatch thread
			try {
				new Main(); 																	//Initialize the application
			} catch (Exception e) { 														//Catch any exception that may happen
				e.printStackTrace(); 															//Print out the exception
			}
		});
	}

}
