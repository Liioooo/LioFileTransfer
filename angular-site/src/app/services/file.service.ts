import { Injectable } from '@angular/core';
import {UserService} from './user.service';
import {Observable} from 'rxjs';
import {RequestData} from '../models/RequestData';
import {HttpClient} from '@angular/common/http';
import {map, tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class FileService {

    constructor(private user: UserService, private http: HttpClient) { }

    private sendRequest(path: string, data: Object): Observable<any> {
        return this.http.post<RequestData>(path, data).pipe(
            tap(data => {
              if(data.status === 110) {
                this.user.setUserLoggedOut();
                this.user.isLoggedIn();
              }
            }),
            map(data => data.response)
        );
    }

    public listDir(dir: string): Observable<ListDirResponse> {
        return this.sendRequest('/api/listDir', {
            dir: dir
        });
    }

    public rename(file: string, newName: string): Observable<any> {
        return this.sendRequest('/api/rename', {
            toRename: file,
            newName: newName
        });
    }

    public delete(file: string): Observable<any> {
        return this.sendRequest('/api/delete', {
            toDelete: file
        });
    }

    public create(file: string): Observable<any> {
        return this.sendRequest('/api/create', {
            toCreate: file
        });
    }

    public copy(from: string, to: string): Observable<any> {
        return this.sendRequest('/api/copy', {
            src: from,
            dest: to
        });
    }

    public move(from: string, to: string): Observable<any> {
        return this.sendRequest('/api/move', {
            src: from,
            dest: to
        });
    }

    public downloadFile(file: string): void {
        let element = document.createElement('a');
        element.setAttribute('href', '/download' + file);
        element.setAttribute('download', '');
        element.style.display = 'none';
        document.body.appendChild(element);
        element.click();
        document.body.removeChild(element);
    }
}
