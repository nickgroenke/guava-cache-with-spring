package com.nickgroenke;

import com.google.common.cache.Cache;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 *
 * I had some trouble coming up with a good demo use case. This is pretty contrived!
 *
 * @author groenken
 */
public class DemoCallerWithCache {

    private final CapsService capsService;
    private final Cache<String,String> cache;

    /*
     * Constructor injection is used so that the capsService and cache
     * instance variables can be final.
     */
    public DemoCallerWithCache(final CapsService capsService, final Cache cache) {
        this.capsService = capsService;
        this.cache = cache;
    }

    public List<String> capitalizeStrings(final List<String> inputList) {

        final ArrayList<String> ret = new ArrayList<String>();

        for(final String s : inputList) {
            try {
                ret.add(capitalizeString(s));
            } catch (ExecutionException e) {
                /* Just catching the exception to keep the demo simple. */
            }
        }

        return ImmutableList.copyOf(ret);
    }

    /*
     * This method uses Guava Cache's read through caching functionality. The cache is
     * checked for the desired key, and a Callable is used to retrieve and store the
     * value otherwise.
     *
     */
    private String capitalizeString(final String input) throws ExecutionException {
        return cache.get(input,new Callable<String>() {
            @Override
            public String call() {
                return capsService.toUpperCase(input);
            }
        });
    }

}
