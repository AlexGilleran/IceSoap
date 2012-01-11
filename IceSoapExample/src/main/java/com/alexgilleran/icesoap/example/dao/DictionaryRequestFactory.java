package com.alexgilleran.icesoap.example.dao;

import com.alexgilleran.icesoap.example.domain.Definition;
import com.alexgilleran.icesoap.example.domain.Dictionary;
import com.alexgilleran.icesoap.request.ListRequest;
import com.alexgilleran.icesoap.request.Request;

public interface DictionaryRequestFactory {
	ListRequest<Dictionary> getAllDictionaries();

	Request<Definition> getDefinition(String dictionaryId, String word);
}
