package com.vf.uk.dal.device.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import com.vf.uk.dal.common.configuration.DataSourceInitializer;
import com.vf.uk.dal.common.exception.ApplicationException;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.dao.DeviceTileCacheDAO;
import com.vf.uk.dal.device.entity.CacheDeviceTileResponse;
import com.vf.uk.dal.device.utils.ExceptionMessages;
import com.vf.uk.dal.utility.solr.entity.BundlePrice;
import com.vf.uk.dal.utility.solr.entity.DevicePreCalculatedData;
import com.vf.uk.dal.utility.solr.entity.HardwarePrice;
import com.vf.uk.dal.utility.solr.entity.Media;
import com.vf.uk.dal.utility.solr.entity.MonthlyDiscountPrice;
import com.vf.uk.dal.utility.solr.entity.MonthlyPrice;
import com.vf.uk.dal.utility.solr.entity.OfferAppliedPriceDetails;
import com.vf.uk.dal.utility.solr.entity.OneOffDiscountPrice;
import com.vf.uk.dal.utility.solr.entity.OneOffPrice;
import com.vf.uk.dal.utility.solr.entity.PriceInfo;

/**
 * 
 * Connects to database and saves the data.
 *
 */
@Component("deviceTileCacheDAO")
public class DeviceTileCacheDAOImpl implements DeviceTileCacheDAO {
	@Autowired
	DataSourceInitializer dataSourceInitializer;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	DataSource datasource;

	Connection conn = null;
	/**
	 * 
	 * @return
	 */
	public JdbcTemplate getJdbcTemplate() {
		DataSource ds = dataSourceInitializer.getDataSource();
		return new JdbcTemplate(ds);
	}

	/**
	 * 
	 * @param tablename
	 */
	public void delete(String tablename) {
		StringBuilder sql=new StringBuilder("DELETE FROM PRODUCT.");
        sql.append(tablename);
		getJdbcTemplate().update(sql.toString());

	}

	/**
	 * 
	 * @param tablename
	 * @return count
	 */
	public int count(String tablename) {
		StringBuilder sql=new StringBuilder("SELECT COUNT(*) FROM PRODUCT.");
        sql.append(tablename);
		int count;
		count = getJdbcTemplate().queryForObject(sql.toString(), Integer.class);
		return count;
	}

	/**
	 * 
	 * @param listProductGroupForDeviceListing
	 * @return DeviceListPreCalcData
	 */
	@Override
	public int saveDeviceListPreCalcData(List<DevicePreCalculatedData> listProductGroupForDeviceListing) {
		int result = 0;
		deleteDeviceListPreCalcDataAndMedia();
		String sql = "INSERT INTO PRODUCT.DEVICE_LIST_PRE_CALC_DATA"
				+ "(DEVICE_ID,RATING,LEAD_PLAN_ID,PRODUCT_GROUP_NAME,PRODUCT_GROUP_ID,"
				+ "BUNDLE_MNTHLY_PRICE_GROSS, BUNDLE_MNTHLY_PRICE_NET, BUNDLE_MNTHLY_PRICE_VAT,"
				+ "BUNDLE_MNTHLY_DISC_PRICE_GROSS, BUNDLE_MNTHLY_DISC_PRICE_NET, BUNDLE_MNTHLY_DISC_PRICE_VAT,"
				+ "HW_ONE_OFF_PRICE_GROSS, HW_ONE_OFF_PRICE_NET, HW_ONE_OFF_PRICE_VAT,"
				+ "HW_ONE_OFF_DISC_PRICE_GROSS, HW_ONE_OFF_DISC_PRICE_NET, HW_ONE_OFF_DISC_PRICE_VAT, CREATE_UPDATE_TIME,"
				+ "IS_LEAD_MEMBER, MINIMUM_COST,NON_UPGRADE_LEAD_DEVICE_ID,UPGRADE_LEAD_DEVICE_ID,NON_UPGRADE_LEAD_PLAN_ID,UPGRADE_LEAD_PLAN_ID)"
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int[] i = getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				DevicePreCalculatedData productGroupForDeviceListing = listProductGroupForDeviceListing.get(i);
				PriceInfo priceInfo = productGroupForDeviceListing.getPriceInfo();
				ps.setString(1, productGroupForDeviceListing.getDeviceId());
				ps.setString(2, String.valueOf(productGroupForDeviceListing.getRating()));
				ps.setString(3, productGroupForDeviceListing.getLeadPlanId());
				ps.setString(4, productGroupForDeviceListing.getProductGroupName());
				ps.setString(5, productGroupForDeviceListing.getProductGroupId());
				setPreparedStatementOnBundlePrice(ps, priceInfo);
				setPreparedStatementOnHardwarePrice(ps, priceInfo);
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				ps.setTimestamp(18, timestamp);
				ps.setString(19, productGroupForDeviceListing.getIsLeadMember());
				ps.setString(20, productGroupForDeviceListing.getMinimumCost());
				ps.setString(21, productGroupForDeviceListing.getNonUpgradeLeadDeviceId());
				ps.setString(22, productGroupForDeviceListing.getUpgradeLeadDeviceId());
				ps.setString(23, productGroupForDeviceListing.getNonUpgradeLeadPlanId());
				ps.setString(24, productGroupForDeviceListing.getUpgradeLeadPlanId());
			}

