package fr.mines.times;

import java.util.ArrayList;

import fr.mines.times.RATPContent.RATPDirection;
import fr.mines.times.RATPContent.RATPStation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoritesDB extends SQLiteOpenHelper {

	public class Favorite {
		public final ArrayList<RATPStation> stations;

		public Favorite(ArrayList<RATPStation> stations) {
			this.stations = stations;
		}

		public String toString() {
			return stations.get(0).toString();
		}
	}

	public FavoritesDB(Context context) {
		super(context, "favorites", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE favorites ("
				+ " id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ " type INTEGER DEFAULT 0," + " sorting INTEGER DEFAULT 0, "
				+ " station_ids TEXT " + ")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE favorites;");
		onCreate(db);
	}

	public ArrayList<Favorite> get_favorites(Context context) {

		RATPContent ratp_content = new RATPContent(context);

		Cursor cursor = getReadableDatabase().query("favorites", null, null,
				null, null, null, null);

		ArrayList<Favorite> output = new ArrayList<Favorite>();
		while (cursor.moveToNext()) {
			ArrayList<RATPStation> stations = new ArrayList<RATPStation>();
			for (String station_id : cursor.getString(3).split(":")) {
				stations.add(ratp_content.get_station(Integer
						.parseInt(station_id)));
			}
			output.add(new Favorite(stations));
		}

		return output;
	}

	public void add_favorite(int station_id) {
		ContentValues values = new ContentValues();
		values.put("station_ids",station_id);
		getWritableDatabase().insert("favorites", null, values);
	}
}
