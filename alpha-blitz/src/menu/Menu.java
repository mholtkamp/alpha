package menu;

import com.alpha.blitz.AlphaBlitz;
import com.alpha.blitz.GameState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Menu {

	private final int BUTTON_X = 515;
	private final int BUTTON_Y = 310;
	private final int BUTTON_Y_PADDING = 30;
	private final int BUTTON_WIDTH = 200;
	private final int BUTTON_HEIGHT = 50;
	
	private Button startButton;
	private Button exitButton;
	private Button optionsButton;
	
	private Texture texture;
	
	public Menu()
	{
		texture = AlphaBlitz.manager.get("data/menuTex.png", Texture.class);
		
		startButton = new Button(BUTTON_X, BUTTON_Y,BUTTON_WIDTH,BUTTON_HEIGHT);
		optionsButton = new Button(BUTTON_X, BUTTON_Y - BUTTON_Y_PADDING - BUTTON_HEIGHT,BUTTON_WIDTH,BUTTON_HEIGHT);
		exitButton = new Button(BUTTON_X, BUTTON_Y - 2*BUTTON_Y_PADDING - 2*BUTTON_HEIGHT,BUTTON_WIDTH,BUTTON_HEIGHT);
		
		startButton.setTexture(AlphaBlitz.manager.get("data/startButtonTex.png",Texture.class));
		optionsButton.setTexture(AlphaBlitz.manager.get("data/optionsButtonTex.png",Texture.class));
		exitButton.setTexture(AlphaBlitz.manager.get("data/exitButtonTex.png",Texture.class));
	}
	
	public void update()
	{
		startButton.update();
		optionsButton.update();
		exitButton.update();
		
		if(startButton.isActivated())
		{
			resetMenu();
			AlphaBlitz.gamestate = GameState.GAME;
		}
		else if(optionsButton.isActivated())
		{
			resetMenu();
			AlphaBlitz.gamestate = GameState.OPTIONS;
		}
		else if(exitButton.isActivated())
		{
			resetMenu();
			Gdx.app.exit();
		}
	}
	
	public void render(SpriteBatch batch)
	{
		batch.draw(texture,0,0,AlphaBlitz.SCREEN_WIDTH,AlphaBlitz.SCREEN_HEIGHT);
		startButton.render(batch);
		optionsButton.render(batch);
		exitButton.render(batch);
	}
	
	private void resetMenu()
	{
		startButton.clearActivation();
		exitButton.clearActivation();
		optionsButton.clearActivation();
	}
}
