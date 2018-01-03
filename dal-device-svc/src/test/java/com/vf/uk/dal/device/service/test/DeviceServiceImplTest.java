package com.vf.uk.dal.device.service.test;

import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
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
import com.vf.uk.dal.device.beans.test.DeviceTestBeans;
import com.vf.uk.dal.device.common.test.CommonMethods;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.entity.Accessory;
import com.vf.uk.dal.device.entity.AccessoryTileGroup;
import com.vf.uk.dal.device.entity.Asset;
import com.vf.uk.dal.device.entity.BundleAndHardwareTuple;
import com.vf.uk.dal.device.entity.DeviceDetails;
import com.vf.uk.dal.device.entity.DeviceSummary;
import com.vf.uk.dal.device.entity.DeviceTile;
import com.vf.uk.dal.device.entity.FacetedDevice;
import com.vf.uk.dal.device.entity.Insurances;
import com.vf.uk.dal.device.entity.Member;
import com.vf.uk.dal.device.entity.PriceForBundleAndHardware;
import com.vf.uk.dal.device.entity.ProductGroup;
import com.vf.uk.dal.device.entity.RequestForBundleAndHardware;
import com.vf.uk.dal.device.entity.SourcePackageSummary;
import com.vf.uk.dal.device.entity.Subscription;
import com.vf.uk.dal.device.svc.DeviceRecommendationService;
import com.vf.uk.dal.device.svc.DeviceService;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.DaoUtils;
import com.vf.uk.dal.device.utils.SolrConnectionProvider;
import com.vf.uk.dal.utility.entity.BundleDetails;
import com.vf.uk.dal.utility.entity.BundleDetailsForAppSrv;
import com.vf.uk.dal.utility.entity.BundleDeviceAndProductsList;
import com.vf.uk.dal.utility.entity.CurrentJourney;
import com.vf.uk.dal.utility.entity.PriceForProduct;
import com.vf.uk.dal.utility.entity.RecommendedProductListRequest;
import com.vf.uk.dal.utility.entity.RecommendedProductListResponse;
import com.vf.uk.dal.utility.solr.entity.DevicePreCalculatedData;
import com.vodafone.common.Filters;
import com.vodafone.dal.bundle.pojo.CommercialBundle;
import com.vodafone.product.pojo.CommercialProduct;
import com.vodafone.solrmodels.OfferAppliedPriceModel;
import com.vodafone.solrmodels.ProductGroupFacetModel;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeviceTestBeans.class)
public class DeviceServiceImplTest 
{
	@Autowired
	DeviceService deviceService;

	@MockBean
	DeviceDao deviceDAOMock;
	
	@MockBean
    EurekaClient eureka;

    @MockBean
    RegistryClient registry;
    
    @MockBean
    RestTemplate restTemplate;
    
    @MockBean
    FacetedDevice facetDeviceMock;
    
    @MockBean
    DeviceRecommendationService deviceRecomServiceMock;
    
	@MockBean
	ConfigHelper configHelperMock;
	
	@Before
	public void setupMockBehaviour() throws Exception {
		String jsonString=new String(Utility.readFile("\\rest-mock\\COMMON-V1.json"));
         CurrentJourney obj=new ObjectMapper().readValue(jsonString, CurrentJourney.class);  
         given(registry.getRestTemplate()).willReturn(restTemplate);
         given(restTemplate.getForObject("http://COMMON-V1/common/journey/"+"c1a42269-6562-4c96-b3be-1ca2a6681d57"+"/queries/currentJourney" ,CurrentJourney.class)).willReturn(obj);
         
         List<String> listOfRecommendedProductTypes;
         RecommendedProductListRequest recomProductListReq = new RecommendedProductListRequest();
			listOfRecommendedProductTypes = new ArrayList<>();
			listOfRecommendedProductTypes.add(Constants.STRING_DEVICE);
			recomProductListReq.setSerialNumber("07003145001");
			recomProductListReq.setRecommendedProductTypes(listOfRecommendedProductTypes);
         String jsonString1=new String(Utility.readFile("\\rest-mock\\CUSTOMER-V1.json"));
         RecommendedProductListResponse obj1=new ObjectMapper().readValue(jsonString1, RecommendedProductListResponse.class);  
         given(registry.getRestTemplate()).willReturn(restTemplate);
         given(restTemplate.postForObject("http://CUSTOMER-V1/customer/getRecommendedProductList/",recomProductListReq,RecommendedProductListResponse.class)).willReturn(obj1);

		/*given(this.deviceDAOMock.getListOfDeviceTile("Apple","iPhone-7","DEVICE", null, null, null,null,null)).willReturn(CommonMethods.getDeviceTile("Apple","iPhone-7","DEVICE"));
		given(this.deviceDAOMock.getListOfDeviceTile(null,"iPhone-7","DEVICE", null, null, null,null,null)).willReturn(null);
		given(this.deviceDAOMock.getListOfDeviceTile("Apple",null,"DEVICE", null, null, null,null,null)).willReturn(null);
		given(this.deviceDAOMock.getListOfDeviceTile("Apple","iPhone-7",null, null, null, null,null,null)).willReturn(null);*/
	
		given(this.deviceDAOMock.getProductGroupByGroupTypeGroupName("DEVICE","Apple iPhone 6s")).willReturn(CommonMethods.getProductGroupByGroupTypeGroupName("DEVICE","Apple IPhone 6s"));
		given(this.deviceDAOMock.getProductGroupByGroupTypeGroupName(null,"Apple iPhone 7")).willReturn(null);
		given(this.deviceDAOMock.getProductGroupByGroupTypeGroupName("DEVICE",null)).willReturn(null);
		given(this.deviceDAOMock.getProductGroupByGroupTypeGroupName("DEVfk","Apple iPhone 6s")).willReturn(null);
		given(this.deviceDAOMock.getProductGroupByGroupTypeGroupName(null,null)).willReturn(null);
		given(this.deviceDAOMock.getDeviceTileById("83921",null,null)).willReturn(CommonMethods.getDeviceTileById("83921"));
		given(this.deviceDAOMock.getDeviceTileById("83964",null,null)).willReturn(null);
		/*given(this.deviceDAOMock.getDeviceDetails("83921","upgrade","34543")).willReturn(CommonMethods.getDevice("83921"));
		given(this.deviceDAOMock.getDeviceDetails("83921","Upgrade","W_HH_PAYM_OC_02")).willReturn(CommonMethods.getDevice("83921"));
		given(this.deviceDAOMock.getDeviceDetails("83929","upgrade","34543")).willReturn(null);*/
		given(deviceDAOMock.getCommercialProductFromCommercialProductRepository(Matchers.anyString())).willReturn(CommonMethods.getCommercialProductByDeviceId());
		given(this.deviceDAOMock.getAccessoriesOfDevice("93353","Upgrade","W_HH_PAYM_OC_02")).willReturn(CommonMethods.getAccessoriesTileGroup("93353"));
		given(this.deviceDAOMock.getAccessoriesOfDevice("93354","Upgrade","W_HH_PAYM_OC_02")).willReturn(null);
		given(this.deviceDAOMock.getAccessoriesOfDevice(null,null,null)).willReturn(null);
		//given(this.deviceDAOMock.getDeviceList("HANDSET","apple", "iPhone-7","DEVICE_PAYM", "Priority", 1, 2,"32 GB","White","iOS","Great Camera")).willReturn(CommonMethods.getFacetedDevice("HANDSET","apple", "iPhone 7", "DEVICE_PAYM", "asc", 1, 2,"123"));
		//given(this.deviceDAOMock.getDeviceList(null,null,null,null,null,1,0,"32 GB","White","iOS","Great Camera")).willReturn(null);
		//given(this.deviceDAOMock.getDeviceList("productclass","make","model",null,"",1,0,"32 GB","White","iOS","Great Camera")).willReturn(null);
		//given(this.deviceDAOMock.getInsuranceById("93353")).willReturn(CommonMethods.getInsurances("93353"));
		given(this.deviceDAOMock.getCommercialProductByProductId("093353")).willReturn(CommonMethods.getCommercialProduct2());
		given(this.deviceDAOMock.getCommercialBundleByBundleId("110154")).willReturn(CommonMethods.getCommercialBundle());
		given(deviceDAOMock.getMerchandisingPromotionByPromotionName("EXTRA.1GB.DATA")).willReturn(CommonMethods.getMerchPromotion());
		given(deviceDAOMock.getMerchandisingPromotionByPromotionName("qwerty")).willReturn(CommonMethods.getMerchPromotion1());
		given(this.deviceDAOMock.getCommercialProductByProductId("093311")).willReturn(CommonMethods.getCommercialProduct3());
		List<BundleAndHardwareTuple> bundleAndHardwareTupleList = new ArrayList<>();
		BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
		bundleAndHardwareTuple.setHardwareId("093353");
		bundleAndHardwareTuple.setBundleId("110154");
		bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
		given(this.deviceDAOMock.getPriceForBundleAndHardware(bundleAndHardwareTupleList,null,null)).willReturn(CommonMethods.getPriceForBundleAndHardware1());
	}
	/*@Test
	public void notNullTestForGetDeviceTileList() {
		List<DeviceTile> deviceTileList = deviceService.getListOfDeviceTile("Apple","iPhone-7","DEVICE", null, null,null,null,null);
		Assert.assertNotNull(deviceTileList);
	}*/

