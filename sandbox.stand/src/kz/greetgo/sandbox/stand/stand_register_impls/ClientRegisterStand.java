package kz.greetgo.sandbox.stand.stand_register_impls;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.ClientListDetails;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.db.stand.beans.StandDb;
import kz.greetgo.sandbox.db.stand.model.ClientDot;

import java.util.ArrayList;
import java.util.List;

@Bean
public class ClientRegisterStand implements ClientRegister {

  public BeanGetter<StandDb> db;

  @Override
  public ClientListDetails getClientList() {
    ClientListDetails clientListDetails=new ClientListDetails();

    db.get().clientStorage.values().forEach(r->
      clientListDetails.clientInfoList.add(ClientRecord
      .newBuilder()
      .setId(r.id)
      .setFio(r.fio)
      .setCharm(r.charm)
      .setAge(r.age)
      .setTotalBalance(r.totalBalance)
      .setMaxBalance(r.maxBalance)
      .setMinBalance(r.minBalance).build()));

    return clientListDetails;
  }
/*
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
  }*/
}
