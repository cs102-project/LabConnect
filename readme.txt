LabConnect
Group: G2C

Group Members:
Borga Haktan Bilen 22002733
Vedat Eren Arıcan 22002643
Berkan Şahin 22003211
Berk Çakar 22003021
Alp Ertan 22003912

Description:
LabConnect facilitates communication between students, TA's, tutors,
and instructors. In the background, it is mainly a web application
(If sensible/necessary, it may possibly be ported to Android) that aims
to assist CS introductory courses in terms of organization and communication.
Proposed ideas for features include priority queuing for TA zoom rooms among many other
enhancements to TA/instructor productivity. For example, those who have completed their labs
can be tested using pre-defined (by TA or instructor) unit tests, if students pass the
tests successfully then they will be ordered by the number of visits to TA
in the same session, in order to decrease waiting times for the students
who are waiting from the beginning, and to optimize the process in general.
TA's can also use the system to see previous versions of each student's code
in a more practical way, similar to real version control managers in spirit.
The style guidelines put forth by the instructors can be enforced automatically by parsing
the student's sent code files. Much of the repetitive work that course
staff need to do can be reduced substantially by automated actions,
allowing TA's to allocate time for more hands-on help towards students.
The student experience can be improved further by adding helpful
features such as personal notes for students and so on.

Deployment of the Project:
Because LabConnect designed and implemented as a web application, its deployment cycle might be hard.
Also it might be benefical to being cognizant about the fact that LabConnect's deployment process is designed
for the maintainer of the project rather than end-user. For the deployment from the operating systems
Fedora 33 or later, Ubuntu 20.04 or later (as these OS's are tested) and MacOS, the script called "install.sh" 
in the root folder of the project, should be used. For manuel (Windows) deployment:

Following dependencies should be installed:
- NodeJS and npm = https://nodejs.org/en/download/
- Maven (optionally the maven wrapper scripts can be used: "mvnw.cmd" and "mvnw"): https://maven.apache.org/download.cgi (installation guide: https://maven.apache.org/install.html)
- Docker = https://docs.docker.com/get-docker/

Commands for deployment: 
- (select the directory /src/main/react-client using cd in the terminal) "npm install" => for installing react dependencies
- (select the directory /src/main/react-client using cd in the terminal) "sudo npm run build" => for building react components
- (if maven is installed and enviromental variables are configured) "mvn package -Dmaven.test.skip=true" => for compiling and packaging the project
- (while all dependencies installed and docker back-end is working) "mvn package -Dmaven.test.skip=true && docker-compose up --build --force-recreate" => for deploying the project
- "sudo docker-compose down -v" => for removing the docker components (in case of full redeployment)

After the deployment project can be accessed from the browser on localhost, port: 8080

Current Status:
Back-end works, REST API is 90% done, but controllers are in need of testing and debugging (due to time limitation). 
Thus, some of the functions on the front-end are not implemented. However, previously promised core features can 
be experienced as expected.

Main Tools Used:
Visual Studio Code, IntelliJ Idea, Drawio, Git and GitHub, MongoDB, Redis, Docker and docker-compose (v3.7), NodeJs (v14.16.1), npm (v6.14.12), 
Spring Boot Framework (v2.4.4 [starter parent's version]), React (v17.0.4). All other packages, libraries and different 
dependencies can be found in pom.xml and /src/main/react-client/package.json.

Code organization:
Project code is organized based on main parts of the project: Back End(Data Layer, Service Layer, Controller Layer) -> REST API -> Front End (React).
Briefly, in the root all the configuration files (that are configuring project or a tool we used), in the "/doc" folder all the required and assessed documentation can be found
in the "src/main" folder all the code can be seen (front-end and back-end), further down the "src/main" folder specific part of the project can be seen. 
In "src/main/resource" folder all the static sources can be found. In the "src/main/react-native" folder all the dynamically on demand front-end 
sources can be found. Finally, in the "src/main/java/me/labconnect" folder all the back-end can be found. Back-end files are organized in a 
layer pattern (as they are all indicated by folder names).

Contributions:
Berkan Şahin -> Back-end development + REST API development + Testing + Documentation + Scripting for deployment of project
Berk Çakar -> Back-end development + REST API development + Testing + Documentation
Borga Haktan Bilen -> Back-end development + REST API development + Testing + Documentation
Alp Ertan -> Back-end development + Documentation
Vedat Eren Arıcan -> Back-end development + REST API development + Front-end development + Testing