package test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Iterator;

import org.hamcrest.internal.ArrayIterator;
import org.jaxen.XPath;
import org.jaxen.expr.XPathExpr;
import org.jaxen.expr.XPathFactory;
import org.jaxen.saxpath.SAXPathException;
import org.jaxen.saxpath.XPathHandler;
import org.jaxen.saxpath.XPathReader;
import org.jaxen.saxpath.helpers.XPathReaderFactory;
import org.junit.Test;

import com.sun.org.apache.xml.internal.dtm.Axis;

public class JaxenTest {

	@Test
	public void test() throws SAXPathException {
		XPathReader reader = XPathReaderFactory.createReader();
		MethodTracker methodTracker = new MethodTracker();

		XPathHandler handler = (XPathHandler) Proxy.newProxyInstance(
				XPathHandler.class.getClassLoader(),
				new Class<?>[] { XPathHandler.class }, methodTracker);
		reader.setXPathHandler(handler);

		reader.parse("example1/example2");

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

	private class UltimateHandler implements XPathHandler {
		private MethodTracker tracker = new MethodTracker();

		@Override
		public void endAbsoluteLocationPath() throws SAXPathException {
			//
		}

		@Override
		public void endAdditiveExpr(int arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endAllNodeStep() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endAndExpr(boolean arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endCommentNodeStep() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endEqualityExpr(int arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endFilterExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endFunction() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endMultiplicativeExpr(int arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endNameStep() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endOrExpr(boolean arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endPathExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endPredicate() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endProcessingInstructionNodeStep() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endRelationalExpr(int arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endRelativeLocationPath() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endTextNodeStep() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endUnaryExpr(int arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endUnionExpr(boolean arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void endXPath() throws SAXPathException {
			System.out.println("yay");

		}

		@Override
		public void literal(String arg0) throws SAXPathException {
			System.out.println("yay");

		}

		@Override
		public void number(int arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void number(double arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startAbsoluteLocationPath() throws SAXPathException {
			// TODO Auto-generated method stub
			System.out.println("yay");
		}

		@Override
		public void startAdditiveExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startAllNodeStep(int arg0) throws SAXPathException {
			int i = Axis.ANCESTORORSELF;

		}

		@Override
		public void startAndExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startCommentNodeStep(int arg0) throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startEqualityExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startFilterExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startFunction(String arg0, String arg1)
				throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startMultiplicativeExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startNameStep(int arg0, String arg1, String arg2)
				throws SAXPathException {
			System.out.println("yay");

		}

		@Override
		public void startOrExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startPathExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startPredicate() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startProcessingInstructionNodeStep(int arg0, String arg1)
				throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startRelationalExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startRelativeLocationPath() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startTextNodeStep(int arg0) throws SAXPathException {
			System.out.println("yay");

		}

		@Override
		public void startUnaryExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startUnionExpr() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void startXPath() throws SAXPathException {
			// TODO Auto-generated method stub

		}

		@Override
		public void variableReference(String arg0, String arg1)
				throws SAXPathException {
			// TODO Auto-generated method stub

		}

	}

}
