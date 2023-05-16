import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EstimateSessionModule } from './estimate-session/estimate-session.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule, 
    EstimateSessionModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
