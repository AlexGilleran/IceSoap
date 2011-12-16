package com.alexgilleran.icesoap.xml;

import org.xmlpull.v1.XmlSerializer;

/**
 * This is a factory that allows clients of XmlElement implementations to
 * override the creation of XmlSerializer. This is necessary because using the
 * standard Android.Xml.newSerializer() method doesn't work outside an Android
 * device, even with Robolectric, and hence needs to be overridden for quick
 * unit tests.
 * 
 * @author Alex Gilleran
 * 
 */
public interface XMLSerializerFactory {

	/**
	 * Builds a new XmlSerializer implementation.
	 * 
	 * @returns new XmlSerializer implementation.
	 */
	XmlSerializer buildXmlSerializer();
}
