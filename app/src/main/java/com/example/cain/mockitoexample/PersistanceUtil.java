package com.example.cain.mockitoexample;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Example showing how to refactor a no-instance static-methods class into a singleton.
 */
public class PersistanceUtil {

    private static PersistanceUtil INSTANCE;
    private static final String KEY = "val";
    private final SharedPreferences sharedPreferences;

    private PersistanceUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(PersistanceUtil.class.getName(), Context.MODE_PRIVATE);
    }

    public synchronized static PersistanceUtil getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new PersistanceUtil(context);
        }
        return INSTANCE;
    }

    /**
     * @deprecated use {@link #saveValue(String)} on singleton instead.
     */
    @Deprecated
    public static void saveValue(Context context, String val){
        getInstance(context).saveValue(val);
    }

    public void saveValue(String val){
        sharedPreferences.edit().putString(KEY, val).apply();
    }


    /**
     * @deprecated use {@link #getValue()} on singleton instead.
     */
    @Deprecated
    public static String getValue(Context context){
        return getInstance(context).getValue();
    }

    public String getValue(){
        return sharedPreferences.getString(KEY, "");
    }

}
