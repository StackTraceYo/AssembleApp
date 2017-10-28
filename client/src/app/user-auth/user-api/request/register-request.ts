export class RegisterRequest {

  email: string;
  password: string;
  username: string;

    constructor(formModel: any) {
    this.email = formModel.email;
    this.password = formModel.password;
        this.username = formModel.username || this.email;
  }

}
