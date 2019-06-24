package com.example.qwez.ui.splash;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import com.example.qwez.RxResources;
import com.example.qwez.interactor.GetUserInteractor;
import com.example.qwez.router.LoginRouter;
import com.example.qwez.router.StartRouter;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SplashViewModelTest {

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();

    @ClassRule
    public static RxResources rxres = new RxResources();


    @InjectMocks
    SplashViewModel viewModel;

    @Mock
    GetUserInteractor getUserInteractor;
    @Mock
    FirebaseUser firebaseUser;
    @Mock
    LifecycleOwner lifecycleOwner;
    @Mock
    LoginRouter loginRouter;
    @Mock
    StartRouter startRouter;
    @Mock
    Context context;

    private LifecycleRegistry lifecycleRegistry;
    private Observer<FirebaseUser> firebaseUserObserver;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        lifecycleRegistry = new LifecycleRegistry(lifecycleOwner);
        when(lifecycleOwner.getLifecycle()).thenReturn(lifecycleRegistry);
        lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);

        firebaseUserObserver = (Observer<FirebaseUser>)mock(Observer.class);
    }

    @Test
    public void getUser() {
        when(getUserInteractor.getUser()).thenReturn(Observable.just(firebaseUser));

        viewModel.user().observe(lifecycleOwner, firebaseUserObserver);

        viewModel.getUser();

        verify(getUserInteractor).getUser();
        verify(firebaseUserObserver).onChanged(firebaseUser);
    }

    @Test
    public void getUserError() {
        when(getUserInteractor.getUser()).thenReturn(Observable.error(new NullPointerException()));

        viewModel.user().observe(lifecycleOwner, firebaseUserObserver);

        viewModel.getUser();

        verify(getUserInteractor).getUser();
        verify(firebaseUserObserver).onChanged(null);
    }

    @Test
    public void openStart() {
        viewModel.openStart(context);
        verify(startRouter).open(context, true);
    }

    @Test
    public void openLogin() {
        viewModel.openLogin(context);
        verify(loginRouter).open(context, true);
    }

}