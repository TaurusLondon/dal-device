package com.vf.uk.dal.utility.entity;

import lombok.Data;

/**
 * MonthlyCostSaver
 */
@Data
public class MonthlyCostSaver {
	private String mcsPlanId = null;

	private String mcsMessage = null;

	private String mcsParentId = null;

	private Price oneOffPrice = null;

	private Price oneOffDiscountPrice = null;

	private Price monthlyPrice = null;

	private Price monthlyDiscountPrice = null;

}
