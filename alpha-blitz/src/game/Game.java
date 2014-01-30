package game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game {

	private final float START_TIME = 2f*60f;
	private final int POOL_SIZE	= 6;
	private final int POOL_X = 150;
	private final int POOL_Y = 75;
	private final int POOL_X_PADDING = 15;
	
	private float time;
	private int score;
	private ArrayList<Letter> pool;
	private ArrayList<Letter> candidate;

	public Game()
	{
		time = START_TIME;
		score = 0;
		pool = new ArrayList<Letter>();
		candidate = new ArrayList<Letter>();
		
		for(int i=0; i < POOL_SIZE; i++)
		{
			if(i < 2)
				pool.add(new Letter(0));
			else
				pool.add(new Letter(1));
			
			pool.get(i).setPoolPos(i);
			pool.get(i).setBox(POOL_X + POOL_X_PADDING*i + Letter.DEFAULT_LETTER_WIDTH*i,POOL_Y);
		}
		
		//TEST
		printPool();
		//END
		
	}
	
	private void printPool()
	{
		for(int i=0; i < POOL_SIZE;i++)
		{
			System.out.print(pool.get(i).getValue());
		}
	}
	
	public void render(SpriteBatch batch)
	{
		for(int i = 0; i < pool.size(); i++)
		{
			pool.get(i).render(batch);
		}
	}
	
	public void update()
	{
		for(int i = 0; i < pool.size(); i++)
		{
			pool.get(i).update();
		}
	}
}
