package com.example.cibilcheck.util;

import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.io.File;

/**
 * Created by ntf-9 on 28/12/17.
 */

public class SheetConstant {

    // static final String AppPackageName = "";
    private static final String FILE_ID = "1jNgffJZcs5Qd22iqsDIL6FYwqyuX58Qo";
    private static final String M_DIRECTORY_NAME = ".database";
    public static final String FILE_DOWNLOAD_URL = "https://drive.google.com/uc?export=download&id="+FILE_ID;
    public static final Long TIME_LIMIT = 600000L;

    //For App advertise
    // static final String AppPackageName = "";
    private static final String SpreadSheetId = "1gU11SWCpPUlZY6FInux9j5W0bF0ujSHGn0je7lPqBNw";
    // public static final String StartURL = "https://sheets.googleapis.com/v4/spreadsheets/" + SpreadSheetId + "/values/";
    // public static final String ENDURL = "!A2:B?majorDimension=ROWS&fields=values&key=AIzaSyBEgkPKDddmmmUKeZK2qhOM3RmGRgcsgvw";
    private static final String category = "Sheet1";
    public static final String fullURL = "https://sheets.googleapis.com/v4/spreadsheets/" + SpreadSheetId + "/values/" + category + "!A2:C?majorDimension=ROWS&fields=values&key=AIzaSyAU0e9_jAboXOmysKfCaRqhdTMvpPNWlGs";


    public static void loadFragment(Fragment fragment, FragmentManager fragmentManager, int replaceViewId) {
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(replaceViewId, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    public static File getDirectory() {
        String directory = Environment.getExternalStorageDirectory().toString();
      //  String directory = context.getFilesDir().getAbsolutePath();
        Log.d("FileDir","is dir "+directory);
        File mFolder = new File(directory, M_DIRECTORY_NAME);
        if (!mFolder.exists()) {
            mFolder.mkdir();
        } else {
            return mFolder;
        }
        return mFolder;
    }




}
