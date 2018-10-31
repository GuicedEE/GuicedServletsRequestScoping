import com.jwebmp.guicedservlets.requestscoped.SiteRequestScopedFilterBinder;
import com.jwebmp.guicedservlets.services.IGuiceSiteBinder;

module com.jwebmp.guicedservlets.requestscoped {
	exports com.jwebmp.guicedservlets.requestscoped;

	requires transitive com.jwebmp.guicedservlets;
	requires transitive com.jwebmp.guicedpersistence;


	provides IGuiceSiteBinder with SiteRequestScopedFilterBinder;

}
