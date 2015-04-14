import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Label;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JTextPane;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class Frame extends JFrame implements KeyListener,ActionListener{
	
	private enum Actions {
	    ABOUT,
	    HIGHSCORE
	  }
	
	int mPozSlova;
	boolean indicator;
	Timer timer;
	TimerTask timerTask;
	Sound sound = new Sound();
	Scores scores = new Scores();
	
	ArrayList <String> rijeci4 = new ArrayList<String>();
	ArrayList <String> rijeci5 = new ArrayList<String>();
	ArrayList <String> rijeci6 = new ArrayList<String>();
	ArrayList <String> rijeci7 = new ArrayList<String>();
	ArrayList <String> rijeci8 = new ArrayList<String>();
	ArrayList <String> rijeci9 = new ArrayList<String>();
	ArrayList <String> rijeci10 = new ArrayList<String>();
	
	
	public JLabel lblTextZaPisanje;
	public JLabel lblBodovi;
	public JLabel lblTimer;
	
	public int bodovi;
	public String rijec;
	
	private JPanel contentPane;
	private JButton btnScores;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.upis_lista();
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame() {
		
		lblBodovi = new JLabel("0000");
		lblBodovi.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblBodovi.setHorizontalAlignment(SwingConstants.CENTER);
		bodovi = 0;
		indicator = false;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		lblTextZaPisanje = new JLabel("Press ENTER to Play!");
		lblTextZaPisanje.setHorizontalAlignment(SwingConstants.CENTER);
		lblTextZaPisanje.setFont(new Font("Traditional Arabic", Font.PLAIN, 32));
		
		lblTimer = new JLabel("4");
		lblTimer.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblTimer.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btnAbout = new JButton("Instructions");
		btnAbout.setActionCommand(Actions.ABOUT.name());
	    btnAbout.addActionListener(this);
	    btnAbout.setFocusable(false);
		
		btnScores = new JButton("Scores");
		btnScores.setActionCommand(Actions.HIGHSCORE.name());
	    btnScores.addActionListener(this);
	    btnScores.setFocusable(false);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTextZaPisanje, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
						.addComponent(lblTimer, Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblBodovi, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
							.addComponent(btnScores)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAbout)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTimer, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblTextZaPisanje, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBodovi, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAbout)
						.addComponent(btnScores))
					.addContainerGap(15, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);	
		addKeyListener(this);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
	}
	
	

	public void promjeni_text(String rijec){
		
		lblTextZaPisanje.setText(rijec.toUpperCase());
		//System.out.println("Text bi trebao biti izmjenjen!");
		
	}
	
	public void izmjeni_bodove(){
		bodovi+=10;
		lblBodovi.setText(Integer.toString(bodovi));
		//System.out.println("Bodovi su izmjenjeni!");
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println("Pritisnuta je tipka: "+e.getKeyChar());
		

		if(indicator){
			//System.out.println("index:"+mPozSlova);
			if(e.getKeyChar() == rijec.toLowerCase().charAt(mPozSlova)){
				++mPozSlova;
//				lblTextZaPisanje.setText(rijec.substring(mPozSlova).toUpperCase());
				promjeni_text(rijec.substring(mPozSlova));
				sound.playSound(sound.type);
				
			}
			else{
				game_over();
				return;
			}			
				
			if(mPozSlova == rijec.length()){
				timer.cancel();
				izmjeni_bodove();
				
				if(bodovi%100 == 0)
					sound.playSound(sound.points_100);
				else
					sound.playSound(sound.ding);
				
				
				nova_rijec();
			}
			//System.out.println(mPozSlova);
			//System.out.println(rijec.length());
			
		}else if(!indicator && e.getKeyCode() == 10){
			reset_game();			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
	
	private void upis_lista(){
		Scanner s4,s5,s6,s7,s8,s9,s10;
		
		try {
			s4 = new Scanner(new File("Rijeci/cetiri.txt"));
			s5 = new Scanner(new File("Rijeci/pet.txt"));
			s6 = new Scanner(new File("Rijeci/sest.txt"));
			s7 = new Scanner(new File("Rijeci/sedam.txt"));
			s8 = new Scanner(new File("Rijeci/osam.txt"));
			s9 = new Scanner(new File("Rijeci/devet.txt"));
			s10 = new Scanner(new File("Rijeci/deset.txt"));
			while (s4.hasNext()){
			    rijeci4.add(s4.next());
			}
			while (s5.hasNext()){
			    rijeci5.add(s5.next());
			}
			while (s6.hasNext()){
			    rijeci6.add(s6.next());
			}
			while (s7.hasNext()){
			    rijeci7.add(s7.next());
			}
			while (s8.hasNext()){
			    rijeci8.add(s8.next());
			}
			while (s9.hasNext()){
			    rijeci9.add(s9.next());
			}
			while (s10.hasNext()){
			    rijeci10.add(s10.next());
			}
			System.out.print("Rijeci su upisane!!!");
			s4.close();
			s5.close();
			s6.close();
			s7.close();
			s8.close();
			s9.close();
			s10.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	public void nova_rijec(){
		mPozSlova = 0;
		int i = 0;
		Random rand = new Random();
		
		if(bodovi <100){
		i = rand.nextInt(rijeci4.size() - 1) + 1;
		rijec = rijeci4.get(i);}
		
		else if(bodovi >=100 && bodovi <200){
			i = rand.nextInt(rijeci5.size() - 1) + 1;
			rijec = rijeci5.get(i);}
		
		else if(bodovi >=200 && bodovi <300){
			i = rand.nextInt(rijeci6.size() - 1) + 1;
			rijec = rijeci6.get(i);}
		
		else if(bodovi >=300 && bodovi <400){
			i = rand.nextInt(rijeci7.size() - 1) + 1;
			rijec = rijeci7.get(i);}
		
		else if(bodovi >=400 && bodovi <500){
			i = rand.nextInt(rijeci8.size() - 1) + 1;
			rijec = rijeci8.get(i);}
		
		else if(bodovi >=500 && bodovi <600){
			i = rand.nextInt(rijeci9.size() - 1) + 1;
			rijec = rijeci9.get(i);}
		
		else if(bodovi >=600 && bodovi <700){
				i = rand.nextInt(rijeci10.size() - 1) + 1;
				rijec = rijeci10.get(i);}
		else{}
		
		//System.out.println("nova rijec je= "+rijec);
		promjeni_text(rijec);
		timer = new Timer();
		timer_odbrojavanje();
	}
	
	public void game_over(){
		sound.playSound(sound.lifeLost);
		timer.cancel();
		indicator = false;
		lblTextZaPisanje.setText("<html><div style=\"text-align: center;\">GAME OVER!!!<br>Your Score is:"+bodovi+"<br>Press ENTER to Play Again!</html>");
		lblBodovi.setVisible(false);
		
		if(bodovi > scores.getLowestScore()){
			String name = JOptionPane.showInputDialog("Please enter your name");
			if(name != null){
				name = name.replaceAll("\\s","");
				scores.score_new(name,bodovi);}
			}
	}
	
	public void reset_game(){

		bodovi = 0;
		lblBodovi.setText("0");
		lblBodovi.setVisible(true);
		indicator = true;
		nova_rijec();
	}
	
	public void timer_odbrojavanje(){
		//timer.schedule(timerTask, 1000);
		
		timer.scheduleAtFixedRate(new TimerTask(){
			int i = 4;
            public void run() {
                //System.out.println(i);
                lblTimer.setText(Integer.toString(i));
                --i;
                if (i< 0){
                	timer.cancel();
                	System.out.println("Timer se prekida!");
                    game_over();
                }
            }
		}, 0, 800);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == Actions.ABOUT.name()) {
			JOptionPane.showMessageDialog(null, "<html><div style=\"text-align: center;\">Type words that appear on the screen!<br>For each word you have 4 seconds!<br>You start at 4 letters,then every 100 points another letter is added (MAX 10 letters)<hr><br>If you miss one letter or the time expires its GAME OVER!</html>" ,"Instructions",JOptionPane.INFORMATION_MESSAGE);
		    } else if (e.getActionCommand() == Actions.HIGHSCORE.name()) {
		    	JOptionPane.showMessageDialog(null, scores.ispis_liste() ,"About",JOptionPane.INFORMATION_MESSAGE);
		    }
		
	}
}
