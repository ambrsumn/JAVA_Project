import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Random;
import java.util.random.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener
{
	
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNIT = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;  
	 int delay = 150;
	
	final int x[] = new int[GAME_UNIT];
	final int y[] = new int[GAME_UNIT];
	
	int bodyparts = 2;
	int score = 0;
	int foodX;
	int foodY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	
	GamePanel()
	{
		random = new Random();
		this.setBackground(Color.black);
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setFocusable(true);
		this.addKeyListener(new MykeyAdapter());
		
		startGame();
	}
	
	public void startGame()
	{
		newFood();
		running = true;
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g)
	{
		if (running)
		{
//		    for (int i = 0; i < SCREEN_WIDTH / UNIT_SIZE; i++)
//		    {
//		        g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
//		        g.drawLine(0, i * UNIT_SIZE, SCREEN_HEIGHT, i * UNIT_SIZE);
//		    }

		    g.setColor(Color.red);
		    g.fillOval(foodX, foodY, UNIT_SIZE, UNIT_SIZE);

		    for (int i = bodyparts; i >= 0; i--)
		    {
		        if (i == 0)
		        {
		            g.setColor(Color.green);
		            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
		        }

		        else
		        {
//		            g.setColor(Color.green);
		        	g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
		            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
		        }
		    }
		    
			g.setColor(Color.white);
			g.setFont(new Font("INK FREE", Font.BOLD, 25));
			FontMetrics metrices = getFontMetrics(g.getFont());
			g.drawString("SCORE : " + score, (SCREEN_WIDTH - metrices.stringWidth("SCORE : " + score)), g.getFont().getSize());		    
		}
		
		else
		{
			gameOver(g);
		}
	}	

	public void newFood()
	{
		foodX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		foodY = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void move()
	{
		for(int i=bodyparts; i>0; i--)
		{
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction)
		{
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
			
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
			
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
			
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}
	
	public void checkScore()
	{
		if((x[0] == foodX) && (y[0] == foodY))
		{
			bodyparts++;
			score++;
			delay -= 50;
			
			newFood();
		}
	}
	
	public void checkCollisions()
	{
		// Head - Body Collision
		for(int i=bodyparts; i>0; i--)
		{
			if((x[0] == x[i]) && y[0] == y[i])
			{
				running = false;
			}
		}
		// Head - Border COllison
		if(x[0] < 0)
			running = false;
		
		if(x[0] > SCREEN_WIDTH)
			running = false;
		if(y[0] < 0)
			running = false;
		if(y[0] > SCREEN_HEIGHT)
			running = false;
		
		if(running == false)
			timer.stop();
	}
	
	public void gameOver(Graphics g)
	{
		g.setColor(Color.white);
		g.setFont(new Font("INK FREE", Font.BOLD, 75));
		FontMetrics metrices1 = getFontMetrics(g.getFont());
		g.drawString("SCORE : " + score, (SCREEN_WIDTH - metrices1.stringWidth("SCORE : " + score))/2, g.getFont().getSize());		
		
		g.setColor(Color.red);
		g.setFont(new Font("INK FREE", Font.BOLD, 75));
		FontMetrics metrices2 = getFontMetrics(g.getFont());
		g.drawString("GAME OVER", (SCREEN_WIDTH - metrices2.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2);
	}	
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(running)
		{
			move();
			checkScore();
			checkCollisions();
		}
		repaint();
	}
	
	public class MykeyAdapter extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_LEFT:
			if(direction != 'R')
			{
				direction = 'L';
			}
			break;
			
			case KeyEvent.VK_RIGHT:
			if(direction != 'L')
			{
				direction = 'R';
			}
			break;		
			
			case KeyEvent.VK_UP:
			if(direction != 'D')
			{
				direction = 'U';
			}
			break;
			
			case KeyEvent.VK_DOWN:
			if(direction != 'U')
			{
				direction = 'D';
			}
			break;			
			}
		}
	}

}
