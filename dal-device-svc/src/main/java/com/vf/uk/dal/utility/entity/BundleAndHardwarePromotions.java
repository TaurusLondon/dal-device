package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * BundleAndHardwarePromotions.
 */
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
  
  /**
   * Bundle id.
   *
   * @param bundleId the bundle id
   * @return the bundle and hardware promotions
   */
  public BundleAndHardwarePromotions bundleId(String bundleId) {
    this.bundleId = bundleId;
    return this;
  }

   /**
    * Bundle sku id added to the basket.
    *
    * @return bundleId
    */
  public String getBundleId() {
    return bundleId;
  }

  /**
   * Sets the bundle id.
   *
   * @param bundleId the new bundle id
   */
  public void setBundleId(String bundleId) {
    this.bundleId = bundleId;
  }

  /**
   * Hardware id.
   *
   * @param hardwareId the hardware id
   * @return the bundle and hardware promotions
   */
  public BundleAndHardwarePromotions hardwareId(String hardwareId) {
    this.hardwareId = hardwareId;
    return this;
  }

   /**
    * Bundle sku id added to the basket.
    *
    * @return hardwareId
    */
  public String getHardwareId() {
    return hardwareId;
  }

  /**
   * Sets the hardware id.
   *
   * @param hardwareId the new hardware id
   */
  public void setHardwareId(String hardwareId) {
    this.hardwareId = hardwareId;
  }

  /**
   * Sash banner for hardware.
   *
   * @param sashBannerForHardware the sash banner for hardware
   * @return the bundle and hardware promotions
   */
  public BundleAndHardwarePromotions sashBannerForHardware(List<CataloguepromotionqueriesForHardwareSash> sashBannerForHardware) {
    this.sashBannerForHardware = sashBannerForHardware;
    return this;
  }

  /**
   * Adds the sash banner for hardware item.
   *
   * @param sashBannerForHardwareItem the sash banner for hardware item
   * @return the bundle and hardware promotions
   */
  public BundleAndHardwarePromotions addSashBannerForHardwareItem(CataloguepromotionqueriesForHardwareSash sashBannerForHardwareItem) {
    if (this.sashBannerForHardware == null) {
      this.sashBannerForHardware = new ArrayList<>();
    }
    this.sashBannerForHardware.add(sashBannerForHardwareItem);
    return this;
  }

   /**
    * Get sashBannerForHardware.
    *
    * @return sashBannerForHardware
    */
  public List<CataloguepromotionqueriesForHardwareSash> getSashBannerForHardware() {
    return sashBannerForHardware;
  }

  /**
   * Sets the sash banner for hardware.
   *
   * @param sashBannerForHardware the new sash banner for hardware
   */
  public void setSashBannerForHardware(List<CataloguepromotionqueriesForHardwareSash> sashBannerForHardware) {
    this.sashBannerForHardware = sashBannerForHardware;
  }

  /**
   * Free extras.
   *
   * @param freeExtras the free extras
   * @return the bundle and hardware promotions
   */
  public BundleAndHardwarePromotions freeExtras(List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtras) {
    this.freeExtras = freeExtras;
    return this;
  }

  /**
   * Adds the free extras item.
   *
   * @param freeExtrasItem the free extras item
   * @return the bundle and hardware promotions
   */
  public BundleAndHardwarePromotions addFreeExtrasItem(CataloguepromotionqueriesForBundleAndHardwareExtras freeExtrasItem) {
    if (this.freeExtras == null) {
      this.freeExtras = new ArrayList<>();
    }
    this.freeExtras.add(freeExtrasItem);
    return this;
  }

   /**
    * Get freeExtras.
    *
    * @return freeExtras
    */
  public List<CataloguepromotionqueriesForBundleAndHardwareExtras> getFreeExtras() {
    return freeExtras;
  }

  /**
   * Sets the free extras.
   *
   * @param freeExtras the new free extras
   */
  public void setFreeExtras(List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtras) {
    this.freeExtras = freeExtras;
  }

  /**
   * Free accessory.
   *
   * @param freeAccessory the free accessory
   * @return the bundle and hardware promotions
   */
  public BundleAndHardwarePromotions freeAccessory(List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccessory) {
    this.freeAccessory = freeAccessory;
    return this;
  }

  /**
   * Adds the free accessory item.
   *
   * @param freeAccessoryItem the free accessory item
   * @return the bundle and hardware promotions
   */
  public BundleAndHardwarePromotions addFreeAccessoryItem(CataloguepromotionqueriesForBundleAndHardwareAccessory freeAccessoryItem) {
    if (this.freeAccessory == null) {
      this.freeAccessory = new ArrayList<>();
    }
    this.freeAccessory.add(freeAccessoryItem);
    return this;
  }

   /**
    * Get freeAccessory.
    *
    * @return freeAccessory
    */
  public List<CataloguepromotionqueriesForBundleAndHardwareAccessory> getFreeAccessory() {
    return freeAccessory;
  }

  /**
   * Sets the free accessory.
   *
   * @param freeAccessory the new free accessory
   */
  public void setFreeAccessory(List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccessory) {
    this.freeAccessory = freeAccessory;
  }

  /**
   * Plan coupling promotions.
   *
   * @param planCouplingPromotions the plan coupling promotions
   * @return the bundle and hardware promotions
   */
  public BundleAndHardwarePromotions planCouplingPromotions(List<CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions> planCouplingPromotions) {
    this.planCouplingPromotions = planCouplingPromotions;
    return this;
  }

  /**
   * Adds the plan coupling promotions item.
   *
   * @param planCouplingPromotionsItem the plan coupling promotions item
   * @return the bundle and hardware promotions
   */
  public BundleAndHardwarePromotions addPlanCouplingPromotionsItem(CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions planCouplingPromotionsItem) {
    if (this.planCouplingPromotions == null) {
      this.planCouplingPromotions = new ArrayList<>();
    }
    this.planCouplingPromotions.add(planCouplingPromotionsItem);
    return this;
  }

   /**
    * Get planCouplingPromotions.
    *
    * @return planCouplingPromotions
    */
  public List<CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions> getPlanCouplingPromotions() {
    return planCouplingPromotions;
  }

  /**
   * Sets the plan coupling promotions.
   *
   * @param planCouplingPromotions the new plan coupling promotions
   */
  public void setPlanCouplingPromotions(List<CataloguepromotionqueriesForBundleAndHardwarePlanCouplingPromotions> planCouplingPromotions) {
    this.planCouplingPromotions = planCouplingPromotions;
  }

  /**
   * Data allowances.
   *
   * @param dataAllowances the data allowances
   * @return the bundle and hardware promotions
   */
  public BundleAndHardwarePromotions dataAllowances(List<CataloguepromotionqueriesForBundleAndHardwareDataAllowances> dataAllowances) {
    this.dataAllowances = dataAllowances;
    return this;
  }

  /**
   * Adds the data allowances item.
   *
   * @param dataAllowancesItem the data allowances item
   * @return the bundle and hardware promotions
   */
  public BundleAndHardwarePromotions addDataAllowancesItem(CataloguepromotionqueriesForBundleAndHardwareDataAllowances dataAllowancesItem) {
    if (this.dataAllowances == null) {
      this.dataAllowances = new ArrayList<>();
    }
    this.dataAllowances.add(dataAllowancesItem);
    return this;
  }

   /**
    * Get dataAllowances.
    *
    * @return dataAllowances
    */
  public List<CataloguepromotionqueriesForBundleAndHardwareDataAllowances> getDataAllowances() {
    return dataAllowances;
  }

  /**
   * Sets the data allowances.
   *
   * @param dataAllowances the new data allowances
   */
  public void setDataAllowances(List<CataloguepromotionqueriesForBundleAndHardwareDataAllowances> dataAllowances) {
    this.dataAllowances = dataAllowances;
  }

  /**
   * Entertainment packs.
   *
   * @param entertainmentPacks the entertainment packs
   * @return the bundle and hardware promotions
   */
  public BundleAndHardwarePromotions entertainmentPacks(List<CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks> entertainmentPacks) {
    this.entertainmentPacks = entertainmentPacks;
    return this;
  }

  /**
   * Adds the entertainment packs item.
   *
   * @param entertainmentPacksItem the entertainment packs item
   * @return the bundle and hardware promotions
   */
  public BundleAndHardwarePromotions addEntertainmentPacksItem(CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks entertainmentPacksItem) {
    if (this.entertainmentPacks == null) {
      this.entertainmentPacks = new ArrayList<>();
    }
    this.entertainmentPacks.add(entertainmentPacksItem);
    return this;
  }

   /**
    * Get entertainmentPacks.
    *
    * @return entertainmentPacks
    */
  public List<CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks> getEntertainmentPacks() {
    return entertainmentPacks;
  }

  /**
   * Sets the entertainment packs.
   *
   * @param entertainmentPacks the new entertainment packs
   */
  public void setEntertainmentPacks(List<CataloguepromotionqueriesForBundleAndHardwareEntertainmentPacks> entertainmentPacks) {
    this.entertainmentPacks = entertainmentPacks;
  }

  /**
   * Sash banner for plan.
   *
   * @param sashBannerForPlan the sash banner for plan
   * @return the bundle and hardware promotions
   */
  public BundleAndHardwarePromotions sashBannerForPlan(List<CataloguepromotionqueriesForBundleAndHardwareSash> sashBannerForPlan) {
    this.sashBannerForPlan = sashBannerForPlan;
    return this;
  }

  /**
   * Adds the sash banner for plan item.
   *
   * @param sashBannerForPlanItem the sash banner for plan item
   * @return the bundle and hardware promotions
   */
  public BundleAndHardwarePromotions addSashBannerForPlanItem(CataloguepromotionqueriesForBundleAndHardwareSash sashBannerForPlanItem) {
    if (this.sashBannerForPlan == null) {
      this.sashBannerForPlan = new ArrayList<>();
    }
    this.sashBannerForPlan.add(sashBannerForPlanItem);
    return this;
  }

   /**
    * Get sashBannerForPlan.
    *
    * @return sashBannerForPlan
    */
  public List<CataloguepromotionqueriesForBundleAndHardwareSash> getSashBannerForPlan() {
    return sashBannerForPlan;
  }

  /**
   * Sets the sash banner for plan.
   *
   * @param sashBannerForPlan the new sash banner for plan
   */
  public void setSashBannerForPlan(List<CataloguepromotionqueriesForBundleAndHardwareSash> sashBannerForPlan) {
    this.sashBannerForPlan = sashBannerForPlan;
  }


  /**
   * Gets the secure net.
   *
   * @return the secure net
   */
  public List<CataloguepromotionqueriesForBundleAndHardwareSecureNet> getSecureNet() {
	return secureNet;
}

