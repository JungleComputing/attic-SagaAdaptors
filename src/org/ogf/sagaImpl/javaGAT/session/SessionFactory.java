package org.ogf.sagaImpl.javaGAT.session;

import org.ogf.saga.error.NoSuccess;
import org.ogf.saga.error.NotImplemented;

public class SessionFactory extends org.ogf.saga.session.SessionFactory {

    private static Session defaultSession = new Session(true);
    
    @Override
    protected synchronized org.ogf.saga.session.Session doCreateSession(boolean defaults)
            throws NotImplemented, NoSuccess {
        if (defaults) {
            return defaultSession;
        }
        return new Session();
    }
}
