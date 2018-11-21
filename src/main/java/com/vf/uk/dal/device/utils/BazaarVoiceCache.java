package com.vf.uk.dal.device.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BazaarVoiceCache {

	@Value("${bazaarvoice.urlPart1}")
	private String urlPart1;

	@Value("${bazaarvoice.urlPart2}")
	private String urlPart2;

	@Cacheable(value = "bazaarVoiceCache", key = "#deviceId")
	public String getBazaarVoiceReviews(String deviceId) {
		log.info( new Date() + " Retrieving from BazaarVoice : " + deviceId);
		String jsonObject = null;
		URL connection;
		URLConnection urlConn;
		try {
			connection = new URL(urlPart1 + deviceId + urlPart2);
			urlConn = connection.openConnection();
			try (BufferedReader buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()))) {
				jsonObject = buffer.lines().collect(Collectors.joining("\n"));
			}

		} catch (Exception e) {
			log.info( "Error Retrieving from BazaarVoice : " + e);
		}
		return jsonObject;
	}
}
