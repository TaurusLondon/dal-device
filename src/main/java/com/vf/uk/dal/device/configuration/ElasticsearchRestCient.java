package com.vf.uk.dal.device.configuration;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * This class to create Java rest client object to communicate with elastic
 * search nodes to Querying /Indexing data
 * 
 * @author dharmapuri.veerabhad
 *
 */
@Slf4j
@Component
public class ElasticsearchRestCient {

	public static final int DEFAULT_PORT = -1;
	public static final String HTTPS_SCHEME = "https";

	@Value("${elasticsearch.host}")
	private String vpcEndPoint;

	private RestHighLevelClient restClient;

	private ElasticsearchRestCient() {

	}

	/**
	 * creates rest client object
	 * 
	 * @return RestHighLevelClient
	 */
	public RestHighLevelClient getClient() {
		if (restClient == null) {
			restClient = createRestClient();
			log.info( "Rest client object created");
		}
		return restClient;
	}

	/**
	 * Closes active rest client object
	 * 
	 * @throws IOException
	 */
	public void closeRestClient() throws IOException {
		try {
			restClient.close();
			log.info( "Rest client object closed");
		} catch (IOException e) {
			log.info( "Exception occured while closing rest client object");
			throw e;
		}
	}
	/**
	 * Method to create Rest Client object
	 * 
	 * @return RestHighLevelClient
	 */
	private RestHighLevelClient createRestClient() {
		try {
			log.info( "Rest client creation with VPC end point::" + vpcEndPoint);
			InetAddress address = InetAddress.getByName(new URL(vpcEndPoint).getHost());
			restClient = new RestHighLevelClient(RestClient
					.builder(new HttpHost(address, address.getHostName(), DEFAULT_PORT,
							HTTPS_SCHEME))
					.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
						@Override
						public RequestConfig.Builder customizeRequestConfig(
								RequestConfig.Builder requestConfigBuilder) {
							return requestConfigBuilder.setConnectTimeout(5000).setSocketTimeout(60000);
						}
					}).setMaxRetryTimeoutMillis(60000));
			log.info( "Rest Client created Successfully  with  VPC End point ::" + vpcEndPoint);
		} catch (UnknownHostException | MalformedURLException e) {
			log.error( "Error ocuured while creating ES Rest client" + e);
		}
		return restClient;
	}
}