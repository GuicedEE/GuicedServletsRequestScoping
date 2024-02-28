package com.guicedee.guicedservlets.requestscoped;

import com.google.inject.Module;
import com.google.inject.*;
import com.google.inject.persist.*;
import com.guicedee.client.*;
import com.guicedee.guicedpersistence.services.*;
import com.guicedee.guicedservlets.services.*;
import com.guicedee.guicedservlets.servlets.services.*;

import java.lang.annotation.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

public class OnCallScopeExit implements IOnCallScopeExit<OnCallScopeExit>
{
	@Override
	public void onScopeExit()
	{
		List<Map.Entry<Class<? extends Annotation>, com.google.inject.Module>> collect = new ArrayList(PersistenceServicesModule
				                                                                                               .getModules()
				                                                                                               .entrySet());
		if (!collect.isEmpty())
		{
			for (Map.Entry<Class<? extends Annotation>, Module> entry : collect)
			{
				Class<? extends Annotation> key = entry.getKey();
				PersistService persistServiceKey = IGuiceContext.get(Key.get(PersistService.class, key));
				UnitOfWork unitOfWork = IGuiceContext.get(Key.get(UnitOfWork.class, key));
				try
				{
					unitOfWork.end();
					try
					{
						Connection c = IGuiceContext.get(Key.get(Connection.class, key));
						c.close();
					}
					catch (ProvisionException | OutOfScopeException e)
					{
					
					}
				}
				catch (Exception e)
				{
					Logger
							.getLogger(getClass().getName())
							.log(Level.SEVERE, "Unable to start persist service for servlet call", e);
				}
			}
		}
	}
	
	/**
	 * Max value - 100
	 * <p>
	 * Try to always run this last on exit strategy
	 *
	 * @return
	 */
	@Override
	public Integer sortOrder()
	{
		return Integer.MAX_VALUE - 100;
	}
}
