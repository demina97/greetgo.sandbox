package kz.greetgo.sandbox.controller.model;


public class ClientDetailsRecord {
  public int id;
  public String surname;
  public String name;
  public String patronymic;
  public Gender gender;
  public String birth_date;
  public int charm;
  public String factAddrStreet;
  public String factAddrHouse;
  public String factAddrFlat;
  public String regAddrStreet;
  public String regAddrHouse;
  public String regAddrFlat;
  public String homePhone;
  public String workPhone;
  public String mobilePhone;

  public static Builder newBuilder() {
    return new ClientDetailsRecord().new Builder();
  }

  public class Builder {
    private Builder() {}

    public Builder setId(int id) {
      ClientDetailsRecord.this.id = id;
      return this;
    }

    public Builder setSurname(String surname) {
      ClientDetailsRecord.this.surname = surname;
      return this;
    }

    public Builder setName(String name) {
      ClientDetailsRecord.this.name = name;
      return this;
    }

    public Builder setPatronymic(String patronymic) {
      ClientDetailsRecord.this.patronymic = patronymic;
      return this;
    }

    public Builder setGender(Gender gender) {
      ClientDetailsRecord.this.gender = gender;
      return this;
    }

    public Builder setBirth_date(String birth_date) {
      ClientDetailsRecord.this.birth_date = birth_date;
      return this;
    }

    public Builder setCharm(int charm) {
      ClientDetailsRecord.this.charm = charm;
      return this;
    }

    public Builder setFactAddrStreet(String factAddrStreet) {
      ClientDetailsRecord.this.factAddrStreet = factAddrStreet;
      return this;
    }

    public Builder setFactAddrHouse(String factAddrHouse) {
      ClientDetailsRecord.this.factAddrHouse = factAddrHouse;
      return this;
    }

    public Builder setFactAddrFlat(String factAddrFlat) {
      ClientDetailsRecord.this.factAddrFlat = factAddrFlat;
      return this;
    }

    public Builder setRegAddrStreet(String regAddrStreet) {
      ClientDetailsRecord.this.regAddrStreet = regAddrStreet;
      return this;
    }

    public Builder setRegAddrHouse(String regAddrHouse) {
      ClientDetailsRecord.this.regAddrHouse = regAddrHouse;
      return this;
    }

    public Builder setRegAddrFlat(String regAddrFlat) {
      ClientDetailsRecord.this.regAddrFlat = regAddrFlat;
      return this;
    }

    public Builder setHomePhone(String homePhone) {
      ClientDetailsRecord.this.homePhone = homePhone;
      return this;
    }

    public Builder setWorkPhone(String workPhone) {
      ClientDetailsRecord.this.workPhone = workPhone;
      return this;
    }

    public Builder setMobilePhone(String mobilePhone) {
      ClientDetailsRecord.this.mobilePhone = mobilePhone;
      return this;
    }

    public ClientDetailsRecord build() {
      return ClientDetailsRecord.this;
    }
  }
}
