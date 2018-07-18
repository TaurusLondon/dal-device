package com.vf.uk.dal.device.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ProductGroup
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-03-03T09:08:40.062Z")

public class ProductGroup {
	@JsonProperty("id")
	private Integer id = null;

	@JsonProperty("groupName")
	private String groupName = null;

	@JsonProperty("groupPriority")
	private String groupPriority = null;

	@JsonProperty("groupType")
	private String groupType = null;

	@JsonProperty("members")
	private List<Member> members = new ArrayList<>();

	/**
	 * 
	 * @param id
	 * @return
	 */
	public ProductGroup id(Integer id) {
		this.id = id;
		return this;
	}

	/**
	 * Product Group ID
	 * 
	 * @return id
	 **/
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @param groupName
	 * @return
	 */
	public ProductGroup groupName(String groupName) {
		this.groupName = groupName;
		return this;
	}

	/**
	 * Name of the product group, like Apple iPhone 6S
	 * 
	 * @return groupName
	 **/
	public String getGroupName() {
		return groupName;
	}

	/**
	 * 
	 * @param groupName
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * 
	 * @param groupPriority
	 * @return
	 */
	public ProductGroup groupPriority(String groupPriority) {
		this.groupPriority = groupPriority;
		return this;
	}

	/**
	 * Display Priority of the Group as provided in the product catalogue
	 * 
	 * @return groupPriority
	 **/
	public String getGroupPriority() {
		return groupPriority;
	}

	/**
	 * 
	 * @param groupPriority
	 */
	public void setGroupPriority(String groupPriority) {
		this.groupPriority = groupPriority;
	}

	/**
	 * 
	 * @param groupType
	 * @return
	 */
	public ProductGroup groupType(String groupType) {
		this.groupType = groupType;
		return this;
	}

	/**
	 * type of product Group - DEVICE, ACCESSORY etc.
	 * 
	 * @return groupType
	 **/
	public String getGroupType() {
		return groupType;
	}

	/**
	 * 
	 * @param groupType
	 */
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	/**
	 * 
	 * @param members
	 * @return
	 */
	public ProductGroup members(List<Member> members) {
		this.members = members;
		return this;
	}

	/**
	 * 
	 * @param membersItem
	 * @return
	 */
	public ProductGroup addMembersItem(Member membersItem) {
		this.members.add(membersItem);
		return this;
	}

	/**
	 * List of members for all the product group type
	 * 
	 * @return members
	 **/
	public List<Member> getMembers() {
		return members;
	}

	/**
	 * 
	 * @param members
	 */
	public void setMembers(List<Member> members) {
		this.members = members;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ProductGroup productGroup = (ProductGroup) o;
		return Objects.equals(this.id, productGroup.id) && Objects.equals(this.groupName, productGroup.groupName)
				&& Objects.equals(this.groupPriority, productGroup.groupPriority)
				&& Objects.equals(this.groupType, productGroup.groupType)
				&& Objects.equals(this.members, productGroup.members);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, groupName, groupPriority, groupType, members);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ProductGroup {\n");

		sb.append("    id: ").append(toIndentedString(id)).append("\n");
		sb.append("    groupName: ").append(toIndentedString(groupName)).append("\n");
		sb.append("    groupPriority: ").append(toIndentedString(groupPriority)).append("\n");
		sb.append("    groupType: ").append(toIndentedString(groupType)).append("\n");
		sb.append("    members: ").append(toIndentedString(members)).append("\n");
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
