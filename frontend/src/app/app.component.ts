import {AfterViewInit, Component, Inject, OnInit} from '@angular/core';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {User} from './user.js'
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";


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
  constructor(private http: HttpClient,public dialog: MatDialog) {

  }

  ngOnInit() {
    this.dataSource._updateChangeSubscription()
    this.http.get(this.userUrl)
      .subscribe(data => {
          this.dataSource.data = data["prefix1"]
      }, error => console.error(error));
    console.log(this.dataSource.data)
  }
  openDialog(idNum): void {
    console.log("qqqqqq "+this.dataSource.data[idNum].id)
    const dialogRef = this.dialog.open(DialogOverviewExampleDialog, {
      width: '250px',
      data: {id: "",
            // firstName:this.dataSource[idNum].firstName,
            // lastName:this.dataSource[idNum].lastName,
            // birthday:this.dataSource[idNum].birthday,
            // address:this.dataSource[idNum].address
      }
    });

    dialogRef.afterClosed().subscribe(result => {

      console.log('Sending data');

    });

  }

  deleteUser(){

  }
  apiUrl = '/api'
  userUrl = this.apiUrl+'/user'
  animal: string;
  name: string;

  title = 'frontend';
  users : User[] = [];
  dataSource = new MatTableDataSource<User>();
  value = "test";
  displayedColumns: string[] = ['id', 'firstName', 'lastName', 'birthday', 'address'];
    // {key: 'firstName', header: 'firstName', cell: (row: User) => `${row.weight}`},];
}
@Component({
  selector: 'app.component-dialog',
  templateUrl: 'app.component-dialog.html',
  styleUrls: ['./app.component.css']
})
export class DialogOverviewExampleDialog {

  constructor(
    public dialogRef: MatDialogRef<DialogOverviewExampleDialog>,
    @Inject(MAT_DIALOG_DATA) public data: User) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
