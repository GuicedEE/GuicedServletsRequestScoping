package com.guicedee.guicedservlets.requestscoped;

import com.google.inject.Module;
import com.google.inject.*;
import com.google.inject.persist.*;
import com.guicedee.client.*;
import com.guicedee.guicedpersistence.services.*;
import com.guicedee.guicedservlets.services.*;
import com.guicedee.guicedservlets.servlets.services.*;

import java.lang.annotation.*;
import java.util.*;
import java.util.logging.*;

public class OnCallScopeStart implements IOnCallScopeEnter<OnCallScopeStart>
{
    @Override
    public void onScopeEnter(Scope scope)
    {
        PersistService persistServiceKey = IGuiceContext.get(Key.get(PersistService.class));
        UnitOfWork unitOfWork = IGuiceContext.get(Key.get(UnitOfWork.class));
        try
        {
            persistServiceKey.start();
            unitOfWork.begin();
        }
        catch (Exception e)
        {
            Logger
                    .getLogger(getClass().getName())
                    .log(Level.SEVERE, "Unable to start persist service for servlet call", e);
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
