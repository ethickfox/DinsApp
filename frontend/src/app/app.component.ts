import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpClientModule} from '@angular/common/http';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit{
  constructor(private http: HttpClient) {
  }
  ;
  ngOnInit() {
    this.http.get(this.apiUrl)
      .subscribe(data => {
          data["prefix1"].forEach(
            x=>{
              this.users.push(x['firstName'])
              // console.log(x['firstName'])
            }
          )
      }, error => console.error(error));
  }

  apiUrl = '/api'
  title = 'frontend';
  users: string[] =[];
  value = "test";
}
