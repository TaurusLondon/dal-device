package com.vf.uk.dal.device.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.datamodel.productgroups.Count;
import com.vf.uk.dal.device.datamodel.productgroups.FacetField;

@Component
public class ElasticSearchUtils {
	
	ObjectMapper mapper = SingletonMapperUtility.getObjectMapper();
	/**
	 * @author manoj.bera
	 * @param response
	 * @param classType
	 * @return
	 */
	public <T>List<T> getListOfObject(SearchResponse response, Class<T> classType)
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
	private <U>CompletableFuture<List<U>> getCompatibleFutureObject(List<SearchHit> searchHitList,
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
	public <T> T getObject(SearchResponse response, Class<T> classType)
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
	private <T> T getObject(SearchHit hit, Class<T> classType)
	{
		try {
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return mapper.readValue(hit.getSourceAsString(), classType);
		} catch (IOException e) {
			LogHelper.error(ResponseMappingHelper.class, "::::::Exception occurred preparing Commercial Product list from ES response:::::: " + e);
		}return null;
	}
	
	public List<FacetField> getListOfObjectForAggrs(SearchResponse response)
	{
		List<FacetField> res=new ArrayList<>();
		try {
		Map<String, Aggregation> aggrsMap=response.getAggregations().getAsMap();
		for (Map.Entry<String, Aggregation> entry : aggrsMap.entrySet()) {
			FacetField facetFeild=new FacetField();
			facetFeild.setName(entry.getKey());
			Terms stringTerms = (Terms) entry.getValue();
			List<Count> listOfCount = new ArrayList<>();
			stringTerms.getBuckets().forEach(
				      bucket -> {
				    	  Count count=new Count();
				    	  count.setCount(bucket.getDocCount());
				    	  count.setName(bucket.getKey().toString());
				    	  listOfCount.add(count);
				      });
			facetFeild.setValues(listOfCount);
			res.add(facetFeild);
		}
		} catch (Exception e) {
			LogHelper.error(ElasticSearchUtils.class, "Exception occured while executing thread pool :" + e);
		}
		return res;
	}
}
