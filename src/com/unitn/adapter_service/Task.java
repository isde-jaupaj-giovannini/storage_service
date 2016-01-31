package com.unitn.adapter_service;

import lombok.Data;

/**
 * Created by demiurgo on 1/30/16.
 */

@Data
public class Task {
    public long Id;
    public String Content;
    public int ItemType;
    public Boolean Checked;
    public Long ProjectId;
    public String DateString;
    public int DateStringPriority;
    public String DueDate;
    public Integer Priority;
    public String CreatedDate;
    public Boolean Deleted;
}
