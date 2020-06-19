# edalex server
----------------
Project overview
----------------
This project is a simple RESTful WS that uses file based H2 to manage simple table message.

-- Source structure --

|-database
|	|-MessageModel.java
|	|-MessageModelAssembler.java
|	|-MessageModelRepository.java
|-exception
|	|-MessageNotFoundAdvice.java
|	|-MessageNotFoundException.java
|-Application.java
|-MessageController.java

-------------------
Develop environment
-------------------
- Language: Java(Spring) with gradle
- IDE: Netbeans 

To start server(using Spring boot):
	On Linux and MacOS:
		./gradlew bootRun
	On Windows:
		gradlew.bat bootRun

--------
Testing
--------
Using JUnit
Unit test for each method approached with white box testing to cover all branch

----------
Versioning
----------
Using Git for version control

-------
License
-------
Edalex
