/**
 * 
 */
package com.mljr.sync.task;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Ckex zha </br>
 *         2016年11月28日,下午12:16:25
 *
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MobileTask extends AbstractTask {

	public MobileTask() {
		super();
	}

	@Override
	void execute() {

	}

}
