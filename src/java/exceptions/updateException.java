/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author 2dam
 */
public class updateException extends Exception {

    /**
     * Creates a new instance of <code>updateException</code> without detail
     * message.
     */
    public updateException() {
    }

    /**
     * Constructs an instance of <code>updateException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public updateException(String msg) {
        super(msg);
    }
}
