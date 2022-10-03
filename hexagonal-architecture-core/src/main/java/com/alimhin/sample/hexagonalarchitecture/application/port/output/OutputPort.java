package com.alimhin.sample.hexagonalarchitecture.application.port.output;

import java.util.List;
import java.util.Optional;

/**
 * The interface Output com.alimhin.sample.hexagonalarchitecture.application.port.
 *
 * @param <T> the type parameter
 */
public interface OutputPort<T> {

    /**
     * Save t.
     *
     * @param object the object
     * @return the t
     */
    T save(T object);

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    Optional<T> getById(Long id);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<T> findAll();
}
