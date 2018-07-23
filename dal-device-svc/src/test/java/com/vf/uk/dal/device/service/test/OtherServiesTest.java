package com.vf.uk.dal.device.service.test;

import static org.mockito.BDDMockito.given;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.common.registry.client.RegistryClient;
import com.vf.uk.dal.common.registry.client.Utility;
import com.vf.uk.dal.device.aspect.CatalogServiceAspect;
import com.vf.uk.dal.device.beans.test.DeviceTestBeans;
import com.vf.uk.dal.device.common.test.CommonMethods;
import com.vf.uk.dal.device.controller.CacheDeviceAndReviewController;
import com.vf.uk.dal.device.controller.DeviceController;
import com.vf.uk.dal.device.controller.DeviceDetailsController;
import com.vf.uk.dal.device.controller.DeviceEntityController;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.dao.impl.DeviceTileCacheDAOImpl;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.datamodel.product.ProductControl;
import com.vf.uk.dal.device.datamodel.product.ProductModel;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.BundlePrice;
import com.vf.uk.dal.device.entity.CacheDeviceTileResponse;
import com.vf.uk.dal.device.entity.Device;
import com.vf.uk.dal.device.entity.DeviceDetails;
import com.vf.uk.dal.device.entity.DeviceSummary;
import com.vf.uk.dal.device.entity.KeepDeviceChangePlanRequest;
import com.vf.uk.dal.device.entity.MerchandisingPromotion;
import com.vf.uk.dal.device.entity.Price;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.helper.DeviceConditionallHelper;
import com.vf.uk.dal.device.helper.DeviceServiceImplUtility;
import com.vf.uk.dal.device.svc.DeviceService;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.ResponseMappingHelper;
import com.vf.uk.dal.utility.entity.BundleDetailsForAppSrv;
import com.vf.uk.dal.utility.entity.BundleModelAndPrice;
import com.vf.uk.dal.utility.entity.CurrentJourney;
import com.vf.uk.dal.utility.entity.RecommendedProductListResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeviceTestBeans.class)
public class OtherServiesTest {

	@MockBean
	DeviceTileCacheDAOImpl deviceCacheDAOMock;

	@MockBean
	DeviceDao deviceDAOMock;

	@Autowired
	DeviceController deviceController;

	@MockBean
	ResponseMappingHelper response;

	@MockBean
	RegistryClient registry;

	@MockBean
	RestTemplate restTemplate;

	@Autowired
	CatalogServiceAspect catalogServiceAspect;

	@Autowired
	DeviceEntityController deviceEntityController;

	@MockBean
	DeviceConditionallHelper deviceConditionallHelper;

	@Autowired
	DeviceDetailsController deviceDetailsController;

	@Autowired
	CacheDeviceAndReviewController cacheDeviceAndReviewController;

	@Autowired
	DeviceService deviceService;

	@Before
	public void setupMockBehaviour() throws Exception {
		catalogServiceAspect.beforeAdvice(null);
		String jsonString = new String(Utility.readFile("\\rest-mock\\COMMON-V1.json"));
		CurrentJourney obj = new ObjectMapper().readValue(jsonString, CurrentJourney.class);
		given(registry.getRestTemplate()).willReturn(restTemplate);
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
		String jsonString1 = new String(Utility.readFile("\\rest-mock\\CUSTOMER-V1.json"));
		RecommendedProductListResponse obj1 = new ObjectMapper().readValue(jsonString1,
				RecommendedProductListResponse.class);
		given(registry.getRestTemplate()).willReturn(restTemplate);
		given(restTemplate.postForObject("http://CUSTOMER-V1/customer/getRecommendedProductList/",
				CommonMethods.getRecommendedDeviceListRequest("7741655541", "109381"),
				RecommendedProductListResponse.class)).willReturn(obj1);
		given(this.deviceDAOMock.getBundleDetailsFromComplansListingAPI("093353", null))
				.willReturn(CommonMethods.getCompatibleBundleListJson());

	}

	@Test
	public void testGetConditionalCriteriaForDeviceList() {
		Map<String, String> listOfProductVariants = new HashMap<>();
		listOfProductVariants.put("2", "093353");
		listOfProductVariants.put("3", "093354");
		given(deviceConditionallHelper.getLeadDeviceMap(Matchers.any())).willReturn(listOfProductVariants);
		BundleModelAndPrice bundleModelAndPrice = new BundleModelAndPrice();
		bundleModelAndPrice.setBundleModel(CommonMethods.getBundleModelListForBundleList().get(0));
		bundleModelAndPrice.setBundlePrice(CommonMethods.getBundlePrice());
		given(deviceConditionallHelper.calculatePlan(Matchers.anyFloat(), Matchers.any(), Matchers.any()))
				.willReturn(bundleModelAndPrice);
		List<String> variantsList = new ArrayList<String>();
		variantsList.add("093353|1");
		variantsList.add("092660|5");
		deviceService.getConditionalCriteriaForDeviceList(40.0F, new HashMap<>(), new HashMap<>(), new ArrayList<>(),
				new ArrayList<>(), variantsList);
	}

