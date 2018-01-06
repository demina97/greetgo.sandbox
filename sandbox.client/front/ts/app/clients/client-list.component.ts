import {Component, OnInit, ViewChild} from "@angular/core";
import {HttpService} from "../HttpService";
import {ClientRecord} from "../../model/ClientRecord";
import {ClientFormComponent} from "./client-form.component";
import {ClientDetailsRecord} from "../../model/ClientDetailsRecord";
import {ClientPage} from "../../model/ClientPage";
import {copyObj} from "@angular/animations/browser/src/util";

@Component({
  selector: "client-list",
  template: require('./client-list.component.html')
})
export class ClientListComponent implements OnInit {

  constructor(private httpService: HttpService) {}

  list: ClientRecord[] = [];
  listForPage: ClientRecord[] = [];
  client: ClientDetailsRecord = new ClientDetailsRecord();
  selectedId: string | null;
  selectedPage: number | null = 1;
  numOfClientsOnPage: number = 10;
  totalPages: number[];

  @ViewChild("clientForm") clientForm: ClientFormComponent;

  ngOnInit(): void {
    this.getClientPage(this.selectedPage, this.numOfClientsOnPage);
  }

  getClientList() {
    this.httpService.get("/client/list").toPromise().then(result => {
      this.list = result.json().clientInfoList.map(a => ClientRecord.copy(a));
      console.log(this.list);
    }, error => {
      console.log(error);
    });
  }

  getClientPage(selectedPage, numOfClientsOnPage) {
    this.httpService.get("/client/page", {
      "numOfPage": this.selectedPage,
      "numOfClients": this.numOfClientsOnPage
    }).toPromise().then(result => {
      this.listForPage = result.json().pageOfClients.map(a => ClientRecord.copy(a));
      this.totalPages = result.json().totalPages;
      console.log(this.listForPage);
      console.log(this.totalPages)
    }, error => {
      console.log(error);
    })
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
