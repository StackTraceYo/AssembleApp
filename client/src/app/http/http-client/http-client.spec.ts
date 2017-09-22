import {inject, TestBed} from '@angular/core/testing';

import {HttpClient} from './http-client';

describe('HttpClientService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HttpClient]
    });
  });

  it('should be created', inject([HttpClient], (service: HttpClient) => {
    expect(service).toBeTruthy();
  }));
});
