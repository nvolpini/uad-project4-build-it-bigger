package nvolpini.jokes.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class JokerActivity extends AppCompatActivity {

	public static final String JOKE_EXTRA = "JOKE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_joker);

		if (getIntent() != null) {

			TextView textView = (TextView) findViewById(R.id.jokeText);

			textView.setText(
				getIntent().getStringExtra(JOKE_EXTRA)
			);
		}

	}

}
