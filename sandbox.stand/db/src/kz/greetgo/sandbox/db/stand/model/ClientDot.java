package kz.greetgo.sandbox.db.stand.model;

import kz.greetgo.sandbox.controller.model.ClientRecord;

public class ClientDot {
  public String id, fio,  charm;
  public int age,  totalBalance,  maxBalance, minBalance;

  private ClientDot() { }

  public static Builder newBuilder(){
    return new ClientDot().new Builder();
  }

  public class Builder{
    private Builder() {}

    public Builder setId(String id){
      ClientDot.this.id = id;
      return this;
    }

    public Builder setFio(String fio){
      ClientDot.this.fio = fio;
      return this;
    }

    public Builder setCharm(String charm){
      ClientDot.this.charm = charm;
      return this;
    }

    public Builder setAge(int age){
      ClientDot.this.age = age;
      return this;
    }

    public Builder setTotalBalance(int totalBalance){
      ClientDot.this.totalBalance = totalBalance;
      return this;
    }

    public Builder setMaxBalance(int maxBalance){
      ClientDot.this.maxBalance = maxBalance;
      return this;
    }

    public Builder setMinBalance(int minBalance){
      ClientDot.this.minBalance = minBalance;
      return this;
    }

    public ClientDot build(){
      return ClientDot.this;
    }
  }
}