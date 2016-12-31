package net.johnnylee.androidtest;

import android.app.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import org.apache.http.client.*;
import org.apache.http.client.entity.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.message.*;


public class MainActivity extends Activity 
{

	static Random rand = new Random();
	static float voltage;
	static float amphours;

	private class SubmitTask extends AsyncTask<String, Integer, Long>
	{
		protected Long doInBackground(String... urls)
		{
			long totalSize = 0;
			submitVote("test");
			return totalSize;
		}

		protected void onProgressUpdate(Integer... progress)
		{
		}

		protected void onPostExecute(Long result)
		{
		}


		private void submitVote(String outcome)
		{
			HttpClient client = new DefaultHttpClient();
			//HttpPost post = new HttpPost("https://spreadsheets.google.com/spreadsheet/formResponse?hl=en_US&formkey=1FAIpQLSfEAIYjoQ88Jaqu6mmIMMfgDv6viLUbmkAxsXFZ2yJXCHC-VA");
			HttpPost post = new HttpPost("https://docs.google.com/forms/d/e/1FAIpQLSfEAIYjoQ88Jaqu6mmIMMfgDv6viLUbmkAxsXFZ2yJXCHC-VA/formResponse");


			List<BasicNameValuePair> results = new ArrayList<BasicNameValuePair>();
			results.add(new BasicNameValuePair("entry.563220952", Float.toString(voltage)));
			results.add(new BasicNameValuePair("entry.488905207", Float.toString(amphours)));
			Log.e("YOUR_TAG", "Preparing POST");
			try
			{
				post.setEntity(new UrlEncodedFormEntity(results));
			}
			catch (UnsupportedEncodingException e)
			{
				// Auto-generated catch block
				Log.e("YOUR_TAG", "An error has occurred", e);
			}
			try
			{
				client.execute(post);
			}
			catch (ClientProtocolException e)
			{
				// Auto-generated catch block
				Log.e("YOUR_TAG", "client protocol exception", e);
			}
			catch (IOException e)
			{
				// Auto-generated catch block
				Log.e("YOUR_TAG", "io exception", e);
			}


		}
	}


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		final Button button = (Button) findViewById(R.id.mainButton1);
		button.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v)
				{
					// Perform action on click

					voltage = 12 + rand.nextFloat();
					amphours = 300 + rand.nextFloat();

					new SubmitTask().execute("");
					String msg = "Values : " + Float.toString(voltage) + ", " + Float.toString(amphours);
					Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
				}

			});

	}
}
