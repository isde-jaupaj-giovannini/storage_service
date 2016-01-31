package com.unitn.storage_service.model;

import com.unitn.adapter_service.Task;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by erinda on 1/31/16.
 */
@Getter
@Setter
public class Goal {
    private String Content;
    private String DueDate;


    public Task toTask(){
        Task t = new Task();
        t.setContent(this.Content);
        t.setDueDate(this.DueDate);

        return t;
    }
}
