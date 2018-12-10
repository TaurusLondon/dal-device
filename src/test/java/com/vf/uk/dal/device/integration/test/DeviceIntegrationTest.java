package com.vf.uk.dal.device.integration.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.uk.dal.device.DeviceApplication;
import com.vf.uk.dal.device.aspect.CatalogServiceAspect;
import com.vf.uk.dal.device.client.converter.ResponseMappingHelper;
import com.vf.uk.dal.device.client.entity.bundle.BundleDetailsForAppSrv;
import com.vf.uk.dal.device.client.entity.customer.RecommendedProductListResponse;
import com.vf.uk.dal.device.client.entity.price.PriceForProduct;
import com.vf.uk.dal.device.common.test.CommonMethods;
import com.vf.uk.dal.device.controller.AccessoryInsuranceController;
import com.vf.uk.dal.device.controller.CacheDeviceAndReviewController;
import com.vf.uk.dal.device.controller.DeviceController;
import com.vf.uk.dal.device.controller.DeviceDetailsController;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.dao.DeviceTileCacheDAO;
import com.vf.uk.dal.device.model.AccessoryTileGroup;
import com.vf.uk.dal.device.model.DeviceDetails;
import com.vf.uk.dal.device.model.ErrorPopulation;
import com.vf.uk.dal.device.model.Insurances;
import com.vf.uk.dal.device.service.CacheDeviceService;
import com.vf.uk.dal.device.service.DeviceRecommendationService;
import com.vf.uk.dal.device.service.DeviceService;
import com.vf.uk.dal.device.utils.DeviceESHelper;
import com.vf.uk.dal.device.utils.DeviceServiceCommonUtility;

