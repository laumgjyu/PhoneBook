package com.lmy.core.cache;

import com.lmy.core.hash.HashMap;
import com.lmy.core.hash.Node;
import com.lmy.model.User;

/**
 * Created by lmy on 2018/3/9.
 */
public class MemoryCache {

    private static final HashMap<String, User> nameCacheMap = new HashMap<>();
    private static final HashMap<String, User> phoneNumberCacheMap = new HashMap<>();

    public void putByName(User user) {
        nameCacheMap.put(user.getName(), user);
    }

    public void putByPhoneNumber(User user) {
        phoneNumberCacheMap.put(user.getPhoneNumber(), user);
    }

    public Node<String, User> getByName(String name) {
        return nameCacheMap.getNode(name);
    }

    public Node<String, User> getByPhoneNumber(String phoneNumber) {
        return phoneNumberCacheMap.getNode(phoneNumber);
    }

    public void removeByName(String name) {
        nameCacheMap.remove(name);
    }

    public void removeByPhoneNumber(String phoneNumber) {
        phoneNumberCacheMap.remove(phoneNumber);
    }


}
