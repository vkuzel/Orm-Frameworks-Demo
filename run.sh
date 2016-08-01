#!/bin/sh

cd hibernate
gradle clean bootRun
cd ..

cd jooq
gradle clean generateDomainObjects bootRun
cd ..

cd mybatis
gradle clean generateDomainObjects bootRun
cd ..

cd cayenne
gradle clean generateDomainObjects bootRun
cd ..