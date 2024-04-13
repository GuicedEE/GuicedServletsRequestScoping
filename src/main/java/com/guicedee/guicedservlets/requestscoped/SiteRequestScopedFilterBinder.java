package com.guicedee.guicedservlets.requestscoped;

import com.google.inject.servlet.ServletModule;
import com.guicedee.guicedinjection.interfaces.IGuiceModule;
import lombok.extern.java.Log;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

@Log
public class SiteRequestScopedFilterBinder extends ServletModule implements IGuiceModule<SiteRequestScopedFilterBinder>
{
    @Override
    protected void configureServlets()
    {
        SiteRequestScopedFilterBinder.log.config("Loading Request Scope Transactions");
        filter("/*").through(PersistFilter.class);
    }

    @Override
    public Integer sortOrder()
    {
        return 150;
    }
}
