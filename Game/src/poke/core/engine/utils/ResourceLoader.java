package poke.core.engine.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ResourceLoader {

	public static FileReader loadResource(String fileName) {
		String path = System.getProperty("user.dir") + "/res/" + fileName;
		FileReader fr = null;
		try {
			fr = new FileReader(new File(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fr;
	}

	public static FileReader loadResource(String dir, String fileName) {
		return loadResource(dir + "/" + fileName);
	}

	public static String loadShader(String source) {
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = new BufferedReader(loadResource("shader/" + source));
		String line;
		try {
			while ((line = shaderReader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			shaderReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return shaderSource.toString();
	}

}
