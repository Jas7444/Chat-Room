import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingUtilities;
import net.jini.space.JavaSpace;
import javax.swing.JScrollPane;
import java.awt.SystemColor;
 
 
public class OneHubGUI extends javax.swing.JFrame {
 
	//GUI Interface
    private JPanel OneHubGUIPanel;
    private JTextField currentTopicLable, topicListLable, userListLable, messageJTF;
    private JButton connectJB, sendMessageJB, deleteTopicJB, createTopicJB, joinTopicJB;
    private JTextArea chatArea;
    private JList userJList, topicJList;
    
    //Manager(Manages different classes).
    private Manager manage;
    private JScrollPane scrollPane;
    private JScrollPane scrollPane_1;
    private JScrollPane scrollPane_2;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                OneHubGUI inst = new OneHubGUI();
                inst.setLocationRelativeTo(null);
                inst.setVisible(true);
                 
                
                //Find the space.
                JavaSpace space = SpaceUtils.getSpace();
                if (space == null){
                    System.err.println("Failed to find the javaspace");
                    System.exit(1);
                }
            }
        });
    }
     
    public OneHubGUI() {
        super();
        hubChatGUI();
         
        //Manage to update chat areas and lists.
        manage = new Manager(this.chatArea, this.topicJList, this.userJList, this.currentTopicLable);
        


    }
     
    
    //Start up the HubChat Interface.
    private void hubChatGUI() {
        try {
        	
        	
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //OneHubGUIPanel.setBackground(Color.BLUE);
            setBounds(100, 100, 700, 400);
            OneHubGUIPanel = new JPanel();
            OneHubGUIPanel.setBackground(SystemColor.menu);
            OneHubGUIPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            setContentPane(OneHubGUIPanel);
            OneHubGUIPanel.setLayout(null);
            
            sendMessageJB = new JButton("SendMessage");
            sendMessageJB.setBackground(Color.WHITE);
            sendMessageJB.setEnabled(false);
            sendMessageJB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    manage.sendMessage(messageJTF.getText(), UserMessage.NORMALTXTMSG);
                    messageJTF.setText("");
                }
            });
            sendMessageJB.setBounds(286, 328, 131, 23);
            OneHubGUIPanel.add(sendMessageJB);
             
            connectJB = new JButton("Connect");     
            connectJB.setBackground(Color.WHITE);
            connectJB.setBounds(13, 25, 189, 51);
            OneHubGUIPanel.add(connectJB);
            connectJB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                     
                    LoginFrame userDialog = new LoginFrame(OneHubGUI.this);
                    userDialog.setModal(true);
                    userDialog.setVisible(true);
                                                 
                    manage.addNewUser(userDialog.getUsername(), userDialog.getPassword());
                    userDialog.dispose();
                    if (manage.connected)
                    {
                        topicJList.setModel(manage.getTopicListModel());
                         
                        joinTopicJB.setEnabled(true);
                        createTopicJB.setEnabled(true);
                        deleteTopicJB.setEnabled(true);
                        currentTopicLable.setEnabled(true);
                        chatArea.setEnabled(true);
                        messageJTF.setEnabled(true);
                        userListLable.setEnabled(true);
                        userJList.setEnabled(true);
                        topicListLable.setEnabled(true);
                        topicJList.setEnabled(true);
                        sendMessageJB.setEnabled(true);
                        connectJB.setVisible(false);
                         
                         
                    }
                }
            });
             
            joinTopicJB = new JButton("Join Topic");
            joinTopicJB.setBackground(Color.WHITE);
            joinTopicJB.setEnabled(false);
            joinTopicJB.setBounds(13, 102, 149, 40);
            OneHubGUIPanel.add(joinTopicJB);
            joinTopicJB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                        manage.topicConnect((String) topicJList.getSelectedValue());
                        manage.updateUserList();
                }
            });
             
            createTopicJB = new JButton("Create Topic");
            createTopicJB.setBackground(Color.WHITE);
            createTopicJB.setEnabled(false);
            createTopicJB.setBounds(212, 102, 149, 40);
            OneHubGUIPanel.add(createTopicJB);
            createTopicJB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                     
                    CreateTopicJF topicFrame = new CreateTopicJF(OneHubGUI.this);
                    topicFrame.setModal(true);
                    topicFrame.setVisible(true);
                     
                    manage.addNewTopics(topicFrame.getTopic(), topicFrame.getDescription());
                    topicFrame.dispose();
                }
            });
             
             
            deleteTopicJB = new JButton("Delete Topic");
            deleteTopicJB.setBackground(Color.WHITE);
            deleteTopicJB.setEnabled(false);
            deleteTopicJB.setBounds(500, 315, 112, 23);
            OneHubGUIPanel.add(deleteTopicJB);
            deleteTopicJB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {

                        manage.deleteTopic((String) topicJList.getSelectedValue());

                }
            });
             
            currentTopicLable = new JTextField();
            currentTopicLable.setBackground(Color.WHITE);
            currentTopicLable.setEnabled(false);
            currentTopicLable.setEditable(false);
            currentTopicLable.setBounds(13, 153, 348, 20);
            OneHubGUIPanel.add(currentTopicLable);
            currentTopicLable.setColumns(10);
            
            scrollPane = new JScrollPane();
            scrollPane.setBounds(13, 178, 349, 139);
            OneHubGUIPanel.add(scrollPane);
             
            chatArea = new JTextArea();
            chatArea.setBackground(Color.WHITE);
            scrollPane.setViewportView(chatArea);
            chatArea.setEditable(false);
            chatArea.setEnabled(false);
             
            messageJTF = new JTextField();
            messageJTF.setEnabled(false);
            messageJTF.setBounds(8, 328, 263, 20);
            OneHubGUIPanel.add(messageJTF);
            messageJTF.setColumns(10);
             
            userListLable = new JTextField();
            userListLable.setEnabled(false);
            userListLable.setEditable(false);
            userListLable.setText("User List");
            userListLable.setBounds(508, 11, 74, 20);
            OneHubGUIPanel.add(userListLable);
            userListLable.setColumns(10);
             
            userJList = new JList();
            userJList.setEnabled(false);
            userJList.setBounds(422, 42, 252, 111);
            OneHubGUIPanel.add(userJList);
             
            topicListLable = new JTextField();
            topicListLable.setEnabled(false);
            topicListLable.setEditable(false);
            topicListLable.setText("Topic List");
            topicListLable.setBounds(508, 162, 86, 20);
            OneHubGUIPanel.add(topicListLable);
            topicListLable.setColumns(10);
             
            topicJList = new JList();
            topicJList.setEnabled(false);
            topicJList.setBounds(422, 193, 252, 111);
            OneHubGUIPanel.add(topicJList);
             
        }catch (Exception e) {
        }
    }
}