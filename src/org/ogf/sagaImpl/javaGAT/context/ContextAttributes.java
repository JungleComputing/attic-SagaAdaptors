package org.ogf.sagaImpl.javaGAT.context;

import org.ogf.saga.error.BadParameter;
import org.ogf.saga.error.DoesNotExist;
import org.ogf.saga.error.IncorrectState;
import org.ogf.saga.error.NotImplemented;
import org.ogf.sagaImpl.javaGAT.attributes.AttributeType;
import org.ogf.sagaImpl.javaGAT.attributes.Attributes;

class ContextAttributes extends Attributes {
    ContextAttributes() {
        addAttribute(Context.TYPE, AttributeType.STRING, false, false, false, false);
        addAttribute(Context.SERVER, AttributeType.STRING, false, false, false, false);
        addAttribute(Context.CERTREPOSITORY, AttributeType.STRING, false, false, false, false);
        addAttribute(Context.USERPROXY, AttributeType.STRING, false, false, false, false);
        addAttribute(Context.USERCERT, AttributeType.STRING, false, false, false, false);
        addAttribute(Context.USERKEY, AttributeType.STRING, false, false, false, false);
        addAttribute(Context.USERPASS, AttributeType.STRING, false, false, false, false);
        addAttribute(Context.USERVO, AttributeType.STRING, false, false, false, false);
        addAttribute(Context.LIFETIME, AttributeType.INT, false, false, false, false);
        addAttribute(Context.REMOTEID, AttributeType.STRING, false, true, false, false);
        addAttribute(Context.REMOTEHOST, AttributeType.STRING, false, true, false, false);
        addAttribute(Context.REMOTEPORT, AttributeType.INT, false, true, false, false);
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
