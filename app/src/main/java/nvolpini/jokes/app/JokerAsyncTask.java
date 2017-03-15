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

public abstract class JokerAsyncTask extends AsyncTask<Context, Void, String> {
	private static JokeApi myApiService = null;

	private Context context;

	private TaskListener listener;
	private Exception error = null;

	public interface TaskListener {
		void onComplete(String joke, Exception e);
	}

	@Override
	protected String doInBackground(Context... params) {
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

		context = params[0];
		//String name = params[0].second;

		try {
			return myApiService.tellJoke().execute().getJoke();
		} catch (IOException e) {
			error = e;
			return e.getMessage();
		}
	}

	@Override
	protected final void onPostExecute(String result) {

		if (listener != null) {
			listener.onComplete(result,error);
		}

		onJoke(result);

	};

	@Override
	protected void onCancelled() {
		super.onCancelled();
		error = new InterruptedException("AsyncTask cancelled");
		if (listener != null) {
			listener.onComplete(null, error);
		}
	}

	protected abstract void onJoke(String joke);

	public JokerAsyncTask setListener(TaskListener listener) {
		this.listener = listener;
		return this;
	}

}