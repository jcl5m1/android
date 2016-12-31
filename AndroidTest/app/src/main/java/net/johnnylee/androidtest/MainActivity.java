package net.johnnylee.androidtest;

import android.app.*;
import android.content.*;
import android.location.*;
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

public class MainActivity extends Activity implements LocationListener
{
	private double lat;
	private double lng;
	private int count;
	
	@Override
	public void onLocationChanged(Location location)
	{
		// TODO: Implement this method
		
		lat = location.getLatitude();
		lng = location.getLongitude();

		voltage = 12 + rand.nextFloat();
		amphours = 300 + rand.nextFloat();
		
		String msg = "Location update: " + 
		String.valueOf(lat) + ", " + 
		String.valueOf(lng) + " - " + String.valueOf(count);
		count++;
		label.setText(msg);
		new SubmitToGoogleSheets().execute("");
	}

	@Override
	public void onStatusChanged(String p1, int p2, Bundle p3)
	{
		// TODO: Implement this method
	}

	@Override
	public void onProviderEnabled(String p1)
	{
		// TODO: Implement this method
	}

	@Override
	public void onProviderDisabled(String p1)
	{
		// TODO: Implement this method
	}


	static Random rand = new Random();
	static float voltage;
	static float amphours;

	private class SubmitToGoogleSheets extends AsyncTask<String, Integer, Long>
	{
		protected Long doInBackground(String... urls)
		{
			long totalSize = 0;
			submitData("test");
			return totalSize;
		}

		protected void onProgressUpdate(Integer... progress)
		{
		}

		protected void onPostExecute(Long result)
		{
		}


		private void submitData(String outcome)
		{
			HttpClient client = new DefaultHttpClient();
			//HttpPost post = new HttpPost("https://spreadsheets.google.com/spreadsheet/formResponse?hl=en_US&formkey=1FAIpQLSfEAIYjoQ88Jaqu6mmIMMfgDv6viLUbmkAxsXFZ2yJXCHC-VA");
			HttpPost post = new HttpPost("https://docs.google.com/forms/d/e/1FAIpQLSfEAIYjoQ88Jaqu6mmIMMfgDv6viLUbmkAxsXFZ2yJXCHC-VA/formResponse");


			List<BasicNameValuePair> results = new ArrayList<BasicNameValuePair>();
			
			results.add(new BasicNameValuePair("entry.404330614", String.valueOf(lat)));
			results.add(new BasicNameValuePair("entry.607702298", String.valueOf(lng)));
			results.add(new BasicNameValuePair("entry.563220952", String.valueOf(voltage)));
			results.add(new BasicNameValuePair("entry.488905207", String.valueOf(amphours)));
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

	private TextView label;
	private LocationManager locationManager;
	private String provider;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		label = (TextView) findViewById(R.id.mainTextView1);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		count = 0;
		// default
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		
		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 50, this);
		
		final Button button = (Button) findViewById(R.id.mainButton1);
		button.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v)
				{
					// Perform action on click
					
					voltage = 12 + rand.nextFloat();
					amphours = 300 + rand.nextFloat();
	
					//new SubmitToGoogleSheets().execute("");
					String msg = "Values : " + String.valueOf(voltage) + ", " + String.valueOf(amphours);
					//Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
					 msg += "\nLocation: " + String.valueOf(lat) + ", " + String.valueOf(lng);
	
					label.setText(msg);
				}

			});

	}
}
