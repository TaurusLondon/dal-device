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
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.device.aspect.CatalogServiceAspect;
import com.vf.uk.dal.device.beans.test.DeviceTestBeans;
import com.vf.uk.dal.device.client.converter.ResponseMappingHelper;
import com.vf.uk.dal.device.client.entity.bundle.BundleDetailsForAppSrv;
import com.vf.uk.dal.device.client.entity.bundle.BundleModelAndPrice;
import com.vf.uk.dal.device.client.entity.customer.RecommendedProductListResponse;
import com.vf.uk.dal.device.client.entity.price.BundleAndHardwareTuple;
import com.vf.uk.dal.device.client.entity.price.BundlePrice;
import com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion;
import com.vf.uk.dal.device.client.entity.price.Price;
import com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware;
import com.vf.uk.dal.device.common.test.CommonMethods;
import com.vf.uk.dal.device.controller.CacheDeviceAndReviewController;
import com.vf.uk.dal.device.controller.DeviceController;
import com.vf.uk.dal.device.controller.DeviceDetailsController;
import com.vf.uk.dal.device.controller.DeviceEntityController;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.dao.DeviceTileCacheDAOImpl;
import com.vf.uk.dal.device.model.CacheDeviceTileResponse;
import com.vf.uk.dal.device.model.Device;
import com.vf.uk.dal.device.model.DeviceDetails;
import com.vf.uk.dal.device.model.DeviceSummary;
import com.vf.uk.dal.device.model.product.CommercialProduct;
import com.vf.uk.dal.device.model.product.ProductControl;
import com.vf.uk.dal.device.model.product.ProductModel;
import com.vf.uk.dal.device.service.DeviceService;
import com.vf.uk.dal.device.utils.DeviceConditionallHelper;
import com.vf.uk.dal.device.utils.DeviceServiceImplUtility;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.device.utils.Validator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeviceTestBeans.class)
public class OtherServiesTest {

	public static final String LIMITED_TIME_DISCOUNT = "limited_time";
	public static final String FULL_DURATION_DISCOUNT = "full_duration";
	public static final String JOURNEY_TYPE_UPGRADE = "Upgrade";
	public static final String JOURNEY_TYPE_SECONDLINE = "SecondLine";
	public static final String JOURNEY_TYPE_ACQUISITION = "Acquisition";
	public static final String STRING_DEVICE_PAYM = "DEVICE_PAYM";
	public static final String STRING_DEVICE_PAYG = "DEVICE_PAYG";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String STRING_HANDSET = "Handset";
	public static final String STRING_ACCESSORY = "Accessory,Compatible Accessories";
	public static final String STRING_DEVICE_NEARLY_NEW = "DEVICE_NEARLY_NEW";
	public static final String STRING_DATADEVICE_PAYM = "DATA_DEVICE_PAYM";
	public static final String STRING_DATADEVICE_PAYG = "DATA_DEVICE_PAYG";
	
	@MockBean
	DeviceTileCacheDAOImpl deviceCacheDAOMock;

	@MockBean
	DeviceDao deviceDAOMock;

	@Autowired
	DeviceController deviceController;

	@MockBean
	ResponseMappingHelper response;

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
	public void testGetConditionalCriteriaForDeviceList() {
		Map<String, String> listOfProductVariants = new HashMap<>();
		listOfProductVariants.put("2", "093353");
		listOfProductVariants.put("3", "093354");
		given(deviceConditionallHelper.getLeadDeviceMap(ArgumentMatchers.any())).willReturn(listOfProductVariants);
		BundleModelAndPrice bundleModelAndPrice = new BundleModelAndPrice();
		bundleModelAndPrice.setBundleModel(CommonMethods.getBundleModelListForBundleList().get(0));
		bundleModelAndPrice.setBundlePrice(CommonMethods.getBundlePrice());
		given(deviceConditionallHelper.calculatePlan(ArgumentMatchers.anyFloat(), ArgumentMatchers.any(), ArgumentMatchers.any()))
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
		given(deviceDAOMock.getPriceForBundleAndHardware(ArgumentMatchers.anyList(), ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString())).willReturn(CommonMethods.getPriceForBundleAndHardwareListFromTupleList());
		deviceDetails = deviceDetailsController
				.getListOfDeviceDetails(CommonMethods.getQueryParamsMapForDeviceDetails("093353"));
		Assert.assertNotNull(deviceDetails);
	}

