# Qwez
[![Build Status](https://travis-ci.org/aliamid93/Qwez.svg?branch=master)](https://travis-ci.org/aliamid93/Qwez)

## Architecture:
- MVVM: Model-Viewmodel-Model
- Repository: One repository per data type e.g sharedpreferences repository, Firebase repository etc
- Interactor's (Aka Use-cases): Single responsibility interactor classes for interacting with repository/repositories, services, etc. This is where the applications "business logic" resides.
- App is highly extensible. It can easily be modified and added with further behaviour. To add new "UI package", one must:
    - Create Activity (And/or Fragment(s))
    - Create Activity Module (For dependency injection), add module to Dagger Application Component
    - Bind module to activity in "di" package
    - Create Viewmodel
    - Create Viewmodel factory
    - Provide all dependencies via Module, provide dependencies in parameters to factory+viewmodel
    
# Following libraries are using in this app:

## Design
- [Dagger2](https://google.github.io/dagger/)
- [RxJava](https://github.com/ReactiveX/RxJava)
- [RxAndroid](https://github.com/ReactiveX/RxAndroid)
- [Butterknife](http://jakewharton.github.io/butterknife/)
- [Glide](https://github.com/bumptech/glide)
## Data
- Firebase:
    - [Firestore](https://firebase.google.com/docs/firestore)
    - [Firebase Auth](https://firebase.google.com/docs/auth)
- [Room](https://developer.android.com/topic/libraries/architecture/room)
## UI
- [Material Design](https://material.io/develop/android/docs/getting-started/)
- [Material Dialogs](https://github.com/afollestad/material-dialogs)
- [Lottie](https://github.com/airbnb/lottie-android)
## Networking
- [Retrofit](https://square.github.io/retrofit/) (+[okhttp for interceptor](https://square.github.io/okhttp/3.x/logging-interceptor/okhttp3/logging/HttpLoggingInterceptor.Level.html))
## Testing
- [Mocktio](https://site.mockito.org/)
## Logging/Debugging
- [Leak Canary](https://github.com/square/leakcanary)
- [Timber](https://github.com/JakeWharton/timber)
- [Stetho](https://github.com/facebook/stetho)
## Others
- Standard libraries: AppCompat, JUnit, Espresso, CardView, RecyclerView, support libraries,Apache Commons Text...

# Wrappers
Application have some RxJava wrappers for different parts of the application, to ease reactive programming within the app:
