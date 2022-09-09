package com.cleanup.todoc.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.dao.TaskDao;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {

    private TaskDao mTaskDao;
    private LiveData<List<Task>> mAllTasks;


    public TaskRepository(Application application) {
        TodocDatabase database = TodocDatabase.getInstance(application);
        mTaskDao = database.mTaskDao();

        mAllTasks = mTaskDao.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }


    public void insert(Task task) {
        new InsertTaskAsyncTask(mTaskDao).execute(task);
    }

    public void update(Task task) {
        new UpdateTaskAsyncTask(mTaskDao).execute(task);
    }

    public void delete(Task task) {
        new DeleteTaskAsyncTask(mTaskDao).execute(task);
    }



    /**
     * Class for inserting Project in the background
     */
    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao mAsyncTaskDao;

        private InsertTaskAsyncTask(TaskDao taskDao) {
            this.mAsyncTaskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            mAsyncTaskDao.insert(tasks[0]);
            return null;
        }
    }

    private static class UpdateTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao mAsyncTaskDao;

        private UpdateTaskAsyncTask(TaskDao taskDao) {
            this.mAsyncTaskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            mAsyncTaskDao.update(tasks[0]);
            return null;
        }
    }

    private static class DeleteTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao mAsyncTaskDao;

        private DeleteTaskAsyncTask(TaskDao taskDao) {
            this.mAsyncTaskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            mAsyncTaskDao.delete(tasks[0]);
            return null;
        }
    }
}
