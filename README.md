# ORM Frameworks Demo

The goal of this project is to show how hard it is to use [PostgreSQL extra features (JSON, custom types, etc.)](#learn) in various [ORM frameworks](#implemented-frameworks).

## Learn

Each ORM has its own root project. In each project there is an implementation of `AirlinesService`. Its a good place to start exploring the code.

Following PostgreSQL features has been implemented by each project.

* [JSON type](https://www.postgresql.org/docs/9.5/static/functions-json.html) column. Sorting and filtering by this column.
* Custom [composite type](https://www.postgresql.org/docs/9.5/static/rowtypes.html) column.
* [Enumerated type](https://www.postgresql.org/docs/current/static/datatype-enum.html) column.
* [Array type](https://www.postgresql.org/docs/9.5/static/arrays.html) column.
* [Stored procedures](https://www.postgresql.org/docs/9.5/static/plpgsql.html) with OUT parameters.
* Auditing to determine who and when created/modified a record. And what revision the record has.
* Paging and sorting of the selected records by Spring Data's [`Pageable`](http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html) class.
* [Java 8 time API](http://www.oracle.com/technetwork/articles/java/jf14-date-time-2125367.html) for date-time types.

Check out the `schema.sql` file for more details about the schema.

## Test the ORMs

1. Install the [Gradle](http://gradle.org) and [PostgreSQL](https://www.postgresql.org) on your machine.
 To count SLOC install the [CLOC](https://github.com/AlDanial/cloc).
2. Initialize the database by executing `create_database.sql` script.
3. Execute `run.sh` to run all ORM implementations. Application cleans its database automatically.

## Implemented frameworks

* [ActiveJDBC](http://javalite.io/activejdbc)
* [Apache Cayenne](http://cayenne.apache.org)
* [Apache OpenJPA](http://openjpa.apache.org)
* [DataNucleus Access Platform](http://www.datanucleus.org)
* [Ebean](http://ebean-orm.github.io)
* [EclipseLink](http://www.eclipse.org/eclipselink/)
* [JOOQ](http://www.jooq.org)
* [MyBatis](http://mybatis.org)
* [Spring Data JPA/Hibernate](http://projects.spring.io/spring-data-jpa/)

## Excluded frameworks

### Lack of support of features

* [Exposed](https://github.com/JetBrains/Exposed)
* [Reladomo](https://github.com/goldmansachs/reladomo)
* [Speedment](http://www.speedment.com)

### Obsolete

* [ActiveJPA](https://github.com/activejpa/activejpa)
* [Apache Torque](https://db.apache.org/torque/torque-4.0/index.html)
* [Carbonado](https://github.com/Carbonado/Carbonado)
* [Fjorm](https://github.com/mladenadamovic/fjorm/tree/master)
* [Hydrate](http://hydrate.sourceforge.net/Manual.html)
* [IBM PureQuery](http://www.ibm.com/developerworks/downloads/im/datastudiodev/?S_TACT=105AGX01&S_CMP=LP)
* [JDO Instruments](http://www.jdoinstruments.org)
* [JPOX](http://www.jpox.org)
* [ORMLite](http://ormlite.com)
* [QuickDB ORM](https://code.google.com/archive/p/quickdb/)
* [Speedo](http://speedo.ow2.org)
* [TJDO Project](http://tjdo.sourceforge.net)
