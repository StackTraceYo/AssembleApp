# AssembleApp

## Importing and Running
To Import the project
* To Import this project import the top level build.sbt


To Run web and client in Dev Mode with Docker:
* use 'docker-compose up'

To Run application in Dev Mode without Docker:
* ./run-assemble.sh -r to run the web app (web project)
* go into client directory and run 'ng serve' or 'npm start'


## AssembleApp Sub-Projects
Splitting projects up to decouple them from one another as much as possible
### Client
* <b>client</b> project houses the client tier
    * ngrx/angular application
    
### Assemble-web
* <b>web</b> project is the REST API
    * Houses the rest interface for the app
    * dependent on below projects.

* <b>user-api</b> project is a placeholder for the interface for user services
    * authentication ect..
    * built against interfaces for this project so we can swap out implementations later
    
   
* <b>assemble-group</b> is the project that houses the main system of the app

    * Project is separate to decouple it from Play/ the Web
    * Actor based system that the web will interact with via a rest interface
    * will handle the user groups
    
* <b>assemble-geo</b> is the project that houses the geospatial components of groups
    * Interface for geospatial querys and group handling
    * Planned implementations include an in memory and a database backed implementation (database tbd)
