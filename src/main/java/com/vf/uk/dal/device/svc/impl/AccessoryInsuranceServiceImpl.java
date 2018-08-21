package com.vf.uk.dal.device.svc.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.dao.DeviceTileCacheDAO;
import com.vf.uk.dal.device.datamodel.product.CommercialProduct;
import com.vf.uk.dal.device.datamodel.product.ProductGroups;
import com.vf.uk.dal.device.datamodel.productgroups.Group;
import com.vf.uk.dal.device.datamodel.productgroups.Member;
import com.vf.uk.dal.device.entity.Accessory;
import com.vf.uk.dal.device.entity.AccessoryTileGroup;
import com.vf.uk.dal.device.entity.Insurance;
import com.vf.uk.dal.device.entity.Insurances;
import com.vf.uk.dal.device.entity.Price;
import com.vf.uk.dal.device.helper.DeviceESHelper;
import com.vf.uk.dal.device.svc.AccessoryInsuranceService;
import com.vf.uk.dal.device.svc.DeviceRecommendationService;
import com.vf.uk.dal.device.utils.AccessoriesAndInsurancedaoUtils;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.Constants;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.device.utils.ResponseMappingHelper;
import com.vf.uk.dal.utility.entity.BundleDeviceAndProductsList;
import com.vf.uk.dal.utility.entity.PriceForAccessory;
import com.vf.uk.dal.utility.entity.PriceForProduct;
/**
 * 
 * Accessory Insurance Service Impl
 *
 */
@Component
public class AccessoryInsuranceServiceImpl implements AccessoryInsuranceService {

	@Autowired
	DeviceDao deviceDao;

	@Autowired
	ResponseMappingHelper response;

	@Autowired
	DeviceESHelper deviceEs;

	@Autowired
	DeviceTileCacheDAO deviceTileCacheDAO;

	@Autowired
	DeviceRecommendationService deviceRecommendationService;
	
	@Autowired
	CommonUtility commonUtility;

	/**
	 * Handles requests from controller and connects to DAO.
	 * 
	 * @param deviceId
	 * @return List<Accessory>
	 */

	@Override
	public List<AccessoryTileGroup> getAccessoriesOfDevice(String deviceId, String journeyType, String offerCode) {
		List<AccessoryTileGroup> listOfAccessoryTileGroup;
		listOfAccessoryTileGroup = getAccessoriesOfDevice_Implementation(deviceId, journeyType, offerCode);
		return listOfAccessoryTileGroup;
	}

