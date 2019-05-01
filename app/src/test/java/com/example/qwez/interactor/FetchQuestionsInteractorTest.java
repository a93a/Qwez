package com.example.qwez.interactor;

import com.example.qwez.RxResources;
import com.example.qwez.repository.local.GameRepositoryType;
import com.example.qwez.repository.opentdb.OpenTDBType;
import com.example.qwez.util.Category;
import com.example.qwez.util.Difficulty;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class FetchQuestionsInteractorTest {

    @ClassRule
    public static final RxResources rxres = new RxResources();

    @Mock
    OpenTDBType openTDBType;

    @Mock
    GameRepositoryType gameRepositoryType;

    @InjectMocks
    FetchQuestionsInteractor fetchQuestionsInteractor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getQuestionByCategoryMultiple() {
        

        fetchQuestionsInteractor.getQuestionByCategoryMultiple(Category.ANIMALS, Difficulty.EASY )
                .test()
                .assertNoErrors()
                .assertComplete();
    }
}