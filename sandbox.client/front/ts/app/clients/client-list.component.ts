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
  listForPage: ClientRecord[] = [];

  client: ClientDetailsRecord = new ClientDetailsRecord();
  selectedId: string | null;

  selectedPage: number | null = 1;
  numOfClientsOnPage: number = 10;
  totalPages: number[];

  filtrSurname: string | null;
  filtrName: string | null;
  filtrPatronymic: string | null;

  @ViewChild("clientForm") clientForm: ClientFormComponent;

  ngOnInit(): void {
    this.getClientPage(this.selectedPage, this.numOfClientsOnPage, this.filtrSurname, this.filtrName, this.filtrPatronymic);
  }

  getClientList() {
    this.httpService.get("/client/list").toPromise().then(result => {
      this.list = result.json().clientInfoList.map(a => ClientRecord.copy(a));
      console.log(this.list);
    }, error => {
      console.log(error);
    });
  }

  getClientPage(selectedPage, numOfClientsOnPage, filtrSurname, filtrName, filtrPatronymic) {
    this.httpService.get("/client/page", {
      "numOfPage": this.selectedPage,
      "numOfClients": this.numOfClientsOnPage,
      "filtrSurname": this.filtrSurname,
      "filtrName": this.filtrName,
      "filtrPatronymic": this.filtrPatronymic
    }).toPromise().then(result => {
      this.listForPage = result.json().pageOfClients.map(a => ClientRecord.copy(a));
      this.totalPages = result.json().totalPages;
      this.selectedPage = result.json().pageNum;
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
