package org.ogf.sagaImpl.javaGAT.context;

import org.gridlab.gat.security.SecurityContext;
import org.ogf.saga.ObjectType;
import org.ogf.saga.error.AuthenticationFailed;
import org.ogf.saga.error.AuthorizationFailed;
import org.ogf.saga.error.BadParameter;
import org.ogf.saga.error.DoesNotExist;
import org.ogf.saga.error.IncorrectState;
import org.ogf.saga.error.NoSuccess;
import org.ogf.saga.error.NotImplemented;
import org.ogf.saga.error.PermissionDenied;
import org.ogf.saga.error.Timeout;
import org.ogf.sagaImpl.javaGAT.SagaObject;

public class Context extends SagaObject implements org.ogf.saga.context.Context {
    
    private ContextAttributes attributes;
    
    Context(String type) throws NotImplemented, IncorrectState, Timeout, NoSuccess {
        super(null);
        attributes = new ContextAttributes();
        if (type != null && ! type.equals("")) {
            try {
                attributes.setValue(TYPE, type);
            } catch(Throwable e) {
                throw new NoSuccess("Oops, could not set TYPE attribute", e);
            }
            setDefaults();
        }
    }
    
    public Object clone() throws CloneNotSupportedException {
        Context clone = (Context) super.clone();
        clone.attributes = (ContextAttributes) attributes.clone();
        return clone;
    }

    @Override
    public ObjectType getType() {
        return ObjectType.CONTEXT;
    }

    public void setDefaults() throws NotImplemented, IncorrectState, Timeout,
            NoSuccess {
        String type;
        try {
            type = attributes.getAttribute(TYPE);
        } catch(DoesNotExist e1) {
            throw new IncorrectState("setDefaults called but TYPE attribute not set");
        } catch(Throwable e) {
            // Should not happen.
            throw new NoSuccess("could not get TYPE attribute", e);
        }
        if (! type.equals("")) {
            throw new NoSuccess("Unregognized TYPE attribute value: " + type);
            // TODO: implement.
        }
    }

    public String[] findAttributes(String pattern)
            throws NotImplemented, BadParameter, AuthenticationFailed,
            AuthorizationFailed, PermissionDenied, Timeout, NoSuccess {
        return attributes.findAttributes(pattern);
    }

    public String getAttribute(String key) throws NotImplemented,
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
    
    public SecurityContext cvt2GATContext() {
        // TODO: implement
        return null;
    }
}
