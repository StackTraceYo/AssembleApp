import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginFormComponent} from './login-form/login-form.component';
import {NgMaterialModule} from '../ng-material/ng-material.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {UserApiService} from './user-api/user-api.service';
import {RegisterFormComponent} from './register-form/register-form.component';
import {RouterModule} from '@angular/router';
import {UserService} from './user-service';
import {StoreModule} from '@ngrx/store';
import {reducers} from './reducers/reducers';

@NgModule({
    imports: [
        CommonModule,
        NgMaterialModule,
        FormsModule,
        RouterModule,
        ReactiveFormsModule,
        StoreModule,
        StoreModule.forFeature('auth', reducers),
    ],
    declarations: [LoginFormComponent, RegisterFormComponent],
    providers: [UserApiService, UserService]
})
export class UserModule {
}
