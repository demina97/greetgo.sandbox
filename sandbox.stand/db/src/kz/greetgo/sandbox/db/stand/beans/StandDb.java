package kz.greetgo.sandbox.db.stand.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.HasAfterInject;
import kz.greetgo.sandbox.controller.model.AddressType;
import kz.greetgo.sandbox.controller.model.Gender;
import kz.greetgo.sandbox.controller.model.PhoneType;
import kz.greetgo.sandbox.db.stand.model.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
  public final Map<String, ClientAccountDot> accountStorage = new HashMap<>();
  public final Map<String, List<ClientAccountDot>> accountClientMapStorage = new HashMap<>();
  public final Map<String, ClientAccountTransactionDot> transactionStorage = new HashMap<>();
  public final Map<String, List<ClientAccountTransactionDot>> transactionClientMapStorage = new HashMap<>();
  public final Map<String, TransactionTypeDot> transactionTypeStorage = new HashMap<>();

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
    charmStorage.put("1", CharmDot.newBuilder()
      .setId(1)
      .setName("Меланхолик")
      .setDescription("Меланхолик - человек легко ранимый, " +
        "склонный к постоянному переживанию различных событий, " +
        "он мало реагирует на внешние факторы.")
      .setEnergy(1.1f).build());
    charmStorage.put("2", CharmDot.newBuilder()
      .setId(2)
      .setName("Сангвиник")
      .setDescription("Сангвиник - живой, горячий, подвижный человек, " +
        "с частой сменой настроения, впечатлений, с быстрой реакцией на все события, " +
        "происходящие вокруг него, довольно легко примиряющийся " +
        "со своими неудачами и неприятностями.")
      .setEnergy(1.2f).build());
    charmStorage.put("3", CharmDot.newBuilder()
      .setId(3)
      .setName("Флегматик")
      .setDescription("Флегматик неспешен, невозмутим, " +
        "имеет устойчивые стремления и настроение, внешне скуп на проявление эмоций и чувств.")
      .setEnergy(1.3f).build());
    charmStorage.put("4", CharmDot.newBuilder()
      .setId(4)
      .setName("Холерик")
      .setDescription("Холерик - быстрый, страстный," +
        " порывистый, однако совершенно неуравновешенный, " +
        "с резко меняющимся настроением с эмоциональными вспышками, быстро истощаемый. ")
      .setEnergy(1.4f).build());

    transactionTypeStorage.put("11", TransactionTypeDot.newBuilder().setId(11).setName("").setCode("").build());
    transactionTypeStorage.put("22", TransactionTypeDot.newBuilder().setId(11).setName("").setCode("").build());
    transactionTypeStorage.put("33", TransactionTypeDot.newBuilder().setId(11).setName("").setCode("").build());
    transactionTypeStorage.put("44", TransactionTypeDot.newBuilder().setId(11).setName("").setCode("").build());
    transactionTypeStorage.put("55", TransactionTypeDot.newBuilder().setId(11).setName("").setCode("").build());

    int l = clientSeq.getAndIncrement();
    clientStorage.put(l + "", ClientDot.newBuilder()
      .setId(l)
      .setSurname("Петров")
      .setName("Иван")
      .setPatronymic("Иванович")
      .setGender(Gender.MALE)
      .setBirth_date(new Date(100_000_000_000L))
      .setCharm(1)
      .build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.REG)
      .setStreet("")
      .setHouse("")
      .setFlat("").build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.FACT)
      .setStreet("")
      .setHouse("")
      .setFlat("").build());
    phoneStorage.put(l + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("")
      .setType(PhoneType.HOME).build());
    phoneStorage.put(l + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("")
      .setType(PhoneType.MOBILE).build());
    phoneStorage.put(l + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("")
      .setType(PhoneType.WORK).build());
    accountStorage.put(l + "", ClientAccountDot.newBuilder()
      .setId(100)
      .setClient(l)
      .setMoney(120_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 1 + "", ClientAccountDot.newBuilder()
      .setId(101)
      .setClient(l)
      .setMoney(3_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 2 + "", ClientAccountDot.newBuilder()
      .setId(102)
      .setClient(l)
      .setMoney(75_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    transactionStorage.put(l + "", ClientAccountTransactionDot.newBuilder()
      .setId(10)
      .setAccount(100)
      .setMoney(0.0f)
      .setFinished_at(new Timestamp(0L))
      .setType(11).build());
    Collection<ClientAccountDot> values = accountStorage.values();
    List<ClientAccountDot> accountsForClients = new ArrayList<>();
    for (ClientAccountDot value : values) {
      if (value.client == l)
        accountsForClients.add(value);
    }

    accountClientMapStorage.put(l+"", accountsForClients);

    /*float sum = 0;
    for(int i = 0; i < accountClientList.size(); i++)
      sum += accountClientList.get(i).money;
    */

    //accountClientMapStorage.put(l+"", )
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
