package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.alpha.blitz.AlphaBlitz;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class DictionaryLoader implements Runnable {
	
	public volatile boolean finished;
	
	public DictionaryLoader()
	{
		finished = false;
	}
	
	public void run()
	{
		try
		{
			FileHandle handle = Gdx.files.internal("data/wordsTrimmed.txt");
			InputStream fstream = handle.read();

			BufferedReader br = new BufferedReader(new InputStreamReader(fstream),81612);
			
			String str;
			while((str = br.readLine()) != null)
				AlphaBlitz.wordlist.add(str);
			br.close();
			
			handle = Gdx.files.internal("data/pools.txt");
			fstream = handle.read();
			BufferedReader br2 = new BufferedReader(new InputStreamReader(fstream),35856);

			int i=0;
			while((str = br2.readLine()) != null)
			{
				AlphaBlitz.poolList[i] = str;
				i++;
			}
		
			br2.close();
			
			finished = true;
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}

}
