package com.wurq.dex.mobilerecovery.hearttouch.common.util.collection;

/**
 * Created by ht-template
 **/
public interface IFilter<T> {
    boolean onFilter(T item);
}
