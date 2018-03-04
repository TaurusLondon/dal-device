package com.vf.uk.dal.device.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.common.logger.LogHelper;

@Component
public class BazaarVoiceCache {

    @Cacheable(value = "bazaarVoiceCache", key = "#deviceId")
    public String getBazaarVoiceReviews(String deviceId) {
    	LogHelper.info(this,new Date()+" Retrieving from BazaarVoice : " + deviceId);
        String jsonObject = null;
        URL connection;
		URLConnection urlConn;
		try {
			connection = new URL("http://api.bazaarvoice.com/data/reviews.json?apiversion=5.4&passkey=a1nyq80ufv2yvznpitrjbco7e&Filter=ProductID:" + deviceId + "&Include=Products&Stats=Reviews&locale=en_GB");
			urlConn = connection.openConnection();
				try (BufferedReader buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()))) {
					jsonObject = buffer.lines().collect(Collectors.joining("\n"));
				}
				
		} catch (Exception e) {
			LogHelper.info(this,"Error Retrieving from BazaarVoice : " + e);
		}
		return jsonObject;
    }
}
