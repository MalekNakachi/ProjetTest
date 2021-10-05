import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'RoleSearchPipe', pure: false })
export class RoleSearchPipe implements PipeTransform {
  transform(value, args?): Array<any> {
    let searchText = new RegExp(args, 'ig');
    if (value) {
      return value.filter(role => {
        if (role.name) {
          return role.name.search(searchText) !== -1;
        }
        else{
          return role.id.search(searchText) !== -1;
        }
      });
    }
  }
}
