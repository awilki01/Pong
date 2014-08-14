package com.game.src.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final int SCALE = 1;
	public final String TITLE = "Pong";

	Puck puck = new Puck((WIDTH / 2 - 2.5), (HEIGHT / 2 - 2.5), 1, -0.5);
	Player p = new Player((WIDTH - 50), (HEIGHT / 2 - 30));
	ComputerPlayer computerPlayer = new ComputerPlayer(30, (HEIGHT / 2 - 30));

	private double puckPositionX;
	private double puckPositionY;
	private double playerPositionX;
	private double playerPositionY;
	private double computerPlayerPositionX;
	private double computerPlayerPositionY;
	private int computerPlayerScore = 0;
	private int playerScore = 0;

	private boolean running = false;
	private Thread thread;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
			BufferedImage.TYPE_INT_RGB);

	public Game() {

		// Hide the mouse
		Cursor hiddenCursor = getToolkit().createCustomCursor(
				new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
				new Point(1, 1), "");
		setCursor(hiddenCursor);

		// Add mouse motion listener
		addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent e) {
				// Nothing to put here, but method must be implemented for
				// MouseMotionListener
			}

			public void mouseMoved(MouseEvent e) {
				p.setY(e.getY());

			}

		});

	}

	private synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	private synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			System.out
					.println("Exception caught in Game class stop() method...");
			e.printStackTrace();
		}
		System.exit(1);
	}

	public void run() {
		long lastTime = System.nanoTime();
		final double amountOfTicks = 120.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();

		while (running) {
			// Game loop

			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				// System.out.println(updates + " Ticks, FPS " + frames);
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}

	private void tick() {

		puckPositionX = puck.getX();
		puckPositionY = puck.getY();

		// System.out.println("PuckPositionX/Y: " + puckPositionX + "," +
		// puckPositionY + "     PlayerX/Y: " + p.getX() + "," + p.getY());

		// The -5, 25, and 60 below in the if statements account for puck width
		// and height
		// Logic to check if puck hits player

		if ((puckPositionX >= p.getX() - 5)
				&& ((puckPositionX <= p.getX() + 15))
				&& ((puckPositionY >= p.getY()) && (puckPositionY <= p.getY() + 60))) {

			// PLAYER LOGIC
			// Extra logic to determine where on puck it was struck
			// I divided the puck in six parts and will make the puck act
			// differently depending on
			// which part it hits. It gets the current Y Direction and
			// XDirection from the Puck class and either
			// adds or subtracts to it. The Puck class keeps this max value in
			// check by limiting it.
			// The closer to the top and bottom the puck hits the player, the
			// faster the ball gets.
			// The puck class will limit the speed.

			if (puckPositionY <= p.getY() + 10) { // top quarter // 1
				puck.setXDirection(-puck.getXDirection() - 1); // Max speed
																// change
				puck.setYDirection(puck.getYDirection() - 0.75);
			} else if (puckPositionY <= p.getY() + 20) { // 2nd quarter // 2
				puck.setXDirection(-puck.getXDirection() - 0.5);
				puck.setYDirection(puck.getYDirection() - 0.50);
			} else if (puckPositionY <= p.getY() + 30) { // 3rd quarter // 3
				puck.setXDirection(-puck.getXDirection()); // No speed change
				puck.setYDirection(puck.getYDirection() - 0.25);
			} else if (puckPositionY <= p.getY() + 40) { // 4th quarter //4
				puck.setXDirection(-puck.getXDirection()); // No speed change
				puck.setYDirection(puck.getYDirection() + 0.25);
			} else if (puckPositionY <= p.getY() + 50) { // 4th quarter //5
				puck.setXDirection(-puck.getXDirection() - 0.5);
				puck.setYDirection(puck.getYDirection() + 0.50);
			} else if (puckPositionY <= p.getY() + 60) { // 4th quarter //6
				puck.setXDirection(-puck.getXDirection() - 1); // Max speed
																// change
				puck.setYDirection(puck.getYDirection() + 0.75);

			}

			// Logic to check if puck hits computer player
		} else if ((puckPositionX <= computerPlayer.getX() + 25)
				&& ((puckPositionX >= computerPlayer.getX()))
				&& ((puckPositionY >= computerPlayer.getY()) && (puckPositionY <= computerPlayer
						.getY() + 60))) {

			// COMPUTER PLAYER LOGIC
			// Computer player does NOT change speed of puck - it did not feel
			// natural
			// Extra logic to determine where on puck it was struck
			// I divided the puck in six parts and will make the puck act
			// differently depending on
			// which part it hits. It gets the current Y Direction and
			// XDirection from the Puck class and either
			// adds or subtracts to it. The Puck class keeps this max value in
			// check by limiting it.
			// The closer to the top and bottom the puck hits the player, the
			// faster the ball gets.
			// The puck class will limit the speed.
			if (puckPositionY <= computerPlayer.getY() + 10) { // top quarter
				puck.setXDirection(-puck.getXDirection()); // Computer player
															// does not change
															// speed for any of
															// these
				puck.setYDirection(puck.getYDirection() - 0.75);
			} else if (puckPositionY <= computerPlayer.getY() + 20) { // 2nd
																		// quarter
				puck.setXDirection(-puck.getXDirection());
				puck.setYDirection(puck.getYDirection() - 0.50);
			} else if (puckPositionY <= computerPlayer.getY() + 30) { // 3rd
																		// quarter
				puck.setXDirection(-puck.getXDirection());
				puck.setYDirection(puck.getYDirection() - 0.25);
			} else if (puckPositionY <= computerPlayer.getY() + 40) { // 4th
																		// quarter
				puck.setXDirection(-puck.getXDirection());
				puck.setYDirection(puck.getYDirection() + 0.25);
			} else if (puckPositionY <= computerPlayer.getY() + 50) { // 4th
																		// quarter
				puck.setXDirection(-puck.getXDirection());
				puck.setYDirection(puck.getYDirection() + 0.50);
			} else if (puckPositionY <= computerPlayer.getY() + 60) { // 4th
																		// quarter
				puck.setXDirection(-puck.getXDirection());
				puck.setYDirection(puck.getYDirection() + 0.75);
			}

			// Player wins round
		} else if (puckPositionX <= 5) {

			long pause = System.currentTimeMillis();
			playerScore += 1;

			// Reset puck
			puck.setXDirection(-1);
			puck.setYDirection(-0.5);
			puck.setX(WIDTH / 2 - 2.5);
			puck.setY(HEIGHT / 2 - 2.5);
			System.out.println("Score --- Computer: " + computerPlayerScore
					+ "    Player: " + playerScore);

			// Computer player wins round
		} else if (puckPositionX > 795) {
			computerPlayerScore += 1;

			// Reset puck
			puck.setXDirection(1);
			puck.setYDirection(-0.5);
			puck.setX(WIDTH / 2 - 2.5);
			puck.setY(HEIGHT / 2 - 2.5);
			System.out.println("Score --- Computer: " + computerPlayerScore
					+ "    Player: " + playerScore);
		}

		// Logic to check if puck hits top and bottom edges
		if (puckPositionY <= 0) {
			puck.setYDirection(-puck.getYDirection()); // Reverse puck direction
														// and bounce
														// realistically

		} else if (puckPositionY >= HEIGHT) {
			puck.setYDirection(-puck.getYDirection()); // Reverse puck direction
														// and bounce
														// realistically
		}

		p.tick();
		computerPlayer.tick(puckPositionY);
		puck.tick();

	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3); // 3 is triple buffering
			return;
		}

		Graphics g = bs.getDrawGraphics();

		// Enable anti-aliasing
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// ///////////////////////////////////

		g.drawImage(image, 0, 0, getWidth(), getHeight(),/* Color.BLACK, */this);
		p.render(g);
		computerPlayer.render(g);
		puck.render(g);

		// ///////////////////////////////////

		g.dispose();
		bs.show();

	}

	public static void main(String args[]) {
		Game game = new Game();

		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		JFrame frame = new JFrame(game.TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		game.start();

	}

}
