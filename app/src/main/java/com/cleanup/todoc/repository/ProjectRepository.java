package com.cleanup.todoc.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import com.cleanup.todoc.dao.ProjectDao;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {

    private ProjectDao mProjectDao;
    private LiveData<List<Project>> mAllProjects;


    public ProjectRepository(Application application) {
        TodocDatabase database = TodocDatabase.getInstance(application);
        mProjectDao = database.mProjectDao();

        mAllProjects = mProjectDao.getAllProjects();
    }


    public LiveData<List<Project>> getAllProjects() {
        return mAllProjects;
    }

    /**
     * Methods for our database operations
     * These should be executed on the background to avoid crashing the app
     * So we use Asynctasks
     */


    public void insert(Project project) {
        new InsertProjectAsyncTask(mProjectDao).execute(project);
    }

    public void update(Project project) {
        new UpdateProjectAsyncTask(mProjectDao).execute(project);
    }

    public void delete(Project project) {
        new DeleteProjectAsyncTask(mProjectDao).execute(project);
    }


    /**
     * Class for inserting Project in the background
     */
    private static class InsertProjectAsyncTask extends AsyncTask<Project, Void, Void> {
        private ProjectDao mAsyncTaskProjectDao;

        private InsertProjectAsyncTask(ProjectDao projectDao) {
            this.mAsyncTaskProjectDao = projectDao;
        }

        @Override
        protected Void doInBackground(Project... projects) {
            mAsyncTaskProjectDao.insert(projects[0]);
            return null;
        }
    }

    private static class UpdateProjectAsyncTask extends AsyncTask<Project, Void, Void> {
        private ProjectDao mAsyncTaskProjectDao;

        private UpdateProjectAsyncTask(ProjectDao projectDao) {
            this.mAsyncTaskProjectDao = projectDao;
        }

        @Override
        protected Void doInBackground(Project... projects) {
            mAsyncTaskProjectDao.update(projects[0]);
            return null;
        }
    }

    private static class DeleteProjectAsyncTask extends AsyncTask<Project, Void, Void> {
        private ProjectDao mAsyncTaskProjectDao;

        private DeleteProjectAsyncTask(ProjectDao projectDao) {
            this.mAsyncTaskProjectDao = projectDao;
        }

        @Override
        protected Void doInBackground(Project... projects) {
            mAsyncTaskProjectDao.delete(projects[0]);
            return null;
        }
    }


}
