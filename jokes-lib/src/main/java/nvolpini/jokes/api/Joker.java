package nvolpini.jokes.api;

public class Joker {
/*
	private static MyApi myApiService = null;

	public static String tellJoke() {

		if(myApiService == null) {  // Only do this once
			MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
					new AndroidJsonFactory(), null)
					// options for running against local devappserver
					// - 10.0.2.2 is localhost's IP address in Android emulator
					// - turn off compression when running against local devappserver
					.setRootUrl("http://10.0.2.2:8080/_ah/api/")
					.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
						@Override
						public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
							abstractGoogleClientRequest.setDisableGZipContent(true);
						}
					});
			// end options for devappserver

			myApiService = builder.build();
		}

		return "I'm not funny!";
	}*/

	public static String tellJoke() {
		return "I'm not funny!";
	}
}
