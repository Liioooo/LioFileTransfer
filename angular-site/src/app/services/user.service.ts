import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map, tap} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {RequestData} from '../models/RequestData';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(private http: HttpClient) { }


    login(username: String, password: String): Observable<Object> {
        return this.http.post<RequestData>('/api/login', {
            username: username,
            password: password
        }).pipe(
            tap(data => {
                if(data.status === 100 && data.response['auth'] === true) {
                  UserService.setUserLoggedIn();
                }
            }),
            map(data => data.response)
        );
    }

    private static setUserLoggedIn() {
        sessionStorage.setItem('loggedIn', 'true');
    }

    private static setUserLoggedOut() {
        sessionStorage.removeItem('loggedIn');
    }

    public isLoggedIn(): boolean {
        return sessionStorage.getItem('loggedIn') === 'true';
    }
}
