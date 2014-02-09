package game;

import menu.Button;

import com.alpha.blitz.AlphaBlitz;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Letter extends Button{
	
	public static final int DEFAULT_LETTER_WIDTH = 72;
	public static final int DEFAULT_LETTER_HEIGHT = 72;
	public static final int FONT_X_OFFSET = 25;
	public static final int FONT_Y_OFFSET = 45;

	private char value;
	private int poolPos;
	private int candidatePos;
	private boolean inCandidate;
	private BitmapFont font;
	//private Rectangle box;
	
	public Letter()
	{
		super();
		inCandidate = false;
		box.width = DEFAULT_LETTER_WIDTH;
		box.height = DEFAULT_LETTER_HEIGHT;
		texture = AlphaBlitz.manager.get("data/letterTex.png",Texture.class);
		font = AlphaBlitz.manager.get("data/nint.fnt",BitmapFont.class);
	}
	
	public Letter(int n)
	{
		this();
		if(n == 0)
			generateVowel();
		else
			generateConsonant();
	}
	
	public Letter(char v)
	{
		this();
		value = v;
		
	}
	private void generateVowel()
	{
		int v = MathUtils.random(0,4);
		
		switch(v)
		{
			case 0: value = 'A';
				break;
			case 1: value = 'E';
				break;
			case 2: value = 'I';
				break;
			case 3: value = 'O';
				break;
			case 4: value = 'U';
				break;
		}
	}
	
	private void generateConsonant()
	{
		char v = (char) (MathUtils.random(0,25) + 'A');
		
		while(true)
		{
			if(v=='A' || v=='E' || v=='I' || v=='O' || v=='U')
			{
				v = (char) (MathUtils.random(0,25) + 'A');
				continue;
			}
			else
				break;
		}
		value = v;
	}
	
	public char getValue()
	{
		return value;
	}
	
	public void setBox(int x, int y, int width, int height)
	{
		box.x = x;
		box.y = y;
		box.width = width;
		box.height = height;
	}
	
	public void setBox(int x, int y)
	{
		box.x = x;
		box.y = y;
	}
	
	public void setPoolPos(int i)
	{
		poolPos = i;
	}
	
	public int getPoolPos()
	{
		return poolPos;
	}
	
	public boolean inCandidate()
	{
		return inCandidate;
	}

	public void setCandidate(int i)
	{
		inCandidate = true;
		candidatePos = i;
	}
	
	public int getCandidatePos()
	{
		return candidatePos;
	}
	public void clearCandidate()
	{
		inCandidate = false;
		candidatePos = -1;
	}
	
	public void render(SpriteBatch batch)
	{
		super.render(batch);
		font.setScale(2.0f);
		if(pressed)
			font.setColor(1.0f,1.0f,1.0f,1.0f);
		else
			font.setColor(0.0f,0.0f,0.0f,1.0f);
		font.draw(batch,""+value, box.x + FONT_X_OFFSET, box.y + FONT_Y_OFFSET);
	}
	
}
