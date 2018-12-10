import com.jwebmp.guicedservlets.requestscoped.SiteRequestScopedFilterBinder;
import com.jwebmp.guicedservlets.services.IGuiceSiteBinder;

module com.jwebmp.guicedservlets.requestscoped {


	requires com.google.guice;
	requires javax.servlet.api;
	requires com.google.guice.extensions.persist;
	requires com.jwebmp.guicedinjection;
	requires com.jwebmp.guicedservlets;
	requires com.jwebmp.guicedpersistence;
	requires java.logging;
	requires com.jwebmp.logmaster;
	requires java.validation;

	exports com.jwebmp.guicedservlets.requestscoped;

	provides IGuiceSiteBinder with SiteRequestScopedFilterBinder;

}