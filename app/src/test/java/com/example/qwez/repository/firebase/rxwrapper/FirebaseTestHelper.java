package com.example.qwez.repository.firebase.rxwrapper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.when;

class FirebaseTestHelper {

    static final String EMAIL = "test@gmail.com";
    static final String PASSWORD = "test123";
    static final String NEW_PASSWORD = "newtest123";
    static final Exception EXCEPTION = new Exception();

    static ArgumentCaptor<OnCompleteListener> testOnCompleteListener = ArgumentCaptor.forClass(OnCompleteListener.class);
    static ArgumentCaptor<OnSuccessListener> testOnSuccessListener = ArgumentCaptor.forClass(OnSuccessListener.class);
    static ArgumentCaptor<OnFailureListener> testOnFailureListener = ArgumentCaptor.forClass(OnFailureListener.class);

    static <T> void setupTask(Task<T> task) {
        when(task.addOnCompleteListener(testOnCompleteListener.capture())).thenReturn(task);
        when(task.addOnSuccessListener(testOnSuccessListener.capture())).thenReturn(task);
        when(task.addOnFailureListener(testOnFailureListener.capture())).thenReturn(task);
    }

}
