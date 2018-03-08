package com.vf.uk.dal.device.utils;

import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.ObjectMapper;

public enum SingletonMapperUtility {

	JSONParser,ObjectMapper;
	   
	    public static JSONParser getJSONParser(){
	        return new JSONParser();
	    }
	    public static ObjectMapper getObjectMapper(){
	        return new ObjectMapper();
	    }
}
