import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginFormComponent} from './login-form/login-form.component';
import {NgMaterialModule} from '../ng-material/ng-material.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {UserApiService} from './user-api/user-api.service';
import {RegisterFormComponent} from './register-form/register-form.component';
import {RouterModule} from '@angular/router';
import {StoreModule} from '@ngrx/store';
import { EffectsModule } from '@ngrx/effects';
import {reducers} from './reducers/reducers';
import {AuthEffects} from './effects/auth.effects';

@NgModule({
    imports: [
        CommonModule,
        NgMaterialModule,
        FormsModule,
        RouterModule,
        ReactiveFormsModule,
        StoreModule,
        StoreModule.forFeature('auth', reducers),
        EffectsModule.forFeature([AuthEffects]),
    ],
    declarations: [LoginFormComponent, RegisterFormComponent],
    providers: [UserApiService]
})
export class UserModule {
}
