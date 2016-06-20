package com.yangxiaochen.examples.reveno;

import com.yangxiaochen.examples.reveno.domain.Account;
import com.yangxiaochen.examples.reveno.domain.AccountView;
import lombok.extern.log4j.Log4j2;
import org.reveno.atp.api.Reveno;
import org.reveno.atp.api.dynamic.DynamicCommand;
import org.reveno.atp.core.Engine;

import static org.reveno.atp.utils.MapUtils.map;

/**
 * @author yangxiaochen
 * @date 16/6/17 下午5:53
 */
@Log4j2
public class Main {
    public static void main(String[] args) {
        Reveno reveno = new Engine("/tmp/reveno-sample");

        reveno.domain().viewMapper(Account.class, AccountView.class,
                (id, e, r) -> new AccountView(id, e.name, e.balance));

        DynamicCommand createAccount = reveno.domain()
                .transaction("createAccount", (transaction, context)
                        -> context.repo().store(transaction.id(), new Account(transaction.arg(), 0)))
                .uniqueIdFor(Account.class).command();

        DynamicCommand changeBalance = reveno.domain()
                .transaction("changeBalance", (t, c) -> c.repo().store(t.longArg(), c.repo().get(Account.class, t.arg()).add(t.intArg("inc"))))
                .command();


        reveno.startup();

        long accountId = reveno.executeSync(createAccount, map("name", "John"));
        reveno.executeSync(changeBalance, map("id", accountId, "inc", 10_000));

        log.info("{}, {}", reveno.query().find(AccountView.class, accountId));
        log.info("{}, {}", reveno.query().find(AccountView.class, accountId).name, "John");
        log.info("{}, {}", reveno.query().find(AccountView.class, accountId).balance, 10000);

        reveno.shutdown();



    }
}
