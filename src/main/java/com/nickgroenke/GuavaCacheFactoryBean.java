package com.nickgroenke;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.FactoryBean;

import java.util.concurrent.TimeUnit;

/**
 * @author groenken
 */
public class GuavaCacheFactoryBean<K,V> implements FactoryBean<Cache<K,V>> {

    /* Properties used by the builder: */
    private Long expireMillisecondsAfterWrite;
    private Long maximumSize;
    // etc...

    @Override
    public Cache<K,V> getObject() throws Exception {
        CacheBuilder<K,V> builder = (CacheBuilder<K, V>) CacheBuilder.newBuilder();
        if(expireMillisecondsAfterWrite != null) {
            builder.expireAfterWrite(expireMillisecondsAfterWrite, TimeUnit.SECONDS);
        }
        if(maximumSize != null) {
            builder.maximumSize(maximumSize);
        }
        return builder.build();
    }

    @Override
    public Class<Cache> getObjectType() {
        return Cache.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public void setExpireMillisecondsAfterWrite(Long expireMillisecondsAfterWrite) {
        this.expireMillisecondsAfterWrite = expireMillisecondsAfterWrite;
    }

    public void setMaximumSize(Long maximumSize) {
        this.maximumSize = maximumSize;
    }
}
