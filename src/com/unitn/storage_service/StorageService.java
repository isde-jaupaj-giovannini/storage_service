package com.unitn.storage_service;

import com.unitn.local_database.MeasureData;
import com.unitn.local_database.UserData;
import com.unitn.storage_service.model.Goal;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * Created by erinda on 1/19/16.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface StorageService {

    @WebMethod()
    @WebResult()
    public String getDescription();

    @WebMethod()
    @WebResult()
    void saveData(MeasureData md);

    @WebMethod()
    @WebResult()
    List<MeasureData> getLatestData(int telegramId, int limit);

    @WebMethod()
    @WebResult()
    boolean userExists(int telegramId);

    @WebMethod()
    @WebResult()
    UserData getUser(int telegramId);

    @WebMethod()
    @WebResult()
    void createUser(UserData user);

    @WebMethod()
    @WebResult()
    boolean saveGoal(int telegramId, Goal goal);
}
