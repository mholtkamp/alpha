package game;

import java.util.ArrayList;

import menu.Button;

import com.alpha.blitz.AlphaBlitz;
import com.alpha.blitz.GameState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Game {

	private final float START_TIME = 2f*60f;
	private final int POOL_LIST_SIZE = 4482;
	
	private final int POOL_SIZE	= 6;
	private final int POOL_X = 150;
	private final int POOL_Y = 75;
	private final int POOL_X_PADDING = 15;
	
	private final int CAND_X = 142;
	private final int CAND_Y = 160;
	private final int CAND_WIDTH = 522;
	private final int CAND_HEIGHT = 82;
	private final int CAND_Y_OFFSET = 5;
	
	private final int CONTROL_BUTTON_WIDTH = 96;
	private final int CONTROL_BUTTON_HEIGHT = 80;
	
	private final int BACK_BUTTON_X = 675;
	private final int BACK_BUTTON_Y = 80;
	
	private final int CHECK_BUTTON_X = 675;
	private final int CHECK_BUTTON_Y = 168;
	
	private final int SCRAMBLE_BUTTON_X = 25;
	private final int SCRAMBLE_BUTTON_Y = 80;
	
	private final int NEXT_BUTTON_X = 25;
	private final int NEXT_BUTTON_Y = 168;
	
	private final int TIME_X = 620;
	private final int TIME_Y = 420;
	
	private final int SCORE_X = TIME_X;
	private final int SCORE_Y = 390;
	
	private final int COUNTER_X = 50;
	private final int COUNTER_Y = 300;
	private final int COUNTER_WIDTH = 148;
	private final int COUNTER_HEIGHT = 148;
	
	private final int TOTAL_WORDS_X = 97;
	private final int TOTAL_WORDS_Y = 355;
	
	private final int CURRENT_WORDS_X = 97;
	private final int CURRENT_WORDS_Y = 415;
	
	private int totalWords;
	private int currentWords;
	
	
	private Button backButton;
	private Button checkButton;
	private Button scrambleButton;
	private Button nextButton;
	
	private float time;
	private int score;
	private BitmapFont font;
	private ArrayList<Letter> pool;
	private ArrayList<Letter> candidate;
	private ArrayList<String> roundWordlist;
	private ArrayList<String> prevWords;
	private PrevQueue prevQueue;
	
	private Texture candidateBarTex;
	private Texture counterTex;

	public Game()
	{
		time = START_TIME;
		score = 0;
		pool = new ArrayList<Letter>();
		candidate = new ArrayList<Letter>();
		roundWordlist = new ArrayList<String>();
		candidateBarTex = AlphaBlitz.manager.get("data/candidateBarTex.png",Texture.class);
		counterTex = AlphaBlitz.manager.get("data/progressTex.png",Texture.class);
		
		backButton = new Button(BACK_BUTTON_X,BACK_BUTTON_Y,CONTROL_BUTTON_WIDTH,CONTROL_BUTTON_HEIGHT);
		checkButton = new Button(CHECK_BUTTON_X,CHECK_BUTTON_Y,CONTROL_BUTTON_WIDTH,CONTROL_BUTTON_HEIGHT);
		scrambleButton = new Button(SCRAMBLE_BUTTON_X,SCRAMBLE_BUTTON_Y,CONTROL_BUTTON_WIDTH,CONTROL_BUTTON_HEIGHT);
		nextButton = new Button(NEXT_BUTTON_X,NEXT_BUTTON_Y,CONTROL_BUTTON_WIDTH,CONTROL_BUTTON_HEIGHT);
		
		backButton.setTexture(AlphaBlitz.manager.get("data/backButtonTex.png",Texture.class));
		checkButton.setTexture(AlphaBlitz.manager.get("data/checkButtonTex.png",Texture.class));
		scrambleButton.setTexture(AlphaBlitz.manager.get("data/scrambleButtonTex.png",Texture.class));
		nextButton.setTexture(AlphaBlitz.manager.get("data/nextButtonTex.png",Texture.class));
		
		prevQueue = new PrevQueue();
		prevWords = new ArrayList<String>();
		
		font = AlphaBlitz.manager.get("data/nint.fnt",BitmapFont.class);
		
		totalWords = 0;
		currentWords = 0;
	
		/*
		while(true)
		{
			generateSemiRandomPool();
			if(totalWords >= 10)
				break;
		}
		*/
		
		fetchGoodPool();
		
		
		
	}
	
	private void fetchGoodPool()
	{
		pool.clear();
		roundWordlist.clear();
		prevWords.clear();
		currentWords = 0;
		
		String poolString = AlphaBlitz.poolList[MathUtils.random(POOL_LIST_SIZE)];
		
		for(int i=0; i < poolString.length(); i++)
		{
			pool.add(new Letter(poolString.charAt(i)));
			
			pool.get(i).setPoolPos(i);
			pool.get(i).setBox(POOL_X + POOL_X_PADDING*i + Letter.DEFAULT_LETTER_WIDTH*i,POOL_Y);
		}
		
		for(int i = 0; i < pool.size(); i++)
			generateValidWords(poolString.substring(i,i+1),poolString.substring(0,i) + poolString.substring(i+1, poolString.length()));
		
		totalWords = roundWordlist.size();
		System.out.println(totalWords);
	
	}
	private void generateSemiRandomPool()
	{
		pool.clear();
		roundWordlist.clear();
		prevWords.clear();
		currentWords = 0;
		for(int i=0; i < POOL_SIZE; i++)
		{
			if(i < 2)
				pool.add(new Letter(0));
			else
				pool.add(new Letter(1));
			
			pool.get(i).setPoolPos(i);
			pool.get(i).setBox(POOL_X + POOL_X_PADDING*i + Letter.DEFAULT_LETTER_WIDTH*i,POOL_Y);
		}
		
		String poolstr = "";
		for(int i =0; i < pool.size(); i++)
			poolstr+= pool.get(i).getValue();
		System.out.println("poolstr: "+ poolstr);
		
		for(int i = 0; i < pool.size(); i++)
			generateValidWords(poolstr.substring(i,i+1),poolstr.substring(0,i) + poolstr.substring(i+1, poolstr.length()));
		
		totalWords = roundWordlist.size();
		System.out.println(totalWords);
	}
	
	private void generateValidWords(String cur, String rem)
	{
		if(!rem.equals(""))
		{
			for(int i = 0; i < rem.length(); i++)
				generateValidWords(cur + rem.substring(i,i+1),rem.substring(0,i)+rem.substring(i+1,rem.length()));
		}
		if(AlphaBlitz.wordlist.contains(cur.toLowerCase()) && (cur.length() >= 3) && !roundWordlist.contains(cur))
			roundWordlist.add(cur);
		
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
		batch.draw(counterTex, COUNTER_X,COUNTER_Y, COUNTER_WIDTH,COUNTER_HEIGHT);
		
		for(int i = 0; i < pool.size(); i++)
		{
			pool.get(i).render(batch);
		}
		
		backButton.render(batch);
		checkButton.render(batch);
		scrambleButton.render(batch);
		nextButton.render(batch);
		
		prevQueue.render(batch);
		
		font.setColor(0f,0f,0f,1f);
		font.setScale(1.8f);
		if(((int) time)%60 >= 10)
			font.draw(batch, ""+ ((int) time/60) + ":" + ((int) time)%60, TIME_X, TIME_Y);
		else
			font.draw(batch, ""+ ((int) time/60) + ":0" + ((int) time)%60, TIME_X, TIME_Y);
		font.draw(batch, ""+ score + " PTS",SCORE_X,SCORE_Y);
		
		font.setScale(2.2f);
		
		if((""+currentWords).length() == 1)
			font.draw(batch, ""+currentWords, CURRENT_WORDS_X + 12, CURRENT_WORDS_Y);
		else
		font.draw(batch, ""+currentWords, CURRENT_WORDS_X, CURRENT_WORDS_Y);
		
		if((""+totalWords).length() == 1)
			font.draw(batch, ""+totalWords, TOTAL_WORDS_X + 12, TOTAL_WORDS_Y);
		else
			font.draw(batch, ""+totalWords, TOTAL_WORDS_X, TOTAL_WORDS_Y);
		
		
		
		
	}
	
	public void update()
	{
		time -= Gdx.graphics.getDeltaTime();
		if(time <= 0)
		{
			processScore();
			AlphaBlitz.gamestate = GameState.POSTGAME;
			AlphaBlitz.postgame.setPoints(score);
		}
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
		
		backButton.update();
		checkButton.update();
		scrambleButton.update();
		nextButton.update();
		
		if(checkButton.isActivated())
		{
			checkButton.clearActivation();
			if(candidate.size() >= 3)
			{
				String candStr = lettersToString(candidate);
				if(!prevWords.contains(candStr) && findMatch(candStr))
				{
					prevWords.add(candStr);
					prevQueue.push(candStr);
					awardPoints(candStr);
					awardTime(candStr);
					currentWords++;
				}
			}
			clearCandidate();
		}
		
		if(backButton.isActivated())
		{
			backButton.clearActivation();
			if(candidate.size() > 0)
				removeFromCandidate(candidate.get(candidate.size()-1).getPoolPos());
		}
		
		if(scrambleButton.isActivated())
		{
			scrambleButton.clearActivation();
			Letter[] newPool = new Letter[pool.size()];
			System.out.println();
			for(int i = 0; i < POOL_SIZE; i++)
			{
				int targPos = MathUtils.random(0,pool.size() - 1);
				newPool[i] = pool.get(targPos);
				pool.remove(targPos);
				newPool[i].setPoolPos(i);
				
			}
			
			for(int i = 0; i < POOL_SIZE; i++)
			{
				pool.add(newPool[i]);
				updatePosition(pool.get(i));
			}
		}
		
		if(nextButton.isActivated())
		{
			nextButton.clearActivation();
			if(currentWords >= 5)
			{
				fetchGoodPool();
				prevQueue.clear();
				candidate.clear();

			}
			
		}
	}
	
	private void clearCandidate()
	{
		for(int i = 0; i < pool.size(); i++)
		{
			if(candidate.contains(pool.get(i)))
				removeFromCandidate(i);
		}
		candidate.clear();
	}
	private void awardPoints(String candStr)
	{
		score += (candStr.length()*candStr.length());
	}
	
	private void awardTime(String candStr)
	{
		
	}
	
	private boolean findMatch(String str)
	{
		if(roundWordlist.contains(str))
			return true;
		else
			return false;
	}
	private String lettersToString(ArrayList<Letter> letters)
	{
		String str = "";
		for(int i = 0; i < candidate.size(); i++)
		{
			str += candidate.get(i).getValue();
		}
		return str;	
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
	
	private void processScore()
	{
		if(score > AlphaBlitz.highScore)
		{
			AlphaBlitz.highScore = score;
			Gdx.files.external("hsf.txt").writeString(""+score, false);
		}
			
	}
	public void reset()
	{
		time = START_TIME;
		score = 0;
		currentWords = 0;
		prevQueue.clear();
		candidate.clear();
		
		fetchGoodPool();
	}
}
