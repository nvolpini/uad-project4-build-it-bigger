package nvolpini.jokes.app;

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

/**
 * Created by neimar on 14/03/17.
 */

@RunWith(AndroidJUnit4.class)
public class JokerAsyncTaskTest {

	CountDownLatch signal = null;
	String resultJoke = null;
	Exception error = null;

	@Before
	public void setUp() {
		signal = new CountDownLatch(1);
	}

	@After
	public void tearDown() {
		signal.countDown();
	}

	@Test
	@UiThreadTest
	public void testTask() throws InterruptedException {

		//TODO
		JokerAsyncTask task = new JokerAsyncTask() {
			@Override
			protected void onJoke(String joke) {
				resultJoke = joke;
				//error = e;
				signal.countDown();
			}
		};


		signal.await(20, TimeUnit.SECONDS);

		//assertNull(error);
		assertFalse(TextUtils.isEmpty(resultJoke));
	}
}