	@Test
	public void notNullTestForgetListOfDeviceDetailsWithoutLeadPlanId() {
		List<DeviceDetails> deviceDetails;
		CommercialProduct commercial = CommonMethods.getCommercialProductByDeviceId_093353();
		commercial.setLeadPlanId(null);
		given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(commercial);
		given(response.getMerchandisingPromotion(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getMerchPromotionForListOfDevicedetails());
		given(deviceDAOMock.getPriceForBundleAndHardware(ArgumentMatchers.anyList(), ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString())).willReturn(CommonMethods.getPriceForBundleAndHardwareListFromTupleList());
		deviceDetails = deviceDetailsController
				.getListOfDeviceDetails(CommonMethods.getQueryParamsMapForDeviceDetails("093353"));
		Assert.assertNotNull(deviceDetails);
	}

	@Test
	public void nuullTestForgetListOfDeviceDetailsWithoutLeadPlanId() {
		given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(null);
		given(response.getMerchandisingPromotion(ArgumentMatchers.any())).willReturn(null);
		given(deviceDAOMock.getPriceForBundleAndHardware(ArgumentMatchers.anyList(), ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString())).willReturn(Collections.emptyList());
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
			Map<String, String> map = new HashMap<>();
			deviceDetails = deviceDetailsController
					.getListOfDeviceDetails(map);
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
			given(deviceDAOMock.getDeviceReviewDetails(ArgumentMatchers.anyString()))
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
			given(response.getCommercialProductFromJson(ArgumentMatchers.any())).willReturn(Collections.emptyList());
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
			given(response.getListOfGroupFromJson(ArgumentMatchers.any())).willReturn(Collections.emptyList());
			deviceEntityController.getProductGroupByGroupType("DEVICE_PAYM");
		} catch (Exception e) {
		}
	}

