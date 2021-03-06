import {ChangeDetectorRef, Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Observable} from 'rxjs';
import {FileService} from '../../services/file.service';
import {map, tap} from 'rxjs/operators';
import {MousePosition} from '../../models/MousePosition';
import {el} from '@angular/platform-browser/testing/src/browser_util';
import {Title} from '@angular/platform-browser';
import {Clipboard} from '../../models/Clipboard';

@Component({
  selector: 'app-file-list',
  templateUrl: './file-list.component.html',
  styleUrls: ['./file-list.component.scss']
})
export class FileListComponent implements OnInit, OnChanges {

    @Output()
    public selectedNewDir = new EventEmitter<string>();

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

    public clipboard: Clipboard = {
        file: null,
        copyCut: null
    };

    constructor(private filesService: FileService, private title: Title, private _changeDetectionRef: ChangeDetectorRef) { }

    ngOnInit() {
        this.mapFiles();
        this.windowHeight = window.innerHeight;
    }

    ngOnChanges(changes: SimpleChanges): void {
      if(changes['currentDir'] || changes['reloadRequested']) {
          this.mapFiles();
      }
    }

    public mapFiles(): void {
        this.title.setTitle(this.currentDir);
        this.showingDirMenu = false;
        this.showingFileMenu = false;
        this.files = this.filesService.listDir(this.currentDir).pipe(
          tap(data => this.showDirError = (data['error'] === 'reading')),
          map(data => data['listing'])
        );
    }

    public reloadAfterPaste(reload: boolean): void {
        if(reload) {
            this.mapFiles();
            this._changeDetectionRef.detectChanges();
        }
    }

    public selectedFile(file: FileEntry) {
        if(file.type === 'dir') {
            this.selectedNewDir.emit(file.file);
        }
    }

    public showFileMenu(file: FileEntry, mousePosition: MousePosition): void {
        this.showingDirMenu = false;
        this.fileToShowOptions = file;
        this.mousePosition = mousePosition;
        this.showingFileMenu = true;
    }

    public clickedOnFileList(mouseEvent: MouseEvent): void {
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

    public hideAllMenus(): void {
        this.showingFileMenu = false;
        this.showingDirMenu = false;
    }

    public showRenameModal(file: FileEntry): void {
        this.showingRenameModal = true;
        this.fileToShowOptions = file;
    }

    public showDeleteModal(file: FileEntry): void {
        this.showingDeleteModal = true;
        this.fileToShowOptions = file;
    }

    public showCreateModal(): void {
        this.showingCreateModal = true;

    }

    public modalClosing(reload: boolean): void {
        this.showingRenameModal = false;
        this.showingDeleteModal = false;
        this.showingCreateModal = false;
        if(reload) {
            this.mapFiles();
        }
    }

    public copyFile(file: FileEntry): void {
        this.clipboard.file = (this.currentDir + '/' + file.file).replace('//', '/');
        this.clipboard.copyCut = 'copy';
    }

    public cutFile(file: FileEntry): void {
        this.clipboard.file = (this.currentDir + '/' + file.file).replace('//', '/');
        this.clipboard.copyCut = 'cut';
    }

    public fileInInputChanged(event: any): void {
        const file: File = event.target.files[0];
        if(!file) return;
        const fileName = (this.currentDir + '/' + file.name).replace('//', '/');
        this.filesService.uploadFile(fileName,file).subscribe(data => {
            this.mapFiles();
        });
    }
}
