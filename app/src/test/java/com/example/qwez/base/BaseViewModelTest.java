package com.example.qwez.base;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import com.example.qwez.entity.ErrorCarrier;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.disposables.Disposable;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BaseViewModelTest {

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();

    @InjectMocks
    BaseViewModel viewModel;

    @Mock
    LifecycleOwner lifecycleOwner;
    @Mock
    Disposable disposable;

    private LifecycleRegistry lifecycleRegistry;
    private Throwable throwable;
    private static final String RANDOM_MESSAGE = "Some random message, don't care about me";
    private Observer<ErrorCarrier> errorCarrierObserver;
    private Observer<Boolean> progressObserver;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        lifecycleRegistry = new LifecycleRegistry(lifecycleOwner);
        when(lifecycleOwner.getLifecycle()).thenReturn(lifecycleRegistry);

        errorCarrierObserver = (Observer<ErrorCarrier>)mock(Observer.class);
        progressObserver = (Observer<Boolean>)mock(Observer.class);
        throwable = new Throwable(RANDOM_MESSAGE);
    }

    @Test
    public void testLifecycle(){
        assertThat(viewModel.progress().hasObservers(), is(false));

        viewModel.progress().observe(lifecycleOwner, progressObserver);

        assertThat(viewModel.progress().hasObservers(), is(true));

        lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
        assertThat(viewModel.progress().hasActiveObservers(),is(true));

        lifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED);
        assertThat(viewModel.progress().hasActiveObservers(), is(false));
    }

    @Test
    public void error() {
        lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);

        viewModel.error().observe(lifecycleOwner, errorCarrierObserver);
        viewModel.onError(throwable);

        verify(errorCarrierObserver).onChanged(any(ErrorCarrier.class));
    }

    @Test
    public void errorAndNullProgress() {
        lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);

        viewModel.error().observe(lifecycleOwner, errorCarrierObserver);
        viewModel.progress().observe(lifecycleOwner, progressObserver);
        viewModel.onError(throwable);

        verify(errorCarrierObserver).onChanged(any(ErrorCarrier.class));
        verify(progressObserver,never()).onChanged(anyBoolean());

    }

    @Test
    public void errorAndStopProgress() {
        lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);

        viewModel.error().observe(lifecycleOwner, errorCarrierObserver);
        viewModel.progress().observe(lifecycleOwner, progressObserver);

        viewModel.progress.setValue(true);
        verify(progressObserver).onChanged(true);

        viewModel.onError(throwable);

        verify(progressObserver).onChanged(false);
        verify(errorCarrierObserver).onChanged(any(ErrorCarrier.class));

    }

    @Test
    public void progress() {
        lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);

        viewModel.progress().observe(lifecycleOwner, progressObserver);
        viewModel.progress.setValue(true);

        verify(progressObserver).onChanged(true);

    }

    @Test
    public void cancel(){
        viewModel.cancel();
        verify(disposable).isDisposed();
        verify(disposable).dispose();
    }
}