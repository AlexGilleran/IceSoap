package com.alexgilleran.icesoap.example.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.example.R;
import com.alexgilleran.icesoap.example.model.City;
import com.alexgilleran.icesoap.examples.envelopes.GetCitiesEnvelope;
import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAPListObserver;
import com.alexgilleran.icesoap.request.ListRequest;
import com.alexgilleran.icesoap.request.Request;
import com.alexgilleran.icesoap.request.impl.ListRequestImpl;

public class CityListActivity extends Activity {
	private ArrayAdapter<City> listAdapter;
	private Button button;
	private TextView countryTextView;
	private ListView cityList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.citylist);

		button = (Button) this.findViewById(R.id.city_list_get_cities_button);
		button.setOnClickListener(listener);

		countryTextView = (TextView) this
				.findViewById(R.id.city_list_country_name_textbox);

		listAdapter = new ArrayAdapter<City>(this, R.layout.city_row,
				R.id.city_row_city_name, new ArrayList<City>());

		cityList = (ListView) this.findViewById(R.id.city_list_city_listview);
		cityList.setAdapter(listAdapter);
	}

	private OnClickListener listener = new OnClickListener() {
		public void onClick(View view) {
			SOAPEnvelope env = new GetCitiesEnvelope(countryTextView.getText());

			Log.d("lol", env.toString());

			ListRequest<City> request = new ListRequestImpl<City>(
					"http://www.webservicex.net/globalweather.asmx", env,
					City.class, "http://www.webserviceX.NET/GetCitiesByCountry");

			request.registerObserver(observer);

			request.execute();
		}
	};

	private SOAPListObserver<City> observer = new SOAPListObserver<City>() {

		public void onException(Request<List<City>> request, SOAPException e) {
			throw new RuntimeException(e);
		}

		public void onCompletion(Request<List<City>> request) {

		}

		public void onNewItem(Request<List<City>> request, City item) {
			listAdapter.add(item);
		}

	};

}
