
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ResultPanel extends JPanel{

	JPanel panel1;
	JPanel panel2;
	static JLabel wishing;
	static JLabel scores;
	//static JLabel countLabel;
	JButton next;
	static JLabel description;
	static JLabel badgeLabel;
	static ImageIcon badge;

	static String name;
	CardLayout			cardLO;//to select the next panel when time is completed
	JPanel				panel;

	ResultPanel(CardLayout cardLO,JPanel panel){

		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		badge = new ImageIcon("./images/bronze.jpg");

		this.cardLO = cardLO;
		this.panel  = panel;

		name = new String("Nothing");
		wishing = new JLabel("Congrats "+name);
		scores = new JLabel();
		description = new JLabel("Your badge");

		//countLabel = new JLabel("Number of Ducks shot are: ");

		badgeLabel = new JLabel(badge);

		next = new JButton("scores");
		next.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				cardLO.next(panel);
			}
		});

		panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

		panel1.add(wishing);
		panel1.add(scores);

		panel2.add(description);
		panel2.add(badgeLabel);

		JPanel helperPanel1, helperPanel2, helperPanel3;//used to align panel1, panel2 and countLabel at cetner of this.panel
		helperPanel1  = new JPanel();
		helperPanel2  = new JPanel();
		helperPanel3  = new JPanel();

		helperPanel1.add(panel1);
		helperPanel2.add(panel2);
		//helperPanel3.add(countLabel);
		helperPanel3.add(next);

		add(helperPanel1);
		add(Box.createVerticalGlue());
		add(helperPanel2);
		add(Box.createVerticalGlue());
		add(helperPanel3);
		add(Box.createVerticalGlue());
	}

	static void setScores(String level, int prevScore, int currScore){
		scores.setText("Your score in "+level+" level: previous: "+prevScore+" current: "+currScore);
	}

	static void setPlayerName(String txt){
		name = txt;
		wishing.setText("Congrats: "+name);
	}

	static void setBadge(ImageIcon img, String bName){
		description.setText("Your badge: "+bName);
		badge = img;
		badgeLabel.setIcon(badge);
	}

	/*static void setCount(int count)
	{
		countLabel.setText("Number of Ducks shot are: "+count);
	}*/
}