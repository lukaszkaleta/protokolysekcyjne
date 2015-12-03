package com.ra.dissection.protocol.dao;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

/**
 * @author lukaszkaleta
 * @since 6.8.4.0-R04v44 07.06.13 07:38
 */
public class DataSourceInformation {

    private Logger log = LoggerFactory.getLogger(getClass());

    private final DataSource dataSource;

    public DataSourceInformation(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void printInformation() {
        if (dataSource instanceof BasicDataSource) {
            BasicDataSource basicDataSource = (BasicDataSource) dataSource;
            log.info("Data Source:");
            log.info("Url <{}>", basicDataSource.getUrl());
            log.info("Username <{}>", basicDataSource.getUsername());
            log.info("Driver Class Name <{}>", basicDataSource.getDriverClassName());
            log.info("Default Catalog <{}>", basicDataSource.getDefaultCatalog());
            log.info("Default Auto Commit <{}>", basicDataSource.getDefaultAutoCommit());
            log.info("Initial Size <{}>", basicDataSource.getInitialSize());
            log.info("Max Active <{}>", basicDataSource.getMaxActive());
            log.info("Max Idle <{}>", basicDataSource.getMaxIdle());
            log.info("Min Idle <{}>", basicDataSource.getMinIdle());
            log.info("Max Wait <{}>", basicDataSource.getMaxWait());
            log.info("Max Open Prepared Statements <{}>", basicDataSource.getMaxOpenPreparedStatements());
            log.info("Time Between Eviction Runs Millis <{}>", basicDataSource.getTimeBetweenEvictionRunsMillis());
            log.info("Num Active <{}>", basicDataSource.getNumActive());
            log.info("Num Idle <{}>", basicDataSource.getNumIdle());
            log.info("Connection Init SQL <{}>", basicDataSource.getConnectionInitSqls());
        }
    }
}
