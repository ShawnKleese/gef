/*
* generated by Xtext
*/
package org.eclipse.gef.dot.internal.language;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class DotRectStandaloneSetup extends DotRectStandaloneSetupGenerated{

	public static void doSetup() {
		new DotRectStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

