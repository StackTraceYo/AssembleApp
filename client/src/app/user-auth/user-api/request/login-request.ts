import {LoginFormModel} from "../../login-form/model/login-form-model";

export class LoginRequest {

  email: string;

  constructor(formModel: LoginFormModel) {
    this.email = formModel.email;
  }

}
