package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.CharmsList;
import kz.greetgo.sandbox.controller.model.ClientDetailsRecord;
import kz.greetgo.sandbox.controller.model.ClientPage;
import kz.greetgo.sandbox.controller.model.ClientRecord;

import java.util.List;

public interface ClientRegister {
  List<ClientRecord> getClientList(int pageNum, int numOfClients,
                                   String filtrSurname, String filtrName, String filtrPatronymic,
                                   String sortType, int sortDirect);

  CharmsList getCharmsList();

  ClientDetailsRecord getClientInfo(String clientId);

  ClientDetailsRecord saveClient(ClientDetailsRecord clientDetailsRecord);

  ClientDetailsRecord deleteClient(String clientId);

  Long getSize(int pageNum, int numOfClients,
               String filtrSurname, String filtrName, String filtrPatronymic,
               String sortType, int sortDirect);
}
