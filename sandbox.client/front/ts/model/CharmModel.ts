export class CharmModel {
  public id: number/*int*/;
  public name: string;

  assign(a: any) {
    this.id = a.id;
    this.name = a.name;
  }

  static copy(a: any): CharmModel {
    let ret = new CharmModel();
    ret.assign(a);
    return ret;
  }
}