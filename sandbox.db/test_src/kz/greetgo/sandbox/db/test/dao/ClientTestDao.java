package kz.greetgo.sandbox.db.test.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface ClientTestDao {
  @Delete("delete from client")
  void deleteAllClient();

  @Insert("insert into client(id, name) values(#{id}, #{name})")
  void insertClient(@Param("id") long id, @Param("name") String name);
}
