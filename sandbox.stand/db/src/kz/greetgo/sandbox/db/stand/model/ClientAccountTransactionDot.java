package kz.greetgo.sandbox.db.stand.model;


import java.sql.Timestamp;

public class ClientAccountTransactionDot {
  public int id;
  public int account;
  public float money;
  public Timestamp finished_at;
  public int type;

  private ClientAccountTransactionDot() {}

  public static Builder newBuilder() {
    return new ClientAccountTransactionDot().new Builder();
  }

  public class Builder {
    private Builder() {}

    public Builder setId(int id) {
      ClientAccountTransactionDot.this.id = id;
      return this;
    }

    public Builder setAccount(int account) {
      ClientAccountTransactionDot.this.account = account;
      return this;
    }

    public Builder setMoney(float money) {
      ClientAccountTransactionDot.this.money = money;
      return this;
    }

    public Builder setFinished_at(Timestamp finished_at) {
      ClientAccountTransactionDot.this.finished_at = finished_at;
      return this;
    }

    public Builder setType(int type) {
      ClientAccountTransactionDot.this.type = type;
      return this;
    }

    public ClientAccountTransactionDot build() {
      return ClientAccountTransactionDot.this;
    }
  }

}
