package com.example.qwez.ui.start;

import android.content.Context;
import android.net.Uri;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import com.example.qwez.entity.ErrorCarrier;
import com.example.qwez.interactor.DeleteGameInteractor;
import com.example.qwez.interactor.FetchQuestionsInteractor;
import com.example.qwez.interactor.GetAllGamesInteractor;
import com.example.qwez.interactor.GetUserInteractor;
import com.example.qwez.repository.local.entity.Game;
import com.example.qwez.router.HighscoreRouter;
import com.example.qwez.router.QuestionRouter;
import com.example.qwez.router.SettingsRouter;
import com.example.qwez.util.Category;
import com.example.qwez.util.Difficulty;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.modules.junit4.rule.PowerMockRule;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StartViewModelTest{

    @Rule
    public InstantTaskExecutorRule testRule = new InstantTaskExecutorRule();

    @InjectMocks
    StartViewModel viewModel;

    @Mock
    LifecycleOwner lifecycleOwner;
    @Mock
    FetchQuestionsInteractor getQuestionsInteractor;
    @Mock
    GetAllGamesInteractor getAllGamesInteractor;
    @Mock
    GetUserInteractor getUserInteractor;
    @Mock
    DeleteGameInteractor deleteGameInteractor;
    @Mock
    SettingsRouter settingsRouter;
    @Mock
    QuestionRouter questionRouter;
    @Mock
    HighscoreRouter highscoreRouter;
    @Mock
    Context context;
    @Mock
    FirebaseUser firebaseUser;
    @Mock
    Uri uri;
    @Mock
    Game game;
    @Mock
    Uri otherUri;

    private LifecycleRegistry lifecycleRegistry;
    private Observer<ErrorCarrier> errorObserver;
    private Observer<Boolean> questionDataObserver;
    private Observer<List<Game>> gameDataObserver;
    private Observer<String> userObserver;
    private Observer<Uri> userPhotoUrlObserver;
    private Observer<Boolean> progressObserver;
    private Throwable throwable;
    private List<Game> gameList;

    private static final int QUESTION_ID = 234;
    private static final Category CATEGORY = Category.ANIMALS;
    private static final Difficulty DIFFICULTY = Difficulty.EASY;
    private static final String CATEGORY_STRING = "randonm category";
    private static final String DIFFICULTY_STRING = "random difficulty";
    private static final String DISPLAY_NAME = "random name";
    private static final String FAKE_EMAIL = "test@test.test";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        lifecycleRegistry = new LifecycleRegistry(lifecycleOwner);
        when(lifecycleOwner.getLifecycle()).thenReturn(lifecycleRegistry);
        lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);

        errorObserver = (Observer<ErrorCarrier>)mock(Observer.class);
        questionDataObserver = (Observer<Boolean>)mock(Observer.class);
        gameDataObserver = (Observer<List<Game>>)mock(Observer.class);
        userObserver = (Observer<String>)mock(Observer.class);
        userPhotoUrlObserver = (Observer<Uri>)mock(Observer.class);
        progressObserver = (Observer<Boolean>)mock(Observer.class);

        throwable = new Throwable();
        gameList = new ArrayList<>();
        Game game1 = new Game( CATEGORY_STRING, DIFFICULTY_STRING);
        gameList.add(game1);

        when(getUserInteractor.getUser()).thenReturn(Observable.just(firebaseUser));

    }

    @Test
    public void getQuestion() {
        when(getQuestionsInteractor.getQuestionByCategoryMultiple(CATEGORY,DIFFICULTY)).thenReturn(Completable.complete());

        viewModel.questions().observe(lifecycleOwner, questionDataObserver);
        viewModel.progress().observe(lifecycleOwner, progressObserver);
        viewModel.getQuestion(CATEGORY, DIFFICULTY);

        verify(getQuestionsInteractor).getQuestionByCategoryMultiple(CATEGORY, DIFFICULTY);
        verify(questionDataObserver).onChanged(true);
        verify(progressObserver,times(2)).onChanged(anyBoolean());

        InOrder inOrder = Mockito.inOrder(progressObserver);
        inOrder.verify(progressObserver).onChanged(true);
        inOrder.verify(progressObserver).onChanged(false);
    }

    @Test
    public void getQuestionError() {
        when(getQuestionsInteractor.getQuestionByCategoryMultiple(CATEGORY,DIFFICULTY)).thenReturn(Completable.error(throwable));

        viewModel.questions().observe(lifecycleOwner, questionDataObserver);
        viewModel.progress().observe(lifecycleOwner, progressObserver);
        viewModel.error().observe(lifecycleOwner, errorObserver);
        viewModel.getQuestion(CATEGORY, DIFFICULTY);

        verify(getQuestionsInteractor).getQuestionByCategoryMultiple(CATEGORY, DIFFICULTY);
        verify(questionDataObserver,never()).onChanged(anyBoolean());
        verify(errorObserver).onChanged(any(ErrorCarrier.class));
        verify(progressObserver,times(2)).onChanged(anyBoolean());

        InOrder inOrder = Mockito.inOrder(progressObserver);
        inOrder.verify(progressObserver).onChanged(true);
        inOrder.verify(progressObserver).onChanged(false);
    }

    @Test
    public void getAllGames() {
        when(getAllGamesInteractor.getAllGames()).thenReturn(Flowable.just(gameList));

        viewModel.gameData().observe(lifecycleOwner, gameDataObserver);
        viewModel.getAllGames();

        verify(getAllGamesInteractor).getAllGames();
        verify(gameDataObserver).onChanged(gameList);
    }

    @Test
    public void getAllGamesError() {
        when(getAllGamesInteractor.getAllGames()).thenReturn(Flowable.error(throwable));

        viewModel.gameData().observe(lifecycleOwner, gameDataObserver);
        viewModel.error().observe(lifecycleOwner, errorObserver);
        viewModel.getAllGames();

        verify(getAllGamesInteractor).getAllGames();
        verify(gameDataObserver,never()).onChanged(anyList());
        verify(errorObserver).onChanged(any(ErrorCarrier.class));
    }

    @Test
    public void getUser() {
        when(firebaseUser.getDisplayName()).thenReturn(DISPLAY_NAME);
        when(firebaseUser.getPhotoUrl()).thenReturn(uri);

        viewModel.user().observe(lifecycleOwner, userObserver);
        viewModel.userPhotoUrl().observe(lifecycleOwner, userPhotoUrlObserver);
        viewModel.getUser();

        verify(getUserInteractor).getUser();
        verify(firebaseUser).getDisplayName();
        verify(firebaseUser).getPhotoUrl();
        verify(firebaseUser,never()).getEmail();
        verify(userPhotoUrlObserver).onChanged(uri);
        verify(userObserver).onChanged(DISPLAY_NAME);
    }

    @Test
    public void getUserNoDisplayname() {
        when(firebaseUser.getDisplayName()).thenReturn(null);
        when(firebaseUser.getPhotoUrl()).thenReturn(uri);
        when(firebaseUser.getEmail()).thenReturn(FAKE_EMAIL);

        viewModel.user().observe(lifecycleOwner, userObserver);
        viewModel.userPhotoUrl().observe(lifecycleOwner, userPhotoUrlObserver);
        viewModel.getUser();

        verify(getUserInteractor).getUser();
        verify(firebaseUser).getEmail();
        verify(userPhotoUrlObserver).onChanged(uri);
        verify(userObserver).onChanged(FAKE_EMAIL);
    }

    @Test
    @PrepareForTest(Uri.class)
    public void getUserNoPhoto() throws Exception {
        when(firebaseUser.getDisplayName()).thenReturn(DISPLAY_NAME);
        when(firebaseUser.getPhotoUrl()).thenReturn(null);

        //PowerMockito.mockStatic(Uri.class);
        //PowerMockito.when(Uri.parse(anyString())).thenReturn(otherUri);

        viewModel.user().observe(lifecycleOwner, userObserver);
        viewModel.userPhotoUrl().observe(lifecycleOwner, userPhotoUrlObserver);
        viewModel.getUser();

        verify(getUserInteractor).getUser();
        verify(firebaseUser).getDisplayName();
        verify(firebaseUser).getPhotoUrl();
        verify(userPhotoUrlObserver).onChanged(any(Uri.class)); //not called due to...
        //...Uri.parse() method which is static. Logging shows correct behavior during...
        //...tests (and real life case) however I've had problems trying to use...
        //...power mockito here.
        verify(userObserver).onChanged(DISPLAY_NAME);

    }

    @Test
    public void getUserError() {
        when(getUserInteractor.getUser()).thenReturn(Observable.error(throwable));

        viewModel.user().observe(lifecycleOwner, userObserver);
        viewModel.userPhotoUrl().observe(lifecycleOwner, userPhotoUrlObserver);
        viewModel.error().observe(lifecycleOwner, errorObserver);
        viewModel.getUser();

        verify(getUserInteractor).getUser();
        verify(errorObserver).onChanged(any(ErrorCarrier.class));
        verify(userPhotoUrlObserver,never()).onChanged(any(Uri.class));
        verify(userObserver,never()).onChanged(anyString());

    }

    @Test
    public void deleteGame() {
        when(deleteGameInteractor.deleteGame(any(Game.class))).thenReturn(Completable.complete());
        when(getAllGamesInteractor.getAllGames()).thenReturn(Flowable.just(gameList));

        viewModel.gameData().observe(lifecycleOwner, gameDataObserver);
        viewModel.deleteGame(game);

        verify(deleteGameInteractor).deleteGame(game);
        verify(getAllGamesInteractor).getAllGames();
        verify(gameDataObserver).onChanged(gameList);
    }

    @Test
    public void deleteGameError() {
        when(deleteGameInteractor.deleteGame(any(Game.class))).thenReturn(Completable.error(throwable));

        viewModel.gameData().observe(lifecycleOwner, gameDataObserver);
        viewModel.error().observe(lifecycleOwner, errorObserver);
        viewModel.deleteGame(game);

        verify(deleteGameInteractor).deleteGame(game);
        verify(getAllGamesInteractor,never()).getAllGames();
        verify(gameDataObserver,never()).onChanged(anyList());
        verify(errorObserver).onChanged(any(ErrorCarrier.class));
    }

    @Test
    public void prepare() {
        when(getUserInteractor.getUser()).thenReturn(Observable.just(firebaseUser));
        when(getAllGamesInteractor.getAllGames()).thenReturn(Flowable.just(gameList));
        when(firebaseUser.getDisplayName()).thenReturn(DISPLAY_NAME);
        when(firebaseUser.getPhotoUrl()).thenReturn(uri);

        viewModel.gameData().observe(lifecycleOwner,gameDataObserver);
        viewModel.user().observe(lifecycleOwner, userObserver);
        viewModel.userPhotoUrl().observe(lifecycleOwner, userPhotoUrlObserver);
        viewModel.prepare();

        verify(getUserInteractor).getUser();
        verify(firebaseUser).getPhotoUrl();
        verify(firebaseUser).getDisplayName();
        verify(firebaseUser,never()).getEmail();
        verify(getAllGamesInteractor).getAllGames();
        verify(userObserver).onChanged(DISPLAY_NAME);
        verify(userPhotoUrlObserver).onChanged(uri);

    }

    @Test
    public void prepareError() {
        when(getUserInteractor.getUser()).thenReturn(Observable.error(throwable));
        when(getAllGamesInteractor.getAllGames()).thenReturn(Flowable.just(gameList));

        viewModel.gameData().observe(lifecycleOwner,gameDataObserver);
        viewModel.user().observe(lifecycleOwner, userObserver);
        viewModel.userPhotoUrl().observe(lifecycleOwner, userPhotoUrlObserver);
        viewModel.error().observe(lifecycleOwner, errorObserver);
        viewModel.prepare();

        verify(getUserInteractor).getUser();
        verify(firebaseUser,never()).getPhotoUrl();
        verify(firebaseUser,never()).getDisplayName();
        verify(firebaseUser,never()).getEmail();
        verify(getAllGamesInteractor,never()).getAllGames();
        verify(userObserver,never()).onChanged(DISPLAY_NAME);
        verify(userPhotoUrlObserver,never()).onChanged(uri);
        verify(errorObserver).onChanged(any(ErrorCarrier.class));

    }

    @Test
    public void openSettings() {
        viewModel.openSettings(context);
        verify(settingsRouter).open(context, false);
    }

    @Test
    public void openHighscore() {
        viewModel.openHighscore(context);
        verify(highscoreRouter).open(context, false);
    }

    @Test
    public void openQuestion() {
        viewModel.openQuestion(context, QUESTION_ID);
        verify(questionRouter).open(context, QUESTION_ID);
    }

}