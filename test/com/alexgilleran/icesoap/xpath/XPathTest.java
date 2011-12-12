package com.alexgilleran.icesoap.xpath;

public class XPathTest {

	protected String[] getTestXPaths() {
		return new String[] { //
				"/example",//
				"/example1/example2",//
				"/example1/example2/example3",//
				"//example1",//
				"//example1/example2",//
				"//example1/example2/example3",//
				"/example1//example2/example3",//
				"/example1/example2//example3",//
				"/example1/@attribute",//
				"/example1/example2/@attribute",//
				"/example1//example2/@attribute",//
				"/example1[@predicate=\"value\"]",//
				"/example1/example2[@predicate=\"value\"]" };
	}
}
