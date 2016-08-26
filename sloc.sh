#!/bin/sh

echo JPA/Hibernate
cloc hibernate --include-lang=Java
echo JOOQ
cloc jooq --include-lang=Java --exclude-dir=generated
echo MyBatis
cloc mybatis --include-lang=Java --exclude-dir=generated
echo Cayenne
cloc cayenne --include-lang=Java --exclude-dir=generated
echo OpenJPA
cloc openjpa --include-lang=Java
