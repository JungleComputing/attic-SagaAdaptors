package org.ogf.sagaImpl.javaGAT.monitoring;

import java.util.ArrayList;

import org.ogf.saga.ObjectType;
import org.ogf.saga.attributes.Attributes;
import org.ogf.saga.error.AuthenticationFailed;
import org.ogf.saga.error.AuthorizationFailed;
import org.ogf.saga.error.BadParameter;
import org.ogf.saga.error.DoesNotExist;
import org.ogf.saga.error.IncorrectState;
import org.ogf.saga.error.NoSuccess;
import org.ogf.saga.error.NotImplemented;
import org.ogf.saga.error.PermissionDenied;
import org.ogf.saga.error.Timeout;
import org.ogf.saga.monitoring.Callback;
import org.ogf.sagaImpl.javaGAT.SagaObject;

// Question: how does a metric obtain the Monitorable and context to
// pass on to callbacks???

public class Metric extends SagaObject implements Attributes,
        org.ogf.saga.monitoring.Metric {
    
    private final MetricAttributes attributes = new MetricAttributes();
    private final ArrayList<Callback> callBacks = new ArrayList<Callback>();
    
    Metric(String name, String desc, String mode,
            String unit, String type, String value) {
        super(null);
    }
    
    @Override
    public ObjectType getType() {
        return ObjectType.METRIC;
    }

    public synchronized String[] findAttributes(String pattern) throws NotImplemented,
            BadParameter, AuthenticationFailed, AuthorizationFailed,
            PermissionDenied, Timeout, NoSuccess {
        return attributes.findAttributes(pattern);
    }

    public synchronized String getAttribute(String key) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            IncorrectState, DoesNotExist, Timeout, NoSuccess {
        return attributes.getAttribute(key);
    }

    public String[] getVectorAttribute(String key) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            IncorrectState, DoesNotExist, Timeout, NoSuccess {
        return attributes.getVectorAttribute(key);
    }

    public boolean isReadOnlyAttribute(String key) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            DoesNotExist, Timeout, NoSuccess {
        return attributes.isReadOnlyAttribute(key);
    }

    public boolean isRemovableAttribute(String key) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            DoesNotExist, Timeout, NoSuccess {
        return attributes.isRemovableAttribute(key);
    }

    public boolean isVectorAttribute(String key) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            DoesNotExist, Timeout, NoSuccess {
        return attributes.isVectorAttribute(key);
    }

    public boolean isWritableAttribute(String key) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            DoesNotExist, Timeout, NoSuccess {
        return attributes.isWritableAttribute(key);
    }

    public String[] listAttributes() throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            Timeout, NoSuccess {
        return attributes.listAttributes();
    }

    public void removeAttribute(String key) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            DoesNotExist, Timeout, NoSuccess {
        attributes.removeAttribute(key);

    }

    public void setAttribute(String key, String value) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            IncorrectState, BadParameter, DoesNotExist, Timeout, NoSuccess {
        attributes.setAttribute(key, value);
    }

    public void setVectorAttribute(String key, String[] values)
            throws NotImplemented, AuthenticationFailed, AuthorizationFailed,
            PermissionDenied, IncorrectState, BadParameter, DoesNotExist,
            Timeout, NoSuccess {
        attributes.setVectorAttribute(key, values);

    }

    public synchronized int addCallback(Callback cb) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            IncorrectState, Timeout, NoSuccess {
        // TODO: if type of metric is Final, throw IncorrectState.
        callBacks.add(cb);
        return callBacks.size()-1;
    }

    public void fire() throws NotImplemented, AuthenticationFailed,
            AuthorizationFailed, PermissionDenied, IncorrectState, Timeout,
            NoSuccess {
        // TODO Auto-generated method stub

    }

    public synchronized void removeCallback(int cookie) throws NotImplemented, BadParameter,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            IncorrectState, Timeout, NoSuccess {
        if (cookie >= callBacks.size()) {
            throw new BadParameter("removeCallback with invalid cookie: " + cookie);
        }
        if (callBacks.get(cookie) == null) {
            return;
        }
        Callback cb = callBacks.get(cookie);
        if (cb == null) {
            return;
        }
        callBacks.set(cookie, null);
        // TODO: wait until a possible invocation of this callback
        // (from this metric!) is finished.

    }

}
