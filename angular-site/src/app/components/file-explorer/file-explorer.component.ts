import { Component, OnInit } from '@angular/core';
import {Title} from '@angular/platform-browser';

@Component({
  selector: 'app-file-explorer',
  templateUrl: './file-explorer.component.html',
  styleUrls: ['./file-explorer.component.scss']
})
export class FileExplorerComponent implements OnInit {

  public currentDir: string;
  private history: string[] = [];
  private currentHistoryPos = 0;

  public reloadRequestForListFiles = true;

  constructor(private title: Title) {
    this.currentDir = '/';
  }

  ngOnInit() {
    this.title.setTitle(this.currentDir);
    this.history.push(this.currentDir);
  }

  public pathInBarChanged(newDir: string): void {
      this.currentDir = newDir;
      this.currentHistoryPos++;
      this.history[this.currentHistoryPos] = this.currentDir;
  }

  public reloadFiles(): void {
      this.reloadRequestForListFiles = !this.reloadRequestForListFiles;
  }

  public selectedNewDir(newDir: string): void {
      this.currentDir = this.currentDir + '/' + newDir;
      this.currentDir = this.currentDir.replace('//', '/');
      this.currentHistoryPos++;
      this.history[this.currentHistoryPos] = this.currentDir;
  }

  public clickedForwardBackward(direction: boolean): void {
    if(direction) { //forward
        if(this.currentHistoryPos !== this.history.length - 1) {
            this.currentHistoryPos++;
            this.currentDir = this.history[this.currentHistoryPos];
        }
    } else { //back
        if(this.currentHistoryPos !== 0) {
          this.currentHistoryPos--;
          this.currentDir = this.history[this.currentHistoryPos];
        }
    }
  }

}