/**
 * Sets the secure net.
 *
 * @param secureNet the new secure net
 */
public void setSecureNet(List<CataloguepromotionqueriesForBundleAndHardwareSecureNet> secureNet) {
	this.secureNet = secureNet;
}

/**
 * Gets the free extras for plan.
 *
 * @return the free extras for plan
 */
public List<CataloguepromotionqueriesForBundleAndHardwareExtras> getFreeExtrasForPlan() {
	return freeExtrasForPlan;
}

/**
 * Sets the free extras for plan.
 *
 * @param freeExtrasForPlan the new free extras for plan
 */
public void setFreeExtrasForPlan(List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForPlan) {
	this.freeExtrasForPlan = freeExtrasForPlan;
}



/**
 * Gets the free acc for plan.
 *
 * @return the free acc for plan
 */
public List<CataloguepromotionqueriesForBundleAndHardwareAccessory> getFreeAccForPlan() {
	return freeAccForPlan;
}

/**
 * Sets the free acc for plan.
 *
 * @param freeAccForPlan the new free acc for plan
 */
public void setFreeAccForPlan(List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForPlan) {
	this.freeAccForPlan = freeAccForPlan;
}

/**
 * Gets the free extras for hardware.
 *
 * @return the free extras for hardware
 */
public List<CataloguepromotionqueriesForBundleAndHardwareExtras> getFreeExtrasForHardware() {
	return freeExtrasForHardware;
}

