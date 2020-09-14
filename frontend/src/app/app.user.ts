export interface User {
  id: number;
  firstName: string;
  lastName: string;
  birthday: Date;
  address: string;
}

export class User{
  constructor(id:number,firstName:string,lastName:string,birthday:Date,address:string) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthday = birthday;
    this.address = address;
  }
}
