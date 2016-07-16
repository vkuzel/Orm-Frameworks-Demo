#!/bin/sh

echo JPA/Hibernate
cloc hibernate --include-lang=Java
echo JOOQ
cloc jooq --include-lang=Java --exclude-dir=generated
