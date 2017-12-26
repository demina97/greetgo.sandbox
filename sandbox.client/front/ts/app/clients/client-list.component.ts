import {Component, OnInit} from "@angular/core";
import {HttpService} from "../HttpService";
@Component({
  selector: "client-list",
  template: require('./client-list.component.html')
})
export class ClientListComponent implements OnInit {

  constructor(private httpService: HttpService) {}

  showModal: boolean = false;

  ngOnInit(): void {
    this.httpService.post("/client/list", {})
  }

  addClient(){

    this.showModal = true;

  }

  closeModalForm(){
    this.showModal = false;
  }

}