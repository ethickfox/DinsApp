import {AfterViewInit, Component, OnInit} from '@angular/core';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {User} from './user.js'
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import Table = WebAssembly.Table;


export interface User {
  id: number;
  firstName: string;
  lastName: string;
  birthday: string;
  address: string;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})


export class AppComponent implements OnInit{
  constructor(private http: HttpClient) {

  }

  ngOnInit() {
    this.dataSource._updateChangeSubscription()
    this.http.get(this.userUrl)
      .subscribe(data => {
          this.dataSource.data = data["prefix1"]
      }, error => console.error(error));
    console.log(this.dataSource.data)
  }
  createNew(){

  }
  deleteUser(){

  }
  getRecord(roqw){
    console.log(roqw)
  }
  apiUrl = '/api'
  userUrl = this.apiUrl+'/user'

  title = 'frontend';
  users: User[] = [];
  dataSource = new MatTableDataSource<User>();
  value = "test";
  displayedColumns: string[] = ['id', 'firstName', 'lastName', 'birthday', 'address'];
    // {key: 'firstName', header: 'firstName', cell: (row: User) => `${row.weight}`},];
}
