package com.cleanup.todoc.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.dao.ProjectDao;
import com.cleanup.todoc.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.Date;


@Database(entities = {Project.class, Task.class}, version = 1)
public abstract class TodocDatabase extends RoomDatabase {

    public abstract ProjectDao mProjectDao();

    public abstract TaskDao mTaskDao();

    private static TodocDatabase INSTANCE;

    public static TodocDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TodocDatabase.class, "todoc_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sTodocDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback sTodocDatabaseCallback =
            new Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };


    /**
     * Populate the database in the background.
     */

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {


        private final ProjectDao projectDao;
        private final TaskDao taskDao;

        PopulateDbAsync(TodocDatabase db) {

            projectDao = db.mProjectDao();
            taskDao = db.mTaskDao();
        }

        Project project1 = new Project(1L, "Projet Tartampion", 0xFFEADAD1);
        Project project2 = new Project(2L, "Projet Lucidia", 0xFFB4CDBA);
        Project project3 = new Project(3L, "Projet Circus", 0xFFA3CED2);

        Project[] projects = {project1, project2, project3};

        Task task1 = new Task(project1.getId(), "Nettoyer les vitres", new Date().getTime());
        Task task2 = new Task(project2.getId(), "Vider le lave vaisselle", new Date().getTime() + 1000);
        Task task3 = new Task(project3.getId(), "Passer l'aspirateur", new Date().getTime() + 2000);
        Task task4 = new Task(project1.getId(), "Arroser les plantes", new Date().getTime() + 3000);
        Task task5 = new Task(project2.getId(), "Nettoyer les toilettes", new Date().getTime() + 4000);
        Task task6 = new Task(project1.getId(), "Nettoyer les placards", new Date().getTime() + 5000);
        Task task7 = new Task(project2.getId(), "Vider et laver l'aspirateur", new Date().getTime() + 6000);
        Task task8 = new Task(project3.getId(), "Repasser le linge", new Date().getTime() + 7000);
        Task task9 = new Task(project1.getId(), "Laver la litière du chat", new Date().getTime() + 8000);
        Task task10 = new Task(project2.getId(), "Changer la housse de canapé", new Date().getTime() + 9000);

        Task[] tasks = {task1, task2, task3, task4, task5, task6, task7, task8, task9, task10};


        @Override
        protected Void doInBackground(final Void... params) {

            for (int i = 0; i <= projects.length - 1; i++) {
                projectDao.insert(projects[i]);
            }
//
////
//            for (int j = 0; j <= tasks.length - 1; j++) {
//                taskDao.insert(tasks[j]);
//            }
            return null;
        }
    }
}
