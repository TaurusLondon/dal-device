package com.vf.uk.dal.device.service.test;

import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.uk.dal.common.configuration.ConfigHelper;
import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.device.aspect.CatalogServiceAspect;
import com.vf.uk.dal.device.beans.test.DeviceTestBeans;
import com.vf.uk.dal.device.client.BundleServiceClient;
import com.vf.uk.dal.device.client.PriceServiceClient;
import com.vf.uk.dal.device.client.converter.ResponseMappingHelper;
import com.vf.uk.dal.device.client.entity.bundle.BundleDetails;
import com.vf.uk.dal.device.client.entity.bundle.BundleHeader;
import com.vf.uk.dal.device.client.entity.bundle.BundleModelAndPrice;
import com.vf.uk.dal.device.client.entity.bundle.CommercialBundle;
import com.vf.uk.dal.device.client.entity.customer.SourcePackageSummary;
import com.vf.uk.dal.device.client.entity.price.BundleAndHardwareTuple;
import com.vf.uk.dal.device.client.entity.price.BundlePrice;
import com.vf.uk.dal.device.client.entity.price.HardwarePrice;
import com.vf.uk.dal.device.client.entity.price.MerchandisingPromotion;
import com.vf.uk.dal.device.client.entity.price.Price;
import com.vf.uk.dal.device.client.entity.price.PriceForBundleAndHardware;
import com.vf.uk.dal.device.client.entity.price.PriceForProduct;
import com.vf.uk.dal.device.client.entity.price.RequestForBundleAndHardware;
import com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwarePromotions;
import com.vf.uk.dal.device.client.entity.promotion.BundleAndHardwareRequest;
import com.vf.uk.dal.device.common.test.CommonMethods;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.dao.DeviceTileCacheDAO;
import com.vf.uk.dal.device.model.AccessoryTileGroup;
import com.vf.uk.dal.device.model.DeviceDetails;
import com.vf.uk.dal.device.model.DeviceSummary;
import com.vf.uk.dal.device.model.DeviceTile;
import com.vf.uk.dal.device.model.FacetedDevice;
import com.vf.uk.dal.device.model.Insurance;
import com.vf.uk.dal.device.model.Insurances;
import com.vf.uk.dal.device.model.MediaLink;
import com.vf.uk.dal.device.model.MerchandisingPromotionsPackage;
import com.vf.uk.dal.device.model.product.CommercialProduct;
import com.vf.uk.dal.device.model.product.ProductGroup;
import com.vf.uk.dal.device.model.product.ProductGroups;
import com.vf.uk.dal.device.model.product.ProductModel;
import com.vf.uk.dal.device.model.product.PromoteAs;
import com.vf.uk.dal.device.model.productgroups.Group;
import com.vf.uk.dal.device.model.productgroups.Member;
import com.vf.uk.dal.device.service.AccessoryInsuranceServiceImpl;
import com.vf.uk.dal.device.service.DeviceDetailsServiceImpl;
import com.vf.uk.dal.device.service.DeviceMakeAndModelServiceImpl;
import com.vf.uk.dal.device.service.DeviceRecommendationService;
import com.vf.uk.dal.device.service.DeviceService;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.DeviceConditionallHelper;
import com.vf.uk.dal.device.utils.DeviceDetailsMakeAndModelVaiantDaoUtils;
import com.vf.uk.dal.device.utils.DeviceServiceCommonUtility;
import com.vf.uk.dal.device.utils.DeviceServiceImplUtility;
import com.vf.uk.dal.device.utils.DeviceUtils;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.device.utils.Validator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeviceTestBeans.class)
public class DeviceServiceImplTest {

	public static final String JOURNEYTYPE_UPGRADE = "UPGRADE";
	public static final String STRING_COMPATIBLE_INSURANCE = "Compatible Insurance";
	public static final String JOURNEY_TYPE_SECONDLINE = "SecondLine";
	public static final String JOURNEY_TYPE_UPGRADE = "Upgrade";
	public static final String JOURNEY_TYPE_ACQUISITION = "Acquisition";
	public static final String STRING_DEVICE_PAYG = "DEVICE_PAYG";
	public static final String STRING_DATA_DEVICE = "Data Device";

	@Autowired
	DeviceMakeAndModelServiceImpl deviceMakeAndModelServiceImpl;

	@Autowired
	DeviceServiceCommonUtility utility;

	@MockBean
	DeviceDao deviceDAOMock;

	@MockBean
	ResponseMappingHelper response;

	@Autowired
	DeviceConditionallHelper conditionalHelper;

	@MockBean
	RestTemplate restTemplate;

	@MockBean
	FacetedDevice facetDeviceMock;

	@MockBean
	DeviceRecommendationService deviceRecomServiceMock;

	@MockBean
	DeviceTileCacheDAO cacheDao;

	@MockBean
	ConfigHelper configHelperMock;

	@Autowired
	CatalogServiceAspect aspect;

	@Autowired
	AccessoryInsuranceServiceImpl accessoryInsuranceService;

	@Autowired
	DeviceDetailsServiceImpl deviceDetailsService;

	@Autowired
	DeviceService deviceService;

	@Autowired
	CommonUtility commonUtility;

	@Autowired
	DeviceUtils deviceUtils;

	@Autowired
	BundleServiceClient bundleServiceClient;
	
	@Autowired
	PriceServiceClient priceServiceClient;

	@Value("${cdn.domain.host}")
	private String cdnDomain;

	@Before
	public void setupMockBehaviour() throws Exception {
		aspect.beforeAdvice(null);
		given(response.getCommercialProduct(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getCommercialProductByDeviceId_093353_PAYG());
		given(response.getListOfGroupFromJson(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getListOfProductGroupFromProductGroupRepository());
		given(response.getCommercialProductFromJson(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getCommercialProductsListOfMakeAndModel());
		given(response.getCommercialBundle(ArgumentMatchers.any())).willReturn(CommonMethods.getCommercialBundle());
		given(response.getListOfCommercialBundleFromJson(ArgumentMatchers.any()))
				.willReturn(Arrays.asList(CommonMethods.getCommercialBundle()));
		given(deviceDAOMock.getPriceForBundleAndHardwareListFromTupleListAsync(ArgumentMatchers.anyList(),
				ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString()))
						.willReturn(CommonMethods.getPriceForBundleAndHardwareListFromTupleListAsync());
		given(deviceDAOMock.getBundleAndHardwarePromotionsListFromBundleListAsync(ArgumentMatchers.anyList(),
				ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
						.willReturn(CommonMethods.getBundleAndHardwarePromotionsListFromBundleListAsync());
		given(response.getListOfGroupFromJson(ArgumentMatchers.any())).willReturn(CommonMethods.getGroup());
		given(response.getCommercialProductFromJson(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getCommercialProductsListOfAccessories());
		given(response.getCommercialBundle(ArgumentMatchers.any())).willReturn(CommonMethods.getCommercialBundle());

		given(restTemplate.postForObject("http://PRICE-V1/price/product",
				CommonMethods.bundleDeviceAndProductsList_For_GetAccessoriesOfDevice(), PriceForProduct.class))
						.willReturn(CommonMethods.getPriceForProduct_For_GetAccessoriesForDevice());
		given(response.getMerchandisingPromotion(ArgumentMatchers.any())).willReturn(CommonMethods.getMemPro());
	}

	@Test
	public void getConditionalForDeviceList() {
		String url = "http://CUSTOMER-V1/customer/subscription/msisdn:7741655542/sourcePackageSummary";
		given(restTemplate.getForObject(url, SourcePackageSummary.class))
				.willReturn(CommonMethods.getSourcePackageSummary());
		given(deviceRecomServiceMock.getRecommendedDeviceList(ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString(), ArgumentMatchers.any()))
						.willReturn(CommonMethods.getFacetedDeviceList("HANDSET", "Apple", "iPhone-7", "DEVICE_PAYM",
								"", 1, 9, "wrtety"));
		FacetedDevice facetedDevice = deviceService.getConditionalForDeviceList("7741655542",
				CommonMethods.getFacetedDeviceList("HANDSET", "Apple", "iPhone-7", "DEVICE_PAYM", "", 1, 9, "wrtety"));
		Assert.assertNotNull(facetedDevice);
		Assert.assertEquals( "01234",facetedDevice.getDevice().get(0).getDeviceId());
		Assert.assertEquals( "Apple",facetedDevice.getDevice().get(0).getMake());
		Assert.assertEquals( "iphone7",facetedDevice.getDevice().get(0).getModel());
	}

	// accessory test cases start
	@Test
	public void getAccessoriesOfDevice() {
		List<AccessoryTileGroup> list = accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition",
				"abc");
		Assert.assertNotNull(list);
		Assert.assertEquals( "093329",list.get(0).getAccessories().get(0).getSkuId());
		Assert.assertEquals(
				"Apple iPhone 7 Silicone Case Midnight Blue",list.get(0).getAccessories().get(0).getName());
		Assert.assertEquals( "Midnight Blue",list.get(0).getAccessories().get(0).getColour());
	}

	// deviceRecomServiceMock
	@Test
	public void getdeviceRecomServiceMock() {
		try {
			Assert.assertNull(deviceRecomServiceMock.getRecommendedDeviceList("9237", "093353", null));
		} catch (Exception e) {
			Assert.assertEquals( "Invalid Request parameters",e.getMessage());
		}
	}

	@Test
	public void getAccessoriesOfDeviceCPNull() {
		try {
			given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(null);
			Assert.assertNull(accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc"));
		} catch (Exception e) {
			Assert.assertEquals( "Received Null Values for the given device id",e.getMessage());
		}
	}

	@Test
	public void getAccessoriesOfDeviceCPIdNull() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setId(null);
			given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(cp);
			Assert.assertNull(accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc"));
		} catch (Exception e) {
			Assert.assertEquals( "Received Null Values for the given device id",e.getMessage());
		}
	}

	@Test
	public void getAccessoriesOfDeviceCPProductNull() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setProductClass("abc");
			given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(cp);
			Assert.assertNull(accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc"));
		} catch (Exception e) {
			Assert.assertEquals( "Given DeviceId is not ProductClass Handset",e.getMessage());
		}
	}

	@Test
	public void getAccessoriesOfDeviceCPProductDevice() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setIsDeviceProduct(false);
			given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(cp);
			List<AccessoryTileGroup> accessory = accessoryInsuranceService.getAccessoriesOfDevice("093353",
					"Acquisition", "abc");
			Assert.assertNotNull(accessory);
			Assert.assertEquals( "093329",accessory.get(0).getAccessories().get(0).getSkuId());
			Assert.assertEquals(
					"Apple iPhone 7 Silicone Case Midnight Blue",accessory.get(0).getAccessories().get(0).getName());
			Assert.assertEquals( "Midnight Blue",accessory.get(0).getAccessories().get(0).getColour());
		} catch (Exception e) {
			Assert.assertEquals( "Given DeviceId is not ProductClass Handset",e.getMessage());
		}
	}

	@Test
	public void getAccessoriesOfDeviceCPProductGroupNull() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setProductGroups(null);
			given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(cp);
			Assert.assertNull(accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc"));
		} catch (Exception e) {
			Assert.assertEquals( "No Compatible Accessories found for given device Id",e.getMessage());
		}
	}

	@Test
	public void getAccessoriesOfDeviceCPProductGroupsNull() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			ProductGroups pg = cp.getProductGroups();
			pg.setProductGroup(null);
			cp.setProductGroups(pg);
			given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(cp);
			Assert.assertNull(accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc"));
		} catch (Exception e) {
			Assert.assertEquals( "No Compatible Accessories found for given device Id",e.getMessage());
		}
	}

