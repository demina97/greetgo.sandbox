package kz.greetgo.sandbox.db.stand.model;

import kz.greetgo.sandbox.controller.model.PhoneType;

public class ClientPhoneDot {
  public int client;
  public String number;
  public PhoneType type;

  private ClientPhoneDot() {}

  public static Builder newBuilder() {
    return new ClientPhoneDot().new Builder();
  }

  public class Builder {
    private Builder() {}

    public Builder setClient(int client) {
      ClientPhoneDot.this.client = client;
      return this;
    }

    public Builder setNumber(String number) {
      ClientPhoneDot.this.number = number;
      return this;
    }

    public Builder setType(PhoneType type) {
      ClientPhoneDot.this.type = type;
      return this;
    }

    public ClientPhoneDot build() {
      return ClientPhoneDot.this;
    }
  }
}
