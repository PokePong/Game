package poke.core.engine.core.engine;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class EngineConfig {

	private String fileName;
	private Properties properties;

	private String main;
	private String version;

	private int window_width;
	private int window_height;
	private String window_title;

	private int fps_cap;
	private int ups_cap;

	private float z_near;
	private float z_far;
	private int fov;

	public EngineConfig(String fileName) {
		init(fileName);
	}

	private void init(String fileName) {
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

		this.z_near = Float.parseFloat(properties.getProperty("z_near"));
		this.z_far = Float.parseFloat(properties.getProperty("z_far"));
		this.fov = Integer.parseInt(properties.getProperty("fov"));
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

	public float getZ_near() {
		return z_near;
	}

	public float getZ_far() {
		return z_far;
	}

	public int getFov() {
		return fov;
	}

}
