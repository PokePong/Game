package poke.core.engine.light;

import java.util.ArrayList;
import java.util.List;

public class LightHandler {

	private static List<PointLight> pointLights = new ArrayList<PointLight>();

	public static void addPointLight(PointLight light) {
		pointLights.add(light);
	}

	public static List<PointLight> getPointLights() {
		return pointLights;
	}

}
