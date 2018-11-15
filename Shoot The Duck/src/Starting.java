
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Starting extends JPanel{

	JPanel panel1;
	JLabel label;
	ImageIcon img;
	StringBuffer choice;
	CardLayout cardLO;
	JPanel panel;
	JButton skip;
	Clip clip;
	boolean next = false;

	Starting(CardLayout cardLO,JPanel panel){

		this.cardLO = cardLO;
		this.panel = panel;

		try{
		  File soundFile = new File("./audio/starttone.au");//you could also get the sound file with an URL
          AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);              
         // Get a sound clip resource.
         clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioIn);
         clip.start();
		}catch(Exception e){};

		choice = new StringBuffer("./images/");
		img = new ImageIcon(choice+"image-1.jpg");
		panel1 = new JPanel();
		label = new JLabel(img);
		skip = new JButton("skip");
		skip.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				goNext();
			}
		});

		panel1.add(label);
		//panel1.add(skip);
		panel1.setSize(400,600);
		JPanel panel2 = new JPanel();
		panel2.add(skip);
		this.setLayout(new BorderLayout());
		this.add(panel1,BorderLayout.CENTER);
		this.add(panel2,BorderLayout.SOUTH);

		Thread t = new Thread(new Imaging());
		t.start();
	}

	void goNext(){
		if(!next){
			next = true;
			clip.stop();
			cardLO.next(panel);
		}
	}

	void setImage(Object num){
		String name = null;
		if(num instanceof Integer)
			name = choice+"image-"+String.valueOf((Integer)num)+".jpg";
		else if(num instanceof Character)
			name = choice+"image-"+String.valueOf((Character)num)+".jpg";

		img = new ImageIcon(name);
		label.setIcon(img);
	}

	class Imaging implements Runnable{

		public void run(){

		try{

			//Image with Shoot the Duck text is captured in different angles to make a rotating animation
			for(int index=1;index<8;++index){//displays all the logo images for each 0.5 seconds to make a rotating animation
				Thread.sleep(500);
				setImage(index);
			}

			//after rotating animation, display all the animals that are used in the game
			int ch=1;
			for(int index=1;index<=3;++index){
				Thread.sleep(500);
				setImage(1);
				Thread.sleep(500);
			
				setImage(null);
				Thread.sleep(500);

				switch(ch){

					 case 1:setImage('t');
					   		Thread.sleep(500);
							ch =2;
							break;

					 case 2:setImage('c');
							Thread.sleep(500);
							ch =3;
							break;

					 case 3:setImage('d');
							Thread.sleep(500);
							ch =1;
							break;

					default: break;
			}

				Thread.sleep(500);
				setImage(9);
				Thread.sleep(500);
			}

			//blinking the logo image for the final animation
			setImage(1);
			Thread.sleep(500);
			setImage(null);
			Thread.sleep(500);

			setImage(1);
			Thread.sleep(1000);
			setImage(null);
			Thread.sleep(500);

			setImage(1);
			Thread.sleep(3000);
			goNext();
		}catch(InterruptedException e){
			//e.printStackTrace();//remove comment for tracing errors while testing
		};
		}
	}
}