			private void setPreparedStatementOnHardwarePrice(PreparedStatement ps, PriceInfo priceInfo)
					throws SQLException {
				if (priceInfo != null && priceInfo.getHardwarePrice() != null) {
					setHardwareOneOffPrice(ps, priceInfo);
					setHardwareOneOffDiscountPrice(ps, priceInfo);
				} else {
					ps.setString(12, null);
					ps.setString(13, null);
					ps.setString(14, null);
					ps.setString(15, null);
					ps.setString(16, null);
					ps.setString(17, null);
				}
			}

			private void setHardwareOneOffDiscountPrice(PreparedStatement ps, PriceInfo priceInfo) throws SQLException {
				if (priceInfo.getHardwarePrice().getOneOffDiscountPrice() != null) {
					ps.setString(15, priceInfo.getHardwarePrice().getOneOffDiscountPrice().getGross());
					ps.setString(16, priceInfo.getHardwarePrice().getOneOffDiscountPrice().getNet());
					ps.setString(17, priceInfo.getHardwarePrice().getOneOffDiscountPrice().getVat());
				}
			}

			private void setHardwareOneOffPrice(PreparedStatement ps, PriceInfo priceInfo) throws SQLException {
				if (priceInfo.getHardwarePrice().getOneOffPrice() != null) {
					ps.setString(12, priceInfo.getHardwarePrice().getOneOffPrice().getGross());
					ps.setString(13, priceInfo.getHardwarePrice().getOneOffPrice().getNet());
					ps.setString(14, priceInfo.getHardwarePrice().getOneOffPrice().getVat());
				}
			}

			private void setPreparedStatementOnBundlePrice(PreparedStatement ps, PriceInfo priceInfo)
					throws SQLException {
				if (priceInfo != null && priceInfo.getBundlePrice() != null
						&& priceInfo.getBundlePrice().getMonthlyPrice() != null
						&& priceInfo.getBundlePrice().getMonthlyPrice().getGross() != null) {
					ps.setString(6, priceInfo.getBundlePrice().getMonthlyPrice().getGross());
					ps.setString(7, priceInfo.getBundlePrice().getMonthlyPrice().getNet());
					ps.setString(8, priceInfo.getBundlePrice().getMonthlyPrice().getVat());
					setBundleMonthlyDiscountPrice(ps, priceInfo);
				} else {
					ps.setString(6, null);
					ps.setString(7, null);
					ps.setString(8, null);
					ps.setString(9, null);
					ps.setString(10, null);
					ps.setString(11, null);
				}
			}

			private void setBundleMonthlyDiscountPrice(PreparedStatement ps, PriceInfo priceInfo) throws SQLException {
				if (priceInfo.getBundlePrice().getMonthlyDiscountPrice() != null) {
					ps.setString(9, priceInfo.getBundlePrice().getMonthlyDiscountPrice().getGross());
					ps.setString(10, priceInfo.getBundlePrice().getMonthlyDiscountPrice().getNet());
					ps.setString(11, priceInfo.getBundlePrice().getMonthlyDiscountPrice().getVat());
				}
			}

