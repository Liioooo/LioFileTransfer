import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FileService} from '../../services/file.service';

@Component({
    selector: 'app-delete-file-modal',
    templateUrl: './delete-file-modal.component.html',
    styleUrls: ['./delete-file-modal.component.scss']
})
export class DeleteFileModalComponent implements OnInit {

    @Output()
    public closingModal: EventEmitter<boolean> = new EventEmitter<boolean>();

    @Input()
    public file: FileEntry;

    @Input()
    public currentDir: string;

    public newName: string;
    public errorDeletingFile = false;

    constructor(private filesService: FileService) { }

    ngOnInit() {
        this.newName = this.file.file;
    }

    closeModalDelete() {
        let toDelete = (this.currentDir + '/' + this.file.file).replace('//', '/');
        this.filesService.delete(toDelete).subscribe(data => {
          console.log(data);
            if(data['error'] === 'null') {
                this.errorDeletingFile = false;
                this.closingModal.emit(true);
            } else {
                this.errorDeletingFile = true;
            }
        });
    }

    closeModalClickedOutside(event: MouseEvent) {
        if(event.target == document.getElementById('modal')) {
            this.closingModal.emit(false);
            this.errorDeletingFile = false;
        }
    }

    closeModalCancel() {
        this.closingModal.emit(false);
        this.errorDeletingFile = false;
    }

}
