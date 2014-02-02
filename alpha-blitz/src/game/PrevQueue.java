package game;

import java.util.LinkedList;

import com.alpha.blitz.AlphaBlitz;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PrevQueue {
	
	private final int BOX_X = 250;
	private final int BOX_Y = 295;
	private final int BOX_WIDTH = 310;
	private final int BOX_HEIGHT = 165;
	
	private final int WORDS_START_X = 330;
	private final int WORDS_START_Y = 320;
	private final int WORDS_Y_OFFSET = 30;
	private final float FONT_SCALE = 1.8f;
	private final int MAX_WORDS = 5;
	
	private LinkedList<String> words;
	private Texture bgtexture;
	private Rectangle box;
	private BitmapFont font;
	
	public PrevQueue()
	{
		words = new LinkedList<String>();
		box = new Rectangle(BOX_X,BOX_Y,BOX_WIDTH,BOX_HEIGHT);
		bgtexture = AlphaBlitz.manager.get("data/prevQueueBgTex.png",Texture.class);
		font = AlphaBlitz.manager.get("data/nint.fnt",BitmapFont.class);
		font.setScale(FONT_SCALE);
	}
	
	public void render(SpriteBatch batch)
	{
		batch.draw(bgtexture,box.x,box.y,box.width,box.height);
		font.setScale(FONT_SCALE);
		font.setColor(1f, 1f, 1f, 1f);
		for(int  i = 0; i < words.size(); i++)
			font.draw(batch, words.get(i), WORDS_START_X, WORDS_START_Y + i*WORDS_Y_OFFSET);
	}
	
	public void clear()
	{
		words.clear();
	}
	public void push(String word)
	{
		if(words.size() >= MAX_WORDS)
			words.remove(MAX_WORDS - 1);
		words.push(word);
	}

}
