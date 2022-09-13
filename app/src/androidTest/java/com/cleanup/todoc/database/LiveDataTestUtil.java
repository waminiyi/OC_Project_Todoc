package com.cleanup.todoc.database;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.cleanup.todoc.model.Task;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LiveDataTestUtil {

    public static  List<Task> getValue(final LiveData<List<Task>> liveData) throws InterruptedException {
        final  Object[] taskList = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<List<Task>> observer = new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                taskList[0] = tasks;
                latch.countDown();
                liveData.removeObserver(this);
            }

        };
        liveData.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);

        return (List<Task>) taskList[0];
    }
}
