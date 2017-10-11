import {async, ComponentFixture, inject, TestBed} from '@angular/core/testing';

import {By} from '@angular/platform-browser';
import {RegisterFormComponent} from './register-form.component';
import {HttpClient} from '../../http/http-client/http-client';
import {UserService} from '../user-service';
import {UserApiService} from '../user-api/user-api.service';
import {AppStorageService} from '../../app-storage/app-storage.service';
import {DashboardModule} from '../../dashboard/dashboard.module';
import {AppRoutingModule} from '../../app-routing/app-routing.module';
import {NgMaterialModule} from '../../ng-material/ng-material.module';
import {DebugElement} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {Router} from '@angular/router';
import {MockBackend} from '@angular/http/testing';
import {BaseRequestOptions, Http} from '@angular/http';
import {RegisterRequest} from '../user-api/request/register-request';

describe('RegisterFormComponent', () => {
    let component: RegisterFormComponent;
    let fixture: ComponentFixture<RegisterFormComponent>;
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


    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [RegisterFormComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(RegisterFormComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
        expect(component.regModel.email).toEqual('');
        expect(component.regModel.password).toEqual('');
        expect(component.registering).toEqual(false);
    });

    it('should represent the form model', () => {
        component.regModel.email = 'test@gmail.com';
        fixture.detectChanges();
        fixture.whenStable().then(() => {
            de = fixture.debugElement.query(By.css('.register-form-full-width-email-input'));
            el = de.nativeElement;

            // make sure ui changes based on model
            expect(el.value).toContain('test@gmail.com');

            // change ui value and fire event for input
            el.value = 'change@email.com';
            el.dispatchEvent(new Event('input'));
            fixture.detectChanges();

            // make sure model is updated
            expect(component.regModel.email).toBe('change@email.com');
        });
    });

    it('should make register request call with model input', inject([UserApiService], (service: UserApiService) => {

        const spy = spyOn(service, 'register');

        component.regModel.email = 'test@gmail.com';
        fixture.detectChanges();
        fixture.whenStable().then(() => {
            de = fixture.debugElement.query(By.css('.register-form-register-button'));
            el = de.nativeElement;

            // make sure ui changes based on model
            expect(el.textContent).toContain('Register');
            el.click();

            // make sure login was called with the model
            expect(spy.calls.count()).toBe(1, 'register was called');
            expect(service.register).toHaveBeenCalled();
            expect(service.register).toHaveBeenCalledWith(new RegisterRequest(component.regModel));
        });
    }));
});
