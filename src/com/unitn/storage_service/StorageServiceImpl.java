package com.unitn.storage_service;

import com.unitn.adapter_service.AdapterService;
import com.unitn.adapter_service.AdapterServiceImpl;
import com.unitn.adapter_service.data.Project;
import com.unitn.adapter_service.data.Quote;
import com.unitn.adapter_service.data.Task;
import com.unitn.adapter_service.data.XkcdComic;
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
import java.util.List;

/**
 * Created by erinda on 1/19/16.
 */
@WebService( endpointInterface = "com.unitn.storage_service.StorageService",
             serviceName="Storage")
public class StorageServiceImpl implements StorageService {

    LocalDB localDB = new LocalDatabase().getLocalDBImplPort();
    AdapterService adapterService = AdapterServiceImpl.createAdapterService();


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
    public Integer getFromToStepsData(long t1, long t2) {
        return localDB.totalSteps(t1, t2);
    }

    @Override
    public List<Goal> getGoals(int telegramId) {

        UserData userData = localDB.getUser(telegramId);
        long projectId = userData.getProjectId();

        List<Goal> goals = null;

        try {
            List<Task> ls = adapterService.getTaskList(projectId).execute().body();
            for ( Task t: ls ) {
                goals.add(t.toGoal());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return goals;
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
            project.Content = user.getIdTelegram()+"-tasks";
            project = adapterService.createProject(project).execute().body();
            user.setProjectId(project.Id);
            localDB.createUser(user);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public XkcdComic getRandomComic() {
        try {
            return adapterService.randomComic().execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Quote getMovieQuote() {
        try {
            return adapterService.movieQuote().execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Quote getFamousQuote() {
        try {
            return adapterService.famousQuote().execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
