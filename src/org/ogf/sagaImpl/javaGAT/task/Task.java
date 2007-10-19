package org.ogf.sagaImpl.javaGAT.task;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.ogf.saga.ObjectType;
import org.ogf.saga.error.AlreadyExists;
import org.ogf.saga.error.AuthenticationFailed;
import org.ogf.saga.error.AuthorizationFailed;
import org.ogf.saga.error.BadParameter;
import org.ogf.saga.error.DoesNotExist;
import org.ogf.saga.error.IncorrectState;
import org.ogf.saga.error.IncorrectURL;
import org.ogf.saga.error.NoSuccess;
import org.ogf.saga.error.NotImplemented;
import org.ogf.saga.error.PermissionDenied;
import org.ogf.saga.error.Timeout;
import org.ogf.saga.monitoring.Callback;
import org.ogf.saga.monitoring.Metric;
import org.ogf.saga.task.State;
import org.ogf.saga.task.TaskMode;
import org.ogf.sagaImpl.javaGAT.SagaObject;

// The SAGA specs warn against using threads. However, the javaGAT does not
// have task support, so there really is no other option.

public class Task<E> extends SagaObject implements org.ogf.saga.task.Task<E>, Future<E> {

    private State state = State.NEW;
    private final SagaObject object;
    private Throwable exception = null;

    public Task(SagaObject object, TaskMode mode) {
        super(object.obtainSession());
        this.object = object;
        switch(mode) {
        case ASYNC:
            internalRun();
            break;
        case SYNC:
            internalRun();
            internalWaitTask(-1.0F);
            break;
        case TASK:
            break;
        }
        if (mode == TaskMode.ASYNC) {
            internalRun();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogf.saga.task.Task#cancel()
     */
    public void cancel() throws NotImplemented, IncorrectState, Timeout,
            NoSuccess {
        cancel(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogf.saga.task.Task#cancel(float)
     */
    public void cancel(float timeoutInSeconds) throws NotImplemented,
            IncorrectState, Timeout, NoSuccess {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogf.saga.task.Task#getObject()
     */
    public org.ogf.saga.SagaObject getObject() throws NotImplemented, Timeout,
            NoSuccess {
        return object;
    }

    /*
     * (non-Javadoc)
     * 
     * see org.ogf.saga.task.Task#getResult()
     */
    public E getResult() throws NotImplemented, IncorrectState, Timeout,
           NoSuccess {
        return null;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.ogf.saga.task.Task#getState()
     */
    public State getState() throws NotImplemented, Timeout, NoSuccess {
        return state;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogf.saga.task.Task#rethrow()
     */
    public synchronized void rethrow() throws NotImplemented, IncorrectURL,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            BadParameter, IncorrectState, AlreadyExists, DoesNotExist, Timeout,
            NoSuccess {
        if (state == State.FAILED) {
            // otherwise we should do nothing

            if (exception instanceof NotImplemented) {
                throw (NotImplemented) exception;
            }
            if (exception instanceof IncorrectURL) {
                throw (IncorrectURL) exception;
            }
            if (exception instanceof AuthenticationFailed) {
                throw (AuthenticationFailed) exception;
            }
            if (exception instanceof AuthorizationFailed) {
                throw (AuthorizationFailed) exception;
            }
            if (exception instanceof PermissionDenied) {
                throw (PermissionDenied) exception;
            }
            if (exception instanceof BadParameter) {
                throw (BadParameter) exception;
            }
            if (exception instanceof IncorrectState) {
                throw (IncorrectState) exception;
            }
            if (exception instanceof AlreadyExists) {
                throw (AlreadyExists) exception;
            }
            if (exception instanceof DoesNotExist) {
                throw (DoesNotExist) exception;
            }
            if (exception instanceof Timeout) {
                throw (Timeout) exception;
            }
            if (exception instanceof NoSuccess) {
                throw (NoSuccess) exception;
            }
            //We should not get here.
            throw new NoSuccess("Got unknown exception", exception);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogf.saga.task.Task#run()
     */
    public void run() throws NotImplemented, IncorrectState, Timeout, NoSuccess {
        if (state != State.NEW) {
            throw new IncorrectState("run() called in wrong state");
        }
        internalRun();
    }

    /**
     * Internal version, no exceptions from this one, we know that the state is
     * correct.
     */
    private void internalRun() {
        //TODO: implement this.
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogf.saga.task.Task#waitTask()
     */
    public void waitTask() throws NotImplemented, IncorrectState, Timeout,
            NoSuccess {
        waitTask(-1.0F);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogf.saga.task.Task#waitTask(float)
     */
    public synchronized boolean waitTask(float timeoutInSeconds) throws NotImplemented,
            IncorrectState, Timeout, NoSuccess {
        if (state == State.NEW) {
            throw new IncorrectState("waitTask called on new task");
        }
        return internalWaitTask(timeoutInSeconds);
    }
    
    /**
     * Internal version, we know that the state is correct.
     * @param timeoutInSeconds the timeout.
     * @return <code>true</code> if the task is finished.
     */
    private synchronized boolean internalWaitTask(float timeoutInSeconds) {
        switch(state) {
        case NEW:
            throw new Error("Internal error");
        case DONE:
        case CANCELED:
        case FAILED:
            return true;
        case SUSPENDED:
        case RUNNING:
            // TODO: implement!
        }
 
        return state == State.SUSPENDED || state == State.RUNNING;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogf.saga.SagaObject#getType()
     */
    public ObjectType getType() {
        return ObjectType.TASK;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogf.saga.monitoring.Monitorable#addCallback(java.lang.String,
     *      org.ogf.saga.monitoring.Callback)
     */
    public int addCallback(String name, Callback cb) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            DoesNotExist, Timeout, NoSuccess {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogf.saga.monitoring.Monitorable#getMetric(java.lang.String)
     */
    public Metric getMetric(String name) throws NotImplemented,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied,
            DoesNotExist, Timeout, NoSuccess {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogf.saga.monitoring.Monitorable#listMetrics()
     */
    public String[] listMetrics() throws NotImplemented, AuthenticationFailed,
            AuthorizationFailed, PermissionDenied, Timeout, NoSuccess {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogf.saga.monitoring.Monitorable#removeCallback(java.lang.String,
     *      int)
     */
    public void removeCallback(String name, int cookie) throws NotImplemented,
            DoesNotExist, BadParameter, Timeout, NoSuccess,
            AuthenticationFailed, AuthorizationFailed, PermissionDenied {
        // TODO Auto-generated method stub

    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        // TODO Auto-generated method stub
        return false;
    }

    public E get() throws InterruptedException, ExecutionException {
        // TODO Auto-generated method stub
        return null;
    }

    public E get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isCancelled() {
        return state == State.CANCELED;
    }

    public synchronized boolean isDone() {
        return state == State.DONE || state == State.FAILED
            || state == State.CANCELED;
    }

}
