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
public class getCollectionException extends Exception {

    /**
     * Creates a new instance of <code>getCollectionException</code> without
     * detail message.
     */
    public getCollectionException() {
    }

    /**
     * Constructs an instance of <code>getCollectionException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public getCollectionException(String msg) {
        super(msg);
    }
}
