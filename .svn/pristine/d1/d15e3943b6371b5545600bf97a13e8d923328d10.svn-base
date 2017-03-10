package com.youjuke.swissgearlibrary.rxbus;

import android.support.annotation.NonNull;
import com.youjuke.swissgearlibrary.utils.L;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 *
 * 这个是网上的原始版本。然后通过Event Bus 的方式，注解等封装RxBus
 *
 */

@Deprecated
public class RxBusOlder {
    private static final boolean DEBUG = true;

    private static RxBusOlder sInstance;

    private ConcurrentHashMap<Object, List<Subject>> mSubjectsMapper = new ConcurrentHashMap<>();

    public static synchronized RxBusOlder instance() {
        if (sInstance == null) {
            sInstance = new RxBusOlder();
        }
        return sInstance;
    }

    private RxBusOlder() {
    }

    public <T> Observable<T> register(@NonNull Object tag, @NonNull Class<T> clazz) {
        List<Subject> subjectList = mSubjectsMapper.get(tag);
        if (subjectList == null) {
            subjectList = new ArrayList<>();
            mSubjectsMapper.put(tag, subjectList);
        }

        Subject<T, T> subject = new SerializedSubject<>(PublishSubject.<T>create());
        subjectList.add(subject);
        if (DEBUG) {
            L.d("[register] mSubjectsMapper: " + mSubjectsMapper);
        }
        return subject;
    }

    public void unregister(@NonNull Object tag, @NonNull Observable observable) {
        List<Subject> subjects = mSubjectsMapper.get(tag);
        if (subjects != null) {
            subjects.remove(observable);
            if (subjects.isEmpty()) {
                mSubjectsMapper.remove(tag);
            }
            if (DEBUG) {
                L.d("[unregister] mSubjectsMapper: " + mSubjectsMapper);
            }
        }
    }

    public void post(@NonNull Object tag, @NonNull Object content) {
        List<Subject> subjects = mSubjectsMapper.get(tag);
        if (subjects != null && !subjects.isEmpty()) {
            for (Subject subject : subjects) {
                subject.onNext(content);
            }
        }
        if (DEBUG) {
            L.d("[send] mSubjectsMapper: " + mSubjectsMapper);
        }
    }

}
