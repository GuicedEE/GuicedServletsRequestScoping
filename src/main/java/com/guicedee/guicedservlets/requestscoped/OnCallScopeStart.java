package com.guicedee.guicedservlets.requestscoped;

import com.google.inject.Module;
import com.google.inject.*;
import com.google.inject.persist.*;
import com.guicedee.guicedinjection.*;
import com.guicedee.guicedpersistence.services.*;
import com.guicedee.guicedservlets.services.*;

import java.lang.annotation.*;
import java.util.*;
import java.util.logging.*;

public class OnCallScopeStart implements IOnCallScopeEnter<OnCallScopeStart>
{
	@Override
	public void onScopeEnter()
	{
		List<Map.Entry<Class<? extends Annotation>, com.google.inject.Module>> collect = new ArrayList(PersistenceServicesModule.getModules()
		                                                                                                                        .entrySet());
		if (!collect.isEmpty())
		{
			for (Map.Entry<Class<? extends Annotation>, Module> entry : collect)
			{
				Class<? extends Annotation> key = entry.getKey();
				PersistService persistServiceKey = GuiceContext.get(Key.get(PersistService.class, key));
				UnitOfWork unitOfWork = GuiceContext.get(Key.get(UnitOfWork.class, key));
				try
				{
					persistServiceKey.start();
					unitOfWork.begin();
				}
				catch (Exception e)
				{
					Logger.getLogger(getClass().getName())
					      .log(Level.SEVERE, "Unable to start persist service for servlet call", e);
				}
			}
		}
	}
	
	/**
	 * Integer min + 100
	 * <p>
	 * Always try to run before any other call scope action
	 *
	 * @return
	 */
	@Override
	public Integer sortOrder()
	{
		return Integer.MIN_VALUE + 100;
	}
}
