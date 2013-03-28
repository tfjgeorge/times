package fr.mines.times;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.mines.times.RATPBridge.RATPDirection;

import android.content.Context;
import android.database.Cursor;

public class RATPContent {
	private DataBaseHelper db_helper;

	public static class RATPStation {
		public final String name;
		public final int station_id;
		public final int direction_id;
		public final int network_id;

		public RATPStation(String name, int station_id, int direction_id,
				int network_id) {
			this.name = name;
			this.station_id = station_id;
			this.direction_id = direction_id;
			this.network_id = network_id;
		}

		public void display() {
			System.out.println(name + "|" + station_id + "|" + direction_id
					+ "|" + network_id);
		}
	}

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
		List<RATPStation> output = new ArrayList<RATPStation>();

		Cursor cursor = db_helper.getReadableDatabase().query(
				"station_network", null, selection, null, null, null, null);
		while (cursor.moveToNext()) {
			output.add(new RATPStation(cursor.getString(3), cursor.getInt(1),
					cursor.getInt(4), cursor.getInt(1)));
		}

		return output;
	}

}
