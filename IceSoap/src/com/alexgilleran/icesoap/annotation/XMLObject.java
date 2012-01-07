package com.alexgilleran.icesoap.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.alexgilleran.icesoap.parser.IceSoapParser;

/**
 * Annotation for POJOs intended to be automatically created by IceSoap. If this
 * annotation is used on a class, the {@link XMLField} annotation should also be
 * used on the fields within the object that need to automatically be set. Note
 * that objects annotated with {@link XMLObject} should always have a zero-arg
 * constructor or exceptions will be thrown by {@link IceSoapParser}
 * implementations.
 * 
 * @author Alex Gilleran
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface XMLObject {
	/**
	 * The XPath of the object expressed as a String.
	 */
	String value() default "";
}