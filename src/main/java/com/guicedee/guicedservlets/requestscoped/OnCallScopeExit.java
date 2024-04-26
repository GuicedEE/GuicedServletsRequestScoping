package com.guicedee.guicedservlets.requestscoped;

import com.google.inject.Key;
import com.google.inject.OutOfScopeException;
import com.google.inject.ProvisionException;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;
import com.guicedee.client.IGuiceContext;
import com.guicedee.guicedservlets.servlets.services.IOnCallScopeExit;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OnCallScopeExit implements IOnCallScopeExit<OnCallScopeExit>
{
    @Override
    public void onScopeExit()
    {
        UnitOfWork unitOfWork = IGuiceContext.get(Key.get(UnitOfWork.class));
        try
        {
            unitOfWork.end();
        }
        catch (Exception e)
        {
            Logger
                    .getLogger(getClass().getName())
                    .log(Level.SEVERE, "Unable to start persist service for servlet call", e);
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
