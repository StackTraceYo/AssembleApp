import {inject, TestBed} from '@angular/core/testing';

import {UserApiService} from './user-api.service';
import {HttpClient} from '../../http/http-client/http-client';
import {MockBackend} from '@angular/http/testing';
import {BaseRequestOptions, Http, Response, ResponseOptions} from '@angular/http';
import {LoginRequest} from './request/login-request';
import {LoginFormModel} from '../login-form/model/login-form-model';
import {Observable} from 'rxjs/Observable';
import {UserAuthenticationAttempt} from './model/user-authentication-attempt';
import {AssembleUser} from '../assemble.user';

fdescribe('UserApiService', () => {


    const loginModel = new LoginFormModel('test-email', 'test-password');
    const loginRequest = new LoginRequest(loginModel);
    const authSuccess = {
        user: {
            id: '1234',
            email: 'test@gmail',
            username: 'testUsername'
        },
        success: true
    };

    const authFailure = {
        request: {
            email: 'test@gmail'
        },
        msg: 'Failed To Sign In',
        success: false
    };
    const failedAttempt: UserAuthenticationAttempt =
        new UserAuthenticationAttempt(authSuccess.success, new AssembleUser(authSuccess.user.email, authSuccess.user.id, true),
            '');
    const successAttempt: UserAuthenticationAttempt =
        new UserAuthenticationAttempt(authFailure.success, new AssembleUser(authFailure.request.email, '', false),
            '');

    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [
                UserApiService,
                MockBackend,
                BaseRequestOptions,
                {
                    provide: Http,
                    useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                        return new Http(backendInstance, defaultOptions);
                    },
                    deps: [MockBackend, BaseRequestOptions]
                },
                HttpClient
            ]
        });
    });

    it('should be created', inject([UserApiService], (service: UserApiService) => {
        expect(service).toBeTruthy();
    }));

    it('can login successfully', inject([UserApiService, HttpClient], (service: UserApiService, httpClient: HttpClient) => {
        const options = new ResponseOptions({
            body: JSON.stringify(successAttempt)
        });
        spyOn(httpClient, 'post')
            .and.returnValue(Observable.of(new Response(options)));

        service.login(loginRequest).subscribe((attempt => {
            console.log(attempt);
            expect(attempt.isAuthenticated()).toBe(true);
        }));
    }));
});
