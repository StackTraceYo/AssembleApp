import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginFormComponent} from '../user-auth/login-form/login-form.component';
import {UserModule} from '../user-auth/user.module';
import {RegisterFormComponent} from '../user-auth/register-form/register-form.component';
import {DashboardMainComponent} from '../dashboard/dashboard-main/dashboard-main.component';
import {AuthGuard} from '../user-auth/auth-guard/auth.guard';
import {DashboardModule} from '../dashboard/dashboard.module';

const routes: Routes = [
    {path: '', redirectTo: '/login', pathMatch: 'full'},
    {path: 'login', component: LoginFormComponent},
    {path: 'register', component: RegisterFormComponent},
    {path: 'asm', component: DashboardMainComponent, canActivate: [AuthGuard]}
];

@NgModule({
    imports: [
        RouterModule.forRoot(routes)
    ],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
