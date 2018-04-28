package com.gaoda.rxbus;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * @Created: Suqi
 * @Date: 2018/1/23
 * @Description:
 */

public class RxBus {

    private static volatile RxBus sInstance;

    private RxBus() {
    }

    public static RxBus getDefault() {
        if (sInstance == null) {
            synchronized (RxBus.class) {
                if (sInstance == null) {
                    sInstance = new RxBus();
                }
            }
        }
        return sInstance;
    }

    private final FlowableProcessor<Object> mBus = PublishProcessor.create().toSerialized();

    public void send(Object o) {
        mBus.onNext(o);
    }

    public Flowable<Object> toObservable() {
        return mBus;
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     *
     * @param eventType 事件类型
     * @param <T>
     * @return
     */
    public <T> Flowable<T> toObservable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }

    /**
     * 提供了一个新的事件,根据code进行分发
     *
     * @param code 事件code
     * @param o
     */
    public void post(int code, Object o) {
        mBus.onNext(new RxBusBaseMessage(code, o));

    }


    /**
     * 根据传递的code和 eventType 类型返回特定类型(eventType)的 被观察者
     * 对于注册了code为0，class为voidMessage的观察者，那么就接收不到code为0之外的voidMessage。
     *
     * @param code      事件code
     * @param eventType 事件类型
     * @param <T>
     * @return
     */
    public <T> Flowable<T> toObservable(final int code, final Class<T> eventType) {
        return mBus.ofType(RxBusBaseMessage.class)
                .filter(new Predicate<RxBusBaseMessage>() {
                    @Override
                    public boolean test(RxBusBaseMessage o) throws Exception {
                        return o.getCode() == code && eventType.isInstance(o.getObject());
                    }
                }).map(new Function<RxBusBaseMessage, Object>() {
                    @Override
                    public Object apply(RxBusBaseMessage rxBusBaseMessage) throws Exception {
                        return rxBusBaseMessage.getObject();
                    }
                }).cast(eventType);
    }

    /**
     * 判断是否有订阅者
     */
    public boolean hasObservers() {
        return mBus.hasSubscribers();
    }
}
