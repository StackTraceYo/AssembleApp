import {inject, TestBed} from '@angular/core/testing';

import {GroupApiService} from './group-api.service';
import {BaseRequestOptions, Http} from '@angular/http/';
import {HttpClient} from '../../http/http-client/http-client';
import {MockBackend} from '@angular/http/testing';

describe('GroupApiService', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            providers: [GroupApiService,
                MockBackend,
                BaseRequestOptions,
                {
                    provide: Http,
                    useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                        return new Http(backendInstance, defaultOptions);
                    },
                    deps: [MockBackend, BaseRequestOptions]
                },
                HttpClient
            ]
        });
    });

    it('should be created', inject([GroupApiService], (service: GroupApiService) => {
        expect(service).toBeTruthy();
    }));
});
