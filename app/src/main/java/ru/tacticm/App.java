package ru.tacticm;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import androidx.preference.PreferenceManager;
/**
 * Главный класс приложения
 * 04.2022. Russia, Klimovsk
 * @author Arkady_Gobuzov
 *
 * todo: Sharing pictures (disabled now, due Need ask Permission)
 * https://medium.com/androiddevelopers/sharing-content-between-android-apps-2e6db9d1368b
 * https://stackoverflow.com/questions/7661875/how-to-use-share-image-using-sharing-intent-to-share-images-in-android
 * https://stackoverflow.com/questions/32200068/android-create-file-from-drawable
 * http://developer.alexanderklimov.ru/android/theory/drawable.php
 */
public class App extends Application {

    private static App instance;
    /**
     * текущая активити, та что сейчас на экране
     */
    private static Activity currentActivity;
    /**
     * для хранения переменных приложения
     */
    private SharedPreferences mPrefs;
    /**
     * для хранения переменных приложения
     */
    private SharedPreferences.Editor ed;
    //
    public App() {
        super();
        instance = this;
        System.out.println("*** TactMed start");
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /*if (LeakCanary.isInAnalyzerProcess(this)) {return;}
        LeakCanary.install(this);*/
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        ed = mPrefs.edit();
    }
    /**
     * сохранить строку под ключом в хранилище
     * @param key ключ
     * @param string значение
     */
    public void putString(String key, String string) {
        if (null==string)
            ed.remove(key);
        else
            ed.putString(key, string);
        ed.commit();
    }
    /**
     * сохранить строки под ключом в хранилище
     * @param key ключ
     * @param strings значения
     */
    public void putStrings(String key, String[] strings) {
        if (null==strings || 0==strings.length)
            ed.remove(key);
        else {
            ed.putString(key, Tools.glueString(strings, c.GLUE_DELIM));
        }
        ed.commit();
    }
    /**
     * Взять строку из хранилища
     * @param key название ключа
     * @param def значение по умолчанию
     * @return
     */
    public String getString(String key, String def){
        return mPrefs.getString(key, def);
    }
    /**
     * Взять строки из хранилища
     * @param key название ключа
     * @return
     */
    public String[] getStrings(String key){
        String str = mPrefs.getString(key, "");
        if ("".equals(str))
            return null;
        String[] array = str.split(c.GLUE_DELIM);
        return array;
    }
    /**
     * сохранить число под ключом в хранилище
     * @param key ключ
     * @param i значение
     */
    public void putInt(String key, int i) {
        if (-1==i)
            ed.remove(key);
        else
            ed.putInt(key, i);
        ed.commit();
    }
    /**
     * Взять число int из хранилища
     * @param key название ключа
     * @param def значение по умолчанию
     * @return
     */
    public int getInt(String key, int def){
        return mPrefs.getInt(key, def);
    }
    /**
     * установить Активити текущим
     * @param activity
     */
    public void setCurrentActivity(Activity activity){currentActivity = activity;}
    /**
     * получить текущую Активность
     * @return
     */
    public Activity getCurrentActivity(){return currentActivity;}
    /**
     * Проверить - это текущая Активность?
     * @param activity параметр
     * @return
     */
    public boolean isCurrentActivity(Activity activity){
        return (currentActivity!=null & currentActivity.equals(activity));
    }
}
