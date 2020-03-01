package poke.engine.kernel;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class EngineConfig {

	private static EngineConfig instance = null;

	private String fileName;
	private Properties properties;

	private String main;
	private String version;

	private int window_width;
	private int window_height;
	private String window_title;

	private int fps_cap;
	private int ups_cap;

	public EngineConfig(String fileName) {
		instance = this;
		init(fileName);
	}

	private void init(String fileName) {
		instance = this;
		this.properties = new Properties();
		try {
			FileReader fr = new FileReader(new File("res/" + fileName));
			properties.load(fr);
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.main = properties.getProperty("main");
		this.version = properties.getProperty("version");

		this.window_width = Integer.parseInt(properties.getProperty("window_width"));
		this.window_height = Integer.parseInt(properties.getProperty("window_height"));
		this.window_title = properties.getProperty("window_title");

		this.fps_cap = Integer.parseInt(properties.getProperty("fps_cap"));
		this.ups_cap = Integer.parseInt(properties.getProperty("ups_cap"));
	}

	public String getFileName() {
		return fileName;
	}

	public Properties getProperties() {
		return properties;
	}
	
	public String getMain() {
		return main;
	}

	public String getVersion() {
		return version;
	}

	public int getWindow_width() {
		return window_width;
	}

	public int getWindow_height() {
		return window_height;
	}

	public String getWindow_title() {
		return window_title;
	}

	public int getFps_cap() {
		return fps_cap;
	}

	public int getUps_cap() {
		return ups_cap;
	}

	public static EngineConfig getInstance() {
		return instance;
	}

}
