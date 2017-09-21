import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginFormComponent} from "../login/login-form/login-form.component";
import {LoginModule} from "../login/login.module";

const routes: Routes = [
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'login', component: LoginFormComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes),
    LoginModule],
  exports: [RouterModule]
})
export class AppRoutingModule {
}