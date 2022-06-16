package com.game.src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Endgame {

	public Rectangle menuButton = new Rectangle(Game.WIDTH/2 - 10 +120, 300, 100,50);
	
	public void render(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		Font fnt0 = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.white);
		g.drawString("You Died", Game.WIDTH/2 + 50 , 210);
		
		Font fnt1 = new Font("arial", Font.BOLD, 30);
		g.setFont(fnt1);
		g.setColor(Color.white);
		g.drawString("Your score is :" + Game.SCORE, 204, 260);
		
		g.drawString("Menu", menuButton.x +13, menuButton.y +35);
		g2d.draw(menuButton);

	}
}
