package com.sbplatform.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @author  黄世民
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SbEntityTitle {
	  String name();
}
