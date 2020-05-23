module com.guicedee.guicedservlets.requestscoped {
	requires com.google.guice;
	requires java.servlet;
	requires com.google.guice.extensions.persist;
	requires com.guicedee.guicedinjection;
	requires com.guicedee.guicedservlets;
	requires com.guicedee.guicedpersistence;
	requires java.logging;
	requires com.guicedee.logmaster;
	requires java.validation;

	exports com.guicedee.guicedservlets.requestscoped;

	provides com.guicedee.guicedservlets.services.IGuiceSiteBinder with com.guicedee.guicedservlets.requestscoped.SiteRequestScopedFilterBinder;

}
