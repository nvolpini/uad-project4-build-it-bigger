package nvolpini.jokes.app;

import android.content.Context;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import nvolpini.jokes.backend.jokeApi.JokeApi;

/**
 * Created by neimar on 06/02/17.
 */

public class JokerAsyncTask extends AsyncTask<Void, Void, String> {
	private static JokeApi myApiService = null;

	private final Context context;

	private final TaskListener listener;

	public JokerAsyncTask(Context context, TaskListener listener) {
		this.context = context;
		this.listener = listener;
	}

	private Exception error = null;

	public interface TaskListener {
		void onComplete(String joke, Exception error);
	}

	@Override
	protected String doInBackground(Void... params) {
		if(myApiService == null) {  // Only do this once
			JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
					new AndroidJsonFactory(), null)
					// options for running against local devappserver
					// - 10.0.2.2 is localhost's IP address in Android emulator
					// - turn off compression when running against local devappserver
					//.setRootUrl("http://10.0.2.2:8080/_ah/api/")
					.setRootUrl("http://192.168.2.53:8080/_ah/api/")
					.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
						@Override
						public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
							abstractGoogleClientRequest.setDisableGZipContent(true);
						}
					});
			// end options for devappserver

			myApiService = builder.build();
		}


		try {
			return myApiService.tellJoke().execute().getJoke();
		} catch (IOException e) {
			error = e;
			return e.getMessage();
		}
	}

	@Override
	protected final void onPostExecute(String result) {

			listener.onComplete(result,error);

	};

	@Override
	protected void onCancelled() {
		super.onCancelled();
		error = new InterruptedException("AsyncTask cancelled");
		listener.onComplete(null, error);
	}

}