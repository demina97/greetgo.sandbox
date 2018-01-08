package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.CharmsList;
import kz.greetgo.sandbox.controller.model.ClientDetailsRecord;
import kz.greetgo.sandbox.controller.model.ClientList;
import kz.greetgo.sandbox.controller.model.ClientPage;

public interface ClientRegister {
  ClientList getClientList();

  CharmsList getCharmsList();

  ClientDetailsRecord getClientInfo(String clientId);

  ClientDetailsRecord saveClient(ClientDetailsRecord clientDetailsRecord);

  ClientDetailsRecord deleteClient(String clientId);

  ClientPage getClientPage(int pageNum, int numOfClients,
                           String filtrSurname, String filtrName, String filtrPatronymic, String sortType, int sortDirect);
}
