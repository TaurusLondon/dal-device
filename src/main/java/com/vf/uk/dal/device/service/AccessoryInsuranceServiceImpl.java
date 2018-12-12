package com.vf.uk.dal.device.service;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.device.client.converter.ResponseMappingHelper;
import com.vf.uk.dal.device.client.entity.price.BundleDeviceAndProductsList;
import com.vf.uk.dal.device.client.entity.price.Price;
import com.vf.uk.dal.device.client.entity.price.PriceForAccessory;
import com.vf.uk.dal.device.client.entity.price.PriceForProduct;
import com.vf.uk.dal.device.dao.DeviceDao;
import com.vf.uk.dal.device.dao.DeviceTileCacheDAO;
import com.vf.uk.dal.device.exception.DeviceCustomException;
import com.vf.uk.dal.device.model.Accessory;
import com.vf.uk.dal.device.model.AccessoryTileGroup;
import com.vf.uk.dal.device.model.Insurance;
import com.vf.uk.dal.device.model.Insurances;
import com.vf.uk.dal.device.model.product.CommercialProduct;
import com.vf.uk.dal.device.model.product.ProductGroups;
import com.vf.uk.dal.device.model.productgroups.Group;
import com.vf.uk.dal.device.model.productgroups.Member;
import com.vf.uk.dal.device.utils.AccessoriesAndInsurancedaoUtils;
import com.vf.uk.dal.device.utils.CommonUtility;
import com.vf.uk.dal.device.utils.DeviceESHelper;
import com.vf.uk.dal.device.utils.ExceptionMessages;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Accessory Insurance Service Impl
 *
 */
@Slf4j
@Component
public class AccessoryInsuranceServiceImpl implements AccessoryInsuranceService {

	public static final String STRING_HANDSET = "Handset";
	public static final String STRING_COMPATIBLE_INSURANCE = "Compatible Insurance";
	public static final String STRING_ACCESSORY = "Accessory,Compatible Accessories";
	public static final String STRING_COMPATIBLE_ACCESSORIES = "Compatible Accessories";
	private static final String ERROR_CODE_SELECT_DEVICE = "error_device_accessory_failed";
	private static final String ERROR_CODE_SELECT_DEVICE_INSURANCE = "error_device_insurance_failed";

	@Autowired
	DeviceDao deviceDao;
	
	@Autowired
	AccessoriesAndInsurancedaoUtils accessoriesAndInsurancedaoUtils;

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

	@Value("${cdn.domain.host}")
	private String cdnDomain;

	/**
	 * Handles requests from controller and connects to DAO.
	 * 
	 * @param deviceId
	 * @return List<Accessory>
	 */

	@Override
	public List<AccessoryTileGroup> getAccessoriesOfDevice(String deviceId, String journeyType, String offerCode) {
		List<AccessoryTileGroup> listOfAccessoryTileGroup;
		listOfAccessoryTileGroup = getAccessoriesOfDeviceImplementation(deviceId, journeyType, offerCode);
		return listOfAccessoryTileGroup;
	}

