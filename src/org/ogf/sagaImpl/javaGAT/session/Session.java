package org.ogf.sagaImpl.javaGAT.session;

import java.util.HashMap;
import java.util.List;

import org.gridlab.gat.GATContext;
import org.gridlab.gat.security.SecurityContext;
import org.ogf.saga.ObjectType;
import org.ogf.saga.context.Context;
import org.ogf.saga.error.DoesNotExist;
import org.ogf.saga.error.NoSuccess;
import org.ogf.saga.error.NotImplemented;
import org.ogf.sagaImpl.javaGAT.SagaObject;

/**
 * Corresponds to a JavaGat Context.
 */
public class Session extends SagaObject implements org.ogf.saga.session.Session {

    private GATContext gatContext = new GATContext();
    private HashMap<Context, SecurityContext> map = new HashMap<Context, SecurityContext>();
    private HashMap<SecurityContext, Context> reverseMap
            = new HashMap<SecurityContext, Context>();
    
    Session(boolean defaults) {
        super(null);
    }
    
    Session() {
        this(false);
    }
    
    private SecurityContext getGATContext(Context context) {
        SecurityContext c = map.get(context);
        if (c != null) {
            return c;
        }
        // TODO: implement
        return null;
    }
    
    public synchronized void addContext(Context context) throws NotImplemented {
         gatContext.addSecurityContext(getGATContext(context));
    }

    public void close() throws NotImplemented {
        // nothing, for now.
    }
    
    @SuppressWarnings("unchecked")
    public synchronized Object clone() throws CloneNotSupportedException {
        Session clone = (Session) super.clone();
        clone.gatContext = (GATContext) gatContext.clone(); 
        return clone;
    }
    
    public void close(float timeoutInSeconds) throws NotImplemented {
        close();
    }

    public synchronized Context[] listContexts() throws NotImplemented {
        List<SecurityContext> l = gatContext.getSecurityContexts();
        Context[] contexts = new Context[l.size()];
        int i = 0;
        for (SecurityContext c : l) {
            try {
                contexts[i++] = (Context) reverseMap.get(c).deepCopy();
            } catch(NoSuccess e) {
                // should not happen.
                contexts[i++] = null;
            }
        }
        return contexts;
    }

    public synchronized void removeContext(Context context) throws NotImplemented,
            DoesNotExist {
        SecurityContext c = map.get(context);
        if (c == null) {
            throw new DoesNotExist("Element " + context + " does not exist");
        }
        map.remove(context);
        reverseMap.remove(c);
        gatContext.removeSecurityContext(c);
    }

    public org.ogf.saga.SagaObject deepCopy() throws NoSuccess {
        try {
            return (org.ogf.saga.SagaObject) clone();
        } catch(CloneNotSupportedException e) {
            // should not happen.
            throw new NoSuccess("Got CloneNotSupportedException!", e);
        }
    }

    public ObjectType getType() {
        return ObjectType.SESSION;
    }
}