	@Test
	public void getAccessoriesOfDeviceCPProductGroupEmpty() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			ProductGroups pg = cp.getProductGroups();
			List<ProductGroup> pgg = new ArrayList<>();
			pg.setProductGroup(pgg);
			cp.setProductGroups(pg);
			given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(cp);
			Assert.assertNull(accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc"));
		} catch (Exception e) {
			Assert.assertEquals( "No Compatible Accessories found for given device Id",e.getMessage());
		}
	}

	@Test
	public void getAccessoriesOfDeviceCPProductGroupABC() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			ProductGroups pg = cp.getProductGroups();
			List<ProductGroup> pgg = new ArrayList<>();
			Collection<ProductGroup> pgc = new ArrayList<>();
			ProductGroup pggg = new ProductGroup();
			pggg.setProductGroupName("abc");
			pggg.setProductGroupRole("abc");
			pgc.add(pggg);
			pgg.addAll(pgc);
			pg.setProductGroup(pgg);
			cp.setProductGroups(pg);
			given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(cp);
			Assert.assertNull(accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc"));
		} catch (Exception e) {
			Assert.assertEquals( "No Compatible Accessories found for given device Id",e.getMessage());
		}
	}

	@Test
	public void getAccessoriesOfDeviceCPProductGroupInvalid() {
		try {
			given(response.getListOfGroupFromJson(ArgumentMatchers.any())).willReturn(CommonMethods.getGroup_Two());
			Assert.assertNull(accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc"));
		} catch (Exception e) {
			Assert.assertEquals( "No Compatible Accessories found for given device Id",e.getMessage());
		}
	}

	@Test
	public void getFinalAccessoryListInvalid() {
		List<String> finalAccessoryList = new ArrayList<>();
		Map<String, List<String>> mapForGroupName = new HashMap<>();
		AccessoryInsuranceServiceImpl.getFinalAccessoryList(finalAccessoryList, mapForGroupName, null);
	}

	@Test
	public void getFinalAccessoryListInvalidProductGroup() {
		List<String> finalAccessoryList = new ArrayList<>();
		Map<String, List<String>> mapForGroupName = new HashMap<>();
		Group productGroup = new Group();
		AccessoryInsuranceServiceImpl.getFinalAccessoryList(finalAccessoryList, mapForGroupName, productGroup);
	}

	@Test
	public void getListOfFilteredAccessories1() {
		given(response.getCommercialProductFromJson(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getCommercialProductsListOfAccessoriesWithEndDate());
		List<String> finalAccessoryList = new ArrayList<>();
		List<CommercialProduct> commercialProduct = accessoryInsuranceService
				.getListOfFilteredAccessories("Acquisition", finalAccessoryList);
		Assert.assertNotNull(commercialProduct);
	}

	@Test
	public void testSetMapForCommercialData() {
		try {
			PriceForProduct pc = new PriceForProduct();
			pc.setPriceForAccessoryes(null);
			Assert.assertNull(AccessoryInsuranceServiceImpl.setMapForCommercialData(null, null, pc, null));
		} catch (Exception e) {
			Assert.assertEquals( "Null value received from Pricing API",e.getMessage());
		}
	}

	// insurance test cases start

	@Test
	public void getInsuranceByDeviceId() {
		Insurances insurances = accessoryInsuranceService.getInsuranceByDeviceId("093353", "Acquisition");
		Assert.assertNotNull(insurances);
		Assert.assertEquals( "093329",insurances.getInsuranceList().get(0).getId());
		Assert.assertEquals(
				"Apple iPhone 7 Silicone Case Midnight Blue",insurances.getInsuranceList().get(0).getName());
	}

	@Test
	public void testGetInsurance() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			ProductGroups pg = cp.getProductGroups();
			List<ProductGroup> pgg = new ArrayList<>();
			Collection<ProductGroup> pgc = new ArrayList<>();
			ProductGroup pggg = new ProductGroup();
			pggg.setProductGroupName("abc");
			pggg.setProductGroupRole("abc");
			pgc.add(pggg);
			pgg.addAll(pgc);
			pg.setProductGroup(pgg);
			cp.setProductGroups(pg);
			Assert.assertNull(accessoryInsuranceService.getInsurance("acquisition", cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void testGetInsurancePGNull() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			ProductGroups pg = cp.getProductGroups();
			List<ProductGroup> pgg = new ArrayList<>();
			Collection<ProductGroup> pgc = new ArrayList<>();
			ProductGroup pggg = new ProductGroup();
			pggg.setProductGroupName(null);
			pggg.setProductGroupRole(null);
			pgc.add(pggg);
			pgg.addAll(pgc);
			pg.setProductGroup(pgg);
			cp.setProductGroups(pg);
			Assert.assertNull(accessoryInsuranceService.getInsurance("acquisition", cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void testGetInsuranceProductList() {
		Group gp = new Group();
		gp.setGroupType(STRING_COMPATIBLE_INSURANCE);
		List<Member> members = new ArrayList<>();
		Member member = new Member();
		member.setId("123");
		member.setPriority((long) 12);
		members.add(member);
		gp.setMembers(members);
		List<Member> listOfInsuranceMembers = new ArrayList<>();
		List<String> insurance = AccessoryInsuranceServiceImpl.getInsuranceProductList(listOfInsuranceMembers, gp);
		Assert.assertNotNull(insurance);
		Assert.assertEquals( "123",insurance.get(0));
	}

	@Test
	public void testGetInsuranceProductListForCompatible() {
		Group gp = new Group();
		gp.setGroupType(STRING_COMPATIBLE_INSURANCE);
		List<Member> members = new ArrayList<>();
		Member member = new Member();
		member.setId("123");
		member.setPriority((long) 12);
		members.add(member);
		gp.setMembers(members);
		List<Member> listOfInsuranceMembers = new ArrayList<>();
		listOfInsuranceMembers.add(member);
		List<String> insurance = AccessoryInsuranceServiceImpl.getInsuranceProductList(listOfInsuranceMembers, gp);
		Assert.assertNotNull(insurance);
		Assert.assertEquals( "123",insurance.get(0));
	}

	@Test
	public void testGetInsuranceProductListForNullId() {
		Group gp = new Group();
		gp.setGroupType(STRING_COMPATIBLE_INSURANCE);
		List<Member> members = new ArrayList<>();
		Member member = new Member();
		member.setId(null);
		member.setPriority((long) 12);
		members.add(member);
		gp.setMembers(members);
		List<Member> listOfInsuranceMembers = new ArrayList<>();
		listOfInsuranceMembers.add(member);
		List<String> insurance = AccessoryInsuranceServiceImpl.getInsuranceProductList(listOfInsuranceMembers, gp);
		Assert.assertNotNull(insurance);
	}

	@Test
	public void testAssembleMechandisingPromotionsPackageGeneric() {
		MerchandisingPromotionsPackage mpPackage = DeviceDetailsMakeAndModelVaiantDaoUtils
				.assembleMechandisingPromotionsPackageGeneric(
						CommonMethods.getListOfBundleAndHardwarePromotions().get(0),
						CommonMethods.getPriceForBundleAndHardware().get(0));
		Assert.assertNotNull(mpPackage);
		Assert.assertEquals( "093353",mpPackage.getHardwareId());
		Assert.assertEquals( "110154",mpPackage.getPlanId());
	}

	@Test
	public void testGetInsuranceProductListWithPriority() {
		Group gp = new Group();
		gp.setGroupType(STRING_COMPATIBLE_INSURANCE);
		List<Member> members = new ArrayList<>();
		Member member = new Member();
		member.setId(null);
		member.setPriority((long) 12);
		members.add(member);
		gp.setMembers(members);
		List<Member> listOfInsuranceMembers = new ArrayList<>();
		Assert.assertNotNull(AccessoryInsuranceServiceImpl.getInsuranceProductList(listOfInsuranceMembers, gp));
	}

	@Test
	public void testGetInsuranceProductListCompatible() {
		try {
			Group gp = new Group();
			gp.setGroupType(STRING_COMPATIBLE_INSURANCE);
			List<Member> members = new ArrayList<>();
			Member member = new Member();
			member.setId("123");
			member.setPriority((long) 12);
			members.add(member);
			gp.setMembers(members);
			Assert.assertNotNull(AccessoryInsuranceServiceImpl.getInsuranceProductList(null, gp));
		} catch (Exception e) {

		}
	}

	@Test
	public void testGetInsuranceProductListWithInvalidGroupType() {
		Group gp = new Group();
		gp.setGroupType("acd");
		List<Member> listOfInsuranceMembers = new ArrayList<>();
		Assert.assertNotNull(AccessoryInsuranceServiceImpl.getInsuranceProductList(listOfInsuranceMembers, gp));
	}

	@Test
	public void testValidateInsuranceNullable() {
		try {
			AccessoryInsuranceServiceImpl.validateInsuranceNullable(null, null);
		} catch (Exception e) {
			Assert.assertEquals( "No Compatible Insurances found for given device Id",e.getMessage());
		}
	}

	@Test
	public void testValidateInsuranceNullableWithEmptyInsurance() {
		try {
			Insurances insurance = new Insurances();
			List<Insurance> insuranceList = new ArrayList<>();
			insurance.setInsuranceList(insuranceList);
			AccessoryInsuranceServiceImpl.validateInsuranceNullable(null, insurance);
		} catch (Exception e) {
			Assert.assertEquals( "No Compatible Insurances found for given device Id",e.getMessage());
		}
	}

	@Test
	public void testGetInsuranceProductListWithNullGroup() {
		try {
			Group gp = new Group();
			gp.setGroupType(null);
			List<Member> listOfInsuranceMembers = new ArrayList<>();
			Assert.assertNotNull(AccessoryInsuranceServiceImpl.getInsuranceProductList(listOfInsuranceMembers, gp));
		} catch (Exception e) {
			Assert.assertEquals( "No Compatible Insurances found for given device Id",e.getMessage());
		}
	}

	@Test
	public void testGetInsuranceProductListWithNullMember() {
		try {
			Group gp = new Group();
			gp.setGroupType(null);
			List<Member> listOfInsuranceMembers = new ArrayList<>();
			Assert.assertNotNull(AccessoryInsuranceServiceImpl.getInsuranceProductList(listOfInsuranceMembers, null));
		} catch (Exception e) {
			Assert.assertEquals("No Compatible Insurances found for given device Id",e.getMessage());
		}
	}

	@Test
	public void getInsuranceByDeviceIdNull() {
		try {
			Assert.assertNotNull(accessoryInsuranceService.getInsuranceByDeviceId(null, "Acquisition"));
		} catch (Exception e) {
			Assert.assertEquals( "No Compatible Insurances found for given device Id",e.getMessage());
		}
	}

	@Test
	public void getInsuranceByDeviceIdInvalid() {
		try {
			Assert.assertNotNull(accessoryInsuranceService.getInsuranceByDeviceId("098578", "Acquisition"));
		} catch (Exception e) {
			Assert.assertEquals( "No Compatible Insurances found for given device Id",e.getMessage());
		}
	}

	@Test
	public void getInsuranceByDeviceIdInvalidDevice() {
		try {
			Assert.assertNotNull(accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition"));
		} catch (Exception e) {
			Assert.assertEquals( "No Compatible Insurances found for given device Id",e.getMessage());
		}
	}

	@Test
	public void getInsuranceByDeviceIdInvalidCP() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setIsDeviceProduct(false);
			given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(cp);
			Assert.assertNotNull(accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition"));
		} catch (Exception e) {
			Assert.assertEquals( "Given DeviceId is not ProductClass Handset",e.getMessage());
		}
	}

	@Test
	public void getInsuranceByDeviceIdInvalidProductClass() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setProductClass("abc");
			given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(cp);
			Assert.assertNotNull(accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition"));
		} catch (Exception e) {
			Assert.assertEquals( "Given DeviceId is not ProductClass Handset",e.getMessage());
		}
	}

	@Test
	public void getInsuranceCPProductGroupNull() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setProductGroups(null);
			given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(cp);
			Assert.assertNotNull(accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition"));
		} catch (Exception e) {
			Assert.assertEquals( "No Compatible Insurances found for given device Id",e.getMessage());
		}
	}

	@Test
	public void getInsuranceCPProductGroupNullNUll() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			ProductGroups pg = cp.getProductGroups();
			pg.setProductGroup(null);
			cp.setProductGroups(pg);
			given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(cp);
			Assert.assertNull(accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition"));
		} catch (Exception e) {
			Assert.assertEquals( "No Compatible Insurances found for given device Id",e.getMessage());
		}
	}

	@Test
	public void getInsuranceCPProductGroupEmpty() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			ProductGroups pg = cp.getProductGroups();
			List<ProductGroup> pgg = new ArrayList<>();
			pg.setProductGroup(pgg);
			cp.setProductGroups(pg);
			given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(cp);
			Assert.assertNull(accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition"));
		} catch (Exception e) {
			Assert.assertEquals( "No Compatible Insurances found for given device Id",e.getMessage());
		}
	}

	@Test
	public void getInsuranceCPNull() {
		try {
			given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(null);
			Assert.assertNotNull(accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition"));
		} catch (Exception e) {
			Assert.assertEquals( "No Compatible Insurances found for given device Id",e.getMessage());
		}
	}

	@Test
	public void getInsuranceCPProductGroupABC() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			ProductGroups pg = cp.getProductGroups();
			List<ProductGroup> pgg = new ArrayList<>();
			Collection<ProductGroup> pgc = new ArrayList<>();
			ProductGroup pggg = new ProductGroup();
			pggg.setProductGroupName("abc");
			pggg.setProductGroupRole("abc");
			pgc.add(pggg);
			pgg.addAll(pgc);
			pg.setProductGroup(pgg);
			cp.setProductGroups(pg);
			given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(cp);
			Assert.assertNotNull(accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition"));
		} catch (Exception e) {
			Assert.assertEquals( "No Compatible Insurances found for given device Id",e.getMessage());
		}

	}

	@Test
	public void getListOfDeviceTile() {

		given(response.getCommercialProduct(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getCommercialProductByDeviceId_093353_PAYG());
		given(response.getListOfGroupFromJson(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getListOfProductGroupFromProductGroupRepository());
		given(response.getCommercialProductFromJson(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getCommercialProductsListOfMakeAndModel());
		given(response.getCommercialBundle(ArgumentMatchers.any())).willReturn(CommonMethods.getCommercialBundle());
		given(response.getListOfCommercialBundleFromJson(ArgumentMatchers.any()))
				.willReturn(Arrays.asList(CommonMethods.getCommercialBundle()));
		given(deviceDAOMock.getPriceForBundleAndHardwareListFromTupleListAsync(ArgumentMatchers.anyList(),
				ArgumentMatchers.anyString(), ArgumentMatchers.anyString(), ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString()))
						.willReturn(CommonMethods.getPriceForBundleAndHardwareListFromTupleListAsync());
		given(deviceDAOMock.getBundleAndHardwarePromotionsListFromBundleListAsync(ArgumentMatchers.anyList(),
				ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
						.willReturn(CommonMethods.getBundleAndHardwarePromotionsListFromBundleListAsync());

		PriceForBundleAndHardware[] obj = null;
		try {
			ObjectMapper mapper = new ObjectMapper();

			String jsonString = new String(CommonMethods.readFile("\\rest-mock\\PRICE-V1.json"));
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			obj = mapper.readValue(jsonString, PriceForBundleAndHardware[].class);
		} catch (IOException e) {

		}

		RequestForBundleAndHardware requestForBundleAndHardware = new RequestForBundleAndHardware();
		List<BundleAndHardwareTuple> bundleList = new ArrayList<>();
		BundleAndHardwareTuple bundle = new BundleAndHardwareTuple();
		bundle.setBundleId("110075");
		bundle.setHardwareId("093353");
		bundleList.add(bundle);
		requestForBundleAndHardware.setBundleAndHardwareList(bundleList);

		BundleAndHardwarePromotions[] obj1 = null;
		try {
			String jsonString1 = new String(CommonMethods.readFile("\\BundleandhardwarePromotuions.json"));
			obj1 = new ObjectMapper().readValue(jsonString1, BundleAndHardwarePromotions[].class);
		} catch (Exception e) {

		}
		List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
		BundleAndHardwareTuple bundleAndHardwareTuple1 = new BundleAndHardwareTuple();
		bundleAndHardwareTuple1.setBundleId("110154");
		bundleAndHardwareTuple1.setHardwareId("093353");
		bundleHardwareTupleList.add(bundleAndHardwareTuple1);
		BundleAndHardwareRequest request = new BundleAndHardwareRequest();
		request.setBundleAndHardwareList(bundleHardwareTupleList);
		given(restTemplate.postForObject("http://PROMOTION-V1/promotion/queries/ForBundleAndHardware", request,
				BundleAndHardwarePromotions[].class)).willReturn(obj1);
		// requestForBundleAndHardware.setPackageType("Upgrade");
		given(restTemplate.postForObject("http://PRICE-V1/price/calculateForBundleAndHardware",
				requestForBundleAndHardware, PriceForBundleAndHardware[].class)).willReturn(obj);

		try {
			Assert.assertNotNull(deviceMakeAndModelServiceImpl.getListOfDeviceTile("apple", "iPhone-7", "DEVICE_PAYM",
					"093353", 40.0, "Upgrade", "W_HH_SIMONLY", "110154"));
		} catch (Exception e) {
			Assert.assertEquals( "No Devices Found for the given input search criteria",e.getMessage());
		}

	}

	@Test
	public void getDeviceTileByMakeAndModelForPAYG() {
		RequestForBundleAndHardware requestForBundleAndHardware = new RequestForBundleAndHardware();
		List<BundleAndHardwareTuple> bundleList = new ArrayList<>();
		BundleAndHardwareTuple bundle = new BundleAndHardwareTuple();
		bundle.setHardwareId("091191");
		bundleList.add(bundle);
		PriceForBundleAndHardware[] obj = null;
		try {
			ObjectMapper mapper = new ObjectMapper();

			String jsonString = new String(CommonMethods.readFile("\\rest-mock\\PRICE-V1.json"));
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			obj = mapper.readValue(jsonString, PriceForBundleAndHardware[].class);
		} catch (IOException e) {

		}
		requestForBundleAndHardware.setBillingType("payg");
		requestForBundleAndHardware.setBundleAndHardwareList(bundleList);
		given(restTemplate.postForObject("http://PRICE-V1/price/calculateForBundleAndHardware",
				requestForBundleAndHardware, PriceForBundleAndHardware[].class)).willReturn(obj);
		List<DeviceTile> deviceTile = deviceMakeAndModelServiceImpl.getDeviceTileByMakeAndModelForPAYG(
				CommonMethods.getCommercialProductsListOfMakeAndModel(), CommonMethods.getGroupForVariant(), "apple",
				"iPhone-7", "DEVICE_PAYM");
		Assert.assertNotNull(deviceTile);
		Assert.assertEquals( "091191",deviceTile.get(0).getDeviceId());
	}

	@Test
	public void getLeadBundleBasedOnAllPlans_Implementation() {

		RequestForBundleAndHardware requestForBundleAndHardware = new RequestForBundleAndHardware();
		List<BundleAndHardwareTuple> bundleList = new ArrayList<>();
		BundleAndHardwareTuple bundle = new BundleAndHardwareTuple();
		bundle.setBundleId("123");
		bundle.setHardwareId("093353");
		BundleAndHardwareTuple bundle1 = new BundleAndHardwareTuple();
		bundle1.setBundleId("456");
		bundle1.setHardwareId("093353");
		BundleAndHardwareTuple bundle2 = new BundleAndHardwareTuple();
		bundle2.setBundleId("789");
		bundle2.setHardwareId("093353");
		BundleAndHardwareTuple bundle3 = new BundleAndHardwareTuple();
		bundle3.setBundleId("101112");
		bundle3.setHardwareId("093353");
		BundleAndHardwareTuple bundle4 = new BundleAndHardwareTuple();
		bundle4.setBundleId("131415");
		bundle4.setHardwareId("093353");
		bundleList.add(bundle);
		bundleList.add(bundle1);
		bundleList.add(bundle2);
		bundleList.add(bundle3);
		bundleList.add(bundle4);
		PriceForBundleAndHardware[] obj = null;
		try {
			ObjectMapper mapper = new ObjectMapper();

			String jsonString = new String(CommonMethods.readFile("\\rest-mock\\PRICE-V1.json"));
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			obj = mapper.readValue(jsonString, PriceForBundleAndHardware[].class);
		} catch (IOException e) {

		}
		requestForBundleAndHardware.setBillingType("payg");
		requestForBundleAndHardware.setBundleAndHardwareList(bundleList);
		requestForBundleAndHardware.setPackageType("Upgrade");
		given(restTemplate.postForObject("http://PRICE-V1/price/calculateForBundleAndHardware",
				requestForBundleAndHardware, PriceForBundleAndHardware[].class)).willReturn(obj);

		CommercialBundle commercialBundle = deviceMakeAndModelServiceImpl.getLeadBundleBasedOnAllPlansImplementation(
				(double) (40), CommonMethods.getCommercialProduct(), CommonMethods.getPriceForBundleAndHardware(),
				"Upgrade", "DEVICE_PAYG");
		Assert.assertNotNull(commercialBundle);
		Assert.assertEquals( "110154",commercialBundle.getId());
		Assert.assertEquals( "Red Bundle",commercialBundle.getDisplayName());
	}

	@Test
	public void testgetDeviceDetailsImplementation() {

		DeviceDetails deviceDetails = deviceDetailsService.getDeviceDetailsImplementation("093353",
				JOURNEY_TYPE_SECONDLINE, "abc");
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals( "088417",deviceDetails.getDeviceId());
		Assert.assertEquals( "Handset",deviceDetails.getProductClass());
		Assert.assertEquals( "Apple iPhone 7 128GB jet black",deviceDetails.getName());
	}

	@Test
	public void testgetDeviceDetailsImplementationJTBlank() {
		DeviceDetails deviceDetails = deviceDetailsService.getDeviceDetailsImplementation("093353", "", "abc");
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals( "088417",deviceDetails.getDeviceId());
		Assert.assertEquals( "Handset",deviceDetails.getProductClass());
		Assert.assertEquals( "Apple iPhone 7 128GB jet black",deviceDetails.getName());
	}

	@Test
	public void testgetDeviceDetailsImplementationInvalidCP() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			ProductGroups pg = cp.getProductGroups();
			List<ProductGroup> pgg = new ArrayList<>();
			Collection<ProductGroup> pgc = new ArrayList<>();
			ProductGroup pggg = new ProductGroup();
			pggg.setProductGroupName("abc");
			pggg.setProductGroupRole("abc");
			pgc.add(pggg);
			pgg.addAll(pgc);
			pg.setProductGroup(pgg);
			cp.setProductGroups(pg);
			given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(null);
			Assert.assertNotNull(
					deviceDetailsService.getDeviceDetailsImplementation("093353", JOURNEY_TYPE_SECONDLINE, "abc"));
		} catch (Exception e) {
			Assert.assertEquals( "Received Null Values for the given device id",e.getMessage());
		}
	}

	@Test
	public void testgetDeviceDetailsImplementationInvalidCPIdNUll() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setId(null);
			given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(cp);
			Assert.assertNotNull(
					deviceDetailsService.getDeviceDetailsImplementation("093353", JOURNEY_TYPE_SECONDLINE, "abc"));
		} catch (Exception e) {
			Assert.assertEquals( "Received Null Values for the given device id",e.getMessage());
		}
	}

	@Test
	public void testgetDeviceDetailsImplementationInvalidCPDPInvalid() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setIsDeviceProduct(false);
			given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(cp);
			Assert.assertNull(
					deviceDetailsService.getDeviceDetailsImplementation("093353", JOURNEY_TYPE_SECONDLINE, "abc"));
		} catch (Exception e) {
			Assert.assertEquals( "Received Null Values for the given device id",e.getMessage());
		}
	}

	@Test
	public void testgetDeviceDetailsImplementationInvalidCPPCInvalid() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setProductClass("accessories");
			given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(cp);
			Assert.assertNull(
					deviceDetailsService.getDeviceDetailsImplementation("093353", JOURNEY_TYPE_SECONDLINE, "abc"));
		} catch (Exception e) {
			Assert.assertEquals( "Received Null Values for the given device id",e.getMessage());
		}
	}

	@Test
	public void testgetDeviceDetailsImplementationInvalidCPPCInvalid1() {
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		cp.setProductClass(STRING_DATA_DEVICE);
		given(response.getCommercialProduct(ArgumentMatchers.any())).willReturn(cp);
		DeviceDetails deviceDetails = deviceDetailsService.getDeviceDetailsImplementation("093353",
				JOURNEY_TYPE_SECONDLINE, "abc");
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals( "088417",deviceDetails.getDeviceId());
		Assert.assertEquals( "Data Device",deviceDetails.getProductClass());
		Assert.assertEquals("Apple iPhone 7 128GB jet black",deviceDetails.getName());
	}

	@Test
	public void testGetDeviceDetailsResponse() {
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		DeviceDetails deviceDetails = deviceDetailsService.getDeviceDetailsResponse("093353", "abc", "Acquisition", cp);
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals( "088417",deviceDetails.getDeviceId());
		Assert.assertEquals( "Handset",deviceDetails.getProductClass());
	}

	@Test
	public void testGetDeviceDetailsResponsePLNull() {
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		cp.setProductLines(null);
		DeviceDetails deviceDetails = deviceDetailsService.getDeviceDetailsResponse("093353", "abc", "Acquisition", cp);
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals( "088417",deviceDetails.getDeviceId());
		Assert.assertEquals( "Handset",deviceDetails.getProductClass());
		Assert.assertEquals( "Apple iPhone 7 128GB jet black",deviceDetails.getName());
	}

	@Test
	public void testGetDeviceDetailsResponsePLEmpty() {
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		List<String> pl = new ArrayList<>();
		cp.setProductLines(pl);
		DeviceDetails deviceDetails = deviceDetailsService.getDeviceDetailsResponse("093353", "abc",
				JOURNEY_TYPE_SECONDLINE, cp);
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals( "088417",deviceDetails.getDeviceId());
		Assert.assertEquals( "Handset",deviceDetails.getProductClass());
		Assert.assertEquals( "Apple iPhone 7 128GB jet black",deviceDetails.getName());
	}

	@Test
	public void testGetDeviceDetailsResponseJourney() {
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		DeviceDetails deviceDetails = deviceDetailsService.getDeviceDetailsResponse("093353", "abc",
				JOURNEY_TYPE_SECONDLINE, cp);
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals( "088417",deviceDetails.getDeviceId());
		Assert.assertEquals( "Handset",deviceDetails.getProductClass());
		Assert.assertEquals( "Apple iPhone 7 128GB jet black",deviceDetails.getName());
	}

	@Test
	public void testGetDeviceDetailsResponseJourneyUG() {
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		DeviceDetails deviceDetails = deviceDetailsService.getDeviceDetailsResponse("093353", "abc",
				JOURNEY_TYPE_UPGRADE, cp);
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals( "088417",deviceDetails.getDeviceId());
		Assert.assertEquals( "Handset",deviceDetails.getProductClass());
		Assert.assertEquals( "Apple iPhone 7 128GB jet black",deviceDetails.getName());
	}

	@Test
	public void testGetDeviceDetailsResponseOfferCodeNull() {
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		DeviceDetails deviceDetails = deviceDetailsService.getDeviceDetailsResponse("093353", null,
				JOURNEY_TYPE_SECONDLINE, cp);
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals( "088417",deviceDetails.getDeviceId());
		Assert.assertEquals( "Handset",deviceDetails.getProductClass());
		Assert.assertEquals( "Apple iPhone 7 128GB jet black",deviceDetails.getName());
	}

	@Test
	public void testGetDeviceDetailsResponseJourneyNull() {
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		DeviceDetails deviceDetails = deviceDetailsService.getDeviceDetailsResponse("093353", "abc", null, cp);
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals( "088417",deviceDetails.getDeviceId());
		Assert.assertEquals( "Handset",deviceDetails.getProductClass());
		Assert.assertEquals( "Apple iPhone 7 128GB jet black",deviceDetails.getName());
	}

	@Test
	public void testGetDeviceDetailsResponseJourneyOfferCddeNull() {
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		DeviceDetails deviceDetails = deviceDetailsService.getDeviceDetailsResponse("093353", null, null, cp);
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals( "088417",deviceDetails.getDeviceId());
		Assert.assertEquals( "Handset",deviceDetails.getProductClass());
		Assert.assertEquals( "Apple iPhone 7 128GB jet black",deviceDetails.getName());
	}

	@Test
	public void testGetDeviceDetailsResponseJourneyOfferNull() {
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		DeviceDetails deviceDetails = deviceDetailsService.getDeviceDetailsResponse("093353", null,
				JOURNEY_TYPE_UPGRADE, cp);
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals( "088417",deviceDetails.getDeviceId());
		Assert.assertEquals( "Handset",deviceDetails.getProductClass());
		Assert.assertEquals( "Apple iPhone 7 128GB jet black",deviceDetails.getName());
	}

	@Test
	public void testGetJourneyAndOfferCodeValidationForPAYG() {
		try {
			Validator.getJourneyAndOfferCodeValidationForPAYG("", JOURNEY_TYPE_ACQUISITION);
		} catch (Exception e) {
			Assert.assertEquals( null,e.getMessage());
		}
	}

	@Test
	public void testGetJourneyAndOfferCodeValidationForPAYGNull() {
		try {
			Validator.getJourneyAndOfferCodeValidationForPAYG("", "");
		} catch (Exception e) {
			Assert.assertEquals( null,e.getMessage());
		}
	}

	@Test
	public void testGetDeviceDetailsResponseJourneyUGNull() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			Assert.assertNotNull(
					deviceDetailsService.getDeviceDetailsResponse("093353", null, JOURNEY_TYPE_UPGRADE, cp));
		} catch (Exception e) {
			Assert.assertEquals( null,e.getMessage());
		}
	}

	@Test
	public void testGetDeviceDetailsResponseJourneyAcq() {
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		DeviceDetails deviceDetails = deviceDetailsService.getDeviceDetailsResponse("093353", "abc",
				JOURNEY_TYPE_ACQUISITION, cp);
		Assert.assertNotNull(deviceDetails);
		Assert.assertEquals( "088417",deviceDetails.getDeviceId());
		Assert.assertEquals( "Handset",deviceDetails.getProductClass());
		Assert.assertEquals( "Apple iPhone 7 128GB jet black",deviceDetails.getName());
	}

	@Test
	public void getDeviceSummeryImplementation() {
		List<com.vf.uk.dal.device.model.Member> member = new ArrayList<com.vf.uk.dal.device.model.Member>();
		com.vf.uk.dal.device.model.Member productGroupMember = new com.vf.uk.dal.device.model.Member();
		productGroupMember.setPriority("1");
		productGroupMember.setId("093353");
		member.add(productGroupMember);
		Map<String, CommercialProduct> commerProdMemMap = new HashMap<>();
		commerProdMemMap.put("093353", CommonMethods.getCommercialProduct());
		Map<String, CommercialBundle> commercialBundleMap = new HashMap<>();
		commercialBundleMap.put("110154", CommonMethods.getCommercialBundle());
		Map<String, Boolean> bundleIdMap = new HashMap<>();
		bundleIdMap.put("110154", true);
		Map<String, BundleAndHardwarePromotions> bundleAndHardwarePromotionsMap = new HashMap<>();
		bundleAndHardwarePromotionsMap.put("110154", CommonMethods.getListOfBundleAndHardwarePromotions().get(0));
		Map<String, String> leadPlanIdMap = new HashMap<>();
		leadPlanIdMap.put("110154", "093353");
		Map<String, PriceForBundleAndHardware> priceMapForParticularDevice = new HashMap<>();
		priceMapForParticularDevice.put("093353", CommonMethods.getPriceForBundleAndHardware().get(0));
		Map<String, Boolean> fromPricingMap = new HashMap<>();
		fromPricingMap.put("093353", true);
		Map<String, List<BundleAndHardwarePromotions>> promotionsMap = new HashMap<>();
		promotionsMap.put("093353", CommonMethods.getListOfBundleAndHardwarePromotions());
		Map<String, List<PriceForBundleAndHardware>> priceMap = new HashMap<>();
		priceMap.put("093353", CommonMethods.getPriceForBundleAndHardware());
		Map<String, CommercialBundle> commercialBundleMap1 = new HashMap<>();
		commercialBundleMap1.put("183099", CommonMethods.getCommercialBundleForMAkeAndModel());
		List<String> listOfLeadPlanId = new ArrayList<>();
		BundleAndHardwareTuple bundleAndHardware = new BundleAndHardwareTuple();
		bundleAndHardware.setBundleId("110154");
		bundleAndHardware.setHardwareId("093353");
		List<BundleAndHardwareTuple> bundleAndHardwareTupleList = new ArrayList<>();
		bundleAndHardwareTupleList.add(bundleAndHardware);
		CompletableFuture<List<DeviceSummary>> deviceSummary = deviceMakeAndModelServiceImpl
				.getDeviceSummeryImplementation(member, CommonMethods.getPriceForBundleAndHardware(), commerProdMemMap,
						true, "Upgrade", 40.00, commercialBundleMap, bundleIdMap, "110154",
						bundleAndHardwarePromotionsMap, leadPlanIdMap, "DEVICE_PAYM", priceMapForParticularDevice,
						fromPricingMap, "1");
		deviceMakeAndModelServiceImpl.getPriceAndPromotionMap("Upgrade", commercialBundleMap1, leadPlanIdMap,
				listOfLeadPlanId, bundleAndHardwarePromotionsMap, priceMapForParticularDevice, promotionsMap, priceMap,
				"093353", true);
		deviceMakeAndModelServiceImpl.getPriceAndPromotionMap("Upgrade", commercialBundleMap1, leadPlanIdMap,
				listOfLeadPlanId, bundleAndHardwarePromotionsMap, priceMapForParticularDevice, promotionsMap, priceMap,
				"093353", true);

		deviceMakeAndModelServiceImpl.getListOfLeadPlan("110154", bundleAndHardwareTupleList, bundleIdMap,
				fromPricingMap, leadPlanIdMap, listOfLeadPlanId, CommonMethods.getMemberList().get(0),
				CommonMethods.getCommercialProduct());
		Assert.assertNotNull(deviceSummary);
	}

	@Test
	public void notNullisLeadPlanWithinCreditLimit_Implementation() {
		boolean isLeadPlanWithinCreditLimit = deviceMakeAndModelServiceImpl.isLeadPlanWithinCreditLimit_Implementation(
				CommonMethods.getCommercialProduct(), (double) 40, CommonMethods.getPriceForBundleAndHardware(),
				"Upgrade", "DEVICE_PAYM");
		Assert.assertNotNull(isLeadPlanWithinCreditLimit);
		Assert.assertFalse(isLeadPlanWithinCreditLimit);
	}

	@Test
	public void notNullgetLowestMontlyPrice() {
		BundleModelAndPrice modelPrice = new BundleModelAndPrice();
		modelPrice.setBundleModel(CommonMethods.getBundleModelListForBundleList().get(0));
		BundlePrice bundlePrice = new BundlePrice();
		bundlePrice.setBundleId("183099");
		MerchandisingPromotion merchandisingPromotions = new MerchandisingPromotion();
		merchandisingPromotions.setDiscountId("107531");
		merchandisingPromotions.setLabel("20% off with any handset");
		merchandisingPromotions.setTag("AllPhone.full.2017");
		merchandisingPromotions.setDescription("description");
		merchandisingPromotions.setMpType("limited_discount");

		bundlePrice.setMerchandisingPromotions(merchandisingPromotions);
		Price monthlyDiscountPrice = new Price();
		monthlyDiscountPrice.setGross("10.11");
		monthlyDiscountPrice.setNet("11.23");
		monthlyDiscountPrice.setVat("14.56");

		Price monthlyPrice = new Price();
		monthlyDiscountPrice.setGross("13.64");
		monthlyDiscountPrice.setNet("12.5");
		monthlyDiscountPrice.setVat("8.56");

		bundlePrice.setMonthlyDiscountPrice(monthlyDiscountPrice);
		bundlePrice.setMonthlyPrice(monthlyPrice);
		modelPrice.setBundlePrice(bundlePrice);
		conditionalHelper.getLowestMontlyPrice((float) 40,
				CommonMethods.getBundleModelListForBundleListForConditional(),
				CommonMethods.getCompatibleBundleListJson(), modelPrice);
		List<String> listOfProductsNew = new ArrayList<>();
		listOfProductsNew.add("093353");
		given(response.getListOfProductModel(ArgumentMatchers.any())).willReturn(CommonMethods.getProductModel());
		given(response.getListOfBundleModel(ArgumentMatchers.any()))
				.willReturn(CommonMethods.getBundleModelListForBundleListForConditional());
		given(deviceDAOMock.getBundleDetailsFromComplansListingAPI(ArgumentMatchers.anyString(),
				ArgumentMatchers.anyString())).willReturn(CommonMethods.getCompatibleBundleListJson());
		Assert.assertNotNull(
				conditionalHelper.calculatePlan((float) 10, listOfProductsNew, CommonMethods.getProductModel()));
	}

	@Test
	public void resetDeviceId_Implementation() {
		List<DeviceSummary> deviceSummaryList = new ArrayList<DeviceSummary>();
		DeviceSummary deviceSummary = new DeviceSummary();
		deviceSummary.setDeviceId("093353");
		deviceSummary.setColourHex("D10000000");
		deviceSummary.setColourName("Grey");
		deviceSummary.setDisplayDescription("5.5 inch");
		deviceSummary.setDisplayName("display name");
		deviceSummary.setDeviceId(String.valueOf(122));
		deviceSummary.setIsAffordable(true);
		deviceSummary.setLeadPlanId("Lead plan Id");
		deviceSummary.setLeadPlanDisplayName("Yearly Plan");
		deviceSummary.setMemory("64GB");
		deviceSummary.setPreOrderable(false);
		List<MediaLink> merchandisingMedia = new ArrayList<MediaLink>();
		MediaLink mediaLink = new MediaLink();
		mediaLink.setId("1022");
		mediaLink.setType("JPEG");
		mediaLink.setValue("283");
		deviceSummary.setMerchandisingMedia(merchandisingMedia);
		deviceSummaryList.add(deviceSummary);
		deviceMakeAndModelServiceImpl.resetDeviceIdImplementation(true,
				CommonMethods.getDeviceTile("Apple", "iPhone-7", "Device_PAYM").get(0), deviceSummaryList, "093353");
	}

	@Test
	public void testSettingPriceAndPromotionsToListOfDevicesNull() {
		DeviceDetails dd = CommonMethods.getDevice_One("093353");
		List<DeviceDetails> ddList = new ArrayList<>();
		ddList.add(dd);
		deviceDetailsService.settingPriceAndPromotionsToListOfDevices(null, ddList);
	}

	@Test
	public void testgetReviewRatingListImplementation() {

		given(deviceDAOMock.getBazaarVoice(ArgumentMatchers.anyString())).willThrow(new ApplicationException(""));
		try {
			utility.getReviewRatingListImplementation(Arrays.asList("093353"));
		} catch (Exception e) {

		}
		List<BundleHeader> bundleHeader = utility
				.getAscendingOrderForBundleHeaderPrice(CommonMethods.getBundleHeaderList("SIMO"));
		Assert.assertNotNull(bundleHeader);
		Assert.assertEquals( "RedBundle",bundleHeader.get(0).getName());
	}

	@Test
	public void testSettingPriceAndPromotionsToListOfDevicesEmpty() {
		DeviceDetails dd = CommonMethods.getDevice_One("093353");
		List<DeviceDetails> ddList = new ArrayList<>();
		ddList.add(dd);
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = new ArrayList<>();
		deviceDetailsService.settingPriceAndPromotionsToListOfDevices(listOfPriceForBundleAndHardware, ddList);
	}

	@Test
	public void testSettingPriceAndPromotionsToListOfDevices() {
		DeviceDetails dd = CommonMethods.getDevice_One("093353");
		List<DeviceDetails> ddList = new ArrayList<>();
		ddList.add(dd);
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = new ArrayList<>();
		PriceForBundleAndHardware priceForBundleAndHardware = new PriceForBundleAndHardware();
		priceForBundleAndHardware.setHardwarePrice(null);
		listOfPriceForBundleAndHardware.add(priceForBundleAndHardware);
		deviceDetailsService.settingPriceAndPromotionsToListOfDevices(listOfPriceForBundleAndHardware, ddList);
	}

	@Test
	public void testSettingPriceAndPromotionsToListOfDevicesWithEmptyList() {
		try {
			DeviceDetails dd = CommonMethods.getDevice_One("093353");
			List<DeviceDetails> ddList = new ArrayList<>();
			ddList.add(dd);
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = CommonMethods
					.getPriceForBundleAndHardware();
			PriceForBundleAndHardware priceForBundleAndHardware = new PriceForBundleAndHardware();
			priceForBundleAndHardware.setBundlePrice(null);
			listOfPriceForBundleAndHardware.add(priceForBundleAndHardware);
			deviceDetailsService.settingPriceAndPromotionsToListOfDevices(listOfPriceForBundleAndHardware, ddList);
		} catch (Exception e) {
			Assert.assertEquals( null,e.getMessage());
		}
	}

	@Test
	public void testSettingPriceAndPromotionsToListOfDevicesMPNull() {
		try {
			DeviceDetails dd = CommonMethods.getDevice_One("093353");
			List<DeviceDetails> ddList = new ArrayList<>();
			ddList.add(dd);
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = new ArrayList<>();
			PriceForBundleAndHardware priceForBundleAndHardware = CommonMethods.getUtilityPriceForBundleAndHardware();
			HardwarePrice hp = priceForBundleAndHardware.getHardwarePrice();
			hp.setMerchandisingPromotions(null);
			priceForBundleAndHardware.setHardwarePrice(hp);
			listOfPriceForBundleAndHardware.add(priceForBundleAndHardware);
			deviceDetailsService.settingPriceAndPromotionsToListOfDevices(listOfPriceForBundleAndHardware, ddList);
		} catch (Exception e) {
			Assert.assertEquals( null,e.getMessage());
		}
	}

	@Test
	public void testSettingPriceAndPromotionsToListOfDevicesMPNullForBundle() {
		try {
			DeviceDetails dd = CommonMethods.getDevice_One("093353");
			List<DeviceDetails> ddList = new ArrayList<>();
			ddList.add(dd);
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = new ArrayList<>();
			PriceForBundleAndHardware priceForBundleAndHardware = CommonMethods.getUtilityPriceForBundleAndHardware();
			BundlePrice hp = priceForBundleAndHardware.getBundlePrice();
			hp.setMerchandisingPromotions(null);
			priceForBundleAndHardware.setBundlePrice(hp);
			listOfPriceForBundleAndHardware.add(priceForBundleAndHardware);
			deviceDetailsService.settingPriceAndPromotionsToListOfDevices(listOfPriceForBundleAndHardware, ddList);
		} catch (Exception e) {
			Assert.assertEquals( null,e.getMessage());
		}
	}

	@Test
	public void testSettingPriceAndPromotionsToListOfDevicesMPTagNull() {
		try {
			DeviceDetails dd = CommonMethods.getDevice("093353");
			List<DeviceDetails> ddList = new ArrayList<>();
			ddList.add(dd);
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = new ArrayList<>();
			PriceForBundleAndHardware priceForBundleAndHardware = CommonMethods.getUtilityPriceForBundleAndHardware();
			BundlePrice hp = priceForBundleAndHardware.getBundlePrice();
			MerchandisingPromotion mp = hp.getMerchandisingPromotions();
			mp.setTag(null);
			hp.setMerchandisingPromotions(mp);
			priceForBundleAndHardware.setBundlePrice(hp);
			listOfPriceForBundleAndHardware.add(priceForBundleAndHardware);
			deviceDetailsService.settingPriceAndPromotionsToListOfDevices(listOfPriceForBundleAndHardware, ddList);
		} catch (Exception e) {
			Assert.assertEquals( null,e.getMessage());
		}
	}

	@Test
	public void testSettingPriceAndPromotionsToListOfDevice() {
		DeviceDetails dd = CommonMethods.getDevice("093353");
		List<DeviceDetails> ddList = new ArrayList<>();
		ddList.add(dd);
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = CommonMethods.getPriceForBundleAndHardware();
		deviceDetailsService.settingPriceAndPromotionsToListOfDevices(listOfPriceForBundleAndHardware, ddList);
	}

	@Test
	public void testSettingPriceAndPromotionsToListOfDevices12() {
		try {
			DeviceDetails dd = CommonMethods.getDevice_One("93353");
			List<DeviceDetails> ddList = new ArrayList<>();
			ddList.add(dd);
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = CommonMethods
					.getPriceForBundleAndHardware();
			deviceDetailsService.settingPriceAndPromotionsToListOfDevices(listOfPriceForBundleAndHardware, ddList);
		} catch (Exception e) {
			Assert.assertEquals( null,e.getMessage());
		}
	}

	@Test
	public void testSettingPriceAndPromotionsToListOfDevicesMPInvalidTag() {
		try {
			DeviceDetails dd = CommonMethods.getDevice_One("093353");
			List<DeviceDetails> ddList = new ArrayList<>();
			ddList.add(dd);
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = new ArrayList<>();
			PriceForBundleAndHardware priceForBundleAndHardware = CommonMethods.getUtilityPriceForBundleAndHardware();
			BundlePrice hp = priceForBundleAndHardware.getBundlePrice();
			MerchandisingPromotion mp = new MerchandisingPromotion();
			mp.setDescription("abc");
			mp.setDiscountId("093353");
			mp.setMpType("abc");
			mp.setTag("abc");
			hp.setMerchandisingPromotions(mp);
			priceForBundleAndHardware.setBundlePrice(hp);
			listOfPriceForBundleAndHardware.add(priceForBundleAndHardware);
			deviceDetailsService.settingPriceAndPromotionsToListOfDevices(listOfPriceForBundleAndHardware, ddList);
		} catch (Exception e) {
			Assert.assertEquals( null,e.getMessage());
		}
	}

	@Test
	public void testGetMediaListForDevice() {
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		List<MediaLink> mediaLink = deviceDetailsService.getMediaListForDevice(cp);
		Assert.assertNotNull(mediaLink);
	}

	@Test
	public void testGetMediaListForDeviceCP() {
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		cp.setPromoteAs(null);
		Assert.assertNotNull(deviceDetailsService.getMediaListForDevice(cp));
	}

	@Test
	public void testGetMediaListForDeviceCPNull() {
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		PromoteAs pa = cp.getPromoteAs();
		pa.setPromotionName(null);
		cp.setPromoteAs(pa);
		Assert.assertNotNull(deviceDetailsService.getMediaListForDevice(cp));
	}

	@Test
	public void testGetMediaListForDeviceWithPromoteAs() {
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		PromoteAs pa = cp.getPromoteAs();
		List<String> promotionName = new ArrayList<>();
		pa.setPromotionName(promotionName);
		cp.setPromoteAs(pa);
		Assert.assertNotNull(deviceDetailsService.getMediaListForDevice(cp));
	}

	@Test
	public void testGetMediaListForDeviceWithPromoName() {
		given(response.getMerchandisingPromotion(ArgumentMatchers.any())).willReturn(null);
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		PromoteAs pa = cp.getPromoteAs();
		List<String> promotionName = new ArrayList<>();
		promotionName.add("Abc");
		pa.setPromotionName(promotionName);
		cp.setPromoteAs(pa);
		Assert.assertNotNull(deviceDetailsService.getMediaListForDevice(cp));
	}

	@Test
	public void testGetMediaListForDeviceConditionDiscount() {
		com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion mp = CommonMethods.getMemPro();
		mp.setType("conditional_full_discount");
		given(response.getMerchandisingPromotion(ArgumentMatchers.any())).willReturn(mp);
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		PromoteAs pa = cp.getPromoteAs();
		List<String> promotionName = new ArrayList<>();
		promotionName.add("Abc");
		pa.setPromotionName(promotionName);
		cp.setPromoteAs(pa);
		Assert.assertNotNull(deviceDetailsService.getMediaListForDevice(cp));
	}

	@Test
	public void testGetMediaListForDeviceFullDiscount() {
		com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion mp = CommonMethods.getMemPro();
		mp.setType("conditional_full_discount");
		given(response.getMerchandisingPromotion(ArgumentMatchers.any())).willReturn(mp);
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		PromoteAs pa = cp.getPromoteAs();
		List<String> promotionName = new ArrayList<>();
		promotionName.add("handset.hardware.percentage");
		pa.setPromotionName(promotionName);
		cp.setPromoteAs(pa);
		Assert.assertNotNull(deviceDetailsService.getMediaListForDevice(cp));
	}

	@Test
	public void testGetMediaListForDeviceMpFullDiscount() {
		try {
			com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion mp = CommonMethods.getMemPro();
			mp.setType("conditional_full_discount");
			given(response.getMerchandisingPromotion(ArgumentMatchers.any())).willReturn(mp);
			CommercialProduct cp = CommonMethods.getCommercialProductsListOfAccessoriesWithEndDate_One();
			PromoteAs pa = cp.getPromoteAs();
			List<String> promotionName = new ArrayList<>();
			promotionName.add("handset.hardware.percentage");
			pa.setPromotionName(promotionName);
			cp.setPromoteAs(pa);
			Assert.assertNotNull(deviceDetailsService.getMediaListForDevice(cp));
		} catch (Exception e) {
			Assert.assertEquals( null,e.getMessage());
		}
	}

	@Test
	public void testGetMediaListForDeviceCPNMP1() {
		try {
			com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion mp = CommonMethods.getMemPro();
			mp.setType("conditional_limited_discount");
			given(response.getMerchandisingPromotion(ArgumentMatchers.any())).willReturn(mp);
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			PromoteAs pa = cp.getPromoteAs();
			List<String> promotionName = new ArrayList<>();
			promotionName.add("Abc");
			pa.setPromotionName(promotionName);
			cp.setPromoteAs(pa);
			Assert.assertNotNull(deviceDetailsService.getMediaListForDevice(cp));
		} catch (Exception e) {
			Assert.assertEquals( null,e.getMessage());
		}
	}

	@Test
	public void testGetMediaListForDeviceCPNMP2() {
		try {
			com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion mp = CommonMethods.getMemPro();
			mp.setType("limited_time");
			given(response.getMerchandisingPromotion(ArgumentMatchers.any())).willReturn(mp);
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			PromoteAs pa = cp.getPromoteAs();
			List<String> promotionName = new ArrayList<>();
			promotionName.add("Abc");
			pa.setPromotionName(promotionName);
			cp.setPromoteAs(pa);
			Assert.assertNotNull(deviceDetailsService.getMediaListForDevice(cp));
		} catch (Exception e) {
			Assert.assertEquals( null,e.getMessage());
		}
	}

	@Test
	public void testgetListOfPriceForBundleAndHardwareImplementationWithEmptyId() {
		Map<String, CommercialBundle> commerBundleIdMap = new HashMap<>();
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		List<BundleAndHardwareTuple> tuple = deviceDetailsService.getListOfPriceForBundleAndHardwareImplementation(cp,
				commerBundleIdMap, "Acquisition");
		Assert.assertNotNull(tuple);
	}

	@Test
	public void testgetListOfPriceForBundleAndHardwareImplementation() {
		Map<String, CommercialBundle> commerBundleIdMap = new HashMap<>();
		CommercialBundle cb = CommonMethods.getCommercialBundle();
		cb.setId("088417");
		commerBundleIdMap.put("088417", cb);
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		List<BundleAndHardwareTuple> tuple = deviceDetailsService.getListOfPriceForBundleAndHardwareImplementation(cp,
				commerBundleIdMap, "Acquisition");
		Assert.assertNotNull(tuple);
	}

	@Test
	public void testgetListOfPriceForBundleAndHardwareImplementationCpIdBlank() {
		Map<String, CommercialBundle> commerBundleIdMap = new HashMap<>();
		CommercialBundle cb = CommonMethods.getCommercialBundle();
		cb.setId("088417");
		commerBundleIdMap.put("088417", cb);
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		cp.setLeadPlanId("");
		Assert.assertNotNull(
				deviceDetailsService.getListOfPriceForBundleAndHardwareImplementation(cp, null, "Acquisition"));
	}

	@Test
	public void testgetListOfPriceForBundleAndHardwareImplementationLeadPlanIdnull() {
		Map<String, CommercialBundle> commerBundleIdMap = new HashMap<>();
		CommercialBundle cb = CommonMethods.getCommercialBundle();
		cb.setId("088417");
		commerBundleIdMap.put("088417", cb);
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		cp.setLeadPlanId(null);
		Assert.assertNotNull(
				deviceDetailsService.getListOfPriceForBundleAndHardwareImplementation(cp, null, "Acquisition"));
	}

	@Test
	public void TestvalidateOfferValidForDeviceImplementation() {
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		cp.setPromoteAs(null);
		Assert.assertNotNull(deviceDetailsService.validateOfferValidForDeviceImplementation(cp, "Acquisition", "acb"));
	}

	@Test
	public void TestvalidateOfferValidForDeviceImplementation1() {
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		PromoteAs pa = cp.getPromoteAs();
		pa.setPromotionName(null);
		cp.setPromoteAs(pa);
		Assert.assertNotNull(deviceDetailsService.validateOfferValidForDeviceImplementation(cp, "Acquisition", "acb"));
	}

	@Test
	public void TestvalidateOfferValidForDeviceImplementationEmptyPromotionList() {
		CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
		PromoteAs pa = cp.getPromoteAs();
		List<String> promotioname = new ArrayList<>();
		pa.setPromotionName(promotioname);
		cp.setPromoteAs(pa);
		Assert.assertNotNull(deviceDetailsService.validateOfferValidForDeviceImplementation(cp, "Acquisition", "acb"));
	}

	@Test
	public void TestvalidateOfferValidForDeviceImplementationWithPromotionName() {
		try {
			com.vf.uk.dal.device.model.merchandisingpromotion.MerchandisingPromotion mp = CommonMethods.getMemPro();
			mp.setType("conditional_full_discount");
			given(response.getMerchandisingPromotion(ArgumentMatchers.any())).willReturn(CommonMethods.getMemPro());
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			PromoteAs pa = cp.getPromoteAs();
			List<String> promotioname = new ArrayList<>();
			promotioname.add("limited_time");
			pa.setPromotionName(promotioname);
			cp.setPromoteAs(pa);
			Assert.assertNotNull(
					deviceDetailsService.validateOfferValidForDeviceImplementation(cp, "Acquisition", "acb"));
		} catch (Exception e) {

		}
	}

	@Test
	public void notNullGetPriceAndPromotionMap() {
		Map<String, List<BundleAndHardwarePromotions>> promotionsMap = new HashMap<>();
		promotionsMap.put("093353", CommonMethods.getListOfBundleAndHardwarePromotions());
		Map<String, List<PriceForBundleAndHardware>> priceMap = new HashMap<>();
		priceMap.put("093353", CommonMethods.getOfferAppliedPrice());
		deviceMakeAndModelServiceImpl.getPriceAndPromotionMap("SecondLine", Collections.emptyMap(),
				Collections.emptyMap(), Collections.emptyList(), new HashMap<>(), new HashMap<>(), promotionsMap,
				priceMap, "093353", false);
	}

	@Test
	public void testGetDoubleFrmString() {
		Double price = DeviceUtils.getDoubleFrmString("2.5");
		Assert.assertNotNull(price);
		Assert.assertEquals( 2.5,price, 0);
	}

	@Test
	public void testGetMinimumPriceMapForPayG() {
		Map<String, String> minimumPriceMap = new HashMap<>();
		minimumPriceMap.put("093353", "12.8");
		Map<String, List<PriceForBundleAndHardware>> groupNamePriceMap = new HashMap<>();
		groupNamePriceMap.put("093353", CommonMethods.getPriceForBundleAndHardwareListFromTupleList());
		DeviceUtils.getMinimumPriceMapForPayG(minimumPriceMap, groupNamePriceMap);
	}

	@Test
	public void testGetDiscountTypeAndComparePrice_Implementation() {
		com.vf.uk.dal.device.client.entity.price.BundlePrice bundlePrice = new com.vf.uk.dal.device.client.entity.price.BundlePrice();
		bundlePrice.setBundleId("110154");

		com.vf.uk.dal.device.client.entity.price.Price monthlyPrice = new com.vf.uk.dal.device.client.entity.price.Price();
		monthlyPrice.setGross("10.3");
		monthlyPrice.setNet("12.4");
		monthlyPrice.setVat("11");
		bundlePrice.setMonthlyPrice(monthlyPrice);
		Assert.assertNotNull(
				DeviceServiceImplUtility.getDiscountTypeAndComparePriceImplementation(112.23, bundlePrice));
		Assert.assertTrue(
				DeviceServiceImplUtility.getDiscountTypeAndComparePriceImplementation(112.23, bundlePrice));
	}

	@Test
	public void testisPlanAffordable_Implementation() {
		DeviceSummary deviceSummary = new DeviceSummary();
		deviceSummary.setColourHex("D10000000");
		deviceSummary.setColourName("Grey");
		deviceSummary.setDisplayDescription("5.5 inch");
		deviceSummary.setDisplayName("display name");
		deviceSummary.setDeviceId(String.valueOf(122));
		deviceSummary.setLeadPlanId("Lead plan Id");
		deviceSummary.setLeadPlanDisplayName("Yearly Plan");
		deviceSummary.setMemory("64GB");
		deviceSummary.setPreOrderable(false);
		DeviceServiceImplUtility.isPlanAffordableImplementation(deviceSummary, CommonMethods.getCommercialBundle(),
				222.3, true);
	}

	@Test
	public void getListOfDeviceTileTestInvalidGroupType() {
		Double value = (double) 12345;
		try {
			deviceMakeAndModelServiceImpl.getListOfDeviceTileImplementation("apple", "iPhone-7", "1232_41ed", "093353",
					"Acquisition", value, "123", "180232");
		} catch (Exception e) {
			Assert.assertEquals("Received Null Values for the given product group type", e.getMessage());
		}
	}

	@Test
	public void getListOfDeviceTileTestInvalidGroupTypeDevice_payg() {
		Double value = (double) 12345;
		try {
			deviceMakeAndModelServiceImpl.getListOfDeviceTileImplementation("apple", "iPhone-7", STRING_DEVICE_PAYG,
					"093353", "Acquisition", value, "123", "180232");
		} catch (Exception e) {
			Assert.assertEquals("No Devices Found for the given input search criteria", e.getMessage());
		}
	}

	@Test
	public void getMembersForGroupTest() {

		deviceMakeAndModelServiceImpl.resetDeviceIdImplementation(true, CommonMethods.getDeviceTilee(),
				CommonMethods.getDeviceSummary(), "122");
	}

	@Test
	public void getMembersForGroupBlankId() {

		deviceMakeAndModelServiceImpl.resetDeviceIdImplementation(true, CommonMethods.getDeviceTilee(),
				CommonMethods.getDeviceSummary(), "");
	}

	@Test
	public void getMembersForGroup() {

		deviceMakeAndModelServiceImpl.resetDeviceIdImplementation(true, CommonMethods.getDeviceTilee(),
				CommonMethods.getDeviceSummary(), "122");
	}

	@Test
	public void getMembersForGroupDeviceSummaryAffordable() {
		List<DeviceSummary> dsList = CommonMethods.getDeviceSummary();
		DeviceSummary ds = dsList.get(0);
		ds.setIsAffordable(false);
		dsList.add(ds);
		deviceMakeAndModelServiceImpl.resetDeviceIdImplementation(true, CommonMethods.getDeviceTilee(), dsList, "122");
	}

	@Test
	public void isJourneySpecificLeadPlanTest() {
		Map<String, CommercialBundle> commerBundleIdMap = new HashMap<>();
		commerBundleIdMap.put("1231", CommonMethods.getCommercialBundle());

		Assert.assertNotNull(
				deviceMakeAndModelServiceImpl.isJourneySpecificLeadPlan(commerBundleIdMap, "108242", "Acquisition"));
		Assert.assertFalse(
				deviceMakeAndModelServiceImpl.isJourneySpecificLeadPlan(commerBundleIdMap, "108242", "Acquisition"));
	}

	@Test
	public void isJourneySpecificLeadPlanTestBlankMap() {
		Map<String, CommercialBundle> commerBundleIdMap = new HashMap<>();
		Assert.assertNotNull(
				deviceMakeAndModelServiceImpl.isJourneySpecificLeadPlan(commerBundleIdMap, "108242", "Acquisition"));
	}

	@Test
	public void isJourneySpecificLeadPlanTestBlankId() {
		Map<String, CommercialBundle> commerBundleIdMap = new HashMap<>();
		Assert.assertNotNull(
				deviceMakeAndModelServiceImpl.isJourneySpecificLeadPlan(commerBundleIdMap, "", "Acquisition"));
		Assert.assertFalse(
				deviceMakeAndModelServiceImpl.isJourneySpecificLeadPlan(commerBundleIdMap, "", "Acquisition"));
	}

	@Test
	public void isJourneySpecificLeadPlanTestNullMap() {
		Assert.assertNotNull(deviceMakeAndModelServiceImpl.isJourneySpecificLeadPlan(null, "", "Acquisition"));
	}

	@Test
	public void getMemberByRules() {
		Map<String, CommercialProduct> commerProdMemMapPAYG = new HashMap<>();
		commerProdMemMapPAYG.put("123", CommonMethods.getCommercialProduct());
		deviceMakeAndModelServiceImpl.getMemberByRules("DEVICE_PAYG",
				CommonMethods.getDeviceTile("apple", "iphone 7", "groupType"), CommonMethods.getDeviceTilee(), "PAYG",
				CommonMethods.getMemberListPojo(), commerProdMemMapPAYG,
				new HashSet<>(CommonMethods.getBundleAndHardwareTuple()));
	}

	@Test
	public void testGetBundleDetailsFromComplansListingAPI() {
		commonUtility.getBundleDetailsFromComplansListingAPI("093353", "-sort");
	}

	@Test
	public void testSortedPriceForBundleAndHardware() {
		Assert.assertNotNull(DeviceUtils.sortedPriceForBundleAndHardware(CommonMethods.getPriceForBundleAndHardware()));
	}

	@Test
	public void testSortedPriceForBundleAndHardwareForPayG() {
		Assert.assertNotNull(
				DeviceUtils.sortedPriceForBundleAndHardwareForPayG(CommonMethods.getPriceForBundleAndHardware()));
	}

	// getIlsBundleHardwarePriceMap
	@Test
	public void testGetIlsBundleHardwarePriceMap() {
		Set<String> setOffer = new HashSet<>();
		setOffer.add("093353");
		Map<String, List<BundleAndHardwareTuple>> bundleHardwareTroupleMap = new HashMap<>();
		bundleHardwareTroupleMap.put("093353", CommonMethods.getBundleAndHardwareTuple());

		DeviceUtils.getIlsBundleHardwarePriceMap(setOffer, bundleHardwareTroupleMap, "093353", "110154",
				Arrays.asList("promoteAs"));
	}

	@Test
	public void getPriceDetailsForCompatibaleBundle() {
		try {
			commonUtility.getPriceDetailsForCompatibaleBundle(null, null);
		} catch (Exception e) {
			Assert.assertEquals(ExceptionMessages.COUPLEBUNDLELIST_API_EXCEPTION, e.getMessage());
		}
	}

	@Test
	public void getBundleDetailsFromComplansListingAPIException() {
		try {
			commonUtility.getBundleDetailsFromComplansListingAPI("093353", "abc");
		} catch (Exception e) {
			Assert.assertEquals(ExceptionMessages.BUNDLECOMPATIBLELIST_API_EXCEPTION, e.getMessage());
		}
	}

	@Test
	public void getDecimalFormat() {
		CommonUtility.getDecimalFormat();
	}

	@Test
	public void getJSONFromString() {
		try {
			CommonUtility.getJSONFromString("dasdasd3123");
		} catch (Exception e) {
			Assert.assertEquals(ExceptionMessages.ERROR_STRING_TO_JSONOBJECT, e.getMessage());
		}
	}

	@Test
	public void getAccessoryPriceDetails() {
		try {
			commonUtility.getAccessoryPriceDetails(null);
		} catch (Exception e) {
			Assert.assertEquals(ExceptionMessages.PRICING_API_EXCEPTION, e.getMessage());
		}
	}

	@Test
	public void getPriceDetailsUsingBundleHarwareTrouple() {
		try {
			commonUtility.getPriceDetailsUsingBundleHarwareTrouple(null, "121", "Acquisition", "DEVICE_PAYM");
		} catch (Exception e) {
			Assert.assertEquals(ExceptionMessages.PRICING_API_EXCEPTION, e.getMessage());
		}
	}

	@Test
	public void getSubscriptionBundleId() {
		try {
			commonUtility.getSubscriptionBundleId(null, "abc");
		} catch (Exception e) {
		}
	}

	@Test
	public void isProductJourneySpecific() {
		CommercialProduct cp = CommonMethods.getCommercialProduct();
		Assert.assertNotNull(CommonUtility.isProductJourneySpecific(cp, JOURNEYTYPE_UPGRADE));
		Assert.assertTrue(CommonUtility.isProductJourneySpecific(cp, JOURNEYTYPE_UPGRADE));
	}

	@Test
	public void isProductJourneySpecificJTBlank() {
		CommercialProduct cp = CommonMethods.getCommercialProduct();
		Assert.assertNotNull(CommonUtility.isProductJourneySpecific(cp, ""));
	}

	@Test
	public void getPromotionsForBundleAndHardWarePromotions() {
		List<BundleAndHardwareTuple> bundleHardwareTupleList = new ArrayList<>();
		commonUtility.getPromotionsForBundleAndHardWarePromotions(bundleHardwareTupleList, "Acquisition");
	}

	@Test
	public void getmonthlyPriceFormPrice() {
		PriceForBundleAndHardware price = CommonMethods.getUtilityPriceForBundleAndHardware();
		BundlePrice bp = price.getBundlePrice();
		Price mp = new Price();
		mp.setGross("20");
		bp.setMonthlyDiscountPrice(mp);
		price.setBundlePrice(bp);
		Assert.assertNotNull(DeviceUtils.getmonthlyPriceFormPrice(price));
	}

	@Test
	public void getmonthlyPriceFormPriceNull() {
		try {
			DeviceUtils.getmonthlyPriceFormPrice(null);
		} catch (Exception e) {
			Assert.assertEquals( null,e.getMessage());
		}
	}

	@Test
	public void getmonthlyPriceFormPriceBundlePriceNull() {
		try {
			PriceForBundleAndHardware price = CommonMethods.getUtilityPriceForBundleAndHardware();
			price.setBundlePrice(null);
			DeviceUtils.getmonthlyPriceFormPrice(price);
		} catch (Exception e) {
			Assert.assertEquals( null,e.getMessage());
		}
	}

	@Test
	public void getmonthlyPriceFormPriceMPNull() {
		try {
			PriceForBundleAndHardware price = CommonMethods.getUtilityPriceForBundleAndHardware();
			BundlePrice bp = price.getBundlePrice();
			bp.setMonthlyDiscountPrice(null);
			price.setBundlePrice(bp);
			DeviceUtils.getmonthlyPriceFormPrice(price);
		} catch (Exception e) {
			Assert.assertEquals( null,e.getMessage());
		}
	}

	@Test
	public void getmonthlyPriceFormPriceMPGrossNull() {
		try {
			PriceForBundleAndHardware price = CommonMethods.getUtilityPriceForBundleAndHardware();
			BundlePrice bp = price.getBundlePrice();
			Price mp = bp.getMonthlyDiscountPrice();
			mp.setGross(null);
			bp.setMonthlyDiscountPrice(mp);
			price.setBundlePrice(bp);
			DeviceUtils.getmonthlyPriceFormPrice(price);
		} catch (Exception e) {
			Assert.assertEquals( null,e.getMessage());
		}
	}

	@Test
	public void leastMonthlyPrice() {
		try {
			PriceForBundleAndHardware price = CommonMethods.getUtilityPriceForBundleAndHardware();
			List<PriceForBundleAndHardware> priceList = new ArrayList<>();
			priceList.add(price);
			DeviceUtils.leastMonthlyPrice(priceList);
		}

		catch (Exception e) {
			Assert.assertEquals( null,e.getMessage());
		}
	}

	@Test
	public void sortedPriceForBundleAndHardware() {
		PriceForBundleAndHardware price = CommonMethods.getUtilityPriceForBundleAndHardware();
		List<PriceForBundleAndHardware> priceList = new ArrayList<>();
		priceList.add(price);
		Assert.assertNotNull(DeviceUtils.sortedPriceForBundleAndHardware(priceList));
	}

	@Test
	public void sortedPriceForBundleAndHardwareForPayG() {
		PriceForBundleAndHardware price = CommonMethods.getUtilityPriceForBundleAndHardware();
		List<PriceForBundleAndHardware> priceList = new ArrayList<>();
		priceList.add(price);
		Assert.assertNotNull(DeviceUtils.sortedPriceForBundleAndHardwareForPayG(priceList));
	}

	@Test
	public void getmonthlyPriceFormPriceForPayGNulll() {
		try {
			DeviceUtils.getmonthlyPriceFormPriceForPayG(null);
		} catch (Exception e) {
			Assert.assertEquals( null,e.getMessage());
		}
	}

	@Test
	public void getmonthlyPriceFormPriceForPayGNull() {
		try {
			PriceForBundleAndHardware price = CommonMethods.getUtilityPriceForBundleAndHardware();
			price.setHardwarePrice(null);
			DeviceUtils.getmonthlyPriceFormPriceForPayG(price);
		} catch (Exception e) {
			Assert.assertEquals( null,e.getMessage());
		}
	}

	@Test
	public void getmonthlyPriceFormPriceForPayGMpNull() {
		try {
			PriceForBundleAndHardware price = CommonMethods.getUtilityPriceForBundleAndHardware();
			HardwarePrice bp = price.getHardwarePrice();
			bp.setOneOffDiscountPrice(null);
			price.setHardwarePrice(bp);
			DeviceUtils.getmonthlyPriceFormPrice(price);
		} catch (Exception e) {
			Assert.assertEquals( null,e.getMessage());
		}
	}

	@Test
	public void getmonthlyPriceFormPriceForPayGPriceMPGrossNull() {
		try {
			PriceForBundleAndHardware price = CommonMethods.getUtilityPriceForBundleAndHardware();
			HardwarePrice bp = price.getHardwarePrice();
			Price mp = bp.getOneOffDiscountPrice();
			mp.setGross(null);
			bp.setOneOffDiscountPrice(mp);
			price.setHardwarePrice(bp);
			DeviceUtils.getmonthlyPriceFormPrice(price);
		} catch (Exception e) {
			Assert.assertEquals( null,e.getMessage());
		}
	}
	@Test
	public void getMemberForCaceDevice() {
		DeviceUtils.getMemberForCaceDevice(CommonMethods.getListOfProducts(), CommonMethods.getleadMemberMap(),
				CommonMethods.getGroupp(), CommonMethods.getMemberListPojo(), "1231", "abc");
	}

	@Test
	public void getBundleharwareTrupleForPaymCacheDevice() {
		Set<String> bundleIds = new HashSet<>();
		Set<BundleAndHardwareTuple> bundleAndHardwareTuple = new HashSet<>();
		bundleAndHardwareTuple.add(CommonMethods.getBundleAndHardwareTuplee());
		bundleIds.add("1231");
		DeviceUtils.getBundleharwareTrupleForPaymCacheDevice(bundleIds, CommonMethods.getBundleAndHardwareTuple(),
				CommonMethods.getBundleAndHardwareTuple(), bundleAndHardwareTuple, CommonMethods.getleadMemberMap(),
				CommonMethods.getleadMemberMapp(), CommonMethods.getCommercialProductsListOfAccessories());
	}

	@Test
	public void sortListForProductModel() {
		List<ProductModel> productModel = 
				deviceUtils.sortListForProductModel(CommonMethods.getProductModel(), CommonMethods.getColourHes());
		Assert.assertNotNull(productModel);
		Assert.assertEquals( "110154",productModel.get(0).getLeadPlanId());
		Assert.assertEquals( "093353",productModel.get(0).getProductId());
	}
	
	@Test
	public void getBundleDetailsFromComplansListingAPI() {
		String url = "http://BUNDLES-V1/bundles/catalogue/bundle/queries/byDeviceId/093353//?sort=abc";
		given(restTemplate.getForObject(url, BundleDetails.class))
				.willReturn(CommonMethods.getCompatibleBundleListJson());
		BundleDetails bundle = commonUtility.getBundleDetailsFromComplansListingAPI("093353", "abc");
		Assert.assertNotNull(bundle);
		Assert.assertEquals( "24mth Band B Unlmin 60GB Red Entertainment Plan",bundle.getPlanList().get(0).getBundleName());
		Assert.assertEquals( "110151",bundle.getPlanList().get(0).getSkuId());
	}
	@Test
	public void testconvertCoherenceDeviceToDeviceTile_PAYG() {
		DeviceSummary deviceSummary = DeviceDetailsMakeAndModelVaiantDaoUtils.convertCoherenceDeviceToDeviceTilePAYG(
				Long.valueOf(1), CommonMethods.getCommercialProduct(),
				CommonMethods.getPriceForBundleAndHardware().get(0),
				CommonMethods.getListOfBundleAndHardwarePromotions().get(0), cdnDomain);
		Assert.assertNotNull(deviceSummary);
		Assert.assertEquals("093353",deviceSummary.getDeviceId());
		Assert.assertEquals("1",deviceSummary.getPriority());
	}

	@Test
	public void testconvertCoherenceDeviceToDeviceTile() {
		DeviceSummary deviceSummary = DeviceDetailsMakeAndModelVaiantDaoUtils.convertCoherenceDeviceToDeviceTile(null,
				CommonMethods.getCommercialProduct(), CommonMethods.getCommercialBundle(),
				CommonMethods.getUtilityPriceForBundleAndHardware(),
				CommonMethods.getListOfBundleAndHardwarePromotions(), "DEVICE_PAYG", true, null, cdnDomain);
		Assert.assertNotNull(deviceSummary);
		Assert.assertEquals("093353",deviceSummary.getDeviceId());
	}
	@Test
	public void testappendPrefixString() {
		String deviceId = CommonUtility.appendPrefixString("093353");
		Assert.assertNotNull(deviceId);
		Assert.assertEquals( "sku93353",deviceId);
	}
}