	/**
	 * 
	 * @param deviceId
	 * @param journeyType
	 * @param offerCode
	 * @return List<AccessoryTileGroup>
	 */
	public List<AccessoryTileGroup> getAccessoriesOfDeviceImplementation(String deviceId, String journeyType,
			String offerCode) {
		List<AccessoryTileGroup> listOfAccessoryTile = new ArrayList<>();

		CommercialProduct commercialProduct = deviceEs.getCommercialProduct(deviceId);
		log.info("End -->  After calling  CommercialProductRepository.get");

		if (commercialProduct != null && commercialProduct.getId() != null) {

			if (commercialProduct.getIsDeviceProduct()
					&& commercialProduct.getProductClass().equalsIgnoreCase(STRING_HANDSET)) {

				log.info("Start -->  calling  CommercialProduct.getProductGroups");
				ProductGroups productGroups = commercialProduct.getProductGroups();
				log.info("End -->  After calling  CommercialProduct.getProductGroups");

				List<String> listOfDeviceGroupName = new ArrayList<>();
				List<String> finalAccessoryList = new ArrayList<>();
				if (productGroups != null && productGroups.getProductGroup() != null
						&& !productGroups.getProductGroup().isEmpty()) {

					getAccessoriesResponse(deviceId, journeyType, offerCode, listOfAccessoryTile, productGroups,
							listOfDeviceGroupName, finalAccessoryList);
				} else {
					log.error("No Compatible Accessories found for given device Id:" + deviceId);
					throw new DeviceCustomException(ERROR_CODE_SELECT_DEVICE,ExceptionMessages.NULL_COMPATIBLE_VALUE_FOR_DEVICE_ID,"404");
				}

			} else {
				log.error("Given DeviceId is not ProductClass Handset  :" + deviceId);
				throw new DeviceCustomException(ERROR_CODE_SELECT_DEVICE,ExceptionMessages.DEVICE_ID_NOT_HANDSET,"404");
			}
		} else {
			log.error("No data found for given device Id:" + deviceId);
			throw new DeviceCustomException(ERROR_CODE_SELECT_DEVICE,ExceptionMessages.NULL_VALUE_FROM_COHERENCE_FOR_DEVICE_ID,"404");
		}
		if (listOfAccessoryTile.isEmpty()) {
			log.error("No Compatible Accessories found for given device Id:" + deviceId);
			throw new DeviceCustomException(ERROR_CODE_SELECT_DEVICE,ExceptionMessages.NULL_COMPATIBLE_VALUE_FOR_DEVICE_ID,"404");
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
		log.info("Start -->   calling  CommercialProduct.getAll From ES");
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

		setListOfAccessoryTileGroup(listOfAccessoryTile, mapForGroupName, mapforPrice, mapforCommercialProduct,
				cdnDomain);
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
				.filter(commercialProductAccessories -> commonUtility.isProductNotExpired(commercialProductAccessories)
						&& commonUtility.isProductJourneySpecific(commercialProductAccessories, journeyType))
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
			log.error("No data found for given Device Id :" + deviceId);
			throw new DeviceCustomException(ERROR_CODE_SELECT_DEVICE_INSURANCE,ExceptionMessages.NULL_COMPATIBLE_INSURANCES_FOR_DEVICE_ID,"404");
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
		if (cohProduct.getIsDeviceProduct() && cohProduct.getProductClass().equalsIgnoreCase(STRING_HANDSET)) {
			insurance = getInsurance(journeyType, cohProduct);
		} else {
			log.error("Given DeviceId is not ProductClass Handset  :" + deviceId);
			throw new DeviceCustomException(ERROR_CODE_SELECT_DEVICE_INSURANCE,ExceptionMessages.DEVICE_ID_NOT_HANDSET,"404");
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
			for (com.vf.uk.dal.device.model.product.ProductGroup productGroup : productGroups.getProductGroup()) {
				if (productGroup.getProductGroupRole() != null
						&& productGroup.getProductGroupRole().trim().equalsIgnoreCase(STRING_COMPATIBLE_INSURANCE)) {
					insuranceGroupName = productGroup.getProductGroupName();
					insuranceGroupType = productGroup.getProductGroupRole();
				}
			}
			log.info("::::: Insurance GroupName " + insuranceGroupName + " ::::::");
			if (StringUtils.isNotBlank(insuranceGroupName)) {

				Group productGroup = deviceEs.getProductGroupByTypeAndGroupName(insuranceGroupName, insuranceGroupType);
				List<String> insuranceProductList = getInsuranceProductList(listOfInsuranceMembers, productGroup);
				List<CommercialProduct> listOfInsuranceProducts = deviceEs
						.getListOfCommercialProduct(insuranceProductList);
				insurance = getListOfFilteredInsurance(journeyType, listOfInsuranceProducts, cdnDomain);
			}
		}
		return insurance;
	}

	public BundleDeviceAndProductsList setBundleDeviceAndProductsList(String journeyType, String deviceId,
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
	public List<Group> getGroupBasedOnPriority(List<Group> listOfGroup) {
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
				} else {
					return 1;
				}
			}

			else {
				return -1;
			}
		}

	}

	/**
	 * 
	 * @param productGroup
	 * @return List<Member>
	 */
	public List<Member> getListOfAccessoriesMembers(Group productGroup) {
		List<Member> listOfAccesoriesMembers = new ArrayList<>();
		if (productGroup != null && StringUtils.containsIgnoreCase(STRING_ACCESSORY, productGroup.getGroupType())) {
			listOfAccesoriesMembers.addAll(productGroup.getMembers());
			if (!listOfAccesoriesMembers.isEmpty()) {
				listOfAccesoriesMembers = getAccessoryMembersBasedOnPriorityImplementation(listOfAccesoriesMembers);
			}
		}
		return listOfAccesoriesMembers;
	}

	/**
	 * 
	 * @param listOfDeviceGroupMember
	 * @return List<Member>
	 */
	public List<Member> getAccessoryMembersBasedOnPriorityImplementation(List<Member> listOfDeviceGroupMember) {
		Collections.sort(listOfDeviceGroupMember, new SortedAccessoryPriorityList());

		return listOfDeviceGroupMember;
	}

	static class SortedAccessoryPriorityList implements Comparator<Member> {

		@Override
		public int compare(Member member1, Member member2) {

			if (member1.getPriority() != null && member2.getPriority() != null) {
				if (member1.getPriority() < member2.getPriority()) {
					return -1;
				} else {
					return 1;
				}

			}

			else {
				return -1;
			}
		}

	}

	/**
	 * 
	 * @param finalAccessoryList
	 * @param mapForGroupName
	 * @param productGroup
	 */
	public void getFinalAccessoryList(List<String> finalAccessoryList, Map<String, List<String>> mapForGroupName,
			Group productGroup) {
		List<Member> listOfAccesoriesMembers = getListOfAccessoriesMembers(productGroup);
		List<String> accessoryList = new ArrayList<>();
		if (!listOfAccesoriesMembers.isEmpty()) {
			for (com.vf.uk.dal.device.model.productgroups.Member member : listOfAccesoriesMembers) {
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
	public void setListOfAccessoryTileGroup(List<AccessoryTileGroup> listOfAccessoryTile,
			Map<String, List<String>> mapForGroupName, Map<String, PriceForAccessory> mapforPrice,
			Map<String, CommercialProduct> mapforCommercialProduct, String cdnDomain) {
		for (Map.Entry<String, List<String>> entry : mapForGroupName.entrySet()) {
			AccessoryTileGroup accessoryTileGroup = new AccessoryTileGroup();
			List<Accessory> listOfAccessory = new ArrayList<>();

			for (String hardwareId : entry.getValue()) {
				Accessory accessory = null;
				if (mapforCommercialProduct.containsKey(hardwareId) && mapforPrice.containsKey(hardwareId)) {
					accessory = accessoriesAndInsurancedaoUtils.convertCoherenceAccesoryToAccessory(
							mapforCommercialProduct.get(hardwareId), mapforPrice.get(hardwareId), cdnDomain);
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
				log.error("Accessories not found for the given :" + entry.getKey());
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
	public Map<String, CommercialProduct> setMapForCommercialData(
			List<CommercialProduct> listOfFilteredAccessories, List<String> listOfValidAccesoryIds,
			PriceForProduct priceForProduct, Map<String, PriceForAccessory> mapforPrice) {
		if (priceForProduct != null && priceForProduct.getPriceForAccessoryes() != null) {
			for (PriceForAccessory priceForAccessory : priceForProduct.getPriceForAccessoryes()) {
				String hardwareId = priceForAccessory.getHardwarePrice().getHardwareId();
				if (listOfValidAccesoryIds.contains(hardwareId))
					mapforPrice.put(hardwareId, priceForAccessory);
			}
		} else {
			log.info("Null values received from Price API");
			throw new DeviceCustomException(ERROR_CODE_SELECT_DEVICE,ExceptionMessages.NULL_VALUES_FROM_PRICING_API,"404");
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
	public void getListOfDeviceGroupName(String deviceId, ProductGroups productGroups,
			List<String> listOfDeviceGroupName) {
		for (com.vf.uk.dal.device.model.product.ProductGroup productGroup : productGroups.getProductGroup()) {
			if (productGroup.getProductGroupRole().equalsIgnoreCase(STRING_COMPATIBLE_ACCESSORIES)) {
				listOfDeviceGroupName.add(productGroup.getProductGroupName());
			}
		}

		if (listOfDeviceGroupName.isEmpty()) {
			log.error(" No Compatible Accessories found for given device Id:" + deviceId);
			throw new DeviceCustomException(ERROR_CODE_SELECT_DEVICE,ExceptionMessages.NULL_COMPATIBLE_VALUE_FOR_DEVICE_ID,"404");
		}
	}

	/**
	 * 
	 * @param deviceId
	 * @param insurance
	 */
	public void validateInsuranceNullable(String deviceId, Insurances insurance) {
		if (insurance != null && !insurance.getInsuranceList().isEmpty()) {
			getFormattedPriceForGetCompatibleInsurances(insurance);
			insurance.setMinCost(formatPrice(insurance.getMinCost()));
		} else {
			log.error("No Compatible Insurances found for given device Id" + deviceId);
			throw new DeviceCustomException(ERROR_CODE_SELECT_DEVICE,ExceptionMessages.NULL_COMPATIBLE_INSURANCES_FOR_DEVICE_ID,"404");
		}
	}

	/**
	 * 
	 * @param insurances
	 * @return Insurances
	 */
	public Insurances getFormattedPriceForGetCompatibleInsurances(Insurances insurances) {

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

	private void setValuesForPrice(Price price) {
		if (StringUtils.isNotBlank(price.getNet())) {
			price.setNet(formatPrice(price.getNet()));
		}
		if (StringUtils.isNotBlank(price.getVat())) {
			price.setVat(formatPrice(price.getVat()));
		}
		if (StringUtils.isNotBlank(price.getGross())) {
			price.setGross(formatPrice(price.getGross()));
		}
	}

	/**
	 * 
	 * @param price
	 * @return
	 */
	public String formatPrice(String price) {
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
	public Insurances getListOfFilteredInsurance(String journeyType,
			List<CommercialProduct> listOfInsuranceProducts, String cdnDomain) {
		Insurances insurance = null;
		List<CommercialProduct> listOfFilteredInsurances = listOfInsuranceProducts.stream()
				.filter(commercialProduct -> commonUtility.isProductNotExpired(commercialProduct)
						&& commonUtility.isProductJourneySpecific(commercialProduct, journeyType))
				.collect(Collectors.toList());
		if (listOfFilteredInsurances != null && !listOfFilteredInsurances.isEmpty()) {
			insurance = accessoriesAndInsurancedaoUtils.convertCommercialProductToInsurance(listOfFilteredInsurances,
					cdnDomain);
		}
		return insurance;
	}

	/**
	 * 
	 * @param listOfInsuranceMembers
	 * @param productGroup
	 * @return List<String>
	 */
	public List<String> getInsuranceProductList(List<Member> listOfInsuranceMembers, Group productGroup) {
		if (productGroup != null && productGroup.getGroupType() != null
				&& productGroup.getGroupType().trim().equalsIgnoreCase(STRING_COMPATIBLE_INSURANCE)) {
			listOfInsuranceMembers.addAll(productGroup.getMembers());
		}

		List<String> insuranceProductList = new ArrayList<>();
		if (listOfInsuranceMembers != null && !listOfInsuranceMembers.isEmpty()) {
			for (com.vf.uk.dal.device.model.productgroups.Member member : listOfInsuranceMembers) {
				if (member.getId() != null) {
					insuranceProductList.add(member.getId().trim());
				}
			}
		}
		return insuranceProductList;
	}
}
