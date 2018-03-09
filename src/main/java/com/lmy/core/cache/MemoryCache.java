package com.lmy.core.cache;

import com.lmy.core.hash.HashMap;
import com.lmy.core.hash.Node;
import com.lmy.model.User;

import java.util.List;

/**
 * Created by lmy on 2018/3/9.
 */
public class MemoryCache {

    public static final HashMap<String, User> nameCacheMap = new HashMap<>();
    public static final HashMap<String, User> phoneNumberCacheMap = new HashMap<>();

    public static void putByName(User user) {
        nameCacheMap.put(user.getName(), user);
    }

    public static void putByPhoneNumber(User user) {
        phoneNumberCacheMap.put(user.getPhoneNumber(), user);
    }

    public static Node<String, User> getByName(String name) {
        return nameCacheMap.getNode(name);
    }

    public static Node<String, User> getByPhoneNumber(String phoneNumber) {
        return phoneNumberCacheMap.getNode(phoneNumber);
    }

    public static boolean containsPhoneNumberKey(User user) {
        return phoneNumberCacheMap.containsKey(user.getPhoneNumber());
    }

    public static boolean containsNameKey(User user) {
        return nameCacheMap.containsKey(user.getName());
    }

    public static void removeByName(String name) {
        nameCacheMap.remove(name);
    }

    public static void removeByPhoneNumber(String phoneNumber) {
        phoneNumberCacheMap.remove(phoneNumber);
    }

    public static List<User> getCachedEntities() {
        return nameCacheMap.getValues();
    }
}
