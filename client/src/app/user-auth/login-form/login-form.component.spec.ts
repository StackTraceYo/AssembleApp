import {async, ComponentFixture, TestBed} from '@angular/core/testing';

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

fdescribe('LoginFormComponent', () => {
    let component: LoginFormComponent;
    let fixture: ComponentFixture<LoginFormComponent>;
    let de: DebugElement;
    let el: any;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [NgMaterialModule, FormsModule, AppRoutingModule, CommonModule, DashboardModule, BrowserAnimationsModule],
            providers: [
                {
                    provide: Router,
                    useClass: class {
                        navigate = jasmine.createSpy('navigate');
                    }
                },
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
                },]
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
        de = fixture.debugElement.query(By.css('h1'));
        el = de.nativeElement;
        expect(el.textContent).toContain('test@gmail.com');

    });
});
