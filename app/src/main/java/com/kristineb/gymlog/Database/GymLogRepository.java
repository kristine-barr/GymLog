package com.kristineb.gymlog.Database;

import android.app.Application;
import android.util.Log;

import com.kristineb.gymlog.Database.entities.GymLog;
import com.kristineb.gymlog.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GymLogRepository {
    private GymLogDAO gymLogDAO;
    private ArrayList<GymLog> alllogs;

    public GymLogRepository(Application application) {
        GymLogDatabase db = GymLogDatabase.getDatabase(application);
        this.gymLogDAO = db.gymLogDAO();
        this.alllogs = this.gymLogDAO.getAllRecords();
    }

    public ArrayList<GymLog> getAlllogs() {
        Future<ArrayList<GymLog>>future = GymLogDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<GymLog>>() {
                    @Override
                    public ArrayList<GymLog> call() throws Exception {
                        return gymLogDAO.getAllRecords();
                    }
                }
        );
        try{
            return future.get();
        }
        catch(InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Log.i(MainActivity.TAG, "Problem when getting all GymLogs in the repository");
        }
        return null;
    }

    public void insertGymLog(GymLog gymlog) {
        GymLogDatabase.databaseWriteExecutor.execute(() ->
        {
            gymLogDAO.insert(gymlog);
        });
    }
}
