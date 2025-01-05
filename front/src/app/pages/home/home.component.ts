import { AsyncPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { Router, RouterLink } from '@angular/router';
import { Observable } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';
@Component({
  selector: 'app-home',
  imports: [
    MatButtonModule,
    RouterLink,
    AsyncPipe
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  public $isLogged!: Observable<boolean>;

  constructor(
    private sessionService: SessionService,
    private router: Router
  ) {
    this.$isLogged = this.sessionService.$isLogged();
  }

}
// export class HomeComponent implements OnInit {
//   constructor() { }

//   ngOnInit(): void { }


// }
