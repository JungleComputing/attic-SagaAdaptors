package org.ogf.sagaImpl.javaGAT.attributes;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import org.ogf.saga.error.AuthenticationFailed;
import org.ogf.saga.error.AuthorizationFailed;
import org.ogf.saga.error.BadParameter;
import org.ogf.saga.error.DoesNotExist;
import org.ogf.saga.error.IncorrectState;
import org.ogf.saga.error.NoSuccess;
import org.ogf.saga.error.NotImplemented;
import org.ogf.saga.error.PermissionDenied;
import org.ogf.saga.error.Timeout;

// Questions:
// - what does removable mean? That the value of an attribute can be removed?
//   Or that the attribute is removed altogether (and can never be accessed
//   anymore?
// - what exactly should listAttributes list? All attributes that have a value?
//   Or just all supported attributes? Or all implemented attributes?

/**
 * This is the base class of all attributes in the SAGA implementation on top of
 * JavaGAT.
 */
public class Attributes implements org.ogf.saga.attributes.Attributes, Cloneable {
    
    // TODO: check that this is the proper format.
    private final SimpleDateFormat dateFormatter
        = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy");
    
    // Information about attributes: name, type, value,
    // read/write, removable or not, implemented. 
    private static class AttributeInfo {
        String name;
        AttributeType type;
        String value;
        String[] vectorValue;
        boolean hasValue;
        boolean vector;
        boolean readOnly;
        boolean removable;
        boolean notImplemented;
        
        public AttributeInfo(String name, AttributeType type, boolean vector,
                boolean readOnly, boolean notImplemented, boolean removable) {
            this.name = name;
            this.type = type;
            this.vector = vector;
            this.readOnly = readOnly;
            this.notImplemented = notImplemented;
            this.removable = removable;
            this.hasValue = false;
            this.value = null;
            this.vectorValue = null;
        }
    }
    
    private HashMap<String, AttributeInfo> attributes;

    public Attributes() {
        attributes = new HashMap<String, AttributeInfo>();
        dateFormatter.setLenient(false);
    }
    
    public Object clone() throws CloneNotSupportedException {
        Attributes clone = (Attributes) super.clone();
        clone.attributes = new HashMap<String, AttributeInfo>(attributes);
        return clone;
    }
    
    // Stores information about a specific attribute.
    public synchronized void addAttribute(String name, AttributeType type, boolean vector,
            boolean readOnly, boolean notImplemented, boolean removeable) {
        attributes.put(name,
            new AttributeInfo(name, type, vector, readOnly, notImplemented, removeable));
    }

    public String[] findAttributes(String pattern) throws NotImplemented,
            BadParameter, AuthenticationFailed, AuthorizationFailed,
            PermissionDenied, Timeout, NoSuccess {
        // TODO Auto-generated method stub
        throw new NotImplemented("findAttributes not implemented yet");
    }

    private AttributeInfo getInfo(String key) throws DoesNotExist, NotImplemented {
        AttributeInfo info = attributes.get(key);
        
        if (info == null) {
            throw new DoesNotExist("Attribute " + key + " does not exist");
        }
        if (info.notImplemented) {
            throw new NotImplemented("Attribute " + key
                    + " not available in this implementation");
        }
        return info;
    }
    
    private AttributeInfo getVectorInfo(String key, boolean vector)
            throws DoesNotExist, NotImplemented, IncorrectState {
        AttributeInfo info = getInfo(key);
        if (vector != info.vector) {
            throw new IncorrectState("Attribute " + key
                    + " is " + (vector ? "not " : "") + "a vector attribute");
        }
        return info;
    }
    
    public synchronized String getAttribute(String key) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            IncorrectState, DoesNotExist, Timeout, NoSuccess {
        
        AttributeInfo info = getVectorInfo(key, false);
        
        if (! info.hasValue) {
            throw new DoesNotExist("Attribute " + key
                    + " has no value");
        }
        
        return info.value;
    }

    public synchronized String[] getVectorAttribute(String key) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            IncorrectState, DoesNotExist, Timeout, NoSuccess {
        
        AttributeInfo info = getVectorInfo(key, true);
        
        if (! info.hasValue) {
            throw new DoesNotExist("Attribute " + key
                    + " has no value");
        }
        
        return info.vectorValue.clone();
    }

    public synchronized boolean isReadOnlyAttribute(String key) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            DoesNotExist, Timeout, NoSuccess {
        
        return getInfo(key).readOnly;
    }

    public synchronized boolean isRemovableAttribute(String key) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            DoesNotExist, Timeout, NoSuccess {
        return getInfo(key).removable;
    }

    public synchronized boolean isVectorAttribute(String key) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            DoesNotExist, Timeout, NoSuccess {
        return getInfo(key).vector;
    }

