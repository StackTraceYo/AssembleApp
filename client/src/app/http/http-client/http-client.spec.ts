import {inject, TestBed} from '@angular/core/testing';

import {HttpClient} from './http-client';
import {BaseRequestOptions, Http, HttpModule, Response, ResponseOptions} from '@angular/http';
import {MockBackend, MockConnection} from '@angular/http/testing';

describe('HttpClientService', () => {

    const mockResponse = {
        data: [
            {id: 0, name: 'Obj 0'},
            {id: 1, name: 'Obj 1'},
            {id: 2, name: 'Obj 2'},
            {id: 3, name: 'Obj 3'},
        ]
    };

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpModule],
            providers: [
                HttpClient,
                MockBackend,
                BaseRequestOptions,
                {
                    provide: Http,
                    useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                        return new Http(backendInstance, defaultOptions);
                    },
                    deps: [MockBackend, BaseRequestOptions]
                },
            ]
        });
    });

    it('should be created', inject([HttpClient], (service: HttpClient) => {
        expect(service).toBeTruthy();
    }));

    it('get requests should return a Observable<Response>', inject([HttpClient, MockBackend], (service: HttpClient, mockBackend: MockBackend) => {
        mockBackend.connections.subscribe((connection: MockConnection) => {
            const options = new ResponseOptions({
                body: JSON.stringify(mockResponse)
            });
            connection.mockRespond(new Response(options));
        });

        service.get('http://testurl').subscribe((response) => {
            const res = response.json();
            expect(res).toEqual(mockResponse);
        });
    }));

    it('post requests should return a Observable<Response>', inject([HttpClient, MockBackend], (service: HttpClient, mockBackend: MockBackend) => {
        mockBackend.connections.subscribe((connection: MockConnection) => {
            const options = new ResponseOptions({
                body: JSON.stringify(mockResponse)
            });
            connection.mockRespond(new Response(options));
        });

        service.post('http://testurl', {data: 'test'}).subscribe((response) => {
            const res = response.json();
            expect(res).toEqual(mockResponse);
        });
    }));
});
