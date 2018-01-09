package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.Json;
import kz.greetgo.mvc.annotations.Mapping;
import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.sandbox.controller.model.CharmsList;
import kz.greetgo.sandbox.controller.model.ClientDetailsRecord;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.util.Controller;
import org.fest.util.Strings;

import java.util.List;

@Bean
@Mapping("/client")
public class ClientController implements Controller {
  public BeanGetter<ClientRegister> clientRegister;

  @ToJson
  @Mapping("/list")
  public List<ClientRecord> getClientList(@Par("numOfPage") int pageNum, @Par("numOfClients") int numOfClients,
                                          @Par("filtrSurname") String filtrSurname,
                                          @Par("filtrName") String filtrName,
                                          @Par("filtrPatronymic") String filtrPatronymic,
                                          @Par("sortType") String sortType, @Par("sortDirect") int sortDirect) {
    return clientRegister.get().getClientList(pageNum, numOfClients,
      filtrSurname, filtrName, filtrPatronymic, sortType, sortDirect);
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
  @Mapping("/size")
  public Long getSize(@Par("numOfPage") int pageNum, @Par("numOfClients") int numOfClients,
                      @Par("filtrSurname") String filtrSurname,
                      @Par("filtrName") String filtrName,
                      @Par("filtrPatronymic") String filtrPatronymic,
                      @Par("sortType") String sortType, @Par("sortDirect") int sortDirect) {
    return clientRegister.get().getSize(pageNum, numOfClients,
      filtrSurname, filtrName, filtrPatronymic, sortType, sortDirect);
  }

}
