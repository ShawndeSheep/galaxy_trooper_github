package com.game.src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Help {
	
	public Rectangle menuButton = new Rectangle(20,300, 100,50);
	
	public void render(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		Font fnt0 = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.white);
		g.drawString("Tutorial", 20, 60);
		
		Font fnt1 = new Font("arial", Font.PLAIN, 15);
		g.setFont(fnt1);
		g.setColor(Color.white);
		g.drawString("Welcome Fighters, Your mission is simple kill the enemies and survive as long as you can.", 20, 150);
		g.drawString("You have 4 lives indicated by the healthbar at top left. Each kill will be considered as a point", 20, 170);
		g.drawString("which you can see at top right. Remember, each enemy waves will be harder than before.", 20, 190);
		
		Font fnt2 = new Font("arial", Font.BOLD, 15);
		g.setFont(fnt2);
		g.drawString("So hang tight and be a True Galaxy Trooper !!!", 20, 220); 
		
		Font fnt3 = new Font("arial", Font.BOLD, 25);
		g.setFont(fnt3);
		g.drawString("Menu", menuButton.x +18, menuButton.y +35);
		g2d.draw(menuButton);
	}
	
}
