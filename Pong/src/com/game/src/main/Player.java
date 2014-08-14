package com.game.src.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

public class Player {

	private double x;
	private double y;

	public Player(double x, double y) { // not sure if i need to
										// import Game game into
										// this yet

		this.x = x;
		this.y = y;

	}

	public void tick() {

	}

	public void render(Graphics g) {

		g.setColor(Color.GREEN);
		g.drawRect((int) x, (int) y, 20, 60);
		g.fillRect((int) x, (int) y, 20, 60);

	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double y) {
		this.x = x - 30;
	}

	public void setY(double y) {
		this.y = y - 30;
	}

}
