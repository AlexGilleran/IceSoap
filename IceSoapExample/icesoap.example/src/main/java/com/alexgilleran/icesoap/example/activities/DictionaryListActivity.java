package com.alexgilleran.icesoap.example.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alexgilleran.icesoap.envelope.SOAPEnvelope;
import com.alexgilleran.icesoap.example.R;
import com.alexgilleran.icesoap.example.model.Dictionary;
import com.alexgilleran.icesoap.examples.envelopes.GetDictionariesEnvelope;
import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAPListObserver;
import com.alexgilleran.icesoap.request.ListRequest;
import com.alexgilleran.icesoap.request.Request;
import com.alexgilleran.icesoap.request.impl.ListRequestImpl;

public class DictionaryListActivity extends Activity {
	private ArrayAdapter<Dictionary> listAdapter;
	private ListView cityList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dictionary_list);

		listAdapter = new ArrayAdapter<Dictionary>(this,
				R.layout.dictionary_row, R.id.city_row_city_name,
				new ArrayList<Dictionary>());

		cityList = (ListView) this.findViewById(R.id.city_list_city_listview);
		cityList.setAdapter(listAdapter);
		cityList.setOnItemClickListener(itemListener);

		doRequest();
	}

	private void doRequest() {
		SOAPEnvelope envelope = new GetDictionariesEnvelope();

		ListRequest<Dictionary> request = new ListRequestImpl<Dictionary>(
				"http://services.aonaware.com/DictService/DictService.asmx",
				envelope, Dictionary.class,
				"http://services.aonaware.com/webservices/DictionaryList");

		request.registerObserver(observer);

		request.execute();
	}

	private void launchDefineIntent(String dictionaryId, String dictionaryName) {
		Intent i = new Intent(this, DefineActivity.class);

		i.putExtra(DefineActivity.DICT_ID_KEY, dictionaryId);
		i.putExtra(DefineActivity.DICT_NAME_KEY, dictionaryName);

		startActivity(i);
	}

	private OnItemClickListener itemListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Dictionary item = listAdapter.getItem(position);
			launchDefineIntent(item.getId(), item.getName());
		}
	};

	private SOAPListObserver<Dictionary> observer = new SOAPListObserver<Dictionary>() {

		public void onException(Request<List<Dictionary>> request,
				SOAPException e) {
			throw new RuntimeException(e);
		}

		public void onCompletion(Request<List<Dictionary>> request) {

		}

		public void onNewItem(Request<List<Dictionary>> request, Dictionary item) {
			listAdapter.add(item);
		}
	};
}
