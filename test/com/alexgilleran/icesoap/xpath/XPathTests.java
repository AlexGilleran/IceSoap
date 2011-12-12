package com.alexgilleran.icesoap.xpath;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({ XPathFactoryTest.class, XPathElementTest.class,
		SingleSlashXPathElementTest.class, DoubleSlashXPathElementTest.class,
		XPathRepositoryTest.class })
public class XPathTests {

}
