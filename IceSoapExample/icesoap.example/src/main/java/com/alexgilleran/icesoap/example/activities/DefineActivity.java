package com.alexgilleran.icesoap.example.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alexgilleran.icesoap.example.R;
import com.alexgilleran.icesoap.example.model.Definition;
import com.alexgilleran.icesoap.examples.envelopes.DefineWordEnvelope;
import com.alexgilleran.icesoap.exception.SOAPException;
import com.alexgilleran.icesoap.observer.SOAPObserver;
import com.alexgilleran.icesoap.request.Request;
import com.alexgilleran.icesoap.request.impl.RequestImpl;

public class DefineActivity extends Activity {
	public static final String DICT_ID_KEY = "dictidkey";
	public static final String DICT_NAME_KEY = "dictnamekey";
	private String dictionaryNamePrefix;

	private TextView dictNameTextView;
	private TextView definitionTextView;
	private EditText wordEditText;
	private Button retrieveButton;

	private String dictionaryId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.define);

		dictNameTextView = (TextView) findViewById(R.id.define_dictionary_name_textview);
		definitionTextView = (TextView) findViewById(R.id.define_definition_textview);
		wordEditText = (EditText) findViewById(R.id.define_word_edittext);

		retrieveButton = (Button) findViewById(R.id.define_retrieve_button);
		retrieveButton.setOnClickListener(goButtonListener);

		dictionaryId = getIntent().getStringExtra(DICT_ID_KEY);
		dictionaryNamePrefix = getString(R.string.define_dictionary_name_prefix);

		setDictionaryName(getIntent().getStringExtra(DICT_NAME_KEY));
	}

	private void setDictionaryName(String name) {
		dictNameTextView.setText(dictionaryNamePrefix + name);
	}

	private OnClickListener goButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			retrieveDefinition(wordEditText.getText().toString());
		}
	};

	private void retrieveDefinition(String word) {
		Request<Definition> request = new RequestImpl<Definition>(
				"http://services.aonaware.com/DictService/DictService.asmx",
				new DefineWordEnvelope(dictionaryId, word), Definition.class,
				"http://services.aonaware.com/webservices/DefineInDict");

		request.registerObserver(definitionObserver);

		request.execute();
	}

	private SOAPObserver<Definition> definitionObserver = new SOAPObserver<Definition>() {
		@Override
		public void onCompletion(Request<Definition> request) {
			String definition;

			if (request.getResult() != null) {
				definition = request.getResult().getWordDefinition();
			} else {
				definition = "(no result found)";
			}

			definitionTextView.setText(definition);
		}

		@Override
		public void onException(Request<Definition> request, SOAPException e) {
			throw new RuntimeException(e);
		}
	};
}