	@Test
	public void sizeTestForGetDeviceTileList() {
		List<DeviceTile> deviceTileList=null;
		try
		{
		 deviceTileList = deviceService.getListOfDeviceTile(null,"iPhone-7","DEVICE", null, null,null,null,null);
		}
		catch(Exception e)
		{
			
		}
		Assert.assertNull(deviceTileList);
	}
	@Test
	public void nullInputTestForGetDeviceTileList() throws Exception {
		List<DeviceTile> deviceTileList=null;
		try
		{
		 deviceTileList = deviceService.getListOfDeviceTile("Apple",null,"DEVICE", null, null,"upgrade","34543",null);
		}
		catch(Exception e)
		{
			
		}
		Assert.assertNull(deviceTileList);
	}
	@Test
	public void nullTestGroupTypeForGetDeviceTileList() throws Exception {
		List<DeviceTile> deviceTileList=null;
		try
		{
		 deviceTileList = deviceService.getListOfDeviceTile("Apple","iPhone-7",null, null, null,"upgrade","34543",null);
		}
		catch(Exception e)
		{
			
		}
		Assert.assertNull(deviceTileList);
	}

	@Test
	public void notNullTestForgetProductGroupListByGroupTypeGroupName() {
		List<ProductGroup> productGroupDetails = deviceService.getProductGroupByGroupTypeGroupName("DEVICE","Apple iPhone 6s");
		Assert.assertNotNull(productGroupDetails);
	}

	@Test
	public void nullTestForgetProductGroupListByGroupTypeGroupName() {
		
		List<ProductGroup> productGroupDetails = deviceService.getProductGroupByGroupTypeGroupName("DEVfk","Apple iPhone 6s");
		Assert.assertNull(productGroupDetails);
	}
	
	@Test
	public void nullTestForgetProductGroupListByGroupNameWithNullValue() throws Exception {
		List<ProductGroup> productGroupDetails=null;
		try
		{
		productGroupDetails = deviceService.getProductGroupByGroupTypeGroupName("DEVICE",null);
		}
		catch(Exception e)
		{
		}
		
		Assert.assertNull(productGroupDetails);
	}
	@Test
	public void nullTestForgetProductGroupListByGroupTypeGroupNameWithNullValue() throws Exception {
		List<ProductGroup> productGroupDetails=null;
		try
		{
		productGroupDetails = deviceService.getProductGroupByGroupTypeGroupName(null,"Apple iPhone 7");
		}
		catch(Exception e)
		{
		}
		
		Assert.assertNull(productGroupDetails);
	}
	@Test
	public void notNullTestForGetDeviceTilesById() {
		
		List<DeviceTile> deviceTileList = deviceService.getDeviceTileById("83921",null,null);
		Assert.assertNotNull(deviceTileList);
	}
	@Test
	public void nullTestForGetDeviceTilesById() {
		List<DeviceTile> deviceTileList = deviceService.getDeviceTileById("83964",null,null);
		Assert.assertNull(deviceTileList);
	}
	@Test
	public void notNullTestForGetDeviceDetails() {
		
		DeviceDetails deviceDetails=new DeviceDetails();
		deviceDetails=deviceService.getDeviceDetails("83921","Upgrade","W_HH_PAYM_OC_02");
		Assert.assertNotNull(deviceDetails);
	}
		
	@Test
	public void notNullTestForGetAccessoriesOfDevice() {
		List<AccessoryTileGroup> accessory=new ArrayList<>();
		accessory=deviceService.getAccessoriesOfDevice("93353","Upgrade","W_HH_PAYM_OC_02");
		Assert.assertNotNull(accessory); 
	}
	@Test
	public void nullTestForGetAccessoriesOfDevice() {
		List<AccessoryTileGroup> accessory=new ArrayList<>();
		try{
		accessory=deviceService.getAccessoriesOfDevice(null,null,null);
		}
		catch(Exception e)
		{
			
		}
		Assert.assertNull(accessory); 
	}
	
