package com.cnkaptan.repositorypattern.data;

import java.util.Iterator;
import java.util.List;

/**
 * Created by cihankaptan on 18/07/16.
 */
public interface Repostiory<T> {
    void add(T item);

    void add(Iterator<T> items);

    void update(T item);

    void remove(T item);

    void remove(Specification specification);

    List<T> query(Specification specification);
}
