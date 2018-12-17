package com.vf.uk.dal.device.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
import com.vf.uk.dal.device.aspect.CatalogServiceAspect;
import com.vf.uk.dal.device.beans.test.DeviceTestBeans;
import com.vf.uk.dal.device.client.PromotionServiceClient;
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
import com.vf.uk.dal.device.exception.DeviceCustomException;
import com.vf.uk.dal.device.model.CacheDeviceTileResponse;
import com.vf.uk.dal.device.model.Device;
import com.vf.uk.dal.device.model.DeviceDetails;
import com.vf.uk.dal.device.model.DeviceSummary;
import com.vf.uk.dal.device.model.merchandisingpromotion.DevicePreCalculatedData;
import com.vf.uk.dal.device.model.product.CacheOfferAppliedPriceModel;
import com.vf.uk.dal.device.model.product.CacheProductModel;
import com.vf.uk.dal.device.model.product.CommercialProduct;
import com.vf.uk.dal.device.model.product.ProductControl;
import com.vf.uk.dal.device.model.product.ProductModel;
import com.vf.uk.dal.device.model.productgroups.Group;
import com.vf.uk.dal.device.service.CacheDeviceServiceImpl;
import com.vf.uk.dal.device.service.DeviceService;
import com.vf.uk.dal.device.utils.AccessoriesAndInsurancedaoUtils;
import com.vf.uk.dal.device.utils.CacheDeviceDaoUtils;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.DeviceConditionallHelper;
import com.vf.uk.dal.device.utils.DeviceDetailsMakeAndModelVaiantDaoUtils;
import com.vf.uk.dal.device.utils.DeviceESHelper;
import com.vf.uk.dal.device.utils.DeviceServiceCommonUtility;
import com.vf.uk.dal.device.utils.DeviceServiceImplUtility;
import com.vf.uk.dal.device.utils.DeviceTilesDaoUtils;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.device.utils.ListOfDeviceDetailsDaoUtils;
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

	@Autowired
	CacheDeviceDaoUtils cacheDeviceDaoUtils;

	@Autowired
	AccessoriesAndInsurancedaoUtils accessoriesAndInsurancedaoUtils;

	@Autowired
	CommonUtility commonUtility;

	@Autowired
	ListOfDeviceDetailsDaoUtils listOfDeviceDetailsDaoUtils;

	@Autowired
	DeviceServiceImplUtility deviceServiceImplUtility;

	@Autowired
	DeviceDetailsMakeAndModelVaiantDaoUtils deviceDetailsMakeAndModelVaiantDaoUtils;

	@Autowired
	Validator validator;

	@Autowired
	AccessoriesAndInsurancedaoUtils AccessoriesAndInsurancedaoUtils;

	@Autowired
	DeviceTilesDaoUtils deviceTilesDaoUtils;

	@Autowired
	DeviceESHelper deviceESHelper;

	@Autowired
	DeviceServiceCommonUtility utility;
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

	@Autowired
	CacheDeviceServiceImpl cacheDeviceService;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	PromotionServiceClient promotionServiceClient;

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
		given(deviceConditionallHelper.calculatePlan(ArgumentMatchers.anyFloat(), ArgumentMatchers.any(),
				ArgumentMatchers.any())).willReturn(bundleModelAndPrice);
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
	public void testCacheDeviceTileServiceNull() {
		thrown.expect(DeviceCustomException.class);
		thrown.expectMessage(ExceptionMessages.NULL_VALUE_GROUP_TYPE);
		cacheDeviceService.setListOfProductGroupRepository(null, null, null, null, null, null, null, null,
				null, null, null, null, null);
	}
	
	@Test
	public void testCacheDeviceTileServicEmpty() {
		thrown.expect(DeviceCustomException.class);
		thrown.expectMessage(ExceptionMessages.NULL_VALUE_GROUP_TYPE);
		List<Group> listOfProductGroup = new ArrayList<>();
		cacheDeviceService.setListOfProductGroupRepository(null, null, null, null, listOfProductGroup, null, null, null,
				null, null, null, null, null);
	}
	@Test
	public void testSetFinancingOptions() {
		cacheDeviceService.setFinancingOptions(CommonMethods.getDeviceListObject(), new CacheProductModel());
		cacheDeviceService.setFinanceOptions(new CacheOfferAppliedPriceModel(), CommonMethods.getDeviceFinaceOptionsMp());
	}
	
	@Test
	public void testsetPaygOneOffDiscountedPrice() {
		DevicePreCalculatedData deviceData = CommonMethods.getDeviceListObject();
		deviceData.setGroupType("DEVICE_PAYM");
		cacheDeviceService.setPaygOneOffDiscountedPrice(deviceData, new CacheProductModel());
		cacheDeviceService.setPaygOneOffPrice(deviceData, new CacheProductModel());
		cacheDeviceService.setPaygProductGroupId(deviceData, new CacheProductModel());
		deviceData.setGroupType("DEVICE_PAYG");
		cacheDeviceService.setPaygOneOffDiscountedPrice(deviceData, new CacheProductModel());
		cacheDeviceService.setPaygOneOffPrice(deviceData, new CacheProductModel());
		cacheDeviceService.setPaygProductGroupId(deviceData, new CacheProductModel());
		deviceData.setPaygProductGroupId("110121");
		cacheDeviceService.setPaygProductGroupId(deviceData, new CacheProductModel());
	}
	
	@Test
	public void nulltestGetListOfDeviceDetailsForExceptionWithOffer() {
		Map<String, String> queryparams = new HashMap<>();
		queryparams.put("journeyType", "journeyType");
		queryparams.put("offerCode", "W_HH_OC_01");
		queryparams.put("deviceId", "093353");
		List<DeviceDetails> device = deviceDetailsController.getListOfDeviceDetails(queryparams);
		assertNotNull(device);
		assertEquals("092572", device.get(0).getDeviceId());
	}

	@Test
	public void nulltestGetListOfDeviceDetailsForException() {
		Map<String, String> queryparams = new HashMap<>();
		queryparams.put("journeyType", null);
		queryparams.put("offerCode", "W_HH_OC_01");
		queryparams.put("deviceId", "093353");
		List<DeviceDetails> device = deviceDetailsController.getListOfDeviceDetails(queryparams);
		assertNotNull(device);
		assertEquals("092572", device.get(0).getDeviceId());
	}

	@Test
	public void nullDeviceIdtestGetListOfDeviceDetailsForExceptionWithoutDeviceId() {
		try {
			Map<String, String> queryparams = new HashMap<>();
			queryparams.put("journeyType", "journeyType");
			queryparams.put("offerCode", "W_HH_OC_01");
			deviceDetailsController.getListOfDeviceDetails(queryparams);
		} catch (Exception e) {
			assertEquals("Invalid input request received. Missing Device Id", e.getMessage());
		}
	}

	@Test
	public void notNullTestForGetListOfDeviceDetails() {
		List<DeviceDetails> deviceDetails;
		given(deviceDAOMock.getPriceForBundleAndHardware(ArgumentMatchers.anyList(), ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString()))
						.willReturn(CommonMethods.getPriceForBundleAndHardwareListFromTupleList());
		deviceDetails = deviceDetailsController
				.getListOfDeviceDetails(CommonMethods.getQueryParamsMapForDeviceDetails("093353"));
		Assert.assertNotNull(deviceDetails);
		assertEquals("092572", deviceDetails.get(0).getDeviceId());
	}

	@Test
	public void notNullTestForGetListOfDeviceDetailsWithoutLeadPlanId() {
		List<DeviceDetails> deviceDetails;
		CommercialProduct commercial = CommonMethods.getCommercialProductByDeviceId_093353();
		commercial.setLeadPlanId(null);
		given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(commercial);
		given(response.getMerchandisingPromotion(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getMerchPromotionForListOfDevicedetails());
		given(deviceDAOMock.getPriceForBundleAndHardware(ArgumentMatchers.anyList(), ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString()))
						.willReturn(CommonMethods.getPriceForBundleAndHardwareListFromTupleList());
		deviceDetails = deviceDetailsController
				.getListOfDeviceDetails(CommonMethods.getQueryParamsMapForDeviceDetails("093353"));
		Assert.assertNotNull(deviceDetails);
		assertEquals("093353", deviceDetails.get(0).getDeviceId());
	}

	@Test
	public void nullTestForGetListOfDeviceDetailsWithoutLeadPlanId() {
		given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(null);
		given(response.getMerchandisingPromotion(ArgumentMatchers.any())).willReturn(null);
		given(deviceDAOMock.getPriceForBundleAndHardware(ArgumentMatchers.anyList(), ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString())).willReturn(Collections.emptyList());
		try {
			deviceDetailsController
					.getListOfDeviceDetails(CommonMethods.getQueryParamsMapForDeviceDetails("093353,090572"));
		} catch (DeviceCustomException e) {
			Assert.assertEquals("Invalid Device Id Sent In Request", e.getMessage());
		}
	}

	@Test
	public void emptyParamTestForGetListOfDeviceDetails() throws Exception {
		List<DeviceDetails> deviceDetails = null;
		try {
			Map<String, String> map = new HashMap<>();
			deviceDetails = deviceDetailsController.getListOfDeviceDetails(map);
		} catch (Exception e) {
			Assert.assertEquals("Invalid query parameters", e.getMessage());
		}
		Assert.assertNull(deviceDetails);
	}

	@Test
	public void invalidParamTestForGetListOfDeviceDetails() throws Exception {
		List<DeviceDetails> deviceDetails = null;
		try {
			deviceDetails = deviceDetailsController
					.getListOfDeviceDetails(CommonMethods.getQueryParamsMapForDeviceDetails(null, null));
		} catch (Exception e) {
			Assert.assertEquals("Invalid input request received. Missing Device Id", e.getMessage());
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
			JSONObject json = cacheDeviceAndReviewController.getDeviceReviewDetails("093353");
			assertNotNull(json);
		} catch (Exception exception) {
		}
	}

	@Test
	public void testGetDeviceReviewDetailsNull() {
		try {
			given(this.deviceDAOMock.getDeviceReviewDetails("093353")).willReturn(null);
			cacheDeviceAndReviewController.getDeviceReviewDetails("093353");
		} catch (Exception exception) {
			assertEquals("No reviews found for the given deviceId", exception.getMessage());
		}
	}

	@Test
	public void notNullgetCommercialProduct() {
		List<CommercialProduct> cp = deviceEntityController.getCommercialProduct("093353", null);
		Assert.assertNotNull(deviceEntityController.getCommercialProduct("093353,093329", null));
		Assert.assertNotNull(deviceEntityController.getCommercialProduct(null, "iPhone 7 Silicone Case mid blue"));
		assertEquals("093329", cp.get(0).getId());
		assertEquals(179050, cp.get(0).getOrder(), 0);
		assertEquals("iPhone 7 Silicone Case mid blue", cp.get(0).getName());
		assertEquals("Accessories", cp.get(0).getProductClass());
		try {
			deviceEntityController.getCommercialProduct(null, null);
		} catch (Exception e) {
			assertEquals("Invalid query parameters", e.getMessage());
		}
		try {
			given(response.getCommercialProductFromJson(ArgumentMatchers.any())).willReturn(Collections.emptyList());
			deviceEntityController.getCommercialProduct("093353", null);
		} catch (DeviceCustomException e) {
			Assert.assertEquals("Received Null Values for the given device id", e.getMessage());
		}
	}

	@Test
	public void notProductGroupByGroupType() {
		List<com.vf.uk.dal.device.model.productgroups.Group> group = deviceEntityController
				.getProductGroupByGroupType("DEVICE_PAYM");
		assertNotNull(group);
		assertEquals("Apple iPhone 6s", group.get(0).getName());
		assertEquals(3, group.get(0).getGroupPriority(), 0);
		assertEquals("DEVICE", group.get(0).getGroupType());
		try {
			deviceEntityController.getProductGroupByGroupType(null);
		} catch (Exception e) {
			assertEquals("Invalid query parameters", e.getMessage());
		}
		try {
			given(response.getListOfGroupFromJson(ArgumentMatchers.any())).willReturn(Collections.emptyList());
			deviceEntityController.getProductGroupByGroupType("DEVICE_PAYM");
		} catch (Exception e) {
			assertEquals("Received Null Values for the given product group type", e.getMessage());
		}
	}

	@Test
	public void notGetProductGroupModel() {
		given(response.getListOfProductModel(ArgumentMatchers.any())).willReturn(CommonMethods.getProductModel());
		given(response.getListOfProductGroupModel(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getProductGroupModelForDeliveryMethod());
		Assert.assertNotNull(deviceEntityController.getProductGroupModel("093353,092660"));
		try {
			deviceEntityController.getProductGroupModel(null);
		} catch (Exception e) {
			assertEquals("Invalid query parameters", e.getMessage());
		}
		try {
			given(response.getListOfProductModel(ArgumentMatchers.any())).willReturn(CommonMethods.getProductModel());
			given(response.getListOfProductGroupModel(ArgumentMatchers.any())).willReturn(new ArrayList<>());
			deviceEntityController.getProductGroupModel("093353,092660");
		} catch (Exception e) {
			assertEquals("Received Null Values for the given device id", e.getMessage());
		}
	}

	@Test
	public void testDeviceServiceImplUtility() {
		String journey = deviceServiceImplUtility.getJourney("");
		assertNotNull(journey);
		assertEquals("Acquisition", journey);
	}

	@Test
	public void testDeviceServiceImplUtilitySecondLine() {
		String journey = deviceServiceImplUtility.getJourney(JOURNEY_TYPE_SECONDLINE);
		assertNotNull(journey);
		assertEquals(JOURNEY_TYPE_SECONDLINE, journey);
	}

	@Test
	public void testDeviceServiceImplUtilityGetPromotion() {
		Device device = CommonMethods.getDevice();
		Map<String, MerchandisingPromotion> map = new HashMap<>();
		deviceServiceImplUtility.getPromotionForDeviceList(map, device);
	}

	@Test
	public void testDeviceServiceImplUtilityGetPromotionWithMp() {
		Map<String, MerchandisingPromotion> map = new HashMap<>();
		map.put("1234", CommonMethods.getMP());
		Device device2 = CommonMethods.getDevice_Two();
		deviceServiceImplUtility.getPromotionForDeviceList(map, device2);
	}

	@Test
	public void testDeviceServiceImplUtilityGetPromotionWithMpAndDevice() {
		Map<String, MerchandisingPromotion> map = new HashMap<>();
		map.put("1234", CommonMethods.getMP());
		Device device1 = CommonMethods.getDevice_One();
		deviceServiceImplUtility.getPromotionForDeviceList(map, device1);
	}

	@Test
	public void testDeviceServiceImplUtilityGetPromotionWithHardwareDevice() {
		Device device = CommonMethods.getDeviceHW();
		Map<String, MerchandisingPromotion> map = new HashMap<>();
		deviceServiceImplUtility.getPromotionForDeviceList(map, device);
	}

	@Test
	public void testDeviceServiceImplUtilityHWGetPromotionWithMp() {
		Map<String, MerchandisingPromotion> map = new HashMap<>();
		map.put("1234", CommonMethods.getMP());
		Device device2 = CommonMethods.getDeviceHW_Two();
		deviceServiceImplUtility.getPromotionForDeviceList(map, device2);
	}

	@Test
	public void testDeviceServiceImplUtilityGetPromotionWithMpHWDevice() {
		Map<String, MerchandisingPromotion> map = new HashMap<>();
		map.put("1234", CommonMethods.getMP());
		Device device1 = CommonMethods.getDeviceHW_One();
		deviceServiceImplUtility.getPromotionForDeviceList(map, device1);
	}

	@Test
	public void testDeviceServiceImplUtilityWithDATAGetPromotionWithMp() {
		Map<String, MerchandisingPromotion> map = new HashMap<>();
		map.put("1234", CommonMethods.getMP());
		Device device1 = CommonMethods.getDeviceWithDetails_One();
		deviceServiceImplUtility.getPromotionForDeviceList(map, device1);
	}

	@Test
	public void testDeviceServiceImplUtilityHWWithDATAGetPromotionWithMp() {
		Device device = CommonMethods.getDeviceWithDetails_Two();
		Map<String, MerchandisingPromotion> map = new HashMap<>();
		map.put("1234", CommonMethods.getMP());
		deviceServiceImplUtility.getPromotionForDeviceList(map, device);
	}

	@Test
	public void testDeviceServiceImplUtilityGetPromoteAs() {
		Device device = CommonMethods.getDeviceWithDetails_Two();
		device.setPromotionsPackage(null);
		List<Device> deviceList = new ArrayList<>();
		deviceList.add(device);
		List<String> promoteAsTags = new ArrayList<>();
		deviceServiceImplUtility.getPromoteAsForDevice(deviceList, promoteAsTags);
	}

	@Test
	public void testGetBundleAndHardwareList() {
		ProductModel pm = new ProductModel();
		pm.setUpgradeLeadPlanId("");
		pm.setNonUpgradeLeadPlanId("");
		List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
		deviceServiceImplUtility.getBundleAndHardwareList(STRING_DEVICE_PAYM, "", bundleHardwareTupleList, pm);
	}

	@Test
	public void testGetBundleAndHardwareListWithPlanId() {
		ProductModel pm = new ProductModel();
		pm.setUpgradeLeadPlanId("1212");
		pm.setNonUpgradeLeadPlanId("1212");
		List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
		deviceServiceImplUtility.getBundleAndHardwareList(STRING_DEVICE_PAYM, "", bundleHardwareTupleList, pm);
	}

	@Test
	public void testGetBundleAndHardwareListAcquistion() {
		ProductModel pm = new ProductModel();
		pm.setUpgradeLeadPlanId("1212");
		pm.setNonUpgradeLeadPlanId("1212");
		List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
		deviceServiceImplUtility.getBundleAndHardwareList(STRING_DEVICE_PAYM, "Acquisition", bundleHardwareTupleList,
				pm);
	}

	@Test
	public void testGetBundleAndHardwareListUpgrade() {
		ProductModel pm = new ProductModel();
		pm.setUpgradeLeadPlanId("1212");
		pm.setNonUpgradeLeadPlanId("1212");
		List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
		deviceServiceImplUtility.getBundleAndHardwareList(STRING_DEVICE_PAYM, JOURNEY_TYPE_UPGRADE,
				bundleHardwareTupleList, pm);
	}

	@Test
	public void testGetBundleAndHardwareListBlankPlanId() {
		ProductModel pm = new ProductModel();
		pm.setUpgradeLeadPlanId("");
		pm.setNonUpgradeLeadPlanId("");
		List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
		deviceServiceImplUtility.getBundleAndHardwareList(STRING_DEVICE_PAYM, JOURNEY_TYPE_UPGRADE,
				bundleHardwareTupleList, pm);
	}

	@Test
	public void testGetBundleAndHardwareListPayG() {
		ProductModel pm = new ProductModel();
		pm.setUpgradeLeadPlanId("1212");
		pm.setNonUpgradeLeadPlanId("1212");
		List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
		deviceServiceImplUtility.getBundleAndHardwareList(STRING_DEVICE_PAYG, JOURNEY_TYPE_ACQUISITION,
				bundleHardwareTupleList, pm);
	}

	@Test
	public void testGetBundleAndHardwareListPaymAcquisition() {
		ProductModel pm = new ProductModel();
		pm.setUpgradeLeadPlanId("1212");
		pm.setNonUpgradeLeadPlanId("1212");
		List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
		deviceServiceImplUtility.getBundleAndHardwareList(STRING_DEVICE_PAYM, JOURNEY_TYPE_ACQUISITION,
				bundleHardwareTupleList, pm);
	}

	@Test
	public void testGetStartdateFromProductModelNull() {
		ProductModel pm = new ProductModel();
		pm.setProductStartDate(null);
		deviceServiceImplUtility.getStartdateFromProductModel(pm);
	}

	@Test
	public void testGetStartdateFromProductModel() {
		ProductModel pm = new ProductModel();
		pm.setProductStartDate("12-11-2017");
		Date date = deviceServiceImplUtility.getStartdateFromProductModel(pm);
		assertNotNull(date);
		assertEquals("Tue May 10 00:00:00 UTC 18", date.toString());
	}

	@Test
	public void testGetStartdateFromProductModelInvalid() {
		ProductModel pm = new ProductModel();
		pm.setProductStartDate("adasdasd");
		deviceServiceImplUtility.getStartdateFromProductModel(pm);
	}

	@Test
	public void testGetEnddateFromProductModelNull() {
		ProductModel pm = new ProductModel();
		pm.setProductEndDate(null);
		deviceServiceImplUtility.getEndDateFromProductModel(pm);
	}

	@Test
	public void testGetEnddateFromProductModel() {
		ProductModel pm = new ProductModel();
		pm.setProductEndDate("12-11-2017");
		Date date = deviceServiceImplUtility.getEndDateFromProductModel(pm);
		assertEquals("Tue May 10 00:00:00 UTC 18", date.toString());
	}

	@Test
	public void testGetEnddateFromProductModelInvalid() {
		ProductModel pm = new ProductModel();
		pm.setProductEndDate("adasdasd");
		deviceServiceImplUtility.getEndDateFromProductModel(pm);
	}

	@Test
	public void testDateValidation() {
		try {
			Date startDate = new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017");
			Date endDate = new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000");
			assertTrue(deviceServiceImplUtility.dateValidation(null, null, false));
			assertTrue(deviceServiceImplUtility.dateValidation(null, null, true));
			assertTrue(deviceServiceImplUtility.dateValidation(startDate, null, true));
			assertTrue(deviceServiceImplUtility.dateValidation(startDate, null, false));
			assertFalse(deviceServiceImplUtility.dateValidation(startDate, endDate, true));
			assertFalse(deviceServiceImplUtility.dateValidation(startDate, endDate, false));
			assertFalse(deviceServiceImplUtility.dateValidation(null, endDate, true));
			assertFalse(deviceServiceImplUtility.dateValidation(null, endDate, false));
			assertTrue(deviceServiceImplUtility.dateValidation(endDate, null, true));
			assertTrue(deviceServiceImplUtility.dateValidation(endDate, null, false));
			assertFalse(deviceServiceImplUtility.dateValidation(endDate, startDate, false));
			assertFalse(deviceServiceImplUtility.dateValidation(null, startDate, false));
			assertFalse(deviceServiceImplUtility.dateValidation(null, startDate, true));
			assertFalse(deviceServiceImplUtility.dateValidation(new SimpleDateFormat(DATE_FORMAT).parse("21-11-2018"),
					startDate, true));
			assertFalse(deviceServiceImplUtility.dateValidation(new SimpleDateFormat(DATE_FORMAT).parse("21-11-2018"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"), true));
			assertFalse(deviceServiceImplUtility.dateValidation(new SimpleDateFormat(DATE_FORMAT).parse("21-11-2018"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2019"), true));
			Date currentDate = new Date();
			assertFalse(deviceServiceImplUtility.getDateForValidation(currentDate, startDate, endDate));
			assertFalse(deviceServiceImplUtility.getDateForValidation(currentDate, startDate, endDate));
			assertFalse(deviceServiceImplUtility.getDateForValidation(currentDate, endDate, startDate));
			assertFalse(deviceServiceImplUtility.getDateForValidation(currentDate, currentDate, startDate));
			assertTrue(deviceServiceImplUtility.getDateForValidation(currentDate, currentDate, currentDate));
			assertTrue(deviceServiceImplUtility.getDateForValidation(currentDate, startDate, currentDate));
			assertFalse(deviceServiceImplUtility.getDateForValidation(currentDate, startDate, endDate));
			assertFalse(deviceServiceImplUtility.getDateForValidation(currentDate,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), startDate));
			assertFalse(deviceServiceImplUtility.getDateForValidation(currentDate,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017")));
			assertFalse(deviceServiceImplUtility.getDateForValidation(currentDate,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000")));
			assertTrue(deviceServiceImplUtility.getDateForValidation(currentDate,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), currentDate));

			assertTrue(deviceServiceImplUtility.dateValidationForOffersImplementation(null, null, "//"));
			assertTrue(deviceServiceImplUtility.dateValidationForOffersImplementation(null, null, "//"));
			assertTrue(deviceServiceImplUtility.dateValidationForOffersImplementation("21-11-2017", null, "//"));
			assertTrue(deviceServiceImplUtility.dateValidationForOffersImplementation("21-11-2017", null, "//"));
			assertTrue(
					deviceServiceImplUtility.dateValidationForOffersImplementation("21-11-2017", "21-11-3000", "//"));
			assertTrue(
					deviceServiceImplUtility.dateValidationForOffersImplementation("21-11-2017", "21-11-3000", "//"));
			assertTrue(deviceServiceImplUtility.dateValidationForOffersImplementation(null, "21-11-3000", "//"));
			assertTrue(deviceServiceImplUtility.dateValidationForOffersImplementation(null, "21-11-3000", "//"));
			assertTrue(deviceServiceImplUtility.dateValidationForOffersImplementation("21-11-3000", null, "//"));
			assertTrue(deviceServiceImplUtility.dateValidationForOffersImplementation("21-11-3000", null, "//"));
			assertTrue(
					deviceServiceImplUtility.dateValidationForOffersImplementation("21-11-3000", "21-11-2017", "//"));
			assertTrue(deviceServiceImplUtility.dateValidationForOffersImplementation(null, "21-11-2017", "//"));
			assertTrue(deviceServiceImplUtility.dateValidationForOffersImplementation(null, "21-11-2017", "//"));
			assertTrue(
					deviceServiceImplUtility.dateValidationForOffersImplementation("21-11-2018", "21-11-2017", "//"));
			assertTrue(
					deviceServiceImplUtility.dateValidationForOffersImplementation("21-11-2018", "21-11-2017", "//"));
			assertTrue(
					deviceServiceImplUtility.dateValidationForOffersImplementation("21-11-2018", "21-11-2019", "//"));
			assertTrue(deviceServiceImplUtility.dateValidationForOffersImplementation(null, null, "//"));

		} catch (Exception e) {
		}
		try {
			deviceServiceImplUtility.dateValidationForOffersImplementation("adasdas", null, "//");
		} catch (Exception e) {
		}
		try {
			deviceServiceImplUtility.dateValidationForOffersImplementation(null, "adasdas", "//");
		} catch (Exception e) {
		}
		try {
			List<BundleAndHardwareTuple> bundleAndHardwareTupleList = new ArrayList<>();
			Map<String, Boolean> fromPricingMap = new HashMap<>();
			Map<String, String> leadPlanIdMap = new HashMap<>();
			List<String> listofLeadPlan = new ArrayList<>();
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353();
			deviceServiceImplUtility.getPricingMap(bundleAndHardwareTupleList, fromPricingMap, leadPlanIdMap,
					listofLeadPlan, cp);
		} catch (Exception e) {
		}

	}

	@Test
	public void testIsMember() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_HANDSET);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			assertFalse(deviceServiceImplUtility.isMember(JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true));
		} catch (Exception e) {
		}
	}

	@Test
	public void testIsMemberAcquisition() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_HANDSET);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			assertFalse(deviceServiceImplUtility.isMember(JOURNEY_TYPE_ACQUISITION,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true));
		} catch (Exception e) {
		}
	}

	@Test
	public void testIsMemberAccessory() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_ACCESSORY);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			assertFalse(deviceServiceImplUtility.isMember(JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true));
		} catch (Exception e) {
		}
	}

	@Test
	public void testIsMemberDisplayableFalse() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_HANDSET);
			pm.setIsDisplayableRet("false");
			pm.setIsSellableRet("true");
			assertFalse(deviceServiceImplUtility.isMember(JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true));
		} catch (Exception e) {
		}
	}

	@Test
	public void testIsUpgrade() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_HANDSET);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			assertFalse(deviceServiceImplUtility.isUpgradeCheck(JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true));
		} catch (Exception e) {
		}
	}

	@Test
	public void testIsUpgradeCheckInvalid() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_HANDSET);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			assertFalse(deviceServiceImplUtility.isUpgradeCheck(JOURNEY_TYPE_ACQUISITION,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true));
		} catch (Exception e) {
		}
	}

	@Test
	public void testIsUpgradeCheckAccessory() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_ACCESSORY);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			assertFalse(deviceServiceImplUtility.isUpgradeCheck(JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true));
		} catch (Exception e) {
		}
	}

	@Test
	public void testIsUpgradeCheckDisplayableFalse() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_HANDSET);
			pm.setIsDisplayableRet("false");
			pm.setIsSellableRet("true");
			assertFalse(deviceServiceImplUtility.isUpgradeCheck(JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true));
		} catch (Exception e) {
		}
	}

	@Test
	public void testIsUpgradeCheckSellableFalse() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_HANDSET);
			pm.setIsDisplayableRet("false");
			pm.setIsSellableRet("false");
			assertFalse(deviceServiceImplUtility.isUpgradeCheck(JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"), pm, true));
		} catch (Exception e) {
		}
	}

	@Test
	public void testIsNonUpgrade() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_HANDSET);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			assertFalse(deviceServiceImplUtility.isNonUpgradeCheck(JOURNEY_TYPE_ACQUISITION,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true));
		} catch (Exception e) {
		}
	}

	@Test
	public void testIsNonUpgradeCheckUpgrade() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_HANDSET);
			pm.setIsDisplayableRet("true");
			pm.setIsSellableRet("true");
			assertFalse(deviceServiceImplUtility.isNonUpgradeCheck(JOURNEY_TYPE_UPGRADE,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true));
		} catch (Exception e) {
		}
	}

	@Test
	public void testIsNonUpgradeCheckAccessory() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_ACCESSORY);
			pm.setIsDisplayableAcq("true");
			pm.setIsSellableAcq("true");
			assertFalse(deviceServiceImplUtility.isNonUpgradeCheck(JOURNEY_TYPE_ACQUISITION,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true));
		} catch (Exception e) {
		}
	}

	@Test
	public void testIsNonUpgradeCheckDisplayableFalse() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_HANDSET);
			pm.setIsDisplayableAcq("false");
			pm.setIsSellableAcq("true");
			assertFalse(deviceServiceImplUtility.isNonUpgradeCheck(JOURNEY_TYPE_ACQUISITION,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"), pm, true));
		} catch (Exception e) {
		}
	}

	@Test
	public void testIsNonUpgradeCheckHandset() {
		try {
			ProductModel pm = new ProductModel();
			pm.setProductClass(STRING_HANDSET);
			pm.setIsDisplayableAcq("false");
			pm.setIsSellableAcq("true");
			assertFalse(deviceServiceImplUtility.isNonUpgradeCheck(JOURNEY_TYPE_ACQUISITION,
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-3000"),
					new SimpleDateFormat(DATE_FORMAT).parse("21-11-2017"), pm, true));
		} catch (Exception e) {
		}
	}

	@Test
	public void testIsRet() {
		ProductModel pm = new ProductModel();
		pm.setProductClass(STRING_HANDSET);
		pm.setIsDisplayableRet("true");
		pm.setIsSellableRet("true");
		assertTrue(deviceServiceImplUtility.isRet(pm));
	}

	@Test
	public void testIsRetSellableFalse() {
		ProductModel pm = new ProductModel();
		pm.setProductClass(STRING_HANDSET);
		pm.setIsDisplayableRet("true");
		pm.setIsSellableRet("false");
		assertFalse(deviceServiceImplUtility.isRet(pm));
	}

	@Test
	public void testIsRetDisplayableFalse() {
		ProductModel pm = new ProductModel();
		pm.setProductClass(STRING_HANDSET);
		pm.setIsDisplayableRet("false");
		pm.setIsSellableRet("false");
		deviceServiceImplUtility.isRet(pm);
	}

	@Test
	public void testIsRetHandset() {
		ProductModel pm = new ProductModel();
		pm.setProductClass(STRING_HANDSET);
		pm.setIsDisplayableRet("false");
		pm.setIsSellableRet("true");
		assertFalse(deviceServiceImplUtility.isRet(pm));
	}

	@Test
	public void testIsAcq() {
		ProductModel pm = new ProductModel();
		pm.setProductClass(STRING_HANDSET);
		pm.setIsDisplayableAcq("true");
		pm.setIsSellableAcq("true");
		assertTrue(deviceServiceImplUtility.isAcq(pm));
	}

	@Test
	public void testIsAcqSellableFalse() {
		ProductModel pm = new ProductModel();
		pm.setProductClass(STRING_HANDSET);
		pm.setIsDisplayableAcq("true");
		pm.setIsSellableAcq("false");
		assertFalse(deviceServiceImplUtility.isAcq(pm));
	}

	@Test
	public void testIsAcqDisplayableFalse() {
		ProductModel pm = new ProductModel();
		pm.setProductClass(STRING_HANDSET);
		pm.setIsDisplayableAcq("false");
		pm.setIsSellableAcq("false");
		assertFalse(deviceServiceImplUtility.isAcq(pm));
	}

	@Test
	public void testIsAcqHandset() {
		ProductModel pm = new ProductModel();
		pm.setProductClass(STRING_HANDSET);
		pm.setIsDisplayableAcq("false");
		pm.setIsSellableAcq("true");
		assertFalse(deviceServiceImplUtility.isAcq(pm));
	}

	@Test
	public void testIsNonUpgradeSort() {
		assertTrue(deviceServiceImplUtility.isNonUpgrade(""));
		assertTrue(deviceServiceImplUtility.isNonUpgrade("asa"));
		deviceServiceImplUtility.getSortCriteriaForList(null);
		List<String> sort = deviceServiceImplUtility.getSortCriteriaForList("++adadad");
		List<String> sorthyphen = deviceServiceImplUtility.getSortCriteriaForList("-");
		assertNotNull(sort);
		assertNotNull(sorthyphen);
		assertEquals("asc", sort.get(0));
		assertEquals("++adadad", sort.get(1));
		assertEquals("desc", sorthyphen.get(0));
		assertEquals("", sorthyphen.get(1));
	}

	@Test
	public void testValidateGroupType() {
		assertTrue(deviceServiceImplUtility.validateGroupType(STRING_DEVICE_PAYM));
		assertTrue(deviceServiceImplUtility.validateGroupType(STRING_DEVICE_PAYG));
		assertFalse(deviceServiceImplUtility.validateGroupType("abc"));
		assertTrue(deviceServiceImplUtility.validateGroupType(STRING_DEVICE_NEARLY_NEW));
		assertTrue(deviceServiceImplUtility.validateGroupType(STRING_DATADEVICE_PAYM));
		assertTrue(deviceServiceImplUtility.validateGroupType(STRING_DATADEVICE_PAYG));
		assertTrue(deviceServiceImplUtility.getJourneyForMakeAndModel(""));
		assertFalse(deviceServiceImplUtility.getJourneyForMakeAndModel(JOURNEY_TYPE_ACQUISITION));
		assertFalse(deviceServiceImplUtility.getJourneyForMakeAndModel(JOURNEY_TYPE_UPGRADE));
		assertFalse(deviceServiceImplUtility.getJourneyForMakeAndModel(JOURNEY_TYPE_SECONDLINE));
		assertTrue(deviceServiceImplUtility.getJourneyForMakeAndModel("avcv"));
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353();
		cp.setProductControl(null);
		assertFalse(deviceServiceImplUtility.isNonUpgradeCommercialProduct(cp));
		ProductControl pc = new ProductControl();
		pc.setDisplayableAcq(false);
		pc.setSellableAcq(false);
		cp.setProductControl(pc);
		assertFalse(deviceServiceImplUtility.isNonUpgradeCommercialProduct(cp));
		pc.setDisplayableAcq(true);
		pc.setSellableAcq(false);
		cp.setProductControl(pc);
		assertFalse(deviceServiceImplUtility.isNonUpgradeCommercialProduct(cp));
		pc.setDisplayableAcq(true);
		pc.setSellableAcq(true);
		cp.setProductControl(pc);
		assertTrue(deviceServiceImplUtility.isNonUpgradeCommercialProduct(cp));
		pc.setDisplayableAcq(false);
		pc.setSellableAcq(true);
		cp.setProductControl(pc);
		assertFalse(deviceServiceImplUtility.isNonUpgradeCommercialProduct(cp));

		cp.setProductControl(null);
		assertFalse(deviceServiceImplUtility.isUpgradeFromCommercialProduct(cp));
		pc.setDisplayableRet(false);
		pc.setSellableRet(false);
		cp.setProductControl(pc);
		assertFalse(deviceServiceImplUtility.isUpgradeFromCommercialProduct(cp));
		pc.setDisplayableRet(true);
		pc.setSellableRet(false);
		cp.setProductControl(pc);
		assertFalse(deviceServiceImplUtility.isUpgradeFromCommercialProduct(cp));
		pc.setDisplayableRet(true);
		pc.setSellableRet(true);
		cp.setProductControl(pc);
		assertTrue(deviceServiceImplUtility.isUpgradeFromCommercialProduct(cp));
		pc.setDisplayableRet(false);
		pc.setSellableRet(true);
		cp.setProductControl(pc);
		assertFalse(deviceServiceImplUtility.isUpgradeFromCommercialProduct(cp));
	}

	@Test
	public void testgetBundlePriceBasedOnDiscountDurationImplementation() {
		try {
			deviceServiceImplUtility.getBundlePriceBasedOnDiscountDurationImplementation(null, null);
		} catch (Exception e) {
		}
		try {
			deviceServiceImplUtility.getBundlePriceBasedOnDiscountDurationImplementation(null, FULL_DURATION_DISCOUNT);
		} catch (Exception e) {
		}
		try {
			deviceServiceImplUtility.getBundlePriceBasedOnDiscountDurationImplementation(null, LIMITED_TIME_DISCOUNT);
		} catch (Exception e) {
		}
		try {
			deviceServiceImplUtility.getBundlePriceBasedOnDiscountDurationImplementation(null, "abc");
		} catch (Exception e) {
		}
		try {
			DeviceSummary ds = new DeviceSummary();
			PriceForBundleAndHardware pb = new PriceForBundleAndHardware();
			BundlePrice bp = new BundlePrice();
			bp.setMonthlyDiscountPrice(null);
			pb.setBundlePrice(bp);
			ds.setPriceInfo(pb);
			deviceServiceImplUtility.getBundlePriceBasedOnDiscountDurationImplementation(ds, FULL_DURATION_DISCOUNT);
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
			deviceServiceImplUtility.getBundlePriceBasedOnDiscountDurationImplementation(ds, FULL_DURATION_DISCOUNT);
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
			Double price = deviceServiceImplUtility.getBundlePriceBasedOnDiscountDurationImplementation(ds,
					FULL_DURATION_DISCOUNT);
			assertNotNull(price);
			assertEquals(1212.0, price, 0);
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
			deviceServiceImplUtility.getBundlePriceBasedOnDiscountDurationImplementation(ds, LIMITED_TIME_DISCOUNT);
		} catch (Exception e) {
		}
		try {
			DeviceSummary ds = new DeviceSummary();
			PriceForBundleAndHardware pb = new PriceForBundleAndHardware();
			BundlePrice bp = new BundlePrice();
			bp.setMonthlyDiscountPrice(null);
			pb.setBundlePrice(bp);
			ds.setPriceInfo(pb);
			deviceServiceImplUtility.getBundlePriceBasedOnDiscountDurationImplementation(ds, LIMITED_TIME_DISCOUNT);
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
			deviceServiceImplUtility.getBundlePriceBasedOnDiscountDurationImplementation(ds, LIMITED_TIME_DISCOUNT);
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
			deviceServiceImplUtility.getBundlePriceBasedOnDiscountDurationImplementation(ds, LIMITED_TIME_DISCOUNT);
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
			deviceServiceImplUtility.getBundlePriceBasedOnDiscountDurationImplementation(ds, LIMITED_TIME_DISCOUNT);
		} catch (Exception e) {
		}
	}

	@Test
	public void testvalidateDeviceId() {
		try {
			validator.validateDeviceId("");
		} catch (Exception e) {
			Assert.assertEquals(ExceptionMessages.INVALID_INPUT_MISSING_DEVICEID, e.getMessage());
		}
		try {
			validator.validateDeviceId("9854");
		} catch (Exception e) {
			Assert.assertEquals(ExceptionMessages.INVALID_DEVICE_ID, e.getMessage());
		}
		try {
			validator.validateDeviceId("09854");
		} catch (Exception e) {
			Assert.assertEquals(ExceptionMessages.INVALID_DEVICE_ID, e.getMessage());
		}
		try {
			validator.validateDeviceId("093353");
		} catch (Exception e) {
		}
		try {
			validator.validateDeviceId("985451");
		} catch (Exception e) {
			Assert.assertEquals(ExceptionMessages.INVALID_DEVICE_ID, e.getMessage());
		}
		try {
			validator.validateIncludeRecommendation("false");
		} catch (Exception e) {
			Assert.assertEquals(ExceptionMessages.INVALID_INCLUDERECOMMENDATION, e.getMessage());
		}
		try {
			validator.validateMSISDN("1312312312", "false");
		} catch (Exception e) {
		}
		try {
			validator.validateForCreditLimit("07896das");
		} catch (Exception e) {
			Assert.assertEquals(ExceptionMessages.INVALID_CREDIT_LIMIT, e.getMessage());
		}

	}
}