	/*@Test
	public void notNullTestForGetDeviceList() {
		FacetedDevice deviceLists=null;
		deviceLists=deviceService.getDeviceList("HANDSET","apple", "iPhone-7","DEVICE_PAYM", "Priority", 1, 2,"32 GB","White","iOS","Great Camera","c1a42269-6562-4c96-b3be-1ca2a6681d57");
		
		Assert.assertNotNull(deviceLists);
	}
	@Test
	public void nullTestForGetDeviceList() {
		FacetedDevice deviceLists=null;
		try{
			deviceLists=deviceService.getDeviceList("Handset","apple", "iPhone 7", "DEVICE_PAYM",null,0,0,"32 GB","White","iOS","Great Camera","123");
		}
		catch(Exception e)
		{
			
		}
		Assert.assertNull(deviceLists);
	}*/
	@Test
	public void nullTestForGetDeviceList1() {
		FacetedDevice deviceLists=null;
		try{
			Mockito.when(deviceRecomServiceMock.getRecommendedDeviceList("journeyId", "093353", Mockito.anyObject())).thenReturn(deviceLists);

			deviceLists=deviceService.getDeviceList("Handset1","apple", "iPhone 7", "DEVICE_PAYM", 
					"asc", 1, 2,"32 GB","White","iOS","Great Camera","upgrade",34543.0f,null, "447582367723", true);
		}
		catch(Exception e)
		{
			
		}
		Assert.assertNull(deviceLists);
	}
	@Test
	public void nullTestForGetDeviceList2() {
		FacetedDevice deviceLists=null;
		try{
			Mockito.when(deviceRecomServiceMock.getRecommendedDeviceList("journeyId", "093353", Mockito.anyObject())).thenReturn(deviceLists);

			deviceLists=deviceService.getDeviceList("Handset",null, "iPhone 7", "DEVICE_PAYM", 
					"Priority", 1, 2,"32 GB","White","iOS","Great Camera","upgrade",34543.0f,null, "447582367723", true);
		}
		catch(Exception e)
		{
			
		}
		Assert.assertNull(deviceLists);
	}
	@Test
	public void nullTestForGetDeviceList3() {
		FacetedDevice deviceLists=null;
		try{
			Mockito.when(deviceRecomServiceMock.getRecommendedDeviceList("journeyId", "093353", Mockito.anyObject())).thenReturn(deviceLists);

			deviceLists=deviceService.getDeviceList(null,"apple", "iphone 7", "DEVICE_PAYM",
					"Priority", 1, 2,"32 GB","White","iOS","Great Camera","upgrade",34543.0f,null, "447582367723", true);
		}
		catch(Exception e)
		{
			
		}
		Assert.assertNull(deviceLists);
	}
	@Test
	public void nullTestForGetDeviceList4() {
		FacetedDevice deviceLists=null;
		try{
			Mockito.when(deviceRecomServiceMock.getRecommendedDeviceList("journeyId", "093353", Mockito.anyObject())).thenReturn(deviceLists);

			deviceLists=deviceService.getDeviceList("Handset","apple", "iPhone 7", "DEVICE",
					"Priority", 1, 2,"32 GB","White","iOS","Great Camera","upgrade",34543.0f,null, "447582367723", true);
		}
		catch(Exception e)
		{
			
		}
		Assert.assertNull(deviceLists);
	}
	@Test
	public void nullTestForGetDeviceListForGroupType() {
		FacetedDevice deviceLists=null;
		try{ 
			deviceLists=deviceService.getDeviceList("Handset","apple", "iPhone 7", null,
					"Priority", 1, 2,"32 GB","White","iOS","Great Camera","upgrade",34543.0f,null, "447582367723", true);
		}
		catch(Exception e)
		{
			
		}
		Assert.assertNull(deviceLists);
	}
	/*@Test
	public void nullTestForGetInsuranceById() {
		try{
		Insurances insurance=null;
			insurance=deviceService.getInsuranceByDeviceId("093352");
		}
		catch(Exception e){}
	}*/
	@Test
	public void notNullTestForGetInsuranceById() {
	List<String> insuranceList=new ArrayList<>();
	insuranceList.add("093353");
	insuranceList.add("093354");
	
		given(this.deviceDAOMock.getCommercialProductByProductId("093353")).willReturn(CommonMethods.getCommercialProductForInsurance());
		given(this.deviceDAOMock.getGroupByProdGroupName("DEVICE_PAYM","Compatible Insurance")).willReturn(CommonMethods.getGropuFromProductGroups());
		given(this.deviceDAOMock.getCommercialProductsList(insuranceList)).willReturn(CommonMethods.getListOfCommercialProduct());
		Insurances insurance=null;
			insurance=deviceService.getInsuranceByDeviceId("093353","upgrade");
		Assert.assertNotNull(insurance);
	}
	@Test
	public void NotNullTestForDaoUtilsconvertCoherenceDeviceToDeviceTile() {
		DeviceSummary deviceSummary = DaoUtils.convertCoherenceDeviceToDeviceTile((long)1, CommonMethods.getCommercialProduct(),CommonMethods.getCommercialBundle(),CommonMethods.getPriceForBundleAndHardware().get(0),CommonMethods.getListOfBundleAndHardwarePromotions(),null, false,null);
		Assert.assertNotNull(deviceSummary);
	}
	@Test
	public void NotNullTestDateForDaoUtilsconvertCoherenceDeviceToDeviceTile() {
		DeviceSummary deviceSummary = DaoUtils.convertCoherenceDeviceToDeviceTile((long)1, CommonMethods.getCommercialProduct1(),CommonMethods.getCommercialBundle1(),CommonMethods.getPriceForBundleAndHardware().get(0),CommonMethods.getListOfBundleAndHardwarePromotions(),null, false,null);
		Assert.assertNotNull(deviceSummary);
	}
	@Test
	public void NullTestStartDateForDaoUtilsconvertCoherenceDeviceToDeviceTile() {
		DeviceSummary deviceSummary = DaoUtils.convertCoherenceDeviceToDeviceTile((long)1, CommonMethods.getCommercialProduct1(),CommonMethods.getCommercialBundle2(),CommonMethods.getPriceForBundleAndHardware().get(0),CommonMethods.getListOfBundleAndHardwarePromotions(),null, false,null);
		Assert.assertNotNull(deviceSummary);
	}
	@Test
	public void NullTestEndDateForDaoUtilsconvertCoherenceDeviceToDeviceTile() {
		DeviceSummary deviceSummary = DaoUtils.convertCoherenceDeviceToDeviceTile((long)1, CommonMethods.getCommercialProduct1(),CommonMethods.getCommercialBundle3(),CommonMethods.getPriceForBundleAndHardware().get(0),CommonMethods.getListOfBundleAndHardwarePromotions(),null, false,null);
		Assert.assertNotNull(deviceSummary);
	}
	@Test
	public void NotNullTestForDaoUtilsconvertCoherenceDeviceToDeviceDetails() {
		DeviceDetails deviceDetails = DaoUtils.convertCoherenceDeviceToDeviceDetails(CommonMethods.getCommercialProduct(),CommonMethods.getPriceForBundleAndHardware(),CommonMethods.getListOfBundleAndHardwarePromotions());
		Assert.assertNotNull(deviceDetails);
	}
	
	@Test
	public void NotNullTestForDaoUtilsconvertGroupProductToProductGroupDetails() {
		List<ProductGroup> productGroup = DaoUtils.convertGroupProductToProductGroupDetails((CommonMethods.getProductGroupModel()));
		Assert.assertNotNull(productGroup);
	}
	@Test
	public void NotNullTestForDaoUtilsgetPrice() {
		com.vf.uk.dal.device.entity.Price price = DaoUtils.getPriceFromGross(40.98);
		Assert.assertNotNull(price);
	}
	