			@Override
			public int getBatchSize() {
				return listProductGroupForDeviceListing.size();
			}
		});

		result = checkLengthAndsetResult( i);
		return result;
	}

	private int checkLengthAndsetResult( int[] i) {
		int result=0;
		if (i.length > 0) {
			result = 1;
		}
		return result;
	}

	private void deleteDeviceListPreCalcDataAndMedia() {
		if (count("DEVICE_LIST_PRE_CALC_MEDIA") > 0) {
			delete("DEVICE_LIST_PRE_CALC_MEDIA");
		}
		if (count("DEVICE_LIST_PRE_CALC_DATA") > 0) {
			delete("DEVICE_LIST_PRE_CALC_DATA");
		}
	}

	/**
	 * 
	 * @param mediaList
	 * @param deviceId
	 * @return DeviceMediaData
	 */
	@Override
	public int saveDeviceMediaData(List<Media> mediaList, String deviceId) {
		int result = 0;
		LogHelper.info(this, "Begin DEVICE_LIST_PRE_CALC_MEDIA ");
		String sql = "INSERT INTO PRODUCT.DEVICE_LIST_PRE_CALC_MEDIA (DEVICE_ID,ID,VALUE,TYPE,DESCRIPTION,DISCOUNT_ID,PROMO_CATEGORY,OFFER_CODE) values (?,?,?,?,?,?,?,?)";
		int[] i = getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Media mediaLink = mediaList.get(i);
				ps.setString(1, deviceId);
				ps.setString(2, mediaLink.getId());
				ps.setString(3, mediaLink.getValue());
				ps.setString(4, mediaLink.getType());
				ps.setString(5, mediaLink.getDescription());
				ps.setString(6, mediaLink.getDiscountId());
				ps.setString(7, mediaLink.getPromoCategory());
				ps.setString(8, mediaLink.getOfferCode());
			}

			@Override
			public int getBatchSize() {
				return mediaList.size();
			}
		});
		LogHelper.info(this, "End DEVICE_LIST_PRE_CALC_MEDIA method");

		result = checkLengthAndsetResult(i);
		return result;
	} 

	/**
	 * 
	 * @param listProductGroupForDeviceListing
	 * @return DeviceListILSCalcData
	 */
	@Override
	public int saveDeviceListILSCalcData(List<OfferAppliedPriceDetails> offerAppliedPricesList) {
		int result = 0;
		deleteDeviceOfferAppliedData();
		LogHelper.info(this, "Begin DEVICE_OFFERAPPLIED_PRICE_DATA ");
		String sql = "INSERT INTO PRODUCT.DEVICE_OFFERAPPLIED_PRICE_DATA " + "(OFFER_CODE, DEVICE_ID,BUNDLE_ID, "
				+ "BUNDLE_MNTHLY_PRICE_GROSS,BUNDLE_MNTHLY_PRICE_NET, BUNDLE_MNTHLY_PRICE_VAT, "
				+ "BUNDLE_MNTHLY_DISC_PRICE_GROSS, BUNDLE_MNTHLY_DISC_PRICE_NET, BUNDLE_MNTHLY_DISC_PRICE_VAT, "
				+ "HW_ONE_OFF_PRICE_GROSS, HW_ONE_OFF_PRICE_NET, HW_ONE_OFF_PRICE_VAT, "
				+ "HW_ONE_OFF_DISC_PRICE_GROSS ,HW_ONE_OFF_DISC_PRICE_NET, HW_ONE_OFF_DISC_PRICE_VAT,CREATE_UPDATE_TIME,JOURNEY_TYPE) "
				+ "values (?,?,?,?, ?,?,?,?, ?,?,?,?, ?,?,?,?,?)";
		int[] i = getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OfferAppliedPriceDetails offerAppliedPriceDetails = offerAppliedPricesList.get(i);
				setPreparedStatementForDeviceILSOfferAppliedNotNull(ps, offerAppliedPriceDetails);
			}

			private void setPreparedStatementForDeviceILSOfferAppliedNotNull(PreparedStatement ps,
					OfferAppliedPriceDetails offerAppliedPriceDetails) throws SQLException {
				if (offerAppliedPriceDetails != null) {
					ps.setString(1, offerAppliedPriceDetails.getOfferCode());
					ps.setString(2, offerAppliedPriceDetails.getDeviceId());
					BundlePrice bundlePrice = offerAppliedPriceDetails.getBundlePrice();
					setPreparedStatemenrForDeivceILS(ps, bundlePrice);
					HardwarePrice hardwarePrice = offerAppliedPriceDetails.getHardwarePrice();
					setPreparedStatementOnHardwarePriceDeviceILS(ps, hardwarePrice);
					Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
					ps.setTimestamp(16, timeStamp);
					ps.setString(17, offerAppliedPriceDetails.getJourneyType());
				}
			}

			private void setPreparedStatementOnHardwarePriceDeviceILS(PreparedStatement ps, HardwarePrice hardwarePrice)
					throws SQLException {
				if (hardwarePrice == null) {
					ps.setString(10, null);
					ps.setString(11, null);
					ps.setString(12, null);
					ps.setString(13, null);
					ps.setString(14, null);
					ps.setString(15, null);
				} else {
					OneOffPrice oneOffPrice = hardwarePrice.getOneOffPrice();
					setOneOffPriceForDeviceILS(ps, oneOffPrice);
					OneOffDiscountPrice oneOffDiscountPrice = hardwarePrice.getOneOffDiscountPrice();
					setOneOffDiscountPriceFOrDEviceILS(ps, oneOffDiscountPrice);
				}
			}

			private void setOneOffDiscountPriceFOrDEviceILS(PreparedStatement ps,
					OneOffDiscountPrice oneOffDiscountPrice) throws SQLException {
				if (oneOffDiscountPrice != null && oneOffDiscountPrice.getGross() != null) {
					ps.setString(13, oneOffDiscountPrice.getGross());
					ps.setString(14, oneOffDiscountPrice.getNet());
					ps.setString(15, oneOffDiscountPrice.getVat());
				} else {
					ps.setString(13, null);
					ps.setString(14, null);
					ps.setString(15, null);
				}
			}

			private void setOneOffPriceForDeviceILS(PreparedStatement ps, OneOffPrice oneOffPrice) throws SQLException {
				if (oneOffPrice != null && oneOffPrice.getGross() != null) {
					ps.setString(10, oneOffPrice.getGross());
					ps.setString(11, oneOffPrice.getNet());
					ps.setString(12, oneOffPrice.getVat());
				} else {
					ps.setString(10, null);
					ps.setString(11, null);
					ps.setString(12, null);
				}
			}

			private void setPreparedStatemenrForDeivceILS(PreparedStatement ps, BundlePrice bundlePrice)
					throws SQLException {
				if (bundlePrice == null) {
					ps.setString(3, null);
					ps.setString(4, null);
					ps.setString(5, null);
					ps.setString(6, null);
					ps.setString(7, null);
					ps.setString(8, null);
					ps.setString(9, null);
				} else {
					ps.setString(3, bundlePrice.getBundleId());
					MonthlyPrice monthlyPrice = bundlePrice.getMonthlyPrice();
					setMonthlyPriceValuesForDeviceListILS(ps, monthlyPrice);
					MonthlyDiscountPrice monthlyDiscountPrice = bundlePrice.getMonthlyDiscountPrice();
					setMonthlyDiscountPriceForDeviceListILS(ps, monthlyDiscountPrice);
				}
			}

			private void setMonthlyDiscountPriceForDeviceListILS(PreparedStatement ps,
					MonthlyDiscountPrice monthlyDiscountPrice) throws SQLException {
				if (monthlyDiscountPrice != null && monthlyDiscountPrice.getGross() != null) {
					ps.setString(7, monthlyDiscountPrice.getGross());
					ps.setString(8, monthlyDiscountPrice.getNet());
					ps.setString(9, monthlyDiscountPrice.getVat());
				} else {
					ps.setString(7, null);
					ps.setString(8, null);
					ps.setString(9, null);
				}
			}

			private void setMonthlyPriceValuesForDeviceListILS(PreparedStatement ps, MonthlyPrice monthlyPrice)
					throws SQLException {
				if (monthlyPrice != null && monthlyPrice.getGross() != null) {
					ps.setString(4, monthlyPrice.getGross());
					ps.setString(5, monthlyPrice.getNet());
					ps.setString(6, monthlyPrice.getVat());
				} else {
					ps.setString(4, null);
					ps.setString(5, null);
					ps.setString(6, null);
				}
			}

			@Override
			public int getBatchSize() {
				return offerAppliedPricesList.size();
			}
		});
		LogHelper.info(this, "End DEVICE_OFFERAPPLIED_PRICE_DATA method");

		result = checkLengthAndsetResult( i);
		return result;
	}

	private void deleteDeviceOfferAppliedData() {
		if (count("DEVICE_OFFERAPPLIED_PRICE_DATA") > 0) {
			delete("DEVICE_OFFERAPPLIED_PRICE_DATA");
		}
	}

	@Override
	public void beginTransaction() {
		try {
			conn = DataSourceUtils.getConnection(getJdbcTemplate().getDataSource());
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			LogHelper.error(this, "Exception occurred while opening connection" + e);
		}
	}

	// Method to End JDBC Transaction
	@Override
	public void endTransaction() {
		try {
			conn.commit();
		} catch (SQLException e) {
			LogHelper.error(this, "Exception occurred while persisting data in intermediate tables" + e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				LogHelper.error(this, "Exception occurred while closing connection" + e);
			}
		}
	}

	// Method to roll back JDBC Transaction
	@Override
	public void rollBackTransaction() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			LogHelper.error(this, "Exception occurred while persisting data in intermediate tables" + e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				LogHelper.error(this, "Exception occurred while closing connection" + e);
			}
		}
	}

	/**
	 * 
	 */
	@Override
	public CacheDeviceTileResponse insertCacheDeviceToDb() {
		jdbcTemplate.setDataSource(datasource);
		String jobId = UUID.randomUUID().toString();
		CacheDeviceTileResponse cacheDeviceTileResponse = new CacheDeviceTileResponse();
		try(Connection connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource())) {

			String jobStatus = "INPROGRESS";

			cacheDeviceTileResponse.setJobId(jobId);
			cacheDeviceTileResponse.setJobStatus(jobStatus);

			connection.setAutoCommit(false);
			String query = "INSERT INTO DALMS_CACHE_SERVICES (JOB_ID, JOB_NAME, JOB_USER_ID, JOB_START, JOB_STATUS, JOB_LAST_UPDATED) "
					+ "VALUES (?, ?, ?, ?, ?, ?)";
			Object[] params = new Object[] { jobId, "CacheDevice", null, new Timestamp(new Date().getTime()), jobStatus,
					new Timestamp(new Date().getTime()) };
			jdbcTemplate.update(query, params);
			LogHelper.info(this, jobId + " inserted in DALMS_CACHE_SERVICES_TABLE");
			connection.commit();

		} catch (DataAccessException | SQLException e) {
			LogHelper.error(this, jobId + "==> " + e);
		}

		return cacheDeviceTileResponse;
	}

	@Override
	public void updateCacheDeviceToDb(String jobId, String jobStatus) {
		jdbcTemplate.setDataSource(datasource);
		try(Connection conn = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());) {
			
			conn.setAutoCommit(false);
			String query = "UPDATE DALMS_CACHE_SERVICES SET JOB_STATUS = ?, JOB_END = ?, JOB_LAST_UPDATED = ? WHERE JOB_ID = ?";
			Object[] params = new Object[] { jobStatus, new Timestamp(new Date().getTime()),
					new Timestamp(new Date().getTime()), jobId };
			jdbcTemplate.update(query, params);

			LogHelper.info(this, jobId + " updated with status " + jobStatus + " in DALMS_CACHE_SERVICES_TABLE");
			conn.commit();
		} catch (DataAccessException | SQLException e) {
			LogHelper.error(this, jobId + "==> " + e);
		}

	}

	/**
	 * @param jobId
	 * @return CacheDeviceTileResponse
	 */
	@Override
	public CacheDeviceTileResponse getCacheDeviceJobStatus(String jobId) throws ApplicationException {

		CacheDeviceTileResponse response = new CacheDeviceTileResponse();
		String jobStatus = null;
		try {
			jdbcTemplate.setDataSource(datasource);
			String query = "SELECT JOB_STATUS FROM DALMS_CACHE_SERVICES WHERE JOB_ID = ?";
			Object[] params = new Object[] { jobId };
			jobStatus = jdbcTemplate.queryForObject(query, String.class, params);
			if (StringUtils.isEmpty(jobStatus) || StringUtils.isBlank(jobStatus)) {
				throw new ApplicationException(ExceptionMessages.INVALID_JOB_ID);
			} else {
				response.setJobId(jobId);
				response.setJobStatus(jobStatus);
			}

		} catch (Exception exception) {
			LogHelper.error(this, jobId + "==>" + exception);
			throw new ApplicationException(ExceptionMessages.INVALID_JOB_ID);
		}
		return response;
	}
}
