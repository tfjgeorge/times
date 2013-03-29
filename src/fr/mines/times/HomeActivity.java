package fr.mines.times;

import java.io.IOException;

import fr.mines.times.RATPBridge.RATPTime;
import fr.mines.times.RATPContent.RATPStation;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity {

	public static int FindStationActivityCode = 1;

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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == FindStationActivityCode && resultCode == RESULT_OK) {
			System.out.println(data.getIntExtra("station_id", -1));
		}
	}

}
