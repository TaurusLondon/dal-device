package com.vf.uk.dal.device.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.uk.dal.common.logger.LogHelper;

public class ElasticSearchUtils {
	
	static ObjectMapper mapper = SingletonMapperUtility.getObjectMapper();
	/**
	 * @author manoj.bera
	 * @param response
	 * @param classType
	 * @return
	 */
	public static <T> List<T> getListOfObject(SearchResponse response, Class<T> classType)
	{
		List<T> res=new ArrayList<>();
		List<SearchHit> searchHitList = new ArrayList<>(Arrays.asList(response.getHits().getHits()));
		CompletableFuture<List<T>> future0 = getCompatibleFutureObject(searchHitList,
				new ArrayList<T>(), classType);
		try {
			res = future0.get();
		} catch (Exception e) {
			LogHelper.error(ElasticSearchUtils.class, "Exception occured while executing thread pool :" + e);
		}
		return res;
	}
	/**
	 * @author manoj.bera
	 * @param searchHitList
	 * @param result
	 * @param classType
	 * @return
	 */
	private static<U> CompletableFuture<List<U>> getCompatibleFutureObject(List<SearchHit> searchHitList,
			List<U> result, Class<U> classType) 
	{
		return CompletableFuture.supplyAsync(() -> {
			searchHitList.forEach(hit -> result.add(getObject(hit, classType)));
			return result;
		});
	}
	/**
	 * @author manoj.bera@a
	 * @param response
	 * @param classType
	 * @return
	 */
	public static <T> T getObject(SearchResponse response, Class<T> classType)
	{
		for(SearchHit hit:response.getHits().getHits()){
			return getObject(hit, classType);
		}
		return null;
	}
	/**
	 * @author manoj.bera
	 * @param hit
	 * @param valueType
	 * @return
	 */
	private static <T> T getObject(SearchHit hit, Class<T> classType)
	{
		try {
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return mapper.readValue(hit.getSourceAsString(), classType);
		} catch (IOException e) {
			LogHelper.error(ResponseMappingHelper.class, "::::::Exception occurred preparing Commercial Product list from ES response:::::: " + e);
		}return null;
	}
}
