import {inject, TestBed} from '@angular/core/testing';

import {AuthGuard} from './auth.guard';
import {Router} from '@angular/router';


const mockRouter = {
    navigateByUrl: jasmine.createSpy('navigateByUrl')
};

describe('AuthGuard', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [AuthGuard, {provide: Router, useValue: mockRouter}]
        });
    });

    it('should ...', inject([AuthGuard], (guard: AuthGuard) => {
        expect(guard).toBeTruthy();
    }));
});
