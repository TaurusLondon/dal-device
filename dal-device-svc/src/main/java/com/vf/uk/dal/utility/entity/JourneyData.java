package com.vf.uk.dal.utility.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * JourneyData
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-06-13T09:29:52.184Z")

public class JourneyData   {
  @JsonProperty("name")
  private String name = null;

  @JsonProperty("state")
  private String state = null;

  @JsonProperty("customerPartyID")
  private String customerPartyID = null;

  @JsonProperty("ssoCreatedDate")
  private String ssoCreatedDate = null;

  @JsonProperty("ownedCustomerPartyIDs")
  private String ownedCustomerPartyIDs = null;

  @JsonProperty("offerID")
  private String offerID = null;

  @JsonProperty("offerCommunicationID")
  private String offerCommunicationID = null;

  @JsonProperty("upgradeEligibilityType")
  private String upgradeEligibilityType = null;

  @JsonProperty("commitmentEndDate")
  private String commitmentEndDate = null;

  @JsonProperty("earlyUpgradeFeeNet")
  private String earlyUpgradeFeeNet = null;

  @JsonProperty("earlyUpgradeFeeGross")
  private String earlyUpgradeFeeGross = null;

  @JsonProperty("accountRoles")
  private List<String> accountRoles = new ArrayList<String>();

  public JourneyData name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Journey type \"Upgrade\",\"2ndLine\",\"Offer\",\"Express\"
   * @return name
  **/
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public JourneyData state(String state) {
    this.state = state;
    return this;
  }

   /**
   * \"INPROGRESS\",\"COMPLETE\"
   * @return state
  **/
  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public JourneyData customerPartyID(String customerPartyID) {
    this.customerPartyID = customerPartyID;
    return this;
  }

   /**
   * Siebel Account ID – the \"in-context\" account according to eCare
   * @return customerPartyID
  **/
  public String getCustomerPartyID() {
    return customerPartyID;
  }

  public void setCustomerPartyID(String customerPartyID) {
    this.customerPartyID = customerPartyID;
  }

  public JourneyData ssoCreatedDate(String ssoCreatedDate) {
    this.ssoCreatedDate = ssoCreatedDate;
    return this;
  }

   /**
   * Timestamp the SSO datagram was created
   * @return ssoCreatedDate
  **/
  public String getSsoCreatedDate() {
    return ssoCreatedDate;
  }

  public void setSsoCreatedDate(String ssoCreatedDate) {
    this.ssoCreatedDate = ssoCreatedDate;
  }

  public JourneyData ownedCustomerPartyIDs(String ownedCustomerPartyIDs) {
    this.ownedCustomerPartyIDs = ownedCustomerPartyIDs;
    return this;
  }

   /**
   * The Customer Party accounts that are owned by the user
   * @return ownedCustomerPartyIDs
  **/
  public String getOwnedCustomerPartyIDs() {
    return ownedCustomerPartyIDs;
  }

  public void setOwnedCustomerPartyIDs(String ownedCustomerPartyIDs) {
    this.ownedCustomerPartyIDs = ownedCustomerPartyIDs;
  }

  public JourneyData offerID(String offerID) {
    this.offerID = offerID;
    return this;
  }

   /**
   * Chordiant Next Best Activity Offer ID
   * @return offerID
  **/
  public String getOfferID() {
    return offerID;
  }

  public void setOfferID(String offerID) {
    this.offerID = offerID;
  }

  public JourneyData offerCommunicationID(String offerCommunicationID) {
    this.offerCommunicationID = offerCommunicationID;
    return this;
  }

   /**
   * The communication ID used when eCare invoked TIL GetMarketingOfferList. eShop must use this same value when populating TIL UpdateMarketingOffer.
   * @return offerCommunicationID
  **/
  public String getOfferCommunicationID() {
    return offerCommunicationID;
  }

  public void setOfferCommunicationID(String offerCommunicationID) {
    this.offerCommunicationID = offerCommunicationID;
  }

  public JourneyData upgradeEligibilityType(String upgradeEligibilityType) {
    this.upgradeEligibilityType = upgradeEligibilityType;
    return this;
  }

   /**
   * The type of upgrade eligibility the user has
   * @return upgradeEligibilityType
  **/
  public String getUpgradeEligibilityType() {
    return upgradeEligibilityType;
  }

  public void setUpgradeEligibilityType(String upgradeEligibilityType) {
    this.upgradeEligibilityType = upgradeEligibilityType;
  }

  public JourneyData commitmentEndDate(String commitmentEndDate) {
    this.commitmentEndDate = commitmentEndDate;
    return this;
  }

