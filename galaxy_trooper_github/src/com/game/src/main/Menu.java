package com.game.src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu {

	public Rectangle playButton = new Rectangle(Game.WIDTH/2 - 10 +120, 150, 100,50);
	public Rectangle helpButton = new Rectangle(Game.WIDTH/2 - 10 +120, 250, 100,50);
	public Rectangle quitButton = new Rectangle(Game.WIDTH/2 - 10 +120, 350, 100,50);
	
	public void render(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		Font fnt0 = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.white);
		g.drawString("GALAXY TROOPER", 80 , 100);
		Font fnt1 = new Font("arial", Font.BOLD, 30);
		g.setFont(fnt1);
		
		//Play
		g.drawString("Play", playButton.x +19, playButton.y +35);
		g2d.draw(playButton);
		Game.SCORE = 0;
		
		//Help
		g.drawString("Help", helpButton.x +19, helpButton.y +35);
		g2d.draw(helpButton);
		
		//Quit
		g.drawString("Quit", quitButton.x +19, quitButton.y +35);
		g2d.draw(quitButton);
		
	}
}
