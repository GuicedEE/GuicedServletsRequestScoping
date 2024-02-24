package com.guicedee.guicedservlets.requestscoped;

import com.google.inject.Module;
import com.google.inject.servlet.*;
import com.guicedee.guicedinjection.interfaces.*;
import com.guicedee.guicedpersistence.services.*;
import com.guicedee.guicedservlets.services.*;
import lombok.extern.java.*;

import java.lang.annotation.*;
import java.util.*;
import java.util.logging.*;

@Log
public class SiteRequestScopedFilterBinder extends ServletModule implements IGuiceModule<SiteRequestScopedFilterBinder>
{
	/**
	 * A set of annotations to not assign request scope transactions to
	 */
	private static final Set<Class<? extends Annotation>> excludedAnnotations = new HashSet<>();
	
	/**
	 * Method getExcludedAnnotations returns the excludedAnnotations of this SiteRequestScopedFilterBinder object.
	 * <p>
	 * A set of annotations to not assign request scope transactions to
	 *
	 * @return the excludedAnnotations (type Set Class ? extends Annotation ) of this SiteRequestScopedFilterBinder object.
	 */
	public static Set<Class<? extends Annotation>> getExcludedAnnotations()
	{
		return SiteRequestScopedFilterBinder.excludedAnnotations;
	}
	
	
	@Override
	protected void configureServlets()
	{
		SiteRequestScopedFilterBinder.log.config("Loading Request Scope Transactions");
		List<Map.Entry<Class<? extends Annotation>, com.google.inject.Module>> collect = new ArrayList(PersistenceServicesModule
				                                                                                               .getModules()
				                                                                                               .entrySet());
		if (!collect.isEmpty())
		{
			for (Map.Entry<Class<? extends Annotation>, Module> entry : collect)
			{
				PersistFilter filter = new PersistFilter(entry.getKey());
				filter("/*").through(filter);
				SiteRequestScopedFilterBinder.log.config("Request Scoped Filter Added, Initial Entry @" + entry.getKey());
				log.log(Level.CONFIG, "Started " + collect.size() + " Request Scope on Module - " + entry);
			}
		}
	}
	
	@Override
	public Integer sortOrder()
	{
		return 150;
	}
}
