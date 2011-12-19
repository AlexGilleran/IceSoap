package com.alexgilleran.icesoap.misc.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.hamcrest.internal.ArrayIterator;
import org.jaxen.saxpath.SAXPathException;
import org.jaxen.saxpath.XPathHandler;
import org.jaxen.saxpath.XPathReader;
import org.jaxen.saxpath.helpers.XPathReaderFactory;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This test doesn't actually test anything - it's mainly useful for seeing what
 * Jaxen will do with any given XPath.
 * 
 * Put your XPath into XPATH_TO_PARSE and then a list of the invoked methods
 * with their values will appear in the console.
 * 
 * @author Alex Gilleran
 * 
 */
public class JaxenTest {
	private static final String XPATH_TO_PARSE = "example1/example2";

	@Test
	@Ignore("Comment this line when it's actually necessary to run this test")
	public void test() throws SAXPathException {
		XPathReader reader = XPathReaderFactory.createReader();
		MethodTracker methodTracker = new MethodTracker();

		XPathHandler handler = (XPathHandler) Proxy.newProxyInstance(
				XPathHandler.class.getClassLoader(),
				new Class<?>[] { XPathHandler.class }, methodTracker);
		reader.setXPathHandler(handler);

		reader.parse(XPATH_TO_PARSE);

		System.out.print(methodTracker.getLog());
	}

	private class MethodTracker implements InvocationHandler {
		private StringBuilder log = new StringBuilder("=====\n");

		private void log(String methodName, Object... arguments) {
			log.append(methodName).append("(");

			if (arguments != null) {
				ArrayIterator iterator = new ArrayIterator(arguments);

				while (iterator.hasNext()) {
					log.append(iterator.next());

					if (iterator.hasNext()) {
						log.append(", ");
					}
				}
			}

			log.append(")\n");
		}

		public String getLog() {
			return log.toString();
		}

		@Override
		public Object invoke(Object arg0, Method arg1, Object[] arg2)
				throws Throwable {
			log(arg1.getName(), arg2);

			return null;
		}
	}
}
