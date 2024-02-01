#!/bin/bash
cd "./frontend" || exit

npm install

npm run build

cp -r ./dist/* ../src/main/resources/META-INF/resources

cd "../"

./mvnw package

java -Dquarkus.profile=prod -jar target/quarkus-app/quarkus-run.jar