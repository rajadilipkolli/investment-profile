import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';

import { SipComponent } from './sip.component';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';

describe('SipComponent', () => {
  let component: SipComponent;
  let fixture: ComponentFixture<SipComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
    declarations: [SipComponent],
    imports: [RouterTestingModule,
        FormsModule],
    providers: [provideHttpClient(withInterceptorsFromDi()), provideHttpClientTesting()]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SipComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
