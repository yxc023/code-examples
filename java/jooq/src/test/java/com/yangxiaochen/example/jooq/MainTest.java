package com.yangxiaochen.example.jooq;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.junit.Test;

import java.util.List;

import static org.jooq.impl.DSL.*;

/**
 * @author yangxiaochen
 * @date 2017/12/14 10:46
 */
public class MainTest {

    public static class Foo {
        private Long id;
        private String idx;
        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getIdx() {
            return idx;
        }

        public void setIdx(String idx) {
            this.idx = idx;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Foo{" +
                    "id=" + id +
                    ", idx='" + idx + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Test
    public void test() throws Exception {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setDatabaseName("test");
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("");

        DSLContext create = using(mysqlDataSource, SQLDialect.MYSQL);

        long t = System.currentTimeMillis();
        Result<Record> results = create.select().from("test").fetch();
        System.out.println(System.currentTimeMillis() - t);
        System.out.println(results);
        System.out.println(System.currentTimeMillis() - t);
        List<Foo> fooList =create
                .select().from("test").fetch().into(Foo.class);
        System.out.println(System.currentTimeMillis() - t);

        fooList.forEach(System.out::println);


        System.out.println(
                create.select(sum(field("id", Long.class)), field("idx"))
                .from("test")
                .groupBy(field("idx"))
                .fetch()
        );
    }
}
