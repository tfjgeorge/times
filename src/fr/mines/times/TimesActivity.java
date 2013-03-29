package fr.mines.times;

import java.util.ArrayList;

import fr.mines.times.RATPBridge.RATPTime;
import fr.mines.times.RATPContent.RATPStation;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TimesActivity extends Activity {

	private ListView times_listview;
	private ArrayAdapter<RATPTime> times_adapter;
	private RATPContent ratp_content;

	private class RATPAsyncCall extends
			AsyncTask<ArrayList<Integer>, ArrayList<RATPTime>, Boolean> {

		@Override
		protected Boolean doInBackground(ArrayList<Integer>... station_ids) {
			for (int station_id : station_ids[0]) {
				RATPStation station = ratp_content.get_station(station_id);
				publishProgress(RATPBridge.get_times(station));
			}
			return true;
		}

		@SuppressLint("NewApi")
		// DANGER
		@Override
		protected void onProgressUpdate(ArrayList<RATPTime>... times) {
			times_adapter.addAll(times[0]);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_times);

		ratp_content = new RATPContent(this);

		ArrayList<Integer> station_ids = this.getIntent()
				.getIntegerArrayListExtra("station_ids");

		times_listview = (ListView) findViewById(R.id.times_listview);
		times_adapter = new ArrayAdapter<RATPTime>(this,
				android.R.layout.simple_list_item_1);

		times_listview.setAdapter(times_adapter);

		new RATPAsyncCall().execute(station_ids);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.times, menu);
		return true;
	}

}