/**
 * Sets the free extras for hardware.
 *
 * @param freeExtrasForHardware the new free extras for hardware
 */
public void setFreeExtrasForHardware(List<CataloguepromotionqueriesForBundleAndHardwareExtras> freeExtrasForHardware) {
	this.freeExtrasForHardware = freeExtrasForHardware;
}

/**
 * Gets the free acc for hardware.
 *
 * @return the free acc for hardware
 */
public List<CataloguepromotionqueriesForBundleAndHardwareAccessory> getFreeAccForHardware() {
	return freeAccForHardware;
}

/**
 * Sets the free acc for hardware.
 *
 * @param freeAccForHardware the new free acc for hardware
 */
public void setFreeAccForHardware(List<CataloguepromotionqueriesForBundleAndHardwareAccessory> freeAccForHardware) {
	this.freeAccForHardware = freeAccForHardware;
}




public List<CataloguepromotionqueriesForBundleAndHardwareSash> getConditionalSashBanner() {
	return conditionalSashBanner;
}

public void setConditionalSashBanner(List<CataloguepromotionqueriesForBundleAndHardwareSash> conditionalSashBanner) {
	this.conditionalSashBanner = conditionalSashBanner;
}

public BundleAndHardwarePromotions conditionalSashBanner(List<CataloguepromotionqueriesForBundleAndHardwareSash> conditionalSashBanner) {
    this.conditionalSashBanner = conditionalSashBanner;
    return this;
  }

  /**
   * Adds the sash banner for plan item.
   *
   * @param sashBannerForPlanItem the sash banner for plan item
   * @return the bundle and hardware promotions
   */
  public BundleAndHardwarePromotions addConditionalSashBanner(CataloguepromotionqueriesForBundleAndHardwareSash conditionalSashBanner) {
    if (this.conditionalSashBanner == null) {
      this.conditionalSashBanner = new ArrayList<>();
    }
    this.conditionalSashBanner.add(conditionalSashBanner);
    return this;
  }

