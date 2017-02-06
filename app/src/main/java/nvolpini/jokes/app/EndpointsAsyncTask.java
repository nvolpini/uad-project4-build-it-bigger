package nvolpini.jokes.app;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import nvolpini.jokes.backend.myApi.MyApi;

/**
 * Created by neimar on 06/02/17.
 */

public abstract class EndpointsAsyncTask  extends AsyncTask<Pair<Context, String>, Void, String> {
	private static MyApi myApiService = null;
	private Context context;

	@Override
	protected String doInBackground(Pair<Context, String>... params) {
		if(myApiService == null) {  // Only do this once
			MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
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

		context = params[0].first;
		String name = params[0].second;

		try {
			return myApiService.sayHi(name).execute().getData();
		} catch (IOException e) {
			return e.getMessage();
		}
	}

	@Override
	protected abstract void onPostExecute(String result);
}