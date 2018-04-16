package com.yangxiaochen.example.jooq;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.yangxiaochen.jooq.tables.daos.BookDao;
import com.yangxiaochen.jooq.tables.pojos.Book;
import com.yangxiaochen.jooq.tables.records.BookRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletionStage;

import static com.yangxiaochen.jooq.Tables.BOOK;
import static com.yangxiaochen.jooq.Tables.LANGUAGE;

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
        sql = DSL.using(mysqlDataSource, SQLDialect.MYSQL);
    }

    @Test
    public void asSqlBuilder() {
        {
            String s = DSL.select(DSL.field("id"), DSL.field("title"), DSL.field("published_in"))
                    .from("book").getSQL();
            System.out.println(s);
        }

        {
            String s = DSL.using(SQLDialect.MYSQL).select()
                    .from("book")
                    .join(DSL.table("language").as("a"))
                    .on("book.language_id = a.id")
                    .getSQL();
            System.out.println(s);
        }

        {
            String s = DSL.using(SQLDialect.MYSQL).select()
                    .from("book")
                    .join(DSL.table("language").as("a"))
                    .on("book.language_id = a.id")
                    .where(DSL.field("book.author_id").eq(2))
                    .and("published_in = 1990")
                    .getSQL();
            System.out.println(s);
        }


    }

    @Test
    public void asSqlExecutor() {
        {
            String s = DSL.using(SQLDialect.MYSQL).select()
                    .from("book")
                    .join(DSL.table("language").as("a"))
                    .on("book.language_id = a.id")
                    .where(DSL.field("book.author_id").eq(2))
                    .and("published_in = 1990")
                    .getSQL();
            sql.execute(s, 2);
            sql.resultQuery(s, 2).fetch();
        }
        {
            SelectQuery query = DSL.using(SQLDialect.MYSQL).select()
                    .from("book")
                    .join(DSL.table("language").as("a"))
                    .on("book.language_id = a.id")
                    .where(DSL.field("book.author_id").eq(2))
                    .and("published_in = 1990")
                    .getQuery();
            query.bind(1, 2);
            query.attach(sql.configuration());
            query.fetch();

        }
//        {
//            Result<Record3<Object, Object, Object>> result = sql.select(DSL.field("id"), DSL.field("title"), DSL.field("published_in"))
//                    .from("book")
//                    .fetch();
//            System.out.println(result);
//            System.out.println(result.get(0).value1());
//            System.out.println(result.get(0).value1().getClass());
//            System.out.println(result.get(0).component2());
//        }
//
//
//        Result<Record3<Integer, Object, Object>> result2 = sql.select(DSL.field("id", Integer.class), DSL.field("title"), DSL.field("published_in"))
//                .from("book")
//                .fetch();
//        System.out.println(result2);
//        System.out.println(result2.get(0).value1());
//        System.out.println(result2.get(0).value1().getClass());
//        System.out.println(result2.get(0).component2());
//
//
//        {
//            Result<Record3<Object, Object, Object>> result = sql.select(DSL.field("id"), DSL.field("title"), DSL.field("published_in"))
//                    .from("book")
//                    .where(DSL.field("published_in").eq("1990"))
//                    .fetch();
//            System.out.println(result);
//            System.out.println(result.get(0).value1());
//            System.out.println(result.get(0).value1().getClass());
//            System.out.println(result.get(0).component2());
//        }
    }


    @Test
    public void typesafeSql() {

        {
            Result<Record3<Integer, Integer, String>> result = sql.select(BOOK.ID, BOOK.AUTHOR_ID, LANGUAGE.DESCRIPTION).from(BOOK).join(LANGUAGE)
                    .on(BOOK.LANGUAGE_ID.eq(LANGUAGE.ID))
                    .where(BOOK.PUBLISHED_IN.eq(1990))
                    .fetch();
            Integer id = result.get(0).getValue(BOOK.ID);
            Integer authorId = result.get(0).get("author_id", Integer.class);
            String desc = result.get(0).value3();
            System.out.println(id + " " + authorId + " " + desc);

        }

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
        bookRecord1.setPublishedIn(2018);
        bookRecord1.store();

        System.out.println(bookRecord1);

        BookDao bookDao = new BookDao(sql.configuration());
        List<Book> books = bookDao.fetchByAuthorId(2);
        System.out.println(books.get(0));
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
    public void mapping() {

        List<R> r = sql.select(BOOK.ID, BOOK.AUTHOR_ID, LANGUAGE.DESCRIPTION).from(BOOK).join(LANGUAGE)
                .on(BOOK.LANGUAGE_ID.eq(LANGUAGE.ID))
                .where(BOOK.PUBLISHED_IN.eq(1990)).fetch().into(R.class);

        r.forEach(it -> System.out.println(it));
    }


}
