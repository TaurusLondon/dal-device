/*package com.vf.uk.dal.device.integration.test;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.vf.uk.dal.device.DeviceApplication;

import com.vf.uk.dal.device.common.test.CommonMethods;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.entity.DeviceDetails;
import com.vf.uk.dal.device.entity.DeviceTile;
import com.vf.uk.dal.device.entity.ProductGroup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes=DeviceApplication.class)

 //This class has integration test which tests the entire application 

public class DeviceIntegrationTest {

	@Autowired
	private TestRestTemplate trt;

	@MockBean
	DeviceDao deviceDAOMock;


	@Before
	public void setupMockBehaviour() throws Exception {

		given(deviceDAOMock.getProductGroupByGroupTypeGroupName("DEVICE", "Apple iPhone 7")).willReturn(CommonMethods.getProductGroupByGroupTypeGroupName("DEVICE", "Apple iPhone 7"));
		given(deviceDAOMock.getProductGroupByGroupTypeGroupName(null, "Apple iPhone 7")).willReturn(null);
		given(deviceDAOMock.getProductGroupByGroupTypeGroupName("DEVICE", null)).willReturn(null);
		given(deviceDAOMock.getListOfDeviceTile("DEVICE", "Apple iPhone 7")).willReturn(CommonMethods.getDeviceTile("DEVICE", "Apple iPhone 7"));
		given(deviceDAOMock.getListOfDeviceTile(null, "Apple iPhone 7")).willReturn(null);
		given(deviceDAOMock.getListOfDeviceTile("DEVICE", null)).willReturn(null);
		given(deviceDAOMock.getDeviceTileById("93353")).willReturn(CommonMethods.getDeviceTileById("93353"));
		given(deviceDAOMock.getDeviceTileById("")).willReturn(null);
		given(deviceDAOMock.getDeviceDetails("93353")).willReturn(CommonMethods.getDevice("93353"));
		given(deviceDAOMock.getDeviceDetails("88880")).willReturn(null);
	}

	// Test case for testing whole application

	@Test
	public void testForGetProductGroupList() throws Exception {
		ResponseEntity<? extends ArrayList<ProductGroup>> entity = trt.getForEntity("/productGroup/?filter[groupType]=DEVICE&filter[groupName]=Apple iPhone 7",
				(Class<? extends ArrayList<ProductGroup>>) ArrayList.class);
		Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
	}
	@Test
	public void testForNullGroupTypeGetProductGroupList() throws Exception {
		ResponseEntity<? extends ArrayList<ProductGroup>> entity =null;	
	
		try
		{
		 entity = trt.getForEntity("/productGroup/?filter[groupType]=&filter[groupName]=Apple iPhone 7",
				(Class<? extends ArrayList<ProductGroup>>) ArrayList.class);
		}
		catch(Exception e)
		{
		}
		
		Assert.assertNull(entity);
	}
	@Test
	public void testForNullGroupNameGetProductGroupList() throws Exception {
		ResponseEntity<? extends ArrayList<ProductGroup>> entity =null;	
	
		try
		{
		 entity = trt.getForEntity("/productGroup/?filter[groupType]=DEVICE&filter[groupName]=",
				(Class<? extends ArrayList<ProductGroup>>) ArrayList.class);
		}
		catch(Exception e)
		{
		}
	
		Assert.assertNull(entity);
	}
	@Test
	public void testForGetDeviceTileList() throws Exception {
		ResponseEntity<? extends ArrayList<DeviceTile>> entity = trt.getForEntity("/?filter[groupType]=DEVICE&filter[groupName]=Apple iPhone 7",
				(Class<? extends ArrayList<DeviceTile>>) ArrayList.class);
		Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
	}
	@Test
	public void testForNullGroupTypeGetDeviceTileList() throws Exception {
		ResponseEntity<? extends ArrayList<DeviceTile>> entity =null;	
	
		try
		{
		 entity = trt.getForEntity("/?filter[groupType]=&filter[groupName]=Apple iPhone 7",
				(Class<? extends ArrayList<DeviceTile>>) ArrayList.class);
		}
		catch(Exception e)
		{
		}
		
		Assert.assertNull(entity);
	}
	@Test
	public void testForNullGroupNameGetDeviceTileList() throws Exception {
		ResponseEntity<? extends ArrayList<DeviceTile>> entity =null;	
	
		try
		{
		 entity = trt.getForEntity("/?filter[groupType]=DEVICE&filter[groupName]=",
				(Class<? extends ArrayList<DeviceTile>>) ArrayList.class);
		}
		catch(Exception e)
		{
		}
		
		Assert.assertNull(entity);
	}
	@Test
	public void testForGetDeviveListById() throws Exception {
		ResponseEntity<? extends ArrayList<DeviceTile>> entity = trt.getForEntity("/deviceTile/93353",
				(Class<? extends ArrayList<DeviceTile>>) ArrayList.class);
		Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
	}
	@Test
	public void testForNullGetDeviceTileById() throws Exception {
		ResponseEntity<? extends ArrayList<DeviceTile>> entity =null;	
	
		try
		{
		 entity = trt.getForEntity("/deviceTile/",
				(Class<? extends ArrayList<DeviceTile>>) ArrayList.class);
		}
		catch(Exception e)
		{
		}
		
		Assert.assertNull(entity.getBody());
	}
	@Test
	public void testForGetDeviceDetails() throws Exception {
		ResponseEntity<DeviceDetails> entity = trt.getForEntity("/93353",
				DeviceDetails.class);
		Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
	}
	@Test
	public void testForInvalidInputGetDeviceDetails() throws Exception {
		ResponseEntity<DeviceDetails> entity =null;	
	
		try
		{
		 entity = trt.getForEntity("/88880",
				DeviceDetails.class);
		}
		catch(Exception e)
		{
		}
		
		Assert.assertNull(entity.getBody());
	}
}*/