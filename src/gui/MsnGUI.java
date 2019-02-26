package gui;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import logic.Message;
import logic.MsnLogic;
import util.Announcement;
import util.JHintUI;
import util.MessageAdapter;
import util.MessageType;
import javax.swing.JLabel;
import javax.swing.JButton;

/**
 * @author Mohammed Al-Safwan
 * @date Jan 31, 2019
 */
public class MsnGUI extends JPanel implements Announcement {

	private MsnLogic mMsnLogic;
	private JTextField mMsgBox;
	private JList<Message> mMsgsPanel;
	private JTextField mTxtIpaddress;
	private SwingWorker<Void, Void> mJobWorker;

	public MsnGUI() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	public MsnGUI() 
		//
		// Method parameters	:
		//
		// Method return		:
		//
		// Synopsis				:  Constructor
		//							
		//
		// Modifications		:
		//							Date			    Developer				Notes
		//							  ----			      ---------			 	     -----
		//							Jan 15, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		mMsnLogic = MsnLogic.getInstance();
		mMsnLogic.setAnnouncer(this);
		mMsnLogic.setSender(JOptionPane.showInputDialog("what is your name?"));
		mJobWorker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				while (true) {
					mMsnLogic.sendMessage("", MessageType.GET_MSG_AFTER_DATE);
					publish();
					System.err.println("Before wait");
					Thread.sleep(1000);
					System.err.println("After wait");
				}
			}

			/* (non-Javadoc)
			 * @see javax.swing.SwingWorker#process(java.util.List)
			 */
			@Override
			protected void process(List<Void> chunks) {
				// TODO Auto-generated method stub
				super.process(chunks);
				updateMsnGUI();
			}

			/* (non-Javadoc)
			 * @see javax.swing.SwingWorker#done()
			 */
			@Override
			protected void done() {
				// TODO Auto-generated method stub
				super.done();

			}

		};
		init();
	}

	private void init() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void init()
		//
		// Method parameters	:	
		//
		// Method return		:	void
		//
		// Synopsis				:   Initialize the window components 
		//							
		//
		// Modifications		:
		//							Date			    Developer				Notes
		//							  ----			      ---------			 	     -----
		//							Jan 15, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		setLayout(null);
		setSize(1500, 800);

		mMsgsPanel = new JList<Message>(); //The messages display
		mMsgsPanel.setListData(mMsnLogic.getMessages().toArray(new Message[0]));
		mMsgsPanel.setBounds(12, 13, 1460, 623);
		mMsgsPanel.setCellRenderer(new MessageAdapter());

		JScrollPane scrollPane = new JScrollPane(mMsgsPanel); //Add the messages to a scroll pane
		scrollPane.setBounds(12, 13, 1460, 623);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		add(scrollPane);

		mMsgBox = new JTextField();
		mMsgBox.setUI(new JHintUI("Potatos are heaven"));
		mMsgBox.setBounds(12, 649, 1460, 34);
		mMsgBox.setColumns(10);
		mMsgBox.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!mMsnLogic.isRunning()) {
						mMsnLogic.setServerAddress(mTxtIpaddress.getText());
						runJobThread();
					}
					mMsnLogic.sendMessage(mMsgBox.getText(), MessageType.SEND);
					refreshMessageList();
					mMsgBox.setText("");

				}
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});
		add(mMsgBox);

		mTxtIpaddress = new JTextField();
		mTxtIpaddress.setText("127.0.0.1");
		mTxtIpaddress.setBounds(12, 696, 116, 22);
		mTxtIpaddress.setColumns(10);
		add(mTxtIpaddress);

		JLabel lblPort = new JLabel(": 6666");
		lblPort.setBounds(135, 699, 56, 16);
		add(lblPort);

		JButton btnConnect = new JButton("Connect");
		btnConnect.setBounds(189, 696, 97, 25);
		btnConnect.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				//Empty the view from messages
				DefaultListModel<Message> listModel = new DefaultListModel<>();
				mMsgsPanel.setModel(listModel);
				revalidate();
				repaint();
				//
				mMsnLogic.setServerAddress(mTxtIpaddress.getText());
				runJobThread();
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
		add(btnConnect);

	}

	synchronized private void refreshMessageList() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	private void refreshMessageList() 
		//
		// Method parameters	:	
		//
		// Method return		:	void
		//
		// Synopsis				:   refresh the messages dispalyed on the screen
		//							
		//
		// Modifications		:
		//							Date			    Developer				Notes
		//							  ----			      ---------			 	     -----
		//							Jan 15, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		Lock lock = new ReentrantLock();
		lock.lock();

		Message[] list = mMsnLogic.getMessages().toArray(new Message[0]);
		DefaultListModel<Message> listModel = new DefaultListModel<>();

		for (Message msg : list) {
			listModel.addElement(msg);
		}
		if (null != mMsgsPanel && null != listModel && !listModel.isEmpty()) { //in case the timer to get all the messages started before the user getting to the main screen
			mMsgsPanel.setModel(listModel);
			mMsgsPanel.ensureIndexIsVisible(listModel.size() - 1);
			revalidate();
			repaint();
		}
		lock.unlock();
	}

	public Component setFrameSize(int height, int width) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	public Component setFrameSize(int height, int width)
		//
		// Method parameters	:	height - the height of the DrawGUI
		//											width - the width of DrawGUI
		//
		// Method return		:	Component
		//
		// Synopsis				:   resize this panel to the new width and height
		//							
		//
		// Modifications		:
		//							Date			    Developer				Notes
		//							  ----			      ---------			 	     -----
		//							Jan 15, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		setSize(width, height);
		return this;
	}

	@Override
	public void updateMsnGUI() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	public void updateMsnGUI() 
		//
		// Method parameters	:	
		//
		// Method return		:	void
		//
		// Synopsis				:   Ask the GUI class to update the messages list
		//							
		//
		// Modifications		:
		//								Date			    				Developer							   Notes
		//							 	 ----			      				---------			 	     				-----
		//							Jan 15, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		refreshMessageList();
	}

	private void runJobThread() {
		new Thread(() -> {
			mJobWorker.run();
		}).start();

	}
}
