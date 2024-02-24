import com.guicedee.guicedinjection.interfaces.*;
import com.guicedee.guicedservlets.requestscoped.*;
import com.guicedee.guicedservlets.servlets.services.*;

module com.guicedee.guicedservlets.requestscoped {
	
	requires com.guicedee.guicedservlets;
	requires com.guicedee.guicedpersistence;
	
	requires static lombok;
	
	exports com.guicedee.guicedservlets.requestscoped;
	
	provides IGuiceModule with com.guicedee.guicedservlets.requestscoped.SiteRequestScopedFilterBinder;
	provides IOnCallScopeEnter with OnCallScopeStart;
	provides IOnCallScopeExit with OnCallScopeExit;
	
}
