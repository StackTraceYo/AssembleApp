export class LoginRequest {

  email: string;

    constructor(formModel: any) {
    this.email = formModel.email;
  }

}
