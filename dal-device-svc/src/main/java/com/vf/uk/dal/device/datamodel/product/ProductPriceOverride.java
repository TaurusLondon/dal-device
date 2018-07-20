package com.vf.uk.dal.device.datamodel.product;

import lombok.Data;

@Data
public class ProductPriceOverride {

	private float priceNet;

	private float priceGross;

	private float priceVAT;
}
