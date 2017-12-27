package kz.greetgo.sandbox.db.stand.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.HasAfterInject;
import kz.greetgo.sandbox.db.stand.model.ClientDot;
import kz.greetgo.sandbox.db.stand.model.PersonDot;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Bean
public class StandDb implements HasAfterInject {
  public final Map<String, PersonDot> personStorage = new HashMap<>();
  public final Map<String, ClientDot> clientStorage = new HashMap<>();

  public final AtomicLong clientSeq=new AtomicLong(1);

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

  private void prepareData(){
    clientStorage.put(clientSeq.get()+"",
                          ClientDot
                            .newBuilder()
                            .setId(clientSeq.incrementAndGet() + "")
                            .setFio("Семенов Егор Петрович")
                            .setCharm("Сангвиник")
                            .setAge(23)
                            .setTotalBalance(120000)
                            .setMaxBalance(200000)
                            .setMinBalance(50000).build());
    clientStorage.put(clientSeq.get()+"",
                          ClientDot
                            .newBuilder()
                            .setId(clientSeq.incrementAndGet() + "")
                            .setFio("Петров Егор Семенович")
                            .setCharm("Холерик")
                            .setAge(29)
                            .setTotalBalance(100000)
                            .setMaxBalance(180000)
                            .setMinBalance(30000).build());
    clientStorage.put(clientSeq.get()+"",
                          ClientDot
                            .newBuilder()
                            .setId(clientSeq.incrementAndGet() + "")
                            .setFio("Егоров Семен Петрович")
                            .setCharm("Меланхолик")
                            .setAge(37)
                            .setTotalBalance(130000)
                            .setMaxBalance(210000)
                            .setMinBalance(60000).build());
    clientStorage.put(clientSeq.get()+"",
                          ClientDot
                            .newBuilder()
                            .setId(clientSeq.incrementAndGet() + "")
                            .setFio("Семенов Петр Егорович")
                            .setCharm("Флегматик")
                            .setAge(24)
                            .setTotalBalance(120000)
                            .setMaxBalance(190000)
                            .setMinBalance(20000).build());
    clientStorage.put(clientSeq.get()+"",
                          ClientDot
                            .newBuilder()
                            .setId(clientSeq.incrementAndGet() + "")
                            .setFio("Егоров Петр Семерович")
                            .setCharm("Холерик")
                            .setAge(28)
                            .setTotalBalance(90000)
                            .setMaxBalance(180000)
                            .setMinBalance(30000).build());
  }


}
