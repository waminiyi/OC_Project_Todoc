package com.cleanup.todoc;

import com.cleanup.todoc.model.Task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertSame;


/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */

@RunWith(JUnit4.class)
public class TaskUnitTest {


    @Test
    public void test_az_comparator() {

        final Task task1 = new Task(1L, "Nettoyer les vitres", new Date().getTime());
        final Task task2 = new Task(2L, "Vider le lave vaisselle", new Date().getTime() + 1000);
        final Task task3 = new Task(4L, "Passer l'aspirateur", new Date().getTime() + 2000);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskAZComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task2);
    }

    @Test
    public void test_za_comparator() {
        final Task task1 = new Task(1L, "Nettoyer les vitres", new Date().getTime());
        final Task task2 = new Task(2L, "Vider le lave vaisselle", new Date().getTime() + 1000);
        final Task task3 = new Task(4L, "Passer l'aspirateur", new Date().getTime() + 2000);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskZAComparator());

        assertSame(tasks.get(0), task2);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_recent_comparator() {
        final Task task1 = new Task(1L, "Nettoyer les vitres", new Date().getTime());
        final Task task2 = new Task(2L, "Vider le lave vaisselle", new Date().getTime() + 1000);
        final Task task3 = new Task(4L, "Passer l'aspirateur", new Date().getTime() + 2000);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskRecentComparator());

        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    @Test
    public void test_old_comparator() {
        final Task task1 = new Task(1L, "Nettoyer les vitres", new Date().getTime());
        final Task task2 = new Task(2L, "Vider le lave vaisselle", new Date().getTime() + 1000);
        final Task task3 = new Task(4L, "Passer l'aspirateur", new Date().getTime() + 2000);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new Task.TaskOldComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }
}