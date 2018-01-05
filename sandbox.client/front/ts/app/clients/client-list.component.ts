import {Component, EventEmitter, OnInit, Output, ViewChild} from "@angular/core";
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
  selectedId: string | null;

  @ViewChild("clientForm") clientForm: ClientFormComponent;
  ngOnInit(): void {
    this.getClientList();
  }

  getClientList(){
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

  deleteClient() {
    let q = window.confirm("Вы действительно хотите удалить данные о клиенте?");
    if (q) {
      this.httpService.get("/client/delete", {"clientId": this.selectedId}).toPromise().then(result => {
          window.alert("Данные о клиенте удалены.");
          this.ngOnInit();
        }, error => {
          console.log(error);
        }
      );
    }
  }

}
