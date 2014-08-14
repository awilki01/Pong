package com.game.src.main;

import java.awt.Color;
import java.awt.Graphics;

public class Puck {

	private double x;
	private double y;
	private int puckSpeed = 3;
	private double xDirection;
	private double yDirection;

	public Puck(double x, double y, double xDirection, double yDirection) {
		this.x = x;
		this.y = y;
		this.xDirection = xDirection;
		this.yDirection = yDirection;

	}

	public void tick() {
		/*
		 * // Limit YDirection speed to 1.25		 * 
		 * if (yDirection > 1.25) { this.yDirection = 1.25; } else if
		 * (yDirection < -1.25) { this.yDirection = -1.25; }
		 * 
		 * // Limit XDirection speed to 4 if (xDirection > 4.0) {
		 * this.xDirection = 4.0; } else if (xDirection < -4.0) {
		 * this.yDirection = -4.0; }
		 */

		x += xDirection * puckSpeed;
		y += yDirection * puckSpeed;

		// System.out.println(yDirection);

	}

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawOval((int) x, (int) y, 5, 5);
		g.fillOval((int) x, (int) y, 5, 5);

	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setXDirection(double xDirection) {
		this.xDirection = xDirection;
	}

	public void setYDirection(double yDirection) {
		this.yDirection = yDirection;
	}

	public double getXDirection() {
		return xDirection;
	}

	public double getYDirection() {
		return yDirection;
	}

}
