import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlayerComponent } from './player.component';
import { PlayerVoteStatus } from "./../../models/player-vote-status.enum";

describe('PlayerComponent', () => {
  let component: PlayerComponent;
  let fixture: ComponentFixture<PlayerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PlayerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PlayerComponent);
    component = fixture.componentInstance;

  });

  it('should create', () => {



    component.player = {
      status : PlayerVoteStatus.OBSERVER, 
      alias : "Wonder Alias",
      id : "azer-reza-erz-ezez"
    }; 

    fixture.detectChanges();

    expect(component).toBeTruthy();
  });
});
