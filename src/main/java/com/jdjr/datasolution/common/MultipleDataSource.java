package com.jdjr.datasolution.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by wangbo on 16/1/4.
 */
public class MultipleDataSource extends AbstractRoutingDataSource {
    private static final Logger logger = LoggerFactory.getLogger(MultipleDataSource.class);
    private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();

    public static void setMaster() {
        logger.debug("MultipleDataSource set to master");
        dataSourceKey.set("masterDataSource");
    }

    public static void setSlave() {
        logger.debug("MultipleDataSource set to slave");
        dataSourceKey.set("slaveDataSource");
    }


    @Override
    protected Object determineCurrentLookupKey() {
        try {
            logger.debug("MultipleDataSource invoke " + dataSourceKey.get());
            return dataSourceKey.get();
        } finally {
            setMaster();
        }
    }
}
