#!/bin/bash
#Deploy to dev.telcong.com the bank

mvn clean install
#Runn all tests

mvn test
rc=$?
if [[ $rc != 0 ]] ; then
 echo 'could not perform tests'; exit $rc
fi

#Using rsync to copy the files to testDir
destination=clouway@dev.telcong.com:/opt/telcong/incubator/i-bank/
rsync target/AngularBank-jar-with-dependencies.jar $destination
rsync -vr src/main/webapp/ $destination/frontend/
rsync configuration.properties $destination
#stop server
ssh clouway@dev.telcong.com sudo stop upstibank
ssh clouway@dev.telcong.com sudo start upstibank
