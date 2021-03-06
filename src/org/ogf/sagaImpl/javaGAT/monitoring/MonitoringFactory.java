package org.ogf.sagaImpl.javaGAT.monitoring;

import org.ogf.saga.error.BadParameter;
import org.ogf.saga.error.NoSuccess;
import org.ogf.saga.error.NotImplemented;
import org.ogf.saga.error.Timeout;
import org.ogf.saga.monitoring.Metric;

public class MonitoringFactory extends
        org.ogf.saga.monitoring.MonitoringFactory {

    @Override
    protected Metric doCreateMetric(String name, String desc, String mode,
            String unit, String type, String value) throws NotImplemented,
            BadParameter, Timeout, NoSuccess {
        return new org.ogf.sagaImpl.javaGAT.monitoring.Metric(name, desc, mode, unit, type, value);
    }

}
