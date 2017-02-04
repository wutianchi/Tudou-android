package com.gunlei.app.ui.eventbus;

import de.greenrobot.event.EventBus;

/**
 * 全局事件的实现。
 * Created by beansoft on 15/5/26.
 */
public class GlobalEvent {
    public synchronized boolean isRegistered(Object subscriber) {
        return EventBus.getDefault().isRegistered(subscriber);
    }

    /**
     * 注册监听器
     * @param subscriber Object
     */
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    /**
     * 反注册事件监听器
     * @param subscriber Object
     */
    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    /**
     * 分发事件
     * @param event Object
     */
    public static void post(Object event) {
        EventBus.getDefault().post(event);
    }

    /**
     * 接收固定事件和普通事件，调用此方法时，不要调用 #register(Object)，否则会退出。
     * @param subscriber
     */
    public static void registerSticky(Object subscriber) {
        EventBus.getDefault().registerSticky(subscriber);
    }

    /**
     * 分发置顶事件
     * @param event Object
     */
    public static void postSticky(Object event) {
        EventBus.getDefault().postSticky(event);
    }

    /**
     * 取消分发事件
     * @param subscriber Object
     */
    public static void cancelEventDelivery(Object subscriber) {
        EventBus.getDefault().cancelEventDelivery(subscriber);
    }


    public static <T> T removeStickyEvent(Class<T> eventType) {
        return EventBus.getDefault().removeStickyEvent(eventType);
    }

    public static void removeAllStickyEvents() {
        EventBus.getDefault().removeAllStickyEvents();
    }

    public static boolean removeStickyEvent(Object event) {
        return EventBus.getDefault().removeStickyEvent(event);
    }

}