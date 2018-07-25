package com.vf.uk.dal.device.service.test;

import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.EurekaClient;
import com.vf.uk.dal.common.configuration.ConfigHelper;
import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.common.registry.client.RegistryClient;
import com.vf.uk.dal.common.registry.client.Utility;
import com.vf.uk.dal.device.aspect.CatalogServiceAspect;
import com.vf.uk.dal.device.beans.test.DeviceTestBeans;
import com.vf.uk.dal.device.common.test.CommonMethods;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.dao.DeviceTileCacheDAO;
import com.vf.uk.dal.device.datamodel.bundle.CommercialBundle;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.datamodel.product.ProductGroup;
import com.vf.uk.dal.device.datamodel.product.ProductGroups;
import com.vf.uk.dal.device.datamodel.product.PromoteAs;
import com.vf.uk.dal.device.datamodel.productgroups.Group;
import com.vf.uk.dal.device.datamodel.productgroups.Member;
import com.vf.uk.dal.device.entity.AccessoryTileGroup;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.BundlePrice;
import com.vf.uk.dal.device.entity.DeviceDetails;
import com.vf.uk.dal.device.entity.DeviceSummary;
import com.vf.uk.dal.device.entity.FacetedDevice;
import com.vf.uk.dal.device.entity.HardwarePrice;
import com.vf.uk.dal.device.entity.Insurance;
import com.vf.uk.dal.device.entity.Insurances;
import com.vf.uk.dal.device.entity.MediaLink;
import com.vf.uk.dal.device.entity.MerchandisingPromotion;
import com.vf.uk.dal.device.entity.Price;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.entity.RequestForBundleAndHardware;
import com.vf.uk.dal.device.entity.SourcePackageSummary;
import com.vf.uk.dal.device.helper.DeviceConditionallHelper;
import com.vf.uk.dal.device.helper.DeviceServiceCommonUtility;
import com.vf.uk.dal.device.svc.DeviceRecommendationService;
import com.vf.uk.dal.device.svc.DeviceService;
import com.vf.uk.dal.device.svc.impl.AccessoryInsuranceServiceImpl;
import com.vf.uk.dal.device.svc.impl.DeviceDetailsServiceImpl;
import com.vf.uk.dal.device.svc.impl.DeviceMakeAndModelServiceImpl;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.DeviceDetailsMakeAndModelVaiantDaoUtils;
import com.vf.uk.dal.device.utils.ResponseMappingHelper;
import com.vf.uk.dal.device.validator.Validator;
import com.vf.uk.dal.utility.entity.BundleAndHardwarePromotions;
import com.vf.uk.dal.utility.entity.BundleAndHardwareRequest;
import com.vf.uk.dal.utility.entity.BundleModelAndPrice;
import com.vf.uk.dal.utility.entity.PriceForProduct;
import org.junit.Assert;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeviceTestBeans.class)
public class DeviceServiceImplTest {
	@Autowired
	DeviceMakeAndModelServiceImpl deviceMakeAndModelServiceImpl;

	@Autowired
	DeviceServiceCommonUtility utility;

	@MockBean
	DeviceDao deviceDAOMock;

	@MockBean
	EurekaClient eureka;

	@MockBean
	ResponseMappingHelper response;

	@MockBean
	RegistryClient registry;

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

