package org.ogf.sagaImpl.javaGAT;

import java.util.UUID;

import org.ogf.saga.ObjectType;
import org.ogf.saga.error.DoesNotExist;
import org.ogf.sagaImpl.javaGAT.session.Session;

public abstract class SagaObject implements org.ogf.saga.SagaObject, Cloneable {
    
    private Session session;
    private UUID uuid = UUID.randomUUID();
    
    public SagaObject(Session session) {
        this.session = session;
    }
    
    public Session obtainSession() {
        return session;
    }

    public Object clone() throws CloneNotSupportedException {
        SagaObject clone = (SagaObject) clone();
        // Should we generate a new uuid here ??? I think yes.
        // Note: session should not get cloned! According to the SAGA
        // specs, it gets "shallow copied", which means that the
        // object reference is copied.
        clone.uuid = UUID.randomUUID();
        return clone;
    }

    public String getId() {
        return uuid.toString();
    }

    public org.ogf.saga.session.Session getSession() throws DoesNotExist {
        if (session == null) {
            throw new DoesNotExist("No session associated with object");
        }
        return session;
    }

    public abstract ObjectType getType();
}
