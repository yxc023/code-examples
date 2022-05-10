package com.yangxiaochen.example.javers;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.metamodel.annotation.Entity;
import org.javers.core.metamodel.annotation.Id;
import org.javers.core.metamodel.type.JaversType;
import org.javers.core.metamodel.type.ValueObjectType;
import org.javers.repository.sql.ConnectionProvider;
import org.javers.repository.sql.DialectName;
import org.javers.repository.sql.JaversSqlRepository;
import org.javers.repository.sql.SqlRepositoryBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class Main {




    public static void main(String[] args) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/test_javers");
        dataSource.setUser("root");

        ConnectionProvider connectionProvider = new ConnectionProvider() {
            private Connection conn;
            @Override
            public Connection getConnection() {
                if (conn == null) {
                    try {
                        conn = dataSource.getConnection();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return conn;
            }
        };

        JaversSqlRepository sqlRepository = SqlRepositoryBuilder
                .sqlRepository()
                .withSchema("test_javers") //optionally, provide the schame name
                .withConnectionProvider(connectionProvider)
                .withDialect(DialectName.MYSQL).build();


        Javers javers = JaversBuilder.javers().registerJaversRepository(sqlRepository).build();

        Person tommyOld = new Person("tommy", "Tommy Smart");
        Person tommyNew = new Person("tommy", "Tommy C. Smart");


        JaversType type = javers.getTypeMapping(Person.class);
        System.out.println(type.prettyPrint());
        javers.commit("system",tommyOld);


        Diff diff = javers.compare(null, tommyOld);
        Diff diff1 = javers.compare(tommyOld, tommyNew);

        System.out.println(diff.getChanges().get(0).getAffectedGlobalId());
        System.out.println(diff);

        javers.commit("system",tommyOld);
        javers.commit("system",tommyNew);


    }


    @Entity
    public static class Person {
        @Id
        private String name;
        private String fullName;

        public Person(String name, String fullName) {
            this.name = name;
            this.fullName = fullName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return Objects.equals(name, person.name) && Objects.equals(fullName, person.fullName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, fullName);
        }
    }
}
