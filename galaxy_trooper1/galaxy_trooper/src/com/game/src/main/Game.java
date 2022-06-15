package com.game.src.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;

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
	private BufferedImage player;
	public void init(){
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			spriteSheet = loader.loadImage("Sprite.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		SpriteSheet ss = new SpriteSheet(spriteSheet);
		player = ss.grabImage(1, 1, 32,32);
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
		
		g.drawImage(player, 100, 100, this);
		//// Di dispose suapaya next loop ga null
		g.dispose();
		bs.show();
	}
	
	public static void main(String args[]){
		Game game = new Game();
		
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

}