import {async, ComponentFixture, inject, TestBed} from '@angular/core/testing';

import {LoginFormComponent} from './login-form.component';
import {NgMaterialModule} from '../../ng-material/ng-material.module';
import {FormsModule} from '@angular/forms';
import {UserApiService} from '../user-api/user-api.service';
import {UserService} from '../user-service';
import {HttpClient} from '../../http/http-client/http-client';
import {MockBackend} from '@angular/http/testing';
import {BaseRequestOptions, Http} from '@angular/http';
import {AppStorageService} from '../../app-storage/app-storage.service';
import {Router} from '@angular/router/';
import {AppRoutingModule} from '../../app-routing/app-routing.module';
import {CommonModule} from '@angular/common';
import {DashboardModule} from '../../dashboard/dashboard.module';
import {By} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {DebugElement} from '@angular/core';
import {LoginRequest} from '../user-api/request/login-request';

describe('LoginFormComponent', () => {
    let component: LoginFormComponent;
    let fixture: ComponentFixture<LoginFormComponent>;
    let de: DebugElement;
    let el: any;

    const mockRouter = {
        navigateByUrl: jasmine.createSpy('navigateByUrl')
    };

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [NgMaterialModule,
                FormsModule,
                AppRoutingModule,
                CommonModule,
                DashboardModule,
                BrowserAnimationsModule,
            ],
            providers:
                [
                    {provide: Router, useValue: mockRouter},
                    AppStorageService,
                    UserApiService,
                    UserService,
                    HttpClient,
                    MockBackend,
                    BaseRequestOptions,
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    }, ]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(LoginFormComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
        expect(component.appname).toEqual('Assemble');
        expect(component.user.email).toEqual('');
        expect(component.user.password).toEqual('');
        expect(component.loggingIn).toEqual(false);
    });

    it('should represent the form model', () => {
        component.user.email = 'test@gmail.com';
        fixture.detectChanges();
        fixture.whenStable().then(() => {
            de = fixture.debugElement.query(By.css('.login-form-full-width-email-input'));
            el = de.nativeElement;

            // make sure ui changes based on model
            expect(el.value).toContain('test@gmail.com');

            // change ui category and fire event for input
            el.value = 'change@email.com';
            el.dispatchEvent(new Event('input'));

            // make sure model is updated
            expect(fixture.componentInstance.user.email).toBe('change@email.com');
        });
    });

    it('should make login request call with model input', inject([UserApiService], (service: UserApiService) => {

        const spy = spyOn(service, 'login');

        component.user.email = 'test@gmail.com';
        fixture.detectChanges();
        fixture.whenStable().then(() => {
            de = fixture.debugElement.query(By.css('.login-form-login-button'));
            el = de.nativeElement;

            // make sure ui changes based on model
            expect(el.textContent).toContain('Login');
            el.click();

            // make sure login was called with the model
            expect(spy.calls.count()).toBe(1, 'login was called');
            expect(service.login).toHaveBeenCalled();
            expect(service.login).toHaveBeenCalledWith(new LoginRequest(component.user));
        });
    }));

    it('should navigate to the register page when register is clicked', inject([Router], (router: Router) => {

        component.user.email = 'test@gmail.com';
        fixture.detectChanges();
        fixture.whenStable().then(() => {
            de = fixture.debugElement.query(By.css('.login-form-register-button'));
            el = de.nativeElement;

            // make sure ui changes based on model
            expect(el.textContent).toContain('Register');
            el.click();
            // make sure login was called with the model
            expect(router.navigateByUrl).toHaveBeenCalledWith('/register');
        });
    }));
});
