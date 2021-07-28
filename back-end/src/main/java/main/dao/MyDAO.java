package main.dao;

import com.fasterxml.jackson.databind.node.ObjectNode;
import main.pojo.Account;
import main.pojo.Caption;
import main.pojo.UserProgress;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface MyDAO {
    @SqlUpdate("insert into caption (eng,chn,se,pos) values (:eng,:chn,:se,:pos)")
    void insertCaption(@Bind("eng") String eng,
                       @Bind("chn") String chn,
                       @Bind("se") String se,
                       @Bind("pos") int pos);

    @SqlQuery("select * from account where username= :username and password= :password")
    @RegisterBeanMapper(Account.class)
    Account getAccount(@Bind("username") String username,
                          @Bind("password") String password);

    @SqlQuery("select * from account where username= :username")
    @RegisterBeanMapper(Account.class)
    Account getAccount(@Bind("username") String username);

    @SqlUpdate("insert into account (username,password) values (:username,:password)")
    void createAccount(@Bind("username") String username,
                          @Bind("password") String password);

    @SqlQuery("select * from user_progress where username= :username")
    @RegisterBeanMapper(UserProgress.class)
    UserProgress getProgress(@Bind("username") String username);

    @SqlQuery("select * from caption where id= :id")
    @RegisterBeanMapper(Caption.class)
    Caption getCaption(@Bind("id")int id);

    @SqlQuery("select * from caption where se= :se limit 1")
    @RegisterBeanMapper(Caption.class)
    Caption getCaption(@Bind("se")String se);

    @SqlUpdate("insert into user_progress (username,last_pos) values (:username,:last_pos) on duplicate key update last_pos= :last_pos")
    void updateProgress(@Bind("username") String username,
                        @Bind("last_pos") int last_pos);
}
