package fr.mines.times;

import java.util.ArrayList;
import java.util.List;

import fr.mines.times.FavoritesDB.Favorite;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

public class HomeActivity extends Activity {

	public static int FindStationActivityCode = 1;
	private FavoritesDB favorites_db;
	private GridView favorite_gridview;
	private ArrayAdapter<Favorite> favorite_adapter;

	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_home);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		/*
		 * RATPContent ratp_content = new RATPContent(this); RATPDirection
		 * ligne1 = new RATPDirection(1); for (RATPStation s :
		 * ratp_content.get_stations(ligne1)) { s.display(); }
		 */

		Button find_station_button = (Button) findViewById(R.id.find_station_button);
		find_station_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, FindStationActivity.class);
				startActivityForResult(intent, FindStationActivityCode);
			}
		});

		favorites_db = new FavoritesDB(this);
		favorite_adapter = new ArrayAdapter<Favorite>(this,
				android.R.layout.simple_dropdown_item_1line,
				favorites_db.get_favorites(this));
		favorite_gridview = (GridView) findViewById(R.id.gridView1);

		favorite_gridview.setAdapter(favorite_adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == FindStationActivityCode && resultCode == RESULT_OK) {
			int station_id = data.getIntExtra("station_id", -1);
			ArrayList<Integer> stations = new ArrayList<Integer>();
			stations.add(station_id);
			favorites_db.add_favorite(station_id);
			display_times(stations);
		}
	}

	private void display_times(ArrayList<Integer> station_ids) {
		Intent intent = new Intent(context, TimesActivity.class);
		intent.putIntegerArrayListExtra("station_ids", station_ids);
		startActivity(intent);
	}

}
