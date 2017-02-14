package nvolpini.jokes.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

import nvolpini.jokes.api.Joker;

/**
 * An endpoint class we are exposing
 */
@Api(
		name = "jokeApi",
		version = "v1",
		resource = "joke",
		namespace = @ApiNamespace(
				ownerDomain = "backend.jokes.nvolpini",
				ownerName = "backend.jokes.nvolpini",
				packagePath = ""
		)
)
public class JokeEndpoint {

	private static final Logger logger = Logger.getLogger(JokeEndpoint.class.getName());

	/**
	 * This method gets the <code>Joke</code> object.
	 *
	 * @return The <code>Joke</code> .
	 */
	@ApiMethod(name = "tellJoke")
	public Joke getJoke() {
		logger.info("Calling tellJoke method");

		Joke j = new Joke();
		j.setJoke(Joker.tellJoke());

		return j;
	}

}