package kz.greetgo.sandbox.db.stand.model;

public class TransactionTypeDot {
  public int id;
  public String code;
  public String name;

  private TransactionTypeDot() {}

  public static Builder newBuilder() {
    return new TransactionTypeDot().new Builder();
  }

  public class Builder {
    private Builder() {}

    public Builder setId(int id) {
      TransactionTypeDot.this.id = id;
      return this;
    }

    public Builder setCode(String code) {
      TransactionTypeDot.this.code = code;
      return this;
    }

    public Builder setName(String name) {
      TransactionTypeDot.this.name = name;
      return this;
    }

    public TransactionTypeDot build() {
      return TransactionTypeDot.this;
    }
  }
}
