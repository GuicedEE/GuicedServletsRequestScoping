package com.guicedee.guicedservlets.requestscoped.implementations;

import com.guicedee.guicedinjection.interfaces.IGuiceScanModuleExclusions;

import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

public class GuicedServletsRequestModuleExclusions
		implements IGuiceScanModuleExclusions<GuicedServletsRequestModuleExclusions>
{
	@Override
	public @NotNull Set<String> excludeModules()
	{
		Set<String> strings = new HashSet<>();
		strings.add("com.guicedee.guicedservlets.requestscoped");

		strings.add("com.google.guice");
		strings.add("jakarta.servlet.api");
		strings.add("com.google.guice.extensions.persist");
		strings.add("com.guicedee.guicedinjection");
		strings.add("com.guicedee.guicedservlets");
		strings.add("com.guicedee.guicedpersistence");
		strings.add("java.logging");
		strings.add("com.guicedee.logmaster");
		strings.add("java.validation");


		return strings;
	}
}
