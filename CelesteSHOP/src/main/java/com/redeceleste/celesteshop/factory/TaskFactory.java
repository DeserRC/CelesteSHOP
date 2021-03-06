package com.redeceleste.celesteshop.factory;

import com.redeceleste.celesteshop.CelesteSHOP;
import com.redeceleste.celesteshop.task.PointsGetTask;
import com.redeceleste.celesteshop.task.PointsUpdateTask;
import lombok.Getter;

@Getter
public class TaskFactory {
    private final CelesteSHOP main;
    private final PointsUpdateTask updateTaskAsync;
    private final PointsUpdateTask updateTaskSync;
    private final PointsGetTask getTask;

    public TaskFactory(CelesteSHOP main) {
        this.main = main;
        this.updateTaskAsync = new PointsUpdateTask(main, true);
        this.updateTaskSync = new PointsUpdateTask(main, false);
        this.getTask = new PointsGetTask(main);
    }
}
