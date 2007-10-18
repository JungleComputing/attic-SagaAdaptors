package org.ogf.sagaImpl.javaGAT.namespace;

import org.ogf.saga.URL;
import org.ogf.saga.error.AuthenticationFailed;
import org.ogf.saga.error.AuthorizationFailed;
import org.ogf.saga.error.BadParameter;
import org.ogf.saga.error.DoesNotExist;
import org.ogf.saga.error.IncorrectURL;
import org.ogf.saga.error.NoSuccess;
import org.ogf.saga.error.NotImplemented;
import org.ogf.saga.error.PermissionDenied;
import org.ogf.saga.error.Timeout;
import org.ogf.saga.namespace.NSDirectory;
import org.ogf.saga.session.Session;
import org.ogf.saga.task.Task;
import org.ogf.saga.task.TaskMode;

public class NSFactory extends org.ogf.saga.namespace.NSFactory {

    @Override
    protected Task<NSDirectory> doCreateNSDirectory(TaskMode mode,
            Session session, URL name, int flags) throws NotImplemented {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected NSDirectory doCreateNSDirectory(Session session, URL name,
            int flags) throws NotImplemented, IncorrectURL,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            BadParameter, DoesNotExist, Timeout, NoSuccess {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Task<org.ogf.saga.namespace.NSEntry> doCreateNSEntry(TaskMode mode,
            Session session, URL name, int flags) throws NotImplemented {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected org.ogf.saga.namespace.NSEntry doCreateNSEntry(Session session, URL name,
            int flags) throws NotImplemented, IncorrectURL,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            BadParameter, DoesNotExist, Timeout, NoSuccess {
        return new NSEntry(session, name, flags);
    }

}
