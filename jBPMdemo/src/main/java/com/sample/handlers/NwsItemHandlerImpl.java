package com.sample.handlers;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;

public class NwsItemHandlerImpl implements NwsItemHandler {

    public NwsItemHandlerImpl() {
        super();
    }

    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        System.out.println("Executing NwsItemHandler handler...");
        manager.completeWorkItem(workItem.getId(), null);
    }

    public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
        System.out.println("Aborting...");
    }
}