/* (non-Javadoc)
 * @see java.lang.Object#equals(java.lang.Object)
 */
@Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BundleAndHardwarePromotions bundleAndHardwarePromotions = (BundleAndHardwarePromotions) o;
    return Objects.equals(this.bundleId, bundleAndHardwarePromotions.bundleId) &&
        Objects.equals(this.hardwareId, bundleAndHardwarePromotions.hardwareId) &&
        Objects.equals(this.sashBannerForHardware, bundleAndHardwarePromotions.sashBannerForHardware) &&
        Objects.equals(this.freeExtras, bundleAndHardwarePromotions.freeExtras) &&
        Objects.equals(this.freeAccessory, bundleAndHardwarePromotions.freeAccessory) &&
        Objects.equals(this.planCouplingPromotions, bundleAndHardwarePromotions.planCouplingPromotions) &&
        Objects.equals(this.dataAllowances, bundleAndHardwarePromotions.dataAllowances) &&
        Objects.equals(this.entertainmentPacks, bundleAndHardwarePromotions.entertainmentPacks) &&
        Objects.equals(this.sashBannerForPlan, bundleAndHardwarePromotions.sashBannerForPlan);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(bundleId, hardwareId, sashBannerForHardware, freeExtras, freeAccessory, planCouplingPromotions, dataAllowances, entertainmentPacks, sashBannerForPlan);
  }


  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BundleAndHardwarePromotions {\n");
    
    sb.append("    bundleId: ").append(toIndentedString(bundleId)).append("\n");
    sb.append("    hardwareId: ").append(toIndentedString(hardwareId)).append("\n");
    sb.append("    sashBannerForHardware: ").append(toIndentedString(sashBannerForHardware)).append("\n");
    sb.append("    freeExtras: ").append(toIndentedString(freeExtras)).append("\n");
    sb.append("    freeAccessory: ").append(toIndentedString(freeAccessory)).append("\n");
    sb.append("    planCouplingPromotions: ").append(toIndentedString(planCouplingPromotions)).append("\n");
    sb.append("    dataAllowances: ").append(toIndentedString(dataAllowances)).append("\n");
    sb.append("    entertainmentPacks: ").append(toIndentedString(entertainmentPacks)).append("\n");
    sb.append("    sashBannerForPlan: ").append(toIndentedString(sashBannerForPlan)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   *
   * @param o the o
   * @return the string
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
}

