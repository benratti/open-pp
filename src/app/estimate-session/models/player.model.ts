import { PlayerVoteStatus } from "./player-vote-status.enum";

export class Player {

    alias !: string;
    id !: string;
    status !: PlayerVoteStatus; 
    vote ?: string;

}


