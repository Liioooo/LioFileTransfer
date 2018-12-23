import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MousePosition} from '../../models/MousePosition';
import {FileService} from '../../services/file.service';

@Component({
  selector: 'app-file-options',
  templateUrl: './file-options.component.html',
  styleUrls: ['./file-options.component.scss']
})
export class FileOptionsComponent implements OnInit {

    @Output()
    public openDir: EventEmitter<FileEntry> = new EventEmitter<FileEntry>();

    @Output()
    public showRenameModal: EventEmitter<FileEntry> = new EventEmitter<FileEntry>();

    @Output()
    public showDeleteModal: EventEmitter<FileEntry> = new EventEmitter<FileEntry>();

    @Input()
    public file: FileEntry;

    @Input()
    public currentDir: string;

    @Input()
    public mousePosition: MousePosition;

    constructor(private files: FileService) { }

    ngOnInit() {
    }

    openDirClick() {
        this.openDir.emit(this.file);
    }

    downloadFileClick() {
      this.files.downloadFile((this.currentDir + '/' + this.file.file).replace('//', '/'));
    }

    renameFileClick() {
        this.showRenameModal.emit(this.file);
    }

    deleteFileClick() {
        this.showDeleteModal.emit(this.file);
    }

}
