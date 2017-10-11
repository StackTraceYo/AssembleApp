import {inject, TestBed} from '@angular/core/testing';

import {AppStorageService} from './app-storage.service';

interface TestObj {
    testString: string;
    testNumber: number;
    testObj: TestObj[];
}

describe('AppStorageService', () => {
    beforeEach(() => {
        localStorage.clear();
        TestBed.configureTestingModule({
            providers: [AppStorageService]
        });
    });

    it('should be created', inject([AppStorageService], (service: AppStorageService) => {
        expect(service).toBeTruthy();
    }));

    it('should store and retrieve non objects', inject([AppStorageService], (service: AppStorageService) => {
        service.store('test-key', 'value');
        const getStored = service.retrieve('test-key');
        expect(getStored).toBeTruthy();
        const parsed = JSON.parse(getStored);
        expect(parsed).toEqual('value');
    }));

    it('should store and retrieve objects', inject([AppStorageService], (service: AppStorageService) => {
        const testObj: TestObj = JSON.parse(
            '{ "testString": "testValue", "testNumber": 4, "testObj" : [{ "testString": "testValue", "testNumber": 4, "testObj" : [] }] }'
        );
        service.store('test-key', testObj);
        const getStored = service.retrieve('test-key');
        expect(getStored).toBeTruthy();
        const parsed: TestObj = JSON.parse(getStored);
        expect(parsed.testString).toEqual('testValue');
        expect(parsed.testNumber).toEqual(4);
        expect(parsed.testObj.length).toEqual(1);
        expect(parsed.testObj[0].testString).toEqual('testValue');
        expect(parsed.testObj[0].testNumber).toEqual(4);
        expect(parsed.testObj[0].testObj.length).toEqual(0);
    }));

    it('clears storage', inject([AppStorageService], (service: AppStorageService) => {
        service.store('test-key', 'value');
        expect(service.exists('test-key')).toBe(true);
        service.clear();
        expect(service.exists('test-key')).toBe(false);
    }));
});
