package com.unitn.storage_service;

import com.unitn.adapter_service.AdapterService;
import com.unitn.adapter_service.Project;
import com.unitn.adapter_service.Task;
import com.unitn.local_database.LocalDB;
import com.unitn.local_database.LocalDatabase;
import com.unitn.local_database.MeasureData;
import com.unitn.local_database.UserData;
import com.unitn.storage_service.model.Goal;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.List;

/**
 * Created by erinda on 1/19/16.
 */
@WebService( endpointInterface = "com.unitn.storage_service.StorageService",
             serviceName="Storage")
public class StorageServiceImpl implements StorageService {

    LocalDB localDB = new LocalDatabase().getLocalDBImplPort();
    AdapterService adapterService = AdapterService.createAdapterService();


    @Override
    public String getDescription() {
        return "TEST";
    }

    @Override
    public void saveData(MeasureData md) {
        localDB.saveData(md);
    }

    @Override
    public List<MeasureData> getLatestData(int telegramId, int limit) {
        return localDB.getLatestData(telegramId, limit);
    }

    @Override
    public boolean userExists(int telegramId) {
        return localDB.userExists(telegramId);
    }

    @Override
    public UserData getUser(int telegramId) {
        return localDB.getUser(telegramId);
    }

    @Override
    public boolean saveGoal(int telegramId, Goal goal) {
        UserData user = localDB.getUser(telegramId);
        Task task = goal.toTask();
        task.setProjectId(user.getProjectId());
        try {
            task = adapterService.createTask(task).execute().body();
            return task.getId() > 0;
        } catch (IOException |  NullPointerException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void createUser(UserData user) {
        try {
            Project project = new Project();
            project.Content = Base64.getEncoder().encodeToString((user.getIdTelegram()+"").getBytes())+"-tasks";
            project = adapterService.createProject(project).execute().body();
            user.setProjectId(project.Id);
            localDB.createUser(user);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IllegalArgumentException, IOException, URISyntaxException {
        String PROTOCOL = "http://";
        String HOSTNAME = InetAddress.getLocalHost().getHostAddress();
        if (HOSTNAME.equals("127.0.0.1")) {
            HOSTNAME = "localhost";
        }
        String PORT = "6903";
        String BASE_URL = "/ws/storage";

        if (String.valueOf(System.getenv("PORT")) != "null") {
            PORT = String.valueOf(System.getenv("PORT"));
        }

        String endpointUrl = PROTOCOL + HOSTNAME + ":" + PORT + BASE_URL;
        System.out.println("Starting "+ StorageService.class.getSimpleName() +"...");
        System.out.println("--> Published. Check out " + endpointUrl + "?wsdl");
        Endpoint.publish(endpointUrl, new StorageServiceImpl());
    }


}