	@Test
	public void testCacheDeviceTileStatus() {
		given(cacheDeviceAndReviewController.getCacheDeviceJobStatus("testID"))
				.willReturn(CommonMethods.getCacheDeviceTileResponse());
		CacheDeviceTileResponse response = cacheDeviceAndReviewController.getCacheDeviceJobStatus("testID");
		Assert.assertEquals("Success", response.getJobStatus());
	}

	@Test
	public void nullTestgetListOfDeviceDetailsForException_One() {
		Map<String, String> queryparams = new HashMap<>();
		queryparams.put("journeyType", "journeyType");
		queryparams.put("offerCode", "W_HH_OC_01");
		queryparams.put("deviceId", "093353");
		deviceDetailsController.getListOfDeviceDetails(queryparams);
	}

	@Test
	public void nullTestgetListOfDeviceDetailsForException() {
		Map<String, String> queryparams = new HashMap<>();
		queryparams.put("journeyType", null);
		queryparams.put("offerCode", "W_HH_OC_01");
		queryparams.put("deviceId", "093353");
		deviceDetailsController.getListOfDeviceDetails(queryparams);
	}

	@Test
	public void nullDeviceIdTestgetListOfDeviceDetailsForException_One() {
		try {
			Map<String, String> queryparams = new HashMap<>();
			queryparams.put("journeyType", "journeyType");
			queryparams.put("offerCode", "W_HH_OC_01");
			deviceDetailsController.getListOfDeviceDetails(queryparams);
		} catch (Exception e) {
		}
	}

	@Test
	public void notNullTestForgetListOfDeviceDetails() {
		List<DeviceDetails> deviceDetails;
		given(deviceDAOMock.getPriceForBundleAndHardware(Matchers.anyList(), Matchers.anyString(),
				Matchers.anyString())).willReturn(CommonMethods.getPriceForBundleAndHardwareListFromTupleList());
		deviceDetails = deviceDetailsController
				.getListOfDeviceDetails(CommonMethods.getQueryParamsMapForDeviceDetails("093353"));
		Assert.assertNotNull(deviceDetails);
	}

	@Test
	public void notNullTestForgetListOfDeviceDetailsWithoutLeadPlanId() {
		List<DeviceDetails> deviceDetails;
		CommercialProduct commercial = CommonMethods.getCommercialProductByDeviceId_093353();
		commercial.setLeadPlanId(null);
		given(response.getCommercialProduct(Matchers.anyObject())).willReturn(commercial);
		given(response.getMerchandisingPromotion(Matchers.anyObject()))
				.willReturn(CommonMethods.getMerchPromotionForListOfDevicedetails());
		given(deviceDAOMock.getPriceForBundleAndHardware(Matchers.anyList(), Matchers.anyString(),
				Matchers.anyString())).willReturn(CommonMethods.getPriceForBundleAndHardwareListFromTupleList());
		deviceDetails = deviceDetailsController
				.getListOfDeviceDetails(CommonMethods.getQueryParamsMapForDeviceDetails("093353"));
		Assert.assertNotNull(deviceDetails);
	}

	@Test
	public void nuullTestForgetListOfDeviceDetailsWithoutLeadPlanId() {
		given(response.getCommercialProduct(Matchers.anyObject())).willReturn(null);
		given(response.getMerchandisingPromotion(Matchers.anyObject())).willReturn(null);
		given(deviceDAOMock.getPriceForBundleAndHardware(Matchers.anyList(), Matchers.anyString(),
				Matchers.anyString())).willReturn(Collections.emptyList());
		try {
			deviceDetailsController
					.getListOfDeviceDetails(CommonMethods.getQueryParamsMapForDeviceDetails("093353,090572"));
		} catch (ApplicationException e) {
			Assert.assertEquals("Invalid Device Id Sent In Request", e.getMessage());
		}
	}

	@Test
	public void emptyParamTestForgetListOfDeviceDetails() throws Exception {
		List<DeviceDetails> deviceDetails = null;
		try {
			deviceDetails = deviceDetailsController
					.getListOfDeviceDetails(CommonMethods.getQueryParamsMapForDeviceDetails(null));
		} catch (Exception e) {

		}
		Assert.assertNull(deviceDetails);
	}

	@Test
	public void invalidParamTestForgetListOfDeviceDetails() throws Exception {
		List<DeviceDetails> deviceDetails = null;
		try {
			deviceDetails = deviceDetailsController
					.getListOfDeviceDetails(CommonMethods.getQueryParamsMapForDeviceDetails(null, null));
		} catch (Exception e) {

		}
		Assert.assertNull(deviceDetails);
	}

