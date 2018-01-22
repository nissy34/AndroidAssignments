package com.yn.user.rentacar.model.backend;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.yn.user.rentacar.model.backend.DBManagerFactory.getManager;

/**
 * Created by USER on 07/11/2017.
 */

public class AppContentProvider extends ContentProvider {


    DB_manager manager;
    final String TAG = "AppContentProvider";
    private static final int CAR = 100;
    private static final int CAR_ID = 101;
    private static final int CARMODEL = 200;
    private static final int CARMODEL_ID = 201;
    private static final int CLIENT = 300;
    private static final int CLIENT_ID = 301;
    private static final int MANAGER = 400;
    private static final int MAMAGER_ID = 401;
    private static final int ORDER = 500;
    private static final int ORDER_ID = 501;
    private static final int BRANCH = 600;
    private static final int BRANCH_ID = 601;


    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate");
        manager=getManager();
        return false;
    }
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        String content = AppContract.AUTHORITY;

        // All paths to the UriMatcher have a corresponding code to return
        // when a match is found (the ints above).
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content, "Car", CAR);
        matcher.addURI(content, "Car" + "/#", CAR_ID);
        matcher.addURI(content, "CarModel", CARMODEL);
        matcher.addURI(content, "CarModel"+ "/#", CARMODEL_ID);
        matcher.addURI(content, "Branch", BRANCH);
        matcher.addURI(content, "Branch"+ "/#", BRANCH_ID);
        matcher.addURI(content, "Client", CLIENT);
        matcher.addURI(content, "Client"+ "/#", CLIENT_ID);
        matcher.addURI(content, "Manager", MANAGER);
        matcher.addURI(content, "Manager"+ "/#", MAMAGER_ID);
        matcher.addURI(content, "Order", ORDER);
        matcher.addURI(content, "Order"+ "/#", ORDER_ID);

        return matcher;
    }
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Log.d(TAG, "query " + uri.toString());

        String listName = uri.getLastPathSegment();
        // String s = AcademyContract.Student.STUDENT_URI.getLastPathSegment();
        switch (sUriMatcher.match(uri)) {
            case BRANCH:
                return manager.getBranches();
            case  CLIENT:
                return manager.getClients();
            case MANAGER:
                return manager.getManagers();
            case MAMAGER_ID:
                return manager.getManager(ContentUris.parseId(uri));
            case CAR:
                return manager.getCars();
            case CAR_ID:
                return manager.getCar(ContentUris.parseId(uri));
            case CARMODEL:
                return manager.getCarModels();
            case CARMODEL_ID:
                return manager.getCarModel(ContentUris.parseId(uri));


        }
        return null;
    }
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG, "getType " + uri.toString());
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.d(TAG, "insert " + uri.toString());

        String listName = uri.getLastPathSegment();
        long id=-1;
        switch (listName) {
            case "Client":
                id = manager.addClient(contentValues);
                return ContentUris.withAppendedId(uri, id);
            case "Manager":
                id = manager.addManager(contentValues);
                return ContentUris.withAppendedId(uri, id);
            case "Car":
                id = manager.addCar(contentValues);
                return ContentUris.withAppendedId(uri, id);

            case "CarModel":
                id = manager.addCarModel(contentValues);
                return ContentUris.withAppendedId(uri, id);

            case "Branch":
                id = manager.addBranch(contentValues);
                return ContentUris.withAppendedId(uri, id);

        }
        return null;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        Log.d(TAG, "delete " + uri.toString());

        String listName = uri.getPathSegments().get(0);
        long id = ContentUris.parseId(uri);
        switch (listName) {
            case "Client":
                if(manager.removeClient(id))
                    return 1;
                break;
            case "Manager":
                if(manager.removeManager(id))
                    return 1;
                break;

            case "Car":
                if(manager.removeCar(id))
                    return 1;
                break;

            case "CarModel":
                if(manager.removeCarModel(id))
                    return 1;
                break;

            case "Branch":
                if(manager.removeBranch(id))
                    return 1;
                break;
        }

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        Log.d(TAG, "update " + uri.toString());

        String listName = uri.getPathSegments().get(0);
        long id = ContentUris.parseId(uri);

        switch (listName) {
            case "Client":
                if(manager.updateClient(id,values))
                    return 1;
                break;
            case "Manager":
                if(manager.updateManager(id,values))
                    return 1;
                break;
            case "Car":
                if(manager.updateCar(id,values))
                    return 1;
                break;

            case "CarModel":
                if(manager.updateCarModel(id,values))
                    return 1;
                break;

            case "Branch":
                if(manager.updateBranch(id,values))
                    return 1;
                break;
        }

        return 0;
    }
}
