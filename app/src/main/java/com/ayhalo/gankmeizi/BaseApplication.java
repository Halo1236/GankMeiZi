package com.ayhalo.gankmeizi;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ayhalo.gankmeizi.greendao.DaoMaster;
import com.ayhalo.gankmeizi.greendao.DaoSession;
import com.squareup.leakcanary.LeakCanary;

/**
 * GankMeiZi
 * Created by Halo on 2017/11/6.
 */

public class BaseApplication extends Application {
    private static DaoSession daoSession;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        setupLeakCanary();
        setupDatabase();
    }

    private void setupLeakCanary(){
         if (LeakCanary.isInAnalyzerProcess(this)){
             return;
         }else {
             LeakCanary.install(this);
         }
    }

    private void setupDatabase(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"meiziapi.db",null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoSession(){
        return daoSession;
    }
}
