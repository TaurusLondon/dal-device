package com.vf.uk.dal.device.utils;

import org.elasticsearch.search.builder.SearchSourceBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

public enum SingletonMapperUtility {

	ObjectMapper , SearchSourceBuilder;

	public static ObjectMapper getObjectMapper() {
		return new ObjectMapper();
	}

	public static SearchSourceBuilder getSearchSourceBuilder() {
		return new SearchSourceBuilder();
	}
}
