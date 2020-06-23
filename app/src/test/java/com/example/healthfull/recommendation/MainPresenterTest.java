package com.example.healthfull.recommendation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * local unit test for the main presenter
 */

public class MainPresenterTest {

    @Mock
    private MainContract.recView rView;
    private MainPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = new MainPresenter(rView);
    }

    @Test
    public void handleRecButton() throws Exception {
        mPresenter.handleRecButton();
        verify(rView).showRec();
    }
}