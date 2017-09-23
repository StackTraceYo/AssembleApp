export class AssembleUser {

  constructor(private _email: string, private _id: string, private _authenticated: boolean) {
  }


  get email(): string {
    return this._email;
  }

  get authenticated(): boolean {
    return this._authenticated;
  }


  get id(): string {
    return this._id;
  }

  static noUser() {
    return new AssembleUser("", "", false);
  }

}
