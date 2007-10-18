package org.ogf.sagaImpl.javaGAT.context;

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
    
    Context() {
        super(null);
    }
    
    @Override
    public org.ogf.saga.SagaObject deepCopy() throws NoSuccess {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ObjectType getType() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setDefaults() throws NotImplemented, IncorrectState, Timeout,
            NoSuccess {
        // TODO Auto-generated method stub

    }

    public String[] findAttributes(String pattern)
            throws NotImplemented, BadParameter, AuthenticationFailed,
            AuthorizationFailed, PermissionDenied, Timeout, NoSuccess {
        // TODO Auto-generated method stub
        return null;
    }

    public String getAttribute(String key) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            IncorrectState, DoesNotExist, Timeout, NoSuccess {
        // TODO Auto-generated method stub
        return null;
    }

    public String[] getVectorAttribute(String key) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            IncorrectState, DoesNotExist, Timeout, NoSuccess {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isReadOnlyAttribute(String key) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            DoesNotExist, Timeout, NoSuccess {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isRemovableAttribute(String key) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            DoesNotExist, Timeout, NoSuccess {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isVectorAttribute(String key) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            DoesNotExist, Timeout, NoSuccess {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isWritableAttribute(String key) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            DoesNotExist, Timeout, NoSuccess {
        // TODO Auto-generated method stub
        return false;
    }

    public String[] listAttributes() throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            Timeout, NoSuccess {
        // TODO Auto-generated method stub
        return null;
    }

    public void removeAttribute(String key) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            DoesNotExist, Timeout, NoSuccess {
        // TODO Auto-generated method stub

    }

    public void setAttribute(String key, String value) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            IncorrectState, BadParameter, DoesNotExist, Timeout, NoSuccess {
        // TODO Auto-generated method stub
    }

    public void setVectorAttribute(String key, String[] values)
            throws NotImplemented, AuthenticationFailed, AuthorizationFailed,
            PermissionDenied, IncorrectState, BadParameter, DoesNotExist,
            Timeout, NoSuccess {
        // TODO Auto-generated method stub
     }
}
