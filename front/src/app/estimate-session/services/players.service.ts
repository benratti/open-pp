import { Injectable } from '@angular/core';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { Player } from '../models/player.model';
import { PlayerVoteStatus } from '../models/player-vote-status.enum';

@Injectable({
  providedIn: 'root'
})
export class PlayersService {

  private players$ !: ReplaySubject<Player[]>;

  private players !: [
    { alias: "Jaalib Korror", id: "1", status: PlayerVoteStatus.NONE},
    { alias: "Crev Elson", id: "1", status: PlayerVoteStatus.VOTE_COMPLETED},
    { alias: "Ike Kravhenn", id: "1",status: PlayerVoteStatus.VOTE_IN_PROGRESS},
    { alias: "Neala Omega", id: "1",status: PlayerVoteStatus.VOTE_REVELEAD},
    { alias: "Bulsar Velos", id: "1", status: PlayerVoteStatus.VOTE_IN_PROGRESS},
    { alias: "Braganti Werjua", id: "1",status: PlayerVoteStatus.VOTE_REVELEAD},
    { alias: "Lori Dremine", id: "1",status: PlayerVoteStatus.VOTE_REVELEAD},
    { alias: "Otara Draav", id: "1",status: PlayerVoteStatus.VOTE_REVELEAD},
    { alias: "Penta Axfow", id: "1",status: PlayerVoteStatus.VOTE_REVELEAD},
    { alias: "Karva Elatar", id: "1",status: PlayerVoteStatus.VOTE_REVELEAD},
    { alias: "Janekah Sheotah", id: "1",status: PlayerVoteStatus.VOTE_REVELEAD},
  
  ]


  constructor() { 

    this.players$ = new ReplaySubject<Player[]>(); 
    this.players$.next(this.players); 
    
  }

  getPlayers() : Observable<Player[]> {
    return this.players$; 
  }

}
