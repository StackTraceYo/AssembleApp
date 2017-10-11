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
        console.log('BEFORE');
        localStorage.setItem('asm-token', 'preloaded');
        localStorage.setItem('asm-user', JSON.stringify(mockAuthUser));
    });

    it('should load a previous a token', inject([UserService, AppStorageService], (service: UserService, storage: AppStorageService) => {
        service.getToken().subscribe(token => {
            expect(token).toEqual('preloaded');
        });
    }));

    it('should load a previous user', inject([UserService, AppStorageService], (service: UserService, storage: AppStorageService) => {
        service.getUser().subscribe(user => {
            expect(user).toEqual(new AssembleUser('test@email.com', '123456-id', true));
        });
    }));

    it('should be created', inject([UserService], (service: UserService) => {
        expect(service).toBeTruthy();
    }));

    xit('should star with an empty user', inject([UserService], (service: UserService) => {
        service.getUser().subscribe(user => {
            expect(user).toEqual(AssembleUser.noUser());
        });
    }));

    it('should store a authenticated user', inject([UserService, AppStorageService], (service: UserService, storage: AppStorageService) => {
        service.storeUser(mockAuthUser);

        service.getUser().subscribe(user => {
            expect(user).toEqual(mockAuthUser);
            expect(storage.exists('asm-user')).toBe(true);
            expect(storage.retrieve('asm-user')).toBe(JSON.stringify(mockAuthUser));
        });
    }));

    it('should store a unauthenticated user', inject([UserService], (service: UserService) => {
        service.storeUser(mockNotAuthUser);

        service.getUser().subscribe(user => {
            expect(user).toEqual(mockNotAuthUser);
        });
    }));

    it('should store a token', inject([UserService, AppStorageService], (service: UserService, storage: AppStorageService) => {
        service.putToken('123');

        service.getToken().subscribe(token => {
            expect(token).toEqual('123');
            expect(storage.exists('asm-token')).toBe(true);
            expect(storage.retrieve('asm-token')).toBe(JSON.stringify('123'));
        });
    }));



});
