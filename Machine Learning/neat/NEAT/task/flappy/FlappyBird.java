package NEAT.task.flappy;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

import NEAT.algorithm.neural.NeuralNetwork;
import NEAT.task.Objective;

public class FlappyBird extends Objective implements ActionListener, MouseListener, KeyListener {

	private static int				inputNodes	= 3;
	private static int				outputNodes	= 1;
	private int						fitness		= 0;
	private int						distance	= 0;
	private boolean					human		= true;
	private NeuralNetwork			n;

	private final int				WIDTH		= 800;
	private final int				HEIGHT		= 800;

	private final int				pipeSpacing	= 600;
	private final int				pipeWidth	= 100;
	private final int				pipeGap		= 180;
	private final int				speed		= 5;
	private final int				jumpHeight	= 15;

	private boolean					gameOver	= false;
	private boolean					started		= false;

	private int						yMotion		= 0;
	private int						score		= 0;
	private int						ticks		= 0;

	private JFrame					jf;
	private Timer					timer;

	private Rectangle				bird;
	private ArrayList<Rectangle>	pipes;

	public FlappyBird(boolean human) {
		super(inputNodes, outputNodes);

		jf = new JFrame();
		jf.setTitle("Flappy Bird NEAT");
		jf.setSize(WIDTH, HEIGHT);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.addMouseListener(this);
		jf.addKeyListener(this);
		jf.setVisible(true);

		timer = new Timer(20, this);

		bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
		pipes = new ArrayList<Rectangle>();

		this.human = human;

		addPipeSet();
		addPipeSet();

		if (human) timer.start();
		else getScholar().testGeneration();
	}

	private void addPipeSet() {
		Random r = new Random();
		int height1 = 150 + r.nextInt(HEIGHT - pipeGap - 300);
		int height2 = HEIGHT - pipeGap - height1;
		int x;

		if (!pipes.isEmpty()) x = pipes.get(pipes.size() - 1).x + pipeSpacing;
		else x = WIDTH + pipeWidth;

		pipes.add(new Rectangle(x, HEIGHT - height1, pipeWidth, height1));
		pipes.add(new Rectangle(x, 0, pipeWidth, height2));
	}

	private void jump() {
		if (gameOver) reset();
		if (!started) started = true;
		if (yMotion > 0) yMotion = 0;

		yMotion -= jumpHeight;
	}

	private void reset() {
		bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
		gameOver = false;
		yMotion = 0;
		score = 0;
		ticks = 0;
		distance = 0;

		pipes.clear();
		addPipeSet();
		addPipeSet();

		timer.start();
	}

	public void repaint(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.orange);
		g.fillRect(0, HEIGHT - 120, WIDTH, 120);

		g.setColor(Color.green);
		g.fillRect(0, HEIGHT - 120, WIDTH, 20);

		g.setColor(Color.red);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);

		g.setColor(Color.green.darker());

		for (Rectangle p : pipes)
			g.fillRect(p.x, p.y, p.width, p.height);

		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 100));

		if (!started && human) g.drawString("Click To Start", 75, HEIGHT / 2 - 50);
		if (gameOver && human) g.drawString("Game Over", 100, HEIGHT / 2 - 50);
		if (started) g.drawString("" + score, WIDTH / 2 - 25, 100);
	}

	public static void main(String[] args) {
		//FlappyBird  run = new FlappyBird(true);
		FlappyBird run = new FlappyBird(false);
	}

	// Non-Game Methods
	@Override
	public int calculateFitness(NeuralNetwork n) {
		this.n = n;
		this.human = false;
		started = true;
		timer.start();
		
		return 0;
	}

	@Override
	public Object[] getData() {
		Object[] data = new Object [inputNodeCount];

		data[0] = bird.y;
		data[1] = pipes.get(0).getMaxY();
		data[2] = pipes.get(1).getHeight();

		return data;
	}

	@Override
	public Object[] pushGameData() {
		// TODO Auto-generated method stub
		return null;
	}

	// Listeners
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (human && e.getKeyCode() == KeyEvent.VK_SPACE) jump();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (human) jump();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ticks++;

		if (started) {

			if (!human) {
				distance += speed;
				pushGameData();
			}

			if (ticks % 2 == 0 && yMotion < 15) yMotion += 2;
			bird.y += yMotion;

			for (int i = pipes.size() - 1; i >= 0; i--) {
				Rectangle p = pipes.get(i);

				if (p.x + pipeWidth <= 0) {
					pipes.remove(p);
					if (p.y == 0) addPipeSet();
				}
			}

			for (Rectangle p : pipes) {
				p.x -= speed;

				if (p.y == 0 && bird.x + bird.width / 2 > p.x + pipeWidth / 2 - speed && bird.x + bird.width / 2 < p.x + pipeWidth / 2 + speed) score++;

				if (p.intersects(bird)) {
					gameOver = true;

					if (bird.x <= p.x) bird.x = p.x - bird.width;
					else {
						if (p.y != 0) {
							bird.y = p.y - p.height;
						}
						else if (bird.y < p.height) {
							bird.y = p.height;
						}
					}
				}
			}

			if (bird.y > HEIGHT - 120 || bird.y < 0) gameOver = true;
			if (bird.y + yMotion >= HEIGHT - 120) {
				bird.y = HEIGHT - 120 - bird.height;
				gameOver = true;
			}

			if (gameOver) timer.stop();
		}

		repaint(jf.getGraphics());
	}

}
