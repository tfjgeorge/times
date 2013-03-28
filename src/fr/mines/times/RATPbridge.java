package fr.mines.times;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.os.AsyncTask;
import android.util.Xml;

public class RATPBridge {

	public static class RATPLine {
	}

	public static class RATPDirection {
		public final int direction_id;

		public RATPDirection(int direction_id) {
			this.direction_id = direction_id;
		}
	}

	public static class RATPTime {
		public final String direction;
		public final int delay;
		public final String message;

		public RATPTime(String direction, int delay) {
			this.direction = direction;
			this.delay = delay;
			this.message = "";
		}

		private RATPTime(String direction, String message) {
			this.direction = direction;
			this.delay = -1;
			this.message = message;
		}

		public void display() {
			System.out.println(this.direction + ": "
					+ (this.delay != -1 ? this.delay : this.message));
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
	}

	public static Collection<RATPTime> get_times(RATPStation station) {

		try {
			URL url = new URL(
					"http://ratp-bridge.fabernovel.com/ratp.schedule?reseau=1&direction=80443&station=344832");
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());

			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(in, null);
			parser.nextTag();

			List<RATPTime> times = new ArrayList<RATPTime>();

			while (parser.next() != XmlPullParser.END_DOCUMENT) {
				if (parser.getEventType() != XmlPullParser.START_TAG) {
					continue;
				}
				String name = parser.getName();
				if (name.equals("item")) {
					List<String> text_lines = new ArrayList<String>();

					while (parser.next() != XmlPullParser.END_TAG) {
						parser.next();
						if (parser.getEventType() == XmlPullParser.TEXT) {
							String texte = parser.getText();
							text_lines.add(texte);
							parser.next();
						} else {
							text_lines.add("");
						}
					}
					try {
						int delay = Integer.parseInt(text_lines.get(1)
								.substring(0, text_lines.get(1).length() - 3));
						times.add(new RATPTime(text_lines.get(0), delay));
					} catch (NumberFormatException e) {
						times.add(new RATPTime(text_lines.get(0), text_lines
								.get(1) + text_lines.get(2)));
					}
				}
			}
			return times;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
