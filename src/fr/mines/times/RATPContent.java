package fr.mines.times;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;

public class RATPContent {
	private DataBaseHelper db_helper;

	public static class RATPDirection {
		public final int direction_id;

		public RATPDirection(int direction_id) {
			this.direction_id = direction_id;
		}
	}

	public static class RATPLine {
		public final int line_id;
		public final int network;
		public final String name;
		public final String short_name;

		public RATPLine(int line_id, int network, String name, String short_name) {
			this.line_id = line_id;
			this.network = network;
			this.name = name;
			this.short_name = short_name;
		}

		@Override
		public String toString() {
			return this.name;
		}

	}

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

		@Override
		public String toString() {
			return name;
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

	public List<RATPStation> get_stations(RATPDirection direction) {

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

	public List<RATPStation> get_stations(RATPLine line) {
		String selection = "line=" + line.line_id;

		Cursor cursor = db_helper.getReadableDatabase().query("direction",
				null, selection, null, null, null, null);

		cursor.moveToNext();

		return get_stations(new RATPDirection(cursor.getInt(0)));
	}

	public List<RATPLine> get_lines(int network_id) {

		String selection = "network=" + network_id;
		List<RATPLine> output = new ArrayList<RATPLine>();

		Cursor cursor = db_helper.getReadableDatabase().query("line", null,
				selection, null, null, null, null);
		while (cursor.moveToNext()) {
			output.add(new RATPLine(cursor.getInt(0), cursor.getInt(5), cursor
					.getString(2), cursor.getString(3)));
		}

		return output;
	}

}
