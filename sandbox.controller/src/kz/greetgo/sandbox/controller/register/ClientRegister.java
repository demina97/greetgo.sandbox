package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.CharmsList;
import kz.greetgo.sandbox.controller.model.ClientDetailsRecord;
import kz.greetgo.sandbox.controller.model.ClientListDetails;
import kz.greetgo.sandbox.controller.model.ClientRecord;

import java.util.List;

public interface ClientRegister {
  ClientListDetails getClientList();

  CharmsList getCharmsList();

  ClientDetailsRecord getClientInfo(String clientId);
}
