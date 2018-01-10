package kz.greetgo.sandbox.db.register_impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.db.test.dao.ClientTestDao;
import kz.greetgo.sandbox.db.test.util.ParentTestNg;
import org.fest.assertions.api.Assertions;
import org.testng.annotations.Test;

import java.util.List;

public class ClientRegisterImplTest extends ParentTestNg {

  public BeanGetter<ClientRegister> clientRegister;

  public BeanGetter<ClientTestDao> clientTestDao;

  @Test
  public void testGetClientList() throws Exception {
    clientTestDao.get().deleteAllClient();
    //delete all;

    long id = 1L;
    String name = "name";
    clientTestDao.get().insertClient(id, name);

    //initialization

    List<ClientRecord> clientList = clientRegister.get().getClientList(1, 1,
        null, null, null, null, 1);
    //method

    Assertions.assertThat(clientList.size()).isEqualTo(1);
    //assert
  }

  @Test
  public void testGetCharmsList() throws Exception {
  }

  @Test
  public void testGetClientInfo() throws Exception {
    ClientRegister x = new ClientRegisterImpl();
  }

  @Test
  public void testSaveClient() throws Exception {
  }

  @Test
  public void testDeleteClient() throws Exception {
  }

  @Test
  public void testGetSize() throws Exception {
  }

}