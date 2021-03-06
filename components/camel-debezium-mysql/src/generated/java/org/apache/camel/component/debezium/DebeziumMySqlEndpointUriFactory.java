/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.component.debezium;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.camel.spi.EndpointUriFactory;

/**
 * Generated by camel build tools - do NOT edit this file!
 */
public class DebeziumMySqlEndpointUriFactory extends org.apache.camel.support.component.EndpointUriFactorySupport implements EndpointUriFactory {

    private static final String BASE = ":name";

    private static final Set<String> PROPERTY_NAMES;
    static {
        Set<String> set = new HashSet<>(78);
        set.add("name");
        set.add("additionalProperties");
        set.add("bridgeErrorHandler");
        set.add("internalKeyConverter");
        set.add("internalValueConverter");
        set.add("offsetCommitPolicy");
        set.add("offsetCommitTimeoutMs");
        set.add("offsetFlushIntervalMs");
        set.add("offsetStorage");
        set.add("offsetStorageFileName");
        set.add("offsetStoragePartitions");
        set.add("offsetStorageReplicationFactor");
        set.add("offsetStorageTopic");
        set.add("exceptionHandler");
        set.add("exchangePattern");
        set.add("basicPropertyBinding");
        set.add("synchronous");
        set.add("bigintUnsignedHandlingMode");
        set.add("binaryHandlingMode");
        set.add("binlogBufferSize");
        set.add("columnBlacklist");
        set.add("connectKeepAlive");
        set.add("connectKeepAliveIntervalMs");
        set.add("connectTimeoutMs");
        set.add("databaseBlacklist");
        set.add("databaseHistory");
        set.add("databaseHistoryFileFilename");
        set.add("databaseHistoryKafkaBootstrapServers");
        set.add("databaseHistoryKafkaRecoveryAttempts");
        set.add("databaseHistoryKafkaRecoveryPollIntervalMs");
        set.add("databaseHistoryKafkaTopic");
        set.add("databaseHistorySkipUnparseableDdl");
        set.add("databaseHistoryStoreOnlyMonitoredTablesDdl");
        set.add("databaseHostname");
        set.add("databaseInitialStatements");
        set.add("databaseJdbcDriver");
        set.add("databasePassword");
        set.add("databasePort");
        set.add("databaseServerId");
        set.add("databaseServerIdOffset");
        set.add("databaseServerName");
        set.add("databaseSslKeystore");
        set.add("databaseSslKeystorePassword");
        set.add("databaseSslMode");
        set.add("databaseSslTruststore");
        set.add("databaseSslTruststorePassword");
        set.add("databaseUser");
        set.add("databaseWhitelist");
        set.add("decimalHandlingMode");
        set.add("enableTimeAdjuster");
        set.add("eventDeserializationFailureHandlingMode");
        set.add("eventProcessingFailureHandlingMode");
        set.add("gtidNewChannelPosition");
        set.add("gtidSourceExcludes");
        set.add("gtidSourceFilterDmlEvents");
        set.add("gtidSourceIncludes");
        set.add("heartbeatIntervalMs");
        set.add("heartbeatTopicsPrefix");
        set.add("includeQuery");
        set.add("includeSchemaChanges");
        set.add("inconsistentSchemaHandlingMode");
        set.add("maxBatchSize");
        set.add("maxQueueSize");
        set.add("messageKeyColumns");
        set.add("pollIntervalMs");
        set.add("skippedOperations");
        set.add("snapshotDelayMs");
        set.add("snapshotFetchSize");
        set.add("snapshotLockingMode");
        set.add("snapshotMode");
        set.add("snapshotNewTables");
        set.add("snapshotSelectStatementOverrides");
        set.add("sourceStructVersion");
        set.add("tableBlacklist");
        set.add("tableIgnoreBuiltin");
        set.add("tableWhitelist");
        set.add("timePrecisionMode");
        set.add("tombstonesOnDelete");
        PROPERTY_NAMES = set;
    }

    @Override
    public boolean isEnabled(String scheme) {
        return "debezium-mysql".equals(scheme);
    }

    @Override
    public String buildUri(String scheme, Map<String, Object> properties) throws URISyntaxException {
        String syntax = scheme + BASE;
        String uri = syntax;

        Map<String, Object> copy = new HashMap<>(properties);

        uri = buildPathParameter(syntax, uri, "name", null, true, copy);
        uri = buildQueryParameters(uri, copy);
        return uri;
    }

    @Override
    public Set<String> propertyNames() {
        return PROPERTY_NAMES;
    }

    @Override
    public boolean isLenientProperties() {
        return false;
    }
}

