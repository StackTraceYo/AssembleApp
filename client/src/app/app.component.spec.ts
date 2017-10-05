import {async, TestBed} from '@angular/core/testing';

import {AppComponent} from './app.component';
import {ToolbarModule} from './toolbar/toolbar.module';
import {DashboardModule} from './dashboard/dashboard.module';
import {APP_BASE_HREF, CommonModule} from '@angular/common/';
import {RouterModule} from '@angular/router/';
import {AppStorageService} from './app-storage/app-storage.service';

describe('AppComponent', () => {
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [ToolbarModule, DashboardModule, RouterModule],
            declarations: [
                AppComponent
            ],
            providers: [{provide: APP_BASE_HREF, useValue: '/'}, AppStorageService]
        }).compileComponents();
    }));

    it('should create the app', async(() => {
        const fixture = TestBed.createComponent(AppComponent);
        const app = fixture.debugElement.componentInstance;
        expect(app).toBeTruthy();
    }));
});
