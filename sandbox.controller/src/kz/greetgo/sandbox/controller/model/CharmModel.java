package kz.greetgo.sandbox.controller.model;

public class CharmModel {
  public int id;
  public String name;

  public static Builder newBuilder() {
    return new CharmModel().new Builder();
  }

  public class Builder {
    private Builder() { }

    public Builder setId(int id){
      CharmModel.this.id = id;
      return this;
    }

    public Builder setName(String name){
      CharmModel.this.name = name;
      return this;
    }

    public CharmModel build(){

      return CharmModel.this;
    }

  }
}
