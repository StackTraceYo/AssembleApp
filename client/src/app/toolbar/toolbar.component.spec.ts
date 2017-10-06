import {async, ComponentFixture, inject, TestBed} from '@angular/core/testing';

import {ToolbarComponent} from './toolbar.component';
import {NgMaterialModule} from '../ng-material/ng-material.module';
import {UserService} from '../user-auth/user-service';
import {FormsModule} from '@angular/forms';
import {AppStorageService} from '../app-storage/app-storage.service';
import {APP_BASE_HREF} from '@angular/common/';
import {AppRoutingModule} from '../app-routing/app-routing.module';
import {By} from '@angular/platform-browser';
import {AssembleUser} from '../user-auth/assemble.user';


describe('ToolbarComponent', () => {

    let component: ToolbarComponent;
    let fixture: ComponentFixture<ToolbarComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [NgMaterialModule, FormsModule, AppRoutingModule],
            providers: [
                UserService,
                AppStorageService,
                {provide: APP_BASE_HREF, useValue: '/'},
            ],
            declarations: [ToolbarComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ToolbarComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should switch between login and logut based on use state', inject([UserService], (service: UserService) => {


        fixture.detectChanges();
        const de = fixture.debugElement.query(By.css('toolbar-user-status-link'));
        expect(de.nativeElement.textContent).toContain('123');

        // make sure ui changes based on model
        service.storeUser(new AssembleUser('test', 'test', true));
    }));
});
