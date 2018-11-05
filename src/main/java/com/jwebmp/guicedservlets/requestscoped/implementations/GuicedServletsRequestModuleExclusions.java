package com.jwebmp.guicedservlets.requestscoped.implementations;

import com.jwebmp.guicedinjection.interfaces.IGuiceScanJarExclusions;
import com.jwebmp.guicedinjection.interfaces.IGuiceScanModuleExclusions;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

public class GuicedServletsRequestModuleExclusions
		implements IGuiceScanModuleExclusions<GuicedServletsRequestModuleExclusions>,
				           IGuiceScanJarExclusions<GuicedServletsRequestModuleExclusions>
{
	@Override
	public @NotNull Set<String> excludeJars()
	{
		Set<String> strings = new HashSet<>();
		strings.add("guiced-servlets-request-scoper-*");
		return strings;
	}

	@Override
	public @NotNull Set<String> excludeModules()
	{
		Set<String> strings = new HashSet<>();
		strings.add("com.jwebmp.guicedservlets.requestscoped");

		strings.add("com.google.guice");
		strings.add("javax.servlet.api");
		strings.add("com.google.guice.extensions.persist");
		strings.add("com.jwebmp.guicedinjection");
		strings.add("com.jwebmp.guicedservlets");
		strings.add("com.jwebmp.guicedpersistence");
		strings.add("java.logging");
		strings.add("com.jwebmp.logmaster");
		strings.add("java.validation");


		return strings;
	}
}
