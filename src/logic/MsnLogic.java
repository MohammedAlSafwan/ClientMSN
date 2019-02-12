package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.Announcement;
import util.MessageType;

/**
 * @author Mohammed Al-Safwan
 * @date Jan 31, 2019
 */
public class MsnLogic {

	private MsnService mMsnService = null; //The server to connect with
	private static MsnLogic mInstance = null; //Instance of this class
	private ArrayList<Message> mMessages = null; //Array List that holds all the messages
	private String mSenderName; //The current client name
	private final String MESSAGES = "MESSAGES";
	private Timer mTimer;
	private boolean mIsRunning = false;
	private Announcement announcer;

	private MsnLogic() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	private MsnLogic() 
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
		//							Feb 1, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		mMsnService = new MsnService();
		mMessages = new ArrayList<>();
		mSenderName = "";
		mTimer = new Timer();
		mIsRunning = false;
	}

	public void run() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	public void run()
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
		//							Feb 1, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		sendMessage("", MessageType.GET_ALL_MSGS);

		mTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				sendMessage("", MessageType.GET_MSG_AFTER_DATE);
				if (null != announcer)
					announcer.updateMsnGUI();
			}
		}, 5 * 1000, 1 * 1000);
		mIsRunning = true;
	}

	public static MsnLogic getInstance() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	public static MsnLogic getInstance()
		//
		// Method parameters	:	args - the method permits String command line parameters to be entered
		//
		// Method return		:	Object
		//
		// Synopsis				:   
		//							
		//
		// Modifications		:
		//							Date			    Developer				Notes
		//							  ----			      ---------			 	     -----
		//							Jan 31, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		if (null == mInstance) {
			mInstance = new MsnLogic();
		}
		return mInstance;
	}

	public void sendMessage(String msg, MessageType msgType) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	public void sendMessage(String msg, MessageType msgType)
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
		//							Feb 1, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		switch (msgType) {
		case EMPTY:
			sendEmpty(msg);
			break;
		case NULL:
			sendNull(msg);
			break;
		case SEND:
			sendSend(msg);
			break;
		case GET_ALL_MSGS:
			sendGetAllMsgs(msg);
			break;
		case GET_MSG_AFTER_DATE:
			sendGetMsgAfterDate(msg);
			break;
		case CLOSE:
			sendClose();
			break;
		default:
			System.out.println("Something went wrong with the msg:");
			break;
		}
	}

	private void sendEmpty(String msg) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	private void sendEmpty(String msg)
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
		//							Feb 1, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	}

	private void sendNull(String msg) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	private void sendNull(String msg)
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
		//							Feb 1, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	}

	private void sendSend(String msg) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	private void sendSend(String msg) 
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
		//							Feb 1, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		Message newMsg = new Message();
		newMsg.setType(MessageType.SEND);
		newMsg.setSender(mSenderName);
		newMsg.setMessage(msg);

		JSONObject request = newMsg.toJSON();

		String respond = mMsnService.sendRequest(request.toString());

		digestRespond(respond);

		//		mMessages.add(newMsg);
	}

	private void sendGetAllMsgs(String msg) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	private void sendGetAllMsgs(String msg)
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
		//							Feb 1, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		Message newMsg = new Message();
		newMsg.setType(MessageType.GET_ALL_MSGS);
		newMsg.setSender(mSenderName);
		newMsg.setMessage(msg);
		newMsg.setMessageDate(new Date());

		JSONObject request = newMsg.toJSON();

		String respond = mMsnService.sendRequest(request.toString());

		digestRespond(respond);
	}

	private void sendGetMsgAfterDate(String msg) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	private void sendGetMsgAfterDate(String msg) 
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
		//							Feb 1, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		Message newMsg = new Message();
		newMsg.setType(MessageType.GET_MSG_AFTER_DATE);
		newMsg.setSender(mSenderName);
		newMsg.setMessage(msg);

		if (mMessages.size() > 0) {
			newMsg.setMessageDate(mMessages.get(mMessages.size() - 1).getMessageDate());
		} else {
			newMsg.setMessageDate(new Date(0));
		}
		JSONObject request = newMsg.toJSON();

		String respond = mMsnService.sendRequest(request.toString());

		digestRespond(respond);
	}

	public void sendClose() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	public void sendClose() 
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
		//							Jan 31, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	}

	private void digestRespond(String respond) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	private void digestRespond(String respond) 
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
		//							Feb 1, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		Message respondMsg = new Message();
		try {
			respondMsg.toMessage(new JSONObject(respond));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		switch (respondMsg.getType()) {
		case EMPTY:
			doEmpty(respondMsg);
			break;
		case NULL:
			doNull(respondMsg);
			break;
		case CLOSE:
			doClose();
			break;
		case GET_ALL_MSGS:
			doAllMsgs(respondMsg);
			break;
		case GET_MSG_AFTER_DATE:
			doGetMsgsAfterDate(respondMsg);
			break;
		default:
			doRespond(respond);
			break;
		}
	}

	private void doAllMsgs(Message respondMsg) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	private void doAllMsgs(Message respondMsg) 
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
		//							Feb 4, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		doRespond(respondMsg.toString());
	}

	private void doGetMsgsAfterDate(Message respondMsg) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	private void doGetMsgsAfterDate(Message respondMsg)
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
		//							Feb 4, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		doRespond(respondMsg.toString());
	}

	private void doEmpty(Message respondMsg) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	private void doEmpty(Message respondMsg)
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
		//							Feb 1, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		System.out.println("Empty List");
	}

	private void doNull(Message respondMsg) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	private void doNull(Message respondMsg)
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
		//							Feb 1, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	}

	private void doClose() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	private void doClose()
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
		//							Feb 1, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	}

	private void doRespond(String respond) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	private void doRespond(String respond)
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
		//							Feb 1, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		System.out.println("MsnLogic.doResponse()");
		JSONObject jsonRsponse = new JSONObject(respond);
		Message msgRsponse = new Message();
		msgRsponse.toMessage(jsonRsponse);
		System.out.println(msgRsponse.toPrettyString());
		JSONObject msgs = new JSONObject(msgRsponse.getMessage());
		JSONArray allJsonMsgs = new JSONArray(msgs.optString(MESSAGES, "[]"));
		ArrayList<Message> allNewMsgs = new ArrayList<>();
		for (Object jsonMsg : allJsonMsgs) {
			Message newMsg = new Message();
			newMsg.toMessage((JSONObject) jsonMsg);
			allNewMsgs.add(newMsg);
		}
		Collections.sort(allNewMsgs);

		mMessages = new ArrayList<>(
				Stream.concat(mMessages.stream(), allNewMsgs.stream()).distinct().collect(Collectors.toSet()));

		Collections.sort(mMessages);

		int index = 0;
		for (Message msg : mMessages) {
			System.out.println("msg#" + index);
			System.out.println(msg.toPrettyString());
			index++;
		}

	}

	public void setSender(String senderName) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void setSender()
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
		//							Feb 1, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		this.mSenderName = senderName;
	}

	public ArrayList<Message> getMessages() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	ListModel<Message> getMessages()
		//
		// Method parameters	:	args - the method permits String command line parameters to be entered
		//
		// Method return		:	ListModel<Message>
		//
		// Synopsis				:   
		//							
		//
		// Modifications		:
		//							Date			    Developer				Notes
		//							  ----			      ---------			 	     -----
		//							Feb 1, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		return mMessages;
	}

	public void setAnnouncer(Announcement msnGUI) {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	void setAnnouncer()
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
		//							Feb 4, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		this.announcer = msnGUI;
	}

	// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	// Method				:	void setServerAddress()
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
	//							Feb 6, 2019		    Mohammed Al-Safwan				Initial setup
	//
	// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

	public void setServerAddress(String ipAddress) {
		mMsnService.setServerName(ipAddress);
	}

	public boolean isRunning() {
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		// Method				:	boolean isRunning()
		//
		// Method parameters	:	args - the method permits String command line parameters to be entered
		//
		// Method return		:	boolean
		//
		// Synopsis				:   
		//							
		//
		// Modifications		:
		//							Date			    Developer				Notes
		//							  ----			      ---------			 	     -----
		//							Feb 6, 2019		    Mohammed Al-Safwan				Initial setup
		//
		// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
		return mIsRunning;
	}
}
