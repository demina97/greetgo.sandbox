package kz.greetgo.sandbox.controller.model;

import java.util.ArrayList;
import java.util.List;

public class ClientPage {
  public int pageNum;
  public List<Integer> totalPages = new ArrayList<>();
  public List<ClientRecord> pageOfClients = new ArrayList<>();
}
