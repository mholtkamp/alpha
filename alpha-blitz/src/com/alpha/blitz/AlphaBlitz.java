package com.alpha.blitz;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Scanner;

import postgame.Postgame;
import util.DictionaryLoader;

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
	
	
	public static OrthographicCamera camera;
	private SpriteBatch batch;
	public static Menu menu;
	public static Game game;
	public static Postgame postgame;
	public static HashSet<String> wordlist;
	public static String[] poolList;
	
	private DictionaryLoader dl;
	private BitmapFont font;
	private boolean assetsLoaded;

	
	@Override
	public void create() {		
		
		//camera = new OrthographicCamera(1, h/w);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		batch = new SpriteBatch();
		manager = new AssetManager();
		loadAssets();
		
		font = manager.get("data/nint.fnt",BitmapFont.class);
		
		wordlist =  new HashSet<String>(15000);
		poolList = new String[4482];

		dl = new DictionaryLoader();
		assetsLoaded = false;
		//dl.run();
		Gdx.gl.glClearColor(0.8f, 0.8f, 1.0f, 1.0f);
		gamestate = GameState.SPLASH;

	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	public void loadAssets()
	{
		  manager.load("data/nint.fnt",BitmapFont.class);
          manager.update();
          manager.finishLoading();
          
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
		  
		  manager.update();
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
		if(manager.update())
		{
			assetsLoaded = true;
			dl.run();
		}
		if( dl.finished)
		{
			gamestate = GameState.MENU;		
			menu = new Menu();
			game = new Game();
			postgame = new Postgame();
		}
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
		postgame.update();
	}
	
	private void updateOptions()
	{
		
	}
	
	@Override
	public void render()
	{
		update();
		
		Gdx.gl.glClearColor(0.8f, 0.8f, 1.0f, 1.0f);
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
		font.setScale(3.0f);
		font.setColor(0f, 0f, 0f, 1f);
		font.draw(batch,"LOADING",250,250);
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
		postgame.render(batch);
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
