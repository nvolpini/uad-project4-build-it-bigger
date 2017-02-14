package nvolpini.jokes.api;

import java.util.Random;

public class Joker {

	private static String[] jokes = new String[]{
		"Knock, Knock... who is there? Nobody!"
		,"I'm not funny!"
		,"How many americans to replace lamp? Too many!"
	};

	public static String tellJoke() {

		int jokePos = new Random().nextInt(jokes.length);

		return jokes[jokePos];
	}
}
