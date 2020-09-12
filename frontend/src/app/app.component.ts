import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {User} from './user.js'
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})


export class AppComponent implements OnInit{
  constructor(private http: HttpClient) {
  }

  ngOnInit() {
    this.http.get(this.apiUrl)
      .subscribe(data => {
          data["prefix1"].forEach(
            x=>{
              this.users.push(new User(x['id'],x['firstName'],x['lastName'],x['birthday'],x['address']))
            }
          )
      }, error => console.error(error));
    console.log(this.users)
  }

  apiUrl = '/api'
  title = 'frontend';
  users:User[] = [];
  value = "test";
  displayedColumns: string[] = ['position', 'name', 'weight', 'symbol'];
}
