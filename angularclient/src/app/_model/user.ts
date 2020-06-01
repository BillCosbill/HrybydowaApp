import {Book} from './book';

export class User {
  id: string;
  username: string;
  email: string;
  password: string;
  roles: string[];
  books: Book[];
  isAdmin: boolean;
}
