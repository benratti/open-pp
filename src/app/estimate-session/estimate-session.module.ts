import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EstimateSessionRoutingModule } from './estimate-session-routing.module';
import { PokerTableComponent } from './components/poker-table/poker-table.component';
import { PlayerComponent } from './components/player/player.component';


@NgModule({
  declarations: [
    PokerTableComponent,
    PlayerComponent
  ],
  imports: [
    CommonModule,
    EstimateSessionRoutingModule
  ], 
  exports: [
    PokerTableComponent, 
  ]
})
export class EstimateSessionModule { }
