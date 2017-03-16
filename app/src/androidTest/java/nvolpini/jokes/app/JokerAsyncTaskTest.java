package nvolpini.jokes.app;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;

/**
 * Created by neimar on 14/03/17.
 */

@RunWith(AndroidJUnit4.class)
public class JokerAsyncTaskTest implements JokerAsyncTask.TaskListener {

	CountDownLatch signal = null;
	String resultJoke = null;
	Exception error = null;


	Context context;

	@Before
	public void setUp() {
		signal = new CountDownLatch(1);
		context = InstrumentationRegistry.getContext();
	}

	@After
	public void tearDown() {
		signal.countDown();
	}

	@Test
	@UiThreadTest
	public void testTask() throws InterruptedException {

		JokerAsyncTask task = new JokerAsyncTask(context,this);
		task.execute();

		signal.await(20, TimeUnit.SECONDS);

		assertNull(error);
		assertFalse(TextUtils.isEmpty(resultJoke));
	}

	@Override
	public void onComplete(String joke, Exception error) {
		signal.countDown();
		resultJoke = joke;
		this.error = error;
	}
}