	/**
	 * 
	 * @param deviceId
	 * @param journeyType
	 * @param offerCode
	 * @return List<AccessoryTileGroup>
	 */
	public List<AccessoryTileGroup> getAccessoriesOfDevice_Implementation(String deviceId, String journeyType,
			String offerCode) {
		List<AccessoryTileGroup> listOfAccessoryTile = new ArrayList<>();

		CommercialProduct commercialProduct = deviceEs.getCommercialProduct(deviceId);
		LogHelper.info(this, "End -->  After calling  CommercialProductRepository.get");

		if (commercialProduct != null && commercialProduct.getId() != null) {

			if (commercialProduct.getIsDeviceProduct()
					&& commercialProduct.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)) {

				LogHelper.info(this, "Start -->  calling  CommercialProduct.getProductGroups");
				ProductGroups productGroups = commercialProduct.getProductGroups();
				LogHelper.info(this, "End -->  After calling  CommercialProduct.getProductGroups");

				List<String> listOfDeviceGroupName = new ArrayList<>();
				List<String> finalAccessoryList = new ArrayList<>();
				if (productGroups != null && productGroups.getProductGroup() != null
						&& !productGroups.getProductGroup().isEmpty()) {

					getAccessoriesResponse(deviceId, journeyType, offerCode, listOfAccessoryTile, productGroups,
							listOfDeviceGroupName, finalAccessoryList);
				} else {
					LogHelper.error(this, "No Compatible Accessories found for given device Id:" + deviceId);
					throw new ApplicationException(ExceptionMessages.NULL_COMPATIBLE_VALUE_FOR_DEVICE_ID);
				}

			} else {
				LogHelper.error(this, "Given DeviceId is not ProductClass Handset  :" + deviceId);
				throw new ApplicationException(ExceptionMessages.DEVICE_ID_NOT_HANDSET);
			}
		} else {
			LogHelper.error(this, "No data found for given device Id:" + deviceId);
			throw new ApplicationException(ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID);
		}
		if (listOfAccessoryTile.isEmpty()) {
			LogHelper.error(this, "No Compatible Accessories found for given device Id:" + deviceId);
			throw new ApplicationException(ExceptionMessages.NULL_COMPATIBLE_VALUE_FOR_DEVICE_ID);
		}
		return listOfAccessoryTile;
	}

	/**
	 * 
	 * @param deviceId
	 * @param journeyType
	 * @param offerCode
	 * @param listOfAccessoryTile
	 * @param productGroups
	 * @param listOfDeviceGroupName
	 * @param finalAccessoryList
	 */
	public void getAccessoriesResponse(String deviceId, String journeyType, String offerCode,
			List<AccessoryTileGroup> listOfAccessoryTile, ProductGroups productGroups,
			List<String> listOfDeviceGroupName, List<String> finalAccessoryList) {
		getListOfDeviceGroupName(deviceId, productGroups, listOfDeviceGroupName);

		Map<String, List<String>> mapForGroupName = new LinkedHashMap<>();
		List<Group> listOfProductGroup = deviceEs.getProductGroupByListOfGroupName(listOfDeviceGroupName);
		listOfProductGroup = getGroupBasedOnPriority(listOfProductGroup);
		for (Group productGroup : listOfProductGroup) {
			getFinalAccessoryList(finalAccessoryList, mapForGroupName, productGroup);
		}
		LogHelper.info(this, "Start -->   calling  CommercialProduct.getAll From ES");
		List<CommercialProduct> listOfFilteredAccessories = getListOfFilteredAccessories(journeyType,
				finalAccessoryList);

		List<String> listOfValidAccesoryIds = listOfFilteredAccessories.stream().filter(Objects::nonNull)
				.map(CommercialProduct::getId).filter(Objects::nonNull).collect(Collectors.toList());

		BundleDeviceAndProductsList bundleDeviceAndProductsList = setBundleDeviceAndProductsList(journeyType, deviceId,
				offerCode, listOfValidAccesoryIds);
		PriceForProduct priceForProduct = null;
		priceForProduct = commonUtility.getAccessoryPriceDetails(bundleDeviceAndProductsList);

		Map<String, PriceForAccessory> mapforPrice = new HashMap<>();
		Map<String, CommercialProduct> mapforCommercialProduct = setMapForCommercialData(listOfFilteredAccessories,
				listOfValidAccesoryIds, priceForProduct, mapforPrice);

		setListOfAccessoryTileGroup(listOfAccessoryTile, mapForGroupName, mapforPrice, mapforCommercialProduct);
	}

	/**
	 * 
	 * @param journeyType
	 * @param finalAccessoryList
	 * @return List<CommercialProduct>
	 */
	public List<CommercialProduct> getListOfFilteredAccessories(String journeyType, List<String> finalAccessoryList) {
		List<CommercialProduct> comercialProductList = deviceEs.getListOfCommercialProduct(finalAccessoryList);
		return comercialProductList.stream()
				.filter(commercialProductAccessories -> CommonUtility.isProductNotExpired(commercialProductAccessories)
						&& CommonUtility.isProductJourneySpecific(commercialProductAccessories, journeyType))
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @param deviceId
	 * @param journeyType
	 * @param offerCode
	 * @param listOfValidAccesoryIds
	 * @return Insurances
	 */
	@Override
	public Insurances getInsuranceByDeviceId(String deviceId, String journeyType) {
		Insurances insurance;
		CommercialProduct cohProduct = deviceEs.getCommercialProduct(deviceId);
		if (cohProduct != null) {
			insurance = getInsuranceResponse(deviceId, journeyType, cohProduct);
		} else {
			LogHelper.error(this, "No data found for given Device Id :" + deviceId);
			throw new ApplicationException(ExceptionMessages.NULL_COMPATIBLE_INSURANCES_FOR_DEVICE_ID);
		}
		validateInsuranceNullable(deviceId, insurance);
		return insurance;
	}

	/**
	 * 
	 * @param deviceId
	 * @param journeyType
	 * @param cohProduct
	 * @return Insurances
	 */
	public Insurances getInsuranceResponse(String deviceId, String journeyType, CommercialProduct cohProduct) {
		Insurances insurance;
		if (cohProduct.getIsDeviceProduct()
				&& cohProduct.getProductClass().equalsIgnoreCase(Constants.STRING_HANDSET)) {
			insurance = getInsurance(journeyType, cohProduct);
		} else {
			LogHelper.error(this, "Given DeviceId is not ProductClass Handset  :" + deviceId);
			throw new ApplicationException(ExceptionMessages.DEVICE_ID_NOT_HANDSET);
		}
		return insurance;
	}

	/**
	 * 
	 * @param journeyType
	 * @param cohProduct
	 * @return Insurances
	 */
	public Insurances getInsurance(String journeyType, CommercialProduct cohProduct) {
		ProductGroups productGroups = cohProduct.getProductGroups();
		Insurances insurance = null;
		String insuranceGroupName = null;
		String insuranceGroupType = null;
		List<Member> listOfInsuranceMembers = new ArrayList<>();
		if (productGroups != null && productGroups.getProductGroup() != null
				&& !productGroups.getProductGroup().isEmpty()) {
			for (com.vf.uk.dal.device.datamodel.product.ProductGroup productGroup : productGroups.getProductGroup()) {
				if (productGroup.getProductGroupRole() != null && productGroup.getProductGroupRole().trim()
						.equalsIgnoreCase(Constants.STRING_COMPATIBLE_INSURANCE)) {
					insuranceGroupName = productGroup.getProductGroupName();
					insuranceGroupType = productGroup.getProductGroupRole();
				}
			}
			LogHelper.info(this, "::::: Insurance GroupName " + insuranceGroupName + " ::::::");
			if (StringUtils.isNotBlank(insuranceGroupName)) {

				Group productGroup = deviceEs.getProductGroupByTypeAndGroupName(insuranceGroupName, insuranceGroupType);
				List<String> insuranceProductList = getInsuranceProductList(listOfInsuranceMembers, productGroup);
				List<CommercialProduct> listOfInsuranceProducts = deviceEs
						.getListOfCommercialProduct(insuranceProductList);
				insurance = getListOfFilteredInsurance(journeyType, listOfInsuranceProducts);
			}
		}
		return insurance;
	}

	public static BundleDeviceAndProductsList setBundleDeviceAndProductsList(String journeyType,String deviceId,
			String offerCode, List<String> listOfValidAccesoryIds) {
		BundleDeviceAndProductsList bundleDeviceAndProductsList = new BundleDeviceAndProductsList();
		bundleDeviceAndProductsList.setAccessoryList(listOfValidAccesoryIds);
		bundleDeviceAndProductsList.setDeviceId(deviceId);
		bundleDeviceAndProductsList.setExtraList(new ArrayList<>());
		bundleDeviceAndProductsList.setOfferCode(offerCode);
		bundleDeviceAndProductsList.setPackageType(journeyType);
		return bundleDeviceAndProductsList;
	}

	/**
	 * 
	 * @param listOfGroup
	 * @return List<Group>
	 */
	public static List<Group> getGroupBasedOnPriority(List<Group> listOfGroup) {
		Collections.sort(listOfGroup, new SortedExtrasGroupPriorityList());

		return listOfGroup;
	}

	/**
	 * 
	 * @author manoj.bera
	 *
	 */
	static class SortedExtrasGroupPriorityList implements Comparator<Group> {

		@Override
		public int compare(Group member1, Group member2) {
			if (member1.getGroupPriority() != null && member2.getGroupPriority() != null) {
				if (member1.getGroupPriority() < member2.getGroupPriority()) {
					return -1;
				} else
					return 1;
			}

			else
				return -1;
		}

	}

	/**
	 * 
	 * @param productGroup
	 * @return List<Member>
	 */
	public static List<Member> getListOfAccessoriesMembers(Group productGroup) {
		List<Member> listOfAccesoriesMembers = new ArrayList<>();
		if (productGroup != null
				&& StringUtils.containsIgnoreCase(Constants.STRING_ACCESSORY, productGroup.getGroupType())) {
			listOfAccesoriesMembers.addAll(productGroup.getMembers());
			if (!listOfAccesoriesMembers.isEmpty()) {
				listOfAccesoriesMembers = getAccessoryMembersBasedOnPriority_Implementation(listOfAccesoriesMembers);
			}
		}
		return listOfAccesoriesMembers;
	}

	/**
	 * 
	 * @param listOfDeviceGroupMember
	 * @return List<Member>
	 */
	public static List<Member> getAccessoryMembersBasedOnPriority_Implementation(List<Member> listOfDeviceGroupMember) {
		Collections.sort(listOfDeviceGroupMember, new SortedAccessoryPriorityList());

		return listOfDeviceGroupMember;
	}

	static class SortedAccessoryPriorityList implements Comparator<Member> {

		@Override
		public int compare(Member member1, Member member2) {

			if (member1.getPriority() != null && member2.getPriority() != null) {
				if (member1.getPriority() < member2.getPriority()) {
					return -1;
				} else
					return 1;

			}

			else
				return -1;
		}

	}

	/**
	 * 
	 * @param finalAccessoryList
	 * @param mapForGroupName
	 * @param productGroup
	 */
	public static void getFinalAccessoryList(List<String> finalAccessoryList, Map<String, List<String>> mapForGroupName,
			Group productGroup) {
		List<Member> listOfAccesoriesMembers = getListOfAccessoriesMembers(productGroup);
		List<String> accessoryList = new ArrayList<>();
		if (!listOfAccesoriesMembers.isEmpty()) {
			for (com.vf.uk.dal.device.datamodel.productgroups.Member member : listOfAccesoriesMembers) {
				if (member.getId() != null) {
					accessoryList.add(member.getId().trim());
				}
			}
			if (productGroup != null && StringUtils.isNotBlank(productGroup.getName())) {
				mapForGroupName.put(productGroup.getName(), accessoryList);
			}
			finalAccessoryList.addAll(accessoryList);
		}
	}

	/**
	 * 
	 * @param listOfAccessoryTile
	 * @param mapForGroupName
	 * @param mapforPrice
	 * @param mapforCommercialProduct
	 */
	public static void setListOfAccessoryTileGroup(List<AccessoryTileGroup> listOfAccessoryTile,
			Map<String, List<String>> mapForGroupName, Map<String, PriceForAccessory> mapforPrice,
			Map<String, CommercialProduct> mapforCommercialProduct) {
		for (Map.Entry<String, List<String>> entry : mapForGroupName.entrySet()) {
			AccessoryTileGroup accessoryTileGroup = new AccessoryTileGroup();
			List<Accessory> listOfAccessory = new ArrayList<>();

			for (String hardwareId : entry.getValue()) {
				Accessory accessory = null;
				if (mapforCommercialProduct.containsKey(hardwareId) && mapforPrice.containsKey(hardwareId)) {
					accessory = AccessoriesAndInsurancedaoUtils.convertCoherenceAccesoryToAccessory(
							mapforCommercialProduct.get(hardwareId), mapforPrice.get(hardwareId));
				}
				if (accessory != null) {
					listOfAccessory.add(accessory);
				}
			}
			if (!listOfAccessory.isEmpty()) {
				accessoryTileGroup.setGroupName(entry.getKey());
				accessoryTileGroup.setAccessories(listOfAccessory);
				listOfAccessoryTile.add(accessoryTileGroup);
			} else {
				LogHelper.error(AccessoryInsuranceServiceImpl.class,
						"Accessories not found for the given :" + entry.getKey());
			}
		}
	}

	/**
	 * 
	 * @param listOfFilteredAccessories
	 * @param listOfValidAccesoryIds
	 * @param priceForProduct
	 * @param mapforPrice
	 * @return CommercialProduct
	 */
	public static Map<String, CommercialProduct> setMapForCommercialData(
			List<CommercialProduct> listOfFilteredAccessories, List<String> listOfValidAccesoryIds,
			PriceForProduct priceForProduct, Map<String, PriceForAccessory> mapforPrice) {
		if (priceForProduct != null && priceForProduct.getPriceForAccessoryes() != null) {
			for (PriceForAccessory priceForAccessory : priceForProduct.getPriceForAccessoryes()) {
				String hardwareId = priceForAccessory.getHardwarePrice().getHardwareId();
				if (listOfValidAccesoryIds.contains(hardwareId))
					mapforPrice.put(hardwareId, priceForAccessory);
			}
		} else {
			LogHelper.info(AccessoryInsuranceServiceImpl.class, "Null values received from Price API");
			throw new ApplicationException(ExceptionMessages.NULL_VALUES_FROM_PRICING_API);
		}

		Map<String, CommercialProduct> mapforCommercialProduct = new HashMap<>();
		for (CommercialProduct product : listOfFilteredAccessories) {
			String id = product.getId();
			if (listOfValidAccesoryIds.contains(id))
				mapforCommercialProduct.put(id, product);
		}
		return mapforCommercialProduct;
	}

	/**
	 * 
	 * @param deviceId
	 * @param productGroups
	 * @param listOfDeviceGroupName
	 */
	public static void getListOfDeviceGroupName(String deviceId, ProductGroups productGroups,
			List<String> listOfDeviceGroupName) {
		for (com.vf.uk.dal.device.datamodel.product.ProductGroup productGroup : productGroups.getProductGroup()) {
			if (productGroup.getProductGroupRole().equalsIgnoreCase(Constants.STRING_COMPATIBLE_ACCESSORIES)) {
				listOfDeviceGroupName.add(productGroup.getProductGroupName());
			}
		}

		if (listOfDeviceGroupName.isEmpty()) {
			LogHelper.error(AccessoryInsuranceServiceImpl.class,
					" No Compatible Accessories found for given device Id:" + deviceId);
			throw new ApplicationException(ExceptionMessages.NULL_COMPATIBLE_VALUE_FOR_DEVICE_ID);
		}
	}

	/**
	 * 
	 * @param deviceId
	 * @param insurance
	 */
	public static void validateInsuranceNullable(String deviceId, Insurances insurance) {
		if (insurance != null && !insurance.getInsuranceList().isEmpty()) {
			getFormattedPriceForGetCompatibleInsurances(insurance);
			insurance.setMinCost(FormatPrice(insurance.getMinCost()));
		} else {
			LogHelper.error(AccessoryInsuranceServiceImpl.class,
					"No Compatible Insurances found for given device Id" + deviceId);
			throw new ApplicationException(ExceptionMessages.NULL_COMPATIBLE_INSURANCES_FOR_DEVICE_ID);
		}
	}

	/**
	 * 
	 * @param insurances
	 * @return Insurances
	 */
	public static Insurances getFormattedPriceForGetCompatibleInsurances(Insurances insurances) {

		if (insurances.getInsuranceList() != null && !insurances.getInsuranceList().isEmpty()) {
			List<Insurance> insuranceList = insurances.getInsuranceList();
			for (Insurance insurance : insuranceList) {
				Price price = insurance.getPrice();
				if (price != null) {
					setValuesForPrice(price);
					insurance.setPrice(price);
				}
			}
		}
		return insurances;
	}

	private static void setValuesForPrice(Price price) {
		if (StringUtils.isNotBlank(price.getNet())) {
			price.setNet(FormatPrice(price.getNet()));
		}
		if (StringUtils.isNotBlank(price.getVat())) {
			price.setVat(FormatPrice(price.getVat()));
		}
		if (StringUtils.isNotBlank(price.getGross())) {
			price.setGross(FormatPrice(price.getGross()));
		}
	}

	/**
	 * 
	 * @param price
	 * @return
	 */
	public static String FormatPrice(String price) {
		if (price.contains(".")) {
			String[] decimalSplit = price.split("\\.");
			String beforeDecimal = decimalSplit[0];
			String afterDecimal = decimalSplit[1];

			if (afterDecimal.length() == 1 && "0".equals(afterDecimal)) {
				return beforeDecimal;
			} else if (afterDecimal.length() == 1) {
				afterDecimal += "0";
				return beforeDecimal + "." + afterDecimal;
			}
		}
		return price;
	}

	/**
	 * 
	 * @param journeyType
	 * @param listOfInsuranceProducts
	 * @return Insurances
	 */
	public static Insurances getListOfFilteredInsurance(String journeyType,
			List<CommercialProduct> listOfInsuranceProducts) {
		Insurances insurance = null;
		List<CommercialProduct> listOfFilteredInsurances = listOfInsuranceProducts.stream()
				.filter(commercialProduct -> CommonUtility.isProductNotExpired(commercialProduct)
						&& CommonUtility.isProductJourneySpecific(commercialProduct, journeyType))
				.collect(Collectors.toList());
		if (listOfFilteredInsurances != null && !listOfFilteredInsurances.isEmpty()) {
			insurance = AccessoriesAndInsurancedaoUtils.convertCommercialProductToInsurance(listOfFilteredInsurances);
		}
		return insurance;
	}

	/**
	 * 
	 * @param listOfInsuranceMembers
	 * @param productGroup
	 * @return List<String>
	 */
	public static List<String> getInsuranceProductList(List<Member> listOfInsuranceMembers, Group productGroup) {
		if (productGroup != null && productGroup.getGroupType() != null
				&& productGroup.getGroupType().trim().equalsIgnoreCase(Constants.STRING_COMPATIBLE_INSURANCE)) {
			listOfInsuranceMembers.addAll(productGroup.getMembers());
		}

		List<String> insuranceProductList = new ArrayList<>();
		if (listOfInsuranceMembers != null && !listOfInsuranceMembers.isEmpty()) {
			for (com.vf.uk.dal.device.datamodel.productgroups.Member member : listOfInsuranceMembers) {
				if (member.getId() != null) {
					insuranceProductList.add(member.getId().trim());
				}
			}
		}
		return insuranceProductList;
	}
}
