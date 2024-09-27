package io.toolisticon.pogen4selenium.runtime;

import io.toolisticon.pogen4selenium.api.PageObjectParent;

public interface ExecuteBlock <IPO extends PageObjectParent<IPO>,OPO extends PageObjectParent<OPO>>{

	OPO execute (IPO pageObject);
	
}
