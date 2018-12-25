import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Observable} from 'rxjs';
import {FileService} from '../../services/file.service';
import {map, tap} from 'rxjs/operators';
import {MousePosition} from '../../models/MousePosition';
import {el} from '@angular/platform-browser/testing/src/browser_util';
import {Title} from '@angular/platform-browser';

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

    @Input()
    reloadRequested: boolean;

    files: Observable<ListDirResponse>;

    public showingFileMenu = false;
    public showingDirMenu = false;

    public showingRenameModal = false;
    public showingDeleteModal = false;
    public showingCreateModal = false;

    public fileToShowOptions: FileEntry;
    public mousePosition: MousePosition;
    public showDirError = false;

    public windowHeight: number;

    constructor(private filesService: FileService, private title: Title) { }

    ngOnInit() {
        this.mapFiles();
        this.windowHeight = window.innerHeight;
    }

    ngOnChanges(changes: SimpleChanges): void {
      if(changes['currentDir'] || changes['reloadRequested']) {
          this.mapFiles();
      }
    }

    private mapFiles() {
        this.title.setTitle(this.currentDir);
        this.showingDirMenu = false;
        this.showingFileMenu = false;
        this.files = this.filesService.listDir(this.currentDir).pipe(
          tap(data => this.showDirError = (data['error'] === 'reading')),
          map(data => data['listing'])
        );
    }

    public selectedFile(file: FileEntry) {
        if(file.type === 'dir') {
            this.selectedNewDir.emit(file.file);
        }
    }

    public showFileMenu(file: FileEntry, mousePosition: MousePosition) {
        this.showingDirMenu = false;
        this.fileToShowOptions = file;
        this.mousePosition = mousePosition;
        this.showingFileMenu = true;
    }

    public clickedOnFileList(mouseEvent: MouseEvent) {
        mouseEvent.preventDefault();
        let showingDirMenu = false;
        const targets = document.getElementsByClassName('clickTargetDirOptions');
        for (let i = 0; i < targets.length; i++) {
            if (mouseEvent.target === targets[i]) showingDirMenu = true;
        }
        if(showingDirMenu) {
            this.showingFileMenu = false;
            this.showingDirMenu = true;
            this.fileToShowOptions = {
                file: this.currentDir,
                type: 'dir',
                size: 0
            };
            this.mousePosition = {
                x: mouseEvent.clientX,
                y: mouseEvent.clientY
            };
        }
    }

    hideAllMenus() {
        this.showingFileMenu = false;
        this.showingDirMenu = false;
    }

    public showRenameModal(file: FileEntry) {
        this.showingRenameModal = true;
        this.fileToShowOptions = file;
    }

    public showDeleteModal(file: FileEntry) {
        this.showingDeleteModal = true;
        this.fileToShowOptions = file;
    }

    public showCreateModal() {
        this.showingCreateModal = true;

    }

    public modalClosing(reload: boolean) {
        this.showingRenameModal = false;
        this.showingDeleteModal = false;
        this.showingCreateModal = false;
        if(reload) {
            this.mapFiles();
        }
    }

    public reloadFiles() {
        this.mapFiles();
    }
}
