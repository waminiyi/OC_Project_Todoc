package com.cleanup.todoc.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.dao.ProjectDao;
import com.cleanup.todoc.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;
import java.util.List;


@RunWith(AndroidJUnit4.class)
public class TodocDatabaseUnitTest {

    private TaskDao taskDao;
    private ProjectDao projectDaO;
    private TodocDatabase database;

    @Rule
    public InstantTaskExecutorRule taskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class).build();
        taskDao = database.mTaskDao();
        projectDaO = database.mProjectDao();
    }

    @After
    public void closeDb() throws IOException {
        database.close();
    }

    public void insertSomeData() {
        projectDaO.insert(new Project(1L, "Projet Tartampion", 0xFFEADAD1));
        projectDaO.insert(new Project(2L, "Projet Lucidia", 0xFFB4CDBA));

        taskDao.insert(new Task(1L, "Nettoyer les vitres", new Date().getTime()));
        taskDao.insert(new Task(2L, "Vider le lave vaisselle", new Date().getTime() + 1000));
        taskDao.insert(new Task(2L, "Passer l'aspirateur", new Date().getTime() + 2000));
    }

    @Test
    public void get_task_list() throws InterruptedException {

        insertSomeData();

        List<Task> taskList = LiveDataTestUtil.getValue(taskDao.getAllTasks());

        assertEquals(3, taskList.size());
    }

    @Test
    public void test_projects() throws InterruptedException {

        insertSomeData();
        List<Task> taskList = LiveDataTestUtil.getValue(taskDao.getAllTasks());

        assertEquals("Projet Tartampion", projectDaO.findProjectById(taskList.get(0).getProjectId()).getName());
        assertEquals("Projet Lucidia", projectDaO.findProjectById(taskList.get(1).getProjectId()).getName());
        assertNull(projectDaO.findProjectById(4L));
    }

    @Test
    public void add_task_with_success() throws InterruptedException {
        Project project1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
        projectDaO.insert(project1);
        List<Task> taskList;

        taskList = LiveDataTestUtil.getValue(taskDao.getAllTasks());

        //we ensure taskList is still empty, as we have not added task yet
        assertTrue(taskList.isEmpty());

        /*here we create a new task, as the task id is autoGenerate when we insert it in the database,
        the task id is 0 (default) when we call new task, the real task id will be create when we will
        call taskDao.insert()
         */

        Task task1 = new Task(1L, "Nettoyer les vitres", new Date().getTime());
        taskDao.insert(task1);

        taskList = LiveDataTestUtil.getValue(taskDao.getAllTasks());


//        // to ensure the task is inserted, we'll compare projectId, name and creationTimeStamp
        assertEquals(task1.getProjectId(), taskList.get(0).getProjectId());
        assertEquals(task1.getName(), taskList.get(0).getName());
        assertEquals(task1.getCreationTimestamp(), taskList.get(0).getCreationTimestamp());

    }

    @Test
    public void delete_task_with_success() throws InterruptedException {

        insertSomeData();
        List<Task> taskList = LiveDataTestUtil.getValue(taskDao.getAllTasks());

        assertEquals(3, taskList.size());

        Task task = taskList.get(0);

        taskDao.delete(task);
        taskList = LiveDataTestUtil.getValue(taskDao.getAllTasks());

        assertFalse(taskList.contains(task));
    }

}