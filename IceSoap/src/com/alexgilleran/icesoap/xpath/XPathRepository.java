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
	 * Instantiates a new {@link XPathRepository} with no data.
	 */
	public XPathRepository() {
		// zero-arg
	}

	/**
	 * Instantiates a new {@link XPathRepository}, adding the provided key and
	 * value.
	 * 
	 * @param key
	 *            They key of an initial value to add
	 * @param value
	 *            An initial value to add.
	 */
	public XPathRepository(XPathElement key, T value) {
		this.put(key, value);
	}

	/**
	 * Gets the first object stored against the supplied XPathElement.
	 * 
	 * @param key
	 *            The element to retrieve an object for.
	 * @return The object stored against the supplied element if one is found,
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

	public T remove(XPathElement key) {
		XPathRecord<T> record = getFullRecord(key);

		if (record != null) {
			valueMap.remove(record.getKey());

			Set<XPathElement> elements = lookupMap.get(record.getKey().getName());
			elements.remove(record.getKey());

			if (elements.isEmpty()) {
				lookupMap.remove(record.getKey());
			}

			return record.getValue();
		} else {
			return null;
		}
	}

	public int size() {
		return valueMap.size();
	}

	/**
	 * Returns true of an element exists within the repo that matches the
	 * supplied key.
	 * 
	 * @param key
	 *            The key to match
	 * @return Whether it's contained.
	 */
	public boolean contains(XPathElement key) {
		if (get(key) != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets all the XPathElements with values stores against them in the
	 * repository.
	 * 
	 * @return The set of XPathElements within the repository.
	 */
	public Set<XPathElement> keySet() {
		return valueMap.keySet();
	}

	/**
	 * Gets a record containing both the object stored against the supplied
	 * {@link XPathElement} and the {@link XPathElement} that it was stored
	 * against (which is usually simpler than the one used for the lookup.
	 * 
	 * @param key
	 *            The element to retrieve an object for.
	 * @return A {@link XPathRecord} object containing the object stored against
	 *         the element that the {@link XPathElement} matches as well as the
	 *         {@link XPathElement} that it was stored against.
	 */
	public XPathRecord<T> getFullRecord(XPathElement key) {
		// Look for the set of elements with this name
		Set<XPathElement> possibleElements = lookupMap.get(key.getName());

		if (possibleElements != null) {
			// If there's a set of elements here, loop through them and return
			// the first one that matches the passed XPath

			for (XPathElement possElement : possibleElements) {
				if (possElement.matches(key)) {
					return new XPathRecord<T>(possElement, valueMap.get(possElement));
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

	/**
	 * A simple object containing a value of a supplied generic type, as well as
	 * an {@link XPathElement} that it's stored against.
	 * 
	 * @author Alex Gillean
	 * 
	 * @param <E>
	 *            The type of the value to store against the
	 *            {@link XPathElement} key.
	 */
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lookupMap == null) ? 0 : lookupMap.hashCode());
		result = prime * result + ((valueMap == null) ? 0 : valueMap.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XPathRepository<?> other = (XPathRepository<?>) obj;
		if (lookupMap == null) {
			if (other.lookupMap != null)
				return false;
		} else if (!lookupMap.equals(other.lookupMap))
			return false;
		if (valueMap == null) {
			if (other.valueMap != null)
				return false;
		} else if (!valueMap.equals(other.valueMap))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return valueMap.toString();
	}

}
