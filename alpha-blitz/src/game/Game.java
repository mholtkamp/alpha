package game;

import java.util.ArrayList;

import com.alpha.blitz.AlphaBlitz;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Game {

	private final float START_TIME = 2f*60f;
	private final int POOL_SIZE	= 6;
	private final int POOL_X = 150;
	private final int POOL_Y = 75;
	private final int POOL_X_PADDING = 15;
	private final int CAND_X = 142;
	private final int CAND_Y = 160;
	private final int CAND_WIDTH = 522;
	private final int CAND_HEIGHT = 82;
	private final int CAND_Y_OFFSET = 5;
	
	private float time;
	private int score;
	private ArrayList<Letter> pool;
	private ArrayList<Letter> candidate;
	
	private Rectangle candidateBox;
	private Texture candidateBarTex;

	public Game()
	{
		time = START_TIME;
		score = 0;
		pool = new ArrayList<Letter>();
		candidate = new ArrayList<Letter>();
		candidateBarTex = AlphaBlitz.manager.get("data/candidateBarTex.png");
		
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
		batch.draw(candidateBarTex, CAND_X, CAND_Y, CAND_WIDTH, CAND_HEIGHT);
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
			if(pool.get(i).isActivated())
			{
				if(pool.get(i).inCandidate())
					removeFromCandidate(i);
				else
					addToCandidate(i);
			}
		}
	}
	
	private void removeFromCandidate(int i)
	{
		int j = pool.get(i).getCandidatePos();
		candidate.remove(pool.get(i));
		pool.get(i).clearCandidate();
		pool.get(i).clearActivation();
		updatePosition(pool.get(i));
		for(; j < candidate.size();j++)
		{
			candidate.get(j).setCandidate(j);
			updatePosition(candidate.get(j));
		}
	}
	
	private void addToCandidate(int i)
	{
		candidate.add(pool.get(i));
		pool.get(i).setCandidate(candidate.size()-1);
		pool.get(i).clearActivation();
		updatePosition(pool.get(i));
	}
	
	private void updatePosition(Letter letter)
	{
		if(!letter.inCandidate())
			letter.setBox(POOL_X + POOL_X_PADDING*letter.getPoolPos() + Letter.DEFAULT_LETTER_WIDTH*letter.getPoolPos(),POOL_Y);
		else
			letter.setBox(POOL_X + POOL_X_PADDING*letter.getCandidatePos() + Letter.DEFAULT_LETTER_WIDTH*letter.getCandidatePos(),CAND_Y+CAND_Y_OFFSET);
	}
}
