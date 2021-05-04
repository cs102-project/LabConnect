#!/bin/bash

# TODO check distro, don't assume ubuntu/debian
echo "Updating the system, just in case"
sudo apt update && sudo apt -y upgrade
printf "Packages to install\tTheir purpose:\n"
printf "**************************************\n"
printf "Docker\nDocker-compose\t\tFor building and deploying the container setup\n"
printf "npm\nNode.js\t\t\tFor building the frontend\n"
printf "OpenJDK 11\t\tFor building the backend\n"
sudo apt -y install docker docker-compose npm node openjdk-11-jdk
echo "Now building the frontend, might take some time during the first run because of dependencies"
cd src/main/react-client && npm install && npm run build && cd ../../..
echo "Now building the backend, might take some time during the first run because of dependencies"
./mvnw package -Dmaven.test.skip=true
echo "Now deploying the container composition, this takes some time and bandwidth the first time"
sudo systemctl start docker
sudo docker-compose up --force-recreate --build -V -d
echo "You're all set!"
echo "Now visit localhost:8080 from your browser of choice"
echo "Default credentials"
printf "Student:\tdev@vedat.xyz\tmyPasswd\n"
printf "Instructor:\tdavid@cs.bilkent.edu.tr\tDBRefsBadRoboGood\n"
printf "TA:\tta@bilkent.edu.tr\tpasswd\n"
echo "***************************************"
echo
echo "Once you are done with the demo, run:"
echo "cd $(pwd)"
echo "sudo docker-compose down"
echo "Enjoy!"
