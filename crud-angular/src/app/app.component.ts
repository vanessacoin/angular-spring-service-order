import { BooleanInput } from '@angular/cdk/coercion';
import { BreakpointObserver } from '@angular/cdk/layout';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDrawerMode, MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive,
    MatToolbarModule, MatButtonModule, MatSidenavModule, MatIconModule
    ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'crud-angular';
  mdcBackdrop: BooleanInput = false;
  drawerMode: MatDrawerMode = "push";

  readonly breakpoint$ = this.breakpointObserver
  .observe([ '(max-width: 500px)']);

  constructor(private breakpointObserver: BreakpointObserver, private router: Router) {
    this.breakpoint$.subscribe(() =>
      this.breakpointChanges()
  );
  }

  breakpointChanges(): void {

    if (this.breakpointObserver.isMatched('(max-width: 500px)')) {
      this.drawerMode = "over";
      this.mdcBackdrop = true;
    } else {
      this.drawerMode = "push";
      this.mdcBackdrop = false;
    }

  }

}
