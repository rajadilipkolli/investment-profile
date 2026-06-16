import { Directive, ElementRef, inject } from '@angular/core';

@Directive({
    selector: '[appDropdown]',
    host: {
      '[class.open]': 'isOpen',
      '(document:click)': 'toggleOpen($event)'
    }
})
export class DropdownDirective {
  isOpen = false;
  private elRef = inject(ElementRef);

  toggleOpen(event: Event) {
    this.isOpen = this.elRef.nativeElement.contains(event.target) ? !this.isOpen : false;
  }

}
