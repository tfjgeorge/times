package fr.mines.times;

import java.io.IOException;
import java.util.Collection;

import fr.mines.times.RATPBridge.RATPDirection;
import fr.mines.times.RATPBridge.RATPLine;
import fr.mines.times.RATPBridge.RATPStation;

import android.content.Context;
import android.database.Cursor;

public class RATPContent {
	private DataBaseHelper db_helper;

	public RATPContent(Context context) {
		db_helper = new DataBaseHelper(context);

		try {
			db_helper.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		db_helper.openDataBase();
	}

	public Collection<RATPStation> get_stations(RATPDirection direction) {

		String selection = "direction_id=" + direction.direction_id;

		Cursor cursor = db_helper.getReadableDatabase().query(
				"station_network", null, selection, null, null, null, null);
		while (cursor.moveToNext()) {
			System.out.println(cursor.getString(3));
		}

		return null;
	}

}
