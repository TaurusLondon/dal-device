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
		deviceService.getConditionalForDeviceList("7741655542",
				CommonMethods.getFacetedDeviceList("HANDSET", "Apple", "iPhone-7", "DEVICE_PAYM", "", 1, 9, "wrtety"));
	}

	// accessory test cases start
	@Test
	public void getAccessoriesOfDevice() {
		accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc");
	}

	// deviceRecomServiceMock
	@Test
	public void getdeviceRecomServiceMock() {
		try {
			deviceRecomServiceMock.getRecommendedDeviceList("9237", "093353", null);
		} catch (Exception e) {

		}
	}

	@Test
	public void getAccessoriesOfDeviceCPNull() {
		try {
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(null);
			accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc");
		} catch (Exception e) {

		}
	}

	@Test
	public void getAccessoriesOfDeviceCPIdNull() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setId(null);
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc");
		} catch (Exception e) {

		}
	}

	@Test
	public void getAccessoriesOfDeviceCPProductNull() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setProductClass("abc");
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc");
		} catch (Exception e) {

		}
	}

	@Test
	public void getAccessoriesOfDeviceCPProductDevice() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setIsDeviceProduct(false);
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc");
		} catch (Exception e) {

		}
	}

	@Test
	public void getAccessoriesOfDeviceCPProductGroupNull() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setProductGroups(null);
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc");
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
			accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc");
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
			accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc");
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
			accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc");
		} catch (Exception e) {

		}
	}

	@Test
	public void getAccessoriesOfDeviceCPProductGroupInvalid() {
		try {
			given(response.getListOfGroupFromJson(Matchers.anyObject())).willReturn(CommonMethods.getGroup_Two());
			accessoryInsuranceService.getAccessoriesOfDevice("093353", "Acquisition", "abc");
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
			accessoryInsuranceService.getListOfFilteredAccessories("Acquisition", finalAccessoryList);
		} catch (Exception e) {

		}
	}

	@Test
	public void TestsetMapForCommercialData() {
		try {
			PriceForProduct pc = new PriceForProduct();
			pc.setPriceForAccessoryes(null);
			AccessoryInsuranceServiceImpl.setMapForCommercialData(null, null, pc, null);
		} catch (Exception e) {

		}
	}

	// insurance test cases start

	@Test
	public void getInsuranceByDeviceId() {
		try {
			accessoryInsuranceService.getInsuranceByDeviceId("093353", "Acquisition");
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
			accessoryInsuranceService.getInsurance("acquisition", cp);
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
			accessoryInsuranceService.getInsurance("acquisition", cp);
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
			AccessoryInsuranceServiceImpl.getInsuranceProductList(listOfInsuranceMembers, gp);
		} catch (Exception e) {

		}
	}

	@Test
	public void TestgetInsuranceProductList5() {
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
			listOfInsuranceMembers.add(member);
			AccessoryInsuranceServiceImpl.getInsuranceProductList(listOfInsuranceMembers, gp);
		} catch (Exception e) {

		}
	}

	@Test
	public void TestgetInsuranceProductList6() {
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
			listOfInsuranceMembers.add(member);
			AccessoryInsuranceServiceImpl.getInsuranceProductList(listOfInsuranceMembers, gp);
		} catch (Exception e) {

		}
	}
	@Test
	public void testassembleMechandisingPromotionsPackageGeneric(){
		DeviceDetailsMakeAndModelVaiantDaoUtils.assembleMechandisingPromotionsPackageGeneric(CommonMethods.getListOfBundleAndHardwarePromotions().get(0),
				CommonMethods.getPriceForBundleAndHardware().get(0));
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
			AccessoryInsuranceServiceImpl.getInsuranceProductList(listOfInsuranceMembers, gp);
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
			AccessoryInsuranceServiceImpl.getInsuranceProductList(null, gp);
		} catch (Exception e) {

		}
	}

	@Test
	public void TestgetInsuranceProductList1() {
		try {
			Group gp = new Group();
			gp.setGroupType("acd");
			List<Member> listOfInsuranceMembers = new ArrayList<>();
			AccessoryInsuranceServiceImpl.getInsuranceProductList(listOfInsuranceMembers, gp);
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
			;
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
			AccessoryInsuranceServiceImpl.getInsuranceProductList(listOfInsuranceMembers, gp);
		} catch (Exception e) {

		}
	}

	@Test
	public void TestgetInsuranceProductList3() {
		try {
			Group gp = new Group();
			gp.setGroupType(null);
			List<Member> listOfInsuranceMembers = new ArrayList<>();
			AccessoryInsuranceServiceImpl.getInsuranceProductList(listOfInsuranceMembers, null);
		} catch (Exception e) {

		}
	}

	@Test
	public void getInsuranceByDeviceIdNull() {
		try {
			accessoryInsuranceService.getInsuranceByDeviceId(null, "Acquisition");
		} catch (Exception e) {
		}
	}

	@Test
	public void getInsuranceByDeviceIdInvalid() {
		try {
			accessoryInsuranceService.getInsuranceByDeviceId("098578", "Acquisition");
		} catch (Exception e) {
		}
	}

	@Test
	public void getInsuranceByDeviceIdInvalid1() {
		try {
			accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition");
		} catch (Exception e) {
		}
	}

	@Test
	public void getInsuranceByDeviceIdInvalid2() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setIsDeviceProduct(false);
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition");
		} catch (Exception e) {
		}
	}

	@Test
	public void getInsuranceByDeviceIdInvalid3() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setProductClass("abc");
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition");
		} catch (Exception e) {
		}
	}

	@Test
	public void getInsuranceCPProductGroupNull() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setProductGroups(null);
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition");
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
			accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition");
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
			accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition");
		} catch (Exception e) {

		}
	}

	@Test
	public void getInsuranceCPNull() {
		try {
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(null);
			accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition");
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
			accessoryInsuranceService.getInsuranceByDeviceId("098578asa", "Acquisition");
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
			deviceMakeAndModelServiceImpl.getListOfDeviceTile("apple", "iPhone-7", "DEVICE_PAYM", "093353", 40.0,
					"Upgrade", "W_HH_SIMONLY", "110154");
		} catch (Exception e) {

		}

	}

	@Test
	public void getDeviceTileByMakeAndModelForPAYG() {
		deviceMakeAndModelServiceImpl.getDeviceTileByMakeAndModelForPAYG(
				CommonMethods.getCommercialProductsListOfMakeAndModel(), CommonMethods.getGroupForVariant(), "apple",
				"iPhone-7", "DEVICE_PAYM");
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

		deviceMakeAndModelServiceImpl.getLeadBundleBasedOnAllPlans_Implementation((double) (40),
				CommonMethods.getCommercialProduct(), CommonMethods.getPriceForBundleAndHardware(), "Upgrade");
	}

	@Test
	public void TestgetDeviceDetails_Implementation() {
		try {
			deviceDetailsService.getDeviceDetails_Implementation("093353", Constants.JOURNEY_TYPE_SECONDLINE, "abc");
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetails_ImplementationJTBlank() {
		try {
			deviceDetailsService.getDeviceDetails_Implementation("093353", "", "abc");
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
			deviceDetailsService.getDeviceDetails_Implementation("093353", Constants.JOURNEY_TYPE_SECONDLINE, "abc");
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetails_ImplementationInvalidCPIdNUll() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setId(null);
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			deviceDetailsService.getDeviceDetails_Implementation("093353", Constants.JOURNEY_TYPE_SECONDLINE, "abc");
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetails_ImplementationInvalidCPDPInvalid() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setIsDeviceProduct(false);
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			deviceDetailsService.getDeviceDetails_Implementation("093353", Constants.JOURNEY_TYPE_SECONDLINE, "abc");
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetails_ImplementationInvalidCPPCInvalid() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setProductClass("accessories");
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			deviceDetailsService.getDeviceDetails_Implementation("093353", Constants.JOURNEY_TYPE_SECONDLINE, "abc");
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetails_ImplementationInvalidCPPCInvalid1() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setProductClass(Constants.STRING_DATA_DEVICE);
			given(response.getCommercialProduct(Matchers.anyObject())).willReturn(cp);
			deviceDetailsService.getDeviceDetails_Implementation("093353", Constants.JOURNEY_TYPE_SECONDLINE, "abc");
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetailsResponse() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			deviceDetailsService.getDeviceDetailsResponse("093353", "abc", "Acquisition", cp);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetailsResponsePLNull() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setProductLines(null);
			deviceDetailsService.getDeviceDetailsResponse("093353", "abc", "Acquisition", cp);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetailsResponsePLEmpty() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			List<String> pl = new ArrayList<>();
			cp.setProductLines(pl);
			deviceDetailsService.getDeviceDetailsResponse("093353", "abc", Constants.JOURNEY_TYPE_SECONDLINE, cp);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetailsResponseJourney() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			deviceDetailsService.getDeviceDetailsResponse("093353", "abc", Constants.JOURNEY_TYPE_SECONDLINE, cp);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetailsResponseJourneyUG() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			deviceDetailsService.getDeviceDetailsResponse("093353", "abc", Constants.JOURNEY_TYPE_UPGRADE, cp);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetailsResponseJourneyNull() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			deviceDetailsService.getDeviceDetailsResponse("093353", null, Constants.JOURNEY_TYPE_SECONDLINE, cp);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetailsResponseJourneyNull1() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			deviceDetailsService.getDeviceDetailsResponse("093353", "abc", null, cp);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetailsResponseJourneyNull11() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			deviceDetailsService.getDeviceDetailsResponse("093353", null, null, cp);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetailsResponseJourneyUGNull() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			deviceDetailsService.getDeviceDetailsResponse("093353", null, Constants.JOURNEY_TYPE_UPGRADE, cp);
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
			deviceDetailsService.getDeviceDetailsResponse("093353", null, Constants.JOURNEY_TYPE_UPGRADE, cp);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetDeviceDetailsResponseJourneyUG1() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			deviceDetailsService.getDeviceDetailsResponse("093353", "abc", Constants.JOURNEY_TYPE_ACQUISITION, cp);
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
		deviceMakeAndModelServiceImpl.isLeadPlanWithinCreditLimit_Implementation(CommonMethods.getCommercialProduct(),
				(double) 40, CommonMethods.getPriceForBundleAndHardware(), "Upgrade");
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
		conditionalHelper.calculatePlan((float) 10, listOfProductsNew, CommonMethods.getProductModel());
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

	/*@Test
	public void TestgetReviewRatingList_Implementation() {

		given(deviceDAOMock.getBazaarVoice(Matchers.anyString())).willThrow(new ApplicationException(""));
		try {
			utility.getReviewRatingList_Implementation(Arrays.asList("093353"));
		} catch (Exception e) {

		}
		utility.getAscendingOrderForBundleHeaderPrice(CommonMethods.getBundleHeaderList("SIMO"));
	}*/

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
			deviceDetailsService.getMediaListForDevice(cp);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetMediaListForDeviceCP() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setPromoteAs(null);
			deviceDetailsService.getMediaListForDevice(cp);
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
			deviceDetailsService.getMediaListForDevice(cp);
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
			deviceDetailsService.getMediaListForDevice(cp);
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
			deviceDetailsService.getMediaListForDevice(cp);
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
			deviceDetailsService.getMediaListForDevice(cp);
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
			deviceDetailsService.getMediaListForDevice(cp);
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
			deviceDetailsService.getMediaListForDevice(cp);
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
			deviceDetailsService.getMediaListForDevice(cp);
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
			deviceDetailsService.getMediaListForDevice(cp);
		} catch (Exception e) {
		}
	}

	@Test
	public void TestgetListOfPriceForBundleAndHardware_Implementation() {
		try {
			Map<String, CommercialBundle> commerBundleIdMap = new HashMap<>();
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			deviceDetailsService.getListOfPriceForBundleAndHardware_Implementation(cp, commerBundleIdMap,
					"Acquisition");
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
			deviceDetailsService.getListOfPriceForBundleAndHardware_Implementation(cp, commerBundleIdMap,
					"Acquisition");
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
			deviceDetailsService.getListOfPriceForBundleAndHardware_Implementation(cp, null, "Acquisition");
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
			deviceDetailsService.getListOfPriceForBundleAndHardware_Implementation(cp, null, "Acquisition");
		} catch (Exception e) {
		}
	}

	@Test
	public void TestvalidateOfferValidForDevice_Implementation() {
		try {
			CommercialProduct cp = CommonMethods.getCommercialProductByDeviceId_093353_PAYG();
			cp.setPromoteAs(null);
			deviceDetailsService.validateOfferValidForDevice_Implementation(cp, "Acquisition", "acb");
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
			deviceDetailsService.validateOfferValidForDevice_Implementation(cp, "Acquisition", "acb");
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
			deviceDetailsService.validateOfferValidForDevice_Implementation(cp, "Acquisition", "acb");
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
			deviceDetailsService.validateOfferValidForDevice_Implementation(cp, "Acquisition", "acb");
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

	/*
	 * @Before public void setupMockBehaviour() throws Exception {
	 * aspect.beforeAdvice(null); String jsonString=new
	 * String(Utility.readFile("\\rest-mock\\COMMON-V1.json")); CurrentJourney
	 * obj=new ObjectMapper().readValue(jsonString, CurrentJourney.class);
	 * given(registry.getRestTemplate()).willReturn(restTemplate);
	 * given(restTemplate.getForObject("http://COMMON-V1/common/journey/"+
	 * "c1a42269-6562-4c96-b3be-1ca2a6681d57"+"/queries/currentJourney"
	 * ,CurrentJourney.class)).willReturn(obj);
	 * given(response.getCommercialProduct(Matchers.anyObject())).willReturn(
	 * CommonMethods.getCommercialProductByDeviceId_093353_PAYG());
	 * given(response.getListOfGroupFromJson(Matchers.anyObject())).willReturn(
	 * CommonMethods.getGroup());
	 * given(response.getCommercialProductFromJson(Matchers.anyObject())).
	 * willReturn(CommonMethods.getCommercialProductsListOfAccessories());
	 * given(response.getCommercialBundle(Matchers.anyObject())).willReturn(
	 * CommonMethods.getCommercialBundle()); List<String>
	 * listOfRecommendedProductTypes; RecommendedProductListRequest
	 * recomProductListReq = new RecommendedProductListRequest();
	 * listOfRecommendedProductTypes = new ArrayList<>();
	 * listOfRecommendedProductTypes.add(Constants.STRING_DEVICE);
	 * recomProductListReq.setSerialNumber("07003145001");
	 * recomProductListReq.setRecommendedProductTypes(
	 * listOfRecommendedProductTypes); String jsonString1=new
	 * String(Utility.readFile("\\rest-mock\\CUSTOMER-V1.json"));
	 * RecommendedProductListResponse obj1=new
	 * ObjectMapper().readValue(jsonString1,
	 * RecommendedProductListResponse.class);
	 * given(registry.getRestTemplate()).willReturn(restTemplate);
	 * given(restTemplate.postForObject(
	 * "http://CUSTOMER-V1/customer/getRecommendedProductList/",
	 * recomProductListReq,RecommendedProductListResponse.class)).willReturn(
	 * obj1);
	 * 
	 * given(this.deviceDAOMock.getListOfDeviceTile("Apple","iPhone-7","DEVICE",
	 * null, null,
	 * null,null,null)).willReturn(CommonMethods.getDeviceTile("Apple",
	 * "iPhone-7","DEVICE"));
	 * given(this.deviceDAOMock.getListOfDeviceTile(null,"iPhone-7","DEVICE",
	 * null, null, null,null,null)).willReturn(null);
	 * given(this.deviceDAOMock.getListOfDeviceTile("Apple",null,"DEVICE", null,
	 * null, null,null,null)).willReturn(null);
	 * given(this.deviceDAOMock.getListOfDeviceTile("Apple","iPhone-7",null,
	 * null, null, null,null,null)).willReturn(null);
	 * 
	 * given(this.deviceDAOMock.getProductGroupByGroupTypeGroupName("DEVICE",
	 * "Apple iPhone 6s"
	 * )).willReturn(CommonMethods.getProductGroupByGroupTypeGroupName("DEVICE",
	 * "Apple IPhone 6s"));
	 * given(this.deviceDAOMock.getProductGroupByGroupTypeGroupName(null,
	 * "Apple iPhone 7")).willReturn(null);
	 * given(this.deviceDAOMock.getProductGroupByGroupTypeGroupName("DEVICE",
	 * null)).willReturn(null);
	 * given(this.deviceDAOMock.getProductGroupByGroupTypeGroupName("DEVfk",
	 * "Apple iPhone 6s")).willReturn(null);
	 * given(this.deviceDAOMock.getProductGroupByGroupTypeGroupName(null,null)).
	 * willReturn(null);
	 * given(this.deviceDAOMock.getDeviceTileById("83921",null,null)).willReturn
	 * (CommonMethods.getDeviceTileById("83921"));
	 * given(this.deviceDAOMock.getDeviceTileById("83964",null,null)).willReturn
	 * (null);
	 * given(this.deviceDAOMock.getDeviceDetails("83921","upgrade","34543")).
	 * willReturn(CommonMethods.getDevice("83921"));
	 * given(this.deviceDAOMock.getDeviceDetails("83921","Upgrade",
	 * "W_HH_PAYM_OC_02")).willReturn(CommonMethods.getDevice("83921"));
	 * given(this.deviceDAOMock.getDeviceDetails("83929","upgrade","34543")).
	 * willReturn(null);
	 * given(this.deviceDAOMock.getAccessoriesOfDevice("93353","Upgrade",
	 * "W_HH_PAYM_OC_02")).willReturn(CommonMethods.getAccessoriesTileGroup(
	 * "93353"));
	 * given(this.deviceDAOMock.getAccessoriesOfDevice("93354","Upgrade",
	 * "W_HH_PAYM_OC_02")).willReturn(null);
	 * given(this.deviceDAOMock.getAccessoriesOfDevice(null,null,null)).
	 * willReturn(null);
	 * //given(this.deviceDAOMock.getDeviceList("HANDSET","apple",
	 * "iPhone-7","DEVICE_PAYM", "Priority", 1, 2,"32 GB","White","iOS",
	 * "Great Camera"
	 * )).willReturn(CommonMethods.getFacetedDevice("HANDSET","apple",
	 * "iPhone 7", "DEVICE_PAYM", "asc", 1, 2,"123"));
	 * //given(this.deviceDAOMock.getDeviceList(null,null,null,null,null,1,0,
	 * "32 GB","White","iOS","Great Camera")).willReturn(null);
	 * //given(this.deviceDAOMock.getDeviceList("productclass","make","model",
	 * null,"",1,0,"32 GB","White","iOS","Great Camera")).willReturn(null);
	 * //given(this.deviceDAOMock.getInsuranceById("93353")).willReturn(
	 * CommonMethods.getInsurances("93353"));
	 * 
	 * List<BundleAndHardwareTuple> bundleAndHardwareTupleList = new
	 * ArrayList<>(); BundleAndHardwareTuple bundleAndHardwareTuple = new
	 * BundleAndHardwareTuple(); bundleAndHardwareTuple.setHardwareId("093353");
	 * bundleAndHardwareTuple.setBundleId("110154");
	 * bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
	 * given(this.deviceDAOMock.getPriceForBundleAndHardware(
	 * bundleAndHardwareTupleList,null,null)).willReturn(CommonMethods.
	 * getPriceForBundleAndHardware1());
	 * 
	 * given(restTemplate.postForObject("http://PRICE-V1/price/product",
	 * CommonMethods.bundleDeviceAndProductsList_For_GetAccessoriesOfDevice(),
	 * PriceForProduct.class))
	 * .willReturn(CommonMethods.getPriceForProduct_For_GetAccessoriesForDevice(
	 * ));
	 * 
	 * 
	 * given(this.response.getListOfMerchandisingPromotionModelFromJson(Matchers
	 * .anyObject())).willReturn(CommonMethods.getMerChandisingPromotion());
	 * //ProductGroupFacetModel productGroupFacetModel =
	 * CommonMethods.getProductGroupFacetModel();
	 * //productGroupFacetModel.setListOfProductGroups(CommonMethods.
	 * getListOfProductGroupModels());
	 * //this.deviceDAOMock.getProductGroupFacetModelfromSolr(Matchers.any(),
	 * Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(),
	 * Matchers.any())
	 * given(response.getListOfProductGroupModel(Matchers.anyObject())).
	 * willReturn(CommonMethods.getListOfProductGroupMode());
	 * 
	 * }
	 * 
	 * @Test public void notNullTestForGetDeviceTileList() { List<DeviceTile>
	 * deviceTileList =
	 * deviceService.getListOfDeviceTile("Apple","iPhone-7","DEVICE", null,
	 * null,null,null,null); Assert.assertNotNull(deviceTileList); }
	 * 
	 * @Test public void sizeTestForGetDeviceTileList() { List<DeviceTile>
	 * deviceTileList=null; try { deviceTileList =
	 * deviceService.getListOfDeviceTile(null,"iPhone-7","DEVICE", null,
	 * null,null,null,null); } catch(Exception e) {
	 * 
	 * } Assert.assertNull(deviceTileList); }
	 * 
	 * @Test public void nullInputTestForGetDeviceTileList() throws Exception {
	 * List<DeviceTile> deviceTileList=null; try { deviceTileList =
	 * deviceService.getListOfDeviceTile("Apple",null,"DEVICE", null,
	 * null,"upgrade","34543",null); } catch(Exception e) {
	 * 
	 * } Assert.assertNull(deviceTileList); }
	 * 
	 * @Test public void nullTestGroupTypeForGetDeviceTileList() throws
	 * Exception { List<DeviceTile> deviceTileList=null; try { deviceTileList =
	 * deviceService.getListOfDeviceTile("Apple","iPhone-7",null, null,
	 * null,"upgrade","34543",null); } catch(Exception e) {
	 * 
	 * } Assert.assertNull(deviceTileList); }
	 * 
	 * @Test public void invalidTestGroupTypeForGetDeviceTileList() throws
	 * Exception { List<DeviceTile> deviceTileList=null; try { deviceTileList =
	 * deviceService.getListOfDeviceTile("Apple","iPhone-7","INVALID_GT", null,
	 * null,"upgrade","34543",null); } catch(Exception e) {
	 * 
	 * } Assert.assertNull(deviceTileList); }
	 * 
	 * @Test public void notNullTestForgetProductGroupListByGroupTypeGroupName()
	 * { List<ProductGroup> productGroupDetails =
	 * deviceService.getProductGroupByGroupTypeGroupName("DEVICE",
	 * "Apple iPhone 6s"); Assert.assertNotNull(productGroupDetails); }
	 * 
	 * @Test public void nullTestForgetProductGroupListByGroupTypeGroupName() {
	 * 
	 * List<ProductGroup> productGroupDetails =
	 * deviceService.getProductGroupByGroupTypeGroupName("DEVfk",
	 * "Apple iPhone 6s"); Assert.assertNull(productGroupDetails); }
	 * 
	 * @Test public void
	 * nullTestForgetProductGroupListByGroupNameWithNullValue() throws Exception
	 * { List<ProductGroup> productGroupDetails=null; try { productGroupDetails
	 * = deviceService.getProductGroupByGroupTypeGroupName("DEVICE",null); }
	 * catch(Exception e) { }
	 * 
	 * Assert.assertNull(productGroupDetails); }
	 * 
	 * @Test public void
	 * nullTestForgetProductGroupListByGroupTypeGroupNameWithNullValue() throws
	 * Exception { List<ProductGroup> productGroupDetails=null; try {
	 * productGroupDetails =
	 * deviceService.getProductGroupByGroupTypeGroupName(null,"Apple iPhone 7");
	 * } catch(Exception e) { }
	 * 
	 * Assert.assertNull(productGroupDetails); }
	 * 
	 * @Test public void notNullTestForGetDeviceTilesById() {
	 * 
	 * List<DeviceTile> deviceTileList =
	 * deviceService.getDeviceTileById("83921",null,null);
	 * Assert.assertNotNull(deviceTileList); }
	 * 
	 * @Test public void nullTestForGetDeviceTilesById() { List<DeviceTile>
	 * deviceTileList=null; try { deviceTileList =
	 * deviceService.getDeviceTileById("83964",null,null); } catch (Exception e)
	 * { Assert.assertNull(deviceTileList); }
	 * 
	 * }
	 * 
	 * @Test public void notNullTestForGetDeviceDetails() {
	 * 
	 * DeviceDetails deviceDetails=new DeviceDetails();
	 * deviceDetails=deviceService.getDeviceDetails("83921","","");
	 * Assert.assertNotNull(deviceDetails); }
	 * 
	 * @Test public void notNullTestForGetAccessoriesOfDevice() {
	 * List<AccessoryTileGroup> accessory=new ArrayList<>(); try {
	 * accessory=deviceService.getAccessoriesOfDevice("093353","Upgrade",
	 * "W_HH_PAYM_OC_02"); } catch (Exception e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } //Assert.assertNotNull(accessory); }
	 * 
	 * @Test public void nullTestForGetAccessoriesOfDevice() {
	 * List<AccessoryTileGroup> accessory = null; try{
	 * accessory=deviceService.getAccessoriesOfDevice(null,null,null); }
	 * catch(Exception e) { Assert.assertNull(accessory); }
	 * 
	 * }
	 * 
	 * @Test public void notNullTestForGetDeviceList() { FacetedDevice
	 * deviceLists=null;
	 * deviceLists=deviceService.getDeviceList("HANDSET","apple",
	 * "iPhone-7","DEVICE_PAYM", "Priority", 1, 2,"32 GB","White","iOS",
	 * "Great Camera","c1a42269-6562-4c96-b3be-1ca2a6681d57");
	 * 
	 * Assert.assertNotNull(deviceLists); }
	 * 
	 * @Test public void nullTestForGetDeviceList() { FacetedDevice
	 * deviceLists=null; try{
	 * deviceLists=deviceService.getDeviceList("Handset","apple", "iPhone 7",
	 * "DEVICE_PAYM",null,0,0,"32 GB","White","iOS","Great Camera","123"); }
	 * catch(Exception e) {
	 * 
	 * } Assert.assertNull(deviceLists); }
	 * 
	 * @Test public void nullTestForGetDeviceList1() { FacetedDevice
	 * deviceLists=null; try{
	 * Mockito.when(deviceRecomServiceMock.getRecommendedDeviceList("journeyId",
	 * "093353", Mockito.anyObject())).thenReturn(deviceLists);
	 * 
	 * deviceLists=deviceService.getDeviceList("Handset1","apple", "iPhone 7",
	 * "DEVICE_PAYM", "asc", 1, 2,"32 GB","White","iOS","Great Camera"
	 * ,"upgrade",34543.0f,null, "447582367723", true); } catch(Exception e) {
	 * 
	 * } Assert.assertNull(deviceLists); }
	 * 
	 * @Test public void nullTestForGetDeviceList2() { FacetedDevice
	 * deviceLists=null; try{
	 * Mockito.when(deviceRecomServiceMock.getRecommendedDeviceList("journeyId",
	 * "093353", Mockito.anyObject())).thenReturn(deviceLists);
	 * 
	 * deviceLists=deviceService.getDeviceList("Handset",null, "iPhone 7",
	 * "DEVICE_PAYM", "Priority", 1, 2,"32 GB","White","iOS","Great Camera"
	 * ,"upgrade",34543.0f,null, "447582367723", true); } catch(Exception e) {
	 * 
	 * } Assert.assertNull(deviceLists); }
	 * 
	 * @Test public void nullTestForGetDeviceList3() { FacetedDevice
	 * deviceLists=null; try{
	 * Mockito.when(deviceRecomServiceMock.getRecommendedDeviceList("journeyId",
	 * "093353", Mockito.anyObject())).thenReturn(deviceLists);
	 * 
	 * deviceLists=deviceService.getDeviceList(null,"apple", "iphone 7",
	 * "DEVICE_PAYM", "Priority", 1, 2,"32 GB","White","iOS","Great Camera"
	 * ,"upgrade",34543.0f,null, "447582367723", true); } catch(Exception e) {
	 * 
	 * } Assert.assertNull(deviceLists); }
	 * 
	 * @Test public void nullTestForGetDeviceList4() { FacetedDevice
	 * deviceLists=null; try{
	 * Mockito.when(deviceRecomServiceMock.getRecommendedDeviceList("journeyId",
	 * "093353", Mockito.anyObject())).thenReturn(deviceLists);
	 * 
	 * deviceLists=deviceService.getDeviceList("Handset","apple", "iPhone 7",
	 * "DEVICE", "Priority", 1, 2,"32 GB","White","iOS","Great Camera"
	 * ,"upgrade",34543.0f,null, "447582367723", true); } catch(Exception e) {
	 * 
	 * } Assert.assertNull(deviceLists); }
	 * 
	 * @Test public void nullTestForGetDeviceListForGroupType() { FacetedDevice
	 * deviceLists=null; try{
	 * deviceLists=deviceService.getDeviceList("Handset","apple", "iPhone 7",
	 * null, "Priority", 1, 2,"32 GB","White","iOS","Great Camera"
	 * ,"upgrade",34543.0f,null, "447582367723", true); } catch(Exception e) {
	 * 
	 * } Assert.assertNull(deviceLists); }
	 * 
	 * @Test public void nullTestForGetInsuranceById() { try{ Insurances
	 * insurance=null; insurance=deviceService.getInsuranceByDeviceId("093352");
	 * } catch(Exception e){} }
	 * 
	 * @Test public void notNullTestForGetInsuranceById() { List<String>
	 * insuranceList=new ArrayList<>(); insuranceList.add("093353");
	 * insuranceList.add("093354");
	 * 
	 * 
	 * Insurances insurance=null;
	 * insurance=deviceService.getInsuranceByDeviceId("093353","upgrade");
	 * Assert.assertNotNull(insurance); }
	 * 
	 * @Test public void
	 * NotNullTestForDaoUtilsconvertCoherenceDeviceToDeviceTile() {
	 * DeviceSummary deviceSummary =
	 * DaoUtils.convertCoherenceDeviceToDeviceTile((long)1,
	 * CommonMethods.getCommercialProduct(),CommonMethods.getCommercialBundle(),
	 * CommonMethods.getPriceForBundleAndHardware().get(0),CommonMethods.
	 * getListOfBundleAndHardwarePromotions(),null, false,null);
	 * Assert.assertNotNull(deviceSummary); }
	 * 
	 * @Test public void
	 * NotNullTestDateForDaoUtilsconvertCoherenceDeviceToDeviceTile() {
	 * DeviceSummary deviceSummary =
	 * DaoUtils.convertCoherenceDeviceToDeviceTile((long)1,
	 * CommonMethods.getCommercialProduct1(),CommonMethods.getCommercialBundle1(
	 * ),CommonMethods.getPriceForBundleAndHardware().get(0),CommonMethods.
	 * getListOfBundleAndHardwarePromotions(),null, false,null);
	 * Assert.assertNotNull(deviceSummary); }
	 * 
	 * @Test public void
	 * NullTestStartDateForDaoUtilsconvertCoherenceDeviceToDeviceTile() {
	 * DeviceSummary deviceSummary =
	 * DaoUtils.convertCoherenceDeviceToDeviceTile((long)1,
	 * CommonMethods.getCommercialProduct1(),CommonMethods.getCommercialBundle2(
	 * ),CommonMethods.getPriceForBundleAndHardware().get(0),CommonMethods.
	 * getListOfBundleAndHardwarePromotions(),null, false,null);
	 * Assert.assertNotNull(deviceSummary); }
	 * 
	 * @Test public void
	 * NullTestEndDateForDaoUtilsconvertCoherenceDeviceToDeviceTile() {
	 * DeviceSummary deviceSummary =
	 * DaoUtils.convertCoherenceDeviceToDeviceTile((long)1,
	 * CommonMethods.getCommercialProduct1(),CommonMethods.getCommercialBundle3(
	 * ),CommonMethods.getPriceForBundleAndHardware().get(0),CommonMethods.
	 * getListOfBundleAndHardwarePromotions(),null, false,null);
	 * Assert.assertNotNull(deviceSummary); }
	 * 
	 * @Test public void
	 * NotNullTestForDaoUtilsconvertCoherenceDeviceToDeviceDetails() {
	 * DeviceDetails deviceDetails =
	 * DaoUtils.convertCoherenceDeviceToDeviceDetails(CommonMethods.
	 * getCommercialProduct(),CommonMethods.getPriceForBundleAndHardware(),
	 * CommonMethods.getListOfBundleAndHardwarePromotions());
	 * Assert.assertNotNull(deviceDetails); }
	 * 
	 * @Test public void
	 * NotNullTestForDaoUtilsconvertGroupProductToProductGroupDetails() {
	 * List<ProductGroup> productGroup =
	 * DaoUtils.convertGroupProductToProductGroupDetails((CommonMethods.
	 * getProductGroupModel())); Assert.assertNotNull(productGroup); }
	 * 
	 * @Test public void NotNullTestForDaoUtilsgetPrice() {
	 * com.vf.uk.dal.device.entity.Price price =
	 * DaoUtils.getPriceFromGross(40.98); Assert.assertNotNull(price); }
	 * 
	 * @Test public void
	 * NotNullTestForDaoUtilsisCurrentDateBetweenStartAndEndDate() { boolean
	 * date =
	 * DaoUtils.isCurrentDateBetweenStartAndEndDate(Date.valueOf("2016-09-05"),
	 * Date.valueOf("2019-09-05")); Assert.assertNotNull(date); }
	 * 
	 * @Test public void
	 * NotNullTestForDaoUtilsconvertCommercialProductToInsurance() { Insurances
	 * insurances = DaoUtils.convertCommercialProductToInsurance(CommonMethods.
	 * getListOfCommercialProduct()); Assert.assertNotNull(insurances); }
	 *//**
		 * need to modify
		 *//*
		 * @Test public void
		 * NotNullTestForDaoUtilsconvertProductModelListToDeviceList() {
		 * Map<String, GroupDetails> productGroupdetailsMap = new HashMap<>();
		 * productGroupdetailsMap.put("093353",
		 * CommonMethods.getGroupDetails());
		 * productGroupdetailsMap.put("092660",
		 * CommonMethods.getGroupDetails()); Map<String,String>
		 * groupNameWithProdId=new HashMap<String, String>();
		 * groupNameWithProdId.put("Apple", "10936");
		 * groupNameWithProdId.put("Samsung","7630"); Map<String,
		 * List<OfferAppliedPriceModel>> listOfOfferAppliedPrice = new
		 * HashMap<>(); listOfOfferAppliedPrice.put("093353",
		 * Arrays.asList(CommonMethods.getOfferAppliedPriceModel().get(1)));
		 * listOfOfferAppliedPrice.put("092660",
		 * Arrays.asList(CommonMethods.getOfferAppliedPriceModel().get(0)));
		 * Map<String,Boolean> isLeadMemberFromSolr = new HashMap<>();
		 * isLeadMemberFromSolr.put("leadMember", true); FacetedDevice
		 * deviceList =
		 * DaoUtils.convertProductModelListToDeviceList(CommonMethods.
		 * getProductModel(), CommonMethods.getListOfProducts(),CommonMethods.
		 * getProductGroupFacetModel1().getListOfFacetsFields(),
		 * "DEVICE_PAYM",null,null,listOfOfferAppliedPrice,"W_HH_OC_02",
		 * groupNameWithProdId ,null,null,
		 * isLeadMemberFromSolr,listOfOfferAppliedPrice,"Upgrade",
		 * productGroupdetailsMap); Assert.assertNotNull(deviceList); }
		 * 
		 * @Test public void
		 * NotNullTestForDaoUtilsconvertProductModelListToDeviceList1() {
		 * Map<String, GroupDetails> productGroupdetailsMap = new HashMap<>();
		 * productGroupdetailsMap.put("093353",
		 * CommonMethods.getGroupDetails());
		 * productGroupdetailsMap.put("092660",
		 * CommonMethods.getGroupDetails()); Map<String,String>
		 * groupNameWithProdId=new HashMap<String, String>();
		 * groupNameWithProdId.put("Apple", "10936");
		 * groupNameWithProdId.put("Samsung","7630"); Map<String,
		 * List<OfferAppliedPriceModel>> listOfOfferAppliedPrice = new
		 * HashMap<>(); listOfOfferAppliedPrice.put("093353",
		 * Arrays.asList(CommonMethods.getOfferAppliedPriceModel().get(1)));
		 * listOfOfferAppliedPrice.put("092660",
		 * Arrays.asList(CommonMethods.getOfferAppliedPriceModel().get(0)));
		 * Map<String, List<OfferAppliedPriceModel>> listOfOfferAppliedPrice1 =
		 * new HashMap<>(); listOfOfferAppliedPrice1.put("093353", new
		 * ArrayList<>()); listOfOfferAppliedPrice1.put("092660", new
		 * ArrayList<>()); Map<String,Boolean> isLeadMemberFromSolr = new
		 * HashMap<>(); isLeadMemberFromSolr.put("leadMember", true);
		 * FacetedDevice deviceList =
		 * DaoUtils.convertProductModelListToDeviceList(CommonMethods.
		 * getProductModel(), CommonMethods.getListOfProducts(),CommonMethods.
		 * getProductGroupFacetModel1().getListOfFacetsFields(),
		 * "DEVICE_PAYM",null,null,listOfOfferAppliedPrice1,null,
		 * groupNameWithProdId ,null,null,
		 * isLeadMemberFromSolr,listOfOfferAppliedPrice,"Upgrade",
		 * productGroupdetailsMap); Assert.assertNotNull(deviceList); }
		 * 
		 * @Test public void
		 * NotNullTestForDaoUtilsconvertProductModelListToDeviceList2() {
		 * Map<String, GroupDetails> productGroupdetailsMap = new HashMap<>();
		 * productGroupdetailsMap.put("093353",
		 * CommonMethods.getGroupDetails());
		 * productGroupdetailsMap.put("092660",
		 * CommonMethods.getGroupDetails()); Map<String,String>
		 * groupNameWithProdId=new HashMap<String, String>();
		 * groupNameWithProdId.put("Apple", "10936");
		 * groupNameWithProdId.put("Samsung","7630"); Map<String,
		 * List<OfferAppliedPriceModel>> listOfOfferAppliedPrice1 = new
		 * HashMap<>(); listOfOfferAppliedPrice1.put("093353", new
		 * ArrayList<>()); listOfOfferAppliedPrice1.put("092660", new
		 * ArrayList<>()); Map<String,Boolean> isLeadMemberFromSolr = new
		 * HashMap<>(); isLeadMemberFromSolr.put("leadMember", true);
		 * FacetedDevice deviceList =
		 * DaoUtils.convertProductModelListToDeviceList(CommonMethods.
		 * getProductModel(), CommonMethods.getListOfProducts(),CommonMethods.
		 * getProductGroupFacetModel1().getListOfFacetsFields(),
		 * "DEVICE_PAYM",null,null,listOfOfferAppliedPrice1,null,
		 * groupNameWithProdId ,null,null,
		 * isLeadMemberFromSolr,listOfOfferAppliedPrice1,"Upgrade",
		 * productGroupdetailsMap); Assert.assertNotNull(deviceList); }
		 * 
		 * @Test public void
		 * NotNullTestForDaoUtilsconvertProductModelListToDeviceList3() {
		 * Map<String, GroupDetails> productGroupdetailsMap = new HashMap<>();
		 * productGroupdetailsMap.put("093353",
		 * CommonMethods.getGroupDetails());
		 * productGroupdetailsMap.put("092660",
		 * CommonMethods.getGroupDetails()); Map<String,String>
		 * groupNameWithProdId=new HashMap<String, String>();
		 * groupNameWithProdId.put("Apple", "10936");
		 * groupNameWithProdId.put("Samsung","7630"); Map<String,
		 * List<OfferAppliedPriceModel>> listOfOfferAppliedPrice1 = new
		 * HashMap<>(); listOfOfferAppliedPrice1.put("093353", new
		 * ArrayList<>()); listOfOfferAppliedPrice1.put("092660", new
		 * ArrayList<>()); Map<String,Boolean> isLeadMemberFromSolr = new
		 * HashMap<>(); isLeadMemberFromSolr.put("leadMember", true);
		 * FacetedDevice deviceList =
		 * DaoUtils.convertProductModelListToDeviceList(CommonMethods.
		 * getProductModel(), CommonMethods.getListOfProducts(),CommonMethods.
		 * getProductGroupFacetModel1().getListOfFacetsFields(),
		 * "DEVICE_PAYM",null,null,listOfOfferAppliedPrice1,null,
		 * groupNameWithProdId ,null,null,
		 * isLeadMemberFromSolr,listOfOfferAppliedPrice1,null,
		 * productGroupdetailsMap); Assert.assertNotNull(deviceList); }
		 * 
		 * @Test public void
		 * NotNullTestForDaoUtilsconvertProductModelListToDeviceList4() {
		 * Map<String, GroupDetails> productGroupdetailsMap = new HashMap<>();
		 * productGroupdetailsMap.put("093353",
		 * CommonMethods.getGroupDetails());
		 * productGroupdetailsMap.put("092660",
		 * CommonMethods.getGroupDetails()); Map<String,String>
		 * groupNameWithProdId=new HashMap<String, String>();
		 * groupNameWithProdId.put("Apple", "10936");
		 * groupNameWithProdId.put("Samsung","7630"); Map<String,
		 * List<OfferAppliedPriceModel>> listOfOfferAppliedPrice = new
		 * HashMap<>(); listOfOfferAppliedPrice.put("093353",
		 * Arrays.asList(CommonMethods.getOfferAppliedPriceModel().get(1)));
		 * listOfOfferAppliedPrice.put("092660",
		 * Arrays.asList(CommonMethods.getOfferAppliedPriceModel().get(0)));
		 * Map<String, List<OfferAppliedPriceModel>> listOfOfferAppliedPrice1 =
		 * new HashMap<>(); listOfOfferAppliedPrice1.put("093353", new
		 * ArrayList<>()); listOfOfferAppliedPrice1.put("092660", new
		 * ArrayList<>()); Map<String,Boolean> isLeadMemberFromSolr = new
		 * HashMap<>(); isLeadMemberFromSolr.put("leadMember", true);
		 * FacetedDevice deviceList =
		 * DaoUtils.convertProductModelListToDeviceList(CommonMethods.
		 * getProductModel(), CommonMethods.getListOfProducts(),CommonMethods.
		 * getProductGroupFacetModel1().getListOfFacetsFields(),
		 * "DEVICE_PAYM",null,null,listOfOfferAppliedPrice1,"W_HH_OC_02",
		 * groupNameWithProdId ,null,null,
		 * isLeadMemberFromSolr,listOfOfferAppliedPrice,"Upgrade",
		 * productGroupdetailsMap); Assert.assertNotNull(deviceList); }
		 * 
		 * @Test public void
		 * NotNullTestForDaoUtilsconvertProductModelListToDeviceList5() {
		 * Map<String, GroupDetails> productGroupdetailsMap = new HashMap<>();
		 * productGroupdetailsMap.put("093353",
		 * CommonMethods.getGroupDetails());
		 * productGroupdetailsMap.put("092660",
		 * CommonMethods.getGroupDetails()); Map<String,String>
		 * groupNameWithProdId=new HashMap<String, String>();
		 * groupNameWithProdId.put("Apple", "10936");
		 * groupNameWithProdId.put("Samsung","7630"); Map<String,
		 * List<OfferAppliedPriceModel>> listOfOfferAppliedPrice1 = new
		 * HashMap<>(); listOfOfferAppliedPrice1.put("093353", new
		 * ArrayList<>()); listOfOfferAppliedPrice1.put("092660", new
		 * ArrayList<>()); Map<String,Boolean> isLeadMemberFromSolr = new
		 * HashMap<>(); isLeadMemberFromSolr.put("leadMember", true);
		 * FacetedDevice deviceList =
		 * DaoUtils.convertProductModelListToDeviceList(CommonMethods.
		 * getProductModel(), CommonMethods.getListOfProducts(),CommonMethods.
		 * getProductGroupFacetModel1().getListOfFacetsFields(),
		 * "DEVICE_PAYM",null,null,listOfOfferAppliedPrice1,"W_HH_OC_02",
		 * groupNameWithProdId ,null,null,
		 * isLeadMemberFromSolr,listOfOfferAppliedPrice1,"Upgrade",
		 * productGroupdetailsMap); Assert.assertNotNull(deviceList); }
		 * 
		 * @Test public void
		 * NotNullTestForDaoUtilsconvertProductModelListToDeviceListPayG() {
		 * Map<String, GroupDetails> productGroupdetailsMap = new HashMap<>();
		 * productGroupdetailsMap.put("093353",
		 * CommonMethods.getGroupDetails());
		 * productGroupdetailsMap.put("092660",
		 * CommonMethods.getGroupDetails()); Map<String,String>
		 * groupNameWithProdId=new HashMap<String, String>();
		 * groupNameWithProdId.put("Apple", "10936");
		 * groupNameWithProdId.put("Samsung","7630"); Map<String,
		 * List<OfferAppliedPriceModel>> listOfOfferAppliedPrice1 = new
		 * HashMap<>(); listOfOfferAppliedPrice1.put("093353", new
		 * ArrayList<>()); listOfOfferAppliedPrice1.put("092660", new
		 * ArrayList<>()); Map<String,Boolean> isLeadMemberFromSolr = new
		 * HashMap<>(); isLeadMemberFromSolr.put("leadMember", true);
		 * FacetedDevice deviceList =
		 * DaoUtils.convertProductModelListToDeviceList(CommonMethods.
		 * getProductModel(), CommonMethods.getListOfProducts(),CommonMethods.
		 * getProductGroupFacetModel1().getListOfFacetsFields(),
		 * "DEVICE_PAYG",null,null,null,null, groupNameWithProdId ,null,null,
		 * isLeadMemberFromSolr,null,Constants.JOURNEY_TYPE_ACQUISITION,
		 * productGroupdetailsMap); Assert.assertNotNull(deviceList); }
		 * 
		 * @Test public void
		 * NotNullTestForDaoUtilsconvertProductModelListToDeviceList_Data_PAYG()
		 * { Map<String,String> groupNameWithProdId=new HashMap<String,
		 * String>(); groupNameWithProdId.put("Apple", "10936");
		 * groupNameWithProdId.put("Samsung","7630"); FacetedDevice deviceList =
		 * DaoUtils.convertProductModelListToDeviceList(CommonMethods.
		 * getProductModel(),CommonMethods.getListOfProducts(),
		 * groupNameWithProdId ,getProductGroupFacetModel1,CommonMethods.
		 * getPriceForBundleAndHardware(),null,
		 * "DEVICE_PAYG",CommonMethods.getListofdataPayGCoherence());
		 * Assert.assertNotNull(deviceList); }
		 * 
		 * 
		 * @Test public void NotNullTestForDeviceListSort() {
		 * given(this.deviceDAOMock.getDeviceList("HANDSET","apple",
		 * "iPhone-7","DEVICE_PAYM", "Priority", 1, 2,"32 GB","White","iOS",
		 * "Great Camera"
		 * )).willReturn(CommonMethods.getFacetedDeviceForSorting("HANDSET",
		 * "apple", "iPhone 7", "DEVICE_PAYM", "asc", 1, 2,"123"));
		 * 
		 * FacetedDevice
		 * deviceLists=deviceService.getDeviceList("HANDSET","apple",
		 * "iPhone-7","DEVICE_PAYM", "Priority", 1, 2,"32 GB","White","iOS",
		 * "Great Camera","c1a42269-6562-4c96-b3be-1ca2a6681d57");
		 * Assert.assertNotNull(deviceLists); }
		 * 
		 * @Test public void exceptionTestForCommonJourney() {
		 * given(this.deviceDAOMock.getDeviceList("HANDSET","apple",
		 * "iPhone-7","DEVICE_PAYM", "Priority", 1, 2,"32 GB","White","iOS",
		 * "Great Camera"
		 * )).willReturn(CommonMethods.getFacetedDeviceForSorting("HANDSET",
		 * "apple", "iPhone 7", "DEVICE_PAYM", "asc", 1, 2,"123"));
		 * given(restTemplate.getForObject(
		 * "http://COMMON-V1/common/journey/c1a42269-6562-4c96-b3be-1ca2a6681d57/queries/currentJourney"
		 * ,CurrentJourney.class)).willThrow(new ApplicationException("")); try
		 * { deviceService.getDeviceList("HANDSET","apple",
		 * "iPhone-7","DEVICE_PAYM", "Priority", 1, 2,"32 GB","White","iOS",
		 * "Great Camera","c1a42269-6562-4c96-b3be-1ca2a6681d57"); } catch
		 * (Exception e) { Assert.assertEquals(
		 * "Invalid Journey Id sent in the request",e.getMessage()); }
		 * 
		 * }
		 * 
		 * @Test public void exceptionTestForrecommodation() { List<String>
		 * listOfRecommendedProductTypes; RecommendedProductListRequest
		 * recomProductListReq = new RecommendedProductListRequest();
		 * listOfRecommendedProductTypes = new ArrayList<>();
		 * listOfRecommendedProductTypes.add(Constants.STRING_DEVICE);
		 * recomProductListReq.setSerialNumber("07003145001");
		 * recomProductListReq.setRecommendedProductTypes(
		 * listOfRecommendedProductTypes);
		 * given(this.deviceDAOMock.getDeviceList("HANDSET","apple",
		 * "iPhone-7","DEVICE_PAYM", "Priority", 1, 2,"32 GB","White","iOS",
		 * "Great Camera"
		 * )).willReturn(CommonMethods.getFacetedDeviceForSorting("HANDSET",
		 * "apple", "iPhone 7", "DEVICE_PAYM", "asc", 1, 2,"123"));
		 * given(restTemplate.postForObject(
		 * "http://CUSTOMER-V1/customer/getRecommendedProductList/",
		 * recomProductListReq,RecommendedProductListResponse.class)).willThrow(
		 * new ApplicationException("")); try {
		 * deviceService.getDeviceList("HANDSET","apple",
		 * "iPhone-7","DEVICE_PAYM", "Priority", 1, 2,"32 GB","White","iOS",
		 * "Great Camera","c1a42269-6562-4c96-b3be-1ca2a6681d57"); } catch
		 * (Exception e) { Assert.assertEquals(
		 * "Exception occured while connecting to GRPL API",e.getMessage()); }
		 * 
		 * }
		 * 
		 * @Test public void testForStock() { List<StockInfo>
		 * listofStock=CommonUtility.getStockAvailabilityForDevice("093353",
		 * registry); Assert.assertNull(listofStock); }
		 * 
		 * @Test public void testForGetBundleDetailsFromComplansListingAPI() {
		 * BundleDetails bundleDetails =
		 * CommonUtility.getBundleDetailsFromComplansListingAPI("093353",
		 * "priority", registry); Assert.assertNull(bundleDetails); }
		 * 
		 * public void
		 * testForGetBundleDetailsFromComplansListingAPIForException() {
		 * given(restTemplate.getForObject(
		 * "http://BUNDLES-V1/bundles/catalogue/bundle/queries/byDeviceId/093353//?sort=priority"
		 * ,BundleDetails.class)).willThrow(new ApplicationException(""));
		 * CommonUtility.getBundleDetailsFromComplansListingAPI("093353",
		 * "priority", registry); }
		 * 
		 * @Test public void testForGetPriceDetails() {
		 * List<BundleAndHardwareTuple> bundleAndHardwareTupleList=new
		 * ArrayList<>(); BundleAndHardwareTuple bht= new
		 * BundleAndHardwareTuple(); bht.setBundleId("110154");
		 * bht.setHardwareId("093353"); bundleAndHardwareTupleList.add(bht);
		 * List<PriceForBundleAndHardware>
		 * listOfPriceForBundleAndHardware=CommonUtility.getPriceDetails(
		 * bundleAndHardwareTupleList,null,registry,null);
		 * Assert.assertNull(listOfPriceForBundleAndHardware); }
		 * 
		 * @Test public void testForGetPriceDetailsForCompatibaleBundle() {
		 * BundleDetailsForAppSrv bdfas=
		 * CommonUtility.getPriceDetailsForCompatibaleBundle("093353",null,
		 * registry); Assert.assertNull(bdfas); }
		 * 
		 * @Test public void
		 * testForConvertBundleHeaderForDeviceToProductGroupForDeviceListing() {
		 * Map<String,
		 * List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>>
		 * iLSPriceMap =new HashMap<>(); iLSPriceMap.put("093353",
		 * CommonMethods.getOfferAppliedPrice()); DevicePreCalculatedData
		 * productGroupForDeviceListing=DaoUtils
		 * .convertBundleHeaderForDeviceToProductGroupForDeviceListing("093353",
		 * "leadPlanId","groupname" ,"groupId", CommonMethods.getPrice(),
		 * CommonMethods.getleadMemberMap(),iLSPriceMap,CommonMethods.
		 * getleadMemberMap(),"upgradeLeadPlanId",Constants.STRING_DEVICE_PAYM);
		 * 
		 * Assert.assertNotNull(productGroupForDeviceListing); }
		 * 
		 * @Test public void
		 * testForConvertBundleHeaderForDeviceToProductGroupForDeviceListingNullLeadPlanId
		 * () { Map<String,
		 * List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>>
		 * iLSPriceMap =new HashMap<>(); iLSPriceMap.put("093353",
		 * CommonMethods.getOfferAppliedPrice()); DevicePreCalculatedData
		 * productGroupForDeviceListing=DaoUtils
		 * .convertBundleHeaderForDeviceToProductGroupForDeviceListing("093353",
		 * null,"groupname" ,"groupId",
		 * CommonMethods.getPrice(),CommonMethods.getleadMemberMap(),iLSPriceMap
		 * ,CommonMethods.getleadMemberMap(),null,Constants.STRING_DEVICE_PAYG);
		 * 
		 * Assert.assertNotNull(productGroupForDeviceListing); }
		 * 
		 * @Test(expected=Exception.class) public void
		 * testForConvertBundleHeaderForDeviceToProductGroupForDeviceListingException
		 * () { Map<String,
		 * List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>>
		 * iLSPriceMap =new HashMap<>(); iLSPriceMap.put("093353",
		 * CommonMethods.getOfferAppliedPrice()); DaoUtils
		 * .convertBundleHeaderForDeviceToProductGroupForDeviceListing("093353",
		 * Float.valueOf("2.0"),"leadPlanId","groupname" ,"groupId",
		 * CommonMethods.getPriceForBundleAndHardware(),iLSPriceMap);
		 * 
		 * }
		 * 
		 * @Test public void notNullTestForGetListDeviceDetails() {
		 * 
		 * List<DeviceDetails> deviceDetails;
		 * deviceDetails=deviceService.getListOfDeviceDetails("093353",null,null
		 * ); Assert.assertNotNull(deviceDetails); }
		 * 
		 * @Test public void notNullTestForGetListOfDeviceDetais() {
		 * List<DeviceDetails> deviceDetails = null; try{
		 * deviceDetails=deviceService.getListOfDeviceDetails("83929",null,null)
		 * ; }catch(Exception e){
		 * 
		 * } Assert.assertNotNull(deviceDetails); }
		 * 
		 * @Test public void
		 * notNullMultipleDevicesTestForGetListOfDeviceDetais() {
		 * List<DeviceDetails> deviceDetails;
		 * deviceDetails=deviceService.getListOfDeviceDetails("093353,093427",
		 * null,null); Assert.assertNotNull(deviceDetails); }
		 * 
		 * @Test public void
		 * notNullInvalidLeadPlanIdTestForGetListOfDeviceDetais() {
		 * List<DeviceDetails> deviceDetails;
		 * deviceDetails=deviceService.getListOfDeviceDetails("093311",null,null
		 * ); Assert.assertNotNull(deviceDetails); }
		 * 
		 * @Test public void testForGetCurrentJourney() { //CurrentJourney
		 * cj=CommonUtility.getCurrentJourney(
		 * "a1e2be6b-4c7a-4d6b-a621-f22e3266e651",registry);
		 * //Assert.assertNull(cj); }
		 * 
		 * @Test public void testGetSubscriptionBundleId() {
		 * 
		 * String subscriptionId = "07741655541"; String subscriptionType =
		 * "msisdn";
		 * 
		 * Mockito.when(registry.getRestTemplate()).thenReturn(restTemplate);
		 * String url = "http://CUSTOMER-V1/customer/subscription/" +
		 * subscriptionType + ":" + subscriptionId + "/sourcePackageSummary";
		 * 
		 * 
		 * given(restTemplate.getForObject(url,
		 * SourcePackageSummary.class)).willReturn(CommonMethods.
		 * getSourcePackageSummary()); String deviceId =
		 * CommonUtility.getSubscriptionBundleId(subscriptionId,
		 * Constants.SUBSCRIPTION_TYPE_MSISDN, registry);
		 * Assert.assertEquals("109381", deviceId); }
		 * 
		 * @Test public void testGetDeviceListFromPricing() {
		 * 
		 * try { deviceService.getDeviceListFromPricing("DEVICE_PAYM"); }
		 * catch(Exception e) { System.out.println(e.toString()); } }
		 * 
		 * @Test public void testGetDeviceListFromPricingForNullLeadPlanId() {
		 * given(registry.getRestTemplate()).willReturn(restTemplate);
		 * RequestForBundleAndHardware requestForBundleAndHardware=new
		 * RequestForBundleAndHardware(); List<BundleAndHardwareTuple>
		 * bundleAndHardwareTupleList = new ArrayList<>();
		 * BundleAndHardwareTuple bundleAndHardwareTuple = new
		 * BundleAndHardwareTuple();
		 * bundleAndHardwareTuple.setBundleId("110154");
		 * bundleAndHardwareTuple.setHardwareId("093353");
		 * bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
		 * bundleAndHardwareTuple = new BundleAndHardwareTuple();
		 * bundleAndHardwareTuple.setBundleId("110154");
		 * bundleAndHardwareTuple.setHardwareId("123");
		 * bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
		 * requestForBundleAndHardware.setBundleAndHardwareList(
		 * bundleAndHardwareTupleList);
		 * requestForBundleAndHardware.setOfferCode(null);
		 * 
		 * 
		 * 
		 * ObjectMapper mapper=new ObjectMapper();
		 * mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		 * mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		 * String jsonString=""; try { jsonString = new
		 * String(Utility.readFile("\\rest-mock\\PRICE-V1.json"));
		 * com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[]
		 * obj=mapper.readValue(jsonString,
		 * com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[].class);
		 * given(restTemplate.postForObject(
		 * "http://PRICE-V1/price/calculateForBundleAndHardware"
		 * ,requestForBundleAndHardware,com.vf.uk.dal.utility.entity.
		 * PriceForBundleAndHardware[].class)).willReturn(obj);
		 * 
		 * RequestForBundleAndHardware requestForBundleAndHardwarelocal=new
		 * RequestForBundleAndHardware();
		 * 
		 * List<BundleAndHardwareTuple> bundleAndHardwareTupleListlocal = new
		 * ArrayList<>(); BundleAndHardwareTuple bundleAndHardwareTuplelocal =
		 * new BundleAndHardwareTuple();
		 * bundleAndHardwareTuplelocal.setBundleId("123");
		 * bundleAndHardwareTuplelocal.setHardwareId("93353");
		 * bundleAndHardwareTupleListlocal.add(bundleAndHardwareTuplelocal);
		 * bundleAndHardwareTuplelocal = new BundleAndHardwareTuple();
		 * bundleAndHardwareTuplelocal.setBundleId("456");
		 * bundleAndHardwareTuplelocal.setHardwareId("93353");
		 * bundleAndHardwareTupleListlocal.add(bundleAndHardwareTuplelocal);
		 * bundleAndHardwareTuplelocal = new BundleAndHardwareTuple();
		 * bundleAndHardwareTuplelocal.setBundleId("789");
		 * bundleAndHardwareTuplelocal.setHardwareId("93353");
		 * bundleAndHardwareTupleListlocal.add(bundleAndHardwareTuplelocal);
		 * bundleAndHardwareTuplelocal = new BundleAndHardwareTuple();
		 * bundleAndHardwareTuplelocal.setBundleId("101112");
		 * bundleAndHardwareTuplelocal.setHardwareId("93353");
		 * bundleAndHardwareTupleListlocal.add(bundleAndHardwareTuplelocal);
		 * bundleAndHardwareTuplelocal = new BundleAndHardwareTuple();
		 * bundleAndHardwareTuplelocal.setBundleId("131415");
		 * bundleAndHardwareTuplelocal.setHardwareId("93353");
		 * bundleAndHardwareTupleListlocal.add(bundleAndHardwareTuplelocal);
		 * requestForBundleAndHardwarelocal.setBundleAndHardwareList(
		 * bundleAndHardwareTupleListlocal);
		 * requestForBundleAndHardwarelocal.setOfferCode(null);
		 * 
		 * given(restTemplate.postForObject(
		 * "http://PRICE-V1/price/calculateForBundleAndHardware"
		 * ,requestForBundleAndHardwarelocal,com.vf.uk.dal.utility.entity.
		 * PriceForBundleAndHardware[].class)).willReturn(obj);
		 * 
		 * deviceService.getDeviceListFromPricing("DEVICE_PAYM"); }
		 * catch(Exception e) { } }
		 * 
		 * @Test public void testSolrMock() { ConfigHelper configHelper =
		 * Mockito.mock(ConfigHelper.class); SolrConnectionProvider
		 * solrConnectionProvider = new SolrConnectionProvider(); try {
		 * 
		 * solrConnectionProvider.getSolrConnection();
		 * 
		 * } catch(Exception e) { try {
		 * solrConnectionProvider.closeSolrConnection(); } catch(Exception te) {
		 * 
		 * } } }
		 * 
		 * @Test public void testGetDeviceListForErrors(){
		 * 
		 * try { deviceService.getDeviceList("productClass", "listOfMake",
		 * "model", "groupType", null,2, 2, "capacity", "colour",
		 * "operatingSystem", "mustHaveFeatures", "journeyType", (float) 14.0,
		 * "offerCode", "msisdn", false); } catch(Exception e) { try {
		 * deviceService.getDeviceList(null, "listOfMake", "model", "groupType",
		 * "Sort",2, 2, "capacity", "colour", "operatingSystem",
		 * "mustHaveFeatures", "journeyType", (float) 14.0, "offerCode",
		 * "msisdn", false); } catch(Exception ew) { try {
		 * deviceService.getDeviceList("PC", "listOfMake", "model", "groupType",
		 * "Sort",2, 2, "capacity", "colour", "operatingSystem",
		 * "mustHaveFeatures", "journeyType", (float) 14.0, "offerCode",
		 * "msisdn", false); } catch(Exception ed) { try {
		 * deviceService.getDeviceList("HANDSET", "listOfMake", "model",
		 * "ksjdbhf", "Sort",2, 2, "capacity", "colour", "operatingSystem",
		 * "mustHaveFeatures", "journeyType", (float) 14.0, "offerCode",
		 * "msisdn", false); } catch(Exception er) { try {
		 * deviceService.getDeviceList("HANDSET", "listOfMake", "model",
		 * "DEVICE_PAYM", "Sort",2, 2, "capacity", "colour", "operatingSystem",
		 * "mustHaveFeatures", "journeyType", (float) 14.0, "offerCode",
		 * "msisdn", false); } catch(Exception eq) { try {
		 * deviceService.getDeviceList("HANDSET", "listOfMake", "model", null,
		 * "Sort",2, 2, "capacity", "colour", "operatingSystem",
		 * "mustHaveFeatures", "journeyType", (float) 14.0, "offerCode",
		 * "msisdn", false); } catch(Exception ek) { try {
		 * deviceService.getDeviceList("HANDSET", "listOfMake", "model",
		 * "DEVICE_PAYM", "Sort",2, 2, "capacity", "colour", "operatingSystem",
		 * "mustHaveFeatures", "journeyType", (float) 14.0, "offerCode", "",
		 * true); } catch(Exception lkj) { try {
		 * deviceService.getDeviceList("HANDSET", "listOfMake", "model",
		 * "DEVICE_PAYM", "Sort",2, 2, "capacity", "colour", "operatingSystem",
		 * "mustHaveFeatures", "conditionalaccept", (float) 14.0, "offerCode",
		 * "msisdn", true); } catch(Exception edfg) { try {
		 * deviceService.getDeviceList(null, "listOfMake", "model",
		 * "DEVICE_PAYM", "Rating",2, 2, "capacity", "colour",
		 * "operatingSystem", "mustHaveFeatures", "conditionalaccept", (float)
		 * 14.0, "offerCode", "msisdn", true); } catch(Exception ex) { try {
		 * deviceService.getDeviceList("HandsetHandset", "listOfMake", "model",
		 * "DEVICE_PAYM", "Rating",2, 2, "capacity", "colour",
		 * "operatingSystem", "mustHaveFeatures", "conditionalaccept", (float)
		 * 14.0, "offerCode", "msisdn", true); } catch(Exception ex1) { try {
		 * deviceService.getDeviceList("Handset", "listOfMake", "model",
		 * "DEVICE_PAYMDEVICE_PAYM", "Rating",2, 2, "capacity", "colour",
		 * "operatingSystem", "mustHaveFeatures", "conditionalaccept", (float)
		 * 14.0, "offerCode", "msisdn", true); } catch(Exception ex2) { try {
		 * deviceService.getDeviceList("Handset", "listOfMake", null,
		 * "DEVICE_PAYM", "Rating",2, 2, "capacity", "colour",
		 * "operatingSystem", "mustHaveFeatures", "conditionalaccept", (float)
		 * 14.0, "offerCode", "msisdn", true); } catch(Exception ex3) { try {
		 * deviceService.getDeviceList("Handset", "listOfMake", "model",
		 * "DEVICE_PAYM", "Rating",2, 2, "capacity", "colour",
		 * "operatingSystem", "mustHaveFeatures", null, (float) 14.0,
		 * "offerCode", "msisdn", true); } catch(Exception ex4) { try {
		 * deviceService.getDeviceList("Handset", "listOfMake", "model",
		 * "DEVICE_PAYM", "Rating",2, 2, "capacity", "colour",
		 * "operatingSystem", "mustHaveFeatures", "conditionalaccept", (float)
		 * 14.0, "offerCode", null, true); } catch(Exception ex5) {
		 * 
		 * } } } } }
		 * 
		 * } }
		 * 
		 * } } } } } } } }
		 * 
		 * @Test public void testgetListOfMembers() { try { List<String> list =
		 * new ArrayList<>(); list.add("abd|def");
		 * deviceService.getListOfMembers(list); } catch(Exception e) {
		 * 
		 * } }
		 * 
		 * @Test public void testgetMemeberBasedOnRules1() {
		 * List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember =
		 * new ArrayList<>(); com.vf.uk.dal.device.entity.Member member = new
		 * Member(); member.setId("12345"); member.setPriority("1");
		 * listOfDeviceGroupMember.add(member); try { List<String> list = new
		 * ArrayList<>(); list.add("abd|def");
		 * deviceService.getMemeberBasedOnRules1(listOfDeviceGroupMember,
		 * "Upgrade"); } catch(Exception e) {
		 * 
		 * } }
		 * 
		 * @Test public void testvalidateMemeber1() {
		 * given(this.response.getListOfProductModel(Matchers.anyObject())).
		 * willReturn(CommonMethods.getProductModel()); try {
		 * deviceService.validateMemeber1("12345","Upgrade"); } catch(Exception
		 * e) {
		 * 
		 * } }
		 * 
		 * @Test public void testgetDeviceListForConditionalAccept() {
		 * ProductGroupFacetModel productGroupFacetModel =
		 * CommonMethods.getProductGroupFacetModel();
		 * productGroupFacetModel.setListOfProductGroups(CommonMethods.
		 * getProductGroupModel());
		 * given(response.getListOfProductGroupModel(Matchers.anyObject())).
		 * willReturn(CommonMethods.getListOfProductGroupMode());
		 * given(this.response.getFacetField(Matchers.anyObject())).willReturn(
		 * CommonMethods.getListOfFacetField()); try {
		 * deviceService.getDeviceListForConditionalAccept("HANDSET", "make",
		 * "model", "DEVICE_PAYM", "Order", 5, 6, "12", "red", "os",
		 * "somefeature", (float) 500.00,""); } catch(Exception e) {
		 * 
		 * } }
		 * 
		 * @Test public void testgetFilterForDeviceList() { try {
		 * deviceService.getFilterForDeviceList("Filter,filter,filter", "R"); }
		 * catch(Exception e) {
		 * 
		 * } }
		 * 
		 * @Test public void notNullGetLeadPlanIdForDeviceId()throws IOException
		 * { CommercialProduct
		 * commercialProduct=CommonMethods.getCommercialProduct5();
		 * commercialProduct.setLeadPlanId(null); String jsonString=new
		 * String(Utility.readFile("\\rest-mock\\BUNDLES-V1.json"));
		 * ObjectMapper mapper1=new ObjectMapper();
		 * mapper1.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		 * mapper1.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
		 * false); BundleDetailsForAppSrv obj=mapper1.readValue(jsonString,
		 * BundleDetailsForAppSrv.class);
		 * given(registry.getRestTemplate()).willReturn(restTemplate);
		 * given(restTemplate.getForObject(
		 * "http://BUNDLES-V1/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId=123&journeyType=Upgrade",
		 * BundleDetailsForAppSrv.class )).willReturn(obj);
		 * Assert.assertNotNull(deviceService.getLeadPlanIdForDeviceId("123",
		 * "Upgrade")); }
		 * 
		 * @Test public void notNullGetLeadPlanIdForDeviceId1()throws
		 * IOException { CommercialProduct
		 * commercialProduct=CommonMethods.getCommercialProduct5(); String
		 * jsonString=new
		 * String(Utility.readFile("\\rest-mock\\BUNDLES-V1.json"));
		 * ObjectMapper mapper1=new ObjectMapper();
		 * mapper1.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		 * mapper1.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
		 * false); BundleDetailsForAppSrv obj=mapper1.readValue(jsonString,
		 * BundleDetailsForAppSrv.class);
		 * given(registry.getRestTemplate()).willReturn(restTemplate);
		 * given(restTemplate.getForObject(
		 * "http://BUNDLES-V1/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId=123&journeyType=Upgrade",
		 * BundleDetailsForAppSrv.class )).willReturn(obj);
		 * Assert.assertNotNull(deviceService.getLeadPlanIdForDeviceId("123",
		 * "Upgrade")); }
		 * 
		 * @Test public void notNullValidateMemeberForUpgrade()throws
		 * IOException { CommercialProduct
		 * commercialProduct=CommonMethods.getCommercialProduct5();
		 * Assert.assertNotNull(deviceService.validateMemeber("123","Upgrade"));
		 * }
		 * 
		 * @Test public void notNullValidateMemeber()throws IOException {
		 * CommercialProduct
		 * commercialProduct=CommonMethods.getCommercialProduct5();
		 * Assert.assertNotNull(deviceService.validateMemeber("123",
		 * "Acquisition")); }
		 * 
		 * @Test public void notNullValidateMemeberForException() {
		 * Assert.assertNotNull(deviceService.validateMemeber("123",null)); }
		 * 
		 * @Test public void testCommonUtilitygetCurrentJourney() { try {
		 * CommonUtility.getCurrentJourney("SomeID", registry); }
		 * catch(Exception e) {
		 * 
		 * } }
		 * 
		 * @Test public void testGetPriceDetailsForCompatibaleBundle() {
		 * Mockito.doThrow(new
		 * ApplicationException("Exception")).when(registry).getRestTemplate();
		 * try {
		 * CommonUtility.getPriceDetailsForCompatibaleBundle("093353",null,
		 * registry); } catch(Exception e) {
		 * 
		 * } }
		 * 
		 * @Test public void testGetJSONFromString() { try {
		 * CommonUtility.getJSONFromString(
		 * "{ \"name\":\"John\", \"age\":31, \"city\":\"New York\" }"); }
		 * catch(Exception e) {
		 * 
		 * } }
		 * 
		 * @Test public void testGetJSONFromStringException() { try {
		 * CommonUtility.getJSONFromString("one':'two'"); } catch(Exception e) {
		 * 
		 * }
		 * 
		 * }
		 * 
		 * @Test public void testtrimLeadingZeros() { try {
		 * CommonUtility.trimLeadingZeros("000353"); } catch(Exception e) {
		 * 
		 * } }
		 * 
		 * @Test public void testtrimLeadingZerosOne() { try {
		 * CommonUtility.trimLeadingZeros("1"); } catch(Exception e) {
		 * 
		 * } }
		 * 
		 * @Test public void testtrimLeadingZerosTwo() { try {
		 * CommonUtility.trimLeadingZeros("000"); } catch(Exception e) {
		 * 
		 * } }
		 * 
		 * @Test public void testgetAccessoryPriceDetails() {
		 * BundleDeviceAndProductsList deviceAndProductsList = new
		 * BundleDeviceAndProductsList();
		 * deviceAndProductsList.setAccessoryList(CommonMethods.
		 * getListOfProducts()); deviceAndProductsList.setBundleId("110154");
		 * deviceAndProductsList.setDeviceId("093353");
		 * deviceAndProductsList.setExtraList(CommonMethods.getListOfProducts())
		 * ; given(registry.getRestTemplate()).willReturn(restTemplate);
		 * PriceForProduct price = new PriceForProduct();
		 * price.setPriceForAccessoryes(null); price.setPriceForExtras(null);
		 * given(restTemplate.postForObject("http://PRICE-V1/price/product"
		 * ,deviceAndProductsList,PriceForProduct.class)).willReturn(price);
		 * try{ CommonUtility.getAccessoryPriceDetails(deviceAndProductsList,
		 * registry); } catch(Exception e) { try { Mockito.doThrow(new
		 * ApplicationException("Exception")).when(restTemplate).postForObject(
		 * "http://PRICE-V1/price/product"
		 * ,deviceAndProductsList,PriceForProduct.class);
		 * 
		 * CommonUtility.getAccessoryPriceDetails(deviceAndProductsList,
		 * registry); } catch(Exception et) {
		 * 
		 * } } }
		 * 
		 * @Test public void testGetAccessoriesOfDeviceFor93353() { try {
		 * deviceService.getAccessoriesOfDevice("093353","Upgrade",
		 * "W_HH_PAYM_OC_02"); } catch(Exception e) {
		 * 
		 * } }
		 * 
		 * @Test public void
		 * nullTestForGetDeviceListForGroupTypeWithOutConditionalAcceptance() {
		 * given(deviceDAOMock.getProductGroupsWithFacets(Matchers.any(),
		 * Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(),
		 * Matchers.any(),Matchers.any())).willReturn(CommonMethods.
		 * getProductGroupFacetModel1());
		 * given(response.getListOfProductGroupModel(Matchers.anyObject())).
		 * willReturn(CommonMethods.getListOfProductGroupMode());
		 * //given(deviceDAOMock.getProductGroupsWithFacets(Filters.HANDSET,
		 * "Upgrade")).willReturn(CommonMethods.getProductGroupFacetModel1());
		 * given(response.getFacetField(Matchers.anyObject())).willReturn(
		 * CommonMethods.getListOfFacetField());
		 * given(response.getListOfProductModel(Matchers.anyObject())).
		 * willReturn(CommonMethods.getProductModel());
		 * 
		 * FacetedDevice deviceLists=null; try{
		 * deviceLists=deviceService.getDeviceList("Handset","apple", "iPhone 7"
		 * , "DEVICE_PAYM", "Priority", 0, 9,"32 GB","White","iOS",
		 * "Great Camera",null,null,null, "447582367723", true); }
		 * catch(Exception e) {
		 * 
		 * } Assert.assertNotNull(deviceLists); }
		 * 
		 * @Test public void
		 * nullTestForGetDeviceListForGroupTypeWithOutConditionalAcceptance1() {
		 * given(deviceDAOMock.getProductGroupsWithFacets(Matchers.any(),
		 * Matchers.any(), Matchers.any(), Matchers.any(), Matchers.any(),
		 * Matchers.any(),Matchers.any())).willReturn(CommonMethods.
		 * getProductGroupFacetModel1());
		 * given(response.getListOfProductGroupModel(Matchers.anyObject())).
		 * willReturn(CommonMethods.getListOfProductGroupMode());
		 * //given(deviceDAOMock.getProductGroupsWithFacets(Filters.HANDSET,
		 * "SecondLine")).willReturn(CommonMethods.getProductGroupFacetModel1())
		 * ; given(response.getFacetField(Matchers.anyObject())).willReturn(
		 * CommonMethods.getListOfFacetField());
		 * given(response.getListOfProductModel(Matchers.anyObject())).
		 * willReturn(CommonMethods.getProductModel());
		 * given(response.getListOfOfferAppliedPriceModel(Matchers.anyObject()))
		 * .willReturn(CommonMethods.getOfferAppliedPriceModel());
		 * given(this.response.getListOfMerchandisingPromotionModelFromJson(
		 * Matchers.anyObject())).willReturn(CommonMethods.
		 * getMerChandisingPromotion1());
		 * 
		 * 
		 * FacetedDevice deviceLists=null; try{
		 * deviceLists=deviceService.getDeviceList("Handset","apple", "iPhone 7"
		 * , "DEVICE_PAYM", "Priority", 0, 9,"32 GB","White","iOS",
		 * "Great Camera","Upgrade",null,"W_HH_PAYM_OC_01", "447582367723",
		 * true); } catch(Exception e) {
		 * 
		 * } //Assert.assertNotNull(deviceLists); }
		 * 
		 * @Test(expected=Exception.class) public void
		 * nullTestForGetDeviceListForExcption() {
		 * given(this.response.getListOfMerchandisingPromotionModelFromJson(
		 * Matchers.anyObject())).willReturn(CommonMethods.
		 * getMerChandisingPromotion());
		 * deviceService.getDeviceList("Handset","apple", "iPhone 7",
		 * "DEVICE_PAYM", "Priority", 0, 9,"32 GB","White","iOS","Great Camera"
		 * ,"JourneyType",null,null, "447582367723", true);
		 * 
		 * }
		 * 
		 * @Test(expected=Exception.class) public void
		 * nullTestForGetDeviceListForExcption1() {
		 * given(this.response.getListOfMerchandisingPromotionModelFromJson(
		 * Matchers.anyObject())).willReturn(CommonMethods.
		 * getMerChandisingPromotion());
		 * 
		 * deviceService.getDeviceList("Handset","apple", "iPhone 7",
		 * "DEVICE_PAYM", "Priority", 0, 9,"32 GB","White","iOS","Great Camera"
		 * ,"Upgrade",null,"offerCode", "447582367723", true);
		 * 
		 * }
		 * 
		 * @Test(expected=Exception.class) public void
		 * nullTestForGetDeviceListForExcption3() {
		 * given(this.response.getListOfMerchandisingPromotionModelFromJson(
		 * Matchers.anyObject())).willReturn(CommonMethods.
		 * getMerChandisingPromotion());
		 * deviceService.getDeviceList("Handset","apple", "iPhone 7",
		 * "DEVICE_PAYM", "Priority", 0, 9,"32 GB","White","iOS","Great Camera"
		 * ,"SecondLine",null,"offerCode", "447582367723", true);
		 * 
		 * }
		 * 
		 * @Test(expected=Exception.class) public void
		 * nullTestForGetDeviceListForExcption4() {
		 * given(this.response.getListOfMerchandisingPromotionModelFromJson(
		 * Matchers.anyObject())).willReturn(CommonMethods.
		 * getMerChandisingPromotion());
		 * deviceService.getDeviceList("Handset","apple", "iPhone 7",
		 * "DEVICE_PAYM", "Priority", 0, 9,"32 GB","White","iOS","Great Camera"
		 * ,"SecondLine",null,"W_HH_PAYM_02", null, true);
		 * 
		 * }
		 * 
		 * @Test public void nullTestIsValidBundleForProductUpgrade() {
		 * Map<String,CommercialBundle> commercialBundleMap=new HashMap<>();
		 * commercialBundleMap.put("110154",
		 * CommonMethods.getCommercialBundle()); List<String> productLinesList =
		 * new ArrayList<>(); String upgrade="Upgrade";
		 * productLinesList.add(Constants.STRING_MOBILE_PHONE_SERVICE_SELLABLE);
		 * productLinesList.add(Constants.STRING_MBB_SELLABLE);
		 * Assert.assertNotNull(CommonUtility.isValidBundleForProduct(
		 * CommonMethods.getUtilityPriceForBundleAndHardware(),
		 * commercialBundleMap,productLinesList,upgrade)); }
		 * 
		 * @Test public void nullTestIsValidBundleForProduct() {
		 * Map<String,CommercialBundle> commercialBundleMap=new HashMap<>();
		 * commercialBundleMap.put("110154",
		 * CommonMethods.getCommercialBundle()); List<String> productLinesList =
		 * new ArrayList<>(); String Acquistion="Acquistion";
		 * productLinesList.add(Constants.STRING_MOBILE_PHONE_SERVICE_SELLABLE);
		 * productLinesList.add(Constants.STRING_MBB_SELLABLE);
		 * Assert.assertNotNull(CommonUtility.isValidBundleForProduct(
		 * CommonMethods.getUtilityPriceForBundleAndHardware(),
		 * commercialBundleMap,productLinesList,Acquistion)); }
		 * 
		 * @Test public void
		 * getBundlePriceBasedOnDiscountDuration_Implementation(){ DeviceSummary
		 * deviceSummary = DaoUtils.convertCoherenceDeviceToDeviceTile((long)1,
		 * CommonMethods.getCommercialProduct(),CommonMethods.
		 * getCommercialBundle(),CommonMethods.getPriceForBundleAndHardware().
		 * get(0),CommonMethods.getListOfBundleAndHardwarePromotions(),null,
		 * false,null);
		 * deviceService.getBundlePriceBasedOnDiscountDuration_Implementation(
		 * deviceSummary,"full_duration");
		 * deviceService.getBundlePriceBasedOnDiscountDuration_Implementation(
		 * deviceSummary,"limited_time"); }
		 * 
		 * @Test public void validatorTest(){
		 * Validator.validateGroupType("DEVICE_PAYM");
		 * Validator.validateGroupType("DEVICE_PAYM1");
		 * Validator.validateMSISDN("12331354");
		 * Validator.validateMSISDN("AS46");
		 * Validator.validateIncludeRecommendation("true");
		 * Validator.validateIncludeRecommendation("FALSE");
		 * Validator.validateGetListOfDeviceTile(new HashMap());
		 * Validator.validateGetDeviceList(new HashMap());
		 * Validator.validateJourneyType("acquisition"); }
		 * 
		 * @Test public void notNullTestForIndexPrecalData() {
		 * List<com.vf.uk.dal.device.datamodel.merchandisingpromotion.
		 * DevicePreCalculatedData> deviceListObjectList =
		 * DaoUtils.convertDevicePreCalDataToSolrData(CommonMethods.
		 * getDevicePreCal());
		 * deviceService.indexPrecalData(deviceListObjectList); }
		 */
}
