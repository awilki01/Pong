package com.game.src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ComputerPlayer {

	private double puckPosition;
	private double x;
	private double y = puckPosition - 30;
	private double playerSpeed = 4;

	public ComputerPlayer(double x, double y) { // not sure if i need
												// to import Game
												// game into this
												// yet
		this.x = x;
		this.y = y;

	}

	public void tick(double puckPosition) {

		// Center computer player on puck position

		if (y <= puckPosition - 30) {
			y += playerSpeed;
		} 
		if (y > puckPosition - 30){
			y -= playerSpeed;
		}

	}

	public void render(Graphics g) {
		
		
		
		g.setColor(Color.PINK);
		g.drawRect((int) x, (int) y, 20, 60);
		g.fillRect((int) x, (int) y, 20, 60);
		
		/*
		----Use this in a different Score class to keep score---
		char[] score = new char[10];
		score[0] = 'H';
		g.setFont(new Font("default", Font.BOLD, 32));
		g.drawChars(score, 0, 5, 100, 100);
		*/

	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double y) {
		this.x = x;
	}

	public void setYPuckPosition(double y) {
		this.puckPosition = y;
	}

}
