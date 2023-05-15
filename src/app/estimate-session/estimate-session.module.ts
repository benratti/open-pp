import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EstimateSessionRoutingModule } from './estimate-session-routing.module';
import { PokerTableComponent } from './components/poker-table/poker-table.component';
import { PlayerComponent } from './components/player/player.component';
import { IssuesBoardComponent } from './components/issues-board/issues-board.component';
import { IssueCardComponent } from './components/issue-card/issue-card.component';


@NgModule({
  declarations: [
    PokerTableComponent,
    PlayerComponent,
    IssuesBoardComponent,
    IssueCardComponent
  ],
  imports: [
    CommonModule,
    EstimateSessionRoutingModule
  ], 
  exports: [
    PokerTableComponent, 
    IssuesBoardComponent
  ]
})
export class EstimateSessionModule { }
