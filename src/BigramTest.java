import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Test of CS 143 Assignment 5 by Martin Hock (Version 2 of 5:40 PM 5/14/2017)
 * 
 * You may only use this code as a student of Martin Hock, CS 143 Spring 2017.
 */
public class BigramTest {

	public static int test(String file, byte[] xmd5, String[] gen, String[] desired, String[] check, boolean[] truth)
			throws NoSuchAlgorithmException {
		System.out.println("Loading " + file + "...");
		String text;
		try {
			text = new String(Files.readAllBytes(Paths.get(file)));
		} catch (Exception e) {
			System.out.println("Couldn't find '" + file
					+ "'. Please place this file in the root directory of this project (next to JRE System Library, not indented).");
			return 0;
		}
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] digest = md5.digest(text.getBytes());
		// System.out.println(Arrays.toString(digest));
		if (!Arrays.equals(digest, xmd5)) {
			System.out.println("Your copy of " + file + " appears to contain errors! Please download it again.");
			return 0;
		}
		System.out.println("Loaded " + file + ". Initializing Bigram object...");
		long start = System.currentTimeMillis();
		Bigram u = new Bigram(text);
		System.out.println("Generating.");
		int genScore = 0;
		for (int i = 0; i < gen.length; i++) {
			String[] foo = u.generate(gen[i], 10);
			if (foo == null) {
				System.out.println("For start word " + gen[i] + " with 10 words, you returned a null array!");
				continue;
			}
			String gened = "";
			for (int j = 0; j < foo.length; j++) {
				gened = gened + foo[j] + (j < foo.length - 1 ? " " : "");
			}
			if (gened.equals(desired[i])) {
				genScore += 10;
			} else {
				System.out.println("For start word " + gen[i] + " with 10 words, expected '" + desired[i] + "' got '"
						+ gened + "'.");
			}
		}
		System.out.println("Checking.");
		int checkScore = 0;
		for (int i = 0; i < check.length; i++) {
			boolean ck = u.check(check[i]);
			if (ck == truth[i]) {
				checkScore += 10;
			} else {
				System.out
						.println("For phrase '" + check[i] + "' expected return value " + truth[i] + " but got " + ck);
			}
		}
		long end = System.currentTimeMillis();
		if (end - start > 2000) {
			System.out.println(
					"Your program is taking a while! It should take less than 2 seconds to complete the construction, generate, and check tasks if you correctly use Java collections as specified in the assignment.");
			if (genScore > 5)
				genScore -= 5;
			if (checkScore > 5)
				checkScore -= 5;
		}
		return genScore * 100 + checkScore;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		final byte[] dmd5 = { -55, -105, 88, 22, 1, 100, -62, 44, 46, -25, -127, 121, -8, -50, -8, 6 };
		final byte[] gmd5 = { -92, 49, 13, 111, 33, -120, -38, 62, -7, -52, -64, -94, -58, 0, -125, -59 };
		int checkScore = 0, genScore = 0;
		try {
			System.out.println("Trying 'Bob' example from homework.");
			Bigram x = new Bigram("Bob likes dogs. Bill likes cats. Jane hates dogs.");
			if (x.check("Bob likes cats.")) {
				checkScore += 10;
			} else {
				System.out.println("First check failed.");
			}
			if (!x.check("Jane likes cats.")) {
				checkScore += 10;
			} else {
				System.out.println("Second check failed.");
			}
			System.out.println("Trying 'Balloon' example from homework.");
			Bigram y = new Bigram("The balloon was red. The balloon got bigger and bigger. The balloon popped.");
			String[] g1 = y.generate("The", 3);
			if (Arrays.equals(g1, new String[] { "The", "balloon", "got" })) {
				genScore += 10;
			} else {
				System.out.println("First generate failed. Got " + Arrays.toString(g1));
			}
			String[] g2 = y.generate("popped.", 2);
			if (Arrays.equals(g2, new String[] { "popped." })) {
				genScore += 10;
			} else {
				System.out.println("Second generate failed. Got " + Arrays.toString(g2));
			}

			System.out.println("Testing with the Declaration of Independence...");
			int dscores = test("decl.txt", dmd5, new String[] { "When" },
					new String[] { "When in the most barbarous ages, and to the most" },
					new String[] { "We have Petitioned for the rectitude of this Declaration,",
							"instrument for pretended offences For abolishing" },
					new boolean[] { true, true });
			genScore += dscores / 100;
			checkScore += dscores % 100;

			System.out.println("Testing with Great Expectations...");
			int gscores = test("gexp.txt", gmd5, new String[] { "Pip", "dozen" },
					new String[] { "Pip and I had been a little while, and I",
							"dozen yards of the same time to be a little" },
					new String[] { "low leaden hue" }, new boolean[] { false });
			genScore += gscores / 100;
			checkScore += gscores % 100;

		} finally {
			System.out.println("Check: " + checkScore + " / 50");
			System.out.println("Generate: " + genScore + " / 50");
			System.out.println("Tentative total: " + (checkScore + genScore + " / 100"));
			System.out.println("Violations of the academic honesty policy may affect this score.");
		}
	}

}
