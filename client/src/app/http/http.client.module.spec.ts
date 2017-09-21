import {HttpClientModule} from './http.client.module';

describe('HttpModule', () => {
  let httpModule: HttpClientModule;

  beforeEach(() => {
    httpModule = new HttpClientModule();
  });

  it('should create an instance', () => {
    expect(httpModule).toBeTruthy();
  });
});
