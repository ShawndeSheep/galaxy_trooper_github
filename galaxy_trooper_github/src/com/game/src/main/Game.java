package com.game.src.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.sql.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;

public class Game extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 320;
	public static final int HEIGHT = WIDTH/12*9 ; //Preffered Ratio
	public static final int SCALE = 2;
	public final String TITLE = "2D Game Space";
	private boolean running = false;
	private Thread thread;
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB); // Delay
	private BufferedImage spriteSheet = null;
	private BufferedImage background = null;
	
	private boolean is_shooting = false;
	
	private int enemy_count=5;
	private int enemy_killed=0;
	
	private Player p;
	private Controller c;
	private Menu menu;
	private Endgame endgame;
	private Help help;
	
	public int getEnemy_count() {
		return enemy_count;
	}

	public void setEnemy_count(int enemy_count) {
		this.enemy_count = enemy_count;
	}

	public int getEnemy_killed() {
		return enemy_killed;
	}

	public void setEnemy_killed(int enemy_killed) {
		this.enemy_killed = enemy_killed;
	}

	private Textures tex;
	
	public LinkedList<EntityA> ea;
	public LinkedList<EntityB> eb;
	public static int SCORE = 0;
	
	public static int HEALTH = 100 * 2; 
	
	public static enum STATE{
		MENU,
		GAME,
		ENDGAME,
		HELP
	};
	
	public static STATE State = STATE.MENU;
	
	
	public void init(){
		requestFocus(); //Auto Focus ke game screen
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			spriteSheet = loader.loadImage("res/Sprite.png");
			background = loader.loadImage("res/background.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		addKeyListener(new KeyInput(this));
		this.addMouseListener(new MouseInput());
		
		tex = new Textures(this);

		c = new Controller(tex, this);
		p = new Player(200, 200, tex, this, c);
		menu = new Menu();
		endgame = new Endgame();
		help = new Help();
		
		ea = c.getEntityA();
		eb = c.getEntityB();
		
		c.createEnemy(enemy_count);
	}
	
	//Untuk mulai gamenya
	private synchronized void start(){
		if(running)
			return;
		running=true;
		thread = new Thread(this);
		thread.start();
	}
	
	//Untuk memberhentikan gamenya
	private synchronized void stop() {
		if(!running)return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	public void run() {
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0; //60 FPS
		double ns = 1000000000/amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		while(running){
			//Game Loop
			long now = System.nanoTime();
			delta += (now-lastTime)/ns;
			lastTime=now;
			if(delta>=1) {
				tick();
				updates ++;
				delta--;
			}
			render();
			frames++;
			if(System.currentTimeMillis()-timer > 1000) {
				timer +=1000;
				System.out.println(updates + "Ticks,FPS " + frames);
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() {
		if(State == STATE.GAME) {
		p.tick();
		c.tick();
		}

		
		if(enemy_killed >= enemy_count) {
			enemy_count += 2;
			enemy_killed = 0;
			c.createEnemy(enemy_count);
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy(); 
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		////////////////////////////////
		g.drawImage(image,0,0,getWidth(),getHeight(),this);
		g.drawImage(background, 0, 0, null);
		
		if(State == STATE.GAME) {		
		p.render(g);
		c.render(g);
		
		g.setColor(Color.GRAY);
		g.fillRect(5, 5, 200, 50);
		
		g.setColor(Color.green);
		g.fillRect(5, 5, HEALTH, 50);
		
		g.setColor(Color.white);
		g.drawRect(5, 5, 200, 50);
		
		Font fnt0 = new Font("arial", Font.BOLD, 30);
		g.setFont(fnt0);
		g.setColor(Color.white);
		g.drawString("Score: " + SCORE , 500 , 30);
		
		if(HEALTH <= 0) {
		State = STATE.ENDGAME;
		
		
		try {
			//Open connection
			Class.forName("com.mysql.jbdc.Driver");
			Connection con = DriverManager.getConnection("jbdc:mysql://localhost::3306/pbo_tugas", "root", "");
			
			//Insert Data
			Statement stm = con.createStatement();
			String sql = "INSERT INTO leaderboard VALUES(SCORE)";
			
			//Execute
			stm.executeUpdate(sql);
			
			//Message
			System.out.println("Score Recorded");
			
			//Close connection
			con.close();
			
		}catch(Exception e) {}
		
		HEALTH = 200;
		}
		
		} else if (State == STATE.MENU){
			menu.render(g);
		} else if (State == STATE.ENDGAME) {
			endgame.render(g);
		} else if (State == STATE.HELP) {
			help.render(g);
		}
		

		//// Di dispose suapaya next loop ga null
		g.dispose();
		bs.show();
	}
	
	public void keyPressed(KeyEvent e) throws LineUnavailableException, UnsupportedAudioFileException, IOException{
		int key = e.getKeyCode();
		
		if(State == STATE.GAME) {
		if(key == KeyEvent.VK_RIGHT){
			p.setVelX(5);
		}else if(key == KeyEvent.VK_LEFT){
			p.setVelX(-5);
		}else if(key == KeyEvent.VK_DOWN){
			p.setVelY(5);
		}else if(key == KeyEvent.VK_UP){
			p.setVelY(-5);
		}else if(key == KeyEvent.VK_SPACE && !is_shooting){
			is_shooting = true;
			c.addEntity(new Bullet(p.getX(), p.getY(), tex, this));
			
			//Laser SFX
			File file = new File("res/Laser.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		}
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_RIGHT){
			p.setVelX(0);
		}else if(key == KeyEvent.VK_LEFT){
			p.setVelX(0);
		}else if(key == KeyEvent.VK_DOWN){
			p.setVelY(0);
		}else if(key == KeyEvent.VK_UP){
			p.setVelY(0);
		}else if(key == KeyEvent.VK_SPACE){
			is_shooting = false;
		}
	}
	
	public static void main(String args[]) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		Game game = new Game();
		
		//Music
		File file = new File("res/BGM.wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);
		clip.start();
		
		//Untuk Ukuran Game nya
		game.setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		game.setMaximumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		game.setMinimumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		
		//Import JFrame
		JFrame frame = new JFrame(game.TITLE);
		frame.add(game);
		frame.pack(); //Ukuranya bakal sedikit lebih fleksibel bedasarkan compnent di dalamnya
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Buat bisa di close
		frame.setResizable(false); //Buat g bisa di resize (ukuran fixed sesuai diatas)
		frame.setLocationRelativeTo(null); //G set location
		frame.setVisible(true); // Bisa terlihat? 
		game.start();
	}
	
	public BufferedImage getSpriteSheet(){
		return spriteSheet;
	}
	
}
