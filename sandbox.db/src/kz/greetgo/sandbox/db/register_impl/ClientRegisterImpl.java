package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.CharmsList;
import kz.greetgo.sandbox.controller.model.ClientDetailsRecord;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.register.ClientRegister;

import java.util.List;

@Bean
public class ClientRegisterImpl implements ClientRegister{

  @Override
  public List<ClientRecord> getClientList(int pageNum, int numOfClients, String filtrSurname, String filtrName, String filtrPatronymic, String sortType, int sortDirect) {
    return null;
  }

  @Override
  public CharmsList getCharmsList() {
    return null;
  }

  @Override
  public ClientDetailsRecord getClientInfo(String clientId) {
    return null;
  }

  @Override
  public ClientDetailsRecord saveClient(ClientDetailsRecord clientDetailsRecord) {
    return null;
  }

  @Override
  public ClientDetailsRecord deleteClient(String clientId) {
    return null;
  }

  @Override
  public Long getSize(int pageNum, int numOfClients, String filtrSurname, String filtrName, String filtrPatronymic, String sortType, int sortDirect) {
    return null;
  }
}
