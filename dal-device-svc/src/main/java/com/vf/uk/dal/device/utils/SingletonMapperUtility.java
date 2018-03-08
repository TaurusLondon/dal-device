package com.vf.uk.dal.device.utils;

import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.ObjectMapper;

public enum SingletonMapperUtility {

	JSONParser, ObjectMapper, SearchSourceBuilder;

	public static JSONParser getJSONParser() {
		return new JSONParser();
	}

	public static ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}

	public static SearchSourceBuilder getSearchSourceBuilder() {
		return new SearchSourceBuilder();
	}
}
