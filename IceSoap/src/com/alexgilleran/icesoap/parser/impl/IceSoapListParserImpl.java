package com.alexgilleran.icesoap.parser.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.exception.XMLParsingException;
import com.alexgilleran.icesoap.parser.IceSoapListParser;
import com.alexgilleran.icesoap.parser.ItemObserver;
import com.alexgilleran.icesoap.parser.XPathPullParser;
import com.alexgilleran.icesoap.xpath.XPathRepository;
import com.alexgilleran.icesoap.xpath.elements.XPathElement;

/**
 * Implementation of {@link IceSoapListParser}
 * 
 * @author Alex Gilleran
 * 
 * @param <ListItemType>
 *            The type of each item in the list that will be parsed.
 */
public class IceSoapListParserImpl<ListItemType> extends BaseIceSoapParserImpl<List<ListItemType>> implements
		IceSoapListParser<ListItemType> {
	/**
	 * The parser to use for parsing individual items in the list. This is
	 * programmed to the implementation so it can use the
	 * {@link BaseIceSoapParserImpl}{@link #parse(XPathPullParser)} method that
	 * should only ever be used in this class.
	 */
	private BaseIceSoapParserImpl<ListItemType> parser;
	/** The XPath of the list items within the XML document */
	private XPathRepository<XPathElement> objectXPaths;
	/** A set of observers to notify of new items as they're parsed. */
	private Set<ItemObserver<ListItemType>> observers = new HashSet<ItemObserver<ListItemType>>();

	/**
	 * Instantiates a new list parser.
	 * 
	 * @param clazz
	 *            The class of the item that will be parsed as part of the list.
	 */
	public IceSoapListParserImpl(Class<ListItemType> clazz) {
		super(retrieveRootXPath(clazz));

		this.parser = new IceSoapParserImpl<ListItemType>(clazz);
	}

	/**
	 * Instantiates a new list parser.
	 * 
	 * @param clazz
	 *            The class of the item that will be parsed as part of the list.
	 * @param containingXPath
	 *            The XPath of the XML element that contains the list.
	 */
	public IceSoapListParserImpl(Class<ListItemType> clazz, XPathElement containingXPath) {
		this(clazz, containingXPath, new IceSoapParserImpl<ListItemType>(clazz, containingXPath));
	}

	/**
	 * Instantiates a new list parser. This is protected because it's generally
	 * only used when a parser encounters a {@link List} field annotated with
	 * {@link XMLField} and needs to parse it.
	 * 
	 * @param clazz
	 *            The class of the item that will be parsed as part of the list.
	 * @param containingXPath
	 *            The XPath of the XML element that contains the list.
	 * @param parser
	 */
	protected IceSoapListParserImpl(Class<ListItemType> clazz, XPathElement containingXPath,
			BaseIceSoapParserImpl<ListItemType> parser) {
		super(containingXPath);

		objectXPaths = super.retrieveRootXPath(clazz);

		this.parser = parser;
	}

	/**
	 * Registers an observer. This observer will be called every time a new item
	 * is parsed.
	 * 
	 * @param observer
	 *            The observer to register.
	 */
	public void registerItemObserver(ItemObserver<ListItemType> observer) {
		observers.add(observer);
	}

	/**
	 * Deregisters an observer. This will no longer receive parsing events.
	 * 
	 * @param the
	 *            observer to register.
	 */
	public void deregisterItemObserver(ItemObserver<ListItemType> observer) {
		observers.remove(observer);
	}

	/**
	 * Notifies the observers of this parser that a new item has been completely
	 * parsed.
	 * 
	 * @param newItem
	 *            The new item that's been parsed.
	 */
	private void notifyObservers(ListItemType newItem) {
		for (ItemObserver<ListItemType> observer : observers) {
			observer.onNewItem(newItem);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<ListItemType> initializeParsedObject() {
		return new ArrayList<ListItemType>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<ListItemType> onNewTag(XPathPullParser xmlPullParser, List<ListItemType> listSoFar)
			throws XMLParsingException {
		if ((objectXPaths == null || objectXPaths.contains(xmlPullParser.getCurrentElement()))) {
			// Figure out if the element is XSI nil before the parser skips past
			// the relevant markup in the XML
			boolean isXsiNil = xmlPullParser.isCurrentValueXsiNil();

			// Even if the value is XSI nil, we still need to use the parser to
			// skip over the XML element - however because the element is empty
			// this won't hurt performance much.
			ListItemType object = parser.parse(xmlPullParser);

			if (isXsiNil) {
				object = null;
			}

			if (object != null || isXsiNil) {
				listSoFar.add(object);
				notifyObservers(object);
			}
		}

		return listSoFar;
	}

	@Override
	protected List<ListItemType> onText(XPathPullParser pullParser, List<ListItemType> objectToModify)
			throws XMLParsingException {
		// Do nothing - list parsers aren't in the business of parsing text (at
		// least for now).
		return objectToModify;
	}
}
