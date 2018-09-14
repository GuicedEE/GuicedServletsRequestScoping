package com.jwebmp.guicedservlets.requestscoped;

import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.UnitOfWork;
import com.jwebmp.guicedinjection.GuiceContext;

import javax.servlet.*;
import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * Apply this filter to enable the HTTP Request unit of work and to have guice-persist manage the
 * lifecycle of active units of work. The filter automatically starts and stops the relevant {@link
 * PersistService} upon {@link javax.servlet.Filter#init(javax.servlet.FilterConfig)} and {@link
 * javax.servlet.Filter#destroy()} respectively.
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

	public PersistFilter(Class<? extends Annotation> annotation)
	{
		this.unitOfWorkKey = Key.get(UnitOfWork.class, annotation);
		this.persistServiceKey = Key.get(PersistService.class, annotation);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		GuiceContext.get(persistServiceKey)
		            .start();
	}

	@Override
	public void doFilter(
			final ServletRequest servletRequest,
			final ServletResponse servletResponse,
			final FilterChain filterChain)
			throws IOException, ServletException
	{
		GuiceContext.get(unitOfWorkKey)
		            .begin();
		try
		{
			filterChain.doFilter(servletRequest, servletResponse);
		}
		finally
		{
			GuiceContext.get(unitOfWorkKey)
			            .end();
		}
	}

	@Override
	public void destroy()
	{
		GuiceContext.get(persistServiceKey)
		            .stop();
	}
}
