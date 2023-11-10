package me.subkek.elyrestorer.type;

import java.util.concurrent.atomic.AtomicLong;

public abstract class Task {
    private static final AtomicLong taskIdGenerator = new AtomicLong(0);

    private final TaksType taksType;
    private final long taskId;


    public Task(TaksType taksType) {
        this.taskId = taskIdGenerator.getAndIncrement();
        this.taksType = taksType;
    }

    public long getTaskId() {
        return taskId;
    }

    public TaksType getType() {
        return taksType;
    }

    public abstract void execute();
}