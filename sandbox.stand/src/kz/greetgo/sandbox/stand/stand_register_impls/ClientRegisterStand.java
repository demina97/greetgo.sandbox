package kz.greetgo.sandbox.stand.stand_register_impls;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.CharmsList;
import kz.greetgo.sandbox.controller.model.ClientListDetails;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.db.stand.beans.StandDb;
import kz.greetgo.sandbox.db.stand.model.CharmDot;
import kz.greetgo.sandbox.db.stand.model.ClientAccountDot;
import kz.greetgo.sandbox.db.stand.model.ClientDot;

import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Bean
public class ClientRegisterStand implements ClientRegister {

  public BeanGetter<StandDb> db;

  @Override
  public ClientListDetails getClientList() {
    ClientListDetails clientListDetails=new ClientListDetails();

    db.get().clientStorage.values().forEach(r -> clientListDetails.clientInfoList.add(ClientRecord.newBuilder()
      .setId(r.id)
      .setFio(r.surname + " " + r.name + " " + r.patronymic)
      .setCharm(db.get().charmStorage.get(r.charm + "").name)
      .setAge((int) Math.floor((new Date().getTime() - r.birth_date.getTime()) / 3.156e+10))
      .setTotalBalance(getTotalBalance(r.id))
      .setMinBalance(db.get().accountClientMapStorage.get(r.id+"")
        .stream().min(ClientAccountDot::compareTo).get().getMoney())
      .setMaxBalance(db.get().accountClientMapStorage.get(r.id+"")
        .stream().max(ClientAccountDot::compareTo).get().getMoney()).build()));

    return clientListDetails;
  }

  @Override
  public CharmsList getCharmsList() {
    CharmsList charmsList = new CharmsList();

    //db.get().charmStorage.values().forEach();
    return null;
  }

  private float getTotalBalance(int id) {
    float result = 0;
    for (ClientAccountDot dot : db.get().accountClientMapStorage.get(id+"")) {
      result += dot.money;
    }

    return result;
  }

}
