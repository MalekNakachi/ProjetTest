import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'MailSearch'
})

export class MailSearchPipe implements PipeTransform {
  transform(value, args?): Array<any> {
    let searchText = new RegExp(args, 'ig');
    if (value) {
      return value.filter(mail => {
        if(mail.demandeProprietaire || 'Bon de Sortie'){
          if(mail.demandeProprietaire.search(searchText) !== -1 || mail.demandeProprietaire.search(searchText) !== -1){
            return true;
          }
        }
      });
    }
  }
}
