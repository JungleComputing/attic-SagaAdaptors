package org.ogf.sagaImpl.javaGAT.session;

import java.util.HashMap;
import java.util.List;

import org.gridlab.gat.GATContext;
import org.gridlab.gat.security.SecurityContext;
import org.ogf.saga.ObjectType;
import org.ogf.saga.context.Context;
import org.ogf.saga.error.DoesNotExist;
import org.ogf.saga.error.NotImplemented;
import org.ogf.sagaImpl.javaGAT.SagaObject;

// Question: where to call GAT.end() ???

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
        c = ((org.ogf.sagaImpl.javaGAT.context.Context) context).cvt2GATContext();
        try {
            context = (Context) context.clone();
        } catch(CloneNotSupportedException e) {
            // Should not happen
        }
        map.put(context, c);
        reverseMap.put(c, context);
        return c;
    }
    
    public synchronized void addContext(Context context) throws NotImplemented {
         gatContext.addSecurityContext(getGATContext(context));
    }

    public void close() throws NotImplemented {
        map = null;
        reverseMap = null;
        gatContext = null;
    }

    protected void finalize() {
        try {
            close();
        } catch(Throwable e) {
            // ignored
        }
    }
    
    @SuppressWarnings("unchecked")
    public synchronized Object clone() throws CloneNotSupportedException {
        Session clone = (Session) super.clone();
        clone.map = new HashMap<Context, SecurityContext>(map);
        clone.reverseMap = new HashMap<SecurityContext, Context>(reverseMap);
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
                contexts[i++] = (Context) reverseMap.get(c).clone();
            } catch(CloneNotSupportedException e) {
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

    public ObjectType getType() {
        return ObjectType.SESSION;
    }
}
