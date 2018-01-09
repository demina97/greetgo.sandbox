import {Component, OnInit, ViewChild} from "@angular/core";
import {HttpService} from "../HttpService";
import {ClientRecord} from "../../model/ClientRecord";
import {ClientFormComponent} from "./client-form.component";
import {ClientDetailsRecord} from "../../model/ClientDetailsRecord";
import {SortType} from "../../model/SortType";

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

  sortType: SortType = SortType.NON;
  sortDirection: number = 0;

  selectedPage: number | null = 1;
  numOfClientsOnPage: number = 10;
  clientsNum: number = 0;
  totalPages: number[];

  filtrSurname: string | null;
  filtrName: string | null;
  filtrPatronymic: string | null;

  @ViewChild("clientForm") clientForm: ClientFormComponent;

  ngOnInit(): void {
    this.getClientsNum();
    this.getClientList(this.selectedPage, this.numOfClientsOnPage, this.filtrSurname, this.filtrName,
      this.filtrPatronymic, this.sortType, this.sortDirection);
  }

  getClientList(selectedPage, numOfClientsOnPage, filtrSurname, filtrName, filtrPatronymic, sortType, sortDirection) {
    this.httpService.get("/client/list", {
      "numOfPage": this.selectedPage,
      "numOfClients": this.numOfClientsOnPage,
      "filtrSurname": this.filtrSurname,
      "filtrName": this.filtrName,
      "filtrPatronymic": this.filtrPatronymic,
      "sortType": this.sortType,
      "sortDirect": this.sortDirection
    })
      .toPromise().then(result => {
      this.listForPage = result.json().map(a => ClientRecord.copy(a));
      this.selectedId = null;
      console.log(this.listForPage);
    }, error => {
      console.log(error);
    })
  }

  getClientsNum() {
    this.httpService.get("/client/size", {
      "numOfPage": this.selectedPage,
      "numOfClients": this.numOfClientsOnPage,
      "filtrSurname": this.filtrSurname,
      "filtrName": this.filtrName,
      "filtrPatronymic": this.filtrPatronymic,
      "sortType": this.sortType,
      "sortDirect": this.sortDirection
    }
    ).toPromise().then(result => {
      this.clientsNum = result.json();
      this.getTotalPages();
      console.log(this.clientsNum);
    });
  }

  getTotalPages() {
    this.totalPages = [];
    var numOfPages = Math.ceil(this.clientsNum / 10);
    for (var i = 0; i < numOfPages; i++) {
      this.totalPages[i] = i + 1;
    }
    console.log(this.totalPages);
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

  changeSort(type: string) {
    if (!this.sortDirection) {
      this.sortDirection = 1;
    }

    switch (type) {
      case "age":
        if (this.sortType != SortType.AGE) {
          this.sortType = SortType.AGE;
          console.log(this.sortType);
          this.sortDirection = 1;
          console.log(this.sortDirection);
        } else {
          this.sortDirection *= -1;
        }
        this.getClientList(this.selectedPage, this.numOfClientsOnPage, this.filtrSurname, this.filtrName,
          this.filtrPatronymic,this.sortType, this.sortDirection);
        break;

      case "totalScore":
        if (this.sortType != SortType.TOTAL_SCORE) {
          this.sortType = SortType.TOTAL_SCORE;
          this.sortDirection = 1;
        } else {
          this.sortDirection *= -1;
        }
        this.getClientList(this.selectedPage, this.numOfClientsOnPage, this.filtrSurname, this.filtrName,
          this.filtrPatronymic,this.sortType, this.sortDirection);
        break;

      case "maxScore":
        if (this.sortType != SortType.MAX_SCORE) {
          this.sortType = SortType.MAX_SCORE;
          this.sortDirection = 1;
        } else {
          this.sortDirection *= -1;
        }
        this.getClientList(this.selectedPage, this.numOfClientsOnPage, this.filtrSurname, this.filtrName,
          this.filtrPatronymic, this.sortType, this.sortDirection);
        break;

      case "minScore":
        if (this.sortType != SortType.MIN_SCORE) {
          this.sortType = SortType.MIN_SCORE;
          this.sortDirection = 1;
        } else {
          this.sortDirection *= -1;
        }
        this.getClientList(this.selectedPage, this.numOfClientsOnPage, this.filtrSurname, this.filtrName,
          this.filtrPatronymic, this.sortType, this.sortDirection);
        break;
    }
  }
}
