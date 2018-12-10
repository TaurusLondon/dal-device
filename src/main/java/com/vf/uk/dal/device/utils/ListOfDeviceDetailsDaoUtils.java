package com.vf.uk.dal.device.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.vf.uk.dal.device.model.Member;
import com.vf.uk.dal.device.model.ProductGroup;
import com.vf.uk.dal.device.model.productgroups.ProductGroupModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ListOfDeviceDetailsDaoUtils {

	// For price API
	/**
	 * 
	 * @param listOfPriceForBundleHeader
	 * @return bundleHeaderForDevice1
	 */
	public com.vf.uk.dal.device.client.entity.bundle.BundleHeader getListOfPriceForBundleAndHardwareForDevice(
			List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> listOfPriceForBundleHeader) {
		List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> listOfBundelMonthlyPriceForBundleHeader = null;
		com.vf.uk.dal.device.client.entity.bundle.BundleHeader bundleHeaderForDevice1 = null;
		String gross = null;

		try {

			if (listOfPriceForBundleHeader != null && !listOfPriceForBundleHeader.isEmpty()) {
				List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> listOfOneOffPriceForBundleHeader = getAscendingOrderForOneoffPrice1(
						listOfPriceForBundleHeader);
				if (listOfOneOffPriceForBundleHeader != null && !listOfOneOffPriceForBundleHeader.isEmpty()) {
					gross = setGross(listOfOneOffPriceForBundleHeader);
					List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> listOfEqualOneOffPriceForBundleHeader = setListOfOneOffPrice(
							gross, listOfOneOffPriceForBundleHeader);
					String bundleId = null;
					if (!listOfEqualOneOffPriceForBundleHeader.isEmpty()) {
						listOfBundelMonthlyPriceForBundleHeader = getAscendingOrderForBundlePrice1(
								listOfEqualOneOffPriceForBundleHeader);
						if (listOfBundelMonthlyPriceForBundleHeader != null
								&& !listOfBundelMonthlyPriceForBundleHeader.isEmpty()) {
							bundleId = listOfBundelMonthlyPriceForBundleHeader.get(0).getSkuId();
						}
					}
					log.info("Compatible Id:" + bundleId);
					bundleHeaderForDevice1 = setBundleHeaderForDevice(listOfBundelMonthlyPriceForBundleHeader,
							bundleId);

				}
			}
		} catch (Exception e) {
			log.error("Exception occured when call happen to compatible bundles api: " + e);
		}

		return bundleHeaderForDevice1;

	}

	private String setGross(
			List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> listOfOneOffPriceForBundleHeader) {
		String gross;
		if (listOfOneOffPriceForBundleHeader.get(0).getPriceInfo().getHardwarePrice()
				.getOneOffDiscountPrice().getGross() != null) {
			gross = listOfOneOffPriceForBundleHeader.get(0).getPriceInfo().getHardwarePrice()
					.getOneOffDiscountPrice().getGross();
		} else {
			gross = listOfOneOffPriceForBundleHeader.get(0).getPriceInfo().getHardwarePrice()
					.getOneOffPrice().getGross();
		}
		return gross;
	}

	private List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> setListOfOneOffPrice(String gross,
			List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> listOfOneOffPriceForBundleHeader) {
		List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> listOfEqualOneOffPriceForBundleHeader = new ArrayList<>();
		for (com.vf.uk.dal.device.client.entity.bundle.BundleHeader bundleHeaderForDevice : listOfOneOffPriceForBundleHeader) {
			if (bundleHeaderForDevice.getPriceInfo() != null
					&& bundleHeaderForDevice.getPriceInfo().getHardwarePrice() != null
					&& (bundleHeaderForDevice.getPriceInfo().getHardwarePrice()
							.getOneOffDiscountPrice() != null
							|| bundleHeaderForDevice.getPriceInfo().getHardwarePrice()
									.getOneOffPrice() != null)
					&& gross != null) {
				setlistOfEqualOneOffPriceForBundleHeader(gross, listOfEqualOneOffPriceForBundleHeader,
						bundleHeaderForDevice);
			}
		}
		return listOfEqualOneOffPriceForBundleHeader;
	}

	private com.vf.uk.dal.device.client.entity.bundle.BundleHeader setBundleHeaderForDevice(
			List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> listOfBundelMonthlyPriceForBundleHeader,
			 String bundleId) {
		com.vf.uk.dal.device.client.entity.bundle.BundleHeader bundleHeaderForDevice1= null;
		if (bundleId != null && !bundleId.isEmpty()) {
			bundleHeaderForDevice1 = listOfBundelMonthlyPriceForBundleHeader.get(0);
		}
		return bundleHeaderForDevice1;
	}

	private void setlistOfEqualOneOffPriceForBundleHeader(String gross,
			List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> listOfEqualOneOffPriceForBundleHeader,
			com.vf.uk.dal.device.client.entity.bundle.BundleHeader bundleHeaderForDevice) {
		if ((bundleHeaderForDevice.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice()
				.getGross() != null
				|| bundleHeaderForDevice.getPriceInfo().getHardwarePrice().getOneOffPrice()
						.getGross() != null)
				&& (gross
						.equalsIgnoreCase(bundleHeaderForDevice.getPriceInfo().getHardwarePrice()
								.getOneOffDiscountPrice().getGross())
						|| gross.equalsIgnoreCase(bundleHeaderForDevice.getPriceInfo()
								.getHardwarePrice().getOneOffPrice().getGross()))) {
			listOfEqualOneOffPriceForBundleHeader.add(bundleHeaderForDevice);
		}
	}

	/**
	 * 
	 * @param bundleHeaderForDeviceSorted
	 * @return bundleHeaderForDeviceSorted
	 */
	public List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> getAscendingOrderForBundlePrice1(
			List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> bundleHeaderForDeviceSorted) {
		Collections.sort(bundleHeaderForDeviceSorted, new SortedBundlePriceList1());

		return bundleHeaderForDeviceSorted;
	}

	class SortedBundlePriceList1 implements Comparator<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> {

		@Override
		public int compare(com.vf.uk.dal.device.client.entity.bundle.BundleHeader bundleHeaderList,
				com.vf.uk.dal.device.client.entity.bundle.BundleHeader bundleHeaderList1) {
			String gross = null;
			String gross1 = null;
			if (bundleHeaderList.getPriceInfo() != null && bundleHeaderList1.getPriceInfo() != null
					&& bundleHeaderList.getPriceInfo().getBundlePrice() != null
					&& bundleHeaderList1.getPriceInfo().getBundlePrice() != null) {
				if (bundleHeaderList.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice() != null
						&& bundleHeaderList.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice()
								.getGross() != null) {
					gross = bundleHeaderList.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice().getGross();
				} else {
					gross = bundleHeaderList.getPriceInfo().getBundlePrice().getMonthlyPrice().getGross();
				}
				if (bundleHeaderList1.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice() != null
						&& bundleHeaderList1.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice()
								.getGross() != null) {
					gross1 = bundleHeaderList1.getPriceInfo().getBundlePrice().getMonthlyDiscountPrice().getGross();
				} else {
					gross1 = bundleHeaderList1.getPriceInfo().getBundlePrice().getMonthlyPrice().getGross();
				}
				if (Double.parseDouble(gross) < Double.parseDouble(gross1)) {
					return -1;
				} else{
					return 1;
				}

			}

			else{
				return -1;
			}
		}

	}

	/**
	 * 
	 * @param bundleHeaderForDeviceSorted
	 * @return bundleHeaderForDeviceSorted
	 */
	public List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> getAscendingOrderForOneoffPrice1(
			List<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> bundleHeaderForDeviceSorted) {
		Collections.sort(bundleHeaderForDeviceSorted, new SortedOneOffPriceList1());

		return bundleHeaderForDeviceSorted;
	}

	class SortedOneOffPriceList1 implements Comparator<com.vf.uk.dal.device.client.entity.bundle.BundleHeader> {

		@Override
		public int compare(com.vf.uk.dal.device.client.entity.bundle.BundleHeader bundleHeaderList,
				com.vf.uk.dal.device.client.entity.bundle.BundleHeader bundleHeaderList1) {
			String gross = null;
			String gross1 = null;
			if (bundleHeaderList.getPriceInfo() != null && bundleHeaderList1.getPriceInfo() != null
					&& bundleHeaderList.getPriceInfo().getHardwarePrice() != null
					&& bundleHeaderList1.getPriceInfo().getHardwarePrice() != null) {
				if (bundleHeaderList.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice() != null
						&& bundleHeaderList.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice()
								.getGross() != null) {
					gross = bundleHeaderList.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice().getGross();
				} else {
					gross = bundleHeaderList.getPriceInfo().getHardwarePrice().getOneOffPrice().getGross();
				}
				if (bundleHeaderList1.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice() != null
						&& bundleHeaderList1.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice()
								.getGross() != null) {
					gross1 = bundleHeaderList1.getPriceInfo().getHardwarePrice().getOneOffDiscountPrice().getGross();
				} else {
					gross1 = bundleHeaderList1.getPriceInfo().getHardwarePrice().getOneOffPrice().getGross();
				}

				if (Double.parseDouble(gross) < Double.parseDouble(gross1)) {
					return -1;
				} else{
					return 1;
				}
			} else{
				return -1;
			}
		}
	}

	/**
	 * ################################ Below
	 * ProductGroupModel########################################################
	 * Obsolate service Daoutils conversion of coherence ProductGroup to
	 * ProductGroupDetails.
	 * 
	 * @param cohProduct
	 * @return List<ProductGroup>
	 */
	public static List<ProductGroup> convertGroupProductToProductGroupDetails(
			List<ProductGroupModel> sohProductGroupList) {

		List<ProductGroup> productGroupList;
		productGroupList = new ArrayList<>();
		for (ProductGroupModel productGroupModel : sohProductGroupList) {
			ProductGroup productGroup;
			productGroup = new ProductGroup();
			productGroup.setId(null);
			productGroup.setGroupName(productGroupModel.getName());
			productGroup.setGroupPriority(String.valueOf(productGroupModel.getPriority()));
			productGroup.setGroupType(productGroupModel.getType());

			Member member;

			List<Member> listOfMember = new ArrayList<>();

			List<String> variantsList = productGroupModel.getListOfVariants();
			if (variantsList != null && !variantsList.isEmpty()) {
				for (String variants : variantsList) {
					member = new Member();
					String[] variantIdPriority = variants.split("\\|");
					if (variantIdPriority != null && variantIdPriority.length == 2) {
						member.setId(variantIdPriority[0]);
						member.setPriority(variantIdPriority[1]);
						listOfMember.add(member);
					}
				}
			}
			productGroup.setMembers(listOfMember);
			productGroupList.add(productGroup);
		}
		return productGroupList;
	}

}
