package poke.instance.planet.noise;

import java.util.Arrays;

public final class Noise {

	public static final int NOISE_SIZE = 512;

	private int[] source = { 151, 160, 137, 91, 90, 15, 131, 13, 201, 95, 96, 53, 194, 233, 7, 225, 140, 36, 103, 30,
			69, 142, 8, 99, 37, 240, 21, 10, 23, 190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203, 117,
			35, 11, 32, 57, 177, 33, 88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139,
			48, 27, 166, 77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220, 105, 92, 41, 55, 46, 245, 40,
			244, 102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200, 196, 135, 130,
			116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123, 5, 202, 38, 147, 118,
			126, 255, 82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42, 223, 183, 170, 213, 119, 248,
			152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9, 129, 22, 39, 253, 19, 98, 108, 110, 79, 113,
			224, 232, 178, 185, 112, 104, 218, 246, 97, 228, 251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241,
			81, 51, 145, 235, 249, 14, 239, 107, 49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50,
			45, 127, 4, 150, 254, 138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156,
			180 };

	private int[] random;

	public Noise() {
		randomize(0);
	}

	public Noise(int seed) {
		randomize(seed);
	}

	private void randomize(int seed) {
		random = new int[NOISE_SIZE];

		if (seed != 0) {
			// Shuffle the array using the given seed
			// Unpack the seed into 4 bytes then perform a bitwise XOR operation
			// with each byte
			byte F[] = new byte[4];
			unpackLittleUint32(seed, F);

			for (int i = 0; i < source.length; i++) {
				random[i] = source[i] ^ F[0];
				random[i] ^= F[1];
				random[i] ^= F[2];
				random[i] ^= F[3];

				random[i + NOISE_SIZE / 2] = random[i];
			}

		} else {
			for (int i = 0; i < NOISE_SIZE / 2; i++)
				random[i + NOISE_SIZE / 2] = random[i] = source[i];
		}
	}

	public byte[] unpackLittleUint32(int value, byte buffer[]) {
		if (buffer.length < 4) {
			buffer = Arrays.copyOf(buffer, 4);
		}

		buffer[0] = (byte) (value & 0x00ff);
		buffer[1] = (byte) ((value & 0xff00) >> 8);
		buffer[2] = (byte) ((value & 0x00ff0000) >> 16);
		buffer[3] = (byte) ((value & 0xff000000) >> 24);

		return buffer;
	}

	public int[] getRandom() {
		return random;
	}
}