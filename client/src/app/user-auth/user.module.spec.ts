import {UserModule} from './user.module';

describe('UserModule', () => {
  let loginModule: UserModule;

  beforeEach(() => {
    loginModule = new UserModule();
  });

  it('should create an instance', () => {
    expect(loginModule).toBeTruthy();
  });
});
