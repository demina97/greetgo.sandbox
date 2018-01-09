package kz.greetgo.sandbox.stand.stand_register_impls;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.db.stand.beans.StandDb;
import kz.greetgo.sandbox.db.stand.model.ClientAccountDot;
import kz.greetgo.sandbox.db.stand.model.ClientAddressDot;
import kz.greetgo.sandbox.db.stand.model.ClientDot;
import kz.greetgo.sandbox.db.stand.model.ClientPhoneDot;
import org.fest.util.Strings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Bean
public class ClientRegisterStand implements ClientRegister {

  public BeanGetter<StandDb> db;

  @Override
  public List<ClientRecord> getClientList(int pageNum, int numOfClients,
                                          String filtrSurname, String filtrName, String filtrPatronymic,
                                          String sortType, int sortDirect) {
    List<ClientRecord> clientList = new ArrayList<>();
    List<ClientRecord> list = new ArrayList<>();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    db.get().clientStorage.values().forEach(r -> {
      try {
        list.add(ClientRecord.newBuilder()
          .setId(r.id)
          .setFio(r.surname + " " + r.name + " " + r.patronymic)
          .setCharm(db.get().charmStorage.get(r.charm + "").name)
          .setAge((int) Math.floor((new Date().getTime() - format.parse(r.birth_date).getTime()) / 3.156e+10))
          .setTotalBalance(getTotalBalance(r.id))
          .setMinBalance(getMinBalance(r.id))
          .setMaxBalance(getMaxBalance(r.id)).build());
      } catch (ParseException e) {
        e.printStackTrace();
      }
    });

    List<ClientRecord> result = list.stream().sorted((c1, c2) -> {
      switch (sortType) {
        case "age":
          return (sortDirect * Integer.compare(c1.age, c2.age));
        case "totalScore":
          return (sortDirect * Float.compare(c1.totalBalance, c2.totalBalance));
        case "maxScore":
          return (sortDirect * Float.compare(c1.maxBalance, c2.maxBalance));
        case "minScore":
          return (sortDirect * Float.compare(c1.minBalance, c2.minBalance));
        default:
          return 0;
      }
    }).filter(clientRecord -> {
      boolean p = true;
      if (!Strings.isNullOrEmpty(filtrSurname)) {
        p = clientRecord.fio.startsWith(filtrSurname);
      }
      if (!Strings.isNullOrEmpty(filtrName)) {
        p = clientRecord.fio.contains(filtrName);
      }
      if (!Strings.isNullOrEmpty(filtrPatronymic)) {
        p = clientRecord.fio.endsWith(filtrPatronymic);
      }
      return p;
    })
      .collect(Collectors.toList());

    for (int i = (pageNum - 1) * numOfClients; i < (pageNum - 1) * numOfClients + numOfClients; i++) {
      if (i < result.size())
        clientList.add(result.get(i));
    }

    return clientList;
  }

  @Override
  public Long getSize(int pageNum, int numOfClients,
                      String filtrSurname, String filtrName, String filtrPatronymic,
                      String sortType, int sortDirect) {
    List<ClientRecord> clientList = new ArrayList<>();
    List<ClientRecord> list = new ArrayList<>();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    db.get().clientStorage.values().forEach(r -> {
      try {
        list.add(ClientRecord.newBuilder()
          .setId(r.id)
          .setFio(r.surname + " " + r.name + " " + r.patronymic)
          .setCharm(db.get().charmStorage.get(r.charm + "").name)
          .setAge((int) Math.floor((new Date().getTime() - format.parse(r.birth_date).getTime()) / 3.156e+10))
          .setTotalBalance(getTotalBalance(r.id))
          .setMinBalance(getMinBalance(r.id))
          .setMaxBalance(getMaxBalance(r.id)).build());
      } catch (ParseException e) {
        e.printStackTrace();
      }
    });

    List<ClientRecord> result = list.stream().sorted((c1, c2) -> {
      switch (sortType) {
        case "age":
          return (sortDirect * Integer.compare(c1.age, c2.age));
        case "totalScore":
          return (sortDirect * Float.compare(c1.totalBalance, c2.totalBalance));
        case "maxScore":
          return (sortDirect * Float.compare(c1.maxBalance, c2.maxBalance));
        case "minScore":
          return (sortDirect * Float.compare(c1.minBalance, c2.minBalance));
        default:
          return 0;
      }
    }).filter(clientRecord -> {
      boolean p = true;
      if (!Strings.isNullOrEmpty(filtrSurname)) {
        p = clientRecord.fio.startsWith(filtrSurname);
      }
      if (!Strings.isNullOrEmpty(filtrName)) {
        p = clientRecord.fio.contains(filtrName);
      }
      if (!Strings.isNullOrEmpty(filtrPatronymic)) {
        p = clientRecord.fio.endsWith(filtrPatronymic);
      }
      return p;
    })
      .collect(Collectors.toList());

    for (int i = (pageNum - 1) * numOfClients; i < (pageNum - 1) * numOfClients + numOfClients; i++) {
      if (i < result.size())
        clientList.add(result.get(i));
    }


      return (long)result.size();
  }

