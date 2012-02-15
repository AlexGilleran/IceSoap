package com.alexgilleran.icesoap.example.activities;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alexgilleran.icesoap.example.R;
import com.alexgilleran.icesoap.example.dao.DictionaryRequestFactory;
import com.alexgilleran.icesoap.example.domain.Dictionary;
import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAPListObserver;
import com.alexgilleran.icesoap.request.BaseRequest;
import com.alexgilleran.icesoap.soapfault.SOAP11Fault;
import com.google.inject.Inject;

/**
 * Activity that loads a list of all the potential dictionaries, allowing one to
 * be picked.
 * 
 * @author Alex Gilleran
 * 
 */
public class DictionaryListActivity extends RoboActivity {
	/** Builds requests */
	@Inject
	private DictionaryRequestFactory requestFactory;

	/** Adapter for the main list */
	private ArrayAdapter<Dictionary> dictListAdapter;
	/** ListViiw for the main list of dictionaries */
	private ListView dictListView;

	/**
	 * Sets up the activity and executes the initial request.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.dictionary_list);

		setProgressBarIndeterminateVisibility(true);

		dictListAdapter = new ArrayAdapter<Dictionary>(this,
				R.layout.dictionary_row, R.id.city_row_city_name,
				new ArrayList<Dictionary>());

		dictListView = (ListView) this
				.findViewById(R.id.city_list_city_listview);
		dictListView.setAdapter(dictListAdapter);
		dictListView.setOnItemClickListener(itemListener);

		doRequest();
	}

	/**
	 * Performs a dictionary list request - gets a request from the factory,
	 * registers {@link #soapObserver} as an observer and executes the request.
	 */
	private void doRequest() {
		requestFactory.getAllDictionaries().execute(soapObserver);
	}

	/**
	 * Launches the {@link DefineActivity}, passing the dictionary id and name.
	 * 
	 * @param dictionaryId
	 *            The dictionary id to pass.
	 * @param dictionaryName
	 *            The dictionary name to pass.
	 */
	private void launchDefineActivity(String dictionaryId, String dictionaryName) {
		Intent i = new Intent(this, DefineActivity.class);

		i.putExtra(DefineActivity.DICT_ID_KEY, dictionaryId);
		i.putExtra(DefineActivity.DICT_NAME_KEY, dictionaryName);

		startActivity(i);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Creates a dialog in the case of connection problems - this is the only
	 * dialog for this activity and hence the id is not used.
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.dialog_connection_error).setPositiveButton(
				R.string.dialog_button_ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		return builder.create();
	}

	/**
	 * Item listener for clicks on individual dictionaries.
	 */
	private OnItemClickListener itemListener = new OnItemClickListener() {
		/**
		 * {@inheritDoc}
		 * 
		 * Gets the clicked dictionary, and opens the {@link DefineActivity} for
		 * it.
		 */
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Dictionary item = dictListAdapter.getItem(position);
			launchDefineActivity(item.getId(), item.getName());
		}
	};

	/**
	 * Adds new {@link Dictionary} objects to the list, and controls UI-related
	 * functionality such as stopping the progress bar on request completion and
	 * showing a dialog box on errors.
	 */
	private SOAPListObserver<Dictionary> soapObserver = new SOAPListObserver<Dictionary>() {
		@Override
		public void onNewItem(
				BaseRequest<List<Dictionary>, SOAP11Fault> request,
				Dictionary item) {
			dictListAdapter.add(item);

		}

		@Override
		public void onCompletion(
				BaseRequest<List<Dictionary>, SOAP11Fault> request) {
			setProgressBarIndeterminateVisibility(false);
		}

		@Override
		public void onException(
				BaseRequest<List<Dictionary>, SOAP11Fault> request,
				SOAPException e) {
			Log.e(DictionaryListActivity.class.getSimpleName(), e.getMessage(),
					e);
			showDialog(0);
		}
	};
}
