#!/bin/bash

# TODO check distro, don't assume ubuntu/debian
echo "Updating the system, just in case"
sudo apt update && sudo apt -y upgrade
printf "\n\n"
printf "Packages to install\tTheir purpose:\n"
printf "**************************************\n"
printf "Docker\nDocker-compose\t\tFor building and deploying the container setup\n"
printf "npm\nNode.js\t\t\tFor building the frontend\n"
printf "OpenJDK 11\t\tFor building the backend\n"
printf "\n\n"
sudo apt -y install docker docker-compose npm nodejs openjdk-11-jdk
printf "\n\n"
echo "Now building the frontend, might take some time during the first run because of dependencies"
printf "\n\n"
cd src/main/react-client && npm install && npm rebuild node-sass && npm run build && cd ../../..
printf "\n\n"
echo "Now building the backend, might take some time during the first run because of dependencies"
printf "\n\n"
./mvnw package -Dmaven.test.skip=true
printf "\n\n"
echo "Now deploying the container composition, this takes some time and bandwidth the first time"
printf "\n\n"
sudo systemctl start docker
sudo docker-compose up --force-recreate --build -V -d
printf "\n\n"
echo "You're all set!"
echo "Now visit localhost:8080 from your browser of choice"
echo
echo "Default credentials"
printf "Student:\tdev@vedat.xyz\t\t\tmyPasswd\n"
printf "Instructor:\tdavid@cs.bilkent.edu.tr\t\tDBRefsBadRoboGood\n"
printf "TA:\t\tta@bilkent.edu.tr\t\t\tpasswd\n"
echo "***************************************"
echo
echo "Once you are done with the demo, run:"
echo
echo "cd $(pwd)"
echo "sudo docker-compose down"
echo
echo "Enjoy!"
