#!/bin/bash
cd ../
nohup java -jar common-code-generate-web-0.0.1-SNAPSHOT.jar -cp config/generator.properties >bin/nohup.out 2>&1 &