    public synchronized boolean isWritableAttribute(String key) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            DoesNotExist, Timeout, NoSuccess {
        return ! getInfo(key).readOnly;
    }

    public synchronized String[] listAttributes() throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            Timeout, NoSuccess {
        HashSet<String> keys = new HashSet<String>();
        for (String key : attributes.keySet()) {
            try {
                getInfo(key); // will throw exception if not implemented.
                keys.add(key);
            } catch(Throwable e) {
                // ignored
            }
        }
        
        return keys.toArray(new String[keys.size()]);
    }

    public synchronized void removeAttribute(String key) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            DoesNotExist, Timeout, NoSuccess {
        AttributeInfo info = getInfo(key);
        if (! info.removable) {
            throw new PermissionDenied("Attribute " + key + " is not removable");
        }
        info.hasValue = false;
        info.value = null;
        info.vectorValue = null;
    }
    
    // Set method without checks for readOnly. We must be able to set the
    // attribute, somehow.
    protected synchronized void setValue(String key, String value)
            throws DoesNotExist, NotImplemented, IncorrectState, BadParameter { 
        AttributeInfo info = getVectorInfo(key, false);
        if (info.type == AttributeType.TIME) {
            try {
                long v = Long.parseLong(value);
                value = dateFormatter.format(new Date(v));
            } catch(NumberFormatException e) {
                // ignored. checkValueType will check the format.
            }
        }
        checkValueType(info.type, value);
        info.hasValue = true;
        info.value = value;
    }
 
    // Set method without checks for readOnly. We must be able to set the
    // attribute, somehow.   
    protected synchronized void setVectorValue(String key, String[] values)
            throws DoesNotExist, NotImplemented, IncorrectState, BadParameter {
        AttributeInfo info = getVectorInfo(key, true);
        
        values = values.clone();

        for (int i = 0; i < values.length; i++) {
            if (info.type == AttributeType.TIME) {
                try {
                    long v = Long.parseLong(values[i]);
                    values[i] = dateFormatter.format(new Date(v));
                } catch(NumberFormatException e) {
                    // ignored. checkValueType will check the format.
                }
            }
            checkValueType(info.type, values[i]);
        }
        info.hasValue = true;
        info.vectorValue = values;
    }
    
    private void checkValueType(AttributeType type, String value) throws BadParameter {
        if (value == null) {
            throw new BadParameter("Attribute value set to null");
        }
        switch(type) {
        case STRING:
            break;
        case ENUM:
            // TODO: what to do here? Do we know the possible values?
            break;
        case INT:
            try {
                Long.parseLong(value);  
            } catch(NumberFormatException e) {
                throw new BadParameter("Int-typed attribute set to non-integer " + value);
            }
            break;
        case FLOAT:
            try {
                Double.parseDouble(value);
            } catch(NumberFormatException e) {
                throw new BadParameter("Float-typed attribute set to non-float " + value);
            }
            break;
        case BOOL:
            if (! value.equals("True") && ! value.equals("False")) {
                throw new BadParameter("Bool-typed attribute set to non-bool " + value);
            }
            break;
        case TIME:
            ParsePosition p = new ParsePosition(0);
            dateFormatter.parse(value, p);
            if (p.getErrorIndex() >= 0 || p.getIndex() < value.length()) {
                throw new BadParameter("Time-values attribute set to non-time " + value);
            }
            break;
        }
    }

    public synchronized void setAttribute(String key, String value) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            IncorrectState, BadParameter, DoesNotExist, Timeout, NoSuccess {
        AttributeInfo info = getVectorInfo(key, false);
        if (info.readOnly) {
            throw new PermissionDenied("Attribute " + key + " is readOnly");
        }
        if (info.type == AttributeType.TIME) {
            try {
                long v = Long.parseLong(value);
                value = dateFormatter.format(new Date(v));
            } catch(NumberFormatException e) {
                // ignored. Try and parse value here?
            }
        }
        checkValueType(info.type, value);
        info.hasValue = true;
        info.value = value;
    }

    public synchronized void setVectorAttribute(String key, String[] values)
            throws NotImplemented, AuthenticationFailed, AuthorizationFailed,
            PermissionDenied, IncorrectState, BadParameter, DoesNotExist,
            Timeout, NoSuccess {
        AttributeInfo info = getVectorInfo(key, true);

        if (info.readOnly) {
            throw new PermissionDenied("Attribute " + key + " is readOnly");
        }
        
        values = values.clone();
        for (int i = 0; i < values.length; i++) {
            if (info.type == AttributeType.TIME) {
                try {
                    long v = Long.parseLong(values[i]);
                    values[i] = dateFormatter.format(new Date(v));
                } catch(NumberFormatException e) {
                    // ignored. checkValueType will check the format.
                }
            }
            checkValueType(info.type, values[i]);
        }
        info.hasValue = true;
        info.vectorValue = values;
    }  
}
