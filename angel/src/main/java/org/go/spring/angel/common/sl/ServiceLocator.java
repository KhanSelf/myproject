package org.go.spring.angel.common.sl;

import org.go.spring.angel.common.exception.ServiceLocatorException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
	private Map<String,DataSource> cache;
	private Context envCtx;
	private static ServiceLocator instance;
	
	static{
		try{
			instance = new ServiceLocator();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private ServiceLocator() {
		try {
			envCtx = new InitialContext();
			cache = Collections.synchronizedMap(new HashMap<String,DataSource>());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceLocatorException(e.getMessage());
		}
	}
	
	public static ServiceLocator getInstance() {
		return instance;
	}
	
	public DataSource getDataSource(String jndiName) throws ServiceLocatorException {
		DataSource dataSource;
		try {
			if (cache.containsKey(jndiName)) {
				dataSource=cache.get(jndiName);
			} else {
				dataSource = (DataSource)envCtx.lookup("java:comp/env/"+jndiName);
				cache.put(jndiName, dataSource);
			}
		} catch (NamingException e) {
			throw new ServiceLocatorException(e.getMessage());
		}
		return dataSource;
	}

}
