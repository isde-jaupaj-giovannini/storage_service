package com.unitn.storage_service.model;

import com.unitn.adapter_service.data.Task;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by erinda on 1/31/16.
 */
@Getter
@Setter
public class Goal {
    private String Content;
    private String DueDate;
    private String CreatedDate;
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm");

    public Task toTask(){
        Task t = new Task();
        t.setContent(this.Content);
        t.setDueDate(this.DueDate);

        Calendar calendar = Calendar.getInstance();
        if(DueDate.contains("WEEK")){
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
        }else{
            //DAY
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
        t.setDueDate(simpleDateFormat.format(calendar.getTime()));
        return t;
    }
}
