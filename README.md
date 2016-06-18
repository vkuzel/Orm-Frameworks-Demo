# ORM Frameworks Demo

The goal of this project is to show how hard it is to use [PostgreSQL extra features (JSON, custom types, etc.)](#tested-features) in various [ORM frameworks](#frameworks).

There is one root project for each ORM.
Each project is implementation of `AirilinesService` stored in common module.
`AirlinesService` declares methods that are used to test ORMs capabilities.

## Getting started

1. Install the [Gradle](http://gradle.org) and [PostgreSQL](https://www.postgresql.org) on your machine.
 To count the SLOC install the [CLOC](https://github.com/AlDanial/cloc).
2. Initialize the database by executing `initialize.sql` and then `database.sql` scripts.
3. To run one of the ORM implementations go to it's directory and type `gradle bootRun`. Or execute `run.sh` to run it all. Application cleans all mess in database on each run.

Don't forget to explore the code.

## Frameworks

* [Spring Data JPA/Hibernate](http://projects.spring.io/spring-data-jpa/)

### TODO

* [JOOQ](http://www.jooq.org)
* [myBATIS](http://blog.mybatis.org)
* [Speedment](http://www.speedment.com)
* [Apache Cayenne](http://cayenne.apache.org)
* [Apache OpenJPA](http://openjpa.apache.org)
* [DataNucleus Access Platform](http://www.datanucleus.org)
* [Fjorm](https://github.com/mladenadamovic/fjorm/tree/master)
* [Ebean](http://ebean-orm.github.io)
* [ActiveJDBC](http://javalite.io/activejdbc)

### Excluded

All non-free projects are automatically excluded from this demo. Following projects are excluded usually because long time inactivity of their authors.

* [IBM PureQuery](http://www.ibm.com/developerworks/downloads/im/datastudiodev/?S_TACT=105AGX01&S_CMP=LP) - Last version is from 2009.
* [Hydrate](http://hydrate.sourceforge.net/Manual.html) - Last version is from 2006 + XML configuration.
* [ActiveJPA](https://activejpa.org) - Last commits from 2014. Seems like there is no stable release.
* [Apache Torque](https://db.apache.org/torque/torque-4.0/index.html) - DOs are generated from XML file.
* [QuickDB ORM](https://code.google.com/archive/p/quickdb/) - Last commits from 2010. Code is accesible from Google Code Archive only.
* [ORMLite](http://ormlite.com) - Last version from 2013.
* [JDO Instruments](http://www.jdoinstruments.org) - This project even doesn't have working homepage.
* [Speedo](http://speedo.ow2.org) - Last commit from 2005.
* [TJDO Project](http://tjdo.sourceforge.net) - It has CVS repository with last commit from 2008.
* [JPOX](http://www.jpox.org) - Last version on SourceForge is from 2013.
* [Carbonado](https://github.com/Carbonado/Carbonado) - Seems like abandoned project.

## Tested features

Following PostgreSQL features are tested by the project.
Check out the database.sql file for more details about the schema.

* [JSON type](https://www.postgresql.org/docs/9.5/static/functions-json.html) column. Sorting and filtering by this column.
* [Custom composite type](https://www.postgresql.org/docs/9.5/static/rowtypes.html) column.
* [Enumerated type](https://www.postgresql.org/docs/current/static/datatype-enum.html) column.
* [Array type](https://www.postgresql.org/docs/9.5/static/arrays.html) column.
* [Stored procedures](https://www.postgresql.org/docs/9.5/static/plpgsql.html) with out parameters.
* Auditing to determine who and when created/modified a record. And what revision the record has.
* Paging and sorting of the selected records by Spring Data's [`Pageable`](http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html) class.
