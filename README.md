# AssembleApp

## Importing and Running
To Import the project
* To Import this project import the top level build.sbt


To Run application in Dev Mode with Docker:
* TODO

To Run application in Dev Mode without Docker:
* ./run-assemble.sh -r to run the web app (web project)
* do something for client


## multi-project
* <b>client</b> project houses the client tier
    * currently outside of the sbt run task (have to bring up manually)
    * later we can have scripts that bring up the client stuff along with the web tier
* <b>web</b> project is the REST API

* <b>user-api</b> project is a placeholder for the interface for user services
    * authentication ect..
    * we will build interfaces for this project so we can swap out implementations later
