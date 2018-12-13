package com.vf.uk.dal.device.service.test;

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

import com.vf.uk.dal.device.beans.test.DeviceTestBeans;
import com.vf.uk.dal.device.client.entity.customer.InstalledProduct;
import com.vf.uk.dal.device.client.entity.customer.RecommendedProduct;
import com.vf.uk.dal.device.client.entity.customer.RecommendedProductListRequest;
import com.vf.uk.dal.device.client.entity.customer.RecommendedProductListResponse;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.dao.DeviceTileCacheDAO;
import com.vf.uk.dal.device.model.Device;
import com.vf.uk.dal.device.model.FacetedDevice;
import com.vf.uk.dal.device.service.DeviceRecommendationService;
import com.vf.uk.dal.device.utils.CommonUtility;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeviceTestBeans.class)
public class DeviceRecommendationServiceTest {

	@Autowired
	CommonUtility commonUtility;
	@Autowired
	DeviceRecommendationService deviceRecommendationService;

	@MockBean
	RestTemplate restTemplateMock;

	@MockBean
	DeviceDao deviceDAOMock;

	@MockBean
	DeviceTileCacheDAO cacheDao;

	@MockBean
	FacetedDevice facetedDeviceMock;

	@MockBean
	RecommendedProductListResponse recomProdListRespMock;

	@Before
	public void setupMockBehaviour() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetRecommendedDeviceListRequest() {
		String msisdn = "447436899023";
		String deviceId = "109381";
		RecommendedProductListRequest req = deviceRecommendationService.getRecommendedDeviceListRequest(msisdn,
				deviceId);
		String serialNum = req.getSerialNumber();
		String prodId = null;
		List<InstalledProduct> instProds = new ArrayList<>();
		instProds = req.getInstalledProducts();
		for (InstalledProduct instProd : instProds) {
			prodId = instProd.getId();
		}
		Assert.assertNotNull(req);
		Assert.assertEquals(msisdn, serialNum);
		Assert.assertEquals(deviceId, prodId);
	}

	@Test
	public void testGetRecommendedDeviceList() {
		String msisdn = "447436899023";
		String deviceId = "109381";
		RecommendedProductListRequest req = deviceRecommendationService.getRecommendedDeviceListRequest(msisdn,
				deviceId);

		FacetedDevice fd = new FacetedDevice();
		fd.setNewFacet(null);
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

		Mockito.when(commonUtility.getRecommendedProductList(req)).thenReturn(resp);
		fd = deviceRecommendationService.getRecommendedDeviceList(msisdn, deviceId, fd);

		Assert.assertTrue(fd.getNoOfRecordsFound() > 0);
		Assert.assertNotNull(fd);
		Assert.assertEquals( "109382",fd.getDevice().get(0).getDeviceId());
		deviceRecommendationService.getRecommendedDeviceList(null, null, fd);
		deviceList.add(d);
		deviceRecommendationService.getRecommendedDeviceList(msisdn, deviceId, fd);
		d.setDeviceId(null);
		deviceList.add(0,d);
		deviceRecommendationService.getRecommendedDeviceList(msisdn, deviceId, fd);
		deviceList.clear();
		d.setDeviceId("093353");
		deviceList.add(d);
		Device d1 = new Device();
		deviceList.add(d1);
		deviceRecommendationService.getRecommendedDeviceList(msisdn, deviceId, fd);
		deviceList.clear();
		Device d2 = new Device();
		d2.setDeviceId("093353");
		deviceList.add(d2);
		deviceList.add(d1);
		
		deviceRecommendationService.getRecommendedDeviceList(msisdn, deviceId, fd);
	}

}
