package com.alexgilleran.icesoap.xpath.elements;

/**
 * Represents a single element in an xpath expression. E.g. in the xpath "
 * <code>this//is/an/@xpath</code>", "<code>this</code>", "<code>is</code>", "
 * <code>an</code>" and "<code>@xpath</code>" would all be XPathElements of
 * various types.
 * 
 * Within IceSoap there is no holistic "XPath" type - rather, an XPath as a
 * whole is represented by its last element. Hence, if someone wanted to
 * determine whether one XPath expression matched another, they would find the
 * XPathElement instance representing the final element of each and execute the
 * <code>matches</code> operation.
 * 
 * @author Alex Gilleran
 * 
 */
public interface XPathElement {
	/**
	 * Gets the name of the element. E.g.
	 * 
	 * <li>"<code>/name</code>" returns "name"</li> <li>"<code>//name</code>
	 * " returns "name"</li> <li>"<code>name</code>" returns "name"</li> <li>"
	 * <code>/@name</code>" returns "@name"</li>
	 * 
	 * @return The name of the element.
	 * 
	 */
	String getName();

	/**
	 * Gets the value of a predicate with the name passed in.
	 * 
	 * @param predicateName
	 *            The name of the predicate (e.g. to get the value of the
	 *            predicate expression <code>[@name="value"]</code>,
	 *            <code>predicateName</code> would be "name").
	 * @return The value of the predicate - "value" in the example above.
	 */
	String getPredicate(String predicateName);

	/**
	 * <p>
	 * Adds a predicate to this XPath element. Currently, any predicates added
	 * will act as "and" rather than "or" when matching with another elements.
	 * </p>
	 * 
	 * <p>
	 * <b>e.g.</b> In the XPath expression
	 * <code>/xpath/example[@predname="predvalue"]</code>, "predname" would be
	 * the name passed into this operation, and "predvalue" would be the value.
	 * </p>
	 * 
	 * @param name
	 *            The name of the predicate.
	 * @param value
	 *            The value for the predicate.
	 */
	void addPredicate(String name, String value);

	/**
	 * <p>
	 * Determines whether the passed XPathElement matches all the information
	 * specified by this one.
	 * </p>
	 * <p>
	 * Note that this operation does not work the same in both directions like
	 * <code>.equals</code>. For instance, if I call
	 * <p>
	 * <p>
	 * <blockquote><code>xpathElementA.matches(xpathElementB)</code>
	 * </blockquote>
	 * </p>
	 * <p>
	 * Then I'm querying whether <code>xpathElementB</code> matches everything
	 * specified by <code>xpathElementB</code>. So if <code>xpathElementB</code>
	 * is horrendously complicated like
	 * 
	 * <blockquote>
	 * <code>/this[@pred="value"]/is/a[@anotherpred="anothervalue" and @yetanotherpred="yetanothervalue"]/very/complicated/xpath</code>
	 * </blockquote>
	 * 
	 * but <code>xpathElementA</code> is something very simple like
	 * 
	 * <blockquote> <code>//xpath</code> </blockquote>
	 * 
	 * <code>xpathElementA.matches(xpathElementB)</code> will return
	 * <code>true</code>. In this case,
	 * <code>xpathElementB.matches(xpathElementA)</code> will return
	 * <code>false</code>.
	 * </p>
	 * 
	 * @param otherElement
	 *            Another element to try to match.
	 * @return Whether the passed element matches all the information
	 *         represented by this xpath.
	 */
	boolean matches(XPathElement otherElement);

	/**
	 * Gets the element previous to this one in the XPath expression.
	 * 
	 * @return Either an XPath element, or null if this is the first expression.
	 */
	XPathElement getPreviousElement();

	/**
	 * <p>
	 * Sets the previous element to this one in the XPath expression. Note that
	 * an xpath element will retain the behaviour determined by how many slashes
	 * prefix it.
	 * </p>
	 * <p>
	 * E.g. If I have an XPath <code>//allnode/xpath</code> and set
	 * <code>allnode</code>'s previous element as <code>/element</code>, the
	 * resulting entire XPath will be <code>/element//allnode/xpath</code>.
	 * </p>
	 * <p>
	 * Note that for relative XPaths, setting a previous element changes it to a
	 * standard single-slash XPath element. E.g. if I have a relative xpath
	 * <code>allnode/xpath</code> and set <code>allnode</code>'s previous
	 * element to <code>/element</code> as above, the resulting entire XPath
	 * will become <code>/element/allnode/xpath</code>.
	 * 
	 * @param element
	 *            The element to set as the previous element to this one.
	 */
	void setPreviousElement(XPathElement element);

	/**
	 * Determines whether this element is the first in the XPath expression.
	 * E.g. in the XPath expression <code>/example/xpath</code>, invoking
	 * <code>isFirstElement()</code> on the <code>example</code> element will
	 * return true, whereas on the <code>xpath</code> node it will return false.
	 * 
	 * @return true if the first element, otherwise false.
	 */
	boolean isFirstElement();

	/**
	 * Returns the toString() representation of this XPathElement as a
	 * StringBuilder.
	 * 
	 * @see XPathElement.toString()
	 * 
	 * @return the toString() representation of this XPathElement as a
	 *         StringBuilder
	 */
	StringBuilder toStringBuilder();

	/**
	 * Returns this element and all previous elements in standard XPath
	 * notation.
	 * 
	 * @return this element and all previous elements in standard XPath
	 *         notation.
	 */
	String toString();

	/**
	 * Determines whether this is an attribute.
	 * 
	 * @return true if this XPathElement represents an attribute, otherwise
	 *         false.
	 */
	boolean isAttribute();
}