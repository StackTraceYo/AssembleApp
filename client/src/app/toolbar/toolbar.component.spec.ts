import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ToolbarComponent} from './toolbar.component';
import {NgMaterialModule} from '../ng-material/ng-material.module';
import {FormsModule} from '@angular/forms';
import {AppStorageService} from '../app-storage/app-storage.service';
import {APP_BASE_HREF} from '@angular/common/';
import {AppRoutingModule} from '../app-routing/app-routing.module';


xdescribe('ToolbarComponent', () => {

    let component: ToolbarComponent;
    let fixture: ComponentFixture<ToolbarComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [NgMaterialModule, FormsModule, AppRoutingModule],
            providers: [
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
});
