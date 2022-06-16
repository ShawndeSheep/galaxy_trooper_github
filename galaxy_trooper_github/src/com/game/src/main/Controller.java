package com.game.src.main;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;

public class Controller {
	
	private LinkedList<EntityA> ea = new LinkedList<EntityA>();
	private LinkedList<EntityB> eb = new LinkedList<EntityB>();
	
	EntityA enta;
	EntityB entb;
	
	private Textures tex;
	private Game game;
	Random r = new Random();
	
	public Controller(Textures tex, Game game) {
		this.tex = tex;
		this.game = game;
	}
	
	public void createEnemy(int enemy_count){
		for(int i= 0; i < enemy_count; i++) {
			addEntity(new Enemy(r.nextInt(640), -10, tex, this, game));
		}
	}
	
	public void tick(){
		// Entity A
		for(int i=0;i<ea.size();i++) {
			enta = ea.get(i);
			
			enta.tick();
		}
		// Entity B
		for(int i=0;i<eb.size();i++) {
			entb = eb.get(i);
			
			entb.tick();
		}
	}
	
	public void render(Graphics g){
		//Entity A
		for(int i=0;i<ea.size();i++) {
			enta = ea.get(i);
			
			enta.render(g);
		}
		//Entity B
		for(int i=0;i<eb.size();i++) {
			entb = eb.get(i);
			
			entb.render(g);
		}
	}
	
	//Entity A
	public void addEntity(EntityA block) {
		ea.add(block);
	}
	
	public void removeEntity(EntityA block) {
		ea.remove(block);
	}
	
	//Entity B
	public void addEntity(EntityB block) {
		eb.add(block);
	}
	
	public void removeEntity(EntityB block) {
		eb.remove(block);
	}
	
	public LinkedList<EntityA> getEntityA(){
		return ea;
	}
	
	public LinkedList<EntityB> getEntityB(){
		return eb;
	}
	
	
}
