import {inject, TestBed} from '@angular/core/testing';

import {UserService} from './user-service';
import {AppStorageService} from '../app-storage/app-storage.service';
import {AssembleUser} from './assemble.user';

describe('UserService', () => {

    const mockAuthUser = new AssembleUser('test@email.com', '123456-id', true);
    const mockNotAuthUser = new AssembleUser('test2@email.com', '1234567-id', false);

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [UserService, AppStorageService]
        });
    });

    it('should be created', inject([UserService], (service: UserService) => {
        expect(service).toBeTruthy();
    }));

    it('should star with an empty user', inject([UserService], (service: UserService) => {
        service.getUser().subscribe(user => {
            expect(user).toEqual(AssembleUser.noUser());
        });
    }));

    it('should store a authenticated user', inject([UserService], (service: UserService) => {
        service.storeUser(mockAuthUser);

        service.getUser().subscribe(user => {
            expect(user).toEqual(mockAuthUser);
        });
    }));

    it('should store a unauthenticated user', inject([UserService], (service: UserService) => {
        service.storeUser(mockNotAuthUser);

        service.getUser().subscribe(user => {
            expect(user).toEqual(mockNotAuthUser);
        });
    }));
});
