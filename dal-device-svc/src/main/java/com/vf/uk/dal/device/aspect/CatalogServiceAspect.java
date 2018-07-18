package com.vf.uk.dal.device.aspect;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vf.uk.dal.common.configuration.ConfigHelper;
import com.vf.uk.dal.common.logger.LogHelper;
import com.vf.uk.dal.device.utils.Constants;

/**
 * 
 * This Aspect class to handle catalog Version
 *
 */
@Aspect
@Component
public class CatalogServiceAspect {

	/**
	 * 
	 * @param joinPoint
	 */
	@Before(value = "execution(* com.vf.uk.dal.device.controller.*.*(..))")
	public void beforeAdvice(JoinPoint joinPoint) {

		String version = null;
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes instanceof ServletRequestAttributes) {
			HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
			String versionFromHeader = servletRequest.getHeader("dal-catalogue-version");
			if (StringUtils.isNotBlank(versionFromHeader)) {
				version = "dal-cat-" + versionFromHeader;
				Constants.CATALOG_VERSION.set(version);
			} else {
				Constants.CATALOG_VERSION.set(null);
			}

		}
		if (!StringUtils.isNotBlank(version) && !StringUtils.isNotBlank(Constants.CATALOG_VERSION.get())) {
			Constants.CATALOG_VERSION.set(
					ConfigHelper.getString(Constants.ELASTIC_SEARCH_ALIAS, Constants.DEFAULT_ELASTIC_SEARCH_ALIAS));
		}
		LogHelper.info(this, Constants.CATALOG_VERSION.get());
	}

}