  @Override
  public CharmsList getCharmsList() {
    CharmsList charmsList = new CharmsList();

    db.get().charmStorage.values().forEach(r -> charmsList.listOfCharms.add(CharmModel.newBuilder()
      .setId(r.id)
      .setName(r.name)
      .build()));
    return charmsList;
  }

  @Override
  public ClientDetailsRecord getClientInfo(String clientId) {

    ClientDot r = db.get().clientStorage.get(clientId);

    return ClientDetailsRecord.newBuilder()
      .setId(r.id)
      .setSurname(r.surname)
      .setName(r.name)
      .setPatronymic(r.patronymic)
      .setGender(r.gender)
      .setBirth_date(r.birth_date)
      .setCharm(r.charm)
      .setFactAddrStreet(db.get().addressStorage.get(r.id + 1 + "").street)
      .setFactAddrHouse(db.get().addressStorage.get(r.id + 1 + "").house)
      .setFactAddrFlat(db.get().addressStorage.get(r.id + 1 + "").flat)
      .setRegAddrStreet(db.get().addressStorage.get(r.id + "").street)
      .setRegAddrHouse(db.get().addressStorage.get(r.id + "").house)
      .setRegAddrFlat(db.get().addressStorage.get(r.id + "").flat)
      .setHomePhone(db.get().phoneStorage.get(r.id + "").number)
      .setWorkPhone(db.get().phoneStorage.get(r.id + 1 + "").number)
      .setMobilePhone(db.get().phoneStorage.get(r.id + 2 + "").number).build();
  }

  public ClientDetailsRecord saveClient(ClientDetailsRecord clientDetailsRecord) {
    if (clientDetailsRecord == null)
      return null;

    if (clientDetailsRecord.id == 0) {
      clientDetailsRecord.id = db.get().clientStorage.values().stream().max(ClientDot::compareTo).get().id + 1;
    }

    db.get().clientStorage.put(clientDetailsRecord.id + "", ClientDot.newBuilder()
      .setId(clientDetailsRecord.id)
      .setSurname(clientDetailsRecord.surname)
      .setName(clientDetailsRecord.name)
      .setPatronymic(clientDetailsRecord.patronymic)
      .setGender(clientDetailsRecord.gender)
      .setBirth_date(clientDetailsRecord.birth_date)
      .setCharm(clientDetailsRecord.charm).build());

    db.get().addressStorage.put(clientDetailsRecord.id + 1 + "", ClientAddressDot.newBuilder()
      .setClient(clientDetailsRecord.id)
      .setType(AddressType.FACT)
      .setStreet(clientDetailsRecord.factAddrStreet)
      .setHouse(clientDetailsRecord.factAddrHouse)
      .setFlat(clientDetailsRecord.factAddrFlat).build());

    db.get().addressStorage.put(clientDetailsRecord.id + "", ClientAddressDot.newBuilder()
      .setClient(clientDetailsRecord.id)
      .setType(AddressType.REG)
      .setStreet(clientDetailsRecord.regAddrStreet)
      .setHouse(clientDetailsRecord.regAddrHouse)
      .setFlat(clientDetailsRecord.regAddrFlat).build());

    db.get().phoneStorage.put(clientDetailsRecord.id + "", ClientPhoneDot.newBuilder()
      .setClient(clientDetailsRecord.id)
      .setNumber(clientDetailsRecord.homePhone)
      .setType(PhoneType.HOME).build());

    db.get().phoneStorage.put(clientDetailsRecord.id + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(clientDetailsRecord.id)
      .setNumber(clientDetailsRecord.mobilePhone)
      .setType(PhoneType.MOBILE).build());

    db.get().phoneStorage.put(clientDetailsRecord.id + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(clientDetailsRecord.id)
      .setNumber(clientDetailsRecord.workPhone)
      .setType(PhoneType.WORK).build());

    return getClientInfo(clientDetailsRecord.id + "");
  }

  public ClientDetailsRecord deleteClient(String clientId) {
    db.get().clientStorage.remove(clientId);
    db.get().addressStorage.remove(clientId);
    db.get().addressStorage.remove(String.valueOf(clientId) + 1 + "");
    db.get().phoneStorage.remove(clientId);
    db.get().phoneStorage.remove(String.valueOf(clientId) + 1 + "");
    db.get().phoneStorage.remove(String.valueOf(clientId) + 2 + "");

    return null;
  }

  private float getTotalBalance(int id) {
    float result = 0;
    if (db.get().accountClientMapStorage.containsKey(id + "")) {
      for (ClientAccountDot dot : db.get().accountClientMapStorage.get(id + "")) {
        result += dot.money;
      }
    }
    return result;
  }

  private float getMinBalance(int id) {
    float result = 0;
    if (db.get().accountClientMapStorage.containsKey(id + "")) {
      result = db.get().accountClientMapStorage.get(id + "")
        .stream().min(ClientAccountDot::compareTo).get().getMoney();
    }
    return result;
  }

  private float getMaxBalance(int id) {
    float result = 0;
    if (db.get().accountClientMapStorage.containsKey(id + "")) {
      result = db.get().accountClientMapStorage.get(id + "")
        .stream().max(ClientAccountDot::compareTo).get().getMoney();
    }
    return result;
  }

}
