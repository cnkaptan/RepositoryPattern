package com.cnkaptan.repositorypattern.data;

/**
 * Created by cihankaptan on 18/07/16.
 */
public interface Mapper<From,To> {
    To map(From from);
}
