rem  脚本执行生成自定义zip包
cd ../../../
mvn clean install -Dmaven.test.skip=true -P profile1
