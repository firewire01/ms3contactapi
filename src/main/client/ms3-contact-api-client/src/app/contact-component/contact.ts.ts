import { Address } from './address';
import { Communication } from './communication';

export class Contact {
  id: number;
  firstName: string;
  lastName: string;
  dob: string;
  gender: string;
  title: string;
  Address: Array<Address>;
  Communication: Array<Communication>;
}
