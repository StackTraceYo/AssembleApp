import {RegistrationFormModel} from '../../register-form/model/registeration-form-model';

export class RegisterRequest {

  email: string;
  password: string;
  username: string;

  constructor(formModel: RegistrationFormModel) {
    this.email = formModel.email;
    this.password = formModel.password;
    this.username = formModel.username;
  }

}
