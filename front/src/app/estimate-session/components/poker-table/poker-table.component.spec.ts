import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PokerTableComponent } from './poker-table.component';
import { EstimateSessionModule } from '../../estimate-session.module';
import { VoteStatus } from '../../models/vote-status.enum';
import { PlayerVoteStatus } from '../../models/player-vote-status.enum';

describe('PokerTableComponent', () => {
  let component: PokerTableComponent;
  let fixture: ComponentFixture<PokerTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PokerTableComponent ], 
      imports: [EstimateSessionModule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PokerTableComponent);
    component = fixture.componentInstance;

  });

  it('should create', () => {

    component.sessionName = "My private session"; 
    component.vote = VoteStatus.DONE; 
    component.myId = "AZER-eaza-HGBDVC";
    component.choiceTable = ['1', '2', '3'];
    component.issue = {
      id : "aaaa",
      title : "my-title", 
      isVoting : false,
      description : "a little description" 
    }
    component.players = [
      { alias: 'Jaalib Korror', id: '1', status: PlayerVoteStatus.OBSERVER },
      { alias: 'Crev Elson', id: '2', status: PlayerVoteStatus.NONE },
      { alias: 'Ike Kravhenn', id: '3', status: PlayerVoteStatus.NONE },
      { alias: 'Neala Omega', id: '4', status: PlayerVoteStatus.OBSERVER },
      { alias: 'Bulsar Velos', id: '5', status: PlayerVoteStatus.NONE },
      { alias: 'Braganti Werjua', id: '6', status: PlayerVoteStatus.NONE },
      { alias: 'Lori Dremine', id: '7', status: PlayerVoteStatus.NONE },
      { alias: 'Otara Draav', id: '8', status: PlayerVoteStatus.NONE },
      { alias: 'Penta Axfow', id: '9', status: PlayerVoteStatus.NONE },
      { alias: 'Karva Elatar', id: '10', status: PlayerVoteStatus.OBSERVER },
      { alias: 'Janekah Sheotah', id: '11', status: PlayerVoteStatus.OBSERVER },
    ];

    fixture.detectChanges();

    expect(component).toBeTruthy();
  });
});
