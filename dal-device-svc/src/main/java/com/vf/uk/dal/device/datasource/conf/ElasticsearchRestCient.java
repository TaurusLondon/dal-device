package com.vf.uk.dal.device.datasource.conf;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import com.vf.uk.dal.common.configuration.YAMLConfigurationSource;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.utils.Constants;

/**
 * This class to create Java rest client object to communicate with elastic search nodes to Querying /Indexing data 
 * 
 * @author dharmapuri.veerabhad
 *
 */
public class ElasticsearchRestCient {

	private static String vpcEndPoint = getYamlConfig().get(Constants.ELASTIC_SEARCH_HOST);

	private static RestClient restClient;
	
	/* Default constructor */
	private ElasticsearchRestCient() {
		
	}

	/**
	 *  creates rest client object
	 * @return
	 */
	public static RestClient getClient() {
		if (restClient == null) {
			restClient = createRestClient();
			LogHelper.info(RestClient.class, "Rest client object created" );
		}
		return restClient;
	}

	/**
	 * Closes active rest client object
	 * @throws IOException
	 */
	public static void closeRestClient() throws IOException {
		try {
			restClient.close();
			LogHelper.info(RestClient.class, "Rest client object closed" );
		} catch (IOException e) {
			LogHelper.info(RestClient.class, "Exception occured while closing rest client object" );
			throw e;
		}
	}

	/**
	 * Reads AWS Elastic search VPC end point from Application YML file
	 * 
	 * @return
	 */
	public static Map<String, String> getYamlConfig() {
		Map<String, String> configMap = new HashMap<>();
		try {
			YAMLConfigurationSource source = new YAMLConfigurationSource("application.yml");
			configMap.put(Constants.ELASTIC_SEARCH_HOST,
					(String) source.getProperties().get(Constants.ELASTIC_SEARCH_HOST));
			configMap.put(Constants.BAZAAR_VOICE_PART1,
					(String) source.getProperties().get(Constants.BAZAAR_VOICE_PART1));
			configMap.put(Constants.BAZAAR_VOICE_PART2,
					(String) source.getProperties().get(Constants.BAZAAR_VOICE_PART2));
		} catch (Exception exception) {
			LogHelper.error(RestClient.class, "Error ocuured while reading Application YML file" + exception);
		}
		return configMap;

	}

	/**
	 * Method to create Rest Client object
	 * @return
	 */
	private static RestClient createRestClient() {
		try {
			LogHelper.info(RestClient.class, "Rest client creation with VPC end point::" + vpcEndPoint);
			InetAddress address = InetAddress.getByName(new URL(vpcEndPoint).getHost());
			restClient = RestClient.builder(
					new HttpHost(address, address.getHostName(), Constants.DEFAULT_PORT, Constants.HTTPS_SCHEME))
					.build();
			LogHelper.info(RestClient.class, "Rest Client created Successfully  with  VPC End point ::" + vpcEndPoint);
		} catch (UnknownHostException | MalformedURLException e) {
			LogHelper.error(RestClient.class, "Error ocuured while creating ES Rest client" + e);
		}
		return restClient;
	}
}