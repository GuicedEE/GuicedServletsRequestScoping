package com.guicedee.guicedservlets.requestscoped;

import com.guicedee.guicedservlets.services.GuiceSiteInjectorModule;
import com.guicedee.guicedservlets.services.IGuiceSiteBinder;
import com.guicedee.guicedpersistence.db.DatabaseModule;
import com.guicedee.logger.LogFactory;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class SiteRequestScopedFilterBinder
		implements IGuiceSiteBinder<GuiceSiteInjectorModule>
{
	private static final Logger log = LogFactory.getLog("SiteRequestScopeFilter");
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
	public void onBind(GuiceSiteInjectorModule module)
	{
		SiteRequestScopedFilterBinder.log.config("Loading Request Scope Transactions");
		Set<Class<? extends Annotation>> workOn = DatabaseModule.getBoundAnnotations();
		workOn.removeIf(SiteRequestScopedFilterBinder.excludedAnnotations::contains);
		for (Class<? extends Annotation> aClass : workOn)
		{
			module.filter$("/*")
			      .through(new PersistFilter(aClass));
			SiteRequestScopedFilterBinder.log.config("Request Scoped Filter Added for @" + aClass.getName());
		}
	}

	@Override
	public Integer sortOrder()
	{
		return 150;
	}
}
