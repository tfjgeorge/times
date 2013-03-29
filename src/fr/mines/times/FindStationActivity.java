package fr.mines.times;

import java.util.ArrayList;
import java.util.List;

import fr.mines.times.RATPContent.RATPLine;
import fr.mines.times.RATPContent.RATPStation;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

public class FindStationActivity extends Activity {

	private AutoCompleteTextView line_picker;
	private Spinner station_picker;
	private ArrayAdapter<RATPLine> line_adapter;
	private ArrayAdapter<RATPStation> station_adapter;
	private RATPContent ratp_content;
	private boolean station_picker_activated = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_station_activity);

		ratp_content = new RATPContent(this);
		List<RATPLine> lines = ratp_content.get_lines(1);

		line_picker = (AutoCompleteTextView) findViewById(R.id.line_picker);
		station_picker = (Spinner) findViewById(R.id.station_picker);

		line_adapter = new ArrayAdapter<RATPLine>(this,
				android.R.layout.simple_dropdown_item_1line, lines);
		line_picker.setAdapter(line_adapter);

		station_adapter = new ArrayAdapter<RATPStation>(this,
				android.R.layout.simple_dropdown_item_1line,
				new ArrayList<RATPStation>());
		station_picker.setAdapter(station_adapter);

		line_picker
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						update_stations(line_adapter.getItem(arg2));
					}
				});

		station_picker
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if (station_picker_activated) {
							return_station(station_adapter.getItem(arg2));
						} else {
							station_picker_activated = true;
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	@SuppressLint("NewApi")
	// DANGER
	private void update_stations(RATPLine line) {
		List<RATPStation> stations = ratp_content.get_stations(line);
		station_adapter.clear();
		station_adapter.addAll(stations);
		station_adapter.notifyDataSetChanged();

		station_picker_activated = false;
		
		station_picker.setVisibility(android.view.View.VISIBLE);
	}

	private void return_station(RATPStation station) {
		Intent data = new Intent();
		data.putExtra("station_id", station.station_id);
		setResult(RESULT_OK, data);
		finish();
	}

}
