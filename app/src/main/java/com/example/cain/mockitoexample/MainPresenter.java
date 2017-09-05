package com.example.cain.mockitoexample;


public class MainPresenter {

    private IMainView view;
    private final Datasource datasource;
    private final PersistanceUtil persistanceUtil;
    private int count = 0;

    public MainPresenter(Datasource datasource, PersistanceUtil persistanceUtil) {
        this.datasource = datasource;
        this.persistanceUtil = persistanceUtil;
    }

    public void onAttachView(IMainView view){
        this.view = view;
        view.setText(persistanceUtil.getValue());
    }

    public void onDetachView(){
        this.view = null;
    }

    public void doSync(){
        view.setText(datasource.getValueSync(count++));
    }

    public void doAsync(){
        datasource.getValueAsync(count++, new Datasource.Callback() {
            @Override
            public void onCallback(String value) {
                view.setText(value);
            }
        });
    }

    public void doAsyncAndPersist(){
        datasource.getValueAsync(count++, new Datasource.Callback(){
            @Override
            public void onCallback(String value) {
                view.setText(value);
                persistanceUtil.saveValue(value);
            }
        });
    }

}
