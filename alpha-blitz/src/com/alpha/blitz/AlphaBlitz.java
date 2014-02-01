package com.alpha.blitz;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;

import game.Game;
import menu.Menu;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AlphaBlitz implements ApplicationListener {
	
	public static AssetManager manager;
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 480;
	public static GameState gamestate;
	
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Menu menu;
	private Game game;
	public static HashSet<String> wordlist;

	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		

		camera = new OrthographicCamera(1, h/w);
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		batch = new SpriteBatch();
		manager = new AssetManager();
		loadAssets();
		
		wordlist =  new HashSet(45402);

		fillWordList();

		menu = new Menu();
		game = new Game();
		gamestate = GameState.MENU;
		Gdx.gl.glClearColor(1, 1, 1, 1);

	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	public void loadAssets()
	{
		  manager.load("data/menuTex.png",Texture.class);
		  manager.load("data/startButtonTex.png",Texture.class);
		  manager.load("data/optionsButtonTex.png",Texture.class);
		  manager.load("data/exitButtonTex.png",Texture.class);
		  
		  manager.load("data/letterTex.png",Texture.class);
		  manager.load("data/candidateBarTex.png",Texture.class);
		  manager.load("data/backButtonTex.png",Texture.class);
		  manager.load("data/checkButtonTex.png",Texture.class);
		  manager.load("data/scrambleButtonTex.png",Texture.class);
		  manager.load("data/nextButtonTex.png",Texture.class);
		  manager.load("data/prevQueueBgTex.png",Texture.class);
		  manager.load("data/progressTex.png",Texture.class);
		  
		  manager.load("data/nint.fnt",BitmapFont.class);
          manager.update();
          manager.finishLoading();
	}
	
	private void update()
	{
		switch (gamestate.index) 
		{
	        case 0:  updateSplash();
	            break;
	        case 1:  updateMenu();
	        	break;
	        case 2:  updateGame();
	        	break;
	        case 3:  updatePostGame();
				break;	
	        case 4:  updateOptions();
				break;
			default: System.out.println("Unknown GameState");
		}
	}
	
	private void updateSplash()
	{
		
	}
	
	private void updateMenu()
	{
		menu.update();
	}
	
	private void updateGame()
	{
		game.update();
	}
	
	private void updatePostGame()
	{
		
	}
	
	private void updateOptions()
	{
		
	}
	
	@Override
	public void render()
	{
		update();
		
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		
		switch (gamestate.index) 
		{
	        case 0:  renderSplash();
	            break;
	        case 1:  renderMenu();
	        	break;
	        case 2:  renderGame();
	        	break;
	        case 3:  renderPostGame();
				break;	
	        case 4:  renderOptions();
				break;
			default: System.out.println("Unknown GameState");
		}
		
		batch.end();
	}
	
	private void renderSplash()
	{
		
	}
	
	private void renderMenu()
	{
		menu.render(batch);
	}
	
	private void renderGame()
	{
		game.render(batch);
	}
	
	private void renderPostGame()
	{
		
	}
	
	private void renderOptions()
	{
		
	}
	
	private void fillWordList()
	{
		FileHandle handle = Gdx.files.internal("data/words.txt");
		InputStream fstream = handle.read();
		Scanner in = new Scanner(fstream);

		while(in.hasNext())
			wordlist.add(in.next());
		in.close();
	}
	
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
