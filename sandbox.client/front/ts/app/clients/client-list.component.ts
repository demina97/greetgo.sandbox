import {Component, OnInit, ViewChild} from "@angular/core";
import {HttpService} from "../HttpService";
import {ClientRecord} from "../../model/ClientRecord";
import {ClientFormComponent} from "./client-form.component";
import {ClientDetailsRecord} from "../../model/ClientDetailsRecord";

@Component({
  selector: "client-list",
  template: require('./client-list.component.html')
})
export class ClientListComponent implements OnInit {

  constructor(private httpService: HttpService) {}

  list: ClientRecord[] = [];
  client: ClientDetailsRecord = new ClientDetailsRecord();
  selectedId: string | null = "a10";

  @ViewChild("clientForm") clientForm: ClientFormComponent;

  ngOnInit(): void {
    this.httpService.get("/client/list").toPromise().then(result => {
      this.list = result.json().clientInfoList.map(a => ClientRecord.copy(a));
      console.log(this.list);
    }, error => {
      console.log(error);
    });

  }

  addClient() {
    this.clientForm.show(null);
  }

  editClient() {
    this.clientForm.show(this.selectedId);
  }
}
