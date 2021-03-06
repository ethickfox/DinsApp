import { Component, Inject, OnInit} from '@angular/core';
import {HttpClient,  HttpHeaders} from '@angular/common/http';
import {MatTableDataSource} from "@angular/material/table";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {DateAdapter, MAT_DATE_FORMATS} from "@angular/material/core";
import {APP_DATE_FORMATS,AppDateAdapter} from "./date.adapter"
import {User} from "./app.user"


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})


export class AppComponent implements OnInit{
  constructor(private http: HttpClient,public dialog: MatDialog) {}
  ngOnInit() {
    this.updateFromServer()
  }
  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  createUser(user:User){
    user.birthday.setDate(user.birthday.getDate() + 1);

    const body = {
      id: 99,
      firstName: user.firstName,
      lastName: user.lastName,
      birthday: user.birthday.toISOString().split('T')[0],
      address: user.address
    };
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })}


    this.http.post("/api/user/create",body,options)
      .subscribe(data => {}, error => console.error(error));
  }
  openDialog(idNum): void {
    const dialogRef = this.dialog.open(DialogOverview, {
       data: {user: this.getUserFromDataSource(idNum), component:this},
    });
    dialogRef.afterClosed().subscribe(() => {
      this.updateFromServer()
    });
  }
  openDialogCreateUser():void{
    var tmpUser: User = {id:null,firstName:"", lastName:"", birthday:new Date(), address:""};
    const dialogRef = this.dialog.open(DialogOverview, {
      data: {user: tmpUser, component:this},
    });
    dialogRef.afterClosed().subscribe(() => {
      this.updateFromServer()
    });
  }
  updateFromServer(){
    this.http.get(this.userUrl)
      .subscribe(data => {
        this.dataSource.data = data["prefix1"]
      }, error => console.error(error));
  }
  deleteUser(id){
    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    };

    this.http.delete("/api/user/delete/"+id, options)
      .subscribe(() => {
        this.updateFromServer()
      });
  }
  getUserFromDataSource(id:number):User{
    var result = null;
    this.dataSource.data.forEach(
      user=>{
        if(user.id == id)
          result =  user;
      }
    )
    return result;

  }
  updateUser(id){
    let bd:string;
    if(typeof this.getUserFromDataSource(id).birthday === "string"){
      bd = this.getUserFromDataSource(id).birthday.toString();
    }else{
      this.getUserFromDataSource(id).birthday.setDate(this.getUserFromDataSource(id).birthday.getDate() + 1);
      bd = this.getUserFromDataSource(id).birthday.toISOString().split('T')[0]
    }
    const body = {
        id: this.getUserFromDataSource(id).id,
        firstName: this.getUserFromDataSource(id).firstName,
        lastName: this.getUserFromDataSource(id).lastName,
        birthday: bd,//this.getUserFromDataSource(id).birthday,//.toISOString().split('T')[0],
        address: this.getUserFromDataSource(id).address
    };


    const options = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })}


    this.http.put("/api/user/update",body, options)
      .subscribe(() => {
        this.updateFromServer()
      });}
  apiUrl = '/api'
  userUrl = this.apiUrl+'/user'
  animal: string;
  name: string;

  title = 'frontend';
  // users : Map<number,User> =new Map<number,User>();
  dataSource = new MatTableDataSource<User>();
  value = "test";
  displayedColumns: string[] = ['id', 'firstName', 'lastName', 'birthday', 'address'];
}

@Component({
  selector: 'app.component-dialog',
  templateUrl: 'app.component-dialog.html',
  styleUrls: ['./app.component-dialog.css'],
  providers: [
    {provide: DateAdapter, useClass: AppDateAdapter},
    {provide: MAT_DATE_FORMATS, useValue: APP_DATE_FORMATS}
  ]
})

export class DialogOverview {
  constructor(
    public dialogRef: MatDialogRef<DialogOverview>,
    @Inject(MAT_DIALOG_DATA) public data) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
  onSaveClick(id){
    this.data['component'].updateUser(id)
    this.dialogRef.close()
  }
  onCreateUser(){
    this.data['component'].createUser(this.data['user'])
    this.dialogRef.close()

  }
  onDeleteClick(id){
    this.data['component'].deleteUser(id)
    this.dialogRef.close()
  }


}
