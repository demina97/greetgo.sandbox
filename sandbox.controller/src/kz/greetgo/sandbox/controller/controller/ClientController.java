package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.Mapping;
import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.sandbox.controller.model.CharmsList;
import kz.greetgo.sandbox.controller.model.ClientDetailsRecord;
import kz.greetgo.sandbox.controller.model.ClientListDetails;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.util.Controller;

@Bean
@Mapping("/client")
public class ClientController implements Controller {
  public BeanGetter<ClientRegister> clientRegister;

  @ToJson
  @Mapping("/list")
  public ClientListDetails getClientList() {
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
}
