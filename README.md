# ORM Frameworks Demo

The goal of this project is to show how hard it is to use [PostgreSQL extra features (JSON, custom types, etc.)](#tested-features) in various [ORM frameworks](#frameworks).

There is one root project for each ORM.
Each project is implementation of `AirilinesService` stored in common module.
`AirlinesService` declares methods that are used to test ORMs capabilities.

## Getting started

1. Install the [Gradle](http://gradle.org) and [PostgreSQL](https://www.postgresql.org) on your machine.
 To count SLOC install the [CLOC](https://github.com/AlDanial/cloc).
2. Initialize the database by executing `initialize.sql` and then `database.sql` scripts.
3. Execute `run.sh` to run all ORM implementations. Application cleans all mess in database on each run.

Don't forget to explore the code.

## Implemented frameworks

* [Spring Data JPA/Hibernate](#spring-data-jpahibernate)
* [JOOQ](#jooq)
* [MyBatis](#mybatis)
* [Speedment](#speedment)

In version 2.3.5 Speedment does not support transactions. So I decided to freeze this ORM until it will support it.
There is partial implementation. But there are missing mappers, etc.
There is also second project speedment-mapping-plugin to map custom types to UDTs.
This project is used by Speedment generator and by my implementation too.

### TODO

* [Apache Cayenne](http://cayenne.apache.org)
* [Apache OpenJPA](http://openjpa.apache.org)
* [DataNucleus Access Platform](http://www.datanucleus.org)
* [Fjorm](https://github.com/mladenadamovic/fjorm/tree/master)
* [Ebean](http://ebean-orm.github.io)
* [ActiveJDBC](http://javalite.io/activejdbc)

## Excluded frameworks

All non-free projects are automatically excluded from this demo. Following projects are also excluded. Usually because of long time inactivity of their authors.

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
* [Stored procedures](https://www.postgresql.org/docs/9.5/static/plpgsql.html) with OUT parameters.
* Auditing to determine who and when created/modified a record. And what revision the record has.
* Paging and sorting of the selected records by Spring Data's [`Pageable`](http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html) class.
* [Java 8 time API](http://www.oracle.com/technetwork/articles/java/jf14-date-time-2125367.html) for date-time types.

## Pros & cons of each framework

### [Spring Data JPA/Hibernate](http://projects.spring.io/spring-data-jpa/)

#### Pros

* Built-in support for auditing.
* Built-in support for relations between tables. Even though it's not used in this project.

#### Cons

* No native support for Postgres-specific or user defined types. You have to implement your own mappings. Mappings and custom types declaration can be simplified by custom Hibernate dialect: https://github.com/vkuzel/Hibernate-PostgreSQL-Extended-Dialect
* No support for stored procedures you have to implement it by yourself.

### [JOOQ](http://www.jooq.org)

Before you start generate domain classes by running `gradle generateDomainObjects`.

#### Pros

* Ligthweight with quite small amount of boilerplate code.
* Generated code supports UDTs and stored procedures.

#### Cons

* Generated POJOs with UDT are not working because of https://github.com/jOOQ/jOOQ/issues/5401
* Generated DAO.insert() method is not useable because it does not return generated ID. So I decided to use DAO at all. See https://github.com/jOOQ/jOOQ/issues/2536
* Custom naming strategy that turns plural table names to singulars breaks a record's column names. So its unusable.
* No built-in support for auditing.

### [MyBatis](http://mybatis.org)

#### Pros

* Lightweight.
* Easy to understand and easy to implement.

#### Cons

* No built-in support for auditing.
* Records pagination and ordering is kind of sketchy.
* Does not recognize PostgreSQL UDTs.

### [Speedment](http://www.speedment.com)

#### Pros

#### Cons