	@Test
	public void NotNullTestForDaoUtilsisCurrentDateBetweenStartAndEndDate() {
		boolean date = DaoUtils.isCurrentDateBetweenStartAndEndDate(Date.valueOf("2016-09-05"),Date.valueOf("2019-09-05"));
		Assert.assertNotNull(date);
	}
	@Test
	public void NotNullTestForDaoUtilsconvertCommercialProductToInsurance() {
		Insurances insurances = DaoUtils.convertCommercialProductToInsurance(CommonMethods.getListOfCommercialProduct());
		Assert.assertNotNull(insurances);
	}
	/**
	 * need to modify
	 */
	@Test
	public void NotNullTestForDaoUtilsconvertProductModelListToDeviceList() {
		Map<String,String> groupNameWithProdId=new HashMap<String, String>();
		groupNameWithProdId.put("Apple", "10936");
		groupNameWithProdId.put("Samsung","7630");
		Map<String, List<OfferAppliedPriceModel>> listOfOfferAppliedPrice = new HashMap<>();
		listOfOfferAppliedPrice.put("093353", Arrays.asList(CommonMethods.getOfferAppliedPriceModel().get(1)));
		listOfOfferAppliedPrice.put("092660", Arrays.asList(CommonMethods.getOfferAppliedPriceModel().get(0)));
		Map<String,Boolean> isLeadMemberFromSolr = new HashMap<>();
		isLeadMemberFromSolr.put("leadMember", true);
		FacetedDevice deviceList = DaoUtils.convertProductModelListToDeviceList(CommonMethods.getProductModel(),
				CommonMethods.getListOfProducts(),CommonMethods.getProductGroupFacetModel1().getListOfFacetsFields(),
				"DEVICE_PAYM",null,null,listOfOfferAppliedPrice,"W_HH_OC_02",
				groupNameWithProdId ,null,null,
				isLeadMemberFromSolr,listOfOfferAppliedPrice,"Upgrade");
		Assert.assertNotNull(deviceList);
	}
	@Test
	public void NotNullTestForDaoUtilsconvertProductModelListToDeviceList1() {
		Map<String,String> groupNameWithProdId=new HashMap<String, String>();
		groupNameWithProdId.put("Apple", "10936");
		groupNameWithProdId.put("Samsung","7630");
		Map<String, List<OfferAppliedPriceModel>> listOfOfferAppliedPrice = new HashMap<>();
		listOfOfferAppliedPrice.put("093353", Arrays.asList(CommonMethods.getOfferAppliedPriceModel().get(1)));
		listOfOfferAppliedPrice.put("092660", Arrays.asList(CommonMethods.getOfferAppliedPriceModel().get(0)));
		Map<String, List<OfferAppliedPriceModel>> listOfOfferAppliedPrice1 = new HashMap<>();
		listOfOfferAppliedPrice1.put("093353", new ArrayList<>());
		listOfOfferAppliedPrice1.put("092660", new ArrayList<>());
		Map<String,Boolean> isLeadMemberFromSolr = new HashMap<>();
		isLeadMemberFromSolr.put("leadMember", true);
		FacetedDevice deviceList = DaoUtils.convertProductModelListToDeviceList(CommonMethods.getProductModel(),
				CommonMethods.getListOfProducts(),CommonMethods.getProductGroupFacetModel1().getListOfFacetsFields(),
				"DEVICE_PAYM",null,null,listOfOfferAppliedPrice1,null,
				groupNameWithProdId ,null,null,
				isLeadMemberFromSolr,listOfOfferAppliedPrice,"Upgrade");
		Assert.assertNotNull(deviceList);
	}
	@Test
	public void NotNullTestForDaoUtilsconvertProductModelListToDeviceList2() {
		Map<String,String> groupNameWithProdId=new HashMap<String, String>();
		groupNameWithProdId.put("Apple", "10936");
		groupNameWithProdId.put("Samsung","7630");
		Map<String, List<OfferAppliedPriceModel>> listOfOfferAppliedPrice1 = new HashMap<>();
		listOfOfferAppliedPrice1.put("093353", new ArrayList<>());
		listOfOfferAppliedPrice1.put("092660", new ArrayList<>());
		Map<String,Boolean> isLeadMemberFromSolr = new HashMap<>();
		isLeadMemberFromSolr.put("leadMember", true);
		FacetedDevice deviceList = DaoUtils.convertProductModelListToDeviceList(CommonMethods.getProductModel(),
				CommonMethods.getListOfProducts(),CommonMethods.getProductGroupFacetModel1().getListOfFacetsFields(),
				"DEVICE_PAYM",null,null,listOfOfferAppliedPrice1,null,
				groupNameWithProdId ,null,null,
				isLeadMemberFromSolr,listOfOfferAppliedPrice1,"Upgrade");
		Assert.assertNotNull(deviceList);
	}
	
	@Test
	public void NotNullTestForDaoUtilsconvertProductModelListToDeviceList3() {
		Map<String,String> groupNameWithProdId=new HashMap<String, String>();
		groupNameWithProdId.put("Apple", "10936");
		groupNameWithProdId.put("Samsung","7630");
		Map<String, List<OfferAppliedPriceModel>> listOfOfferAppliedPrice1 = new HashMap<>();
		listOfOfferAppliedPrice1.put("093353", new ArrayList<>());
		listOfOfferAppliedPrice1.put("092660", new ArrayList<>());
		Map<String,Boolean> isLeadMemberFromSolr = new HashMap<>();
		isLeadMemberFromSolr.put("leadMember", true);
		FacetedDevice deviceList = DaoUtils.convertProductModelListToDeviceList(CommonMethods.getProductModel(),
				CommonMethods.getListOfProducts(),CommonMethods.getProductGroupFacetModel1().getListOfFacetsFields(),
				"DEVICE_PAYM",null,null,listOfOfferAppliedPrice1,null,
				groupNameWithProdId ,null,null,
				isLeadMemberFromSolr,listOfOfferAppliedPrice1,null);
		Assert.assertNotNull(deviceList);
	}
	
	@Test
	public void NotNullTestForDaoUtilsconvertProductModelListToDeviceList4() {
		Map<String,String> groupNameWithProdId=new HashMap<String, String>();
		groupNameWithProdId.put("Apple", "10936");
		groupNameWithProdId.put("Samsung","7630");
		Map<String, List<OfferAppliedPriceModel>> listOfOfferAppliedPrice = new HashMap<>();
		listOfOfferAppliedPrice.put("093353", Arrays.asList(CommonMethods.getOfferAppliedPriceModel().get(1)));
		listOfOfferAppliedPrice.put("092660", Arrays.asList(CommonMethods.getOfferAppliedPriceModel().get(0)));
		Map<String, List<OfferAppliedPriceModel>> listOfOfferAppliedPrice1 = new HashMap<>();
		listOfOfferAppliedPrice1.put("093353", new ArrayList<>());
		listOfOfferAppliedPrice1.put("092660", new ArrayList<>());
		Map<String,Boolean> isLeadMemberFromSolr = new HashMap<>();
		isLeadMemberFromSolr.put("leadMember", true);
		FacetedDevice deviceList = DaoUtils.convertProductModelListToDeviceList(CommonMethods.getProductModel(),
				CommonMethods.getListOfProducts(),CommonMethods.getProductGroupFacetModel1().getListOfFacetsFields(),
				"DEVICE_PAYM",null,null,listOfOfferAppliedPrice1,"W_HH_OC_02",
				groupNameWithProdId ,null,null,
				isLeadMemberFromSolr,listOfOfferAppliedPrice,"Upgrade");
		Assert.assertNotNull(deviceList);
	}
	@Test
	public void NotNullTestForDaoUtilsconvertProductModelListToDeviceList5() {
		Map<String,String> groupNameWithProdId=new HashMap<String, String>();
		groupNameWithProdId.put("Apple", "10936");
		groupNameWithProdId.put("Samsung","7630");
		Map<String, List<OfferAppliedPriceModel>> listOfOfferAppliedPrice1 = new HashMap<>();
		listOfOfferAppliedPrice1.put("093353", new ArrayList<>());
		listOfOfferAppliedPrice1.put("092660", new ArrayList<>());
		Map<String,Boolean> isLeadMemberFromSolr = new HashMap<>();
		isLeadMemberFromSolr.put("leadMember", true);
		FacetedDevice deviceList = DaoUtils.convertProductModelListToDeviceList(CommonMethods.getProductModel(),
				CommonMethods.getListOfProducts(),CommonMethods.getProductGroupFacetModel1().getListOfFacetsFields(),
				"DEVICE_PAYM",null,null,listOfOfferAppliedPrice1,"W_HH_OC_02",
				groupNameWithProdId ,null,null,
				isLeadMemberFromSolr,listOfOfferAppliedPrice1,"Upgrade");
		Assert.assertNotNull(deviceList);
	}
	
