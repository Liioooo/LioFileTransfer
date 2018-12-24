import {Component, Input, OnInit} from '@angular/core';
import {MousePosition} from '../../models/MousePosition';

@Component({
  selector: 'app-dir-options',
  templateUrl: './dir-options.component.html',
  styleUrls: ['./dir-options.component.scss']
})
export class DirOptionsComponent implements OnInit {

    @Input()
    public file: FileEntry;

    @Input()
    public currentDir: string;

    @Input()
    public mousePosition: MousePosition;

    constructor() { }

    ngOnInit() {
    }

}