	@SuppressWarnings("unchecked")
	@Before
	public void setupMockBehaviour() throws Exception {
		aspect.beforeAdvice(null);
		given(response.getCommercialProduct(Matchers.anyObject()))
				.willReturn(CommonMethods.getCommercialProductByDeviceId_093353_PAYG());
		given(response.getListOfGroupFromJson(Matchers.anyObject()))
				.willReturn(CommonMethods.getListOfProductGroupFromProductGroupRepository());
		given(response.getCommercialProductFromJson(Matchers.anyObject()))
				.willReturn(CommonMethods.getCommercialProductsListOfMakeAndModel());
		given(response.getCommercialBundle(Matchers.anyObject())).willReturn(CommonMethods.getCommercialBundle());
		given(response.getListOfCommercialBundleFromJson(Matchers.anyObject()))
				.willReturn(Arrays.asList(CommonMethods.getCommercialBundle()));
		given(deviceDAOMock.getPriceForBundleAndHardwareListFromTupleListAsync(Matchers.anyList(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString()))
						.willReturn(CommonMethods.getPriceForBundleAndHardwareListFromTupleListAsync());
		given(deviceDAOMock.getBundleAndHardwarePromotionsListFromBundleListAsync(Matchers.anyList(),
				Matchers.anyString(), Matchers.anyString()))
						.willReturn(CommonMethods.getBundleAndHardwarePromotionsListFromBundleListAsync());
		given(response.getCommercialProduct(Matchers.anyObject()))
				.willReturn(CommonMethods.getCommercialProductByDeviceId_093353_PAYG());
		given(response.getListOfGroupFromJson(Matchers.anyObject())).willReturn(CommonMethods.getGroup());
		given(response.getCommercialProductFromJson(Matchers.anyObject()))
				.willReturn(CommonMethods.getCommercialProductsListOfAccessories());
		given(response.getCommercialBundle(Matchers.anyObject())).willReturn(CommonMethods.getCommercialBundle());
		String jsonString = new String(Utility.readFile("\\rest-mock\\COMMON-V1.json"));
		given(registry.getRestTemplate()).willReturn(restTemplate);
		given(restTemplate.postForObject("http://PRICE-V1/price/product",
				CommonMethods.bundleDeviceAndProductsList_For_GetAccessoriesOfDevice(), PriceForProduct.class))
						.willReturn(CommonMethods.getPriceForProduct_For_GetAccessoriesForDevice());
		given(response.getMerchandisingPromotion(Matchers.anyObject())).willReturn(CommonMethods.getMemPro());
	}

	@Test
	public void getConditionalForDeviceList() {
		String url = "http://CUSTOMER-V1/customer/subscription/msisdn:7741655542/sourcePackageSummary";
		given(restTemplate.getForObject(url, SourcePackageSummary.class))
				.willReturn(CommonMethods.getSourcePackageSummary());
		given(deviceRecomServiceMock.getRecommendedDeviceList(Matchers.anyString(), Matchers.anyString(),
				Matchers.anyObject()))
						.willReturn(CommonMethods.getFacetedDeviceList("HANDSET", "Apple", "iPhone-7", "DEVICE_PAYM",
								"", 1, 9, "wrtety"));
		Assert.assertNotNull(deviceService.getConditionalForDeviceList("7741655542",
				CommonMethods.getFacetedDeviceList("HANDSET", "Apple", "iPhone-7", "DEVICE_PAYM", "", 1, 9, "wrtety")));
		
	}

	// accessory test cases start
	@Test
	public void getAccessoriesOfDevice() {
		List<AccessoryTileGroup> list=accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc");
		Assert.assertNotNull(list);
	}

	// deviceRecomServiceMock
	@Test
	public void getdeviceRecomServiceMock() {
		try {
			Assert.assertNull(deviceRecomServiceMock.getRecommendedDeviceList("9237", "093353", null));
		} catch (Exception e) {
			
		}
	}

	@Test
	public void getAccessoriesOfDeviceCPNull() {
		try {
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(null);
			Assert.assertNull(accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc"));
		} catch (Exception e) {

		}
	}

	@Test
	public void getAccessoriesOfDeviceCPIdNull() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setId(null);
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			Assert.assertNull(accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc"));
		} catch (Exception e) {

		}
	}

	@Test
	public void getAccessoriesOfDeviceCPProductNull() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setProductClass("abc");
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			Assert.assertNull(accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc"));
		} catch (Exception e) {

		}
	}

