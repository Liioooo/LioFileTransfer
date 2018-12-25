import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import { FileExplorerComponent } from './components/file-explorer/file-explorer.component';
import { AddressBarComponent } from './components/address-bar/address-bar.component';
import { FileListComponent } from './components/file-list/file-list.component';
import { FileEntryComponent } from './components/file-entry/file-entry.component';
import { FileOptionsComponent } from './components/file-options/file-options.component';
import { FileRenameModalComponent } from './components/file-rename-modal/file-rename-modal.component';
import { DeleteFileModalComponent } from './components/delete-file-modal/delete-file-modal.component';
import { DirOptionsComponent } from './components/dir-options/dir-options.component';
import { CreateModalComponent } from './components/create-modal/create-modal.component';

@NgModule({
  declarations: [
      AppComponent,
      LoginComponent,
      FileExplorerComponent,
      AddressBarComponent,
      FileListComponent,
      FileEntryComponent,
      FileOptionsComponent,
      FileRenameModalComponent,
      DeleteFileModalComponent,
      DirOptionsComponent,
      CreateModalComponent
  ],
  imports: [
      BrowserModule,
      AppRoutingModule,
      ReactiveFormsModule,
      HttpClientModule,
      FormsModule
  ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule { }
