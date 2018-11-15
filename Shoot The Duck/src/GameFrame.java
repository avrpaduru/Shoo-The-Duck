
import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame{

	JPanel panel;

	//JPanel instruction;
	JPanel startingPanel;//Title
	JPanel panel1;//ComboBox(User Names)
	JPanel panel2;//Game
	JPanel panel3;//Result
	JPanel panel4;//UserDetails
	CardLayout cardLO;

	GameFrame(){

		super("Shoot The Duck");

		cardLO = new CardLayout();
		panel  = new JPanel();
		panel.setLayout(cardLO);

		startingPanel = new Starting(cardLO,panel);
		//instruction = new InstructionPanel(cardLO,panel);//used to describe a story and instruction about the game
		panel1 = new UserNames(cardLO,panel);
		panel2 = new GamePanel(cardLO,panel);//panel is sent so that we can access the next panel of the card layout
		panel3 = new ResultPanel(cardLO,panel);
		panel4 = new UserDetails();

		panel.add(startingPanel,"StartingPanel");
		panel.add(panel1,"User Name");
		panel.add(panel2,"Game");
		panel.add(panel3,"Result");
		panel.add(panel4,"UserDetails");

		add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(800,800);
		setVisible(true);
	}

	public static void main(String[] args){

		SwingUtilities.invokeLater(new Runnable(){
			
			public void run(){
				
				new GameFrame();
			}
		});
	}
}