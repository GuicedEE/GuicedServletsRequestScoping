import com.guicedee.guicedservlets.requestscoped.*;
import com.guicedee.guicedservlets.services.*;

module com.guicedee.guicedservlets.requestscoped {

	requires com.guicedee.guicedservlets;
	requires com.guicedee.guicedpersistence;

	exports com.guicedee.guicedservlets.requestscoped;

	provides com.guicedee.guicedservlets.services.IGuiceSiteBinder with com.guicedee.guicedservlets.requestscoped.SiteRequestScopedFilterBinder;
	provides IOnCallScopeEnter with OnCallScopeStart;
	provides IOnCallScopeExit with OnCallScopeExit;
	
}
