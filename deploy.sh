#!/bin/bash
#Deploy to dev.telcong.com the bank
mvn clean install
#Runn all tests
#If the tests dont pass files wont be deployed
mvn test
rc=$?
if [[ $rc != 0 ]] ; then
 echo 'could not perform tests'; exit $rc
fi
#Using rsync to copy the files to testDir
destination=clouway@dev.telcong.com:/opt/telcong/incubator/i-bank/
scp target/AngularBank-jar-with-dependencies.jar $destination
rsync -vr src/main/webapp/ $destination/frontend/
scp configuration.properties $destination
#stop server
ssh clouway@dev.telcong.com sudo stop upstibank
#Start server
ssh clouway@dev.telcong.com sudo start upstibank
