import {Component, OnInit} from "@angular/core";
import {ClientDetailsRecord} from "../../model/ClientDetailsRecord";
import {HttpService} from "../HttpService";
import {CharmModel} from "../../model/CharmModel";


@Component({
  selector: "client-form",
  template: require('./client-form.component.html'),
})
export class ClientFormComponent implements OnInit {
  shown: boolean = false;
  client: ClientDetailsRecord = new ClientDetailsRecord();
  charms: CharmModel[] = [];

  constructor(private httpService: HttpService) {}

  ngOnInit(): void {
    this.httpService.get("/client/charms").toPromise().then(result => {
      this.charms = result.json().listOfCharms.map(a => CharmModel.copy(a));
      console.log(this.charms);
    }, error => {
      console.log(error);
    });
  }

  clientId: string | null;

  show(clientId: string | null) {
    this.clientId = clientId;
    this.shown = true;
    console.log(clientId);
    if (clientId === null) {
      this.client = new ClientDetailsRecord();
    } else {
      this.httpService.get("/client/info", {"clientId": this.clientId}).toPromise().then(result => {
        this.client = result.json() as ClientDetailsRecord;
        console.log(this.client);
      }, error => {
        console.log(error);
      })
    }
  }
}

