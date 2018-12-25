import {Component, EventEmitter, Input, Output} from '@angular/core';
import {MousePosition} from '../../models/MousePosition';

@Component({
  selector: 'app-file-entry',
  templateUrl: './file-entry.component.html',
  styleUrls: ['./file-entry.component.scss']
})
export class FileEntryComponent {

    @Output()
    public selectedFile = new EventEmitter();

    @Output()
    public showFileMenu = new EventEmitter<MousePosition>();

    @Input()
    public file: FileEntry;

    constructor() { }

    public showContextMenu(mouseEvent: MouseEvent): void {
        mouseEvent.preventDefault();
        this.showFileMenu.emit({
            x: mouseEvent.clientX,
            y: mouseEvent.clientY
        });
    }

    public open(): void {
        this.selectedFile.emit();
    }

}
