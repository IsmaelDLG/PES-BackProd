#!/bin/bash

# run our server as a service

MY_PATH="/home/alumne/FIB-PES-LAB/Sources/purplepoint-backend/target/"

run_purplepoint () {
	dt=$(TZ=Europe/Madrid /bin/date)
	echo "---------- Starting Server ( $dt ) ----------" 
	/usr/bin/java -jar /home/alumne/FIB-PES-LAB/Sources/purplepoint-backend/target/purplepoint-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod &>> /home/alumne/execution.log 
	dt=$(TZ=Europe/Madrid /bin/date)
	echo "---------- Shutdown ( $dt ) ----------"
}



while true
do
	run_purplepoint
	/bin/sleep 5
done
