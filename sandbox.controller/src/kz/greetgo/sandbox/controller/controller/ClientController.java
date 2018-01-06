package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.Json;
import kz.greetgo.mvc.annotations.Mapping;
import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.sandbox.controller.model.CharmsList;
import kz.greetgo.sandbox.controller.model.ClientDetailsRecord;
import kz.greetgo.sandbox.controller.model.ClientList;
import kz.greetgo.sandbox.controller.model.ClientPage;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.util.Controller;

@Bean
@Mapping("/client")
public class ClientController implements Controller {
  public BeanGetter<ClientRegister> clientRegister;

  @ToJson
  @Mapping("/list")
  public ClientList getClientList() {
    return clientRegister.get().getClientList();
  }

  @ToJson
  @Mapping("/charms")
  public CharmsList getCharmsList() {
    return clientRegister.get().getCharmsList();
  }

  @ToJson
  @Mapping("/info")
  public ClientDetailsRecord getClientInfo(@Par("clientId") String clientId) {
    return clientRegister.get().getClientInfo(clientId);
  }

  @ToJson
  @Mapping("/save")
  public ClientDetailsRecord saveClient(@Par("clientToSave") @Json ClientDetailsRecord clientDetailsRecord) {
    return clientRegister.get().saveClient(clientDetailsRecord);
  }

  @ToJson
  @Mapping("/delete")
  public ClientDetailsRecord deleteClient(@Par("clientId") String clientId) {
    return clientRegister.get().deleteClient(clientId);
  }

  @ToJson
  @Mapping("/page")
  public ClientPage getClientPage(@Par("numOfPage") int pageNum, @Par("numOfClients") int numOfClients) {
    return clientRegister.get().getClientPage(pageNum, numOfClients);
  }
}
