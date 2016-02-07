# storage_service

Storage service is responsible for handling all data related requests. Mainly, this service include persistence and retrieval related tasks. The storage service layer sits on top of databases in order to provide data to all other modules in the application. This module can talk to [Local Database Service](https://github.com/isde-jaupaj-giovannini/local_database) and to external [API adapter](https://github.com/isde-jaupaj-giovannini/adapter_service).

[WSDL](https://radiant-chamber-78838.herokuapp.com/ws/storage?wsdl)

## SOAP Services
 
 
  1- **saveData(MeasureData md)** Saves Measure Data
 
  2- **getLatestData(int telegramId, int limit)** return the latest data in the database
 
  3- **userExists(int id)** tells you if a user is already registered
 
  4- **getUser(int id)** return the user data given it's id
 
  5- **createUser(UserData user)** create a new user
  
  6- **saveGoal(int telegramId, Goal goal)** save a new goal 