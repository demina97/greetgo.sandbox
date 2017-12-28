package kz.greetgo.sandbox.db.stand.model;

import kz.greetgo.sandbox.controller.model.AddressType;

public class ClientAddressDot {
  public int client;
  public AddressType type;
  public String street;
  public String house;
  public String flat;

  private ClientAddressDot() {}

  public static Builder newBuilder() {
    return new ClientAddressDot().new Builder();
  }

  public class Builder {
    private Builder() {}

    public Builder setClient(int client) {
      ClientAddressDot.this.client = client;
      return this;
    }

    public Builder setType(AddressType type) {
      ClientAddressDot.this.type = type;
      return this;
    }

    public Builder setStreet(String street) {
      ClientAddressDot.this.street = street;
      return this;
    }

    public Builder setHouse(String house) {
      ClientAddressDot.this.house = house;
      return this;
    }

    public Builder setFlat(String flat) {
      ClientAddressDot.this.flat = flat;
      return this;
    }

    public ClientAddressDot build() {
      return ClientAddressDot.this;
    }
  }
}


