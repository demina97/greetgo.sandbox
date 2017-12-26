package kz.greetgo.sandbox.stand.stand_register_impls;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.register.ClientRegister;

import java.util.ArrayList;
import java.util.List;

@Bean
public class ClientRegisterStand implements ClientRegister {
  @Override
  public List<ClientRecord> list() {
    List<ClientRecord> ret = new ArrayList<>();
    ret.add(record("a10", "Иванов Иван", "Сангвиник", 27, 100000, 150000, 50000));
    ret.add(record("a11", "Петров Иван", "Меланхолик", 45, 200000, 250000, 150000));
    ret.add(record("a12", "Сидоров Иван", "Холерик", 20, 75000, 100000,25000 ));
    ret.add(record("a13", "Абрамов Иван", "Флегматик", 30, 120000, 200000, 2000));
    return ret;
  }

  private ClientRecord record(String id, String fio, String charm, int age, int totalBalance, int maxBalance, int minBalance) {
    ClientRecord ret = new ClientRecord();
    ret.id = id;
    ret.fio = fio;
    ret.charm = charm;
    ret.age = age;
    ret.totalBalance = totalBalance;
    ret.maxBalance = maxBalance;
    ret.minBalance = minBalance;
    return ret;
  }
}
