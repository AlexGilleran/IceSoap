package com.alexgilleran.icesoap.xpath;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alexgilleran.icesoap.xpath.elements.XPathElement;

/**
 * Akin to a map that stores objects against an XPath value. Lookup logic is
 * according to XPath matching - i.e. if a object is stored in the repository
 * against the xpath <code>/this/is/an/xpath</code> and <code>get</code> is
 * executed with the XPathElement <code>//xpath</code>, the stored object will
 * be returned. This is achieved via simple mapping against
 * {@link XPathElement#getName()}, then confirmation via
 * {@link XPathElement#matches(XPathElement)}.
 * 
 * This means that retrieval of objects from the repository is fastest using
 * smaller numbers of shorter xpaths with different names. Retrieval when the
 * repository is filled with a large number of xpaths with the same names but
 * different predicates will slow retrieval down.
 * 
 * @author Alex Gilleran
 * 
 * @param <T>
 *            The object to store against xpaths - used for {@link Field} by
 *            parsers, but can be anything.
 */
public class XPathRepository<T> {

	/**
	 * Holds an index of XPathElements against their names to facilitate faster
	 * lookups
	 */
	private Map<String, Set<XPathElement>> lookupMap = new HashMap<String, Set<XPathElement>>();
	/** Holds the values stored against XPathElements */
	private Map<XPathElement, T> valueMap = new HashMap<XPathElement, T>();

	/**
	 * Gets the object stored against the supplied XPathElement.
	 * 
	 * @param key
	 *            The element to retrieve an object for.
	 * @return The object stored against the supplied effort if one is found,
	 *         otherwise null.
	 */
	public T get(XPathElement key) {
		XPathRecord<T> record = getFullRecord(key);

		if (record != null) {
			return record.getValue();
		} else {
			return null;
		}
	}

	public XPathRecord<T> getFullRecord(XPathElement key) {
		// Look for the set of elements with this name
		Set<XPathElement> possibleElements = lookupMap.get(key.getName());

		if (possibleElements != null) {
			// If there's a set of elements here, loop through them and return
			// the first one that matches the passed XPath

			for (XPathElement possElement : possibleElements) {
				if (possElement.matches(key)) {
					return new XPathRecord<T>(possElement,
							valueMap.get(possElement));
				}
			}
		}

		return null;
	}

	/**
	 * Stores an object in the repository against the specified
	 * {@link XPathElement}.
	 * 
	 * @param key
	 *            The {@link XPathElement} to use as a key.
	 * @param value
	 *            The object to store.
	 */
	public void put(XPathElement key, T value) {
		valueMap.put(key, value);

		Set<XPathElement> existingSet = lookupMap.get(key.getName());

		if (existingSet == null) {
			lookupMap.put(key.getName(), newElementSet(key));
		} else {
			existingSet.add(key);
		}
	}

	/**
	 * Creates a new set of XPathElements to be stored against a name, and
	 * places the supplied element within it.
	 * 
	 * @param element
	 *            The first element of the new set.
	 * @return The created set.
	 */
	private Set<XPathElement> newElementSet(XPathElement element) {
		Set<XPathElement> elementSet = new HashSet<XPathElement>();
		elementSet.add(element);
		return elementSet;
	}

	public static class XPathRecord<E> {
		private E value;
		private XPathElement key;

		public XPathRecord(XPathElement key, E value) {
			this.key = key;
			this.value = value;
		}

		public E getValue() {
			return value;
		}

		public XPathElement getKey() {
			return key;
		}
	}
}
