package com.vf.uk.dal.device.integration.test;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.uk.dal.device.DeviceApplication;
import com.vf.uk.dal.device.aspect.CatalogServiceAspect;
import com.vf.uk.dal.device.common.test.CommonMethods;
import com.vf.uk.dal.device.controller.AccessoryInsuranceController;
import com.vf.uk.dal.device.controller.CacheDeviceAndReviewController;
import com.vf.uk.dal.device.controller.DeviceController;
import com.vf.uk.dal.device.controller.DeviceDetailsController;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.dao.DeviceTileCacheDAO;
import com.vf.uk.dal.device.helper.DeviceESHelper;
import com.vf.uk.dal.device.helper.DeviceServiceCommonUtility;
import com.vf.uk.dal.device.svc.CacheDeviceService;
import com.vf.uk.dal.device.svc.DeviceRecommendationService;
import com.vf.uk.dal.device.svc.DeviceService;
import com.vf.uk.dal.device.utils.ResponseMappingHelper;
import com.vf.uk.dal.utility.entity.BundleDetailsForAppSrv;
import com.vf.uk.dal.utility.entity.CurrentJourney;
import com.vf.uk.dal.utility.entity.PriceForProduct;
import com.vf.uk.dal.utility.entity.RecommendedProductListResponse;
/**
 * In order to run the controller class a bean of the ProductController is
 * initialized in @SpringBootTest
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,classes = {DeviceApplication.class,DeviceDetailsController.class})
public class DeviceIntegrationTest {
	private MockMvc mockMvc;

	@Autowired
	DeviceESHelper deviceESHelper;

	@MockBean
	DeviceDao deviceDAOMock;

	@Autowired
	DeviceDetailsController deviceDetailsController;

	@Autowired
	AccessoryInsuranceController accessoryInsuranceController;

	@MockBean
	ResponseMappingHelper response;

	@MockBean
	RestTemplate restTemplate;

	@MockBean
	DeviceTileCacheDAO deviceTileCache;

	@Autowired
	CatalogServiceAspect aspect;

	@Autowired
	DeviceController deviceController;

	@Autowired
	DeviceService deviceService;

	@MockBean
	DeviceRecommendationService deviceRecomServiceMock;

	@Autowired
	CacheDeviceAndReviewController cacheDeviceAndReviewController;

	@Autowired
	CacheDeviceService CacheDeviceService;

	@Autowired
	DeviceServiceCommonUtility deviceServiceCommonUtility;
	
	@Autowired
	private WebApplicationContext WebApplicationContext;

	
	
	@Before
	public void setupMockBehaviour() throws Exception {
		aspect.beforeAdvice(null);
		this.mockMvc = webAppContextSetup(WebApplicationContext).build();
		String jsonString = new String(CommonMethods.readFile("\\rest-mock\\COMMON-V1.json"));
		CurrentJourney obj = new ObjectMapper().readValue(jsonString, CurrentJourney.class);
		given(restTemplate
				.getForObject("http://BUNDLES-V1/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId="
						+ "093353" + "&journeyType=" + null, BundleDetailsForAppSrv.class))
								.willReturn(CommonMethods.getCoupledBundleListForDevice());
		given(restTemplate.getForObject(
				"http://COMMON-V1/common/journey/" + "c1a42269-6562-4c96-b3be-1ca2a6681d57" + "/queries/currentJourney",
				CurrentJourney.class)).willReturn(obj);
		given(response.getMerchandisingPromotion(Matchers.anyObject())).willReturn(CommonMethods.getMemPro());
		given(response.getCommercialProduct(Matchers.anyObject()))
				.willReturn(CommonMethods.getCommercialProductsListOfMakeAndModel().get(0));
		given(response.getListOfGroupFromJson(Matchers.anyObject())).willReturn(CommonMethods.getGroup());
		given(response.getCommercialProductFromJson(Matchers.anyObject()))
				.willReturn(CommonMethods.getCommercialProductsListOfAccessories());
		given(response.getCommercialBundle(Matchers.anyObject()))
				.willReturn(CommonMethods.getCommercialBundleFromCommercialBundleRepository());
		given(response.getListOfMerchandisingPromotionFromJson(Matchers.anyObject()))
				.willReturn(CommonMethods.getMerchandisingPromotion_One());
		String jsonString1 = new String(CommonMethods.readFile("\\rest-mock\\CUSTOMER-V1.json"));
		RecommendedProductListResponse obj1 = new ObjectMapper().readValue(jsonString1,
				RecommendedProductListResponse.class);
		//given(registry.getRestTemplate()).willReturn(restTemplate);
		given(restTemplate.postForObject("http://CUSTOMER-V1/customer/getRecommendedProductList/",
				CommonMethods.getRecommendedDeviceListRequest("7741655541", "109381"),
				RecommendedProductListResponse.class)).willReturn(obj1);
		given(this.deviceDAOMock.getBundleDetailsFromComplansListingAPI("093353", null))
				.willReturn(CommonMethods.getCompatibleBundleListJson());
	}

	@Test
	public void notNullTestForgetDeviceDetailsWithJourneyType()  throws JsonProcessingException, Exception  {
		mockMvc.perform(get("/device/093353").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(
		status().is(200));
	}
	@Test
	public void NotNullTestForAccessory()  throws JsonProcessingException, Exception  {
		given(response.getCommercialProduct(Matchers.anyObject()))
		.willReturn(CommonMethods.getCommercialProductByDeviceIdForAccessory());
		given(restTemplate.postForObject("http://PRICE-V1/price/product",
				CommonMethods.bundleDeviceAndProductsList_For_GetAccessoriesOfDeviceIntegration(), PriceForProduct.class))
						.willReturn(CommonMethods.getPriceForProduct_For_GetAccessoriesForDevice());
		mockMvc.perform(get("/accessory/queries/byDeviceId/?deviceId=093353").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(
		status().is(200));
	}
	@Test
	public void NullTestForAccessory()  throws JsonProcessingException, Exception  {
		
		mockMvc.perform(get("/accessory/queries/byDeviceId/?deviceId=093353").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(
		status().is(404));
	}
	@Test
	public void NotNullTestForInsurance()  throws JsonProcessingException, Exception  {
		given(response.getCommercialProduct(Matchers.anyObject()))
		.willReturn(CommonMethods.getCommercialProductForInsurance());
		mockMvc.perform(get("/insurance/queries/byDeviceId/?deviceId=093353").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(
		status().is(200));
	}
	@Test
	public void NullTestForInsurance()  throws JsonProcessingException, Exception  {
		mockMvc.perform(get("/insurance/queries/byDeviceId/?deviceId=093353").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(
		status().is(404));
	}
	@Test
	public void NullTestForgetDeviceTileMakeAndModel()  throws JsonProcessingException, Exception  {
		given(response.getCommercialProductFromJson(Matchers.anyObject()))
		.willReturn(CommonMethods.getCommercialProductsListOfMakeAndModel());
		mockMvc.perform(get("/deviceTile/queries/byMakeModel/?groupType=DEVICE_PAYM&make=apple&model=iPhone-7&journeyType=Upgrade").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(
		status().is(404));
	}
	@Test
	public void NullTestForgetDeviceTileById()  throws JsonProcessingException, Exception  {
		mockMvc.perform(get("/deviceTile/queries/byDeviceVariant/?deviceId=093353").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON));
	}
	
}