	@Test
	public void testGetKeepDeviceChangePlan() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("60");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
		}
	}

	@Test
	public void testGetKeepDeviceChangePlanInvalidInput() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = null;
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("Invalid input request received", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanAllInvalidInput() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("Invalid input request received", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForNullDeviceId() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId(null);
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("60");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (ApplicationException e) {
			Assert.assertEquals("Invalid input request received. Missing Device Id", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForNullDeviceId_One() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("abc");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("60");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (ApplicationException e) {
			Assert.assertEquals("Invalid Device Id Sent In Request", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForNullBundleId() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId(null);
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("60");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("Invalid input request received. Missing Bundle Id", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForNullBundleId_One() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("abc");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("60");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("Invalid Bundle Id Sent In Request", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForNullAllowedRecurringPriceLimit() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit(null);
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("Invalid input request received. Missing AllowedRecurringPriceLimit", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForNullAllowedRecurringPriceLimit_One() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("abc");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("Invalid AllowedRecurringPriceLimit Sent In Request", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForNullPlansLimit() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("60");
		keepDeviceChangePlanRequest.setPlansLimit(null);
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("Invalid input request received. Missing Plans Limit", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForNullPlansLimit_One() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("60");
		keepDeviceChangePlanRequest.setPlansLimit("abc");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("Invalid Plans Limit Sent In Request", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForEmptyDataLimit() {
		given(this.deviceDAOMock.getBundleDetailsFromComplansListingAPI("093353", null))
				.willReturn(CommonMethods.getCompatibleBundleListJson_One());
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("60");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("No Compatible Plans List found for given device Id", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForSingle() {
		given(this.deviceDAOMock.getBundleDetailsFromComplansListingAPI("093353", null))
				.willReturn(CommonMethods.getCompatibleBundleListJson_Five());
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110151");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("73");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			 deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForError() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("10");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("No Similar Plan Found For Given Device Id", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForInvalidBundleIdError() {
		given(this.deviceDAOMock.getBundleDetailsFromComplansListingAPI("093353", null))
				.willReturn(CommonMethods.getCompatibleBundleListJson());
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("000000");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("10");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("Invalid Bundle Id Sent In Request", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForCompatibleError() {
		given(this.deviceDAOMock.getBundleDetailsFromComplansListingAPI("093353", null)).willReturn(null);
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("10");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("No Compatible Plans List found for given device Id", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForSingleAfterSameMonthlyCost() {
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("33");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForSingleAfterSameMonthlyCostException() {
		given(this.deviceDAOMock.getBundleDetailsFromComplansListingAPI("093353", null))
				.willReturn(CommonMethods.getCompatibleBundleListJson_Three());
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("33");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("No Similar Plan Found For Given Device Id", e.getMessage());
		}

	}

	@Test
	public void testGetKeepDeviceChangePlanForSingleAfterSameMonthlyCostException_One() {
		given(this.deviceDAOMock.getBundleDetailsFromComplansListingAPI("093353", null))
				.willReturn(CommonMethods.getCompatibleBundleListJson_Four());
		KeepDeviceChangePlanRequest keepDeviceChangePlanRequest = new KeepDeviceChangePlanRequest();
		keepDeviceChangePlanRequest.setDeviceId("093353");
		keepDeviceChangePlanRequest.setBundleId("110091");
		keepDeviceChangePlanRequest.setAllowedRecurringPriceLimit("33");
		keepDeviceChangePlanRequest.setPlansLimit("3");
		try {
			deviceController.getKeepDeviceChangePlan(keepDeviceChangePlanRequest);
		} catch (Exception e) {
			Assert.assertEquals("No Compatible Plans List found for given device Id", e.getMessage());
		}
	}

	/**
	 * Start test suit for getDeviceReviewDetails API
	 * 
	 * @author suresh.kumar
	 */

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonIgnore
	@Test
	public void test_Controller_getDeviceReviewDetailsValidInput() {
		try {
			given(deviceDAOMock.getDeviceReviewDetails(Matchers.anyString()))
					.willReturn(CommonMethods.getReviewsJson());
			cacheDeviceAndReviewController.getDeviceReviewDetails("093353");
		} catch (Exception exception) {
			// assertEquals("com.vf.uk.dal.common.exception.ApplicationException:
			// No reviews found for the given deviceId", exception.toString());
		}
	}

	@Test(expected = ApplicationException.class)
	public void testGetDeviceReviewDetailsNull() {
		given(this.deviceDAOMock.getDeviceReviewDetails("093353")).willReturn(null);
		cacheDeviceAndReviewController.getDeviceReviewDetails("093353");
	}

	@Test
	public void notNullgetCommercialProduct() {
		Assert.assertNotNull(deviceEntityController.getCommercialProduct("093353", null));
		Assert.assertNotNull(deviceEntityController.getCommercialProduct("093353,093329", null));
		Assert.assertNotNull(deviceEntityController.getCommercialProduct(null, "iPhone 7 Silicone Case mid blue"));
		try {
			deviceEntityController.getCommercialProduct(null, null);
		} catch (Exception e) {
		}
		try {
			given(response.getCommercialProductFromJson(Matchers.anyObject())).willReturn(Collections.emptyList());
			deviceEntityController.getCommercialProduct("093353", null);
		} catch (ApplicationException e) {
			Assert.assertEquals("Received Null Values for the given device id", e.getMessage());
		}
	}

	@Test
	public void notProductGroupByGroupType() {
		Assert.assertNotNull(deviceEntityController.getProductGroupByGroupType("DEVICE_PAYM"));
		try {
			deviceEntityController.getProductGroupByGroupType(null);
		} catch (Exception e) {
		}
		try {
			given(response.getListOfGroupFromJson(Matchers.anyObject())).willReturn(Collections.emptyList());
			deviceEntityController.getProductGroupByGroupType("DEVICE_PAYM");
		} catch (Exception e) {
		}
	}

	@Test
	public void notGetProductGroupModel() {
		try {
			given(response.getListOfProductModel(Matchers.anyObject())).willReturn(CommonMethods.getProductModel());
			given(response.getListOfProductGroupModel(Matchers.anyObject()))
					.willReturn(CommonMethods.getProductGroupModelForDeliveryMethod());
			Assert.assertNotNull(deviceEntityController.getProductGroupModel("093353,092660"));
		} catch (Exception e1) {
		}
		try {
			deviceEntityController.getProductGroupModel(null);
		} catch (Exception e) {
		}
		try {
			given(response.getListOfProductModel(Matchers.anyObject())).willReturn(CommonMethods.getProductModel());
			given(response.getListOfProductGroupModel(Matchers.anyObject())).willReturn(new ArrayList<>());
			deviceEntityController.getProductGroupModel("093353,092660");
		} catch (Exception e) {
		}
	}

	@Test
	public void TestDeviceServiceImplUtility() {
		try {
			DeviceServiceImplUtility.getJourney("");
		} catch (Exception e) {
		}
	}

	@Test
	public void TestDeviceServiceImplUtility_One() {
		try {
			DeviceServiceImplUtility.getJourney(Constants.JOURNEY_TYPE_SECONDLINE);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestDeviceServiceImplUtility_Two() {
		try {
			Device device = CommonMethods.getDevice();
			Map<String, MerchandisingPromotion> map = new HashMap<>();
			DeviceServiceImplUtility.getPromotionForDeviceList(map, device);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestDeviceServiceImplUtility_Three() {
		try {
			Map<String, MerchandisingPromotion> map = new HashMap<>();
			map.put("1234", CommonMethods.getMP());
			Device device2 = CommonMethods.getDevice_Two();
			DeviceServiceImplUtility.getPromotionForDeviceList(map, device2);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestDeviceServiceImplUtility_Four() {
		try {
			Map<String, MerchandisingPromotion> map = new HashMap<>();
			map.put("1234", CommonMethods.getMP());
			Device device1 = CommonMethods.getDevice_One();
			DeviceServiceImplUtility.getPromotionForDeviceList(map, device1);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestDeviceServiceImplUtilityHW_Two() {
		try {
			Device device = CommonMethods.getDeviceHW();
			Map<String, MerchandisingPromotion> map = new HashMap<>();
			DeviceServiceImplUtility.getPromotionForDeviceList(map, device);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestDeviceServiceImplUtilityHW_Three() {
		try {
			Map<String, MerchandisingPromotion> map = new HashMap<>();
			map.put("1234", CommonMethods.getMP());
			Device device2 = CommonMethods.getDeviceHW_Two();
			DeviceServiceImplUtility.getPromotionForDeviceList(map, device2);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestDeviceServiceImplUtilityHW_Four() {
		try {
			Map<String, MerchandisingPromotion> map = new HashMap<>();
			map.put("1234", CommonMethods.getMP());
			Device device1 = CommonMethods.getDeviceHW_One();
			DeviceServiceImplUtility.getPromotionForDeviceList(map, device1);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestDeviceServiceImplUtilityWithDATA_Four() {
		try {
			Map<String, MerchandisingPromotion> map = new HashMap<>();
			map.put("1234", CommonMethods.getMP());
			Device device1 = CommonMethods.getDeviceWithDetails_One();
			DeviceServiceImplUtility.getPromotionForDeviceList(map, device1);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestDeviceServiceImplUtilityHWWithDATA_Two() {
		try {
			Device device = CommonMethods.getDeviceWithDetails_Two();
			Map<String, MerchandisingPromotion> map = new HashMap<>();
			map.put("1234", CommonMethods.getMP());
			DeviceServiceImplUtility.getPromotionForDeviceList(map, device);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestDeviceServiceImplUtilityyy() {
		try {
			Device device = CommonMethods.getDeviceWithDetails_Two();
			device.setPromotionsPackage(null);
			List<Device> deviceList = new ArrayList<>();
			deviceList.add(device);
			List<String> promoteAsTags = new ArrayList<>();
			DeviceServiceImplUtility.getPromoteAsForDevice(deviceList, promoteAsTags);
			// DeviceServiceImplUtility.getBundleAndHardwareList(groupType,
			// journeyType, bundleHardwareTupleList, productModel);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetBundleAndHardwareList() {
		try {
			ProductModel pm = new ProductModel();
			pm.setUpgradeLeadPlanId("");
			pm.setNonUpgradeLeadPlanId("");
			List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
			DeviceServiceImplUtility.getBundleAndHardwareList(Constants.STRING_DEVICE_PAYM, "", bundleHardwareTupleList,
					pm);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetBundleAndHardwareList_One() {
		try {
			ProductModel pm = new ProductModel();
			pm.setUpgradeLeadPlanId("1212");
			pm.setNonUpgradeLeadPlanId("1212");
			List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
			DeviceServiceImplUtility.getBundleAndHardwareList(Constants.STRING_DEVICE_PAYM, "", bundleHardwareTupleList,
					pm);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetBundleAndHardwareList_Two() {
		try {
			ProductModel pm = new ProductModel();
			pm.setUpgradeLeadPlanId("1212");
			pm.setNonUpgradeLeadPlanId("1212");
			List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
			DeviceServiceImplUtility.getBundleAndHardwareList(Constants.STRING_DEVICE_PAYM, "Acquisition",
					bundleHardwareTupleList, pm);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetBundleAndHardwareList_Three() {
		try {
			ProductModel pm = new ProductModel();
			pm.setUpgradeLeadPlanId("1212");
			pm.setNonUpgradeLeadPlanId("1212");
			List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
			DeviceServiceImplUtility.getBundleAndHardwareList(Constants.STRING_DEVICE_PAYM,
					Constants.JOURNEY_TYPE_UPGRADE, bundleHardwareTupleList, pm);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetBundleAndHardwareList_OneTwo() {
		try {
			ProductModel pm = new ProductModel();
			pm.setUpgradeLeadPlanId("");
			pm.setNonUpgradeLeadPlanId("");
			List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
			DeviceServiceImplUtility.getBundleAndHardwareList(Constants.STRING_DEVICE_PAYM,
					Constants.JOURNEY_TYPE_UPGRADE, bundleHardwareTupleList, pm);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetBundleAndHardwareList_Four() {
		try {
			ProductModel pm = new ProductModel();
			pm.setUpgradeLeadPlanId("1212");
			pm.setNonUpgradeLeadPlanId("1212");
			List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
			DeviceServiceImplUtility.getBundleAndHardwareList(Constants.STRING_DEVICE_PAYG,
					Constants.JOURNEY_TYPE_ACQUISITION, bundleHardwareTupleList, pm);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetBundleAndHardwareList_Five() {
		try {
			ProductModel pm = new ProductModel();
			pm.setUpgradeLeadPlanId("1212");
			pm.setNonUpgradeLeadPlanId("1212");
			List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
			DeviceServiceImplUtility.getBundleAndHardwareList(Constants.STRING_DEVICE_PAYM,
					Constants.JOURNEY_TYPE_ACQUISITION, bundleHardwareTupleList, pm);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetStartdateFromProductModel() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductStartDate(null);
			DeviceServiceImplUtility.getStartdateFromProductModel(pm);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetStartdateFromProductModel_One() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductStartDate("12-11-2017");
			DeviceServiceImplUtility.getStartdateFromProductModel(pm);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetStartdateFromProductModel_Two() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductStartDate("adasdasd");
			DeviceServiceImplUtility.getStartdateFromProductModel(pm);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetEnddateFromProductModel() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductEndDate(null);
			DeviceServiceImplUtility.getEndDateFromProductModel(pm);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetEnddateFromProductModel_One() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductEndDate("12-11-2017");
			DeviceServiceImplUtility.getEndDateFromProductModel(pm);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetEnddateFromProductModel_Two() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductEndDate("adasdasd");
			DeviceServiceImplUtility.getEndDateFromProductModel(pm);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestdateValidation() {
		try {
			Date startDate = new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-2017");
			Date endDate = new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-3000");
			DeviceServiceImplUtility.dateValidation(null, null, false);
			DeviceServiceImplUtility.dateValidation(null, null, true);
			DeviceServiceImplUtility.dateValidation(startDate, null, true);
			DeviceServiceImplUtility.dateValidation(startDate, null, false);
			DeviceServiceImplUtility.dateValidation(startDate, endDate, true);
			DeviceServiceImplUtility.dateValidation(startDate, endDate, false);
			DeviceServiceImplUtility.dateValidation(null, endDate, true);
			DeviceServiceImplUtility.dateValidation(null, endDate, false);
			DeviceServiceImplUtility.dateValidation(endDate, null, true);
			DeviceServiceImplUtility.dateValidation(endDate, null, false);
			DeviceServiceImplUtility.dateValidation(endDate, startDate, false);
			DeviceServiceImplUtility.dateValidation(null, startDate, false);
			DeviceServiceImplUtility.dateValidation(null, startDate, true);
			DeviceServiceImplUtility.dateValidation(new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-2018"),
					startDate, true);
			DeviceServiceImplUtility.dateValidation(new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-2018"),
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-2017"), true);
			DeviceServiceImplUtility.dateValidation(new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-2018"),
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-2019"), true);
			Date currentDate = new Date();
			DeviceServiceImplUtility.getDateForValidation(currentDate, startDate, endDate);
			DeviceServiceImplUtility.getDateForValidation(currentDate, startDate, endDate);
			DeviceServiceImplUtility.getDateForValidation(currentDate, endDate, startDate);
			DeviceServiceImplUtility.getDateForValidation(currentDate, currentDate, startDate);
			DeviceServiceImplUtility.getDateForValidation(currentDate, currentDate, currentDate);
			DeviceServiceImplUtility.getDateForValidation(currentDate, startDate, currentDate);
			DeviceServiceImplUtility.getDateForValidation(currentDate, startDate, endDate);
			DeviceServiceImplUtility.getDateForValidation(currentDate,
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-3000"), startDate);
			DeviceServiceImplUtility.getDateForValidation(currentDate,
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-3000"),
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-2017"));
			DeviceServiceImplUtility.getDateForValidation(currentDate,
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-3000"),
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-3000"));
			DeviceServiceImplUtility.getDateForValidation(currentDate,
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-3000"), currentDate);

			DeviceServiceImplUtility.dateValidationForOffers_Implementation(null, null, "//");
			DeviceServiceImplUtility.dateValidationForOffers_Implementation(null, null, "//");
			DeviceServiceImplUtility.dateValidationForOffers_Implementation("21-11-2017", null, "//");
			DeviceServiceImplUtility.dateValidationForOffers_Implementation("21-11-2017", null, "//");
			DeviceServiceImplUtility.dateValidationForOffers_Implementation("21-11-2017", "21-11-3000", "//");
			DeviceServiceImplUtility.dateValidationForOffers_Implementation("21-11-2017", "21-11-3000", "//");
			DeviceServiceImplUtility.dateValidationForOffers_Implementation(null, "21-11-3000", "//");
			DeviceServiceImplUtility.dateValidationForOffers_Implementation(null, "21-11-3000", "//");
			DeviceServiceImplUtility.dateValidationForOffers_Implementation("21-11-3000", null, "//");
			DeviceServiceImplUtility.dateValidationForOffers_Implementation("21-11-3000", null, "//");
			DeviceServiceImplUtility.dateValidationForOffers_Implementation("21-11-3000", "21-11-2017", "//");
			DeviceServiceImplUtility.dateValidationForOffers_Implementation(null, "21-11-2017", "//");
			DeviceServiceImplUtility.dateValidationForOffers_Implementation(null, "21-11-2017", "//");
			DeviceServiceImplUtility.dateValidationForOffers_Implementation("21-11-2018", "21-11-2017", "//");
			DeviceServiceImplUtility.dateValidationForOffers_Implementation("21-11-2018", "21-11-2017", "//");
			DeviceServiceImplUtility.dateValidationForOffers_Implementation("21-11-2018", "21-11-2019", "//");
			DeviceServiceImplUtility.dateValidationForOffers_Implementation(null, null, "//");

		} catch (Exception e) {
		}
		try {
			DeviceServiceImplUtility.dateValidationForOffers_Implementation("adasdas", null, "//");
		} catch (Exception e) {
		}
		try {
			DeviceServiceImplUtility.dateValidationForOffers_Implementation(null, "adasdas", "//");
		} catch (Exception e) {
		}
		try {
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList = new ArrayList<>();
			Map<String, Boolean> fromPricingMap = new HashMap<>();
			Map<String, String> leadPlanIdMap = new HashMap<>();
			List<String> listofLeadPlan = new ArrayList<>();
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353();
			DeviceServiceImplUtility.getPricingMap(bundleAndHardwareTupleList, fromPricingMap, leadPlanIdMap,
					listofLeadPlan, cp);
		} catch (Exception e) {
		}

	}

	@Test
	public void TestisMember() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(Constants.STRING_HANDSET);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			DeviceServiceImplUtility.isMember(Constants.JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisMember_One() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(Constants.STRING_HANDSET);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			DeviceServiceImplUtility.isMember(Constants.JOURNEY_TYPE_ACQUISITION,
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisMember_Two() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(Constants.STRING_ACCESSORY);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			DeviceServiceImplUtility.isMember(Constants.JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisMember_Three() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(Constants.STRING_HANDSET);
			pm.setIsDisplayableRet("false");
			pm.setIsSellableRet("true");
			DeviceServiceImplUtility.isMember(Constants.JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisUpgrade() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(Constants.STRING_HANDSET);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			DeviceServiceImplUtility.isUpgradeCheck(Constants.JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisUpgradeCheck_One() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(Constants.STRING_HANDSET);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			DeviceServiceImplUtility.isUpgradeCheck(Constants.JOURNEY_TYPE_ACQUISITION,
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisUpgradeCheck_Two() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(Constants.STRING_ACCESSORY);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			DeviceServiceImplUtility.isUpgradeCheck(Constants.JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisUpgradeCheck_Three() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(Constants.STRING_HANDSET);
			pm.setIsDisplayableRet("false");
			pm.setIsSellableRet("true");
			DeviceServiceImplUtility.isUpgradeCheck(Constants.JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisUpgradeCheck_Four() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(Constants.STRING_HANDSET);
			pm.setIsDisplayableRet("false");
			pm.setIsSellableRet("false");
			DeviceServiceImplUtility.isUpgradeCheck(Constants.JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-3000"),
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-2017"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisNonUpgrade() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(Constants.STRING_HANDSET);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			DeviceServiceImplUtility.isNonUpgradeCheck(Constants.JOURNEY_TYPE_ACQUISITION,
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisNonUpgradeCheck_One() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(Constants.STRING_HANDSET);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			DeviceServiceImplUtility.isNonUpgradeCheck(Constants.JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisNonUpgradeCheck_Two() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(Constants.STRING_ACCESSORY);
			pm.setIsDisplayableAcq("true");
			pm.setIsSellableAcq("true");
			DeviceServiceImplUtility.isNonUpgradeCheck(Constants.JOURNEY_TYPE_ACQUISITION,
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisNonUpgradeCheck_Three() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(Constants.STRING_HANDSET);
			pm.setIsDisplayableAcq("false");
			pm.setIsSellableAcq("true");
			DeviceServiceImplUtility.isNonUpgradeCheck(Constants.JOURNEY_TYPE_ACQUISITION,
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisNonUpgradeCheck_Four() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(Constants.STRING_HANDSET);
			pm.setIsDisplayableAcq("false");
			pm.setIsSellableAcq("true");
			DeviceServiceImplUtility.isNonUpgradeCheck(Constants.JOURNEY_TYPE_ACQUISITION,
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-3000"),
					new SimpleDateFormat(Constants.DATE_FORMAT).parse("21-11-2017"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisRet() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(Constants.STRING_HANDSET);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			DeviceServiceImplUtility.isRet(pm);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisRet_One() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(Constants.STRING_HANDSET);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("false");
			DeviceServiceImplUtility.isRet(pm);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisRet_Two() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(Constants.STRING_HANDSET);
			pm.setIsDisplayableRet("false");
			pm.setIsSellableRet("false");
			DeviceServiceImplUtility.isRet(pm);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisRet_Three() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(Constants.STRING_HANDSET);
			pm.setIsDisplayableRet("false");
			pm.setIsSellableRet("true");
			DeviceServiceImplUtility.isRet(pm);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisAcq() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(Constants.STRING_HANDSET);
			pm.setIsDisplayableAcq("true");
			pm.setIsSellableAcq("true");
			DeviceServiceImplUtility.isAcq(pm);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisAcq_One() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(Constants.STRING_HANDSET);
			pm.setIsDisplayableAcq("true");
			pm.setIsSellableAcq("false");
			DeviceServiceImplUtility.isAcq(pm);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisAcq_Two() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(Constants.STRING_HANDSET);
			pm.setIsDisplayableAcq("false");
			pm.setIsSellableAcq("false");
			DeviceServiceImplUtility.isAcq(pm);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisAcq_Three() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(Constants.STRING_HANDSET);
			pm.setIsDisplayableAcq("false");
			pm.setIsSellableAcq("true");
			DeviceServiceImplUtility.isAcq(pm);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisNonUpgrade_Two() {
		try {
			DeviceServiceImplUtility.isNonUpgrade("");
			DeviceServiceImplUtility.isNonUpgrade("asa");
			DeviceServiceImplUtility.getSortCriteriaForList(null);
			DeviceServiceImplUtility.getSortCriteriaForList("++adadad");
			DeviceServiceImplUtility.getSortCriteriaForList(Constants.SORT_HYPEN);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestvalidateGroupType() {
		try {
			DeviceServiceImplUtility.validateGroupType(Constants.STRING_DEVICE_PAYM);
			DeviceServiceImplUtility.validateGroupType(Constants.STRING_DEVICE_PAYG);
			DeviceServiceImplUtility.validateGroupType("abc");
			DeviceServiceImplUtility.validateGroupType(Constants.STRING_DEVICE_NEARLY_NEW);
			DeviceServiceImplUtility.validateGroupType(Constants.STRING_DATADEVICE_PAYM);
			DeviceServiceImplUtility.validateGroupType(Constants.STRING_DATADEVICE_PAYG);
			DeviceServiceImplUtility.getJourneyForMakeAndModel("");
			DeviceServiceImplUtility.getJourneyForMakeAndModel(Constants.JOURNEY_TYPE_ACQUISITION);
			DeviceServiceImplUtility.getJourneyForMakeAndModel(Constants.JOURNEY_TYPE_UPGRADE);
			DeviceServiceImplUtility.getJourneyForMakeAndModel(Constants.JOURNEY_TYPE_SECONDLINE);
			DeviceServiceImplUtility.getJourneyForMakeAndModel("avcv");
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353();
			cp.setProductControl(null);
			DeviceServiceImplUtility.isNonUpgradeCommercialProduct(cp);
			ProductControl pc = new ProductControl();
			pc.setDisplayableAcq(false);
			pc.setSellableAcq(false);
			cp.setProductControl(pc);
			DeviceServiceImplUtility.isNonUpgradeCommercialProduct(cp);
			pc.setDisplayableAcq(true);
			pc.setSellableAcq(false);
			cp.setProductControl(pc);
			DeviceServiceImplUtility.isNonUpgradeCommercialProduct(cp);
			pc.setDisplayableAcq(true);
			pc.setSellableAcq(true);
			cp.setProductControl(pc);
			DeviceServiceImplUtility.isNonUpgradeCommercialProduct(cp);
			pc.setDisplayableAcq(false);
			pc.setSellableAcq(true);
			cp.setProductControl(pc);
			DeviceServiceImplUtility.isNonUpgradeCommercialProduct(cp);

			cp.setProductControl(null);
			DeviceServiceImplUtility.isUpgradeFromCommercialProduct(cp);
			pc.setDisplayableRet(false);
			pc.setSellableRet(false);
			cp.setProductControl(pc);
			DeviceServiceImplUtility.isUpgradeFromCommercialProduct(cp);
			pc.setDisplayableRet(true);
			pc.setSellableRet(false);
			cp.setProductControl(pc);
			DeviceServiceImplUtility.isUpgradeFromCommercialProduct(cp);
			pc.setDisplayableRet(true);
			pc.setSellableRet(true);
			cp.setProductControl(pc);
			DeviceServiceImplUtility.isUpgradeFromCommercialProduct(cp);
			pc.setDisplayableRet(false);
			pc.setSellableRet(true);
			cp.setProductControl(pc);
			DeviceServiceImplUtility.isUpgradeFromCommercialProduct(cp);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetBundlePriceBasedOnDiscountDuration_Implementation() {
		try {
			DeviceServiceImplUtility.getBundlePriceBasedOnDiscountDuration_Implementation(null, null);
		} catch (Exception e) {
		}
		try {
			DeviceServiceImplUtility.getBundlePriceBasedOnDiscountDuration_Implementation(null,
					Constants.FULL_DURATION_DISCOUNT);
		} catch (Exception e) {
		}
		try {
			DeviceServiceImplUtility.getBundlePriceBasedOnDiscountDuration_Implementation(null,
					Constants.LIMITED_TIME_DISCOUNT);
		} catch (Exception e) {
		}
		try {
			DeviceServiceImplUtility.getBundlePriceBasedOnDiscountDuration_Implementation(null, "abc");
		} catch (Exception e) {
		}
		try {
			DeviceSummary ds = new DeviceSummary();
			PriceForBundleAndHardware pb = new PriceForBundleAndHardware();
			BundlePrice bp = new BundlePrice();
			bp.setMonthlyDiscountPrice(null);
			pb.setBundlePrice(bp);
			ds.setPriceInfo(pb);
			DeviceServiceImplUtility.getBundlePriceBasedOnDiscountDuration_Implementation(ds,
					Constants.FULL_DURATION_DISCOUNT);
		} catch (Exception e) {
		}
		try {
			DeviceSummary ds = new DeviceSummary();
			PriceForBundleAndHardware pb = new PriceForBundleAndHardware();
			BundlePrice bp = new BundlePrice();
			Price mp = new Price();
			mp.setGross(null);
			bp.setMonthlyDiscountPrice(mp);
			pb.setBundlePrice(bp);
			ds.setPriceInfo(pb);
			DeviceServiceImplUtility.getBundlePriceBasedOnDiscountDuration_Implementation(ds,
					Constants.FULL_DURATION_DISCOUNT);
		} catch (Exception e) {
		}
		try {
			DeviceSummary ds = new DeviceSummary();
			PriceForBundleAndHardware pb = new PriceForBundleAndHardware();
			BundlePrice bp = new BundlePrice();
			Price mp = new Price();
			mp.setGross("1212");
			bp.setMonthlyDiscountPrice(mp);
			pb.setBundlePrice(bp);
			ds.setPriceInfo(pb);
			DeviceServiceImplUtility.getBundlePriceBasedOnDiscountDuration_Implementation(ds,
					Constants.FULL_DURATION_DISCOUNT);
		} catch (Exception e) {
		}
		try {
			DeviceSummary ds = new DeviceSummary();
			PriceForBundleAndHardware pb = new PriceForBundleAndHardware();
			BundlePrice bp = new BundlePrice();
			Price mp = new Price();
			mp.setGross("1212");
			bp.setMonthlyDiscountPrice(mp);
			pb.setBundlePrice(bp);
			ds.setPriceInfo(pb);
			DeviceServiceImplUtility.getBundlePriceBasedOnDiscountDuration_Implementation(ds,
					Constants.LIMITED_TIME_DISCOUNT);
		} catch (Exception e) {
		}
		try {
			DeviceSummary ds = new DeviceSummary();
			PriceForBundleAndHardware pb = new PriceForBundleAndHardware();
			BundlePrice bp = new BundlePrice();
			bp.setMonthlyDiscountPrice(null);
			pb.setBundlePrice(bp);
			ds.setPriceInfo(pb);
			DeviceServiceImplUtility.getBundlePriceBasedOnDiscountDuration_Implementation(ds,
					Constants.LIMITED_TIME_DISCOUNT);
		} catch (Exception e) {
		}
		try {
			DeviceSummary ds = new DeviceSummary();
			PriceForBundleAndHardware pb = new PriceForBundleAndHardware();
			BundlePrice bp = new BundlePrice();
			Price mp = new Price();
			mp.setGross(null);
			bp.setMonthlyDiscountPrice(mp);
			pb.setBundlePrice(bp);
			ds.setPriceInfo(pb);
			DeviceServiceImplUtility.getBundlePriceBasedOnDiscountDuration_Implementation(ds,
					Constants.LIMITED_TIME_DISCOUNT);
		} catch (Exception e) {
		}
		try {
			DeviceSummary ds = new DeviceSummary();
			PriceForBundleAndHardware pb = new PriceForBundleAndHardware();
			BundlePrice bp = new BundlePrice();
			Price mp = new Price();
			mp.setGross("1212");
			bp.setMonthlyDiscountPrice(mp);
			pb.setBundlePrice(bp);
			ds.setPriceInfo(pb);
			DeviceServiceImplUtility.getBundlePriceBasedOnDiscountDuration_Implementation(ds,
					Constants.LIMITED_TIME_DISCOUNT);
		} catch (Exception e) {
		}
		try {
			DeviceSummary ds = new DeviceSummary();
			PriceForBundleAndHardware pb = new PriceForBundleAndHardware();
			BundlePrice bp = new BundlePrice();
			Price mp = new Price();
			mp.setGross("1212");
			bp.setMonthlyDiscountPrice(mp);
			pb.setBundlePrice(bp);
			ds.setPriceInfo(pb);
			DeviceServiceImplUtility.getBundlePriceBasedOnDiscountDuration_Implementation(ds,
					Constants.LIMITED_TIME_DISCOUNT);
		} catch (Exception e) {
		}
	}

}
