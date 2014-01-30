package menu;

import com.alpha.blitz.AlphaBlitz;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Button {
	private final int DEFAULT_WIDTH = 64;
	private final int DEFAULT_HEIGHT = 32;
	
	protected Rectangle box;
	protected Texture texture;
	protected boolean pressed;
	protected boolean activated;
	
	public Button()
	{
		box = new Rectangle();
		box.x = 0;
		box.y = 0;
		box.width = DEFAULT_WIDTH;
		box.height = DEFAULT_HEIGHT;
		pressed = false;
		activated = false;
	}
	
	public Button(int x, int y)
	{
		this();
		box.x = x;
		box.y = y;
	}
	
	public Button(int x, int y, int width, int height)
	{
		this();
		box.x = x;
		box.y = y;
		box.width = width;
		box.height = height;
	}
	
	public void update()
	{
		if(Gdx.input.justTouched())
		{
			if(!pressed)
			{
				if(box.contains(Gdx.input.getX(),AlphaBlitz.SCREEN_HEIGHT - Gdx.input.getY()))
				{
					pressed = true;
				}
			}
		}
		else if(pressed)
		{
			if(Gdx.input.isTouched() && !box.contains(Gdx.input.getX(),AlphaBlitz.SCREEN_HEIGHT - Gdx.input.getY()))
			{
				pressed = false;
			}
			else if(!Gdx.input.isTouched())
			{
				pressed = false;
				activated = true;
			}
		}
	}
	
	public void clearActivation()
	{
		activated = false;
	}
	
	public boolean isActivated()
	{
		return activated;
	}
	
	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}
	
	public void render(SpriteBatch batch)
	{
		if(!pressed)
			batch.draw(texture, box.x, box.y, box.width, box.height);
		else
		{
			Color prevColor = batch.getColor();
			batch.setColor(0.5f, 0.0f, 0.0f, 1.0f);
			batch.draw(texture, box.x, box.y, box.width, box.height);
			batch.setColor(prevColor);
		}
	}
}
