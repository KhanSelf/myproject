package org.go.spring.angel.common.advice;



import org.go.spring.angel.common.exception.DataAccessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.ThrowsAdvice;


public class DataAccessThrowsAdvice implements ThrowsAdvice {

	protected final Log logger = LogFactory.getLog(this.getClass());

	public void afterThrowing(DataAccessException ex) throws Throwable {
		if (logger.isDebugEnabled()) {
			logger.debug("DataAccessException afterThrowing START");
			logger.debug("Caught: " + ex.getClass().getName());
		}

			logger.fatal(ex.getMessage());


		if (logger.isDebugEnabled()) {
			logger.debug("DataAccessException afterThrowing END");
		}
		throw ex;
	}

	public void afterThrowing(Exception ex) throws Throwable {
		if (logger.isDebugEnabled()) {
			logger.debug("Exception afterThrowing START");
			logger.debug("Caught: " + ex.getClass().getName());
		}

			logger.fatal(ex.getMessage());

		if (logger.isDebugEnabled()) {
			logger.debug("Exception afterThrowing END");
		}
		throw ex;
	}
}
