package com.wurq.dex.mobilerecovery.hearttouch.common.util.collection;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ht-template
 **/
// TODO google guava ???
public class CollectionsUtil {
    public static <T> T firstItem(Collection<T> collection) {
        if (collection == null) return null;
        for (T item : collection) {
            return item;
        }
        return null;
    }

    public static <T> T lastItem(List<T> collection) {
        if (collection == null || collection.isEmpty()) return null;

        return collection.get(collection.size() - 1);
    }

    public static <T> int firstIndexOf(T object, T[] collection) {
        int result = 0;
        for (T item : collection) {
            if (object.equals(item)) {
                return result;
            }
            result++;
        }

        return -1;
    }

    public static <T> int firstIndexOf(T object, Collection<T> collection) {
        int result = 0;
        for (T item : collection) {
            if (object.equals(item)) {
                return result;
            }
            result++;
        }

        return -1;
    }

    public static <T> int firstIndexOf(T object, Collection<T> collection, Comparator<T> comparator) {
        int result = 0;
        for (T item : collection) {
            if (comparator.compare(object, item) == 0) {
                return result;
            }
            result++;
        }

        return -1;
    }

    public static <T> int lastIndexOf(T object, Collection<T> collection) {
        int result = -1;
        int index = 0;
        for (T item : collection) {
            if (object.equals(item)) {
                result = index;
            }
            index++;
        }

        return result;
    }

    public static <T> int lastIndexOf(T object, Collection<T> collection,
                                      Comparator<T> comparator) {
        int result = -1;
        int index = 0;
        for (T item : collection) {
            if (comparator.compare(object, item) == 0) {
                result = index;
            }
            index++;
        }

        return result;
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return (collection == null || collection.size() == 0);
    }

    public static <T> List<T> newList(T[] array) {
        List<T> result = new ArrayList<>();
        if (array != null) {
            for (T item : array) {
                result.add(item);
            }
        }
        return result;
    }

    public static <T> ArrayList<T> newArrayList(Collection<T> collection) {
        ArrayList<T> result = new ArrayList<>();
        for (T item : collection) {
            result.add(item);
        }
        return result;
    }

    public static <T> ArrayList<T> newArrayList(T... items) {
        ArrayList<T> result = new ArrayList<>();
        for (T item : items) {
            result.add(item);
        }
        return result;
    }

    public static <T> String toString(Collection<T> collection) {
        return toString(collection, null);
    }

    public static <T> String toString(Collection<T> collection,
                                      @Nullable String separater) {
        StringBuilder result = new StringBuilder();
        boolean isEmptySeparator = TextUtils.isEmpty(separater);
        for (T item : collection) {
            result.append(item.toString());
            if (!isEmptySeparator) {
                result.append(separater);
            }
        }

        if (!isEmptySeparator) {
            result = result.delete(result.length() - separater.length(), result.length());
        }

        return result.toString();
    }

    public static <T> Collection<T> filter(@NonNull Collection<T> collection,
                                           @NonNull IFilter<T> filter) {
        List<T> result = new ArrayList<>();
        if (collection != null) {
            for (T item : collection) {
                if (filter.onFilter(item)) {
                    result.add(item);
                }
            }
        }

        return result;
    }
}

