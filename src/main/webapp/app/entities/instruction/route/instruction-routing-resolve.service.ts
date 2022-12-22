import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInstruction } from '../instruction.model';
import { InstructionService } from '../service/instruction.service';

@Injectable({ providedIn: 'root' })
export class InstructionRoutingResolveService implements Resolve<IInstruction | null> {
  constructor(protected service: InstructionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInstruction | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((instruction: HttpResponse<IInstruction>) => {
          if (instruction.body) {
            return of(instruction.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
