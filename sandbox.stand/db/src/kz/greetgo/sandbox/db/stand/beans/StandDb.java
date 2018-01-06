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
import java.text.ParseException;
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

  private void prepareData() throws ParseException {
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
      .setBirth_date("1995-05-12")
      .setCharm(1)
      .build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.REG)
      .setStreet("Абая")
      .setHouse("127")
      .setFlat("15").build());
    addressStorage.put(l + 1 + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.FACT)
      .setStreet("Кунаева")
      .setHouse("3")
      .setFlat("10").build());
    phoneStorage.put(l + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87275364757")
      .setType(PhoneType.HOME).build());
    phoneStorage.put(l + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87057781281")
      .setType(PhoneType.MOBILE).build());
    phoneStorage.put(l + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87025412113")
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
    accountClientMapStorage.put(l + "", accountsForClients);

    l = clientSeq.getAndIncrement();

    clientStorage.put(l + "", ClientDot.newBuilder()
      .setId(l)
      .setSurname("Иванов")
      .setName("Петр")
      .setPatronymic("Петрович")
      .setGender(Gender.MALE)
      .setBirth_date("1991-12-12")
      .setCharm(4)
      .build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.REG)
      .setStreet("Байтурсынова")
      .setHouse("126")
      .setFlat("13").build());
    addressStorage.put(l + 1 + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.FACT)
      .setStreet("Байтурсынова")
      .setHouse("126")
      .setFlat("13").build());
    phoneStorage.put(l + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87275363434")
      .setType(PhoneType.HOME).build());
    phoneStorage.put(l + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87057781212")
      .setType(PhoneType.MOBILE).build());
    phoneStorage.put(l + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87025413113")
      .setType(PhoneType.WORK).build());
    accountStorage.put(l + "", ClientAccountDot.newBuilder()
      .setId(200)
      .setClient(l)
      .setMoney(150_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 1 + "", ClientAccountDot.newBuilder()
      .setId(201)
      .setClient(l)
      .setMoney(5_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 2 + "", ClientAccountDot.newBuilder()
      .setId(202)
      .setClient(l)
      .setMoney(55_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    transactionStorage.put(l + "", ClientAccountTransactionDot.newBuilder()
      .setId(10)
      .setAccount(100)
      .setMoney(0.0f)
      .setFinished_at(new Timestamp(0L))
      .setType(11).build());
    Collection<ClientAccountDot> values2 = accountStorage.values();
    List<ClientAccountDot> accountsForClients2 = new ArrayList<>();
    for (ClientAccountDot value : values2) {
      if (value.client == l)
        accountsForClients2.add(value);
    }
    accountClientMapStorage.put(l + "", accountsForClients2);

    l = clientSeq.getAndIncrement();

    clientStorage.put(l + "", ClientDot.newBuilder()
      .setId(l)
      .setSurname("Семенов")
      .setName("Иван")
      .setPatronymic("Петрович")
      .setGender(Gender.MALE)
      .setBirth_date("1980-11-23")
      .setCharm(3)
      .build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.REG)
      .setStreet("Раимбека")
      .setHouse("113")
      .setFlat("13").build());
    addressStorage.put(l + 1 + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.FACT)
      .setStreet("Ауэзова")
      .setHouse("11")
      .setFlat("12").build());
    phoneStorage.put(l + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87275364343")
      .setType(PhoneType.HOME).build());
    phoneStorage.put(l + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87057785454")
      .setType(PhoneType.MOBILE).build());
    phoneStorage.put(l + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87025416666")
      .setType(PhoneType.WORK).build());
    accountStorage.put(l + "", ClientAccountDot.newBuilder()
      .setId(300)
      .setClient(l)
      .setMoney(200_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 1 + "", ClientAccountDot.newBuilder()
      .setId(301)
      .setClient(l)
      .setMoney(7_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 2 + "", ClientAccountDot.newBuilder()
      .setId(302)
      .setClient(l)
      .setMoney(70_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    transactionStorage.put(l + "", ClientAccountTransactionDot.newBuilder()
      .setId(10)
      .setAccount(100)
      .setMoney(0.0f)
      .setFinished_at(new Timestamp(0L))
      .setType(11).build());
    Collection<ClientAccountDot> values3 = accountStorage.values();
    List<ClientAccountDot> accountsForClients3 = new ArrayList<>();
    for (ClientAccountDot value : values3) {
      if (value.client == l)
        accountsForClients3.add(value);
    }
    accountClientMapStorage.put(l + "", accountsForClients3);

    l = clientSeq.getAndIncrement();

    clientStorage.put(l + "", ClientDot.newBuilder()
      .setId(l)
      .setSurname("Семенов")
      .setName("Федор")
      .setPatronymic("Борисович")
      .setGender(Gender.MALE)
      .setBirth_date("1967-03-20")
      .setCharm(2)
      .build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.REG)
      .setStreet("Абая")
      .setHouse("11")
      .setFlat("1").build());
    addressStorage.put(l + 1 + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.FACT)
      .setStreet("Ауэзова")
      .setHouse("16")
      .setFlat("125").build());
    phoneStorage.put(l + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87275364343")
      .setType(PhoneType.HOME).build());
    phoneStorage.put(l + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87057782222")
      .setType(PhoneType.MOBILE).build());
    phoneStorage.put(l + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87025411232")
      .setType(PhoneType.WORK).build());
    accountStorage.put(l + "", ClientAccountDot.newBuilder()
      .setId(400)
      .setClient(l)
      .setMoney(250_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 1 + "", ClientAccountDot.newBuilder()
      .setId(401)
      .setClient(l)
      .setMoney(1_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 2 + "", ClientAccountDot.newBuilder()
      .setId(402)
      .setClient(l)
      .setMoney(90_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    transactionStorage.put(l + "", ClientAccountTransactionDot.newBuilder()
      .setId(10)
      .setAccount(100)
      .setMoney(0.0f)
      .setFinished_at(new Timestamp(0L))
      .setType(11).build());
    Collection<ClientAccountDot> values4 = accountStorage.values();
    List<ClientAccountDot> accountsForClients4 = new ArrayList<>();
    for (ClientAccountDot value : values4) {
      if (value.client == l)
        accountsForClients4.add(value);
    }
    accountClientMapStorage.put(l + "", accountsForClients4);

    l = clientSeq.getAndIncrement();

    clientStorage.put(l + "", ClientDot.newBuilder()
      .setId(l)
      .setSurname("Федоров")
      .setName("Борис")
      .setPatronymic("Борисович")
      .setGender(Gender.MALE)
      .setBirth_date("1975-05-22")
      .setCharm(2)
      .build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.REG)
      .setStreet("Абая")
      .setHouse("11")
      .setFlat("119").build());
    addressStorage.put(l + 1 + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.FACT)
      .setStreet("Кунаева")
      .setHouse("112")
      .setFlat("19").build());
    phoneStorage.put(l + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87275362332")
      .setType(PhoneType.HOME).build());
    phoneStorage.put(l + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87057784545")
      .setType(PhoneType.MOBILE).build());
    phoneStorage.put(l + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87025415471")
      .setType(PhoneType.WORK).build());
    accountStorage.put(l + "", ClientAccountDot.newBuilder()
      .setId(500)
      .setClient(l)
      .setMoney(350_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 1 + "", ClientAccountDot.newBuilder()
      .setId(501)
      .setClient(l)
      .setMoney(20_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 2 + "", ClientAccountDot.newBuilder()
      .setId(502)
      .setClient(l)
      .setMoney(100_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    transactionStorage.put(l + "", ClientAccountTransactionDot.newBuilder()
      .setId(10)
      .setAccount(100)
      .setMoney(0.0f)
      .setFinished_at(new Timestamp(0L))
      .setType(11).build());
    Collection<ClientAccountDot> values5 = accountStorage.values();
    List<ClientAccountDot> accountsForClients5 = new ArrayList<>();
    for (ClientAccountDot value : values5) {
      if (value.client == l)
        accountsForClients5.add(value);
    }
    accountClientMapStorage.put(l + "", accountsForClients5);

    l = clientSeq.getAndIncrement();

    clientStorage.put(l + "", ClientDot.newBuilder()
      .setId(l)
      .setSurname("Михайлова")
      .setName("Дарья")
      .setPatronymic("Борисовна")
      .setGender(Gender.FEMALE)
      .setBirth_date("1977-07-17")
      .setCharm(3)
      .build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.REG)
      .setStreet("Фурманова")
      .setHouse("71")
      .setFlat("7").build());
    addressStorage.put(l + 1 + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.FACT)
      .setStreet("Кунаева")
      .setHouse("112")
      .setFlat("35").build());
    phoneStorage.put(l + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87275232455")
      .setType(PhoneType.HOME).build());
    phoneStorage.put(l + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87057676799")
      .setType(PhoneType.MOBILE).build());
    phoneStorage.put(l + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87025213711")
      .setType(PhoneType.WORK).build());
    accountStorage.put(l + "", ClientAccountDot.newBuilder()
      .setId(600)
      .setClient(l)
      .setMoney(240_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 1 + "", ClientAccountDot.newBuilder()
      .setId(601)
      .setClient(l)
      .setMoney(10_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 2 + "", ClientAccountDot.newBuilder()
      .setId(602)
      .setClient(l)
      .setMoney(95_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    transactionStorage.put(l + "", ClientAccountTransactionDot.newBuilder()
      .setId(10)
      .setAccount(100)
      .setMoney(0.0f)
      .setFinished_at(new Timestamp(0L))
      .setType(11).build());
    Collection<ClientAccountDot> values6 = accountStorage.values();
    List<ClientAccountDot> accountsForClients6 = new ArrayList<>();
    for (ClientAccountDot value : values6) {
      if (value.client == l)
        accountsForClients6.add(value);
    }
    accountClientMapStorage.put(l + "", accountsForClients6);

    l = clientSeq.getAndIncrement();

    clientStorage.put(l + "", ClientDot.newBuilder()
      .setId(l)
      .setSurname("Антонова")
      .setName("Роза")
      .setPatronymic("Владимировна")
      .setGender(Gender.FEMALE)
      .setBirth_date("1986-09-09")
      .setCharm(3)
      .build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.REG)
      .setStreet("Калдаякова")
      .setHouse("96")
      .setFlat("17").build());
    addressStorage.put(l + 1 + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.FACT)
      .setStreet("Кунаева")
      .setHouse("112")
      .setFlat("12").build());
    phoneStorage.put(l + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87275214231")
      .setType(PhoneType.HOME).build());
    phoneStorage.put(l + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87057678897")
      .setType(PhoneType.MOBILE).build());
    phoneStorage.put(l + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87025144571")
      .setType(PhoneType.WORK).build());
    accountStorage.put(l + "", ClientAccountDot.newBuilder()
      .setId(700)
      .setClient(l)
      .setMoney(200_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 1 + "", ClientAccountDot.newBuilder()
      .setId(701)
      .setClient(l)
      .setMoney(4_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 2 + "", ClientAccountDot.newBuilder()
      .setId(702)
      .setClient(l)
      .setMoney(95_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    transactionStorage.put(l + "", ClientAccountTransactionDot.newBuilder()
      .setId(10)
      .setAccount(100)
      .setMoney(0.0f)
      .setFinished_at(new Timestamp(0L))
      .setType(11).build());
    Collection<ClientAccountDot> values7 = accountStorage.values();
    List<ClientAccountDot> accountsForClients7 = new ArrayList<>();
    for (ClientAccountDot value : values7) {
      if (value.client == l)
        accountsForClients7.add(value);
    }
    accountClientMapStorage.put(l + "", accountsForClients7);

    l = clientSeq.getAndIncrement();

    clientStorage.put(l + "", ClientDot.newBuilder()
      .setId(l)
      .setSurname("Сергеева")
      .setName("Анна")
      .setPatronymic("Владимировна")
      .setGender(Gender.FEMALE)
      .setBirth_date("1997-12-18")
      .setCharm(4)
      .build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.REG)
      .setStreet("Блока")
      .setHouse("12")
      .setFlat("1").build());
    addressStorage.put(l + 1 + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.FACT)
      .setStreet("Кунаева")
      .setHouse("113")
      .setFlat("14").build());
    phoneStorage.put(l + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87275233177")
      .setType(PhoneType.HOME).build());
    phoneStorage.put(l + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87057623131")
      .setType(PhoneType.MOBILE).build());
    phoneStorage.put(l + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87025144246")
      .setType(PhoneType.WORK).build());
    accountStorage.put(l + "", ClientAccountDot.newBuilder()
      .setId(800)
      .setClient(l)
      .setMoney(210_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 1 + "", ClientAccountDot.newBuilder()
      .setId(801)
      .setClient(l)
      .setMoney(6_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 2 + "", ClientAccountDot.newBuilder()
      .setId(802)
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
    Collection<ClientAccountDot> values8 = accountStorage.values();
    List<ClientAccountDot> accountsForClients8 = new ArrayList<>();
    for (ClientAccountDot value : values8) {
      if (value.client == l)
        accountsForClients8.add(value);
    }
    accountClientMapStorage.put(l + "", accountsForClients8);

    l = clientSeq.getAndIncrement();

    clientStorage.put(l + "", ClientDot.newBuilder()
      .setId(l)
      .setSurname("Юрьева")
      .setName("Лидия")
      .setPatronymic("Дмитриевна")
      .setGender(Gender.FEMALE)
      .setBirth_date("1984-12-02")
      .setCharm(1)
      .build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.REG)
      .setStreet("Блока")
      .setHouse("12")
      .setFlat("1").build());
    addressStorage.put(l + 1 + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.FACT)
      .setStreet("Лермонтова")
      .setHouse("113")
      .setFlat("7").build());
    phoneStorage.put(l + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87275237788")
      .setType(PhoneType.HOME).build());
    phoneStorage.put(l + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87057628787")
      .setType(PhoneType.MOBILE).build());
    phoneStorage.put(l + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87025147878")
      .setType(PhoneType.WORK).build());
    accountStorage.put(l + "", ClientAccountDot.newBuilder()
      .setId(900)
      .setClient(l)
      .setMoney(250_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 1 + "", ClientAccountDot.newBuilder()
      .setId(901)
      .setClient(l)
      .setMoney(12_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 2 + "", ClientAccountDot.newBuilder()
      .setId(902)
      .setClient(l)
      .setMoney(70_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    transactionStorage.put(l + "", ClientAccountTransactionDot.newBuilder()
      .setId(10)
      .setAccount(100)
      .setMoney(0.0f)
      .setFinished_at(new Timestamp(0L))
      .setType(11).build());
    Collection<ClientAccountDot> values9 = accountStorage.values();
    List<ClientAccountDot> accountsForClients9 = new ArrayList<>();
    for (ClientAccountDot value : values9) {
      if (value.client == l)
        accountsForClients9.add(value);
    }
    accountClientMapStorage.put(l + "", accountsForClients9);

    l = clientSeq.getAndIncrement();

    clientStorage.put(l + "", ClientDot.newBuilder()
      .setId(l)
      .setSurname("Несторова")
      .setName("Нина")
      .setPatronymic("Дмитриевна")
      .setGender(Gender.FEMALE)
      .setBirth_date("1993-04-02")
      .setCharm(2)
      .build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.REG)
      .setStreet("Гоголя")
      .setHouse("171")
      .setFlat("15").build());
    addressStorage.put(l + 1 + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.FACT)
      .setStreet("Лермонтова")
      .setHouse("13")
      .setFlat("17").build());
    phoneStorage.put(l + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87275231336")
      .setType(PhoneType.HOME).build());
    phoneStorage.put(l + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87057622717")
      .setType(PhoneType.MOBILE).build());
    phoneStorage.put(l + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87025147375")
      .setType(PhoneType.WORK).build());
    accountStorage.put(l + "", ClientAccountDot.newBuilder()
      .setId(1000)
      .setClient(l)
      .setMoney(420_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 1 + "", ClientAccountDot.newBuilder()
      .setId(1001)
      .setClient(l)
      .setMoney(32_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 2 + "", ClientAccountDot.newBuilder()
      .setId(1002)
      .setClient(l)
      .setMoney(120_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    transactionStorage.put(l + "", ClientAccountTransactionDot.newBuilder()
      .setId(10)
      .setAccount(100)
      .setMoney(0.0f)
      .setFinished_at(new Timestamp(0L))
      .setType(11).build());
    Collection<ClientAccountDot> values10 = accountStorage.values();
    List<ClientAccountDot> accountsForClients10 = new ArrayList<>();
    for (ClientAccountDot value : values10) {
      if (value.client == l)
        accountsForClients10.add(value);
    }
    accountClientMapStorage.put(l + "", accountsForClients10);

    l = clientSeq.getAndIncrement();

    clientStorage.put(l + "", ClientDot.newBuilder()
      .setId(l)
      .setSurname("Григорьев")
      .setName("Антон")
      .setPatronymic("Владимирович")
      .setGender(Gender.
        MALE)
      .setBirth_date("1976-08-13")
      .setCharm(2)
      .build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.REG)
      .setStreet("Тимирязева")
      .setHouse("155")
      .setFlat("12").build());
    addressStorage.put(l + 1 + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.FACT)
      .setStreet("Абая")
      .setHouse("133")
      .setFlat("22").build());
    phoneStorage.put(l + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87275234562")
      .setType(PhoneType.HOME).build());
    phoneStorage.put(l + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87057627217")
      .setType(PhoneType.MOBILE).build());
    phoneStorage.put(l + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87025147854")
      .setType(PhoneType.WORK).build());
    accountStorage.put(l + "", ClientAccountDot.newBuilder()
      .setId(1100)
      .setClient(l)
      .setMoney(230_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 1 + "", ClientAccountDot.newBuilder()
      .setId(1101)
      .setClient(l)
      .setMoney(24_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 2 + "", ClientAccountDot.newBuilder()
      .setId(1102)
      .setClient(l)
      .setMoney(170_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    transactionStorage.put(l + "", ClientAccountTransactionDot.newBuilder()
      .setId(10)
      .setAccount(100)
      .setMoney(0.0f)
      .setFinished_at(new Timestamp(0L))
      .setType(11).build());
    Collection<ClientAccountDot> values11 = accountStorage.values();
    List<ClientAccountDot> accountsForClients11 = new ArrayList<>();
    for (ClientAccountDot value : values11) {
      if (value.client == l)
        accountsForClients11.add(value);
    }
    accountClientMapStorage.put(l + "", accountsForClients11);

    l = clientSeq.getAndIncrement();

    clientStorage.put(l + "", ClientDot.newBuilder()
      .setId(l)
      .setSurname("Федоров")
      .setName("Владимир")
      .setPatronymic("Васильевич")
      .setGender(Gender.MALE)
      .setBirth_date("1971-02-19")
      .setCharm(1)
      .build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.REG)
      .setStreet("Тимирязева")
      .setHouse("155")
      .setFlat("12").build());
    addressStorage.put(l + 1 + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.FACT)
      .setStreet("Абая")
      .setHouse("133")
      .setFlat("22").build());
    phoneStorage.put(l + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87275233454")
      .setType(PhoneType.HOME).build());
    phoneStorage.put(l + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87057622354")
      .setType(PhoneType.MOBILE).build());
    phoneStorage.put(l + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87025141254")
      .setType(PhoneType.WORK).build());
    accountStorage.put(l + "", ClientAccountDot.newBuilder()
      .setId(1200)
      .setClient(l)
      .setMoney(330_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 1 + "", ClientAccountDot.newBuilder()
      .setId(1201)
      .setClient(l)
      .setMoney(22_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 2 + "", ClientAccountDot.newBuilder()
      .setId(1202)
      .setClient(l)
      .setMoney(130_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    transactionStorage.put(l + "", ClientAccountTransactionDot.newBuilder()
      .setId(10)
      .setAccount(100)
      .setMoney(0.0f)
      .setFinished_at(new Timestamp(0L))
      .setType(11).build());
    Collection<ClientAccountDot> values12 = accountStorage.values();
    List<ClientAccountDot> accountsForClients12 = new ArrayList<>();
    for (ClientAccountDot value : values12) {
      if (value.client == l)
        accountsForClients12.add(value);
    }
    accountClientMapStorage.put(l + "", accountsForClients12);

    l = clientSeq.getAndIncrement();

    clientStorage.put(l + "", ClientDot.newBuilder()
      .setId(l)
      .setSurname("Шведова")
      .setName("Екатерина")
      .setPatronymic("Александровна")
      .setGender(Gender.FEMALE)
      .setBirth_date("1992-05-15")
      .setCharm(3)
      .build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.REG)
      .setStreet("Тимирязева")
      .setHouse("155")
      .setFlat("12").build());
    addressStorage.put(l + 1 + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.FACT)
      .setStreet("Абая")
      .setHouse("133")
      .setFlat("22").build());
    phoneStorage.put(l + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87275238189")
      .setType(PhoneType.HOME).build());
    phoneStorage.put(l + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87057628189")
      .setType(PhoneType.MOBILE).build());
    phoneStorage.put(l + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87025148989")
      .setType(PhoneType.WORK).build());
    accountStorage.put(l + "", ClientAccountDot.newBuilder()
      .setId(1300)
      .setClient(l)
      .setMoney(270_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 1 + "", ClientAccountDot.newBuilder()
      .setId(1301)
      .setClient(l)
      .setMoney(17_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 2 + "", ClientAccountDot.newBuilder()
      .setId(1302)
      .setClient(l)
      .setMoney(110_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    transactionStorage.put(l + "", ClientAccountTransactionDot.newBuilder()
      .setId(10)
      .setAccount(100)
      .setMoney(0.0f)
      .setFinished_at(new Timestamp(0L))
      .setType(11).build());
    Collection<ClientAccountDot> values13 = accountStorage.values();
    List<ClientAccountDot> accountsForClients13 = new ArrayList<>();
    for (ClientAccountDot value : values13) {
      if (value.client == l)
        accountsForClients13.add(value);
    }
    accountClientMapStorage.put(l + "", accountsForClients13);

    l = clientSeq.getAndIncrement();

    clientStorage.put(l + "", ClientDot.newBuilder()
      .setId(l)
      .setSurname("Шведова")
      .setName("Екатерина")
      .setPatronymic("Александровна")
      .setGender(Gender.FEMALE)
      .setBirth_date("1971-02-19")
      .setCharm(4)
      .build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.REG)
      .setStreet("Тимирязева")
      .setHouse("155")
      .setFlat("12").build());
    addressStorage.put(l + 1 + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.FACT)
      .setStreet("Абая")
      .setHouse("133")
      .setFlat("22").build());
    phoneStorage.put(l + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87275238189")
      .setType(PhoneType.HOME).build());
    phoneStorage.put(l + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87057628189")
      .setType(PhoneType.MOBILE).build());
    phoneStorage.put(l + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87025148989")
      .setType(PhoneType.WORK).build());
    accountStorage.put(l + "", ClientAccountDot.newBuilder()
      .setId(1400)
      .setClient(l)
      .setMoney(540_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 1 + "", ClientAccountDot.newBuilder()
      .setId(1401)
      .setClient(l)
      .setMoney(74_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 2 + "", ClientAccountDot.newBuilder()
      .setId(1402)
      .setClient(l)
      .setMoney(270_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    transactionStorage.put(l + "", ClientAccountTransactionDot.newBuilder()
      .setId(10)
      .setAccount(100)
      .setMoney(0.0f)
      .setFinished_at(new Timestamp(0L))
      .setType(11).build());
    Collection<ClientAccountDot> values14 = accountStorage.values();
    List<ClientAccountDot> accountsForClients14 = new ArrayList<>();
    for (ClientAccountDot value : values14) {
      if (value.client == l)
        accountsForClients14.add(value);
    }
    accountClientMapStorage.put(l + "", accountsForClients14);

    l = clientSeq.getAndIncrement();

    clientStorage.put(l + "", ClientDot.newBuilder()
      .setId(l)
      .setSurname("Семенов")
      .setName("Андрей")
      .setPatronymic("Сергеевич")
      .setGender(Gender.MALE)
      .setBirth_date("1983-10-15")
      .setCharm(3)
      .build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.REG)
      .setStreet("Тимирязева")
      .setHouse("155")
      .setFlat("12").build());
    addressStorage.put(l + 1 + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.FACT)
      .setStreet("Абая")
      .setHouse("133")
      .setFlat("22").build());
    phoneStorage.put(l + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87275238189")
      .setType(PhoneType.HOME).build());
    phoneStorage.put(l + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87057628189")
      .setType(PhoneType.MOBILE).build());
    phoneStorage.put(l + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87025148989")
      .setType(PhoneType.WORK).build());
    accountStorage.put(l + "", ClientAccountDot.newBuilder()
      .setId(1500)
      .setClient(l)
      .setMoney(440_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 1 + "", ClientAccountDot.newBuilder()
      .setId(1501)
      .setClient(l)
      .setMoney(64_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 2 + "", ClientAccountDot.newBuilder()
      .setId(1502)
      .setClient(l)
      .setMoney(170_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    transactionStorage.put(l + "", ClientAccountTransactionDot.newBuilder()
      .setId(10)
      .setAccount(100)
      .setMoney(0.0f)
      .setFinished_at(new Timestamp(0L))
      .setType(11).build());
    Collection<ClientAccountDot> values15 = accountStorage.values();
    List<ClientAccountDot> accountsForClients15 = new ArrayList<>();
    for (ClientAccountDot value : values15) {
      if (value.client == l)
        accountsForClients15.add(value);
    }
    accountClientMapStorage.put(l + "", accountsForClients15);

    l = clientSeq.getAndIncrement();

    clientStorage.put(l + "", ClientDot.newBuilder()
      .setId(l)
      .setSurname("Алексеева")
      .setName("Тамара")
      .setPatronymic("Сергеевна")
      .setGender(Gender.FEMALE)
      .setBirth_date("1997-09-02")
      .setCharm(1)
      .build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.REG)
      .setStreet("Тимирязева")
      .setHouse("155")
      .setFlat("12").build());
    addressStorage.put(l + 1 + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.FACT)
      .setStreet("Абая")
      .setHouse("133")
      .setFlat("22").build());
    phoneStorage.put(l + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87275238189")
      .setType(PhoneType.HOME).build());
    phoneStorage.put(l + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87057628189")
      .setType(PhoneType.MOBILE).build());
    phoneStorage.put(l + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87025148989")
      .setType(PhoneType.WORK).build());
    accountStorage.put(l + "", ClientAccountDot.newBuilder()
      .setId(1600)
      .setClient(l)
      .setMoney(330_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 1 + "", ClientAccountDot.newBuilder()
      .setId(1601)
      .setClient(l)
      .setMoney(54_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 2 + "", ClientAccountDot.newBuilder()
      .setId(1602)
      .setClient(l)
      .setMoney(150_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    transactionStorage.put(l + "", ClientAccountTransactionDot.newBuilder()
      .setId(10)
      .setAccount(100)
      .setMoney(0.0f)
      .setFinished_at(new Timestamp(0L))
      .setType(11).build());
    Collection<ClientAccountDot> values16 = accountStorage.values();
    List<ClientAccountDot> accountsForClients16 = new ArrayList<>();
    for (ClientAccountDot value : values16) {
      if (value.client == l)
        accountsForClients16.add(value);
    }
    accountClientMapStorage.put(l + "", accountsForClients16);

    l = clientSeq.getAndIncrement();

    clientStorage.put(l + "", ClientDot.newBuilder()
      .setId(l)
      .setSurname("Зайцев")
      .setName("Александр")
      .setPatronymic("Романович")
      .setGender(Gender.MALE)
      .setBirth_date("1994-10-02")
      .setCharm(1)
      .build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.REG)
      .setStreet("Тимирязева")
      .setHouse("155")
      .setFlat("12").build());
    addressStorage.put(l + 1 + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.FACT)
      .setStreet("Абая")
      .setHouse("133")
      .setFlat("22").build());
    phoneStorage.put(l + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87275238189")
      .setType(PhoneType.HOME).build());
    phoneStorage.put(l + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87057628189")
      .setType(PhoneType.MOBILE).build());
    phoneStorage.put(l + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87025148989")
      .setType(PhoneType.WORK).build());
    accountStorage.put(l + "", ClientAccountDot.newBuilder()
      .setId(1700)
      .setClient(l)
      .setMoney(200_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 1 + "", ClientAccountDot.newBuilder()
      .setId(1701)
      .setClient(l)
      .setMoney(47_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 2 + "", ClientAccountDot.newBuilder()
      .setId(1702)
      .setClient(l)
      .setMoney(143_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    transactionStorage.put(l + "", ClientAccountTransactionDot.newBuilder()
      .setId(10)
      .setAccount(100)
      .setMoney(0.0f)
      .setFinished_at(new Timestamp(0L))
      .setType(11).build());
    Collection<ClientAccountDot> values17 = accountStorage.values();
    List<ClientAccountDot> accountsForClients17 = new ArrayList<>();
    for (ClientAccountDot value : values17) {
      if (value.client == l)
        accountsForClients17.add(value);
    }
    accountClientMapStorage.put(l + "", accountsForClients17);

    l = clientSeq.getAndIncrement();

    clientStorage.put(l + "", ClientDot.newBuilder()
      .setId(l)
      .setSurname("Егорова")
      .setName("Анастасия")
      .setPatronymic("Вячеславовна")
      .setGender(Gender.FEMALE)
      .setBirth_date("1992-08-28")
      .setCharm(4)
      .build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.REG)
      .setStreet("Тимирязева")
      .setHouse("155")
      .setFlat("12").build());
    addressStorage.put(l + 1 + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.FACT)
      .setStreet("Абая")
      .setHouse("133")
      .setFlat("22").build());
    phoneStorage.put(l + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87275238189")
      .setType(PhoneType.HOME).build());
    phoneStorage.put(l + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87057628189")
      .setType(PhoneType.MOBILE).build());
    phoneStorage.put(l + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87025148989")
      .setType(PhoneType.WORK).build());
    accountStorage.put(l + "", ClientAccountDot.newBuilder()
      .setId(1800)
      .setClient(l)
      .setMoney(210_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 1 + "", ClientAccountDot.newBuilder()
      .setId(1801)
      .setClient(l)
      .setMoney(49_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 2 + "", ClientAccountDot.newBuilder()
      .setId(1802)
      .setClient(l)
      .setMoney(134_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    transactionStorage.put(l + "", ClientAccountTransactionDot.newBuilder()
      .setId(10)
      .setAccount(100)
      .setMoney(0.0f)
      .setFinished_at(new Timestamp(0L))
      .setType(11).build());
    Collection<ClientAccountDot> values18 = accountStorage.values();
    List<ClientAccountDot> accountsForClients18 = new ArrayList<>();
    for (ClientAccountDot value : values18) {
      if (value.client == l)
        accountsForClients18.add(value);
    }
    accountClientMapStorage.put(l + "", accountsForClients18);

    l = clientSeq.getAndIncrement();

    clientStorage.put(l + "", ClientDot.newBuilder()
      .setId(l)
      .setSurname("Егорова")
      .setName("Анастасия")
      .setPatronymic("Вячеславовна")
      .setGender(Gender.FEMALE)
      .setBirth_date("1992-08-28")
      .setCharm(2)
      .build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.REG)
      .setStreet("Тимирязева")
      .setHouse("155")
      .setFlat("12").build());
    addressStorage.put(l + 1 + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.FACT)
      .setStreet("Абая")
      .setHouse("133")
      .setFlat("22").build());
    phoneStorage.put(l + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87275238189")
      .setType(PhoneType.HOME).build());
    phoneStorage.put(l + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87057628189")
      .setType(PhoneType.MOBILE).build());
    phoneStorage.put(l + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87025148989")
      .setType(PhoneType.WORK).build());
    accountStorage.put(l + "", ClientAccountDot.newBuilder()
      .setId(1900)
      .setClient(l)
      .setMoney(275_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 1 + "", ClientAccountDot.newBuilder()
      .setId(1901)
      .setClient(l)
      .setMoney(57_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 2 + "", ClientAccountDot.newBuilder()
      .setId(1902)
      .setClient(l)
      .setMoney(194_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    transactionStorage.put(l + "", ClientAccountTransactionDot.newBuilder()
      .setId(10)
      .setAccount(100)
      .setMoney(0.0f)
      .setFinished_at(new Timestamp(0L))
      .setType(11).build());
    Collection<ClientAccountDot> values19 = accountStorage.values();
    List<ClientAccountDot> accountsForClients19 = new ArrayList<>();
    for (ClientAccountDot value : values19) {
      if (value.client == l)
        accountsForClients19.add(value);
    }
    accountClientMapStorage.put(l + "", accountsForClients19);

    l = clientSeq.getAndIncrement();

    clientStorage.put(l + "", ClientDot.newBuilder()
      .setId(l)
      .setSurname("Егоров")
      .setName("Кирилл")
      .setPatronymic("Вячеславович")
      .setGender(Gender.MALE)
      .setBirth_date("1992-08-28")
      .setCharm(2)
      .build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.REG)
      .setStreet("Тимирязева")
      .setHouse("155")
      .setFlat("12").build());
    addressStorage.put(l + 1 + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.FACT)
      .setStreet("Абая")
      .setHouse("133")
      .setFlat("22").build());
    phoneStorage.put(l + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87275238189")
      .setType(PhoneType.HOME).build());
    phoneStorage.put(l + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87057628189")
      .setType(PhoneType.MOBILE).build());
    phoneStorage.put(l + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87025148989")
      .setType(PhoneType.WORK).build());
    accountStorage.put(l + "", ClientAccountDot.newBuilder()
      .setId(2000)
      .setClient(l)
      .setMoney(315_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 1 + "", ClientAccountDot.newBuilder()
      .setId(2001)
      .setClient(l)
      .setMoney(82_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 2 + "", ClientAccountDot.newBuilder()
      .setId(2002)
      .setClient(l)
      .setMoney(217_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    transactionStorage.put(l + "", ClientAccountTransactionDot.newBuilder()
      .setId(10)
      .setAccount(100)
      .setMoney(0.0f)
      .setFinished_at(new Timestamp(0L))
      .setType(11).build());
    Collection<ClientAccountDot> values20 = accountStorage.values();
    List<ClientAccountDot> accountsForClients20 = new ArrayList<>();
    for (ClientAccountDot value : values20) {
      if (value.client == l)
        accountsForClients20.add(value);
    }
    accountClientMapStorage.put(l + "", accountsForClients20);

    l = clientSeq.getAndIncrement();

    clientStorage.put(l + "", ClientDot.newBuilder()
      .setId(l)
      .setSurname("Угрюмова")
      .setName("Валентина")
      .setPatronymic("Алексеевна")
      .setGender(Gender.FEMALE)
      .setBirth_date("1985-09-13")
      .setCharm(1)
      .build());
    addressStorage.put(l + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.REG)
      .setStreet("Тимирязева")
      .setHouse("155")
      .setFlat("12").build());
    addressStorage.put(l + 1 + "", ClientAddressDot.newBuilder()
      .setClient(l)
      .setType(AddressType.FACT)
      .setStreet("Абая")
      .setHouse("133")
      .setFlat("22").build());
    phoneStorage.put(l + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87275238189")
      .setType(PhoneType.HOME).build());
    phoneStorage.put(l + 2 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87057628189")
      .setType(PhoneType.MOBILE).build());
    phoneStorage.put(l + 1 + "", ClientPhoneDot.newBuilder()
      .setClient(l)
      .setNumber("87025148989")
      .setType(PhoneType.WORK).build());
    accountStorage.put(l + "", ClientAccountDot.newBuilder()
      .setId(2100)
      .setClient(l)
      .setMoney(222_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 1 + "", ClientAccountDot.newBuilder()
      .setId(2101)
      .setClient(l)
      .setMoney(22_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    accountStorage.put(l + 2 + "", ClientAccountDot.newBuilder()
      .setId(2102)
      .setClient(l)
      .setMoney(122_000.0f)
      .setNumber("")
      .setRegistered_at(new Timestamp(0L)).build());
    transactionStorage.put(l + "", ClientAccountTransactionDot.newBuilder()
      .setId(10)
      .setAccount(100)
      .setMoney(0.0f)
      .setFinished_at(new Timestamp(0L))
      .setType(11).build());
    Collection<ClientAccountDot> values21 = accountStorage.values();
    List<ClientAccountDot> accountsForClients21 = new ArrayList<>();
    for (ClientAccountDot value : values21) {
      if (value.client == l)
        accountsForClients21.add(value);
    }
    accountClientMapStorage.put(l + "", accountsForClients21);
  }

}