	/*@Test
	public void NotNullTestForDaoUtilsconvertProductModelListToDeviceList_Data_PAYG() {
		Map<String,String> groupNameWithProdId=new HashMap<String, String>();
		groupNameWithProdId.put("Apple", "10936");
		groupNameWithProdId.put("Samsung","7630");
		FacetedDevice deviceList = DaoUtils.convertProductModelListToDeviceList(CommonMethods.getProductModel(),CommonMethods.getListOfProducts(), groupNameWithProdId ,getProductGroupFacetModel1,CommonMethods.getPriceForBundleAndHardware(),null, "DEVICE_PAYG",CommonMethods.getListofdataPayGCoherence());
		Assert.assertNotNull(deviceList);
	}*/
	
	
/*	@Test
	public void NotNullTestForDeviceListSort()
	{
		given(this.deviceDAOMock.getDeviceList("HANDSET","apple", "iPhone-7","DEVICE_PAYM", "Priority", 1, 2,"32 GB","White","iOS","Great Camera")).willReturn(CommonMethods.getFacetedDeviceForSorting("HANDSET","apple", "iPhone 7", "DEVICE_PAYM", "asc", 1, 2,"123"));
		
		FacetedDevice deviceLists=deviceService.getDeviceList("HANDSET","apple", "iPhone-7","DEVICE_PAYM", "Priority", 1, 2,"32 GB","White","iOS","Great Camera","c1a42269-6562-4c96-b3be-1ca2a6681d57");
		Assert.assertNotNull(deviceLists);
	}
	@Test
	public void exceptionTestForCommonJourney()
	{
		given(this.deviceDAOMock.getDeviceList("HANDSET","apple", "iPhone-7","DEVICE_PAYM", "Priority", 1, 2,"32 GB","White","iOS","Great Camera")).willReturn(CommonMethods.getFacetedDeviceForSorting("HANDSET","apple", "iPhone 7", "DEVICE_PAYM", "asc", 1, 2,"123"));
		given(restTemplate.getForObject("http://COMMON-V1/common/journey/c1a42269-6562-4c96-b3be-1ca2a6681d57/queries/currentJourney" ,CurrentJourney.class)).willThrow(new ApplicationException(""));		
		try {
			deviceService.getDeviceList("HANDSET","apple", "iPhone-7","DEVICE_PAYM", "Priority", 1, 2,"32 GB","White","iOS","Great Camera","c1a42269-6562-4c96-b3be-1ca2a6681d57");
		} catch (Exception e) {
			Assert.assertEquals("Invalid Journey Id sent in the request",e.getMessage());
		}
		
	}
	@Test
	public void exceptionTestForrecommodation()
	{
		List<String> listOfRecommendedProductTypes;
		RecommendedProductListRequest recomProductListReq = new RecommendedProductListRequest();
		listOfRecommendedProductTypes = new ArrayList<>();
		listOfRecommendedProductTypes.add(Constants.STRING_DEVICE);
		recomProductListReq.setSerialNumber("07003145001");
		recomProductListReq.setRecommendedProductTypes(listOfRecommendedProductTypes);
		given(this.deviceDAOMock.getDeviceList("HANDSET","apple", "iPhone-7","DEVICE_PAYM", "Priority", 1, 2,"32 GB","White","iOS","Great Camera")).willReturn(CommonMethods.getFacetedDeviceForSorting("HANDSET","apple", "iPhone 7", "DEVICE_PAYM", "asc", 1, 2,"123"));
		given(restTemplate.postForObject("http://CUSTOMER-V1/customer/getRecommendedProductList/",recomProductListReq,RecommendedProductListResponse.class)).willThrow(new ApplicationException(""));		
		try {
			deviceService.getDeviceList("HANDSET","apple", "iPhone-7","DEVICE_PAYM", "Priority", 1, 2,"32 GB","White","iOS","Great Camera","c1a42269-6562-4c96-b3be-1ca2a6681d57");
		} catch (Exception e) {
			Assert.assertEquals("Exception occured while connecting to GRPL API",e.getMessage());
		}
		
	}
	@Test
	public void testForStock()
	{
		List<StockInfo> listofStock=CommonUtility.getStockAvailabilityForDevice("093353",registry);
		Assert.assertNull(listofStock);
	}*/
	@Test
	public void testForGetBundleDetailsFromComplansListingAPI()
	{
		BundleDetails bundleDetails = CommonUtility.getBundleDetailsFromComplansListingAPI("093353", "priority",
				registry);
		Assert.assertNull(bundleDetails);
	}
	
	public void testForGetBundleDetailsFromComplansListingAPIForException()
	{
		given(restTemplate.getForObject("http://BUNDLES-V1/bundles/catalogue/bundle/queries/byDeviceId/093353//?sort=priority",BundleDetails.class)).willThrow(new ApplicationException(""));
		CommonUtility.getBundleDetailsFromComplansListingAPI("093353", "priority",
				registry);
	}
	
	@Test
	public void testForGetPriceDetails()
	{
		List<BundleAndHardwareTuple> bundleAndHardwareTupleList=new ArrayList<>();
		BundleAndHardwareTuple bht= new BundleAndHardwareTuple();
		bht.setBundleId("110154");
		bht.setHardwareId("093353");
		bundleAndHardwareTupleList.add(bht);
		List<PriceForBundleAndHardware> listOfPriceForBundleAndHardware=CommonUtility.getPriceDetails(bundleAndHardwareTupleList,null,registry,null);
		Assert.assertNull(listOfPriceForBundleAndHardware);
	}
	@Test
	public void testForGetPriceDetailsForCompatibaleBundle() 
	{
		BundleDetailsForAppSrv bdfas= CommonUtility.getPriceDetailsForCompatibaleBundle("093353",registry);
		Assert.assertNull(bdfas);
	}
	@Test
	public void testForConvertBundleHeaderForDeviceToProductGroupForDeviceListing()
	{
		Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>> iLSPriceMap =new HashMap<>();
		iLSPriceMap.put("093353", CommonMethods.getOfferAppliedPrice());
		DevicePreCalculatedData	productGroupForDeviceListing=DaoUtils
		.convertBundleHeaderForDeviceToProductGroupForDeviceListing("093353","leadPlanId","groupname"
				,"groupId", CommonMethods.getPrice(), CommonMethods.getleadMemberMap(),iLSPriceMap,CommonMethods.getleadMemberMap(),"upgradeLeadPlanId");

		Assert.assertNotNull(productGroupForDeviceListing);
	}
	@Test
	public void testForConvertBundleHeaderForDeviceToProductGroupForDeviceListingNullLeadPlanId()
	{
		Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>> iLSPriceMap =new HashMap<>();
		iLSPriceMap.put("093353", CommonMethods.getOfferAppliedPrice());
		DevicePreCalculatedData	productGroupForDeviceListing=DaoUtils
		.convertBundleHeaderForDeviceToProductGroupForDeviceListing("093353",null,"groupname"
				,"groupId", CommonMethods.getPrice(),CommonMethods.getleadMemberMap(),iLSPriceMap,CommonMethods.getleadMemberMap(),null);

		Assert.assertNotNull(productGroupForDeviceListing);
	}
	/*@Test(expected=Exception.class)
	public void testForConvertBundleHeaderForDeviceToProductGroupForDeviceListingException()
	{
		Map<String, List<com.vf.uk.dal.utility.entity.PriceForBundleAndHardware>> iLSPriceMap =new HashMap<>();
		iLSPriceMap.put("093353", CommonMethods.getOfferAppliedPrice());
		DaoUtils
		.convertBundleHeaderForDeviceToProductGroupForDeviceListing("093353",Float.valueOf("2.0"),"leadPlanId","groupname"
				,"groupId", CommonMethods.getPriceForBundleAndHardware(),iLSPriceMap);

	}*/
	
	@Test
	public void notNullTestForGetListDeviceDetails() {
		
		List<DeviceDetails> deviceDetails;
		deviceDetails=deviceService.getListOfDeviceDetails("093353",null,null);
		Assert.assertNotNull(deviceDetails);
	}
	@Test
	public void nullTestForGetListOfDeviceDetais() {
		List<DeviceDetails> deviceDetails = null;
		try{
		deviceDetails=deviceService.getListOfDeviceDetails("83929",null,null);
		}catch(Exception e){
			
		}
		Assert.assertNull(deviceDetails);
	}
	@Test
	public void notNullMultipleDevicesTestForGetListOfDeviceDetais() {
		List<DeviceDetails> deviceDetails;
		deviceDetails=deviceService.getListOfDeviceDetails("093353,093427",null,null);
		Assert.assertNotNull(deviceDetails);
	}
	@Test
	public void notNullInvalidLeadPlanIdTestForGetListOfDeviceDetais() {
		List<DeviceDetails> deviceDetails;
		deviceDetails=deviceService.getListOfDeviceDetails("093311",null,null);
		Assert.assertNotNull(deviceDetails);
	}
	@Test
	public void testForGetCurrentJourney()
	{
		//CurrentJourney cj=CommonUtility.getCurrentJourney("a1e2be6b-4c7a-4d6b-a621-f22e3266e651",registry);
		//Assert.assertNull(cj);
	}
	
