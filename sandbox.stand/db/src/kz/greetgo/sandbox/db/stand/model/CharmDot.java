package kz.greetgo.sandbox.db.stand.model;

public class CharmDot {
  public int id;
  public String name;
  public String description;
  public float energy;

  private CharmDot() {}

  public static Builder newBuilder() {
    return new CharmDot().new Builder();
  }

  public class Builder {

    public Builder setCharm(int charm) {
      CharmDot.this.id = id;
      return this;
    }

    public Builder setName(String name) {
      CharmDot.this.name = name;
      return this;
    }

    public Builder setDescription(String description) {
      CharmDot.this.description = description;
      return this;
    }

    public Builder setEnergy(float energy) {
      CharmDot.this.energy = energy;
      return this;
    }

    public CharmDot build() {
      return CharmDot.this;
    }
  }
}
