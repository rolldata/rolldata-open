package com.rolldata.core.common.exception;

/**
 * JPA异常
 *
 * @Title: SQLRuntimeException
 * @Description: JPA异常
 * @Company: www.wrenchdata.com
 * @author: shenshilong
 * @date: 2021-4-24
 * @version: V1.0
 */
public class JPAHandleException extends Exception {

    private static final long serialVersionUID = 7934075411045594262L;

    public JPAHandleException(String message) {
        super(message);
    }

    public JPAHandleException(Exception ex) {
        super(ex.getMessage());
    }

    public JPAHandleException(String message, Exception ex) {
        super(message + ":" + ex.getMessage());
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since  1.4
     */
    public JPAHandleException(String message, Throwable cause) {
        super(message, cause);
    }

    /** Constructs a new runtime exception with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).  This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since  1.4
     */
    public JPAHandleException(Throwable cause) {
        super(cause);
    }

    protected JPAHandleException(String message, Throwable cause,
                                 boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
