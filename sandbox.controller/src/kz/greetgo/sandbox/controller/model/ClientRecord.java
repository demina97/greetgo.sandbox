package kz.greetgo.sandbox.controller.model;

public class ClientRecord {
  public String id;
  public String fio;
  public String charm;
  public int age;
  public int totalBalance;
  public int maxBalance;
  public int minBalance;

  public static Builder newBuilder(){
    return new ClientRecord().new Builder();
  }

  public class Builder{
    private Builder() {}

    public Builder setId(String id){
      ClientRecord.this.id = id;
      return this;
    }

    public Builder setFio(String fio){
      ClientRecord.this.fio = fio;
      return this;
    }

    public Builder setCharm(String charm){
      ClientRecord.this.charm = charm;
      return this;
    }

    public Builder setAge(int age){
      ClientRecord.this.age = age;
      return this;
    }

    public Builder setTotalBalance(int totalBalance){
      ClientRecord.this.totalBalance = totalBalance;
      return this;
    }

    public Builder setMaxBalance(int maxBalance){
      ClientRecord.this.maxBalance = maxBalance;
      return this;
    }

    public Builder setMinBalance(int minBalance){
      ClientRecord.this.minBalance = minBalance;
      return this;
    }

    public ClientRecord build(){
      return ClientRecord.this;
    }
  }
}
