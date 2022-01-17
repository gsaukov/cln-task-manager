import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
} from '@angular/common/http';
import { Observable } from 'rxjs';

export class HeaderInterceptor implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const clonedRequest = req.clone({ headers: req.headers.append('Cln-Auth', 'totally_secret') });
    return next.handle(clonedRequest);
  }
}
