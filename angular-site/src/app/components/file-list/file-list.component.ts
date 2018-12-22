import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Observable} from 'rxjs';
import {FileService} from '../../services/file.service';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-file-list',
  templateUrl: './file-list.component.html',
  styleUrls: ['./file-list.component.scss']
})
export class FileListComponent implements OnInit, OnChanges {

    @Output()
    public selectedNewDir: EventEmitter<string> = new EventEmitter<string>();

    @Input()
    currentDir: string;

    files: Observable<ListDirResponse>;

    constructor(private filesService: FileService) { }

    ngOnInit() {
        this.mapFiles();
    }

    ngOnChanges(changes: SimpleChanges): void {
      if(changes['currentDir']) {
          this.mapFiles();
      }
    }

    private mapFiles() {
        this.files = this.filesService.listDir(this.currentDir).pipe(
          map(data => data['listing'])
        );
    }

    public selectedFile(file: FileEntry) {
        if(file.type === 'dir') {
            this.selectedNewDir.emit(file.file);
        }
    }

    public showFileMenu(file: FileEntry) {
        console.log(file)
    }
}
