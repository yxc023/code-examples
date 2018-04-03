package com.yangxiaochen.example.jooq;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.yangxiaochen.jooq.tables.records.BookRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletionStage;

import static com.yangxiaochen.jooq.Tables.BOOK;
import static com.yangxiaochen.jooq.Tables.LANGUAGE;
import static org.jooq.impl.DSL.using;

/**
 * @author yangxiaochen
 * @Date 2018-04-03
 */
public class JOOQTest {
    DSLContext sql;

    @Before
    public void before() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setDatabaseName("jooq_test");
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("");

        sql = using(mysqlDataSource, SQLDialect.MYSQL);
    }

    @Test
    public void asSqlBuilder() {
        {
            String s = DSL.select(DSL.field("id"), DSL.field("title"), DSL.field("published_in"))
                    .from("book").getSQL();
            System.out.println(s);
        }

        {
            String s = DSL.select()
                    .from("book")
                    .join(DSL.table("language").as("l"))
                    .on("bool.language_id = l.id")
                    .getSQL();
            System.out.println(s);
        }

        {
            String s = DSL.select()
                    .from("book")
                    .join(DSL.table("language").as("l"))
                    .on("bool.language_id = l.id")
                    .where(DSL.field("book.author_id").eq(2))
                    .and("published_in = 1990")
                    .getSQL();
            System.out.println(s);
        }


    }

    @Test
    public void asSqlExecutor() {
        {
            Result<Record3<Object, Object, Object>> result = sql.select(DSL.field("id"), DSL.field("title"), DSL.field("published_in"))
                    .from("book")
                    .fetch();
            System.out.println(result);
            System.out.println(result.get(0).value1());
            System.out.println(result.get(0).value1().getClass());
            System.out.println(result.get(0).component2());
        }


        Result<Record3<Integer, Object, Object>> result2 = sql.select(DSL.field("id", Integer.class), DSL.field("title"), DSL.field("published_in"))
                .from("book")
                .fetch();
        System.out.println(result2);
        System.out.println(result2.get(0).value1());
        System.out.println(result2.get(0).value1().getClass());
        System.out.println(result2.get(0).component2());


        {
            Result<Record3<Object, Object, Object>> result = sql.select(DSL.field("id"), DSL.field("title"), DSL.field("published_in"))
                    .from("book")
                    .where(DSL.field("published_in").eq("1990"))
                    .fetch();
            System.out.println(result);
            System.out.println(result.get(0).value1());
            System.out.println(result.get(0).value1().getClass());
            System.out.println(result.get(0).component2());
        }
    }


    @Test
    public void typesafeSql() {
        SelectQuery query = sql.select(BOOK.ID, BOOK.AUTHOR_ID, LANGUAGE.DESCRIPTION).from(BOOK).join(LANGUAGE)
                .on(BOOK.LANGUAGE_ID.eq(LANGUAGE.ID))
                .where(BOOK.PUBLISHED_IN.eq(1990))
                .getQuery();
        System.out.println(query.getSQL());
        System.out.println(query.getParams());

        query.fetch();

        CompletionStage<Result> resultAsync = query.bind(1, 1988).fetchAsync();
        resultAsync.thenAccept(result -> System.out.println(result));


        SelectQuery cacheQuery = DSL.select(BOOK.ID, BOOK.AUTHOR_ID, LANGUAGE.DESCRIPTION).from(BOOK).join(LANGUAGE)
                .on(BOOK.LANGUAGE_ID.eq(LANGUAGE.ID))
                .where(BOOK.PUBLISHED_IN.eq(1990))
                .getQuery();

        cacheQuery.attach(sql.configuration());
        cacheQuery.fetch();
    }


    @Test
    public void curd() {
        BookRecord bookRecord = sql.selectFrom(BOOK).where(BOOK.ID.eq(4)).fetchOne();
        System.out.println(bookRecord);
        bookRecord.setTitle(bookRecord.getTitle() + "ha");
        bookRecord.store();

        BookRecord bookRecord1 = sql.newRecord(BOOK);
        bookRecord1.setAuthorId(2);
        bookRecord1.setLanguageId(1);
        bookRecord1.setTitle("hello");
//        bookRecord1.setId(10);
        bookRecord1.setPublishedIn(2018);
        bookRecord1.store();

        System.out.println(bookRecord1);
    }


//    @Test
//    public void complex() {
//        sql.insertInto(BOOK).columns(BOOK.ID).values(10).onConflictDoNothing().execute();
//    }


    public static class R {
        public Integer id;
        public Integer authorId;
        public String description;

        @Override
        public String toString() {
            return "R{" +
                    "id=" + id +
                    ", authorId=" + authorId +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
    @Test
    public void map() {

        List<R> r = sql.select(BOOK.ID, BOOK.AUTHOR_ID, LANGUAGE.DESCRIPTION).from(BOOK).join(LANGUAGE)
                .on(BOOK.LANGUAGE_ID.eq(LANGUAGE.ID))
                .where(BOOK.PUBLISHED_IN.eq(1990)).fetch().into(R.class);


        r.forEach(it -> System.out.println(it));

    }


}
