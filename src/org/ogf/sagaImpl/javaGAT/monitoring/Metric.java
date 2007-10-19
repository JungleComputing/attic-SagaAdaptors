package org.ogf.sagaImpl.javaGAT.monitoring;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import org.ogf.saga.monitoring.Monitorable;
import org.ogf.sagaImpl.javaGAT.SagaObject;

public class Metric extends SagaObject implements Attributes,
        org.ogf.saga.monitoring.Metric {
    
    /** Threadpool for dealing with callbacks. */
    private static ExecutorService callbackHandler = Executors.newCachedThreadPool();
    
    private class CallbackHandler implements Runnable {
        boolean busy = false;
        private Callback cb;
        private Metric metric;
        private int cookie;
        
        public CallbackHandler(Callback cb, Metric metric, int cookie) {
            this.cb = cb;
            this.metric = metric;
            this.cookie = cookie;
        }
        
        public void run() {        // Wait until a previous invocation of this cb
            // is finished, then set a flag that it is busy.
            synchronized(this) {
                while (busy) {
                    try {
                        wait();
                    } catch(Exception e) {
                        // ignored
                    }
                }
                busy = true;
            }
            
            boolean retval;
            try {
                retval = cb.cb(monitorable, metric, null);
            } catch(Throwable e) {
                // if callback throws an exception, keep the callback.
                retval = true;
            }
            if (! retval) {
                try {
                    metric.removeCallback(cookie);
                } catch(Throwable e) {
                    // ignored
                }
            }
            
            // Release busy flag, notify waiters.
            synchronized(this) {
                busy = false;
                notifyAll();
            }            
        }        
    };
    
    private final MetricAttributes attributes = new MetricAttributes();
    private final ArrayList<CallbackHandler> callBacks = new ArrayList<CallbackHandler>();
    private Monitorable monitorable;
    
    Metric(String name, String desc, String mode,
            String unit, String type, String value) {
        super(null);
    }
    
    public Metric(Monitorable monitorable, String name, String desc,
            String mode, String unit, String type, String value) {
        this(name, desc, mode, unit, type, value);
        this.monitorable = monitorable;
    }

    // This method is to be called from addMetric() implementations.
    public synchronized void setMonitorable(Monitorable monitorable) {
        this.monitorable = monitorable;
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
        CallbackHandler b = new CallbackHandler(cb, this, callBacks.size());
        callBacks.add(b);
        return callBacks.size()-1;
    }

    public void fire() throws NotImplemented, AuthenticationFailed,
            AuthorizationFailed, PermissionDenied, IncorrectState, Timeout,
            NoSuccess {
        
        ArrayList<CallbackHandler> cbhs;
        
        synchronized(this) {
            cbhs = new ArrayList<CallbackHandler>(callBacks);
        }
        
        synchronized(callbackHandler) {            
            for (CallbackHandler cbh : cbhs) {
                if (cbh != null) {
                    callbackHandler.submit(cbh);
                }
            }
        }
    }
  
    public void removeCallback(int cookie) throws NotImplemented, BadParameter,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            IncorrectState, Timeout, NoSuccess {
        
        CallbackHandler cb;
        
        synchronized(this) {
            if (cookie >= callBacks.size()) {
                throw new BadParameter("removeCallback with invalid cookie: " + cookie);
            }

            cb = callBacks.get(cookie);
            if (cb == null) {
                return;
            }
            callBacks.set(cookie, null);
        }

        // The SAGA specs prescribe that we block here until no
        // activation of the removed method is active.
        synchronized(cb) {
            while (cb.busy) {
                try {
                    cb.wait();
                } catch(Exception e) {
                    // ignored
                }
            }
        }
    }

}
