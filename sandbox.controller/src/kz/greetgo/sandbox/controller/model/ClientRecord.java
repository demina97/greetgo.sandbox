package kz.greetgo.sandbox.controller.model;

public class ClientRecord {
  public int id;
  public String fio;
  public String charm;
  public int age;
  public float totalBalance;
  public float maxBalance;
  public float minBalance;

  public static Builder newBuilder(){
    return new ClientRecord().new Builder();
  }

  public class Builder{
    private Builder() {}

    public Builder setId(int id){
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

    public Builder setTotalBalance(float totalBalance){
      ClientRecord.this.totalBalance = totalBalance;
      return this;
    }

    public Builder setMaxBalance(float maxBalance){
      ClientRecord.this.maxBalance = maxBalance;
      return this;
    }

    public Builder setMinBalance(float minBalance){
      ClientRecord.this.minBalance = minBalance;
      return this;
    }

    public ClientRecord build(){
      return ClientRecord.this;
    }
  }
}
