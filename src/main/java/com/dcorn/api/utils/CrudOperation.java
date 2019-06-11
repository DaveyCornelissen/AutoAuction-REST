package com.dcorn.api.utils;

/**
 * The interface ICrudOperations. Uses the CRUD annotation.
 * This interface can be extended by different objects when its given to the parameter.
 * @param <T>
 */
public interface CrudOperation<T> {
    /**
     * The create method is used to create an object
     * and return a boolean when it succeeds.
     * @param object
     * @return
     */
    T create(T object);
    /**
     * The read method is used to read a specific object with the given parameter.
     * @param id
     * @return
     */
    T read(Integer id);

    /**
     * The update method is used to update an object.
     * @param object
     * @return
     */
    void update(T object);

    /**
     * The delete method is used to delete an object
     * @param object
     */
    void delete(T object);
}

