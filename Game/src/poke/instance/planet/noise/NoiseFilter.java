package poke.instance.planet.noise;

public class NoiseFilter {

	private Noise noise;
	private NoiseSettings settings;

	public NoiseFilter(NoiseSettings settings) {
		this.noise = new Noise(64);
		this.settings = settings;
	}

	public Noise getNoise() {
		return noise;
	}

	public NoiseSettings getSettings() {
		return settings;
	}

}
