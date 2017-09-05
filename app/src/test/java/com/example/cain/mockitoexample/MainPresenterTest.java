package com.example.cain.mockitoexample;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

public class MainPresenterTest {

    static String testValue = "test";

    MainPresenter presenter;

    @Mock
    Datasource datasource;

    @Mock
    IMainView view;

    @Mock
    PersistanceUtil persistanceUtil;

    @Mock
    Context context;

    @Before
    public void setUp() throws Exception {

        // initate mocks
        MockitoAnnotations.initMocks(this);

        // configure global mock behaviors
        doReturn(context).when(view).getContext();

        // instatiate presenter
        presenter = new MainPresenter(datasource, persistanceUtil);
        presenter.onAttachView(view);
    }

    @Test
    public void sanityCheck() throws Exception {}

    @Test
    public void testOnAttach() throws Exception {

        // verify the result of the first onAttachView call in setup
        verify(view).setText(null);

        // reset mocks
        reset(view, persistanceUtil);

        // configure mock PersistanceUtil to return a value
        doReturn(testValue).when(persistanceUtil).getValue();

        // call onAttachView again
        presenter.onAttachView(view);

        // verify the result
        verify(view).setText(testValue);

    }

    @Test
    public void doSync() throws Exception {

        // configure Datasource mock behavior
        doReturn(testValue).when(datasource).getValueSync(anyInt());

        presenter.doSync();

        verify(view).setText(testValue);
    }

    @Test
    public void doAsync() throws Exception {

        // configure Datasource mock behavior to call the passed-in callback
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {

                // the callback will be the 2nd object (index==1) of invocation.getArguments()
                ((Datasource.Callback)invocation.getArguments()[1]).onCallback(testValue);
                return null;
            }
        }).when(datasource).getValueAsync(anyInt(), any(Datasource.Callback.class));

        presenter.doAsync();

        // verify the result
        verify(view).setText(testValue);
    }

    @Test
    public void doAsyncAndPersist() throws Exception {

        // configure mock callback as above
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                ((Datasource.Callback)invocation.getArguments()[1]).onCallback(testValue);
                return null;
            }
        }).when(datasource).getValueAsync(anyInt(), any(Datasource.Callback.class));


        presenter.doAsyncAndPersist();

        // verify both results
        verify(view).setText(testValue);
        verify(persistanceUtil).saveValue(testValue);
    }

}