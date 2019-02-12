package util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import logic.Message;

/**
 * @author Mohammed Al-Safwan
 * @date Feb 1, 2019
 */
public class MessageAdapter extends JPanel implements ListCellRenderer<Message> {

	private final JLabel mName = new JLabel("Person Name", SwingConstants.LEFT);
	private final JLabel mDate = new JLabel("00-00-0000 05:13pm", SwingConstants.LEFT);
	private final JLabel mMessage = new JLabel("The message that is coming from the server", SwingConstants.LEFT);
	private final JPanel mDetailPanel = new JPanel();
	private final JPanel mMessagePanel = new JPanel();

	public MessageAdapter() {
		init();
	}

	public MessageAdapter(Message msg) {
		init();
	}

	private void init() {
		setLayout(new GridLayout(2, 1));
		mDetailPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		//		mPanel.add(Box.createRigidArea(new Dimension(6, 0)));
		mDetailPanel.add(mName);
		mDetailPanel.add(Box.createRigidArea(new Dimension(12, 0)));
		mDetailPanel.add(mDate);

		mMessagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 6));
		mMessagePanel.add(mMessage);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends Message> list, Message msg, int index,
			boolean isSelected, boolean cellHasFocus) {
		mName.setText(msg.getSender());
		SimpleDateFormat formator = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		mDate.setText(formator.format(msg.getMessageDate()));
		mMessage.setText(msg.getMessage());
		add(mDetailPanel);
		add(mMessagePanel);
		//		init();
		return this;
	}

}
