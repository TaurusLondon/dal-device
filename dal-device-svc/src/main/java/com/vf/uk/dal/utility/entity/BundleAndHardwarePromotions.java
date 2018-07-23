package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * BundleAndHardwarePromotions.
 */
@Data
public class BundleAndHardwarePromotions {

	/** The bundle id. */
	private String bundleId = null;

	/** The hardware id. */
	private String hardwareId = null;

	/** The sash banner for hardware. */
	private List<CataloguepromotionqueriesForHardwareSash> sashBannerForHardware = null;

	/** The free extras. */
	private List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtras = null;

	/** The free accessory. */
	private List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccessory = null;

	/** The plan coupling promotions. */
	private List<CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions> planCouplingPromotions = null;

	/** The data allowances. */
	private List<CataloguepromotionqueriesForBundleAndHardwareDataAllowances> dataAllowances = null;

	/** The entertainment packs. */
	private List<CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks> entertainmentPacks = null;

	/** The sash banner for plan. */
	private List<CataloguepromotionqueriesForBundleAndHardwareSash> sashBannerForPlan = null;

	/** The secure net. */
	private List<CataloguepromotionqueriesForBundleAndHardwareSecureNet> secureNet = new ArrayList<>();

	/** The free extras for plan. */
	private List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForPlan = null;

	/** The free acc for plan. */
	private List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForPlan = null;

	/** The free extras for hardware. */
	private List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForHardware = null;

	/** The free acc for hardware. */
	private List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForHardware = null;

	private List<CataloguepromotionqueriesForBundleAndHardwareSash> conditionalSashBanner = null;

	
}
