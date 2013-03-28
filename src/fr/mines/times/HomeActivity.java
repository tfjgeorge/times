package fr.mines.times;

import java.io.IOException;

import fr.mines.times.RATPBridge.RATPDirection;
import fr.mines.times.RATPBridge.RATPTime;
import fr.mines.times.RATPContent.RATPStation;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;

public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		RATPContent ratp_content = new RATPContent(this);
		RATPDirection ligne1 = new RATPDirection(1);
		for (RATPStation s : ratp_content.get_stations(ligne1)) {
			s.display();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