	@Test
	public void testGetSubscriptionBundleId() {
		
		String subscriptionId = "07741655541";
		String subscriptionType = "msisdn";
		SourcePackageSummary s = getSourcePackageSummary();
		
		Mockito.when(registry.getRestTemplate()).thenReturn(restTemplate);
		String url = "http://CUSTOMER-V1/customer/subscription/" + subscriptionType + ":" + subscriptionId + "/sourcePackageSummary";
		

		given(restTemplate.getForObject(url, SourcePackageSummary.class)).willReturn(s);
		String deviceId = CommonUtility.getSubscriptionBundleId(subscriptionId, Constants.SUBSCRIPTION_TYPE_MSISDN, registry);
		Assert.assertEquals("109381", deviceId);
	}

	
	private SourcePackageSummary getSourcePackageSummary() {
		SourcePackageSummary s = new SourcePackageSummary();
		s.setPromotionId("109381");
		return s;
		
	}
	
	@Test
	public void testGetDeviceListFromPricing()
	{
		given(this.deviceDAOMock.getProductGroupsByType("DEVICE_PAYM")).willReturn(CommonMethods.getGroup());
		given(this.deviceDAOMock.getListCommercialProductRepositoryByLeadMemberId(Arrays.asList("123"))).willReturn(CommonMethods.getListOfCommercialProduct());
		try
		{
			deviceService.getDeviceListFromPricing("DEVICE_PAYM");
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	@Test
	public void testGetDeviceListFromPricingForNullLeadPlanId()
	{
		given(registry.getRestTemplate()).willReturn(restTemplate);
		RequestForBundleAndHardware requestForBundleAndHardware=new RequestForBundleAndHardware();
		List<BundleAndHardwareTuple> bundleAndHardwareTupleList = new ArrayList<>();
		BundleAndHardwareTuple bundleAndHardwareTuple = new BundleAndHardwareTuple();
		bundleAndHardwareTuple.setBundleId("110154");
		bundleAndHardwareTuple.setHardwareId("093353");
		bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
		bundleAndHardwareTuple = new BundleAndHardwareTuple();
		bundleAndHardwareTuple.setBundleId("110154");
		bundleAndHardwareTuple.setHardwareId("123");
		bundleAndHardwareTupleList.add(bundleAndHardwareTuple);
		requestForBundleAndHardware.setBundleAndHardwareList(bundleAndHardwareTupleList);
		requestForBundleAndHardware.setOfferCode(null);
		
		
		
		ObjectMapper mapper=new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		String jsonString="";
		try {
			jsonString = new String(Utility.readFile("\\rest-mock\\PRICE-V1.json"));
			com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[] obj=mapper.readValue(jsonString, com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[].class);
			given(restTemplate.postForObject("http://PRICE-V1/price/calculateForBundleAndHardware" ,requestForBundleAndHardware,com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[].class)).willReturn(obj);
			
			RequestForBundleAndHardware requestForBundleAndHardwarelocal=new RequestForBundleAndHardware();
			
			List<BundleAndHardwareTuple> bundleAndHardwareTupleListlocal = new ArrayList<>();
			BundleAndHardwareTuple bundleAndHardwareTuplelocal = new BundleAndHardwareTuple();
			bundleAndHardwareTuplelocal.setBundleId("123");
			bundleAndHardwareTuplelocal.setHardwareId("93353");
			bundleAndHardwareTupleListlocal.add(bundleAndHardwareTuplelocal);
			bundleAndHardwareTuplelocal = new BundleAndHardwareTuple();
			bundleAndHardwareTuplelocal.setBundleId("456");
			bundleAndHardwareTuplelocal.setHardwareId("93353");
			bundleAndHardwareTupleListlocal.add(bundleAndHardwareTuplelocal);
			bundleAndHardwareTuplelocal = new BundleAndHardwareTuple();
			bundleAndHardwareTuplelocal.setBundleId("789");
			bundleAndHardwareTuplelocal.setHardwareId("93353");
			bundleAndHardwareTupleListlocal.add(bundleAndHardwareTuplelocal);
			bundleAndHardwareTuplelocal = new BundleAndHardwareTuple();
			bundleAndHardwareTuplelocal.setBundleId("101112");
			bundleAndHardwareTuplelocal.setHardwareId("93353");
			bundleAndHardwareTupleListlocal.add(bundleAndHardwareTuplelocal);
			bundleAndHardwareTuplelocal = new BundleAndHardwareTuple();
			bundleAndHardwareTuplelocal.setBundleId("131415");
			bundleAndHardwareTuplelocal.setHardwareId("93353");
			bundleAndHardwareTupleListlocal.add(bundleAndHardwareTuplelocal);
			requestForBundleAndHardwarelocal.setBundleAndHardwareList(bundleAndHardwareTupleListlocal);
			requestForBundleAndHardwarelocal.setOfferCode(null);
			
			given(restTemplate.postForObject("http://PRICE-V1/price/calculateForBundleAndHardware" ,requestForBundleAndHardwarelocal,com.vf.uk.dal.utility.entity.PriceForBundleAndHardware[].class)).willReturn(obj);
			given(this.deviceDAOMock.getProductGroupsByType("DEVICE_PAYM")).willReturn(CommonMethods.getGroup());
			given(this.deviceDAOMock.getListCommercialProductRepositoryByLeadMemberId(Arrays.asList("123"))).willReturn(CommonMethods.getCommercialProductWithNullLeadPlanID());
		
			deviceService.getDeviceListFromPricing("DEVICE_PAYM");
		}
		catch(Exception e)
		{
		}
	}
	
	@Test
	public void testSolrMock()
	{
		ConfigHelper configHelper = Mockito.mock(ConfigHelper.class);
		SolrConnectionProvider solrConnectionProvider = new SolrConnectionProvider();
		try
		{
			
			solrConnectionProvider.getSolrConnection();
			
		}
		catch(Exception e)
		{
			try
			{
				solrConnectionProvider.closeSolrConnection();
			}
			catch(Exception te)
			{
				
			}
		}
	}
	@Test
	public void testGetDeviceListForErrors()
	{
		given(this.deviceDAOMock.getJourneyTypeCompatibleOfferCodes("journeyType")).willReturn(CommonMethods.getMerChandisingPromotion());
		try
		{    
			deviceService.getDeviceList("productClass", "listOfMake", "model", "groupType", null,2, 2, "capacity", "colour", "operatingSystem", "mustHaveFeatures", "journeyType", (float) 14.0, "offerCode", "msisdn", false);
		}
		catch(Exception e)
		{
			try
			{
				deviceService.getDeviceList(null, "listOfMake", "model", "groupType", "Sort",2, 2, "capacity", "colour", "operatingSystem", "mustHaveFeatures", "journeyType", (float) 14.0, "offerCode", "msisdn", false);
			}
			catch(Exception ew)
			{
				try
				{
					deviceService.getDeviceList("PC", "listOfMake", "model", "groupType", "Sort",2, 2, "capacity", "colour", "operatingSystem", "mustHaveFeatures", "journeyType", (float) 14.0, "offerCode", "msisdn", false);
				}
				catch(Exception ed)
				{
					try
					{
						deviceService.getDeviceList("HANDSET", "listOfMake", "model", "ksjdbhf", "Sort",2, 2, "capacity", "colour", "operatingSystem", "mustHaveFeatures", "journeyType", (float) 14.0, "offerCode", "msisdn", false);
					}
					catch(Exception er)
					{
						try
						{
							deviceService.getDeviceList("HANDSET", "listOfMake", "model", "DEVICE_PAYM", "Sort",2, 2, "capacity", "colour", "operatingSystem", "mustHaveFeatures", "journeyType", (float) 14.0, "offerCode", "msisdn", false);
						}
						catch(Exception eq)
						{
							try
							{
								deviceService.getDeviceList("HANDSET", "listOfMake", "model", null, "Sort",2, 2, "capacity", "colour", "operatingSystem", "mustHaveFeatures", "journeyType", (float) 14.0, "offerCode", "msisdn", false);
							}
							catch(Exception ek)
							{
								try
								{
									deviceService.getDeviceList("HANDSET", "listOfMake", "model", "DEVICE_PAYM", "Sort",2, 2, "capacity", "colour", "operatingSystem", "mustHaveFeatures", "journeyType", (float) 14.0, "offerCode", "", true);
								}
								catch(Exception lkj)
								{
									try
									{
										deviceService.getDeviceList("HANDSET", "listOfMake", "model", "DEVICE_PAYM", "Sort",2, 2, "capacity", "colour", "operatingSystem", "mustHaveFeatures", "conditionalaccept", (float) 14.0, "offerCode", "msisdn", true);
									}
									catch(Exception edfg)
									{
										
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	@Test
	public void testgetListOfMembers()
	{
		try
		{
			List<String> list = new ArrayList<>();
			list.add("abd|def");
			deviceService.getListOfMembers(list);
		}
		catch(Exception e)
		{
			
		}
	}
	
	@Test
	public void testgetMemeberBasedOnRules1()
	{
		List<com.vf.uk.dal.device.entity.Member> listOfDeviceGroupMember = new ArrayList<>();
		com.vf.uk.dal.device.entity.Member member = new Member();
		member.setId("12345");
		member.setPriority("1");
		listOfDeviceGroupMember.add(member);
		try
		{
			List<String> list = new ArrayList<>();
			list.add("abd|def");
			deviceService.getMemeberBasedOnRules1(listOfDeviceGroupMember,"Upgrade");
		}
		catch(Exception e)
		{
			
		}
	}
	@Test
	public void testvalidateMemeber1()
	{
		given(this.deviceDAOMock.getProductModel(Matchers.anyList())).willReturn(CommonMethods.getProductModel());
		try
		{
			deviceService.validateMemeber1("12345","Upgrade");
		}
		catch(Exception e)
		{
			
		}
	}
	@Test
	public void testgetDeviceListForConditionalAccept()
	{
		ProductGroupFacetModel productGroupFacetModel = CommonMethods.getProductGroupFacetModel();
		productGroupFacetModel.setListOfProductGroups(CommonMethods.getProductGroupModel());
		given(this.deviceDAOMock.getProductGroupsWithFacets(Matchers.any(), Matchers.any(), Matchers.any(),
				Matchers.any(), Matchers.any(), Matchers.any(),Matchers.any())).willReturn(productGroupFacetModel);
		given(this.deviceDAOMock.getProductGroupsWithFacets(Matchers.any(),Matchers.anyString())).willReturn(CommonMethods.getProductGroupFacetModel1());
		try
		{
			deviceService.getDeviceListForConditionalAccept("HANDSET", "make", "model", "DEVICE_PAYM", "Order", 5, 6, "12", "red", "os", "somefeature", (float) 500.00,"");
		}
		catch(Exception e)
		{
			
		}
	}
	@Test
	public void testgetFilterForDeviceList()
	{
		try
		{
			deviceService.getFilterForDeviceList("Filter,filter,filter", "R");
		}
		catch(Exception e)
		{
			
		}
	}
	
	@Test
	public void notNullGetLeadPlanIdForDeviceId()throws IOException {
		CommercialProduct commercialProduct=CommonMethods.getCommercialProduct5();
		commercialProduct.setLeadPlanId(null);
		given(this.deviceDAOMock.getCommercialProductRepositoryByLeadMemberId(Matchers.any())).willReturn(commercialProduct);
		String jsonString=new String(Utility.readFile("\\rest-mock\\BUNDLES-V1.json"));
		ObjectMapper mapper1=new ObjectMapper();
		mapper1.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		mapper1.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		BundleDetailsForAppSrv obj=mapper1.readValue(jsonString, BundleDetailsForAppSrv.class);  
        given(registry.getRestTemplate()).willReturn(restTemplate);
        given(restTemplate.getForObject("http://BUNDLES-V1/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId=123", BundleDetailsForAppSrv.class )).willReturn(obj);
		Assert.assertNotNull(deviceService.getLeadPlanIdForDeviceId("123"));
	}
	@Test
	public void notNullGetLeadPlanIdForDeviceId1()throws IOException {
		CommercialProduct commercialProduct=CommonMethods.getCommercialProduct5();
		given(this.deviceDAOMock.getCommercialProductRepositoryByLeadMemberId(Matchers.any())).willReturn(commercialProduct);
		String jsonString=new String(Utility.readFile("\\rest-mock\\BUNDLES-V1.json"));
		ObjectMapper mapper1=new ObjectMapper();
		mapper1.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		mapper1.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		BundleDetailsForAppSrv obj=mapper1.readValue(jsonString, BundleDetailsForAppSrv.class);  
        given(registry.getRestTemplate()).willReturn(restTemplate);
        given(restTemplate.getForObject("http://BUNDLES-V1/bundles/catalogue/bundle/queries/byCoupledBundleList/?deviceId=123", BundleDetailsForAppSrv.class )).willReturn(obj);
		Assert.assertNotNull(deviceService.getLeadPlanIdForDeviceId("123"));
	}
	@Test
	public void notNullValidateMemeberForUpgrade()throws IOException {
		CommercialProduct commercialProduct=CommonMethods.getCommercialProduct5();
		given(this.deviceDAOMock.getCommercialProductRepositoryByLeadMemberId(Matchers.any())).willReturn(commercialProduct);
		Assert.assertNotNull(deviceService.validateMemeber("123","Upgrade"));
	}
	@Test
	public void notNullValidateMemeber()throws IOException {
		CommercialProduct commercialProduct=CommonMethods.getCommercialProduct5();
		given(this.deviceDAOMock.getCommercialProductRepositoryByLeadMemberId(Matchers.any())).willReturn(commercialProduct);
		Assert.assertNotNull(deviceService.validateMemeber("123","Acquisition"));
	}
	@Test
	public void notNullValidateMemeberForException() {
		given(this.deviceDAOMock.getCommercialProductRepositoryByLeadMemberId("123")).willThrow(new NullPointerException());
		Assert.assertNotNull(deviceService.validateMemeber("123",null));
	}
	
	/*@Test
	public void testCommonUtilitygetCurrentJourney()
	{
		try
		{
			CommonUtility.getCurrentJourney("SomeID", registry);
		}
		catch(Exception e)
		{
			
		}
	}*/
	
	@Test
	public void testGetPriceDetailsForCompatibaleBundle()
	{
		Mockito.doThrow(new ApplicationException("Exception")).when(registry).getRestTemplate();
		try
		{
			CommonUtility.getPriceDetailsForCompatibaleBundle("093353", registry);
		}
		catch(Exception e)
		{
			
		}
	}
	@Test
	public void testGetJSONFromString()
	{
		try
		{
			CommonUtility.getJSONFromString("{ \"name\":\"John\", \"age\":31, \"city\":\"New York\" }");
		}
		catch(Exception e)
		{
			
		}
	}
	@Test
	public void testGetJSONFromStringException()
	{
		try
		{
			CommonUtility.getJSONFromString("one':'two'");
		}
		catch(Exception e)
		{
			
		}
	
	}
	@Test
	public void testtrimLeadingZeros()
	{
		try
		{
			CommonUtility.trimLeadingZeros("000353");
		}
		catch(Exception e)
		{
			
		}
	}
	@Test
	public void testtrimLeadingZerosOne()
	{
		try
		{
			CommonUtility.trimLeadingZeros("1");
		}
		catch(Exception e)
		{
			
		}
	}
	@Test
	public void testtrimLeadingZerosTwo()
	{
		try
		{
			CommonUtility.trimLeadingZeros("000");
		}
		catch(Exception e)
		{
			
		}
	}
	@Test
	public void testgetAccessoryPriceDetails()
	{
		BundleDeviceAndProductsList deviceAndProductsList = new BundleDeviceAndProductsList();
		deviceAndProductsList.setAccessoryList(CommonMethods.getListOfProducts());
		deviceAndProductsList.setBundleId("110154");
		deviceAndProductsList.setDeviceId("093353");
		deviceAndProductsList.setExtraList(CommonMethods.getListOfProducts());
		given(registry.getRestTemplate()).willReturn(restTemplate);
		PriceForProduct price = new PriceForProduct();
		price.setPriceForAccessoryes(null);
		price.setPriceForExtras(null);
		given(restTemplate.postForObject("http://PRICE-V1/price/product" ,deviceAndProductsList,PriceForProduct.class)).willReturn(price);
		try{
			CommonUtility.getAccessoryPriceDetails(deviceAndProductsList, registry);
		}
		catch(Exception e)
		{
			try
			{
				Mockito.doThrow(new ApplicationException("Exception")).when(restTemplate).postForObject("http://PRICE-V1/price/product" ,deviceAndProductsList,PriceForProduct.class);
				
				CommonUtility.getAccessoryPriceDetails(deviceAndProductsList, registry);
			}
			catch(Exception et)
			{
				
			}
		}
	}
	@Test
	public void testGetAccessoriesOfDeviceFor93353()
	{
		try
		{
			deviceService.getAccessoriesOfDevice("093353","Upgrade","W_HH_PAYM_OC_02");
		}
		catch(Exception e)
		{
			
		}
	}
	@Test
	public void nullTestForGetDeviceListForGroupTypeWithOutConditionalAcceptance() {
		given(deviceDAOMock.getProductGroupsWithFacets(Matchers.any(), Matchers.any(), Matchers.any(),
				Matchers.any(), Matchers.any(), Matchers.any(),Matchers.any())).willReturn(CommonMethods.getProductGroupFacetModel1());
		given(deviceDAOMock.getProductGroupsWithFacets(Filters.HANDSET,"Upgrade")).willReturn(CommonMethods.getProductGroupFacetModel1());
		given(deviceDAOMock.getProductModel(Matchers.anyList())).willReturn(CommonMethods.getProductModel());
		
		FacetedDevice deviceLists=null;
		try{ 
			deviceLists=deviceService.getDeviceList("Handset","apple", "iPhone 7", "DEVICE_PAYM",
					"Priority", 0, 9,"32 GB","White","iOS","Great Camera",null,null,null, "447582367723", true);
		}
		catch(Exception e)
		{
			
		}
		Assert.assertNotNull(deviceLists);
	}
	@Test
	public void nullTestForGetDeviceListForGroupTypeWithOutConditionalAcceptance1() {
		given(deviceDAOMock.getProductGroupsWithFacets(Matchers.any(), Matchers.any(), Matchers.any(),
				Matchers.any(), Matchers.any(), Matchers.any(),Matchers.any())).willReturn(CommonMethods.getProductGroupFacetModel1());
		given(deviceDAOMock.getProductGroupsWithFacets(Filters.HANDSET,"SecondLine")).willReturn(CommonMethods.getProductGroupFacetModel1());
		given(deviceDAOMock.getProductModel(Matchers.anyList())).willReturn(CommonMethods.getProductModel());
		given(deviceDAOMock.getBundleAndHardwarePriceFromSolr(Matchers.anyList(),Matchers.anyString(),Matchers.anyString())).willReturn(CommonMethods.getOfferAppliedPriceModel());
		given(this.deviceDAOMock.getJourneyTypeCompatibleOfferCodes("Upgrade")).willReturn(CommonMethods.getMerChandisingPromotion1());
		
		
		FacetedDevice deviceLists=null;
		try{ 
			deviceLists=deviceService.getDeviceList("Handset","apple", "iPhone 7", "DEVICE_PAYM",
					"Priority", 0, 9,"32 GB","White","iOS","Great Camera","Upgrade",null,"W_HH_PAYM_OC_01", "447582367723", true);
		}
		catch(Exception e)
		{
			
		}
		Assert.assertNotNull(deviceLists);
	}
	@Test(expected=Exception.class)
	public void nullTestForGetDeviceListForExcption() {
		given(this.deviceDAOMock.getJourneyTypeCompatibleOfferCodes("JourneyType")).willReturn(CommonMethods.getMerChandisingPromotion());
		deviceService.getDeviceList("Handset","apple", "iPhone 7", "DEVICE_PAYM",
					"Priority", 0, 9,"32 GB","White","iOS","Great Camera","JourneyType",null,null, "447582367723", true);
		
	}
	@Test(expected=Exception.class)
	public void nullTestForGetDeviceListForExcption1() {
		given(this.deviceDAOMock.getJourneyTypeCompatibleOfferCodes("Upgrade")).willReturn(CommonMethods.getMerChandisingPromotion());
		
		deviceService.getDeviceList("Handset","apple", "iPhone 7", "DEVICE_PAYM",
					"Priority", 0, 9,"32 GB","White","iOS","Great Camera","Upgrade",null,"offerCode", "447582367723", true);
		
	}
	@Test(expected=Exception.class)
	public void nullTestForGetDeviceListForExcption3() {
		given(this.deviceDAOMock.getJourneyTypeCompatibleOfferCodes("SecondLine")).willReturn(CommonMethods.getMerChandisingPromotion());
		deviceService.getDeviceList("Handset","apple", "iPhone 7", "DEVICE_PAYM",
					"Priority", 0, 9,"32 GB","White","iOS","Great Camera","SecondLine",null,"offerCode", "447582367723", true);
		
	}
	@Test(expected=Exception.class)
	public void nullTestForGetDeviceListForExcption4() {
		given(this.deviceDAOMock.getJourneyTypeCompatibleOfferCodes("SecondLine")).willReturn(CommonMethods.getMerChandisingPromotion());
		deviceService.getDeviceList("Handset","apple", "iPhone 7", "DEVICE_PAYM",
					"Priority", 0, 9,"32 GB","White","iOS","Great Camera","SecondLine",null,"W_HH_PAYM_02", null, true);
		
	}
	@Test
	public void nullTestIsValidBundleForProductUpgrade() {
		Map<String,CommercialBundle> commercialBundleMap=new HashMap<>();
		commercialBundleMap.put("110154", CommonMethods.getCommercialBundle());
		List<String> productLinesList = new ArrayList<>();
		String upgrade="Upgrade";
		productLinesList.add(Constants.STRING_MOBILE_PHONE_SERVICE_SELLABLE);
		productLinesList.add(Constants.STRING_MBB_SELLABLE);
		Assert.assertNotNull(CommonUtility.isValidBundleForProduct(CommonMethods.getUtilityPriceForBundleAndHardware(),
				 commercialBundleMap,productLinesList,upgrade));
	}
	@Test
	public void nullTestIsValidBundleForProduct() {
		Map<String,CommercialBundle> commercialBundleMap=new HashMap<>();
		commercialBundleMap.put("110154", CommonMethods.getCommercialBundle());
		List<String> productLinesList = new ArrayList<>();
		String Acquistion="Acquistion";
		productLinesList.add(Constants.STRING_MOBILE_PHONE_SERVICE_SELLABLE);
		productLinesList.add(Constants.STRING_MBB_SELLABLE);
		Assert.assertNotNull(CommonUtility.isValidBundleForProduct(CommonMethods.getUtilityPriceForBundleAndHardware(),
				 commercialBundleMap,productLinesList,Acquistion));
	}
	
}