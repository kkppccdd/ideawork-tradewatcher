/**
 * 
 */
package me.firecloud.ideawork.tradewatcher;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author kkppccdd
 *
 */
public class AuthenticationSupport {
	private static final Log log=LogFactory.getLog(AuthenticationSupport.class);
	
	/***********
	 * KEY
	 */
	public static final String PROPERTY_APP_KEY="TOP_APP_KEY";
	public static final String PROPERTY_APP_SECRET="TOP_APP_SECRET";
	public static final String PROPERTY_SESSION_KEY="TOP_SESSION_KEY";
	public static final String PROPERTY_TOP_SERVICE_URL="TOP_SERVICE_URL";
	public static final String PROPERTY_TOP_TMC_SERVICE_URL="TOP_TMC_SERVICE_URL";
	
	public static final String PROPERTY_DATA_SERVICE_URL="DATA_SERVICE_URL";
	public static final String PROPERTY_DATA_SERVICE_USERNAME="DATA_SERVICE_USERNAME";
	public static final String PROPERTY_DATA_SERVICE_PASSWORD="DATA_SERVICE_PASSWORD";
	
	private static AuthenticationSupport instance;
	
	public static AuthenticationSupport getInstance(){
		if(instance==null){
			instance = new AuthenticationSupport();
		}
		
		return instance;
	}
	
	private Map<String, String> evironmentProperties;
	
	public AuthenticationSupport(){
		evironmentProperties= System.getenv();
	}
	public String getAppKey(){
		return evironmentProperties.get(PROPERTY_APP_KEY);
	}
	
	public String getAppSecret(){
		return evironmentProperties.get(PROPERTY_APP_SECRET);
	}

	public String getSessionKey(){
		return evironmentProperties.get(PROPERTY_SESSION_KEY);
	}
	
	public String getTopServiceUrl(){
		return evironmentProperties.get(PROPERTY_TOP_SERVICE_URL);
	}
	
	public String getProperty(String key){
		return evironmentProperties.get(key);
	}
}
