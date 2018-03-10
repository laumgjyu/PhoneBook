package com.lmy.core.cache;

import com.lmy.core.hash.HashMap;
import com.lmy.core.hash.HashTable;
import com.lmy.core.hash.Node;
import com.lmy.core.util.PinyinUtil;
import com.lmy.model.User;

import java.util.List;

/**
 * Created by lmy on 2018/3/9.
 */
public class MemoryCache {

    public static final HashMap<String, User> nameCacheMap = new HashMap<>();
    public static final HashMap<String, User> phoneNumberCacheMap = new HashMap<>();

    public static final HashTable<String, User> nameCacheTable = new HashTable<>();
    public static final HashTable<String, User> phoneNumberCacheTable = new HashTable<>();

    public static void putByName(User user) {
        nameCacheMap.put(PinyinUtil.getPingYin(user.getName()), user);
        nameCacheTable.put(PinyinUtil.getPingYin(user.getName()), user);
    }

    public static void putByPhoneNumber(User user) {
        phoneNumberCacheMap.put(user.getPhoneNumber(), user);
        phoneNumberCacheTable.put(user.getPhoneNumber(), user);
    }

    public static Node<String, User> getInMapByName(String name) {
        return nameCacheMap.getNode(PinyinUtil.getPingYin(name));
    }

    public static Node<String, User> getInTableByName(String name) {
        return nameCacheTable.getNode(PinyinUtil.getPingYin(name));
    }

    public static Node<String, User> getInMapByPhoneNumber(String phoneNumber) {
        return phoneNumberCacheMap.getNode(phoneNumber);
    }

    public static Node<String, User> getInTableByPhoneNumber(String phoneNumber) {
        return phoneNumberCacheTable.getNode(phoneNumber);
    }

    public static boolean containsPhoneNumberKeyInMap(User user) {
        return phoneNumberCacheMap.containsKey(user.getPhoneNumber());
    }

    public static boolean containsPhoneNumberKeyInTable(User user) {
        return phoneNumberCacheTable.containsKey(user.getPhoneNumber());
    }

    public static boolean containsNameKeyInMap(User user) {
        return nameCacheMap.containsKey(PinyinUtil.getPingYin(user.getName()));
    }

    public static boolean containsNameKeyInTable(User user) {
        return nameCacheTable.containsKey(PinyinUtil.getPingYin(user.getName()));
    }

    public static void removeByNameInMap(String name) {
        nameCacheMap.remove(PinyinUtil.getPingYin(name));
    }

    public static void removeByNameInTable(String name) {
        nameCacheTable.remove(PinyinUtil.getPingYin(name));
    }

    public static void removeByPhoneNumberInMap(String phoneNumber) {
        phoneNumberCacheMap.remove(phoneNumber);
    }

    public static void removeByPhoneNumberInTable(String phoneNumber) {
        phoneNumberCacheTable.remove(phoneNumber);
    }

    public static List<User> getCachedEntities() {
        return nameCacheMap.getValues();
    }
}
