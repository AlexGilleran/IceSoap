package com.alexgilleran.icesoap.example.activities;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectResource;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alexgilleran.icesoap.example.R;
import com.alexgilleran.icesoap.example.dao.DictionaryRequestFactory;
import com.alexgilleran.icesoap.example.domain.Definition;
import com.alexgilleran.icesoap.example.domain.DictionaryFault;
import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.request.Request;
import com.google.inject.Inject;

/**
 * Simple Activity to display a definition
 * 
 * @author Alex Gilleran
 * 
 */
public class DefineActivity extends RoboActivity {
	/**
	 * A word to send to the service when trying to cause a soap fault (not
	 * really important)
	 */
	private static final String SOAPFAULT_WORD = "dodgyword";
	/** A dictionary id to send to the service when trying to cause a soap fault */
	private static final String SOAPFAULT_DICT_ID = "dodgydict";

	// Tried not to use Roboguice too much for clarity, but Android makes it too
	// hard to programmatically get strings.xml values otherwise.
	@InjectResource(R.string.dialog_connection_error)
	private String CONNECTION_ERROR_MESSAGE;
	@InjectResource(R.string.dialog_soapfault)
	private String SOAP_FAULT_MESSAGE;

	/** Key of the dictionary id extra that must be passed in an intent */
	public static final String DICT_ID_KEY = "dictidkey";
	/** Key of the dictionary name extra that must be passed in an intent */
	public static final String DICT_NAME_KEY = "dictnamekey";

	/** Creates requests */
	@Inject
	private DictionaryRequestFactory requestFactory;

	/** {@link TextView} that displays the dictionary name */
	private TextView dictNameTextView;
	/** {@link TextView} that displays the definition content. */
	private TextView definitionTextView;
	/** {@link EditText} used for inputing a word to define */
	private EditText wordEditText;
	/** {@link Button} to trigger the retrieval of the definition */
	private Button retrieveButton;

	/** Name of the dictionary to look up */
	private String dictionaryNamePrefix;
	/** ID of the dictionary to look up */
	private String dictionaryId;

	/** Stores a SOAPFault if one is encountered */
	private DictionaryFault soapFault;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.define);

		// Note that I've grabbed view elements the annoying native Android way
		// rather than the smooth Roboguice way - this is just so that things
		// are less confusing for those unfamiliar with Roboguice
		dictNameTextView = (TextView) findViewById(R.id.define_dictionary_name_textview);
		definitionTextView = (TextView) findViewById(R.id.define_definition_textview);
		wordEditText = (EditText) findViewById(R.id.define_word_edittext);

		retrieveButton = (Button) findViewById(R.id.define_retrieve_button);
		retrieveButton.setOnClickListener(goButtonListener);

		Button soapFaultButton = (Button) findViewById(R.id.define_soapfault_button);
		soapFaultButton.setOnClickListener(soapFaultButtonListener);

		dictionaryId = getIntent().getStringExtra(DICT_ID_KEY);
		dictionaryNamePrefix = getString(R.string.define_dictionary_name_prefix);

		setDictionaryName(getIntent().getStringExtra(DICT_NAME_KEY));
	}

	/**
	 * Sets the name of the dictionary being used in the UI
	 * 
	 * @param name
	 *            The name to use
	 */
	private void setDictionaryName(String name) {
		dictNameTextView.setText(dictionaryNamePrefix + name);
	}

	/**
	 * Invokes the web service to retrieve a dictionary definition
	 */
	private OnClickListener goButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			retrieveDefinition(wordEditText.getText().toString());
		}
	};

	/**
	 * Invokes the web service to post a bad request, leading to a soap fault
	 * (for science).
	 */
	private OnClickListener soapFaultButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			setProgressBarIndeterminateVisibility(true);
			requestFactory.getDefinition(SOAPFAULT_DICT_ID, SOAPFAULT_WORD)
					.execute(definitionObserver);
		}
	};

	/**
	 * Activates the progress spinner in the corner of the window and invokes
	 * the SOAP request for the definition.
	 * 
	 * @param word
	 *            The word to define.
	 */
	private void retrieveDefinition(String word) {
		setProgressBarIndeterminateVisibility(true);
		requestFactory.getDefinition(dictionaryId, word).execute(
				definitionObserver);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Creates a dialog in the case of connection problems - this is the only
	 * dialog for this activity and hence the id is not used.
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		String message = null;

		if (soapFault == null) {
			message = CONNECTION_ERROR_MESSAGE;
		} else {
			message = SOAP_FAULT_MESSAGE + soapFault.getErrorMessage();
			soapFault = null;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message).setPositiveButton(
				R.string.dialog_button_ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		return builder.create();
	}

	/**
	 * Listens for responses from the dictionary service - when they come
	 * through, it displays the definition.
	 */
	private SOAPObserver<Definition, DictionaryFault> definitionObserver = new SOAPObserver<Definition, DictionaryFault>() {

		@Override
		public void onCompletion(Request<Definition, DictionaryFault> request) {
			String definition;

			// If the request comes back with nothing, display an error message,
			// otherwise display the definition.
			if (request.getResult() != null) {
				definition = request.getResult().getWordDefinition();
			} else {
				definition = getString(R.string.define_no_result_message);
			}
			definitionTextView.setText(definition);

			setProgressBarIndeterminateVisibility(false);
		}

		@Override
		public void onException(Request<Definition, DictionaryFault> request,
				SOAPException e) {
			// Log the exception and show an error dialog.
			Log.e(DefineActivity.class.getSimpleName(), e.getMessage(), e);

			soapFault = request.getSOAPFault();

			showDialog(0);
		}
	};
}
