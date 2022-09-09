package com.cleanup.todoc.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.repository.ProjectRepository;

import java.util.List;

public class ProjectViewModel extends AndroidViewModel {

    /**
     * private member variable to hold a reference to the Repository.
     */
    private ProjectRepository mProjectRepository;

    /**
     * private LiveData member variable to cache the list of Projects.
     */
    private LiveData<List<Project>> mAllProjects;

    /**
     * constructor that gets a reference to the ProjectRepository and gets the list of all Projects
     * from the NoteRepository
     */

    public ProjectViewModel(Application application) {
        super(application);
        mProjectRepository = new ProjectRepository(application);
        mAllProjects = mProjectRepository.getAllProjects();
    }

    /**
     * "getter" method that gets all the Projects. This completely hides
     * the implementation from the UI.
     */
    public LiveData<List<Project>> getAllProjects() {
        return mAllProjects;
    }

    /**
     * wrapper insert() method that calls the Repository's insert() method. In this way, the
     * implementation of insert() is completely hidden from the UI.
     */
    public void insert(Project project) {
        mProjectRepository.insert(project);
    }

    public void update(Project project) {
        mProjectRepository.update(project);
    }

    public void delete(Project project) {
        mProjectRepository.delete(project);
    }


}
