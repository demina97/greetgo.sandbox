package kz.greetgo.sandbox.db.stand.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.HasAfterInject;
import kz.greetgo.sandbox.controller.model.AddressType;
import kz.greetgo.sandbox.controller.model.Gender;
import kz.greetgo.sandbox.controller.model.PhoneType;
import kz.greetgo.sandbox.db.stand.model.*;
import org.fest.util.Lists;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Bean
public class StandDb implements HasAfterInject {
  public final Map<String, PersonDot> personStorage = new HashMap<>();
  public final Map<String, ClientDot> clientStorage = new HashMap<>();
  public final Map<String, ClientAddressDot> addressStorage = new HashMap<>();
  public final Map<String, List<ClientAddressDot>> clientAddressMapStorage = new HashMap<>();
  public final Map<String, ClientPhoneDot> phoneStorage = new HashMap<>();
  public final Map<String, List<ClientPhoneDot>> phoneClientMapStorage = new HashMap<>();
  public final Map<String, CharmDot> charmStorage = new HashMap<>();
  public final Map<String, List<ClientDot>> charmClientMapStorage = new HashMap<>();

  public final AtomicInteger clientSeq = new AtomicInteger(1);

  @Override
  public void afterInject() throws Exception {
    try (BufferedReader br = new BufferedReader(
      new InputStreamReader(getClass().getResourceAsStream("StandDbInitData.txt"), "UTF-8"))) {

      int lineNo = 0;

      while (true) {
        String line = br.readLine();
        if (line == null) break;
        lineNo++;
        String trimmedLine = line.trim();
        if (trimmedLine.length() == 0) continue;
        if (trimmedLine.startsWith("#")) continue;

        String[] splitLine = line.split(";");

        String command = splitLine[0].trim();
        switch (command) {
          case "PERSON":
            appendPerson(splitLine, line, lineNo);
            break;

          default:
            throw new RuntimeException("Unknown command " + command);
        }
      }
    }
    prepareData();
  }

  @SuppressWarnings("unused")
  private void appendPerson(String[] splitLine, String line, int lineNo) {
    PersonDot p = new PersonDot();
    p.id = splitLine[1].trim();
    String[] ap = splitLine[2].trim().split("\\s+");
    String[] fio = splitLine[3].trim().split("\\s+");
    p.accountName = ap[0];
    p.password = ap[1];
    p.surname = fio[0];
    p.name = fio[1];
    if (fio.length > 2) p.patronymic = fio[2];
    personStorage.put(p.id, p);
  }

  private void prepareData() {
    charmStorage.put("1", CharmDot.newBuilder().setCharm(1).setName("").setDescription("").setEnergy(1.1f).build());
    charmStorage.put("2", CharmDot.newBuilder().setCharm(2).setName("").setDescription("").setEnergy(1.2f).build());
    charmStorage.put("3", CharmDot.newBuilder().setCharm(3).setName("").setDescription("").setEnergy(1.3f).build());
    charmStorage.put("4", CharmDot.newBuilder().setCharm(4).setName("").setDescription("").setEnergy(1.4f).build());
    charmStorage.put("5", CharmDot.newBuilder().setCharm(5).setName("").setDescription("").setEnergy(1.5f).build());
    int l = clientSeq.getAndIncrement();
    clientStorage.put(l+"", ClientDot.newBuilder()
                              .setId(l)
                              .setSurname("")
                              .setName("")
                              .setPatronymic("")
                              .setGender(Gender.MALE)
                              .setBirth_date(new Date())
                              .setCharm(1)
                              .build());
    addressStorage.put(l+"", ClientAddressDot.newBuilder()
                              .setClient(l)
                              .setType(AddressType.REG)
                              .setStreet("")
                              .setHouse("")
                              .setFlat("").build());
    addressStorage.put(l+"", ClientAddressDot.newBuilder()
                              .setClient(l)
                              .setType(AddressType.FACT)
                              .setStreet("")
                              .setHouse("")
                              .setFlat("").build());
    phoneStorage.put(l+"", ClientPhoneDot.newBuilder().setClient(l).setNumber("").setType(PhoneType.HOME).build());
    phoneStorage.put(l+"", ClientPhoneDot.newBuilder().setClient(l).setNumber("").setType(PhoneType.MOBILE).build());
    phoneStorage.put(l+"", ClientPhoneDot.newBuilder().setClient(l).setNumber("").setType(PhoneType.WORK).build());
    /*List<ClientDot> charmClientList = Lists.newArrayList();
    for (int i = 0; i < 5; i++) {

      ClientDot build = ClientDot
        .newBuilder()
        .setId(l)
        .setSurname("Петров " + i)
        .setName("Семен " + i)
        .setPatronymic("Егорович " + i)
        .setGender(Gender.MALE)
        .setBirth_date(new Date())
        .setCharm(charm).build();

      clientStorage.put(l + "",
        build);
      charmClientList.add(build);
    }
    charmClientMapStorage.put(charm + "", charmClientList);

    List<ClientDot> charmClientList2 = Lists.newArrayList();

    for (ClientDot clientDot : clientStorage.values()) {
      if (clientDot.charm == charm) {
        charmClientList2.add(clientDot);
      }
    }

    List<ClientDot> charmClientList3 = charmClientMapStorage.get(charm + "");*/

  }

}
