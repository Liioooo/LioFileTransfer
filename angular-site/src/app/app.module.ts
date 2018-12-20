import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import {ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import { FileExplorerComponent } from './components/file-explorer/file-explorer.component';

@NgModule({
  declarations: [
      AppComponent,
      LoginComponent,
      FileExplorerComponent
  ],
  imports: [
      BrowserModule,
      AppRoutingModule,
      ReactiveFormsModule,
      HttpClientModule
  ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule { }
