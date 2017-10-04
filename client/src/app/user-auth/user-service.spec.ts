import {inject, TestBed} from '@angular/core/testing';

import {UserService} from './user-service';
import {AppStorageService} from '../app-storage/app-storage.service';

describe('UserService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
        providers: [UserService, AppStorageService]
    });
  });

  it('should be created', inject([UserService], (service: UserService) => {
    expect(service).toBeTruthy();
  }));
});