	@Test
	public void getAccessoriesOfDeviceCPProductDevice() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setIsDeviceProduct(false);
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			Assert.assertNotNull(accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc"));
		} catch (Exception e) {

		}
	}

	@Test
	public void getAccessoriesOfDeviceCPProductGroupNull() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setProductGroups(null);
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			Assert.assertNull(accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc"));
		} catch (Exception e) {

		}
	}

	@Test
	public void getAccessoriesOfDeviceCPProductGroupNullNUll() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			ProductGroups pg = cp.getProductGroups();
			pg.setProductGroup(null);
			cp.setProductGroups(pg);
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			Assert.assertNull(accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc"));
		} catch (Exception e) {

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
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			Assert.assertNull(accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc"));
		} catch (Exception e) {

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
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			Assert.assertNull(accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc"));
		} catch (Exception e) {

		}
	}

	@Test
	public void getAccessoriesOfDeviceCPProductGroupInvalid() {
		try {
			given(response.getListOfGroupFromJson(Matchers.anyObject())).willReturn(CommonMethods.getGroup_Two());
			Assert.assertNull(accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc"));
		} catch (Exception e) {

		}
	}

	@Test
	public void getgetFinalAccessoryListInvalid() {
		try {
			List<String> finalAccessoryList = new ArrayList<>();
			Map<String, List<String>> mapForGroupName = new HashMap<>();
			AccessoryInsuranceServiceImpl.getFinalAccessoryList(finalAccessoryList, mapForGroupName, null);
		} catch (Exception e) {

		}
	}

	@Test
	public void getgetFinalAccessoryListInvalid1() {
		try {
			List<String> finalAccessoryList = new ArrayList<>();
			Map<String, List<String>> mapForGroupName = new HashMap<>();
			Group productGroup = new Group();
			AccessoryInsuranceServiceImpl.getFinalAccessoryList(finalAccessoryList, mapForGroupName, productGroup);
		} catch (Exception e) {

		}
	}

	@Test
	public void getgetListOfFilteredAccessories1() {
		try {
			given(response.getCommercialProductFromJson(Matchers.anyObject()))
					.willReturn(CommonMethods.getCommercialProductsListOfAccessoriesWithEndDate());
			List<String> finalAccessoryList = new ArrayList<>();
			Assert.assertNotNull(accessoryInsuranceService.getListOfFilteredAccessories("Acquisition", finalAccessoryList));
		} catch (Exception e) {

		}
	}

	@Test
	public void TestsetMapForCommercialData() {
		try {
			PriceForProduct pc = new PriceForProduct();
			pc.setPriceForAccessoryes(null);
			Assert.assertNull(AccessoryInsuranceServiceImpl.setMapForCommercialData(null, null, pc, null));
		} catch (Exception e) {

		}
	}

	// insurance test cases start

	@Test
	public void getInsuranceByDeviceId() {
		try {
			Assert.assertNotNull(accessoryInsuranceService.getInsuranceByDeviceId("093353", "Acquisition"));
		} catch (Exception e) {
		}
	}

	@Test
	public void TEstgetInsurance() {
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
	public void TEstgetInsurancePGNull() {
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
	public void TestgetInsuranceProductList() {
		try {
			Group gp = new Group();
			gp.setGroupType(Constants.STRING_COMPATIBLE_INSURANCE);
			List<Member> members = new ArrayList<>();
			Member member = new Member();
			member.setId("123");
			member.setPriority((long) 12);
			members.add(member);
			gp.setMembers(members);
			List<Member> listOfInsuranceMembers = new ArrayList<>();
			Assert.assertNotNull(AccessoryInsuranceServiceImpl.getInsuranceProductList(listOfInsuranceMembers, gp));
		} catch (Exception e) {

		}
	}

	@Test
	public void TestgetInsuranceProductList5() {
			Group gp = new Group();
			gp.setGroupType(Constants.STRING_COMPATIBLE_INSURANCE);
			List<Member> members = new ArrayList<>();
			Member member = new Member();
			member.setId("123");
			member.setPriority((long) 12);
			members.add(member);
			gp.setMembers(members);
			List<Member> listOfInsuranceMembers = new ArrayList<>();
			listOfInsuranceMembers.add(member);
			Assert.assertNotNull(AccessoryInsuranceServiceImpl.getInsuranceProductList(listOfInsuranceMembers, gp));
		
	}

	@Test
	public void TestgetInsuranceProductList6() {
			Group gp = new Group();
			gp.setGroupType(Constants.STRING_COMPATIBLE_INSURANCE);
			List<Member> members = new ArrayList<>();
			Member member = new Member();
			member.setId(null);
			member.setPriority((long) 12);
			members.add(member);
			gp.setMembers(members);
			List<Member> listOfInsuranceMembers = new ArrayList<>();
			listOfInsuranceMembers.add(member);
			Assert.assertNotNull(AccessoryInsuranceServiceImpl.getInsuranceProductList(listOfInsuranceMembers, gp));
		
	}
	@Test
	public void testassembleMechandisingPromotionsPackageGeneric(){
		Assert.assertNotNull(DeviceDetailsMakeAndModelVaiantDaoUtils.assembleMechandisingPromotionsPackageGeneric(CommonMethods.getListOfBundleAndHardwarePromotions().get(0),
				CommonMethods.getPriceForBundleAndHardware().get(0)));
	}
	
	
	@Test
	public void TestgetInsuranceProductList7() {
		try {
			Group gp = new Group();
			gp.setGroupType(Constants.STRING_COMPATIBLE_INSURANCE);
			List<Member> members = new ArrayList<>();
			Member member = new Member();
			member.setId(null);
			member.setPriority((long) 12);
			members.add(member);
			gp.setMembers(members);
			List<Member> listOfInsuranceMembers = new ArrayList<>();
			Assert.assertNotNull(AccessoryInsuranceServiceImpl.getInsuranceProductList(listOfInsuranceMembers, gp));
		} catch (Exception e) {

		}
	}

	@Test
	public void TestgetInsuranceProductList4() {
		try {
			Group gp = new Group();
			gp.setGroupType(Constants.STRING_COMPATIBLE_INSURANCE);
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
	public void TestgetInsuranceProductList1() {
		try {
			Group gp = new Group();
			gp.setGroupType("acd");
			List<Member> listOfInsuranceMembers = new ArrayList<>();
			Assert.assertNotNull(AccessoryInsuranceServiceImpl.getInsuranceProductList(listOfInsuranceMembers, gp));
		} catch (Exception e) {

		}
	}

	@Test
	public void TestvalidateInsuranceNullable() {
		try {
			AccessoryInsuranceServiceImpl.validateInsuranceNullable(null, null);
		} catch (Exception e) {

		}
	}

	@Test
	public void TestvalidateInsuranceNullable1() {
		try {
			Insurances insurance = new Insurances();
			List<Insurance> insuranceList = new ArrayList<>();
			insurance.setInsuranceList(insuranceList);
			AccessoryInsuranceServiceImpl.validateInsuranceNullable(null, insurance);
		} catch (Exception e) {

		}
	}

	@Test
	public void TestgetInsuranceProductList2() {
		try {
			Group gp = new Group();
			gp.setGroupType(null);
			List<Member> listOfInsuranceMembers = new ArrayList<>();
			Assert.assertNotNull(AccessoryInsuranceServiceImpl.getInsuranceProductList(listOfInsuranceMembers, gp));
		} catch (Exception e) {

		}
	}

	@Test
	public void TestgetInsuranceProductList3() {
		try {
			Group gp = new Group();
			gp.setGroupType(null);
			List<Member> listOfInsuranceMembers = new ArrayList<>();
			Assert.assertNotNull(AccessoryInsuranceServiceImpl.getInsuranceProductList(listOfInsuranceMembers, null));
		} catch (Exception e) {

		}
	}

	@Test
	public void getInsuranceByDeviceIdNull() {
		try {
			Assert.assertNotNull(accessoryInsuranceService.getInsuranceByDeviceId(null, "Acquisition"));
		} catch (Exception e) {
		}
	}

	@Test
	public void getInsuranceByDeviceIdInvalid() {
		try {
			Assert.assertNotNull(accessoryInsuranceService.getInsuranceByDeviceId("098578", "Acquisition"));
		} catch (Exception e) {
		}
	}

	@Test
	public void getInsuranceByDeviceIdInvalid1() {
		try {
			Assert.assertNotNull(accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition"));
		} catch (Exception e) {
		}
	}

	@Test
	public void getInsuranceByDeviceIdInvalid2() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setIsDeviceProduct(false);
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			Assert.assertNotNull(accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition"));
		} catch (Exception e) {
		}
	}

	@Test
	public void getInsuranceByDeviceIdInvalid3() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setProductClass("abc");
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			Assert.assertNotNull(accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition"));
		} catch (Exception e) {
		}
	}

	@Test
	public void getInsuranceCPProductGroupNull() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setProductGroups(null);
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			Assert.assertNotNull(accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition"));
		} catch (Exception e) {

		}
	}

	@Test
	public void getInsuranceCPProductGroupNullNUll() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			ProductGroups pg = cp.getProductGroups();
			pg.setProductGroup(null);
			cp.setProductGroups(pg);
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			Assert.assertNull(accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition"));
		} catch (Exception e) {

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
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			Assert.assertNull(accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition"));
		} catch (Exception e) {

		}
	}

	@Test
	public void getInsuranceCPNull() {
		try {
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(null);
			Assert.assertNotNull(accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition"));
		} catch (Exception e) {

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
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			Assert.assertNotNull(accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition"));
		} catch (Exception e) {

		}

	}

	@Test
	public void getListOfDeviceTile() {

		given(response.getCommercialProduct(Matchers.anyObject()))
				.willReturn(CommonMethods.getCommercialProductByDeviceId_093353_PAYG());
		given(response.getListOfGroupFromJson(Matchers.anyObject()))
				.willReturn(CommonMethods.getListOfProductGroupFromProductGroupRepository());
		given(response.getCommercialProductFromJson(Matchers.anyObject()))
				.willReturn(CommonMethods.getCommercialProductsListOfMakeAndModel());
		given(response.getCommercialBundle(Matchers.anyObject())).willReturn(CommonMethods.getCommercialBundle());
		given(response.getListOfCommercialBundleFromJson(Matchers.anyObject()))
				.willReturn(Arrays.asList(CommonMethods.getCommercialBundle()));
		given(deviceDAOMock.getPriceForBundleAndHardwareListFromTupleListAsync(Matchers.anyList(), Matchers.anyString(),
				Matchers.anyString(), Matchers.anyString()))
						.willReturn(CommonMethods.getPriceForBundleAndHardwareListFromTupleListAsync());
		given(deviceDAOMock.getBundleAndHardwarePromotionsListFromBundleListAsync(Matchers.anyList(),
				Matchers.anyString(), Matchers.anyString()))
						.willReturn(CommonMethods.getBundleAndHardwarePromotionsListFromBundleListAsync());

		PriceForBundleAndHardware[] obj = null;
		try {
			ObjectMapper mapper = new ObjectMapper();

			String jsonString = new String(Utility.readFile("\\rest-mock\\PRICE-V1.json"));
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			obj = mapper.readValue(jsonString, PriceForBundleAndHardware[].class);
		} catch (IOException e) {

		}
		given(registry.getRestTemplate()).willReturn(restTemplate);
		RequestForBundleAndHardware requestForBundleAndHardware = new RequestForBundleAndHardware();
		List<BundleAndHardwareTuple> bundleList = new ArrayList<>();
		BundleAndHardwareTuple bundle = new BundleAndHardwareTuple();
		bundle.setBundleId("110075");
		bundle.setHardwareId("093353");
		bundleList.add(bundle);
		requestForBundleAndHardware.setBundleAndHardwareList(bundleList);

		BundleAndHardwarePromotions[] obj1 = null;
		try {
			String jsonString1 = new String(Utility.readFile("\\BundleandhardwarePromotuions.json"));
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
			Assert.assertNotNull(deviceMakeAndModelServiceImpl.getListOfDeviceTile("apple", "iPhone-7", "DEVICE_PAYM", "093353", 40.0,
					"Upgrade", "W_HH_SIMONLY", "110154"));
		} catch (Exception e) {

		}

	}

	@Test
	public void getDeviceTileByMakeAndModelForPAYG() {
		Assert.assertNotNull(deviceMakeAndModelServiceImpl.getDeviceTileByMakeAndModelForPAYG(
				CommonMethods.getCommercialProductsListOfMakeAndModel(), CommonMethods.getGroupForVariant(), "apple",
				"iPhone-7", "DEVICE_PAYM"));
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

			String jsonString = new String(Utility.readFile("\\rest-mock\\PRICE-V1.json"));
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
			obj = mapper.readValue(jsonString, PriceForBundleAndHardware[].class);
		} catch (IOException e) {

		}
		requestForBundleAndHardware.setBundleAndHardwareList(bundleList);
		given(restTemplate.postForObject("http://PRICE-V1/price/calculateForBundleAndHardware",
				requestForBundleAndHardware, PriceForBundleAndHardware[].class)).willReturn(obj);

		Assert.assertNull(deviceMakeAndModelServiceImpl.getLeadBundleBasedOnAllPlans_Implementation((double) (40),
				CommonMethods.getCommercialProduct(), CommonMethods.getPriceForBundleAndHardware(), "Upgrade"));
	}

	@Test
	public void TestgetDeviceDetails_Implementation() {
		try {
			Assert.assertNull(deviceDetailsService.getDeviceDetails_Implementation("093353", Constants.JOURNEY_TYPE_SECONDLINE, "abc"));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetails_ImplementationJTBlank() {
		try {
			Assert.assertNotNull(deviceDetailsService.getDeviceDetails_Implementation("093353", "", "abc"));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetails_ImplementationInvalidCP() {
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
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(null);
			Assert.assertNotNull(deviceDetailsService.getDeviceDetails_Implementation("093353", Constants.JOURNEY_TYPE_SECONDLINE, "abc"));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetails_ImplementationInvalidCPIdNUll() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setId(null);
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			Assert.assertNotNull(deviceDetailsService.getDeviceDetails_Implementation("093353", Constants.JOURNEY_TYPE_SECONDLINE, "abc"));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetails_ImplementationInvalidCPDPInvalid() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setIsDeviceProduct(false);
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			Assert.assertNull(deviceDetailsService.getDeviceDetails_Implementation("093353", Constants.JOURNEY_TYPE_SECONDLINE, "abc"));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetails_ImplementationInvalidCPPCInvalid() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setProductClass("accessories");
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			Assert.assertNull(deviceDetailsService.getDeviceDetails_Implementation("093353", Constants.JOURNEY_TYPE_SECONDLINE, "abc"));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetails_ImplementationInvalidCPPCInvalid1() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setProductClass(Constants.STRING_DATA_DEVICE);
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			Assert.assertNull(deviceDetailsService.getDeviceDetails_Implementation("093353", Constants.JOURNEY_TYPE_SECONDLINE, "abc"));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetailsResponse() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			Assert.assertNotNull(deviceDetailsService.getDeviceDetailsResponse("093353", "abc", "Acquisition", cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetailsResponsePLNull() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setProductLines(null);
			Assert.assertNotNull(deviceDetailsService.getDeviceDetailsResponse("093353", "abc", "Acquisition", cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetailsResponsePLEmpty() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			List<String> pl = new ArrayList<>();
			cp.setProductLines(pl);
			Assert.assertNotNull(deviceDetailsService.getDeviceDetailsResponse("093353", "abc", Constants.JOURNEY_TYPE_SECONDLINE, cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetailsResponseJourney() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			Assert.assertNotNull(deviceDetailsService.getDeviceDetailsResponse("093353", "abc", Constants.JOURNEY_TYPE_SECONDLINE, cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetailsResponseJourneyUG() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			Assert.assertNotNull(deviceDetailsService.getDeviceDetailsResponse("093353", "abc", Constants.JOURNEY_TYPE_UPGRADE, cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetailsResponseJourneyNull() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			Assert.assertNotNull(deviceDetailsService.getDeviceDetailsResponse("093353", null, Constants.JOURNEY_TYPE_SECONDLINE, cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetailsResponseJourneyNull1() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			Assert.assertNotNull(deviceDetailsService.getDeviceDetailsResponse("093353", "abc", null, cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetailsResponseJourneyNull11() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			Assert.assertNotNull(deviceDetailsService.getDeviceDetailsResponse("093353", null, null, cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetailsResponseJourneyUGNull() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			Assert.assertNull(deviceDetailsService.getDeviceDetailsResponse("093353", null, Constants.JOURNEY_TYPE_UPGRADE, cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetJourneyAndOfferCodeValidationForPAYG() {
		try {
			Validator.getJourneyAndOfferCodeValidationForPAYG("", Constants.JOURNEY_TYPE_ACQUISITION);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetJourneyAndOfferCodeValidationForPAYG1() {
		try {
			Validator.getJourneyAndOfferCodeValidationForPAYG("", "");
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetailsResponseJourneyUGNull1() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			Assert.assertNotNull(deviceDetailsService.getDeviceDetailsResponse("093353", null, Constants.JOURNEY_TYPE_UPGRADE, cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetailsResponseJourneyUG1() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			Assert.assertNotNull(deviceDetailsService.getDeviceDetailsResponse("093353", "abc", Constants.JOURNEY_TYPE_ACQUISITION, cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void getDeviceSummery_Implementation() {
		List<com.vf.uk.dal.device.entity.Member> member = new ArrayList<com.vf.uk.dal.device.entity.Member>();
		com.vf.uk.dal.device.entity.Member productGroupMember = new com.vf.uk.dal.device.entity.Member();
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
		deviceMakeAndModelServiceImpl.getDeviceSummery_Implementation(member,
				CommonMethods.getPriceForBundleAndHardware(), commerProdMemMap, true, "Upgrade", (double) (40),
				commercialBundleMap, bundleIdMap, "110154", bundleAndHardwarePromotionsMap, leadPlanIdMap,
				"DEVICE_PAYM", priceMapForParticularDevice, fromPricingMap, "1");
		deviceMakeAndModelServiceImpl.getPriceAndPromotionMap("Upgrade", commercialBundleMap1, leadPlanIdMap,
				listOfLeadPlanId, bundleAndHardwarePromotionsMap, priceMapForParticularDevice, promotionsMap, priceMap,
				"093353", true);
		deviceMakeAndModelServiceImpl.getPriceAndPromotionMap("Upgrade", commercialBundleMap1, leadPlanIdMap,
				listOfLeadPlanId, bundleAndHardwarePromotionsMap, priceMapForParticularDevice, promotionsMap, priceMap,
				"093353", true);

		deviceMakeAndModelServiceImpl.getListOfLeadPlan("110154", bundleAndHardwareTupleList, bundleIdMap,
				fromPricingMap, leadPlanIdMap, listOfLeadPlanId, CommonMethods.getMemberList().get(0),
				CommonMethods.getCommercialProduct());
	}

	@Test
	public void notNullisLeadPlanWithinCreditLimit_Implementation() {
		Assert.assertNotNull(deviceMakeAndModelServiceImpl.isLeadPlanWithinCreditLimit_Implementation(CommonMethods.getCommercialProduct(),
				(double) 40, CommonMethods.getPriceForBundleAndHardware(), "Upgrade"));
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
		given(response.getListOfProductModel(Matchers.anyObject())).willReturn(CommonMethods.getProductModel());
		given(response.getListOfBundleModel(Matchers.anyObject()))
				.willReturn(CommonMethods.getBundleModelListForBundleListForConditional());
		given(deviceDAOMock.getBundleDetailsFromComplansListingAPI(Matchers.anyString(), Matchers.anyString()))
				.willReturn(CommonMethods.getCompatibleBundleListJson());
		Assert.assertNotNull(conditionalHelper.calculatePlan((float) 10, listOfProductsNew, CommonMethods.getProductModel()));
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
		deviceMakeAndModelServiceImpl.resetDeviceId_Implementation(true,
				CommonMethods.getDeviceTile("Apple", "iPhone-7", "Device_PAYM").get(0), deviceSummaryList, "093353");
	}

	@Test
	public void TestsettingPriceAndPromotionsToListOfDevicesNull() {
		try {
			DeviceDetails dd = CommonMethods.getDevice_One("093353");
			List<DeviceDetails> ddList = new ArrayList<>();
			ddList.add(dd);
			deviceDetailsService.settingPriceAndPromotionsToListOfDevices(null, ddList);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetReviewRatingList_Implementation() {

		given(deviceDAOMock.getBazaarVoice(Matchers.anyString())).willThrow(new ApplicationException(""));
		try {
			utility.getReviewRatingList_Implementation(Arrays.asList("093353"));
		} catch (Exception e) {

		}
		Assert.assertNotNull(utility.getAscendingOrderForBundleHeaderPrice(CommonMethods.getBundleHeaderList("SIMO")));
	}

	@Test
	public void TestsettingPriceAndPromotionsToListOfDevicesEmpty() {
		try {
			DeviceDetails dd = CommonMethods.getDevice_One("093353");
			List<DeviceDetails> ddList = new ArrayList<>();
			ddList.add(dd);
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = new ArrayList<>();
			deviceDetailsService.settingPriceAndPromotionsToListOfDevices(listOfPriceForBundleAndHardware, ddList);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestsettingPriceAndPromotionsToListOfDevices() {
		try {
			DeviceDetails dd = CommonMethods.getDevice_One("093353");
			List<DeviceDetails> ddList = new ArrayList<>();
			ddList.add(dd);
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = new ArrayList<>();
			PriceForBundleAndHardware priceForBundleAndHardware = new PriceForBundleAndHardware();
			priceForBundleAndHardware.setHardwarePrice(null);
			listOfPriceForBundleAndHardware.add(priceForBundleAndHardware);
			deviceDetailsService.settingPriceAndPromotionsToListOfDevices(listOfPriceForBundleAndHardware, ddList);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestsettingPriceAndPromotionsToListOfDevices1() {
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
		}
	}

	@Test
	public void TestsettingPriceAndPromotionsToListOfDevicesMPNull() {
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
		}
	}

	@Test
	public void TestsettingPriceAndPromotionsToListOfDevicesMPNullForBundle() {
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
		}
	}

	@Test
	public void TestsettingPriceAndPromotionsToListOfDevicesMPTagNull() {
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
		}
	}

	@Test
	public void TestsettingPriceAndPromotionsToListOfDevices11() {
		try {
			DeviceDetails dd = CommonMethods.getDevice("093353");
			List<DeviceDetails> ddList = new ArrayList<>();
			ddList.add(dd);
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = CommonMethods
					.getPriceForBundleAndHardware();
			deviceDetailsService.settingPriceAndPromotionsToListOfDevices(listOfPriceForBundleAndHardware, ddList);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestsettingPriceAndPromotionsToListOfDevices12() {
		try {
			DeviceDetails dd = CommonMethods.getDevice_One("93353");
			List<DeviceDetails> ddList = new ArrayList<>();
			ddList.add(dd);
			List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware = CommonMethods
					.getPriceForBundleAndHardware();
			deviceDetailsService.settingPriceAndPromotionsToListOfDevices(listOfPriceForBundleAndHardware, ddList);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestsettingPriceAndPromotionsToListOfDevicesMPInvalidTag() {
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
		}
	}

	@Test
	public void TestgetMediaListForDevice() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			Assert.assertNotNull(deviceDetailsService.getMediaListForDevice(cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetMediaListForDeviceCP() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setPromoteAs(null);
			Assert.assertNotNull(deviceDetailsService.getMediaListForDevice(cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetMediaListForDeviceCPNull() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			PromoteAs pa = cp.getPromoteAs();
			pa.setPromotionName(null);
			cp.setPromoteAs(pa);
			Assert.assertNotNull(deviceDetailsService.getMediaListForDevice(cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetMediaListForDeviceCPNull1() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			PromoteAs pa = cp.getPromoteAs();
			List<String> promotionName = new ArrayList<>();
			pa.setPromotionName(promotionName);
			cp.setPromoteAs(pa);
			Assert.assertNotNull(deviceDetailsService.getMediaListForDevice(cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetMediaListForDeviceCPNull2() {
		try {
			given(response.getMerchandisingPromotion(Matchers.anyObject())).willReturn(null);
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			PromoteAs pa = cp.getPromoteAs();
			List<String> promotionName = new ArrayList<>();
			promotionName.add("Abc");
			pa.setPromotionName(promotionName);
			cp.setPromoteAs(pa);
			Assert.assertNotNull(deviceDetailsService.getMediaListForDevice(cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetMediaListForDeviceCPNMP() {
		try {
			com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion mp = CommonMethods.getMemPro();
			mp.setType("conditional_full_discount");
			given(response.getMerchandisingPromotion(Matchers.anyObject())).willReturn(mp);
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			PromoteAs pa = cp.getPromoteAs();
			List<String> promotionName = new ArrayList<>();
			promotionName.add("Abc");
			pa.setPromotionName(promotionName);
			cp.setPromoteAs(pa);
			Assert.assertNotNull(deviceDetailsService.getMediaListForDevice(cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetMediaListForDeviceCPNMPPN() {
		try {
			com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion mp = CommonMethods.getMemPro();
			mp.setType("conditional_full_discount");
			given(response.getMerchandisingPromotion(Matchers.anyObject())).willReturn(mp);
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			PromoteAs pa = cp.getPromoteAs();
			List<String> promotionName = new ArrayList<>();
			promotionName.add("handset.hardware.percentage");
			pa.setPromotionName(promotionName);
			cp.setPromoteAs(pa);
			Assert.assertNotNull(deviceDetailsService.getMediaListForDevice(cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetMediaListForDeviceCPNMPPN1() {
		try {
			com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion mp = CommonMethods.getMemPro();
			mp.setType("conditional_full_discount");
			given(response.getMerchandisingPromotion(Matchers.anyObject())).willReturn(mp);
			CommercialProduct cp = CommonMethods.getCommercialProductsListOfAccessoriesWithEndDate_One();
			PromoteAs pa = cp.getPromoteAs();
			List<String> promotionName = new ArrayList<>();
			promotionName.add("handset.hardware.percentage");
			pa.setPromotionName(promotionName);
			cp.setPromoteAs(pa);
			Assert.assertNotNull(deviceDetailsService.getMediaListForDevice(cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetMediaListForDeviceCPNMP1() {
		try {
			com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion mp = CommonMethods.getMemPro();
			mp.setType("conditional_limited_discount");
			given(response.getMerchandisingPromotion(Matchers.anyObject())).willReturn(mp);
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			PromoteAs pa = cp.getPromoteAs();
			List<String> promotionName = new ArrayList<>();
			promotionName.add("Abc");
			pa.setPromotionName(promotionName);
			cp.setPromoteAs(pa);
			Assert.assertNotNull(deviceDetailsService.getMediaListForDevice(cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetMediaListForDeviceCPNMP2() {
		try {
			com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion mp = CommonMethods.getMemPro();
			mp.setType("limited_time");
			given(response.getMerchandisingPromotion(Matchers.anyObject())).willReturn(mp);
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			PromoteAs pa = cp.getPromoteAs();
			List<String> promotionName = new ArrayList<>();
			promotionName.add("Abc");
			pa.setPromotionName(promotionName);
			cp.setPromoteAs(pa);
			Assert.assertNotNull(deviceDetailsService.getMediaListForDevice(cp));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetListOfPriceForBundleAndHardware_Implementation() {
		try {
			Map<String, CommercialBundle> commerBundleIdMap = new HashMap<>();
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			Assert.assertNotNull(deviceDetailsService.getListOfPriceForBundleAndHardware_Implementation(cp, commerBundleIdMap,
					"Acquisition"));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetListOfPriceForBundleAndHardware_Implementation1() {
		try {
			Map<String, CommercialBundle> commerBundleIdMap = new HashMap<>();
			CommercialBundle cb = CommonMethods.getCommercialBundle();
			cb.setId("088417");
			commerBundleIdMap.put("088417", cb);
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			Assert.assertNotNull(deviceDetailsService.getListOfPriceForBundleAndHardware_Implementation(cp, commerBundleIdMap,
					"Acquisition"));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetListOfPriceForBundleAndHardware_Implementation1CpIdBlanck() {
		try {
			Map<String, CommercialBundle> commerBundleIdMap = new HashMap<>();
			CommercialBundle cb = CommonMethods.getCommercialBundle();
			cb.setId("088417");
			commerBundleIdMap.put("088417", cb);
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setLeadPlanId("");
			Assert.assertNotNull(deviceDetailsService.getListOfPriceForBundleAndHardware_Implementation(cp, null, "Acquisition"));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetListOfPriceForBundleAndHardware_Implementation1CpIdBnull() {
		try {
			Map<String, CommercialBundle> commerBundleIdMap = new HashMap<>();
			CommercialBundle cb = CommonMethods.getCommercialBundle();
			cb.setId("088417");
			commerBundleIdMap.put("088417", cb);
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setLeadPlanId(null);
			Assert.assertNotNull(deviceDetailsService.getListOfPriceForBundleAndHardware_Implementation(cp, null, "Acquisition"));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestvalidateOfferValidForDevice_Implementation() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setPromoteAs(null);
			Assert.assertNotNull(deviceDetailsService.validateOfferValidForDevice_Implementation(cp, "Acquisition", "acb"));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestvalidateOfferValidForDevice_Implementation1() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			PromoteAs pa = cp.getPromoteAs();
			pa.setPromotionName(null);
			cp.setPromoteAs(pa);
			Assert.assertNotNull(deviceDetailsService.validateOfferValidForDevice_Implementation(cp, "Acquisition", "acb"));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestvalidateOfferValidForDevice_Implementation2() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			PromoteAs pa = cp.getPromoteAs();
			List<String> promotioname = new ArrayList<>();
			pa.setPromotionName(promotioname);
			cp.setPromoteAs(pa);
			Assert.assertNotNull(deviceDetailsService.validateOfferValidForDevice_Implementation(cp, "Acquisition", "acb"));
		} catch (Exception e) {
		}
	}

	@Test
	public void TestvalidateOfferValidForDevice_Implementatio() {
		try {
			com.vf.uk.dal.device.datamodel.merchandisingpromotion.MerchandisingPromotion mp = CommonMethods.getMemPro();
			mp.setType("conditional_full_discount");
			given(response.getMerchandisingPromotion(Matchers.anyObject())).willReturn(CommonMethods.getMemPro());
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			PromoteAs pa = cp.getPromoteAs();
			List<String> promotioname = new ArrayList<>();
			promotioname.add("limited_time");
			pa.setPromotionName(promotioname);
			cp.setPromoteAs(pa);
			Assert.assertNotNull(deviceDetailsService.validateOfferValidForDevice_Implementation(cp, "Acquisition", "acb"));
		} catch (Exception e) {
		}
	}

	@Test
	public void notNullGetPriceAndPromotionMap() {
		Map<String, List<BundleAndHardwarePromotions>> promotionsMap = new HashMap<>();
		promotionsMap.put("093353", CommonMethods.getListOfBundleAndHardwarePromotions());
		Map<String, List<PriceForBundleAndHardware>> priceMap = new HashMap<>();
		priceMap.put("093353", CommonMethods.getOfferAppliedPrice());
		try {
			deviceMakeAndModelServiceImpl.getPriceAndPromotionMap("SecondLine", Collections.emptyMap(),
					Collections.emptyMap(), Collections.emptyList(), new HashMap<>(), new HashMap<>(), promotionsMap,
					priceMap, "093353", false);

		} catch (Exception e) {
		}
	}

	
}