	@Test
	public void notGetProductGroupModel() {
		try {
			given(response.getListOfProductModel(ArgumentMatchers.any())).willReturn(CommonMethods.getProductModel());
			given(response.getListOfProductGroupModel(ArgumentMatchers.any()))
					.willReturn(CommonMethods.getProductGroupModelForDeliveryMethod());
			Assert.assertNotNull(deviceEntityController.getProductGroupModel("093353,092660"));
		} catch (Exception e1) {
		}
		try {
			deviceEntityController.getProductGroupModel(null);
		} catch (Exception e) {
		}
		try {
			given(response.getListOfProductModel(ArgumentMatchers.any())).willReturn(CommonMethods.getProductModel());
			given(response.getListOfProductGroupModel(ArgumentMatchers.any())).willReturn(new ArrayList<>());
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
			DeviceServiceImplUtility.getJourney(JOURNEY_TYPE_SECONDLINE);
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
			DeviceServiceImplUtility.getBundleAndHardwareList(STRING_DEVICE_PAYM, "", bundleHardwareTupleList,
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
			DeviceServiceImplUtility.getBundleAndHardwareList(STRING_DEVICE_PAYM, "", bundleHardwareTupleList,
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
			DeviceServiceImplUtility.getBundleAndHardwareList(STRING_DEVICE_PAYM, "Acquisition",
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
			DeviceServiceImplUtility.getBundleAndHardwareList(STRING_DEVICE_PAYM,
					JOURNEY_TYPE_UPGRADE, bundleHardwareTupleList, pm);
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
			DeviceServiceImplUtility.getBundleAndHardwareList(STRING_DEVICE_PAYM,
					JOURNEY_TYPE_UPGRADE, bundleHardwareTupleList, pm);
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
			DeviceServiceImplUtility.getBundleAndHardwareList(STRING_DEVICE_PAYG,
					JOURNEY_TYPE_ACQUISITION, bundleHardwareTupleList, pm);
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
			DeviceServiceImplUtility.getBundleAndHardwareList(STRING_DEVICE_PAYM,
					JOURNEY_TYPE_ACQUISITION, bundleHardwareTupleList, pm);
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
			Date startDate = new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017");
			Date endDate = new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000");
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
			DeviceServiceImplUtility.dateValidation(new SimpleDateFormat(DATE_FORMAT).parse("21-11-2018"),
					startDate, true);
			DeviceServiceImplUtility.dateValidation(new SimpleDateFormat(DATE_FORMAT).parse("21-11-2018"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"), true);
			DeviceServiceImplUtility.dateValidation(new SimpleDateFormat(DATE_FORMAT).parse("21-11-2018"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2019"), true);
			Date currentDate = new Date();
			DeviceServiceImplUtility.getDateForValidation(currentDate, startDate, endDate);
			DeviceServiceImplUtility.getDateForValidation(currentDate, startDate, endDate);
			DeviceServiceImplUtility.getDateForValidation(currentDate, endDate, startDate);
			DeviceServiceImplUtility.getDateForValidation(currentDate, currentDate, startDate);
			DeviceServiceImplUtility.getDateForValidation(currentDate, currentDate, currentDate);
			DeviceServiceImplUtility.getDateForValidation(currentDate, startDate, currentDate);
			DeviceServiceImplUtility.getDateForValidation(currentDate, startDate, endDate);
			DeviceServiceImplUtility.getDateForValidation(currentDate,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), startDate);
			DeviceServiceImplUtility.getDateForValidation(currentDate,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"));
			DeviceServiceImplUtility.getDateForValidation(currentDate,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"));
			DeviceServiceImplUtility.getDateForValidation(currentDate,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), currentDate);

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
			pm.setProductClass(STRING_HANDSET);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			DeviceServiceImplUtility.isMember(JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisMember_One() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_HANDSET);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			DeviceServiceImplUtility.isMember(JOURNEY_TYPE_ACQUISITION,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisMember_Two() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_ACCESSORY);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			DeviceServiceImplUtility.isMember(JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisMember_Three() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_HANDSET);
			pm.setIsDisplayableRet("false");
			pm.setIsSellableRet("true");
			DeviceServiceImplUtility.isMember(JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisUpgrade() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_HANDSET);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			DeviceServiceImplUtility.isUpgradeCheck(JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisUpgradeCheck_One() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_HANDSET);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			DeviceServiceImplUtility.isUpgradeCheck(JOURNEY_TYPE_ACQUISITION,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisUpgradeCheck_Two() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_ACCESSORY);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			DeviceServiceImplUtility.isUpgradeCheck(JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisUpgradeCheck_Three() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_HANDSET);
			pm.setIsDisplayableRet("false");
			pm.setIsSellableRet("true");
			DeviceServiceImplUtility.isUpgradeCheck(JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisUpgradeCheck_Four() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_HANDSET);
			pm.setIsDisplayableRet("false");
			pm.setIsSellableRet("false");
			DeviceServiceImplUtility.isUpgradeCheck(JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisNonUpgrade() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_HANDSET);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			DeviceServiceImplUtility.isNonUpgradeCheck(JOURNEY_TYPE_ACQUISITION,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisNonUpgradeCheck_One() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_HANDSET);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			DeviceServiceImplUtility.isNonUpgradeCheck(JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisNonUpgradeCheck_Two() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_ACCESSORY);
			pm.setIsDisplayableAcq("true");
			pm.setIsSellableAcq("true");
			DeviceServiceImplUtility.isNonUpgradeCheck(JOURNEY_TYPE_ACQUISITION,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisNonUpgradeCheck_Three() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_HANDSET);
			pm.setIsDisplayableAcq("false");
			pm.setIsSellableAcq("true");
			DeviceServiceImplUtility.isNonUpgradeCheck(JOURNEY_TYPE_ACQUISITION,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisNonUpgradeCheck_Four() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_HANDSET);
			pm.setIsDisplayableAcq("false");
			pm.setIsSellableAcq("true");
			DeviceServiceImplUtility.isNonUpgradeCheck(JOURNEY_TYPE_ACQUISITION,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"), pm, true);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestisRet() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_HANDSET);
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
			pm.setProductClass(STRING_HANDSET);
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
			pm.setProductClass(STRING_HANDSET);
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
			pm.setProductClass(STRING_HANDSET);
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
			pm.setProductClass(STRING_HANDSET);
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
			pm.setProductClass(STRING_HANDSET);
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
			pm.setProductClass(STRING_HANDSET);
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
			pm.setProductClass(STRING_HANDSET);
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
			DeviceServiceImplUtility.getSortCriteriaForList("-");
		} catch (Exception e) {
		}
	}

	@Test
	public void TestvalidateGroupType() {
		try {
			DeviceServiceImplUtility.validateGroupType(STRING_DEVICE_PAYM);
			DeviceServiceImplUtility.validateGroupType(STRING_DEVICE_PAYG);
			DeviceServiceImplUtility.validateGroupType("abc");
			DeviceServiceImplUtility.validateGroupType(STRING_DEVICE_NEARLY_NEW);
			DeviceServiceImplUtility.validateGroupType(STRING_DATADEVICE_PAYM);
			DeviceServiceImplUtility.validateGroupType(STRING_DATADEVICE_PAYG);
			DeviceServiceImplUtility.getJourneyForMakeAndModel("");
			DeviceServiceImplUtility.getJourneyForMakeAndModel(JOURNEY_TYPE_ACQUISITION);
			DeviceServiceImplUtility.getJourneyForMakeAndModel(JOURNEY_TYPE_UPGRADE);
			DeviceServiceImplUtility.getJourneyForMakeAndModel(JOURNEY_TYPE_SECONDLINE);
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
					FULL_DURATION_DISCOUNT);
		} catch (Exception e) {
		}
		try {
			DeviceServiceImplUtility.getBundlePriceBasedOnDiscountDuration_Implementation(null,
					LIMITED_TIME_DISCOUNT);
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
					FULL_DURATION_DISCOUNT);
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
					FULL_DURATION_DISCOUNT);
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
					FULL_DURATION_DISCOUNT);
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
					LIMITED_TIME_DISCOUNT);
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
					LIMITED_TIME_DISCOUNT);
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
					LIMITED_TIME_DISCOUNT);
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
					LIMITED_TIME_DISCOUNT);
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
					LIMITED_TIME_DISCOUNT);
		} catch (Exception e) {
		}
	}

	@Test
	public void testvalidateDeviceId() {
		try {
			Validator.validateDeviceId("");
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(), ExceptionMessages.INVALID_INPUT_MISSING_DEVICEID);
		}
		try {
			Validator.validateDeviceId("9854");
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(), ExceptionMessages.INVALID_DEVICE_ID);
		}
		try {
			Validator.validateDeviceId("09854");
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(), ExceptionMessages.INVALID_DEVICE_ID);
		}
		try {
			Validator.validateDeviceId("093353");
		} catch (Exception e) {
		}
		try {
			Validator.validateDeviceId("985451");
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(), ExceptionMessages.INVALID_DEVICE_ID);
		}
		try {
			Validator.validateIncludeRecommendation("false");
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(), ExceptionMessages.INVALID_INCLUDERECOMMENDATION);
		}
		try {
			Validator.validateMSISDN("1312312312", "false");
		} catch (Exception e) {
		}
		try {
			Validator.validateForCreditLimit("07896das");
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(), ExceptionMessages.INVALID_CREDIT_LIMIT);
		}
		
	}
	
}
