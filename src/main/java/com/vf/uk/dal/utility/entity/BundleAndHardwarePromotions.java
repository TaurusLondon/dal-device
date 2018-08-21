package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * BundleAndHardwarePromotions.
 */
@Data
public class BundleAndHardwarePromotions {

	/** The bundle id. */
	@JsonProperty("bundleId")
	private String bundleId = null;

	/** The hardware id. */
	@JsonProperty("hardwareId")
	private String hardwareId = null;

	/** The sash banner for hardware. */
	@JsonProperty("sashBannerForHardware")
	private List<CataloguepromotionqueriesForHardwareSash> sashBannerForHardware = null;

	/** The free extras. */
	@JsonProperty("freeExtras")
	private List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtras = null;

	/** The free accessory. */
	@JsonProperty("freeAccessory")
	private List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccessory = null;

	/** The plan coupling promotions. */
	@JsonProperty("planCouplingPromotions")
	private List<CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions> planCouplingPromotions = null;

	/** The data allowances. */
	@JsonProperty("dataAllowances")
	private List<CataloguepromotionqueriesForBundleAndHardwareDataAllowances> dataAllowances = null;

	/** The entertainment packs. */
	@JsonProperty("entertainmentPacks")
	private List<CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks> entertainmentPacks = null;

	/** The sash banner for plan. */
	@JsonProperty("sashBannerForPlan")
	private List<CataloguepromotionqueriesForBundleAndHardwareSash> sashBannerForPlan = null;

	/** The secure net. */
	@JsonProperty("secureNet")
	private List<CataloguepromotionqueriesForBundleAndHardwareSecureNet> secureNet = new ArrayList<>();

	/** The free extras for plan. */
	@JsonProperty("freeExtrasForPlan")
	private List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForPlan = null;

	/** The free acc for plan. */
	@JsonProperty("freeAccForPlan")
	private List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForPlan = null;

	/** The free extras for hardware. */
	@JsonProperty("freeExtrasForHardware")
	private List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForHardware = null;

	/** The free acc for hardware. */
	@JsonProperty("freeAccForHardware")
	private List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForHardware = null;

	@JsonProperty("conditionalSashBanner")
	private List<CataloguepromotionqueriesForBundleAndHardwareSash> conditionalSashBanner = null;

}
