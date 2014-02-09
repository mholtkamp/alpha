package postgame;

import com.alpha.blitz.AlphaBlitz;
import com.alpha.blitz.GameState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import menu.Button;

public class Postgame {
	
	private final int GAME_OVER_X = 80;
	private final int GAME_OVER_Y = 300;
	
	private final int POINTS_X = 95;
	private final int POINTS_Y = 200;
	
	private final float FONT_SCALE = 4.0f;
	
	private final float TIMER_START_VALUE = 5f;
	private int points;
	private float timer;

	private BitmapFont font;
	
	public Postgame()
	{
		points = 0;

		font = AlphaBlitz.manager.get("data/nint.fnt",BitmapFont.class);
		timer = TIMER_START_VALUE;
	}
	
	public void setPoints(int points)
	{
		this.points = points;
	}
	
	public void render(SpriteBatch batch)
	{
		font.setScale(FONT_SCALE);
		font.setColor(0f, 0f, 0f, 1f);
		font.draw(batch,"Game Over",GAME_OVER_X,GAME_OVER_Y);
		font.setScale(FONT_SCALE/2);
		font.draw(batch, "Points: "+points, POINTS_X, POINTS_Y);
		
		
	}
	
	public void update()
	{
		timer -= Gdx.graphics.getDeltaTime();
		if(Gdx.input.justTouched() && (timer < 4f))
		{
			timer = TIMER_START_VALUE;
			AlphaBlitz.gamestate = GameState.MENU;
		}
	}

}
