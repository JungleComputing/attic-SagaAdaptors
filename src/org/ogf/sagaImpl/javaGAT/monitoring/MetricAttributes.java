package org.ogf.sagaImpl.javaGAT.monitoring;

import org.ogf.saga.error.BadParameter;
import org.ogf.saga.error.DoesNotExist;
import org.ogf.saga.error.IncorrectState;
import org.ogf.saga.error.NotImplemented;
import org.ogf.sagaImpl.javaGAT.attributes.AttributeType;
import org.ogf.sagaImpl.javaGAT.attributes.Attributes;

class MetricAttributes extends Attributes {
    MetricAttributes() {
        addAttribute(Metric.NAME, AttributeType.STRING, false, true, false, false);
        addAttribute(Metric.DESCRIPTION, AttributeType.STRING, false, true, false, false);
        addAttribute(Metric.MODE, AttributeType.STRING, false, true, false, false);
        addAttribute(Metric.UNIT, AttributeType.STRING, false, true, false, false);
        addAttribute(Metric.TYPE, AttributeType.STRING, false, true, false, false);
        addAttribute(Metric.VALUE, AttributeType.STRING, false, false, false, false);
    }
    
    protected void setValue(String key, String value)
            throws DoesNotExist, NotImplemented, IncorrectState, BadParameter {
        super.setValue(key, value);
    }
    
    protected void setVectorValue(String key, String[] values)
            throws DoesNotExist, NotImplemented, IncorrectState, BadParameter {
        super.setVectorValue(key, values);
    }
}