/**
 * In order to run the controller class a bean of the ProductController is
 * initialized in @SpringBootTest
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = { DeviceApplication.class,
		DeviceDetailsController.class })
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

	private ObjectMapper mapper;

	@Before
	public void setupMockBehaviour() throws Exception {
		aspect.beforeAdvice(null);
		this.mockMvc = webAppContextSetup(WebApplicationContext).build();
		mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		given(restTemplate
				.getForObject("http://BUNDLES-V1/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId="
						+ "093353" + "&journeyType=" + null, BundleDetailsForAppSrv.class))
								.willReturn(CommonMethods.getCoupledBundleListForDevice());
		given(response.getMerchandisingPromotion(ArgumentMatchers.any())).willReturn(CommonMethods.getMemPro());
		given(response.getCommercialProduct(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getCommercialProductsListOfMakeAndModel().get(0));
		given(response.getListOfGroupFromJson(ArgumentMatchers.any())).willReturn(CommonMethods.getGroup());
		given(response.getCommercialProductFromJson(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getCommercialProductsListOfAccessories());
		given(response.getCommercialBundle(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getCommercialBundleFromCommercialBundleRepository());
		given(response.getListOfMerchandisingPromotionFromJson(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getMerchandisingPromotion_One());
		String jsonString1 = new String(CommonMethods.readFile("\\rest-mock\\CUSTOMER-V1.json"));
		RecommendedProductListResponse obj1 = new ObjectMapper().readValue(jsonString1,
				RecommendedProductListResponse.class);
		given(restTemplate.postForObject("http://CUSTOMER-V1/customer/getRecommendedProductList/",
				CommonMethods.getRecommendedDeviceListRequest("7741655541", "109381"),
				RecommendedProductListResponse.class)).willReturn(obj1);
		given(this.deviceDAOMock.getBundleDetailsFromComplansListingAPI("093353", null))
				.willReturn(CommonMethods.getCompatibleBundleListJson());
	}

	@Test
	public void notNullTestForAccessory() throws JsonProcessingException, Exception {
		given(response.getCommercialProduct(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getCommercialProductByDeviceIdForAccessory());
		given(restTemplate.postForObject("http://PRICE-V1/price/product",
				CommonMethods.bundleDeviceAndProductsList_For_GetAccessoriesOfDeviceIntegration(),
				PriceForProduct.class)).willReturn(CommonMethods.getPriceForProduct_For_GetAccessoriesForDevice());
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/accessory/queries/byDeviceId/?deviceId=093353")
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		List<AccessoryTileGroup> accessoryDetails = mapper.readValue(mvcResult.getResponse().getContentAsString(),
				new TypeReference<List<AccessoryTileGroup>>() {
				});
		assertNotNull(accessoryDetails);
		assertEquals( "Apple iPhone 6s cover",accessoryDetails.get(0).getGroupName());
		assertEquals( "093329",accessoryDetails.get(0).getAccessories().get(0).getSkuId());
		assertEquals("Apple iPhone 7 Silicone Case Midnight Blue",accessoryDetails.get(0).getAccessories().get(0).getName());
		assertEquals("Midnight Blue",accessoryDetails.get(0).getAccessories().get(0).getColour());
	}

	@Test
	public void nullTestForAccessory() throws JsonProcessingException, Exception {
		SearchResponse responseSearch = new SearchResponse();
		given(response.getCommercialProduct(responseSearch))
				.willReturn(CommonMethods.getCommercialProductByDeviceIdForAccessory());
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/accessory/queries/byDeviceId/?deviceId=093353")
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
		ErrorPopulation error = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<ErrorPopulation>() {
		});
		assertNotNull(error);
		assertEquals("No Compatible Accessories found for given device Id", error.getMessage());
		assertEquals("DEVICE_INVALID_INPUT_011", error.getCode());
	}

	@Test
	public void notNullTestForInsurance() throws JsonProcessingException, Exception {
		given(response.getCommercialProduct(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getCommercialProductForInsurance());
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/insurance/queries/byDeviceId/?deviceId=093353")
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		Insurances insurances = mapper.readValue(mvcResult.getResponse().getContentAsString(),
				new TypeReference<Insurances>() {
				});
		assertNotNull(insurances);
		assertEquals("20",insurances.getMinCost());
		assertEquals( "093329",insurances.getInsuranceList().get(0).getId());
		assertEquals( "Apple iPhone 7 Silicone Case Midnight Blue",insurances.getInsuranceList().get(0).getName());
		assertEquals("35",insurances.getInsuranceList().get(0).getPrice().getGross());
	}

	@Test
	public void nullTestForInsurance() throws JsonProcessingException, Exception {
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/insurance/queries/byDeviceId/?deviceId=093353")
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
		ErrorPopulation error = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<ErrorPopulation>() {
		});
		assertNotNull(error);
		assertEquals("No Compatible Insurances found for given device Id", error.getMessage());
		assertEquals("DEVICE_INVALID_INPUT_052", error.getCode());
	}

	@Test
	public void nullTestForgetDeviceTileMakeAndModel() throws JsonProcessingException, Exception {
		given(response.getCommercialProductFromJson(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getCommercialProductsListOfMakeAndModel());
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/deviceTile/queries/byMakeModel/?groupType=DEVICE_PAYM&make=apple&model=iPhone-7&journeyType=Upgrade")
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();
		ErrorPopulation error = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<ErrorPopulation>() {
		});
		assertNotNull(error);
		assertEquals("No Devices Found for the given input search criteria", error.getMessage());
		assertEquals("DEVICE_INVALID_INPUT_013", error.getCode());
	}

	@Test
	public void nullTestForgetDeviceTileById() throws JsonProcessingException, Exception {
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/deviceTile/queries/byDeviceVariant/?deviceId=093353").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
		ErrorPopulation error = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<ErrorPopulation>() {
		});
		assertNotNull(error);
		assertEquals("No details found for given criteria", error.getMessage());
		assertEquals("DEVICE_INVALID_INPUT_053", error.getCode());
	}

	@Test
	public void notNullTestForgetDeviceDetailsWithJourneyType() throws JsonProcessingException, Exception {
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get("/device/093353").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		DeviceDetails deviceDetails = mapper.readValue(mvcResult.getResponse().getContentAsString(),
				new TypeReference<DeviceDetails>() {
				});
		assertNotNull(deviceDetails);
		assertEquals( "092572",deviceDetails.getDeviceId());
		assertEquals("110104",deviceDetails.getLeadPlanId() );
		assertEquals("Handset",deviceDetails.getProductClass() );
		assertEquals( "Apple iPhone 7 Plus 128GB silver",deviceDetails.getName());
		assertEquals( "apple",deviceDetails.getEquipmentDetail().getMake());
		assertEquals( "iphone-7-plus",deviceDetails.getEquipmentDetail().getModel());
	}
}