   /**
   * The commitment end date of the contextMSISDN
   * @return commitmentEndDate
  **/
  public String getCommitmentEndDate() {
    return commitmentEndDate;
  }

  public void setCommitmentEndDate(String commitmentEndDate) {
    this.commitmentEndDate = commitmentEndDate;
  }

  public JourneyData earlyUpgradeFeeNet(String earlyUpgradeFeeNet) {
    this.earlyUpgradeFeeNet = earlyUpgradeFeeNet;
    return this;
  }

   /**
   * The Early Upgrade Fee ex VAT
   * @return earlyUpgradeFeeNet
  **/
  public String getEarlyUpgradeFeeNet() {
    return earlyUpgradeFeeNet;
  }

  public void setEarlyUpgradeFeeNet(String earlyUpgradeFeeNet) {
    this.earlyUpgradeFeeNet = earlyUpgradeFeeNet;
  }

  public JourneyData earlyUpgradeFeeGross(String earlyUpgradeFeeGross) {
    this.earlyUpgradeFeeGross = earlyUpgradeFeeGross;
    return this;
  }

   /**
   * The Early Upgrade Fee inc VAT
   * @return earlyUpgradeFeeGross
  **/
  public String getEarlyUpgradeFeeGross() {
    return earlyUpgradeFeeGross;
  }

  public void setEarlyUpgradeFeeGross(String earlyUpgradeFeeGross) {
    this.earlyUpgradeFeeGross = earlyUpgradeFeeGross;
  }

  public JourneyData accountRoles(List<String> accountRoles) {
    this.accountRoles = accountRoles;
    return this;
  }

  public JourneyData addAccountRolesItem(String accountRolesItem) {
    this.accountRoles.add(accountRolesItem);
    return this;
  }

   /**
   * Account Roles like \"Owner\",\"Bill Payer\", \"Service User\", \"Admin L1\", \"Admin L2\", \"Store Collection\"
   * @return accountRoles
  **/
  public List<String> getAccountRoles() {
    return accountRoles;
  }

  public void setAccountRoles(List<String> accountRoles) {
    this.accountRoles = accountRoles;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    JourneyData journeyData = (JourneyData) o;
    return Objects.equals(this.name, journeyData.name) &&
        Objects.equals(this.state, journeyData.state) &&
        Objects.equals(this.customerPartyID, journeyData.customerPartyID) &&
        Objects.equals(this.ssoCreatedDate, journeyData.ssoCreatedDate) &&
        Objects.equals(this.ownedCustomerPartyIDs, journeyData.ownedCustomerPartyIDs) &&
        Objects.equals(this.offerID, journeyData.offerID) &&
        Objects.equals(this.offerCommunicationID, journeyData.offerCommunicationID) &&
        Objects.equals(this.upgradeEligibilityType, journeyData.upgradeEligibilityType) &&
        Objects.equals(this.commitmentEndDate, journeyData.commitmentEndDate) &&
        Objects.equals(this.earlyUpgradeFeeNet, journeyData.earlyUpgradeFeeNet) &&
        Objects.equals(this.earlyUpgradeFeeGross, journeyData.earlyUpgradeFeeGross) &&
        Objects.equals(this.accountRoles, journeyData.accountRoles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, state, customerPartyID, ssoCreatedDate, ownedCustomerPartyIDs, offerID, offerCommunicationID, upgradeEligibilityType, commitmentEndDate, earlyUpgradeFeeNet, earlyUpgradeFeeGross, accountRoles);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class JourneyData {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    customerPartyID: ").append(toIndentedString(customerPartyID)).append("\n");
    sb.append("    ssoCreatedDate: ").append(toIndentedString(ssoCreatedDate)).append("\n");
    sb.append("    ownedCustomerPartyIDs: ").append(toIndentedString(ownedCustomerPartyIDs)).append("\n");
    sb.append("    offerID: ").append(toIndentedString(offerID)).append("\n");
    sb.append("    offerCommunicationID: ").append(toIndentedString(offerCommunicationID)).append("\n");
    sb.append("    upgradeEligibilityType: ").append(toIndentedString(upgradeEligibilityType)).append("\n");
    sb.append("    commitmentEndDate: ").append(toIndentedString(commitmentEndDate)).append("\n");
    sb.append("    earlyUpgradeFeeNet: ").append(toIndentedString(earlyUpgradeFeeNet)).append("\n");
    sb.append("    earlyUpgradeFeeGross: ").append(toIndentedString(earlyUpgradeFeeGross)).append("\n");
    sb.append("    accountRoles: ").append(toIndentedString(accountRoles)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

