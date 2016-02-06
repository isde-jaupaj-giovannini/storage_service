package com.unitn.adapter_service.data;

import com.unitn.storage_service.model.Goal;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by demiurgo on 1/30/16.
 */

@Data
@NoArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
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


    public Goal toGoal(){
        Goal g = new Goal();
        g.setContent(this.getContent());
        g.setDueDate(this.getDueDate());
        g.setCreatedDate(this.getCreatedDate());
        return g;
    }
}
