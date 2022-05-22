package com.yangxiaochen.example.javers;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Change;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.core.metamodel.annotation.Entity;
import org.javers.core.metamodel.annotation.Id;
import org.javers.core.metamodel.annotation.Value;
import org.javers.core.metamodel.annotation.ValueObject;
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

        System.out.println(javers.commit("system", tommyOld));
        System.out.println(javers.commit("system", tommyNew));
        tommyNew.setFullName("123");
        System.out.println(javers.commit("system", tommyNew));
        System.out.println(javers.commit("system", tommyNew));

        tommyNew.info.k = 13;
        javers.commit("system", tommyNew);

        tommyNew.v.v = "value2";

        javers.commit("system", tommyNew);

        Diff diff2 = javers.compare(tommyOld, tommyNew);
        for (Change change : diff2.getChanges()) {
            System.out.println(change.getClass());
            System.out.println(change);
            if (change instanceof ValueChange) {
                System.out.println(((ValueChange) change).getPropertyName());
            }
        }

    }


    @Entity
    public static class Person {
        @Id
        private String name;
        private String fullName;
        private Info info;
        private ValueObjectO v;

        public Person(String name, String fullName) {
            this.name = name;
            this.fullName = fullName;
            this.info = new Info(12);
            this.v = new ValueObjectO("value1");
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

        public Info getInfo() {
            return info;
        }

        public void setInfo(Info info) {
            this.info = info;
        }

        public ValueObjectO getV() {
            return v;
        }

        public void setV(ValueObjectO v) {
            this.v = v;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return Objects.equals(name, person.name) && Objects.equals(fullName, person.fullName) && Objects.equals(info, person.info) && Objects.equals(v, person.v);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, fullName, info, v);
        }
    }

    @Entity
    public static class Info {
        @Id
        private int k;

        public Info(int k) {
            this.k = k;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Info info = (Info) o;
            return k == info.k;
        }

        @Override
        public int hashCode() {
            return Objects.hash(k);
        }
    }

    @Value
    public static class ValueObjectO {
        public String v;

        public ValueObjectO(String v) {
            this.v = v;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ValueObjectO that = (ValueObjectO) o;
            return Objects.equals(v, that.v);
        }

        @Override
        public int hashCode() {
            return Objects.hash(v);
        }
    }
}
