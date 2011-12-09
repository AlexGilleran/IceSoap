package com.alexgilleran.icesoap.parser;
//package com.ibm.au.pcm.android.soap.parser;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserException;
//
//import com.ibm.au.pcm.android.soap.observer.SOAPObserver;
//
//public class OldListParser<T> extends BaseParser<List<T>> {
//	private Parser<T> parser;
//
//	public OldListParser(String rootElementName, Parser<T> parser) {
//		super(rootElementName);
//
//		this.parser = parser;
//	}
//
//	@Override
//	protected List<T> parseTag(String tagName, XPathXmlPullParser pullParser,
//			List<T> listSoFar) throws XmlPullParserException, IOException {
//
//		T t = parser.parse(pullParser);
//
//		if (t != null) {
//			listSoFar.add(t);
//		}
//
//		return listSoFar;
//	}
//
//	@Override
//	public List<T> initializeParsedObject() {
//		return new ArrayList<T>();
//	}
//
//	public void addItemListener(SOAPObserver<T> listener) {
//		parser.addListener(listener);
//	}
//
//	public void removeItemListener(SOAPObserver<T> listener) {
//		parser.removeListener(null);
//	}
//}
