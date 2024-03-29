#!/bin/sh

# This is a shell script that configures and deploys LabConnect for:
# * Fedora 33 or later
# * Ubuntu 20.04 or later
# Any recent version of macOS (might not be complete)
# Since this program relies on node.js and Docker, it is not recommended to run it on Windows
# Please use a virtual machine or any other means of running GNU/Linux or macOS if that is the case

if which apt-get >/dev/null 2>&1
then PKGMAN=apt
elif which dnf >/dev/null 2>&1
then PKGMAN=dnf
elif which brew >/dev/null 2>&1
then PKGMAN=brew
else echo "OS not supported" && exit 1
fi

echo "Updating the system, just in case"
[ $PKGMAN = "apt" ] && sudo apt update && sudo apt -y upgrade
[ $PKGMAN = "dnf" ] && sudo dnf -y update
[ $PKGMAN = "brew" ] && brew update
printf "\n\n"
printf "Packages to install\tTheir purpose:\n"
printf "**************************************\n"
printf "Docker\nDocker-compose\t\tFor building and deploying the container setup\n"
printf "npm\nNode.js\t\t\tFor building the frontend\n"
printf "OpenJDK 11\t\tFor building the backend\n"
printf "\n\n"
[ $PKGMAN = "apt" ] && sudo apt -y install docker docker-compose npm nodejs openjdk-11-jdk
[ $PKGMAN = "dnf" ] && sudo dnf -y in docker docker-compose nodejs npm java-11-openjdk-devel
[ $PKGMAN = "brew" ] && brew install docker docker-compose node openjdk@11
printf "\n\n"
echo "Now building the frontend, might take some time during the first run because of dependencies"
printf "\n\n"
cd src/main/react-client && npm install && npm rebuild node-sass && npm run build && cd ../../..
printf "\n\n"
echo "Now building the backend, might take some time during the first run because of dependencies"
printf "\n\n"
rm "target/*.jar" 2>/dev/null
./mvnw package -Dmaven.test.skip=true
printf "\n\n"
echo "Now deploying the container composition, this takes some time and bandwidth the first time"
printf "\n\n"
[ $PKGMAN != "brew" ] && sudo systemctl start docker
sudo docker-compose up --force-recreate --build -V -d
printf "\n\n"
echo "You're all set!"
echo "Now visit localhost:8080 from your browser of choice"
echo
echo "Default credentials"
printf "Student:\tstudent@bilkent.edu.tr\t\t\tAa123456\n"
printf "Instructor:\tadayanik@cs.bilkent.edu.tr\tAa123456\n"
printf "TA:\t\thaya.khattak@bilkent.edu.tr\t\Aa123456n"
echo "***************************************"
echo
echo "Once you are done with the demo, run:"
echo
echo "cd $(pwd)"
echo "sudo docker-compose down"
echo
echo "Enjoy!"
