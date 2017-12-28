package kz.greetgo.sandbox.db.stand.model;

import java.sql.Timestamp;

public class ClientAccountDot {
  public int id;
  public int client;
  public float money;
  public String number;
  public Timestamp registered_at;

  private ClientAccountDot() {}

  public static Builder newBuilder() {
    return new ClientAccountDot().new Builder();
  }

  public class Builder {
    private Builder() {}

    public Builder setId(int id) {
      ClientAccountDot.this.id = id;
      return this;
    }

    public Builder setClient(int client) {
      ClientAccountDot.this.client = client;
      return this;
    }

    public Builder setMoney(float money) {
      ClientAccountDot.this.money = money;
      return this;
    }

    public Builder setNumber(String number) {
      ClientAccountDot.this.number = number;
      return this;
    }

    public Builder setRegistered_at(Timestamp registered_at) {
      ClientAccountDot.this.registered_at = registered_at;
      return this;
    }

    public ClientAccountDot build() {
      return ClientAccountDot.this;
    }
  }
}
