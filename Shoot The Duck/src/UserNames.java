
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class UserNames extends JPanel implements ActionListener{

	JPanel				panel2;
	JPanel				namePanel;
	JPanel				instructionPanel;
	JLabel				instruction;
	JComboBox			namesBox;
	JLabel				nameLabel;
	JTextField			textField;
	JButton				submitButton;
	JRadioButton		easy, medium, hard;
	String[]			users = {"Guest"};
	static HashMap<String,ArrayList<Integer>> players = new HashMap();
	boolean start = true;
	//DataOutputStream	dataOutputStream;
	static String				output;

	CardLayout			cardLO;//to select the next panel when next button is clicked
	JPanel				panel;

	int					index = 0;

	UserNames(CardLayout cardLO,JPanel panel){

		this.cardLO = cardLO;
		this.panel  = panel;
		getUserNames();

		nameLabel = new JLabel("Enter the User Name: ");
		textField = new JTextField(20);

		namesBox = new JComboBox(users);
		namesBox.setSelectedItem(users[0]);

		textField.setText(users[0]);

		submitButton = new JButton("Next");

		namePanel = new JPanel();
		namePanel.add(nameLabel);
		namePanel.add(textField);


		JLabel difficulty = new JLabel("Choose Difficulty: ");
		easy = new JRadioButton("easy");
		medium = new JRadioButton("medium");
		hard = new JRadioButton("hard");

		easy.setActionCommand("easy");
		easy.setSelected(true);
		easy.addActionListener(new radioAction());
		medium.setActionCommand("medium");
		medium.addActionListener(new radioAction());
		hard.setActionCommand("hard");
		hard.addActionListener(new radioAction());

		ButtonGroup group = new ButtonGroup();
		group.add(easy);
		group.add(medium);
		group.add(hard);

		JLabel diffInstrn = new JLabel("Select difficulty: ");

		JPanel diffPanel = new JPanel();
		diffPanel.add(diffInstrn);
		diffPanel.add(easy);
		diffPanel.add(medium);
		diffPanel.add(hard);

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new BoxLayout(inputPanel,BoxLayout.Y_AXIS));
		inputPanel.add(namePanel);
		inputPanel.add(diffPanel);
		inputPanel.add(submitButton);
		inputPanel.add(Box.createVerticalGlue());

		panel2 = new JPanel();
		panel2.setLayout(new GridLayout(3,0));

		instruction = new JLabel("Shoot only the Duck");
		JLabel target = new JLabel(new ImageIcon("./images/image-t.jpg"));
		instructionPanel = new JPanel();
		//instructionPanel.setLayout(new BoxLayout(instructionPanel,BoxLayout.Y_AXIS));

		instructionPanel.add(instruction);
		//instructionPanel.add(target);

		panel2.add(instructionPanel);
		panel2.add(target);
		panel2.add(inputPanel);

		Thread t = new Thread(new Instruction());
		t.start();

		setLayout(new BorderLayout());
		add(namesBox,BorderLayout.NORTH);
		add(panel2,BorderLayout.CENTER);

		namesBox.addActionListener(this);
		submitButton.addActionListener(this);
	}

	static HashMap<String,ArrayList<Integer>> getPlayers(){
		return players;
	}

	void getUserNames(){
		try{
			FileInputStream fileIn = new FileInputStream("users.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
	        if((players = (HashMap<String,ArrayList<Integer>>) in.readObject())!=null){
				users = new String[players.size()];
				Iterator hmIterator = players.entrySet().iterator(); 
				int index = 0;
				// Iterate through the hashmap 
				while (hmIterator.hasNext()) { 
					Map.Entry mapElement = (Map.Entry)hmIterator.next(); 
					users[index++] = (String)mapElement.getKey(); 
				}
			}
			in.close();
			fileIn.close();

				/*
				users[0] = "Avinash Paduri";
				users[1] = "Karthik Rachamalli";
				users[2] = "Guest";

				int i=0;
				for(String s: list)
					users[i+3] = list.get(i++);*/
			//br.close();
		}catch(IOException e){
			//e.printStackTrace();//remove comment for tracing errors while testing
		}
		catch(ClassNotFoundException e){
			//e.printStackTrace();//remove comment for tracing errors while testing
		}
	}

	void storeUserName(String name){

		boolean exists = false;
		for(String s: users)
			if(s.equals(name))
				exists = true;
		if(!exists || players.size()==0){
			ArrayList<Integer> alist = new ArrayList<Integer>();
			alist.add(0);//easy
			alist.add(0);//medium
			alist.add(0);//hard
			players.put(name,alist);
			try{
				FileOutputStream fileOut = new FileOutputStream("users.ser");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(players);
				out.close();
				fileOut.close();
			}catch(IOException e){
				//e.printStackTrace();//remove comment for tracing errors while testing
			}
		}
	}

	public void actionPerformed(ActionEvent ae){

		if(ae.getSource() == submitButton){

			start = false;

			output = textField.getText();
			if(output.length()<1){
				nameLabel.setForeground(Color.RED);
				nameLabel.setText("You have to enter a name: ");
			}
			else{
				ResultPanel.setPlayerName(output);
				GamePanel.startGame(output);
				storeUserName(output);
				cardLO.next(panel);
			}
		}

		else{
			
			JComboBox source;
			source =(JComboBox)ae.getSource();
			
			output =(String)source.getSelectedItem();
			textField.setText(output);
		}
	}

	class radioAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent ae){
			if(ae.getActionCommand().equals("easy"))
				GamePanel.setThreshold(1500);
			else if(ae.getActionCommand().equals("medium"))
				GamePanel.setThreshold(1000);
			else if(ae.getActionCommand().equals("hard"))
				GamePanel.setThreshold(750);
		}
	}

	class Instruction implements Runnable{

		public void run(){

			try{

				while(start){
					Thread.sleep(1000);

					instruction.setText("Shoot only the Duck");
					Thread.sleep(1500);

					instruction.setText("Below is the image of Duck");
					Thread.sleep(1500);

					instruction.setText("Do not shoot any other Animals");
					Thread.sleep(1500);

					instruction.setText("Shooting Other Animals Or Missing Duck 5 times will end the game");
					Thread.sleep(1500);
				}
				
			}catch(InterruptedException e){};
		}
	}
}