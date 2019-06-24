package com.example.qwez.ui.login;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import com.example.qwez.entity.ErrorCarrier;
import com.example.qwez.interactor.LoginUserInteractor;
import com.example.qwez.interactor.SignupInteractor;
import com.example.qwez.router.StartRouter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Completable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginViewModelTest {

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();

    @Mock
    LifecycleOwner lifecycleOwner;
    @Mock
    StartRouter startRouter;
    @Mock
    Context context;
    @Mock
    LoginUserInteractor loginUserInteractor;
    @Mock
    SignupInteractor signupInteractor;

    @InjectMocks
    LoginViewModel viewModel;

    private Observer<Boolean> loginObserver;
    private Observer<Boolean> signupObserver;
    private Observer<Boolean> progressObserver;
    private Observer<ErrorCarrier> errorObserver;
    private LifecycleRegistry lifecycleRegistry;
    private Throwable throwable;

    private static final String EMAIL = "test@test.test";
    private static final String PASSWORD = "password123";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        lifecycleRegistry = new LifecycleRegistry(lifecycleOwner);
        when(lifecycleOwner.getLifecycle()).thenReturn(lifecycleRegistry);
        lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);

        loginObserver = (Observer<Boolean>) mock(Observer.class);
        signupObserver = (Observer<Boolean>) mock(Observer.class);
        progressObserver = (Observer<Boolean>) mock(Observer.class);
        errorObserver = (Observer<ErrorCarrier>)mock(Observer.class);

        throwable = new Throwable();
    }

    @Test
    public void logInUser() {
        when(loginUserInteractor.login(EMAIL, PASSWORD)).thenReturn(Completable.complete());

        viewModel.login().observe(lifecycleOwner, loginObserver);
        viewModel.progress().observe(lifecycleOwner, progressObserver);
        viewModel.logInUser(EMAIL, PASSWORD);

        verify(loginUserInteractor).login(EMAIL, PASSWORD);
        verify(loginObserver).onChanged(true);
        verify(progressObserver,times(2)).onChanged(anyBoolean());
    }

    @Test
    public void loginUserError() {
        when(loginUserInteractor.login(EMAIL, PASSWORD)).thenReturn(Completable.error(throwable));

        viewModel.login().observe(lifecycleOwner, loginObserver);
        viewModel.progress().observe(lifecycleOwner, progressObserver);
        viewModel.error().observe(lifecycleOwner, errorObserver);
        viewModel.logInUser(EMAIL, PASSWORD);

        verify(loginUserInteractor).login(EMAIL, PASSWORD);
        verify(loginObserver, never()).onChanged(anyBoolean());
        verify(progressObserver,times(2)).onChanged(anyBoolean());
        verify(errorObserver).onChanged(any(ErrorCarrier.class));

        InOrder inOrder = Mockito.inOrder(progressObserver);
        inOrder.verify(progressObserver).onChanged(true);
        inOrder.verify(progressObserver).onChanged(false);
    }

    @Test
    public void signupUser() {
        when(signupInteractor.signupUser(EMAIL, PASSWORD)).thenReturn(Completable.complete());

        viewModel.singup().observe(lifecycleOwner, signupObserver);
        viewModel.progress().observe(lifecycleOwner, progressObserver);
        viewModel.signupUser(EMAIL, PASSWORD);

        verify(signupInteractor).signupUser(EMAIL, PASSWORD);
        verify(signupObserver).onChanged(true);
        verify(progressObserver,times(2)).onChanged(anyBoolean());
    }

    @Test
    public void signupUserError() {
        when(signupInteractor.signupUser(EMAIL, PASSWORD)).thenReturn(Completable.error(throwable));

        viewModel.singup().observe(lifecycleOwner, signupObserver);
        viewModel.progress().observe(lifecycleOwner, progressObserver);
        viewModel.error().observe(lifecycleOwner, errorObserver);
        viewModel.signupUser(EMAIL, PASSWORD);

        verify(signupInteractor).signupUser(EMAIL, PASSWORD);
        verify(signupObserver, never()).onChanged(anyBoolean());
        verify(progressObserver,times(2)).onChanged(anyBoolean());
        verify(errorObserver).onChanged(any(ErrorCarrier.class));

        InOrder inOrder = Mockito.inOrder(progressObserver);
        inOrder.verify(progressObserver).onChanged(true);
        inOrder.verify(progressObserver).onChanged(false);
    }

    @Test
    public void openStart() {
        viewModel.openStart(context, true);
        verify(startRouter).open(context, true);
    }

}