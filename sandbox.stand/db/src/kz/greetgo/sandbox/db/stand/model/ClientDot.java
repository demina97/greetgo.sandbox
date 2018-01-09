package kz.greetgo.sandbox.db.stand.model;

import kz.greetgo.sandbox.controller.model.Gender;

public class ClientDot implements Comparable<ClientDot> {
  public int id;
  public String surname;
  public String name;
  public String patronymic;
  public Gender gender;
  public String birth_date;
  public int charm;

  private ClientDot() {}

  @Override
  public int compareTo(ClientDot clientDot) {
    return Integer.compare(id, clientDot.id);
  }

  public static Builder newBuilder() {
    return new ClientDot().new Builder();
  }

  public class Builder {
    private Builder() {}

    public Builder setId(int id) {
      ClientDot.this.id = id;
      return this;
    }

    public Builder setSurname(String surname) {
      ClientDot.this.surname = surname;
      return this;
    }

    public Builder setName(String name) {
      ClientDot.this.name = name;
      return this;
    }

    public Builder setPatronymic(String patronymic) {
      ClientDot.this.patronymic = patronymic;
      return this;
    }

    public Builder setGender(Gender gender) {
      ClientDot.this.gender = gender;
      return this;
    }

    public Builder setBirth_date(String birth_date) {
      ClientDot.this.birth_date = birth_date;
      return this;
    }

    public Builder setCharm(int charm) {
      ClientDot.this.charm = charm;
      return this;
    }

    public ClientDot build() {
      return ClientDot.this;
    }

  }
}