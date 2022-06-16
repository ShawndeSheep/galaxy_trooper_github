package com.game.src.main;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		 
	}


	public void mousePressed(MouseEvent e) {
		
		int mx = e.getX();
		int my = e.getY();
		
		/**
		 public Rectangle playButton = new Rectangle(Game.WIDTH/2 - 10 +120, 150, 100,50);
	     public Rectangle helpButton = new Rectangle(Game.WIDTH/2 - 10 +120, 250, 100,50);
	     public Rectangle quitButton = new Rectangle(Game.WIDTH/2 - 10 +120, 350, 100,50);
	     public Rectangle menuButton = new Rectangle(Game.WIDTH/2 - 10 +120, 300, 100,50);
	     public Rectangle menuButton = new Rectangle(20,300, 100,50);
		 */
		
		/////////////////////////////////////////// MENU /////////////////////////////////////////
		//Play 
		if(mx >=  Game.WIDTH/2 - 10 + 120 && mx <= Game.WIDTH/2 - 10 + 120 + 100 && Game.State == Game.STATE.MENU) {
			if (my >= 150 && my <= 150+50) {
				Game.State = Game.STATE.GAME;
			}
		} 
		
		//Help
		if(mx >=  Game.WIDTH/2 - 10 + 120 && mx <= Game.WIDTH/2 - 10 + 120 + 100 && Game.State == Game.STATE.MENU) {
			if (my >= 250 && my <= 250+50) {
				Game.State = Game.State.HELP;
			}
		}
		
		//Quit 
		if(mx >=  Game.WIDTH/2 - 10 + 120 && mx <= Game.WIDTH/2 - 10 + 120 + 100 && Game.State == Game.STATE.MENU) {
			if (my >= 350 && my <= 350+50) {
				System.exit(1);
			}
		}
		
		//Menu
		if(mx >=  Game.WIDTH/2 - 10 + 120 && mx <= Game.WIDTH/2 - 10 + 120 + 100 && Game.State == Game.STATE.ENDGAME) {
			if (my >= 300 && my <= 350) {
				Game.State = Game.STATE.MENU;
			}
		} 	
		//Menu 2
		if(mx >=  20 && mx <= 120 && Game.State == Game.STATE.HELP) {
			if (my >= 300 && my <= 350) {
				Game.State = Game.STATE.MENU;
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
