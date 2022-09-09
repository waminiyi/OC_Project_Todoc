package com.cleanup.todoc.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    /**
     * private member variable to hold a reference to the Repository.
     */
    private TaskRepository mTaskRepository;

    /**
     * private LiveData member variable to cache the list of Tasks.
     */
    private LiveData<List<Task>> mAllTasks;

    /**
     * constructor that gets a reference to the TaskRepository and gets the list of all Tasks
     * from the NoteRepository
     */

    public TaskViewModel(Application application) {
        super(application);
        mTaskRepository = new TaskRepository(application);
        mAllTasks = mTaskRepository.getAllTasks();
    }

    /**
     * "getter" method that gets all the Tasks. This completely hides
     * the implementation from the UI.
     */
    public LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }

    /**
     * wrapper insert() method that calls the Repository's insert() method. In this way, the
     * implementation of insert() is completely hidden from the UI.
     */
    public void insert(Task task) {
        mTaskRepository.insert(task);
    }

    public void update(Task task) {
        mTaskRepository.update(task);
    }

    public void delete(Task task) {
        mTaskRepository.delete(task);
    }

}
