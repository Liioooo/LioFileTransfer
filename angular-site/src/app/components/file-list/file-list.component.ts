import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Observable} from 'rxjs';
import {FileService} from '../../services/file.service';
import {map, tap} from 'rxjs/operators';
import {MousePosition} from '../../models/MousePosition';

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

    public showingFileMenu = false;
    public showingRenameModal = false;
    public showingDeleteModal = false
    public fileToShowOptions: FileEntry;
    public mousePosition: MousePosition;
    public showDirError = false;

    public windowHeight: number;

    constructor(private filesService: FileService) { }

    ngOnInit() {
        this.mapFiles();
        this.windowHeight = window.innerHeight;
    }

    ngOnChanges(changes: SimpleChanges): void {
      if(changes['currentDir']) {
          this.mapFiles();
      }
    }

    private mapFiles() {
        this.files = this.filesService.listDir(this.currentDir).pipe(
          tap(data => {
             if(data['error'] === 'reading') {
                 this.showDirError = true;
             } else {
                 this.showDirError = false;
             }
          }),
          map(data => data['listing'])
        );
    }

    public selectedFile(file: FileEntry) {
        if(file.type === 'dir') {
            this.selectedNewDir.emit(file.file);
        }
    }

    public showFileMenu(file: FileEntry, mousePosition: MousePosition) {
        this.showingFileMenu = true;
        this.fileToShowOptions = file;
        this.mousePosition = mousePosition;
    }

    public clickedOnFileList() {
        this.showingFileMenu = false;
    }

    public showRenameModal(file: FileEntry) {
        this.showingRenameModal = true;
        this.fileToShowOptions = file;
    }

    public showDeleteModal(file: FileEntry) {
        this.showingDeleteModal = true;
        this.fileToShowOptions = file;
    }

    public modalClosing(reload: boolean) {
        this.showingRenameModal = false;
        this.showingDeleteModal = false;
        if(reload) {
            this.mapFiles();
        }

    }
}
