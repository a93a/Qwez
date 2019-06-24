package com.example.qwez.bus;

import android.annotation.SuppressLint;
import android.util.SparseArray;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import java.lang.annotation.Retention;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Custom Bus based on RxJava, specifically it's PublishSubject class, which allows for new subscribers
 * to receive all subsequent emitted events. RxBus allows for communication between application components using
 * Subscribe and Publish on event types.
 *
 * All new subscribers must subscribe (by calling {@link #subscribe(int, Object, Consumer)}) to desired {@code Subject} event type in onStart()(Both Activity and Fragment).
 *
 * All subscribers must call {@link #unregister(Object)} in onStop() (Both Activity and Fragment)
 *
 * To add new event types available to be subscribed/published to, First, add a Constant class variable with a
 * unique int value, Second, add said Constant to {@code Subject} annotation.
 *
 * To subscribe, call {@link #subscribe(int, Object, Consumer)}) where {@Code int} is event type to subscribe to,
 * {@Code Object} is the LifeCycle owner, {@code Consumer} is the action to be implemented on received event.
 *
 * !IMPORTANT! ---> FURTHER READING/FIXING SEE ---> https://github.com/JakeWharton/RxRelay/issues/7 where Jack Wharton discusses
 * on why we should diminish subjects as application becoming more increasingly reactive and use streams and
 * exposing Observables instead (Jake argues he is yet to see a use of subjects that is not anti-pattern in
 * reactive programming).
 */
public final class RxBus {

    //Singleton variable for RxBus
    private static RxBus INSTANCE;

    //SparseArray more optimal when keys are primitives (Compared to hashmap (SparseArray is anAndroid specific class))
    //Map of event types and events. (<@IntDef int subject,PublishSubject>)
    private static SparseArray<PublishSubject<Object>> subjectMap = new SparseArray<>();

    //map of all Subscribers and their subscribtions (<LifeCycleOwner, Subscriptions>)
    private static Map<Object, CompositeDisposable> subscriptionsMap = new ConcurrentHashMap<>();

    //event type constants
    public static final int SHOW_LOGIN_FRAGMENT = 0;
    public static final int SHOW_SIGN_UP_FRAGMENT = 1;
    public static final int SHOW_QUESTION_ACTIVITY = 2;
    public static final int TRY_LOG_IN = 3;
    public static final int TRY_SIGN_UP = 4;
    public static final int TRY_LOG_OUT = 5;
    public static final int DELETE_GAME = 6;
    public static final int GAME_TOUCHED = 7;
    public static final int TRY_CHANGE_PASSWORD = 8;
    public static final int TRY_CHANGE_NICK = 9;
    public static final int LOGIN_IS_ATTACHED = 10;
    public static final int TRY_CHANGE_PHOTO = 11;
    public static final int TRY_DELETE_ACCOUNT = 12;
    public static final int START_OR_END_QUIZ = 13;
    public static final int SHOW_NEXT_QUESTION = 14;

    //event type annotation
    @Retention(SOURCE)
    @IntDef({SHOW_LOGIN_FRAGMENT,
            SHOW_SIGN_UP_FRAGMENT,
            SHOW_QUESTION_ACTIVITY,
            TRY_LOG_IN,
            TRY_SIGN_UP,
            TRY_LOG_OUT,
            DELETE_GAME,
            GAME_TOUCHED,
            TRY_CHANGE_PASSWORD,
            TRY_CHANGE_NICK,
            LOGIN_IS_ATTACHED,
            TRY_CHANGE_PHOTO,
            TRY_DELETE_ACCOUNT,
            START_OR_END_QUIZ,
            SHOW_NEXT_QUESTION})
    @interface Subject {
    }

    private RxBus() {
        //private constructor
    }

    //atomic getter of singleton RxBus variable
    public static RxBus get() {
        synchronized (RxBus.class){
            if (INSTANCE == null) {
                INSTANCE = new RxBus();
            }
            return INSTANCE;
        }
    }

    /**
     * Get the PublishSubject object associated with the @IntDef Subject event type
     * @param subjectCode {@code Subject} event type
     * @return PublishSubject associated with Subject
     */
    @SuppressLint("CheckResult")
    @NonNull
    private static PublishSubject<Object> getSubject(@Subject int subjectCode) {
        //get the subject for desired subjectCode
        PublishSubject<Object> subject = subjectMap.get(subjectCode);

        //incase subject does not exist yet, create it
        if (subject == null) {
            subject = PublishSubject.create();
            //Subscribe on Androids Mainthread for Publish/Subscribe operations. Using mainthread for
            // subscribing&publishing is OK for this very specific Bus and this very specific application
            // with its limited number of events. It won't be perfomance heavy and won't have much effect
            // on the applications performace.
            subject.subscribeOn(AndroidSchedulers.mainThread());
            subjectMap.put(subjectCode, subject);   //add to RxBus's subject map
        }
        return subject;
    }

    /**
     * Get the CompositeDisposable(Collection of a LifeCycleOwners all associated Disposable subscriptions)
     * @param object LifeCycle owner
     * @return CompositeDisposable of LifeCycler owner
     */
    private static CompositeDisposable getCompositeDisposable(@NonNull Object object) {
        //Get the lifecycle owners all subscriptions
        CompositeDisposable compositeDisposable = subscriptionsMap.get(object);

        //incase no subscriptions exists for this lifecycle owner yet, create one
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
            subscriptionsMap.put(object, compositeDisposable);  //add to RxBus's subscription map
        }

        return compositeDisposable;
    }

    /**
     * Subscribe to a {@code @IntDef Subject} event type. Subscriber/Lifecycle owner must call this
     * method from its onStart() method. Subscriber will receive all subsequent published events for
     * this event type.
     * @param subject event type to subscribe to
     * @param lifecycle Subscriber
     * @param action Action to take on event received
     */
    public static void subscribe(@Subject int subject, @NonNull Object lifecycle, @NonNull Consumer<Object> action) {
        Disposable disposable = getSubject(subject).subscribe(action);
        getCompositeDisposable(lifecycle).add(disposable);
    }

    /**
     * Unregisters Lifecycle owner from RxBus. This method will remove all subscriptions for the
     * lifecycle owner, and lifecycle owner will receive no more events after this method is called.
     * Strongly recommended to call this method on all lifecycle owners onStop() method.
     * @param lifecycle owner to remove subscriptions for.
     */
    public static void unregister(@NonNull Object lifecycle) {
        CompositeDisposable compositeDisposable = subscriptionsMap.remove(lifecycle);
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    /**
     * Publish an event of {@code @IntDef Subject} event type. All subscribers of this event type will receive
     * this event.
     * @param subject event type
     * @param message event to publish
     */
    public static void publish(@Subject int subject, @NonNull Object message) {
        getSubject(subject).onNext(message);
    }

}
