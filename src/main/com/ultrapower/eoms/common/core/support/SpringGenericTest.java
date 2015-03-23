package com.ultrapower.eoms.common.core.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;


public class SpringGenericTest extends
		AbstractTransactionalDataSourceSpringContextTests {
	protected transient final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	protected String[] getConfigLocations() {
		this.setDefaultRollback(rollBack());
		return getConfig();
	}

	protected String[] getConfig() {
		setAutowireMode(AUTOWIRE_BY_NAME);
		return new String[] { "classpath*:spring/*.xml",
				"classpath*:spring/test/*.xml" };
	}

	protected void initCache() {
	}

	protected boolean rollBack() {
		return false;
	}

}
