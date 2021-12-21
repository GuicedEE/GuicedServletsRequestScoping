package com.guicedee.guicedservlets.requestscoped;

import com.google.inject.*;
import com.google.inject.persist.*;
import com.guicedee.guicedinjection.*;
import jakarta.servlet.Filter;
import jakarta.servlet.*;

import java.io.*;
import java.lang.annotation.*;
import java.util.logging.*;

/**
 * Apply this filter to enable the HTTP Request unit of work and to have guice-persist manage the
 * lifecycle of active units of work. The filter automatically starts and stops the relevant {@link
 * PersistService} upon {@link jakarta.servlet.Filter#init(jakarta.servlet.FilterConfig)} and {@link
 * jakarta.servlet.Filter#destroy()} respectively.
 *
 * <p>To be able to use the open session-in-view pattern (i.e. work per request), register this
 * filter <b>once</b> in your Guice {@code ServletModule}. It is important that you register this
 * filter before any other filter.
 *
 * <p>For multiple providers, you should register this filter once per provider, inside a private
 * module for each persist module installed (this must be the same private module where the specific
 * persist module is itself installed).
 *
 * <p>Example configuration:
 *
 * <pre>{@code
 * public class MyModule extends ServletModule {
 *   public void configureServlets() {
 *     filter("/*").through(PersistFilter.class);
 *
 *     serve("/index.html").with(MyHtmlServlet.class);
 *     // Etc.
 *   }
 * }
 * }</pre>
 *
 * <p>This filter is thread safe and allows you to create injectors concurrently and deploy multiple
 * guice-persist modules within the same injector, or even multiple injectors with persist modules
 * withing the same JVM or web app.
 *
 * <p>This filter requires the Guice Servlet extension.
 *
 * @author Dhanji R. Prasanna (dhanji@gmail.com)
 */
@Singleton
public final class PersistFilter
		implements Filter
{
	private Key<UnitOfWork> unitOfWorkKey;
	private Key<PersistService> persistServiceKey;
	private Boolean started = null;
	
	public PersistFilter(Class<? extends Annotation> annotation)
	{
		unitOfWorkKey = Key.get(UnitOfWork.class, annotation);
		persistServiceKey = Key.get(PersistService.class, annotation);
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		try
		{
			GuiceContext.get(persistServiceKey)
			            .start();
			started = true;
		}
		catch (Exception e)
		{
			Logger.getLogger(getClass().getName())
			      .log(Level.SEVERE, "Unable to start persist service for servlet call", e);
		}
	}
	
	@Override
	public void doFilter(
			ServletRequest servletRequest,
			ServletResponse servletResponse,
			FilterChain filterChain)
			throws IOException, ServletException
	{
		if (started)
		{
			GuiceContext.get(unitOfWorkKey)
			            .begin();
		}
		try
		{
			filterChain.doFilter(servletRequest, servletResponse);
		}
		finally
		{
			if (started)
			{
				GuiceContext.get(unitOfWorkKey)
				            .end();
			}
		}
	}
	
	@Override
	public void destroy()
	{
		if (started)
		{
			GuiceContext.get(persistServiceKey)
			            .stop();
		}
	}
}
