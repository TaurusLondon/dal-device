package com.vf.uk.dal.device.service.test;

import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.vf.uk.dal.common.registry.client.RegistryClient;
import com.vf.uk.dal.device.beans.test.DeviceTestBeans;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.entity.Device;
import com.vf.uk.dal.device.entity.FacetedDevice;
import com.vf.uk.dal.device.svc.DeviceRecommendationService;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.utility.entity.InstalledProduct;
import com.vf.uk.dal.utility.entity.RecommendedProduct;
import com.vf.uk.dal.utility.entity.RecommendedProductListRequest;
import com.vf.uk.dal.utility.entity.RecommendedProductListResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeviceTestBeans.class)
public class DeviceRecommendationServiceTest {

	@Autowired
	DeviceRecommendationService deviceRecommendationService;
	
	@MockBean
	RegistryClient registryClientMock;
	
	@MockBean
	RestTemplate restTemplateMock;
	
	@MockBean
	DeviceDao deviceDAOMock;
	
	@MockBean
	FacetedDevice facetedDeviceMock;
	
	@MockBean
	RecommendedProductListResponse recomProdListRespMock;
	
	@Before
	public void setupMockBehaviour() throws Exception {
        given(registryClientMock.getRestTemplate()).willReturn(restTemplateMock);
	} 

	@After
	public void tearDown() throws Exception {
	}
	

	@Test
	public void testGetRecommendedDeviceListRequest() {
		String msisdn = "447436899023";
		String deviceId = "109381";
		RecommendedProductListRequest req = deviceRecommendationService.getRecommendedDeviceListRequest(msisdn, deviceId);
		String serialNum  = req.getSerialNumber();
		String prodId = null;
		List<InstalledProduct> instProds = new ArrayList<>();
		instProds = req.getInstalledProducts();
		for (InstalledProduct instProd : instProds) {
			prodId = instProd.getId();
		}
		Assert.assertEquals(msisdn, serialNum);
		Assert.assertEquals(deviceId, prodId);		
	}
	
	
	@Test
	public void testGetRecommendedDeviceList() {
		String msisdn = "447436899023";
		String deviceId = "109381";
		RecommendedProductListRequest req = deviceRecommendationService.getRecommendedDeviceListRequest(msisdn, deviceId);

		FacetedDevice fd = new FacetedDevice();
		fd.newFacet(null);
		Device d = new Device();
		d.setDeviceId("109382");
		List<Device> deviceList = new ArrayList<>();
		deviceList.add(d);
		fd.setDevice(deviceList);
		fd.setNoOfRecordsFound(1);
		
		RecommendedProductListResponse resp = new RecommendedProductListResponse();
		List<RecommendedProduct> recList = new ArrayList<>();
		RecommendedProduct rp = new RecommendedProduct();
		rp.setId("109382");
		recList.add(rp);
		resp.setRecommendedProductList(recList);
		
		Mockito.when(CommonUtility.getRecommendedProductList(
				req, registryClientMock)).thenReturn(resp);
		fd = deviceRecommendationService.getRecommendedDeviceList(msisdn, deviceId, fd);

		Assert.assertTrue(fd.getNoOfRecordsFound() > 0);
	}


}
