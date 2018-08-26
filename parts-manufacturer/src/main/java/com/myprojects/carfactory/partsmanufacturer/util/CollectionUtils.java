package com.myprojects.carfactory.partsmanufacturer.util;

import com.google.common.base.Preconditions;
import lombok.experimental.UtilityClass;

import java.util.Collection;

@UtilityClass
public class CollectionUtils {

    public static <T> T getRandom(Collection<T> collection) {
        Preconditions.checkNotNull(collection, "Collection is null");
        Preconditions.checkArgument(!collection.isEmpty(), "Collection is empty");
        int num = (int) (Math.random() * collection.size());
        for (T t : collection) {
            if (--num < 0) {
                return t;
            }
        }
        throw new AssertionError();
    }
}
