package kz.greetgo.sandbox.db.configs;


import kz.greetgo.conf.hot.DefaultStrValue;
import kz.greetgo.conf.hot.Description;

@Description("Параметры доступа к БД (используется только БД Postgresql)")
public interface DbConfig {

  @Description("URL доступа к БД")
  @DefaultStrValue("jdbc:postgres://localhost:5432/n_demina")
  String url();

  @Description("Пользователь для доступа к БД")
  @DefaultStrValue("n_demina")
  String username();

  @Description("Пароль для доступа к БД")
  @DefaultStrValue("n_demina")
  String password();
}
