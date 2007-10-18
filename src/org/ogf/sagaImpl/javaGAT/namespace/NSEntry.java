package org.ogf.sagaImpl.javaGAT.namespace;

import org.ogf.saga.ObjectType;
import org.ogf.saga.URL;
import org.ogf.saga.error.AlreadyExists;
import org.ogf.saga.error.AuthenticationFailed;
import org.ogf.saga.error.AuthorizationFailed;
import org.ogf.saga.error.BadParameter;
import org.ogf.saga.error.IncorrectState;
import org.ogf.saga.error.IncorrectURL;
import org.ogf.saga.error.NoSuccess;
import org.ogf.saga.error.NotImplemented;
import org.ogf.saga.error.PermissionDenied;
import org.ogf.saga.error.Timeout;
import org.ogf.saga.task.RVTask;
import org.ogf.saga.task.Task;
import org.ogf.saga.task.TaskMode;
import org.ogf.sagaImpl.javaGAT.SagaObject;
import org.ogf.sagaImpl.javaGAT.attributes.Attributes;
import org.ogf.sagaImpl.javaGAT.session.Session;

public class NSEntry extends SagaObject implements
        org.ogf.saga.namespace.NSEntry {
    
    private boolean closed = false;
    
    Attributes attributes = new Attributes();
    
    NSEntry(org.ogf.saga.session.Session session, URL name, int flags) {
        super((Session) session);
    }

    @Override
    public org.ogf.saga.SagaObject deepCopy() throws NoSuccess {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ObjectType getType() {
        return ObjectType.NSENTRY;
    }

    public void close() throws NotImplemented, IncorrectState, NoSuccess {
        close(0.0F);
    }

    public void close(float timeoutInSeconds) throws NotImplemented,
            IncorrectState, NoSuccess {
        // TODO Auto-generated method stub

    }
    
    protected void finalize() {
        if (! closed) {
            try {
                close();
            } catch(Throwable e) {
                // ignored
            }
        }
    }

    public Task close(TaskMode mode) throws NotImplemented {
        return close(mode, 0.0F);
    }

    public Task close(TaskMode mode, float timeoutInSeconds)
            throws NotImplemented {
        // TODO Auto-generated method stub
        return null;
    }

    public void copy(URL target, int flags) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            BadParameter, IncorrectState, AlreadyExists, Timeout, NoSuccess,
            IncorrectURL {
        // TODO Auto-generated method stub

    }

    public Task copy(TaskMode mode, URL target, int flags)
            throws NotImplemented {
        // TODO Auto-generated method stub
        return null;
    }

    public String getCWD() throws NotImplemented, IncorrectState, Timeout,
            NoSuccess {
        // TODO Auto-generated method stub
        return null;
    }

    public RVTask<URL> getCWD(TaskMode mode) throws NotImplemented {
        // TODO Auto-generated method stub
        return null;
    }

    public String getName() throws NotImplemented, IncorrectState, Timeout,
            NoSuccess {
        // TODO Auto-generated method stub
        return null;
    }

    public RVTask<String> getName(TaskMode mode) throws NotImplemented {
        // TODO Auto-generated method stub
        return null;
    }

    public URL getURL() throws NotImplemented, IncorrectState, Timeout,
            NoSuccess {
        // TODO Auto-generated method stub
        return null;
    }

    public RVTask<URL> getURL(TaskMode mode) throws NotImplemented {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isDir() throws NotImplemented, AuthenticationFailed,
            AuthorizationFailed, PermissionDenied, BadParameter,
            IncorrectState, Timeout, NoSuccess {
        // TODO Auto-generated method stub
        return false;
    }

    public RVTask<Boolean> isDir(TaskMode mode, int flags)
            throws NotImplemented {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isEntry() throws NotImplemented, AuthenticationFailed,
            AuthorizationFailed, PermissionDenied, BadParameter,
            IncorrectState, Timeout, NoSuccess {
        // TODO Auto-generated method stub
        return false;
    }

    public RVTask<Boolean> isEntry(TaskMode mode, int flags)
            throws NotImplemented {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isLink() throws NotImplemented, AuthenticationFailed,
            AuthorizationFailed, PermissionDenied, BadParameter,
            IncorrectState, Timeout, NoSuccess {
        // TODO Auto-generated method stub
        return false;
    }

    public RVTask<Boolean> isLink(TaskMode mode, int flags)
            throws NotImplemented {
        // TODO Auto-generated method stub
        return null;
    }

    public void link(URL target, int flags) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            BadParameter, IncorrectState, AlreadyExists, Timeout, NoSuccess,
            IncorrectURL {
        // TODO Auto-generated method stub

    }

    public Task link(TaskMode mode, URL target, int flags)
            throws NotImplemented {
        // TODO Auto-generated method stub
        return null;
    }

    public void move(URL target, int flags) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            BadParameter, IncorrectState, AlreadyExists, Timeout, NoSuccess,
            IncorrectURL {
        // TODO Auto-generated method stub

    }

    public Task move(TaskMode mode, URL target, int flags)
            throws NotImplemented {
        // TODO Auto-generated method stub
        return null;
    }

    public void permissionsAllow(String id, int permissions, int flags)
            throws NotImplemented, AuthenticationFailed, AuthorizationFailed,
            PermissionDenied, IncorrectState, BadParameter, Timeout, NoSuccess {
        // TODO Auto-generated method stub

    }

    public Task permissionsAllow(TaskMode mode, String id, int permissions,
            int flags) throws NotImplemented {
        // TODO Auto-generated method stub
        return null;
    }

    public void permissionsDeny(String id, int permissions, int flags)
            throws NotImplemented, AuthenticationFailed, AuthorizationFailed,
            PermissionDenied, BadParameter, Timeout, NoSuccess {
        // TODO Auto-generated method stub

    }

    public Task permissionsDeny(TaskMode mode, String id, int permissions,
            int flags) throws NotImplemented {
        // TODO Auto-generated method stub
        return null;
    }

    public URL readLink() throws NotImplemented, AuthenticationFailed,
            AuthorizationFailed, PermissionDenied, BadParameter,
            IncorrectState, Timeout, NoSuccess {
        // TODO Auto-generated method stub
        return null;
    }

    public RVTask<URL> readLink(TaskMode mode) throws NotImplemented {
        // TODO Auto-generated method stub
        return null;
    }

    public void remove(int flags) throws NotImplemented, AuthenticationFailed,
            AuthorizationFailed, PermissionDenied, BadParameter,
            IncorrectState, Timeout, NoSuccess {
        // TODO Auto-generated method stub

    }

    public Task remove(TaskMode mode, int flags) throws NotImplemented {
        // TODO Auto-generated method stub
        return null;
    }

    public String getGroup() throws NotImplemented, AuthenticationFailed,
            AuthorizationFailed, PermissionDenied, Timeout, NoSuccess {
        // TODO Auto-generated method stub
        return null;
    }

    public RVTask<String> getGroup(TaskMode mode) throws NotImplemented {
        // TODO Auto-generated method stub
        return null;
    }

    public String getOwner() throws NotImplemented, AuthenticationFailed,
            AuthorizationFailed, PermissionDenied, Timeout, NoSuccess {
        // TODO Auto-generated method stub
        return null;
    }

    public RVTask<String> getOwner(TaskMode mode) throws NotImplemented {
        // TODO Auto-generated method stub
        return null;
    }

    public void permissionsAllow(String id, int permissions)
            throws NotImplemented, AuthenticationFailed, AuthorizationFailed,
            PermissionDenied, BadParameter, Timeout, NoSuccess {
        // TODO Auto-generated method stub

    }

    public Task permissionsAllow(TaskMode mode, String id, int permissions)
            throws NotImplemented {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean permissionsCheck(String id, int permissions)
            throws NotImplemented, AuthenticationFailed, AuthorizationFailed,
            PermissionDenied, BadParameter, Timeout, NoSuccess {
        // TODO Auto-generated method stub
        return false;
    }

    public RVTask<Boolean> permissionsCheck(TaskMode mode, String id,
            int permissions) throws NotImplemented {
        // TODO Auto-generated method stub
        return null;
    }

    public void permissionsDeny(String id, int permissions)
            throws NotImplemented, AuthenticationFailed, AuthorizationFailed,
            PermissionDenied, BadParameter, Timeout, NoSuccess {
        // TODO Auto-generated method stub

    }

    public Task permissionsDeny(TaskMode mode, String id, int permissions)
            throws NotImplemented {
        // TODO Auto-generated method stub
        return null;
    }

}
