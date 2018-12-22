import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map, tap} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {RequestData} from '../models/RequestData';
import {Router} from '@angular/router';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(private http: HttpClient, private router: Router) { }


    login(username: string, password: string): Observable<Object> {
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

    public setUserLoggedOut() {
        sessionStorage.removeItem('loggedIn');
    }

    public isLoggedIn(): boolean {
        let isLoggedIn = sessionStorage.getItem('loggedIn') === 'true';
        if (!isLoggedIn && this.router.url !== '/login') {
            this.router.navigate(['/login']);
        }
        return isLoggedIn;
    }
}
