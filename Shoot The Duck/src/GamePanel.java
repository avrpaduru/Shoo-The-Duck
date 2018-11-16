
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.border.LineBorder;

public class GamePanel extends JPanel implements ActionListener{

	JPanel panel1;
	JPanel timePanel;
	JLabel label;
	static JLabel startLabel;
	JLabel timeLabel;
	JButton[] button= new JButton[36];
	ImageIcon img;
	static int threshold = 1500;//game difficulty, set to easy(1.5 seconds for each image) by default, medium: 1 second, hard: 0.75 seconds
	static String name;
	boolean newImage = false;
	HashMap<String,ArrayList<Integer>> players;
	ArrayList<Integer> scores;

	CardLayout			cardLO;//to select the next panel when time is completed
	JPanel				panel;

	StringBuffer choices;
	static int count = 0;
	int totalDucks = 0;
	int misses = 0;
	boolean checking = false;
	boolean result = false;
	char c='t';
	int time = 121;

	Thread t;
	Thread imageThread;
	static boolean flag = false;//set true only when the username is entered in the usernames panel as the game should only be started only when the user eneters his/her name

	GamePanel(CardLayout cardLO,JPanel panel){

		this.cardLO = cardLO;
		this.panel  = panel;

		players = UserNames.getPlayers();

		choices = new StringBuffer("./images/image-1");
		//as we are using relative path for image name, chaning letter at index 15 represents a different image if it exits in images folder
		choices.setCharAt(15,'t');//this is the target image(duck), d implies dog and c implies cat
		img = new ImageIcon(choices+".jpg");
		startLabel = new JLabel("Loading");
		timeLabel = new JLabel("loading");
		label = new JLabel(" loading");
		label.setText("Number Of Ducks dead:"+" "+count+"   Number of ducks missed"+misses);

		timePanel = new JPanel(new GridLayout(1,2));
		timePanel.add(timeLabel);
		timePanel.add(label);

		for(int index=0;index<36;++index){
			button[index]= new JButton();
			button[index].setActionCommand("-");
			button[index].addActionListener(this);
		}

		panel1 = new JPanel(new GridLayout(6,6));
		for(int index=0;index<36;++index){
			button[index].setBorder(new LineBorder(panel1.getBackground(), 4));
			button[index].setBackground(Color.WHITE);
			panel1.add(button[index]);
		}

		setLayout(new BorderLayout());
		add(startLabel,BorderLayout.NORTH);
		add(panel1,BorderLayout.CENTER);
		add(timePanel,BorderLayout.SOUTH);

		t = new Thread(new TimeRemainder());
		t.start();

		imageThread = new Thread(new ImageThread());
		imageThread.start();
	}

	static void startGame(String pName){
		flag = true;
		name = pName;
		startLabel.setText("Start: "+name);
	}

	static void setThreshold(int val){
		threshold = val;
	}

	public void actionPerformed(ActionEvent me){
		if(newImage){
			JButton button =(JButton)me.getSource();
			if(button.getActionCommand().equals("target")){
				newImage = false;
				++count;
				checking = false;
				label.setText("Number Of Ducks dead: "+count+"   Number of ducks missed: "+misses);
			}
			else if(button.getActionCommand().equals("cat") || button.getActionCommand().equals("dog")){
				newImage = false;
				nextPanel();
			}
		}
	}

	void storeUserDetails(){
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



	void nextPanel(){//called when the game is finished
	  if(!result && flag){
		  scores = players.get(name);
		  int prevScore = 0;
		  switch(threshold){
			  case 1500://getting easy level high score
			    prevScore = scores.get(0);
				if(count>prevScore)
					scores.add(0,count);
				ResultPanel.setScores("easy",prevScore,count);
				System.out.println("previos easy score: "+prevScore);
			  break;
			  case 1000://medium level high score
			    prevScore = scores.get(1);
				if(count>prevScore)
					scores.add(1,count);
				ResultPanel.setScores("medium",prevScore,count);
				System.out.println("previos medium score: "+prevScore);
			  break;
			  case 750://hard level high score
			    prevScore = scores.get(2);
				if(count>prevScore)
					scores.add(2,count);
				ResultPanel.setScores("hard",prevScore,count);
				System.out.println("previos hard score: "+prevScore);
			  break;
			  default: System.out.println("Difficulty(threshold) values are changed, please change the case value of the switch in nextPanel() method of GamePanel.java to corresponding values of threshold based on difficulties and recompile GamePanel.java");
			  break;
		  }

		  if(count>prevScore){
			  players.put(name,scores);
			  storeUserDetails();
		  }

		 System.out.println("time: "+time+" misses: "+misses);
		ImageIcon img = null;
		//ResultPanel.setCount(count);
		String bName = "";
		if(count <5){
			bName = "bronze";
			img = new ImageIcon("./images/bronze"+".jpg");
		}
		if(count >=5 && count< 10){
			bName = "sliver";
			img = new ImageIcon("./images/silver"+".jpg");
		}
		if(count >=10){
			bName = "gold";
			img = new ImageIcon("./images/gold"+".jpg");
		}

		ResultPanel.setBadge(img,bName);
		UserDetails.setPlayers(players);
		result = true;//avoids calling carLO.next() multiple times accidentally as we have more than 1 thread
		cardLO.next(panel);
	  }
	}

	void checkMisses(){
		misses = totalDucks-count;
		label.setText("Number Of Ducks dead: "+count+"   Number of ducks missed: "+misses);
		if(misses>=5)
			nextPanel();
	}

	class TimeRemainder implements Runnable{

		public void run(){
				try{
					do{
	
						if(flag){
							timeLabel.setText(" "+time);
							Thread.sleep(1000);
							--time;
						}
					}while(time>0);
				}catch(InterruptedException ie){};

				startLabel.setText("Time is up 0");
				nextPanel();
		}
	}

	class ImageThread implements Runnable{

		public void run(){
		
			int index =15;
			int ch=0;

			int buttonIndex = 0;
			int position = 0;
			Random r = new Random();

			try{

				while(true){

					if(!result){
					  if(time<=120){
							checkMisses();

							checking = true;//for 1st iteration it is not required as actual game starts from the 2nd iteration

							button[buttonIndex].setActionCommand("-");
							position = (r.nextInt()%36)+36;

							buttonIndex = (position%36);
							ch = (position%3)+1;

							switch(ch){

								case 1: c = 'c';//cat image
								button[buttonIndex].setActionCommand("cat");
										break;
					
								case 2: c = 'd';//dog image
								button[buttonIndex].setActionCommand("dog");
										break;
								case 3: c = 't';//target image(duck)
								button[buttonIndex].setActionCommand("target");
								totalDucks++;
										break;
							}//switch

							choices.setCharAt(index,c);
							img = new ImageIcon(choices+".jpg");

							button[buttonIndex].setIcon(img);
							newImage = true;

							Thread.sleep(threshold);//image will be changed for each 0.75 seconds, change it to change the difficulty of the game
							img = new ImageIcon(" ");
							button[buttonIndex].setIcon(img);
					      }
						}//if
						else
							break;
				}//while
			}catch(Exception ie){};
		}
	}
}
