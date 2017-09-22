export class AssembleUser {

  private _id: string;
  private _email: string;
  private _authenticated: boolean;

  constructor(email: string, id: string, authenticated: boolean) {
    this._email = email;
    this._id = id;
    this._authenticated = false;
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
}
