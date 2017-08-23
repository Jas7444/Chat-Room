import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.SystemColor;
import java.awt.Color;

public class CreateTopicJF extends javax.swing.JDialog {
	
	
//Topic Creation GUI
private JButton connectToChatJB;
private JLabel topicNameJL;
private JTextField topicNameJTF;
private JLabel topicDescriptionLabel;
private JTextArea topicDescriptionArea;


public CreateTopicJF(JFrame frame) {
	super(frame);
	getContentPane().setBackground(Color.LIGHT_GRAY);
	topicGUI();
}

public String getTopic()
{
	return topicNameJTF.getText();
}

public String getDescription()
{
	return topicDescriptionArea.getText();
}

private void topicGUI() {
	try {
		getContentPane().setLayout(null);
		{
			topicNameJL = new JLabel();
			getContentPane().add(topicNameJL);
			topicNameJL.setText("Topic Name:");
			topicNameJL.setBounds(12, 27, 157, 15);
		}
		{
			topicNameJTF = new JTextField();
			getContentPane().add(topicNameJTF);
			topicNameJTF.setBounds(12, 54, 250, 27);
		}
		{
			topicDescriptionLabel = new JLabel();
			getContentPane().add(topicDescriptionLabel);
			topicDescriptionLabel.setText("Topic Description:");
			topicDescriptionLabel.setBounds(12, 93, 208, 15);
		}
		{
			topicDescriptionArea = new JTextArea();
			getContentPane().add(topicDescriptionArea);
			topicDescriptionArea.setBounds(6, 132, 413, 83);
			topicDescriptionArea.setLineWrap(true);
		}
		{
			connectToChatJB = new JButton();
			connectToChatJB.setBackground(Color.WHITE);
			getContentPane().add(connectToChatJB);
			connectToChatJB.setText("OK");
				connectToChatJB.setBounds(176, 250, 93, 27);
				connectToChatJB.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						setVisible(false);
					}
			});
			}
			this.setSize(455, 350);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
